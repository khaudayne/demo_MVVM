package com.example.demo.modelview;
import java.sql.Date;
import com.example.demo.DemoMvvmApplication;
import com.example.demo.entities.Human;
import com.example.demo.service.HumanService;
import com.example.demo.service.ServiceResult;
// Đây là lớp ViewModel trong kiến trúc MVVM
public class HumanView {
	// Bean HumanService khởi tạo 1 lần duy nhất để sử dụng các hàm lấy service result chứa data mang lên cho tầng View
	private static HumanService humanService;
	
	public HumanView() {
		if(humanService == null) {
			humanService = DemoMvvmApplication.getContext().getBean(HumanService.class);
		}
	}

	// Các hàm bên dưới là các hàm lấy data mang cho tầng View
	public ServiceResult getHumans(){
		ServiceResult result = new ServiceResult();
		result = humanService.findAllHumans();
		return result;
	}

	
	public ServiceResult createHuman(String name, String gender, Date dob, String diachi) {
		ServiceResult result = new ServiceResult();
		result = humanService.createHuman(new Human(name, gender, dob, diachi));
		return result;
	}
}
