package org.example.hikingmatchservice.services;

import org.apache.catalina.LifecycleState;
import org.example.hikingmatchservice.dto.UserDto;
import org.example.hikingmatchservice.entities.Injury;
import org.example.hikingmatchservice.entities.PersonalInformation;
import org.example.hikingmatchservice.entities.Role;
import org.example.hikingmatchservice.entities.User;
import org.example.hikingmatchservice.repository.IRoleRepository;
import org.example.hikingmatchservice.repository.IUserRepository;
import org.example.hikingmatchservice.security.custom.CustomUser;
import org.example.hikingmatchservice.services.iService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public User createUser(UserDto userDto) {

        PersonalInformation personalInformation = PersonalInformation
                .builder()
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .identityNumber(userDto.getIdentityNumber())
                .identityType(userDto.getIdentityType())
                .name(userDto.getName())
                .phoneNumber(userDto.getPhone())
                .anyInjury(userDto.getIsInjury())
                .build();

        if(userDto.getIsInjury()) {
            List<Injury> injuries = userDto.getInjuries().stream().map(Injury::fromDto).toList();
            personalInformation.setInjuries(injuries);
        }

        Role role = roleRepository.findByRole(userDto.getRole().getRole());

        User user = User
                .builder()
                .personalInformation(personalInformation)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .role(role)
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optUser = userRepository.findUserByUsername(username);
        if(optUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }
        User user = optUser.get();

        return new CustomUser(user.getUsername(), user.getPassword(), user.getRoleAuthorities(), user.getId());
    }
}
