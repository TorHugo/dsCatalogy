package com.torhugo.dscatalog.services;

import com.torhugo.dscatalog.model.dto.UserDTO;
import com.torhugo.dscatalog.model.dto.UserFullDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface UserService {
    public Page<UserDTO> findAllPaged(Pageable pageRequest);
    public UserDTO findById(Long idUser);
    public UserDTO insert(UserFullDTO dto);
    public UserDTO update(Long idUser, UserFullDTO dto);
    public void delete(Long idUser);
}
