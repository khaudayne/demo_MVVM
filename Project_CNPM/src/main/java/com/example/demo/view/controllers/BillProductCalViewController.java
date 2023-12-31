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
import com.example.demo.viewmodel.ViewBillProductDto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BillProductCalViewController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		billProductBarChart.setAnimated(false);
		yAxistBillProduct.setAutoRanging(false);
		yAxistBillProduct.setMinorTickCount(1);
		
		
		datePickEnd.setValue(LocalDate.now());
		datePickStart.setValue(LocalDate.now());
		
		billProductLineChart.setAnimated(false);
		yAxistBillProductLine.setAutoRanging(false);
		yAxistBillProductLine.setMinorTickCount(1);
		
		typeCbb.getItems().addAll("Theo ngày", "Theo tháng");
		typeCbb.setValue("Theo ngày");
		
	}
	
	@SuppressWarnings("unchecked")
	public void billProductCal(ActionEvent event) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM-yyyy");
		if(typeCbb.getValue().equals("Theo ngày")) {
			ServiceResult result1 = ViewBillProductDto.getViewBillProductDto().getProfitPerDay(datePickStart.getValue().atStartOfDay(), datePickEnd.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
			if(result1.getStatus() == Status.SUCCESS) {
				try {
					xAxistBillProductLine.setLabel("Ngày");
					List<BillProductDto> billProductDtos = (List<BillProductDto>) result1.getData();
					int totalProfit = 0; 
					for(BillProductDto b : billProductDtos) {
						totalProfit += b.getCount();
					}
					if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.DAYS) >= 50) {
						totalProfitLabel.setText("Do số lượng thống kê quá nhiều nên hệ thống sẽ không hiển thị trên biểu đồ. Tổng doanh thu: " + totalProfit);
						yAxistBillProductLine.setUpperBound(500000);
						yAxistBillProductLine.setTickUnit(50000);
						billProductLineChart.getData().clear();
					}else {
						
						totalProfitLabel.setText("Tổng doanh thu: " + totalProfit);
						Series<String, Number> series = new Series<>();
						
						billProductLineChart.setData(FXCollections.observableArrayList(series));
						LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
						int max = -1, i = 0, j = 0, sz = (int) st.until(en, ChronoUnit.DAYS);
						
						series.setName("Thống kê doanh thu từ ngày " + st.format(formatter) + " tới ngày " + en.format(formatter));
						
						
						for(i = 0; i <= sz; i++) {
							int total = 0;
							if(j < billProductDtos.size() && st.plusDays(i).format(formatter).equals(billProductDtos.get(j).getName())){
								total += billProductDtos.get(j).getCount();
								j++;		
							}
							Data<String, Number> data = new Data<>(st.plusDays(i).format(formatter), total);
							series.getData().add(data);
							data.getNode().setOnMouseEntered(e -> {
								dateInforLabel.setText("Date: " + data.getXValue());
								profitInforLabel.setText("Profit: " + data.getYValue());
								inforVbox.setVisible(true);
								inforVbox.setLayoutX(e.getSceneX() - 5 - 169);
								inforVbox.setLayoutY(e.getSceneY() - 10 - 45);
							});
							data.getNode().setOnMouseExited(e -> {
								inforVbox.setVisible(false);
							});
							if(max < total) max = total;
						}
						if(max != -1) {
							max = max + max / 10;
							yAxistBillProductLine.setUpperBound(max);
							yAxistBillProductLine.setTickUnit(max / 11);
						}else {
							yAxistBillProductLine.setUpperBound(500000);
							yAxistBillProductLine.setTickUnit(50000);
						}
						
					}
				} catch (Exception e) {
					System.err.println(e);
				}
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
				alert.showAndWait();
				totalProfitLabel.setVisible(false);
				swapChartBtn.setVisible(false);	
				billProductBarChart.setVisible(false);
				billProductLineChart.setVisible(false);
				return;
			}
		}else {
			ServiceResult result = ViewBillProductDto.getViewBillProductDto().getProfitPerMonth(datePickStart.getValue().atStartOfDay(), datePickEnd.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
			if(result.getStatus() == Status.SUCCESS) {
				try {
					xAxistBillProductLine.setLabel("Tháng");
					List<BillProductDto> billProductDtos = (List<BillProductDto>) result.getData();
					int totalProfit = 0;
					for(BillProductDto b : billProductDtos) totalProfit += b.getCount();
					if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.MONTHS) >= 50) {
						totalProfitLabel.setText("Do số lượng thống kê quá nhiều nên hệ thống sẽ không hiển thị trên biểu đồ. Tổng doanh thu: " + totalProfit);
						yAxistBillProductLine.setUpperBound(500000);
						yAxistBillProductLine.setTickUnit(50000);
						billProductLineChart.getData().clear();
					}else {
						totalProfitLabel.setText("Tổng doanh thu: " + totalProfit);
						
						Series<String, Number> series = new Series<>();
						billProductLineChart.setData(FXCollections.observableArrayList(series));
						
						LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
						int max = -1, j = 0, difMonth = (en.getYear() - st.getYear()) * 12 + en.getMonthValue() - st.getMonthValue();
						series.setName("Thống kê doanh thu từ tháng " + st.format(formatter1) + " tới tháng " + en.format(formatter1));
						for(int i = 0; i <= difMonth; i++) {
							int total = 0;
							if(j < billProductDtos.size() && st.plusMonths(i).format(formatter1).equals(billProductDtos.get(j).getName())) {
								total += billProductDtos.get(j).getCount();
								j++;
							}
							Data<String, Number> data = new Data<>(st.plusMonths(i).format(formatter1), total);
							series.getData().add(data);
							data.getNode().setOnMouseEntered(e -> {
								dateInforLabel.setText("Date: " + data.getXValue());
								profitInforLabel.setText("Profit: " + data.getYValue());
								inforVbox.setVisible(true);
								inforVbox.setLayoutX(e.getSceneX() - 5 - 169);
								inforVbox.setLayoutY(e.getSceneY() - 10 - 45);
							});
							data.getNode().setOnMouseExited(e -> {
								inforVbox.setVisible(false);
							});
							if(max < total) max = total;
						}
						if(max != -1) {
							max = max + max / 10;
							yAxistBillProductLine.setUpperBound(max);
							yAxistBillProductLine.setTickUnit(max / 11);
						}else {
							yAxistBillProductLine.setUpperBound(500000);
							yAxistBillProductLine.setTickUnit(50000);
						}
						
					}
				} catch (Exception e) {
					System.err.println(e);
				}
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
				alert.showAndWait();
				totalProfitLabel.setVisible(false);
				swapChartBtn.setVisible(false);	
				billProductBarChart.setVisible(false);
				billProductLineChart.setVisible(false);
				return;
			}
			
		}
		
		
		ServiceResult result = ViewBillProductDto.getViewBillProductDto().getProductCount(datePickStart.getValue().atStartOfDay(), datePickEnd.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
		if(result.getStatus() == Status.SUCCESS) {
			try {
				int max = -1;
				XYChart.Series<String, Number> series= new  XYChart.Series<String, Number>();
				series.setName("Biểu đồ số lượng sản phẩm bán được kể từ: " + datePickStart.getValue().format(formatter) + " tới ngày " + datePickEnd.getValue().format(formatter));
				List<BillProductDto> billProductDtos = (List<BillProductDto>) result.getData();
				for(BillProductDto b : billProductDtos) {
					series.getData().add(new  XYChart.Data<String, Number>(b.getName(), b.getCount()));
					if(max < b.getCount()) max = b.getCount();
				}
				if(max != -1) {
					yAxistBillProduct.setUpperBound(max + 1);	
					if(max + 1 < 10) {
						yAxistBillProduct.setTickUnit(1);
					}else {
						yAxistBillProduct.setTickUnit((max + 1) / 10);
					}	
				}else {
					yAxistBillProduct.setUpperBound(50);
					yAxistBillProduct.setTickUnit(5);
				}
				
				billProductBarChart.setData(FXCollections.observableArrayList(series));
					
			} catch (Exception e) {
				System.err.println(e);
			}	
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Đã có lỗi xảy ra với máy chủ, vui lòng thử lại sau!");
			alert.showAndWait();
			totalProfitLabel.setVisible(false);
			swapChartBtn.setVisible(false);	
			billProductBarChart.setVisible(false);
			billProductLineChart.setVisible(false);
			return;
		}
		totalProfitLabel.setVisible(true);
		swapChartBtn.setVisible(true);	
		billProductBarChart.setVisible(false);
		billProductLineChart.setVisible(true);
		swapChartBtn.setText("Xem thống kê");
	}
	
	public void returnMainWd(ActionEvent event) {
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
	
	public void swapChart(ActionEvent event) {
		if(swapChartBtn.getText().equals("Xem thống kê")) {
			billProductLineChart.setVisible(false);
			billProductBarChart.setVisible(true);
			swapChartBtn.setText("Xem doanh thu");
		}else {
			billProductBarChart.setVisible(false);
			billProductLineChart.setVisible(true);
			swapChartBtn.setText("Xem thống kê");
		}
	}
	
	@FXML
	private AnchorPane parentRoot;
	@FXML
	private DatePicker datePickStart;
	@FXML
	private DatePicker datePickEnd;
	@FXML
	private Button billProductCalBtn;
	@FXML
	private BarChart<String, Number> billProductBarChart;
	@FXML
	private CategoryAxis xAxistBillProduct;
	@FXML
	private NumberAxis yAxistBillProduct;
	@FXML
	private LineChart<String, Number> billProductLineChart;
	@FXML
	private Label totalProfitLabel;
	@FXML
	private CategoryAxis xAxistBillProductLine;
	@FXML
	private NumberAxis yAxistBillProductLine;
	@FXML
	private Button returnMainWdBtn;
	@FXML
	private Button swapChartBtn;
	@FXML
	private VBox inforVbox;
	@FXML
	private Label dateInforLabel;
	@FXML
	private Label profitInforLabel;
	@FXML
	private ComboBox<String> typeCbb;
}
