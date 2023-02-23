package com.torhugo.dscatalog.resource;

import com.torhugo.dscatalog.model.dto.UserDTO;
import com.torhugo.dscatalog.model.dto.UserFullDTO;
import com.torhugo.dscatalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){

		Page<UserDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{idUser}")
	public ResponseEntity<UserDTO> findById(@PathVariable final Long idUser) {
		UserDTO dto = service.findById(idUser);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody final UserFullDTO dto){
		UserDTO newDTO = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
												.path("/{idUser}").buildAndExpand(newDTO.getIdUser()).toUri();
		return ResponseEntity.status(HttpStatus.CREATED).body(newDTO);
	}

	@PutMapping(value = "/{idUser}")
	public ResponseEntity<UserDTO> update(@PathVariable final Long idUser,
										  @Valid @RequestBody final UserFullDTO dto){

		UserDTO newDTO = service.update(idUser, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{idUser}")
	public ResponseEntity<UserDTO> delete(@PathVariable final Long idUser){

		service.delete(idUser);
		return ResponseEntity.noContent().build();
	}
}
