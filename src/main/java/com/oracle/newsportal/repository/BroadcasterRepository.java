package com.oracle.newsportal.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.newsportal.models.Broadcaster;

@Repository
public interface BroadcasterRepository extends JpaRepository<Broadcaster, Long>{

	Optional<Broadcaster> findByBroadcasterName(String broadcasterName);

	List<Broadcaster> findAllByOrderByBroadcasterIdDesc();

	Broadcaster findByBroadcasterType(int fieldType);


}


