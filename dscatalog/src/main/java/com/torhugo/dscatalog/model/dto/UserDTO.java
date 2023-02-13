package com.torhugo.dscatalog.model.dto;

import com.torhugo.dscatalog.model.entities.UserModel;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    @NotBlank(message = "Requeired field!")
    private String firstName;
    private String lastName;
    @Email(message = "Requeired field!")
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
