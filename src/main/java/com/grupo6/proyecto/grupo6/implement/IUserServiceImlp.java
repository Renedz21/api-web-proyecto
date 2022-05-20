package com.grupo6.proyecto.grupo6.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grupo6.proyecto.grupo6.dao.IUserDao;
import com.grupo6.proyecto.grupo6.entity.User;
import com.grupo6.proyecto.grupo6.service.IUserService;

@Service
public class IUserServiceImlp implements IUserService{

	
	@Autowired
	private IUserDao userDao;
	
	
	@Override
	public List<User> finAll() {
		
		return userDao.findAll();
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		String data = "administration";
		return userDao.findAllByType(pageable,data);
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userDao.findById(id).orElse(null);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}

	@Override
	public void delete(Long id) {
		userDao.deleteById(id);
		
	}

}
