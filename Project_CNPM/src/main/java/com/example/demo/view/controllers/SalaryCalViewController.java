package com.example.demo.view.controllers;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import java.util.ResourceBundle;
import com.example.demo.entities.dto.SalaryCalDto;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;
import com.example.demo.viewmodel.ViewSalaryCalDto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SalaryCalViewController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		parentRoot.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				tableSalary.getSelectionModel().clearSelection();
			}
		});
		try {
			tableSalary.getStyleClass().add("no-horizontal-scroll");
		} catch (Exception e) {
			System.err.println(e);
		}
		idStaffTf.setFocusTraversable(false);
		idStaffColumn.setCellValueFactory(new PropertyValueFactory<SalaryCalDto, String>("id"));
		nameStaffColumn.setCellValueFactory(new PropertyValueFactory<SalaryCalDto, String>("name"));
		salaryStaffColumn.setCellValueFactory(new PropertyValueFactory<SalaryCalDto, Integer>("salary"));
		datePickStart.setValue(LocalDate.now());
		datePickEnd.setValue(LocalDate.now());
	}
	
	
	@SuppressWarnings("unchecked")
	public void salaryCal(ActionEvent event) {
		ServiceResult result;
		if(idStaffTf.getText() == null || idStaffTf.getText() == "") {
			result = ViewSalaryCalDto.getViewSalaryCalDto().getAllSalaryCal(datePickStart.getValue(), datePickEnd.getValue());
		}else {
			result = ViewSalaryCalDto.getViewSalaryCalDto().getSalaryBySearch(idStaffTf.getText(), datePickStart.getValue(), datePickEnd.getValue());
		}
		
		if(result.getStatus() == Status.SUCCESS) {
			ScrollBar vScrollBar = (ScrollBar) tableSalary.lookup(".scroll-bar:vertical");
			vScrollBar.setValue(vScrollBar.getMin());
			tableSalary.setItems(FXCollections.observableArrayList((List<SalaryCalDto>)result.getData()));
			tableSalary.setVisible(true);
			
			int totalSalary = 0;
			for(SalaryCalDto sCD : tableSalary.getItems()) {
				totalSalary += sCD.getSalary();
			}
			if(idStaffTf.getText() == null || idStaffTf.getText() == "") {
				totalSalaryLabel.setText("Tổng tiền: " + Integer.toString(totalSalary));
				totalSalaryLabel.setVisible(true);
			}else {
				totalSalaryLabel.setVisible(false);
			}
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
	private AnchorPane parentRoot;
	
	@FXML
	private TextField idStaffTf;
	
	@FXML
	private DatePicker datePickStart;
	
	@FXML
	private DatePicker datePickEnd;
	
	@FXML
	private TableView<SalaryCalDto> tableSalary;
	
	@FXML
	private TableColumn<SalaryCalDto, String> idStaffColumn;
	
	@FXML
	private TableColumn<SalaryCalDto, String> nameStaffColumn;
	
	@FXML
	private TableColumn<SalaryCalDto, Integer> salaryStaffColumn;
	
	@FXML
	private Button salaryCalBtn;
	
	@FXML
	private Label totalSalaryLabel;

}
