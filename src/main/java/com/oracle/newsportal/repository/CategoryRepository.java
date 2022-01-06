package com.oracle.newsportal.repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.newsportal.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	Optional<Category> findByCategoryName(String categoryName);

	List<Category> findAllByOrderByCategoryIdDesc();

	Category findByCategoryType(int fieldType);


}

