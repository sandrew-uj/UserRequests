package com.userrequests.service;

import com.userrequests.dto.LoginDto;

import java.util.List;

public interface UserService {
    List<LoginDto> getAllUsers();

    LoginDto toOperator(int userId);
}
