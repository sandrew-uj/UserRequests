package com.userrequests.controllers;

import com.userrequests.dto.LoginDto;
import com.userrequests.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("admin/all")
    public ResponseEntity<List<LoginDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("admin/toOperator/{userId}")
    public ResponseEntity<LoginDto> toOperator(@PathVariable int userId) {
        return new ResponseEntity<>(userService.toOperator(userId), HttpStatus.OK);
    }
}
