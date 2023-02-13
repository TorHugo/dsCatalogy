package com.torhugo.dscatalog.mapper;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.model.dto.RoleDTO;
import com.torhugo.dscatalog.model.dto.UserDTO;
import com.torhugo.dscatalog.model.entities.RoleModel;
import com.torhugo.dscatalog.model.entities.UserModel;
import com.torhugo.dscatalog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    public UserModel mapper(final UserDTO dto){
        final UserModel entity = new UserModel();

        entity.setIdUser(dto.getIdUser());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();

        for (RoleDTO roleDTO: dto.getRoles()) {
            RoleModel role = roleRepository.findById(roleDTO.getIdRole()).orElseThrow(() -> new DataBaseException("Entity not found!"));
            entity.getRoles().add(role);
        }

        return entity;
    }
}
