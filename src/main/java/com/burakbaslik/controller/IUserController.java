package com.burakbaslik.controller;

import com.burakbaslik.dto.user.UserDto;
import com.burakbaslik.dto.user.UserDtoIU;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IUserController {
    public List<UserDtoIU> getAllUsers();
    public UserDtoIU findUserById(@PathVariable Long id);
    public void deleteUserById(@PathVariable Long id);
    public UserDtoIU editUser(@PathVariable Long id, @RequestBody UserDtoIU userDtoIU);
    public ResponseEntity<UserDtoIU> getMyInfo();
}
