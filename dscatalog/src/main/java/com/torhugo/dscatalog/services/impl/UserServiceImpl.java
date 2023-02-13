package com.torhugo.dscatalog.services.impl;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.exception.impl.ResourceNotFoundException;
import com.torhugo.dscatalog.mapper.UserMapper;
import com.torhugo.dscatalog.model.dto.UserDTO;
import com.torhugo.dscatalog.model.dto.UserFullDTO;
import com.torhugo.dscatalog.model.entities.UserModel;
import com.torhugo.dscatalog.repository.UserRepository;
import com.torhugo.dscatalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageRequest){
		
		Page<UserModel> list = repository.findAll(pageRequest);
		return list.map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<UserModel> obj = repository.findById(id);
		UserModel entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		
		return new UserDTO(entity);
	}

	@Transactional
    public UserDTO insert(UserFullDTO dto) {
		UserModel entity = new UserModel();
		entity = userMapper.mapper(dto);
		passwordEnconder(entity, dto.getPassword());
		repository.save(entity);

		return new UserDTO(entity);
    }

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			UserModel entity = findByIdUser(id);
			entity = userMapper.mapper(dto);
			repository.save(entity);

			return new UserDTO(entity);
		} catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e){
			throw new DataBaseException("Integrity violation!");
		}
	}

	private void passwordEnconder(final UserModel entity, final String password){
		entity.setPassword(passwordEncoder.encode(password));
	}

	private UserModel findByIdUser(final Long idUser){
		return repository.findById(idUser).orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
	}
}
