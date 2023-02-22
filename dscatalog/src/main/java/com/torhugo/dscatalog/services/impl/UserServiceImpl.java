package com.torhugo.dscatalog.services.impl;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.exception.impl.ResourceNotFoundException;
import com.torhugo.dscatalog.mapper.UserMapper;
import com.torhugo.dscatalog.model.dto.UserDTO;
import com.torhugo.dscatalog.model.dto.UserFullDTO;
import com.torhugo.dscatalog.model.entities.UserModel;
import com.torhugo.dscatalog.repository.UserRepository;
import com.torhugo.dscatalog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(final Pageable pageRequest){
		log.info("1. Searching products in the database by page {} and size {}.", pageRequest.getPageNumber(), pageRequest.getPageSize());
		Page<UserModel> list = repository.findAll(pageRequest);
		return list.map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public UserDTO findById(final Long idUser) {
		log.info("1. Searching product in the database by category id {}", idUser);
		UserModel entity = findByIdUser(idUser);
		
		return new UserDTO(entity);
	}

	@Transactional
    public UserDTO insert(final UserFullDTO dto) {
		log.info("1. Mapping the user.");
		UserModel entity = userMapper.mapper(dto);
		log.info("2. Encrypting user password.");
		passwordEnconder(entity, dto.getPassword());
		log.info("3. Saving user in the database.");
		repository.save(entity);

		return new UserDTO(entity);
    }

	@Transactional
	public UserDTO update(final Long idUser, final UserFullDTO dto) {
		try {
			log.info("1. Searching user in the database, by user id {}", idUser);
			UserModel entity = findByIdUser(idUser);
			log.info("2. Mapping the user.");
			entity = userMapper.mapper(dto);
			log.info("3. Encrypting user password.");
			passwordEnconder(entity, dto.getPassword());
			log.info("4. Saving category in the database.");
			repository.save(entity);

			return new UserDTO(entity);
		} catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found: " + idUser);
		}
	}

	public void delete(final Long idUser) {
		try {
			log.info("1. Deleting user from database.");
			repository.deleteById(idUser);
		} catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found: " + idUser);
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

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("1. Searching user in the database, by user email {}", email);
		UserModel user = repository.findByEmail(email);
		if (user == null)
			throw new UsernameNotFoundException("Email not found.");

		return user;
	}
}
