package com.example.demo.View.Controllers;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import com.example.demo.modelview.HumanView;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreateHumanController {
	private HumanView humanView = new HumanView();
	
	public void createHuman(ActionEvent event) {
		if(nameHumanTf.getText() == "") {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Tên không được để trống!");
			alert.showAndWait();
		}else {
			if(dobHumanTf.getValue() == null) {
				ServiceResult result = humanView.createHuman(nameHumanTf.getText(), genderHumanTf.getText(), null, addressHumanTf.getText());
				if(result.getStatus() == Status.SUCCESS) {
					closeWdCreateHuman(event);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText(result.getMessage());
					alert.showAndWait();
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText(result.getMessage());
					alert.showAndWait();
				}
			}else {
				ServiceResult result = humanView.createHuman(nameHumanTf.getText(), genderHumanTf.getText(), Date.valueOf(dobHumanTf.getValue()), addressHumanTf.getText());
				if(result.getStatus() == Status.SUCCESS) {
					closeWdCreateHuman(event);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText(result.getMessage());
					alert.showAndWait();
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText(result.getMessage());
					alert.showAndWait();
				}
			}
			
		}
	}
	public void closeWdCreateHuman(ActionEvent event) {
		try {
			
			URL url = new File("src/main/java/com/example/demo/View/Views/Sample.fxml").toURI().toURL();
			Parent parent = FXMLLoader.load(url);
			Node btn = (Node) event.getSource();
			Stage stage = (Stage)(btn.getScene().getWindow());
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@FXML 
	private Button closeWdCreateHumanBtn;
	@FXML
	private TextField nameHumanTf;
	@FXML
	private TextField genderHumanTf;
	@FXML
	private TextField addressHumanTf;
	@FXML
	private DatePicker dobHumanTf;
	@FXML
	private Button createHumanBtn;
	
}
