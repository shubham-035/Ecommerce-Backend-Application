package com.auth_nov_service.service;

import com.auth_nov_service.dto.UserDto;
import com.auth_nov_service.entity.User;
import com.auth_nov_service.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto addUser(UserDto userDto,String role) {
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);
        User saved = userRepository.save(user);
        UserDto dto=new UserDto();
        BeanUtils.copyProperties(saved,dto);
        return dto;

    }
}
