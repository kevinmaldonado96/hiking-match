package org.example.hikingmatchservice.services.iService;

import org.example.hikingmatchservice.dto.UserDto;
import org.example.hikingmatchservice.entities.User;

public interface IUserService {
    User createUser(UserDto userDto);
}
