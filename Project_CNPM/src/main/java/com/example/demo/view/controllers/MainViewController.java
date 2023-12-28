package com.example.demo.view.controllers;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainViewController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private Button openWdSalaryCalViewBtn;
	@FXML
	private Button openWdBillProductCalViewBtn;
	@FXML
	private Scene scene;
	@FXML
	private Stage stage;
	@FXML
	private Parent parent;
	
	public void openWdSalaryCalView(ActionEvent event) {
		try {
			URL url = new File("src/main/java/com/example/demo/view/views/SalaryCalView.fxml").toURI().toURL();
			URL url1 = new File("src/main/java/com/example/demo/style/style.css").toURI().toURL();
			parent = FXMLLoader.load(url);
			Node btn = (Node) event.getSource();
			stage = (Stage)(btn.getScene().getWindow());
			scene = new Scene(parent);
			scene.getStylesheets().add(url1.toExternalForm());
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.show();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void openWdBillProductCalView(ActionEvent event) {
		try {
			URL url = new File("src/main/java/com/example/demo/view/views/BillProductView.fxml").toURI().toURL();
//			URL url1 = new File("src/main/java/com/example/demo/style/style.css").toURI().toURL();
			parent = FXMLLoader.load(url);
			Node btn = (Node) event.getSource();
			stage = (Stage)(btn.getScene().getWindow());
			scene = new Scene(parent);
//			scene.getStylesheets().add(url1.toExternalForm());
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.show();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
}
