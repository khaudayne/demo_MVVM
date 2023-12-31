package com.example.demo;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.models.ISalaryCal;
import com.example.demo.service.SalaryCalService;
import com.example.demo.service.ServiceResult;

@SpringBootTest
class ProjectCnpmApplicationTests {
	@Autowired
	SalaryCalService salaryCalService;
	@Test
	void contextLoads() {
		try {
			ServiceResult result = salaryCalService.getAllSalaryCal(LocalDate.of(2023, 12, 20), LocalDate.of(2023, 12, 20));
			@SuppressWarnings("unchecked")
			List<ISalaryCal> iSalaryCals = (List<ISalaryCal>) result.getData();
			for(int i = 0; i < iSalaryCals.size(); i++) {
				System.out.println(iSalaryCals.get(i).getSalary());
			}
			System.out.println(result.getMessage());
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
