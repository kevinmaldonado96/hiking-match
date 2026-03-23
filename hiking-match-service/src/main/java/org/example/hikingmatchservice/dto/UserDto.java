package org.example.hikingmatchservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hikingmatchservice.entities.Injury;
import org.example.hikingmatchservice.entities.User;
import org.example.hikingmatchservice.entities.enums.IdentityType;
import org.example.hikingmatchservice.validators.annotations.MatchesPassword;
import org.example.hikingmatchservice.validators.annotations.UniqueEmail;
import org.example.hikingmatchservice.validators.annotations.UniqueUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@MatchesPassword
public class UserDto implements Serializable {

    private Long id;

    @UniqueUser
    @NotNull(message = "The name is required")
    @Size(min = 2, max = 20, message = "he name size must be minimum 2 maximum 20 characters")
    private String username;

    @NotNull(message = "The lastName is required")
    @Size(min = 2, max = 20, message = "he lastName size must be minimum 2 maximum 20 characters")
    private String lastName;

    @NotNull(message = "The password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must be between 8-15 characters, contain at least 1 uppercase letter and 1 number.")
    private String password;

    @NotNull(message = "The password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must be between 8-15 characters, contain at least 1 uppercase letter and 1 number.")
    private String confirmPassword;

    @UniqueEmail
    @Email
    @NotNull(message = "The email is required")
    private String email;

    @NotNull(message = "The name is required")
    @Size(min = 2, max = 20, message = "he name size must be minimum 2 maximum 20 characters")
    private String name;

    @NotNull(message = "The address is required")
    @Size(min = 2, max = 40, message = "he name size must be minimum 2 maximum 20 characters")
    private String address;
    
    @NotNull(message = "identity type is required")
    private IdentityType identityType;

    @NotNull(message = "The identityNumber is required")
    @Pattern(regexp = "^\\d{8,12}$", message = "identityNumber must be between 8-12 characters and be numbers.")
    private String identityNumber;

    @NotNull
    @Pattern(regexp = "^[0-9]{8,10}$", message = "The phone number must be between 8 and 19 characters long and must be numeric.")
    private String phone;

    @NotNull(message = "isInjury is required")
    private Boolean isInjury;

    @Size(max = 3, message = "three injuries maximum")
    private List<InjuryDto> injuries;

    @NotNull
    private RoleDto role;

    @Builder
    public UserDto( Long id, Boolean isInjury, List<InjuryDto> injuries) {
        this.injuries = injuries;
        this.id = id;
        this.isInjury = isInjury;
    }

    public static UserDto fromUser(User user){

        List<InjuryDto> injuriesDto = user.getPersonalInformation().getInjuries().stream().map(InjuryDto::fromInjury).collect(Collectors.toList());
        return new UserDto(user.getId(), user.getPersonalInformation().getAnyInjury(), injuriesDto);
    }
}
