package com.example.demo.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.SalaryCalRepository;
import com.example.demo.service.ServiceResult.Status;

@Service
public class SalaryCalService {
	@Autowired
	SalaryCalRepository salaryCalRepo;
	
	public ServiceResult getAllSalaryCal(Date dateStart, Date dateEnd) {
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
	
	public ServiceResult getSalaryBySearch(String stringSearch, Date dateStart, Date dateEnd) {
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
}
