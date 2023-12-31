package com.example.demo.entities.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BIllIngredientDto {
	@Id
	private String name;
	private Integer count;
	private String unit;
	private Integer total;
	public BIllIngredientDto() {
		
	}
	public BIllIngredientDto(String name, Integer count, String unit, Integer total) {
		this.name = name;
		this.count = count;
		this.unit = unit;
		this.total = total;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
