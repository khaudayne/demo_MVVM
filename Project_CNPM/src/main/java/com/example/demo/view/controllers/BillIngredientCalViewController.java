package com.example.demo.view.controllers;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.example.demo.entities.dto.BIllIngredientDto;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;
import com.example.demo.viewmodel.ViewBillIngredientDto;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BillIngredientCalViewController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		datePickStart.setValue(LocalDate.now());
		datePickEnd.setValue(LocalDate.now());
		nameIngeredientColumn.setCellValueFactory(new PropertyValueFactory<BIllIngredientDto, String>("name"));
		countIngeredientColumn.setCellValueFactory(new PropertyValueFactory<BIllIngredientDto, Integer>("count"));
		unitIngeredientColumn.setCellValueFactory(new PropertyValueFactory<BIllIngredientDto, String>("unit"));
		totalIngeredientColumn.setCellValueFactory(new PropertyValueFactory<BIllIngredientDto, Integer>("total"));
	}
	
	@SuppressWarnings("unchecked")
	public void billIngredientCal(ActionEvent event) {
		ServiceResult result = ViewBillIngredientDto.getViewBillIngredientDto().getIngredientCount(datePickStart.getValue(), datePickEnd.getValue());
		if(result.getStatus() == Status.SUCCESS) {
			ScrollBar vScrollBar = (ScrollBar) tableIngredient.lookup(".scroll-bar:vertical");
			vScrollBar.setValue(vScrollBar.getMin());
			List<BIllIngredientDto> bIllIngredientDtos = (List<BIllIngredientDto>)result.getData();
			if(bIllIngredientDtos == null || bIllIngredientDtos.size() == 0) {
				totalBillIngredientLabel.setText("Tổng tiền mua nguyên liệu: 0");
				tableIngredient.setVisible(false);
			}
			tableIngredient.setItems(FXCollections.observableArrayList(bIllIngredientDtos));
			tableIngredient.setVisible(true);
			int totalBillIngredient = 0;
			for(BIllIngredientDto b : bIllIngredientDtos) {
				totalBillIngredient += b.getTotal();
			}
			totalBillIngredientLabel.setText("Tổng tiền mua nguyên liệu: " + totalBillIngredient);
			totalBillIngredientLabel.setVisible(true);
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(result.getMessage());
			alert.showAndWait();
		}
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
	private DatePicker datePickStart;
	@FXML
	private DatePicker datePickEnd;
	@FXML
	private Button billIngredientCalBtn;
	@FXML
	private Button returnMainViewBtn;
	@FXML
	private Label totalBillIngredientLabel;
	@FXML
	private TableView<BIllIngredientDto> tableIngredient;
	@FXML
	private TableColumn<BIllIngredientDto, String> nameIngeredientColumn;
	@FXML
	private TableColumn<BIllIngredientDto, Integer> countIngeredientColumn;
	@FXML
	private TableColumn<BIllIngredientDto, String> unitIngeredientColumn;
	@FXML
	private TableColumn<BIllIngredientDto, Integer> totalIngeredientColumn;
}
