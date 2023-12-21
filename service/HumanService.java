package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Human;
import com.example.demo.repository.HumanRepository;
import com.example.demo.service.ServiceResult.Status;

@Service
public class HumanService {
	@Autowired
	private HumanRepository humanRepo;
	
	public ServiceResult findAllHumans() {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(humanRepo.findAll());
			result.setMessage("Lấy dữ liệu thành công!");
		} catch (Exception e) {
			result.setStatus(Status.FAILED);
			result.setMessage("Có lỗi với máy chủ, vui lòng thử lại sau!");
		}
		return result;
	}
	
	public ServiceResult createHuman(Human human) {
		ServiceResult result = new ServiceResult();
		try {
			result.setData(humanRepo.save(human));
			result.setMessage("Thêm mới thành công!");
		} catch (Exception e) {
			result.setStatus(Status.FAILED);
			result.setMessage("Có lỗi với máy chủ, vui lòng thử lại sau!");
		}
		return result;
	}
}
