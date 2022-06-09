package com.grupo6.proyecto.grupo6.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.proyecto.grupo6.entity.User;
import com.grupo6.proyecto.grupo6.service.IUserService;

@RestController
@RequestMapping("/api")
public class IUserRestController {

	
	@Autowired
	private IUserService userService;

	
	@GetMapping("/users")
	public List<User> index() {
		return userService.finAll();
	}
	
	
	@GetMapping("/users/page")
	public Page<User> index(@RequestParam Integer pageNumber, @RequestParam Integer perPage) {
		Pageable pageable = PageRequest.of(pageNumber, perPage);
		Page<User> user = userService.findAll(pageable);
		return user;
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		User usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = userService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al buscar al Usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null) {
			response.put("mensaje", "El Usuario ID:  ".concat(id.toString().concat(" no existe en al base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		response.put("usuario", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/users")
	public ResponseEntity<?> create(@Valid @RequestBody User cliente, 
											BindingResult result) {
		User usernew = null;
		String type ="administration";
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El Campo '" + err.getField() + "'" + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			cliente.setType(type);
			usernew = userService.save(cliente);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear el User");
			response.put("type","error");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido creado con Exito");
		response.put("type","success");
		response.put("user", usernew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody User user, 
									BindingResult result, 
									@PathVariable Long id) {
		User userActual = userService.findById(id);
		Map<String, Object> response = new HashMap<>();
		User userUpdate = null;

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El Campo '" + err.getField() + "'" + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (userActual == null) {
			response.put("mensaje", "Error el Usuario ID:  ".concat(id.toString().concat(" no se pudo editar")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			userActual.setName(user.getName());
			userActual.setAge(user.getAge());
			userActual.setPhone(user.getPhone());
			userActual.setWorkArea(user.getWorkArea());
			userActual.setGenero(user.getGenero());
			userUpdate = userService.save(userActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el Usuario");
			response.put("type","error");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El Usuario ha sido atualizado con Exito");
		response.put("type","success");
		response.put("user", userUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	
	
	@DeleteMapping("/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			userService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje","Error en eliminar el usuario , comunicarse con Frank");
			response.put("type","error");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Usuario Eliminado con Exito");
		response.put("type","success");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
}
