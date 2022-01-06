package com.oracle.newsportal.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.oracle.newsportal.exceptions.BadRequestException;
import com.oracle.newsportal.exceptions.ResourceNotFoundException;
import com.oracle.newsportal.models.CreateUser;
import com.oracle.newsportal.models.User;
import com.oracle.newsportal.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	
	@Test
	@DisplayName("test add new user")
	void testAddUser() {
		when(userRepository.findByUserEmailIdOrUserName(Mockito.anyString(),Mockito.anyString())).thenReturn(new ArrayList<User>());
		when(userRepository.save(Mockito.any())).thenReturn(getUser());
		
		CreateUser createUser = getCreateUser();
		User user = userService.addUser(createUser);
		
		assertEquals(user.getUserEmailId(), createUser.getUserEmailId());
		assertEquals(user.getUserName(), createUser.getUserName());
		assertEquals(user.getUserPassword(), createUser.getUserPassword());
	}
	
	
	@Test
	@DisplayName("test add new user: Bad request(email or username already exist)")
	void testAddUserBadRequest() {
		when(userRepository.findByUserEmailIdOrUserName(Mockito.any(),Mockito.any())).thenReturn(getUsers());
		
		CreateUser createUser = getCreateUser();
		
		assertThrows(BadRequestException.class,() -> userService.addUser(createUser));
	}
	
	@Test
	@DisplayName("test verify user")
	void testVerifyUser() {
		when(userRepository.findByUserNameAndUserPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(getUser()));
		
		User user = userService.verifyUser("john","pwd");
		
		assertEquals(user.getUserName(), "john");
		assertEquals(user.getUserPassword(), "pwd");
	}
	
	
	@Test
	@DisplayName("test verify user: ResourceNotFound Exception(Invalid credential)")
	void testVerifyUserResourceNotFoundException() {
		when(userRepository.findByUserNameAndUserPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class,() -> userService.verifyUser("john","pwd"));
	}
	
	@Test
	@DisplayName("test get username")
	void testGetUserName() {
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(getUser()));
		
		String userName = userService.getUserName(getUser());
		
		assertEquals(userName, "john");
	}
	
	
	@Test
	@DisplayName("test get username: ResourceNotFound Exception(user not found)")
	void testGetUserNameResourceNotFoundException() {
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class,() -> userService.getUserName(getUser()));
	}
	
	
	// mocked data
	CreateUser getCreateUser() {
		return new CreateUser("john","abc@gmail.com","pwd");	
	}
	
	User getUser() {
		return new User(1,"john","abc@gmail.com","pwd");
	}
	
	List<User> getUsers(){
		List<User> users = new ArrayList<User>();
		
		User user1 = new User(1,"smith","abc@gmail.com","pass");
		User user2 = new User(1,"john","bcd@gmail.com","password");
		
		users.add(user1);
		users.add(user2);
		
		return users;
	}

}
