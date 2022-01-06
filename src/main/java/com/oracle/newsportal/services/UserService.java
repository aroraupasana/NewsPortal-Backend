package com.oracle.newsportal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.newsportal.exceptions.BadRequestException;
import com.oracle.newsportal.exceptions.ResourceNotFoundException;
import com.oracle.newsportal.models.CreateUser;
import com.oracle.newsportal.models.User;
import com.oracle.newsportal.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User addUser(CreateUser createUser) {
		
		// check if there is no user with same emailId and username
		List<User> existingUsers = userRepository.findByUserEmailIdOrUserName(createUser.getUserEmailId(), createUser.getUserName());
		
		if(existingUsers.size() > 0) {
			throw new BadRequestException("UserName and email already in use");
		}
		
		User user = new User(1, createUser.getUserName(), createUser.getUserEmailId(), createUser.getUserPassword());		
		return userRepository.save(user);
	}
	

	public User verifyUser(String userName, String userPassword) {
					
		Optional<User> user = userRepository.findByUserNameAndUserPassword(userName, userPassword);
		
		if(user.isPresent()) 
			return user.get();
		else 
			throw new ResourceNotFoundException("Invalid Credentials");
		
	}

	
	public String getUserName(User user) {
		Optional<User> optionalUser = userRepository.findById(user.getUserId());
		
		if(optionalUser.isPresent())
			return optionalUser.get().getUserName();
		else
			throw new ResourceNotFoundException("User not found");
					
	}
	
}