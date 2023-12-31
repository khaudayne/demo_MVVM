package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.SalaryCalRepository;
import com.example.demo.service.ServiceResult.Status;

@Service
public class SalaryCalService {
	@Autowired
	SalaryCalRepository salaryCalRepo;
	
	public ServiceResult getAllSalaryCal(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(salaryCalRepo.getAllSalaryCal(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setMessage("Lay du lieu that bai");
			result.setStatus(Status.FAILED);
		}
		return result;
	}
	
	public ServiceResult getSalaryBySearch(String stringSearch, LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(salaryCalRepo.getSalaryBySearch(stringSearch, dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setMessage("Lay du lieu that bai");
			result.setStatus(Status.FAILED);
		}
		return result;
	}
	
	public ServiceResult getSalaryPerDay(LocalDate dateStart, LocalDate dateEnd){
		ServiceResult result = new ServiceResult();
		try {
			result.setData(salaryCalRepo.getSalaryPerDay(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setMessage("Lay du lieu that bai");
			result.setStatus(Status.FAILED);
		}
		return result;
	}
	
	public ServiceResult getSalaryPerMonth(LocalDate dateStart, LocalDate dateEnd){
		ServiceResult result = new ServiceResult();
		try {
			result.setData(salaryCalRepo.getSalaryPerMonth(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setMessage("Lay du lieu that bai");
			result.setStatus(Status.FAILED);
		}
		return result;
	}
}
