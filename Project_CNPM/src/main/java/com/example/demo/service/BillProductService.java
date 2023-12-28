package com.example.demo.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.BillProductRepository;
import com.example.demo.service.ServiceResult.Status;
@Service
public class BillProductService {
	@Autowired
	BillProductRepository billProductRepo;
	
	public ServiceResult getProductCount(LocalDateTime dateStart, LocalDateTime dateEnd) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(billProductRepo.getProductCount(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setMessage("Lay du lieu that bai");
			result.setStatus(Status.FAILED);
		}
		return result;
	}
	
	public ServiceResult getProfit(LocalDateTime dateStart, LocalDateTime dateEnd) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(billProductRepo.getProfit(dateStart, dateEnd));
			result.setMessage("Lay du lieu thanh cong");
		} catch (Exception e) {
			result.setMessage("Lay du lieu that bai");
			result.setStatus(Status.FAILED);
		}
		return result;
	}
}
