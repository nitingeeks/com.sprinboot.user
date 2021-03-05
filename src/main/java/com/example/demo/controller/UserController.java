package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;


@RestController

public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String home() {
		return "Welcome to User Dashboard";
	}

	// Get all User
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> data = this.userService.getUsers();
		if(data == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity<>(data, HttpStatus.OK); 
	}
	
	// Get single user with given ID
	@GetMapping("/users/{userId}")
	public ResponseEntity<Optional<User>> getUser(@PathVariable @NotBlank String userId) {
		Optional<User> data = this.userService.getUser(Long.parseLong(userId));
		if(!data.isPresent()){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
		} 
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// Add single user
	@PostMapping(path = "/users", consumes = "application/json")
	public ResponseEntity<User> addUser(@RequestBody @NotBlank User user) {
		User data = this.userService.addUser(user);
		if(data == null){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// Update Single user
	@PutMapping(path = "/users/{userId}", consumes = "application/json")
	public ResponseEntity<String> updateUser(@PathVariable @NotBlank String userId, @RequestBody @NotBlank User user) {
		String data = this.userService.updateUser(Long.parseLong(userId),user);
		if(data == "Deleted"){
			return new ResponseEntity<>(userId + " user data updated", HttpStatus.OK);
		}
		return new ResponseEntity<>(userId + " not found", HttpStatus.NOT_FOUND);
	}
	
	// Delete single user
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable @NotBlank String userId){
		boolean data = this.userService.deleteUser(Long.parseLong(userId));
		if(!data){
			return new ResponseEntity<>(userId + " User not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userId + " User has been deleted successfully", HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	 
	    ex.getBindingResult().getFieldErrors().forEach(error -> 
	        errors.put(error.getField(), error.getDefaultMessage()));
	     
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		
		ex.getConstraintViolations().forEach(cv -> {
			errors.put("message", cv.getMessage());
			errors.put("path", (cv.getPropertyPath()).toString());
		});	

		return errors;
	}
	
}
