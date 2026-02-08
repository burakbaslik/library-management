package com.burakbaslik.service.impl;

import com.burakbaslik.dto.author.CreateUserRequest;
import com.burakbaslik.dto.author.RegisterRequest;
import com.burakbaslik.dto.user.UserDto;
import com.burakbaslik.dto.user.UserDtoIU;
import com.burakbaslik.model.Role;
import com.burakbaslik.model.User;
import com.burakbaslik.repository.UserRepository;
import com.burakbaslik.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserDtoIU convertToUserDtoIU(User user) {
        if (user == null) {
            return null;
        }
        UserDtoIU userDto = new UserDtoIU();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword()); // Güvenlik notu: Parolayı DTO'da göndermek genellikle tavsiye edilmez.
        userDto.setEnabled(user.isEnabled());


        // En önemli kısım: authorities alanını manuel olarak set edin.
        userDto.setAuthorities(user.getAuthorities());

        return userDto;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ADMIN tarafından rol verilebilen kullanıcı oluşturma

    public User createUser(CreateUserRequest createUserRequest) {
        User newUser = User.builder()
                .name(createUserRequest.name())
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .authorities(createUserRequest.authorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        return userRepository.save(newUser);
    }

    // Public kayıt: her zaman ROLE_USER ile oluşturur
    public User register(RegisterRequest registerRequest) {
        User newUser = User.builder()
                .name(registerRequest.name())
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .authorities(Set.of(Role.ROLE_USER))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public List<UserDtoIU> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::convertToUserDtoIU) // Manuel dönüşüm metodu kullanılıyor
                .collect(Collectors.toList());

        /*List<User> users = userRepository.findAll();
        List<UserDtoIU> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDtoIU userCopy = new UserDtoIU();
            BeanUtils.copyProperties(user, userCopy);
            userDtos.add(userCopy);
        }
        return userDtos;*/
    }

    @Override
    public UserDtoIU findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return convertToUserDtoIU(user);

        /*User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDtoIU userCopy = new UserDtoIU();
        BeanUtils.copyProperties(user, userCopy);
        return  userCopy;*/
    }

    @Override
    public void removeUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDtoIU editUser(Long id,  UserDtoIU userDtoIU) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User userToUpdate = userOptional.get();

            userToUpdate.setUsername(userDtoIU.getUsername());
            if (userDtoIU.getPassword() != null && !userDtoIU.getPassword().isEmpty()) {
                userToUpdate.setPassword(passwordEncoder.encode(userDtoIU.getPassword()));
            }

            userToUpdate.setAuthorities(userDtoIU.getAuthorities());

            User updatedUser = userRepository.save(userToUpdate);
            return convertToUserDtoIU(updatedUser);
        }
        return null;

    }

    @Override
    public UserDtoIU getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Oturum açmış bir kullanıcı bulunamadı.");
        }

        // 2. Authentication nesnesinden kullanıcının adını (username) al.
        String currentUsername = authentication.getName();

        // 3. Kullanıcı adını kullanarak veritabanından User entity'sini bul.
        //    Eğer kullanıcı token'da var ama DB'de yoksa hata fırlat.
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + currentUsername));

        return convertToUserDtoIU(user);
    }



}
