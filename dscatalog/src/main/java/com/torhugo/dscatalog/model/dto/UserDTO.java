package com.torhugo.dscatalog.model.dto;

import com.torhugo.dscatalog.model.entities.UserModel;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(UserModel entity){
        this.idUser = entity.getIdUser();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        entity.getRoles().forEach(role -> {
            this.roles.add(new RoleDTO(role));
        });
    }
}
