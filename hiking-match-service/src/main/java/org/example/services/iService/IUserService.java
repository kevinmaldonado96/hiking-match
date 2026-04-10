package org.example.services.iService;

import org.example.dto.UserDto;
import org.example.entities.User;

public interface IUserService {
    User createUser(UserDto userDto);
}
