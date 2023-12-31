package com.example.demo.view.controllers;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

import com.example.demo.entities.dto.BillProductDto;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;
import com.example.demo.viewmodel.ViewBillIngredientDto;
import com.example.demo.viewmodel.ViewBillProductDto;
import com.example.demo.viewmodel.ViewSalaryCalDto;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ProfitCalViewController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		datePickStart.setValue(LocalDate.now());
		datePickEnd.setValue(LocalDate.now());
		profitLinechart.setAnimated(false);
		yAxisProfitLine.setAutoRanging(false);
		yAxisProfitLine.setMinorTickCount(1);
		
		typeCbb.getItems().addAll("Theo ngày", "Theo tháng");
		typeCbb.setValue("Theo ngày");
	}
	
	@SuppressWarnings("unchecked")
	public void profitCal(ActionEvent event) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM-yyyy");
		if(typeCbb.getValue().equals("Theo ngày")) {
			ServiceResult result1 = ViewBillProductDto.getViewBillProductDto().getProfitPerDay(datePickStart.getValue().atStartOfDay(), datePickEnd.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
			ServiceResult result2 = ViewSalaryCalDto.getViewSalaryCalDto().getSalaryPerDay(datePickStart.getValue(), datePickEnd.getValue());
			ServiceResult result3 = ViewBillIngredientDto.getViewBillIngredientDto().getBIPerDay(datePickStart.getValue(), datePickEnd.getValue());
			if(result1.getStatus() == Status.SUCCESS && result2.getStatus() == Status.SUCCESS && result3.getStatus() == Status.SUCCESS) {
				try {
					List<BillProductDto> products = (List<BillProductDto>) result1.getData();
					List<BillProductDto> salarys = (List<BillProductDto>) result2.getData();
					List<BillProductDto> ingredients = (List<BillProductDto>) result3.getData();
					int totalProfit = 0;
					for(BillProductDto b : products) {
						totalProfit += b.getCount();
					}
					for(BillProductDto b : salarys) {
						totalProfit -= b.getCount();
					}
					for(BillProductDto b : ingredients) {
						totalProfit -= b.getCount();
					}
					if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.DAYS) >= 50) {
						totalProfitLabel.setText("Do số lượng thống kê quá nhiều nên hệ thống sẽ không hiển thị trên biểu đồ. Tổng lợi nhuận: " + totalProfit);
						yAxisProfitLine.setUpperBound(500000);
						yAxisProfitLine.setTickUnit(50000);
						profitLinechart.getData().clear();
						
					}else {
						totalProfitLabel.setText("Tổng doanh thu: " + totalProfit);
						Series<String, Number> series = new Series<>();
						profitLinechart.setData(FXCollections.observableArrayList(series));
						int i = 0, j1 = 0, j2 = 0, j3 = 0, sz = (int) datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.DAYS), min = 0, max = 0;
						LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
						series.setName("Thống kê doanh thu từ ngày " + st.format(formatter) + " tới ngày " + en.format(formatter));
						
						for(i = 0; i <= sz; i++) {
							int total = 0;
							if(j1 < products.size() && st.plusDays(i).format(formatter).equals(products.get(j1).getName())) {
								total += products.get(j1).getCount();
								j1++;
							}
							if(j2 < salarys.size() && st.plusDays(i).format(formatter).equals(salarys.get(j2).getName())) {
								total -= salarys.get(j2).getCount();
								j2++;
							}
							if(j3 < ingredients.size() && st.plusDays(i).format(formatter).equals(ingredients.get(j3).getName())) {
								total -= ingredients.get(j3).getCount();
								j3++;
							}
							if(min > total) min = total;
							if(max < total) max = total;
							Data<String, Number> data = new Data<>(st.plusDays(i).format(formatter), total);
							series.getData().add(data);
						}
						if(max == 0 && min == 0) {
							yAxisProfitLine.setUpperBound(250000);
							yAxisProfitLine.setLowerBound(-250000);
							yAxisProfitLine.setTickUnit(50000);
						}else {
							yAxisProfitLine.setUpperBound(max);
							yAxisProfitLine.setLowerBound(min);
							int diff = (max - min) / 10;
							yAxisProfitLine.setTickUnit(diff);
						}
					}
				} catch (Exception e) {
					System.err.println(e);
				}			
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
				alert.showAndWait();
				profitLinechart.setVisible(false);
				totalProfitLabel.setVisible(false);
				return;
			}
		}else {
			ServiceResult result1 = ViewBillProductDto.getViewBillProductDto().getProfitPerMonth(datePickStart.getValue().atStartOfDay(), datePickEnd.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
			ServiceResult result2 = ViewSalaryCalDto.getViewSalaryCalDto().getSalaryPerMonth(datePickStart.getValue(), datePickEnd.getValue());
			ServiceResult result3 = ViewBillIngredientDto.getViewBillIngredientDto().getBIPerMonth(datePickStart.getValue(), datePickEnd.getValue());
			if(result1.getStatus() == Status.SUCCESS && result2.getStatus() == Status.SUCCESS && result3.getStatus() == Status.SUCCESS) {
				try {
					List<BillProductDto> products = (List<BillProductDto>) result1.getData();
					List<BillProductDto> salarys = (List<BillProductDto>) result2.getData();
					List<BillProductDto> ingredients = (List<BillProductDto>) result3.getData();
					int totalProfit = 0;
					for(BillProductDto b : products) {
						totalProfit += b.getCount();
					}
					for(BillProductDto b : salarys) {
						totalProfit -= b.getCount();
					}
					for(BillProductDto b : ingredients) {
						totalProfit -= b.getCount();
					}
					if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.MONTHS) >= 50) {
						totalProfitLabel.setText("Do số lượng thống kê quá nhiều nên hệ thống sẽ không hiển thị trên biểu đồ. Tổng lợi nhuận: " + totalProfit);
						yAxisProfitLine.setUpperBound(500000);
						yAxisProfitLine.setTickUnit(50000);
						profitLinechart.getData().clear();
						
					}else {
						totalProfitLabel.setText("Tổng doanh thu: " + totalProfit);
						Series<String, Number> series = new Series<>();
						profitLinechart.setData(FXCollections.observableArrayList(series));
						LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
						int i = 0, j1 = 0, j2 = 0, j3 = 0, sz = (en.getYear() - st.getYear()) * 12 + en.getMonthValue() - st.getMonthValue() , min = 0, max = 0;
						series.setName("Thống kê doanh thu từ tháng " + st.format(formatter1) + " tới tháng " + en.format(formatter1));
						
						for(i = 0; i <= sz; i++) {
							int total = 0;
							if(j1 < products.size() && st.plusMonths(i).format(formatter1).equals(products.get(j1).getName())) {
								total += products.get(j1).getCount();
								j1++;
							}
							if(j2 < salarys.size() && st.plusMonths(i).format(formatter1).equals(salarys.get(j2).getName())) {
								total -= salarys.get(j2).getCount();
								j2++;
							}
							if(j3 < ingredients.size() && st.plusMonths(i).format(formatter1).equals(ingredients.get(j3).getName())) {
								total -= ingredients.get(j3).getCount();
								j3++;
							}
							if(min > total) min = total;
							if(max < total) max = total;
							Data<String, Number> data = new Data<>(st.plusMonths(i).format(formatter1), total);
							series.getData().add(data);
						}
						if(max == 0 && min == 0) {
							yAxisProfitLine.setUpperBound(250000);
							yAxisProfitLine.setLowerBound(-250000);
							yAxisProfitLine.setTickUnit(50000);
						}else {
							yAxisProfitLine.setUpperBound(max);
							yAxisProfitLine.setLowerBound(min);
							int diff = (max - min) / 10;
							yAxisProfitLine.setTickUnit(diff);
						}
					}
				} catch (Exception e) {
					System.err.println(e);
				}			
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
				alert.showAndWait();
				profitLinechart.setVisible(false);
				totalProfitLabel.setVisible(false);
				return;
			}
		}
		
		totalProfitLabel.setVisible(true);
		profitLinechart.setVisible(true);
	}
	
	public void returnMainView(ActionEvent event) {
		try {
			URL url = new File("src/main/java/com/example/demo/view/views/MainView.fxml").toURI().toURL();
			Parent parent = FXMLLoader.load(url);
			Node btn = (Node) event.getSource();
			Stage stage = (Stage)(btn.getScene().getWindow());
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.show();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@FXML
	private Button returnMainViewBtn;
	@FXML
	private DatePicker datePickStart;
	@FXML
	private DatePicker datePickEnd;
	@FXML
	private Button profitCalBtn;
	@FXML
	private LineChart<String, Number> profitLinechart;
	@FXML
	private CategoryAxis xAxisProfitLine;
	@FXML
	private NumberAxis yAxisProfitLine;
	@FXML
	private ComboBox<String> typeCbb;
	@FXML
	private Label totalProfitLabel;

}
