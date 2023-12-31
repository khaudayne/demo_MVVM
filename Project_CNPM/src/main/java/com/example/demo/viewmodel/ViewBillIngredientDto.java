package com.example.demo.viewmodel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.ProjectCnpmApplication;
import com.example.demo.entities.dto.BIllIngredientDto;
import com.example.demo.entities.dto.BillProductDto;
import com.example.demo.entities.models.IBIllIngredient;
import com.example.demo.entities.models.IBillProduct;
import com.example.demo.service.BillIngredientService;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;

public class ViewBillIngredientDto {
	private static ViewBillIngredientDto viewBillIngredientDto;
	private BillIngredientService billIngredientService;
	
	private ViewBillIngredientDto() {
		this.billIngredientService = ProjectCnpmApplication.getContext().getBean(BillIngredientService.class);
	}
	
	public static ViewBillIngredientDto getViewBillIngredientDto() {
		if(viewBillIngredientDto == null) {
			viewBillIngredientDto = new ViewBillIngredientDto();
		}
		return viewBillIngredientDto;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceResult getIngredientCount(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = billIngredientService.getIngredientCount(dateStart, dateEnd);
		if(result.getStatus() == Status.SUCCESS) {
			List<IBIllIngredient> ibIllIngredients = (List<IBIllIngredient>) result.getData();
			List<BIllIngredientDto> bIllIngredientDtos = new ArrayList<>();
			for(int i = 0; i < ibIllIngredients.size(); i++) {
				bIllIngredientDtos.add(new BIllIngredientDto(ibIllIngredients.get(i).getName(), ibIllIngredients.get(i).getCount(), ibIllIngredients.get(i).getUnit(), ibIllIngredients.get(i).getTotal()));
			}
			result.setData(bIllIngredientDtos);
			return result;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceResult getBIPerDay(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = billIngredientService.getBIPerDay(dateStart, dateEnd);
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
	public ServiceResult getBIPerMonth(LocalDate dateStart, LocalDate dateEnd) {
		ServiceResult result = billIngredientService.getBIPerMonth(dateStart, dateEnd);
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
