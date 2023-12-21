package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Human;
@Repository
// Tự tìm hiểu về Repository đê
public interface HumanRepository extends JpaRepository<Human, Integer>{
	@Query(value = "select * from human", nativeQuery = true)
	List<Human> findAll();
}
