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
		
	}
	
	@SuppressWarnings("unchecked")
	public void billProductCal(ActionEvent event) {
		ServiceResult result1 = ViewBillProductDto.getViewSalaryCalDto().getProfit(datePickStart.getValue().atStartOfDay(), datePickEnd.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
		if(result1.getStatus() == Status.SUCCESS) {
			try {
				List<BillProductDto> billProductDtos = (List<BillProductDto>) result1.getData();
				int totalProfit = 0;
				for(BillProductDto b : billProductDtos) {
					totalProfit += b.getCount();
				}
				if(datePickStart.getValue().until(datePickEnd.getValue(), ChronoUnit.DAYS) >= 60) {
					totalProfitLabel.setText("Do số lượng thống kê quá nhiều nên hệ thống sẽ không hiển thị trên biểu đồ. Tổng doanh thu: " + totalProfit);
					yAxistBillProductLine.setUpperBound(500000);
					yAxistBillProductLine.setTickUnit(50000);
				}else {
					
					totalProfitLabel.setText("Tong doanh thu: " + totalProfit);
					Series<String, Number> series = new Series<>();
					billProductLineChart.setData(FXCollections.observableArrayList(series));
					int max = -1, i = 0, j = 0;
					LocalDate st = datePickStart.getValue(), en = datePickEnd.getValue();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYY");
					while(!st.plusDays(i).isAfter(en)) {
						Data<String, Number> data = new Data<>(st.plusDays(i).format(formatter), 0);
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
						i++;
					}
					
					for(i = 0; i < series.getData().size(); i++) {
						if(j < billProductDtos.size() && st.plusDays(i).format(formatter).equals(billProductDtos.get(j).getName())){
							Data<String, Number> data = new Data<>(billProductDtos.get(j).getName(), billProductDtos.get(j).getCount());
							series.getData().set(i, data);
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
							if(max < billProductDtos.get(j).getCount()) max = billProductDtos.get(j).getCount();
							j++;		
						}
						if(j >= billProductDtos.size()) break;
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
		
		ServiceResult result = ViewBillProductDto.getViewSalaryCalDto().getProductCount(datePickStart.getValue().atStartOfDay(), datePickEnd.getValue().atStartOfDay().withHour(23).withMinute(59).withSecond(59));
		if(result.getStatus() == Status.SUCCESS) {
			try {
				int max = -1;
				XYChart.Series<String, Number> series= new  XYChart.Series<String, Number>();
				series.setName("Biểu đồ số lượng sản phẩm bán được kể từ: " + datePickStart.getValue().toString() + " tới ngày " + datePickEnd.getValue().toString());
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
}
