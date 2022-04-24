package com.grupo6.proyecto.grupo6.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.grupo6.proyecto.grupo6.entity.User;


public interface IUserService {
	
	public List<User> finAll();
	
	public Page<User> findAll(Pageable pageable);
	
	public User findById(Long id);

	public User save(User cliente);

	public void delete(Long id);

}
