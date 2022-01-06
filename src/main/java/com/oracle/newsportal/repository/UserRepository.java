package com.oracle.newsportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.oracle.newsportal.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByUserEmailIdOrUserName(String userEmailId, String userName);
	
	Optional<User> findByUserNameAndUserPassword(String userName, String userPassowrd);

}
