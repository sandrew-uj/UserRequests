package com.userrequests.service.impl;

import com.userrequests.dto.LoginDto;
import com.userrequests.models.Role;
import com.userrequests.models.UserEntity;
import com.userrequests.repositories.RoleRepository;
import com.userrequests.repositories.UserRepository;
import com.userrequests.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<LoginDto> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream().map(DefaultUserService::toDto).toList();
    }

    @Override
    public LoginDto toOperator(int userId) {
        var user = userRepository.getReferenceById(userId);
        var userRoles = user.getRoles();
        var operatorRole = roleRepository.findByName("OPERATOR_ROLE").get();
        userRoles.add(operatorRole);
        user.setRoles(userRoles);
        var modifiedUser = userRepository.save(user);

        return toDto(modifiedUser);
    }

    private static LoginDto toDto(UserEntity userEntity) {
        return new LoginDto(
                userEntity.getUsername(),
                userEntity.getPassword()
        );
    }
}
