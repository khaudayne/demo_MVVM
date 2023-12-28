package com.example.demo.viewmodel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.ProjectCnpmApplication;
import com.example.demo.entities.dto.BillProductDto;
import com.example.demo.entities.models.IBillProduct;
import com.example.demo.service.BillProductService;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;

public class ViewBillProductDto {
	private static ViewBillProductDto viewBillProductDto;
	private BillProductService billProductService;
	
	private ViewBillProductDto() {
		this.billProductService = ProjectCnpmApplication.getContext().getBean(BillProductService.class);
	}
	
	public static ViewBillProductDto getViewSalaryCalDto() {
		if(viewBillProductDto == null) {
			viewBillProductDto = new ViewBillProductDto();
		}
		return viewBillProductDto;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceResult getProductCount(LocalDateTime dateStart, LocalDateTime dateEnd) {
		ServiceResult result = billProductService.getProductCount(dateStart, dateEnd);
		if(result.getStatus() == Status.SUCCESS) {
			List<IBillProduct> iBillProducts = (List<IBillProduct>) result.getData();
			List<BillProductDto> billProductDtos = new ArrayList<BillProductDto>();
			for(int i = 0; i < iBillProducts.size(); i++) {
				billProductDtos.add(new BillProductDto(iBillProducts.get(i).getName(), iBillProducts.get(i).getCount()));
			}
			result.setData(billProductDtos);
			return result;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceResult getProfit(LocalDateTime dateStart, LocalDateTime dateEnd) {
		ServiceResult result = billProductService.getProfit(dateStart, dateEnd);
		if(result.getStatus() == Status.SUCCESS) {
			List<IBillProduct> iBillProducts = (List<IBillProduct>) result.getData();
			List<BillProductDto> billProductDtos = new ArrayList<BillProductDto>();
			for(int i = 0; i < iBillProducts.size(); i++) {
				billProductDtos.add(new BillProductDto(iBillProducts.get(i).getName(), iBillProducts.get(i).getCount()));
			}
			result.setData(billProductDtos);
			return result;
		}
		return result;
	}
}
