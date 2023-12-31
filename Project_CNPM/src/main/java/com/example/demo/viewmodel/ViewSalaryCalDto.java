package com.example.demo.viewmodel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.ProjectCnpmApplication;
import com.example.demo.entities.dto.BillProductDto;
import com.example.demo.entities.dto.SalaryCalDto;
import com.example.demo.entities.models.IBillProduct;
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
	public ServiceResult getAllSalaryCal(LocalDate dateStart, LocalDate dateEnd) {
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
	public ServiceResult getSalaryBySearch(String stringSearch, LocalDate dateStart, LocalDate dateEnd) {
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
	
	@SuppressWarnings("unchecked")
	public ServiceResult getSalaryPerDay(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result =  salaryCalService.getSalaryPerDay(dateStart, dateEnd);
		if(result.getStatus() == Status.SUCCESS) {
			List<IBillProduct> iBillProducts = (List<IBillProduct>) result.getData();
			List<BillProductDto> billProductDtos = new ArrayList<>();
			for(int i = 0; i < iBillProducts.size(); i++) {
				billProductDtos.add(new BillProductDto(convertDate(iBillProducts.get(i).getName()), iBillProducts.get(i).getCount()));
			}
			result.setData(billProductDtos);
			return result;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceResult getSalaryPerMonth(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result =  salaryCalService.getSalaryPerMonth(dateStart, dateEnd);
		if(result.getStatus() == Status.SUCCESS) {
			List<IBillProduct> iBillProducts = (List<IBillProduct>) result.getData();
			List<BillProductDto> billProductDtos = new ArrayList<>();
			for(int i = 0; i < iBillProducts.size(); i++) {
				billProductDtos.add(new BillProductDto(convertDate(iBillProducts.get(i).getName()), iBillProducts.get(i).getCount()));
			}
			result.setData(billProductDtos);
			return result;
		}
		return result;
	}
	public String convertDate(String date) {
		String[] v = date.split("-");
		String result = v[v.length - 1];
		for(int i = v.length - 2; i >= 0; i--) {
			result += "-" + v[i];
		}
		return result;
	}
}
