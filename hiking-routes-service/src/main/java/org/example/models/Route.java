package org.example.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.models.enums.Difficulty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "routes")
public class Route {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Difficulty difficulty;

    @Size(min = 1, max = 5, message = "Deben haber entre 3 a 10 etiquetas")
    private List<String> tags;

    @Size(min = 2, max = 2, message = "Deben haber un rango minimo y uno maximo")
    private List<Long> temperatureRange;

    @NotNull
    private Integer limit;
    private Integer participants;

    private List<User> users;

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public Boolean routeHasUser(Long id){
        if (users != null) {
            return users.stream().anyMatch(u -> u.getId().equals(id));
        }
        return false;
    }
}
