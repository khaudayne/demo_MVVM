package com.example.demo.entities.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BillProductDto {
	@Id
	private String name;
	private Integer count;
	public BillProductDto() {
		
	}
	public BillProductDto(String name, Integer count) {
		this.name = name;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}

