package org.example.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.UserDto;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private Boolean isInjury;

    @Builder
    public User(Long id, Boolean isInjury) {
        this.id = id;
        this.isInjury = isInjury;
    }

    public static User fromDtoToUser(UserDto dto) {
        return User
                .builder()
                .id(dto.getId())
                .isInjury(dto.getIsInjury())
                .build();
    }



}
