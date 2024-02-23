package com.musterdekho.tutorial.service;

import com.musterdekho.tutorial.dto.UserDto;
import com.musterdekho.tutorial.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
    public List<User> getAllUsers();



}