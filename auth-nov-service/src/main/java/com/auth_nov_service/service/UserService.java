package com.auth_nov_service.service;

import com.auth_nov_service.dto.UserDto;

public interface UserService {
    public UserDto addUser(UserDto userDto,String role);
}
