package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.BillIngredientRepository;
import com.example.demo.service.ServiceResult.Status;

@Service
public class BillIngredientService {
	@Autowired
	BillIngredientRepository  billIngredientRepo;
	
	public ServiceResult getIngredientCount(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(billIngredientRepo.getIngredientCount(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setStatus(Status.FAILED);
			result.setMessage("Lay du lieu that bai");
		}
		return result;
	}
	
	public ServiceResult getBIPerDay(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(billIngredientRepo.getBIPerDay(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setStatus(Status.FAILED);
			result.setMessage("Lay du lieu that bai");
		}
		return result;
	}
	public ServiceResult getBIPerMonth(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(billIngredientRepo.getBIPerMonth(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setStatus(Status.FAILED);
			result.setMessage("Lay du lieu that bai");
		}
		return result;
	}
}
