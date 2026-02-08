package com.burakbaslik.service;

import com.burakbaslik.dto.user.UserDto;
import com.burakbaslik.dto.user.UserDtoIU;
import com.burakbaslik.model.User;

import java.util.List;

public interface IUserService {
    public List<UserDtoIU> getAllUsers();
    public UserDtoIU findUserById(Long id);
    public void removeUserById(Long id);
    public UserDtoIU editUser(Long id, UserDtoIU userDtoIU);
    public UserDtoIU getMe();
}
