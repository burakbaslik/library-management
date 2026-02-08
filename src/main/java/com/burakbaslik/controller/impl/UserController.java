package com.burakbaslik.controller.impl;

import com.burakbaslik.controller.IUserController;
import com.burakbaslik.dto.author.AuthRequest;
import com.burakbaslik.dto.author.CreateUserRequest;
import com.burakbaslik.dto.author.RegisterRequest;
import com.burakbaslik.dto.user.UserDtoIU;
import com.burakbaslik.model.User;
import com.burakbaslik.service.impl.UserServiceImpl;
import com.burakbaslik.service.impl.JwtServiceImpl;

import jakarta.servlet.ServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController implements IUserController {

    private final UserServiceImpl userService;

    private final AuthenticationManager authenticationManager;

    private final JwtServiceImpl jwtService;

    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager,
            JwtServiceImpl jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Security JWT Token";
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody CreateUserRequest createUserRequest, ServletRequest servletRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());

        }
        log.info("invalid username" + authRequest.username());
        throw new UsernameNotFoundException("invalid username {}" + authRequest.username());
    }

    @GetMapping("/users")
    @Override
    public List<UserDtoIU> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @Override
    public UserDtoIU findUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        userService.removeUserById(id);
    }

    @PutMapping("/users/edit/{id}")
    @Override
    public UserDtoIU editUser(@PathVariable(name = "id") Long id, @RequestBody UserDtoIU userDtoIU) {
        return userService.editUser(id, userDtoIU);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDtoIU> getMyInfo() {
        UserDtoIU currentUserDto = userService.getMe();

        return ResponseEntity.ok(currentUserDto);
    }

}
