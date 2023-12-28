package com.example.demo.entities.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SalaryCalDto {
	@Id
	private String id;
	private String name;
	private Integer salary;
	public SalaryCalDto() {
		
	}
	
	public SalaryCalDto(String id, String name, Integer salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
}
