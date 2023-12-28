package com.example.demo.viewmodel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.ProjectCnpmApplication;
import com.example.demo.entities.dto.SalaryCalDto;
import com.example.demo.entities.models.ISalaryCal;
import com.example.demo.service.SalaryCalService;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;

public class ViewSalaryCalDto {
	private static ViewSalaryCalDto viewSalaryCalDto;
	private SalaryCalService salaryCalService;
	
	private ViewSalaryCalDto() {
		this.salaryCalService = ProjectCnpmApplication.getContext().getBean(SalaryCalService.class);
	}
	
	public static ViewSalaryCalDto getViewSalaryCalDto() {
		if(viewSalaryCalDto == null) {
			viewSalaryCalDto = new ViewSalaryCalDto();
		}
		return viewSalaryCalDto;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceResult getAllSalaryCal(Date dateStart, Date dateEnd) {
		ServiceResult result =  salaryCalService.getAllSalaryCal(dateStart, dateEnd);
		if(result.getStatus() == Status.SUCCESS) {
			List<ISalaryCal> iSalaryCals = (List<ISalaryCal>) result.getData();
			List<SalaryCalDto> salaryCalDtos = new ArrayList<SalaryCalDto>();
			for(int i = 0; i < iSalaryCals.size(); i++) {
				salaryCalDtos.add(new SalaryCalDto(iSalaryCals.get(i).getId(), iSalaryCals.get(i).getName(), iSalaryCals.get(i).getSalary()));
			}
			result.setData(salaryCalDtos);
			return result;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceResult getSalaryBySearch(String stringSearch, Date dateStart, Date dateEnd) {
		ServiceResult result =  salaryCalService.getSalaryBySearch(stringSearch, dateStart, dateEnd);
		if(result.getStatus() == Status.SUCCESS) {
			List<ISalaryCal> iSalaryCals = (List<ISalaryCal>) result.getData();
			List<SalaryCalDto> salaryCalDtos = new ArrayList<SalaryCalDto>();
			for(int i = 0; i < iSalaryCals.size(); i++) {
				salaryCalDtos.add(new SalaryCalDto(iSalaryCals.get(i).getId(), iSalaryCals.get(i).getName(), iSalaryCals.get(i).getSalary()));
			}
			result.setData(salaryCalDtos);
			return result;
		}
		return result;
	}
}
