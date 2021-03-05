package com.example.demo.services;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dao.UserDao;
import com.example.demo.entities.User;

@Service
public class UserServiceimpl implements UserService {
	
	@Autowired
	private UserDao userDao;


	@Override
	public List<User> getUsers() {
		return userDao.findAll();
	}

	@Override
	public Optional<User> getUser(long userId) {
		return userDao.findById(userId);
	}

	@Override
	public User addUser(User user) {
		userDao.save(user);
		return user;
	}

	@Override
	public String updateUser(long userId, User updUser) {
		if(userDao.existsById(userId)) {
			updUser.setId(userId);
			userDao.save(updUser);
			return "Deleted";
		}
		return "Not Found";
	}

	@Override
	public boolean deleteUser(long userId) {
		if(userDao.existsById(userId)) {
			userDao.deleteById(userId);
			return true;
		}
		return false;
	}

}
