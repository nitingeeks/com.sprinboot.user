package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.User;

public interface UserService {
	
	public List<User> getUsers();

	public Optional<User> getUser(long userId);
	
	public User addUser(User user);

	public String updateUser(long userId, User user);

	public boolean deleteUser(long userId);
}
