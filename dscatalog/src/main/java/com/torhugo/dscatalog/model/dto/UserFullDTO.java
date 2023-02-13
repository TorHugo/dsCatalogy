package com.torhugo.dscatalog.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFullDTO extends UserDTO {
    private String password;
}
