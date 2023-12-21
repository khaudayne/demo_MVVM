package com.example.demo.View.Controllers;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import com.example.demo.entities.Human;
import com.example.demo.modelview.HumanView;
import com.example.demo.service.ServiceResult;
import com.example.demo.service.ServiceResult.Status;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller implements Initializable{
	private HumanView humanView = new HumanView();

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ServiceResult result = humanView.getHumans();
		if (result.getStatus() == Status.SUCCESS) {
			tableHuman.setItems(FXCollections.observableArrayList((List<Human>)result.getData()));
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(result.getMessage());
			alert.showAndWait();
		}
		
		nameHuman.setCellValueFactory(new PropertyValueFactory<Human, String>("name"));
		dobHuman.setCellValueFactory(new PropertyValueFactory<Human, Date>("dob"));
		genderHuman.setCellValueFactory(new PropertyValueFactory<Human, String>("gender"));
		addressHuman.setCellValueFactory(new PropertyValueFactory<Human, String>("diachi"));
	}
	
	public void openCreateWd(ActionEvent event){
		try {		
			URL url = new File("src/main/java/com/example/demo/View/Views/createHuman.fxml").toURI().toURL();
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
	private TableView<Human> tableHuman;
	@FXML 
	private TableColumn<Human, String> nameHuman;
	@FXML
	private TableColumn<Human, Date> dobHuman;
	@FXML
	private TableColumn<Human, String> genderHuman;
	@FXML
	private TableColumn<Human, String> addressHuman;
	@FXML
	private Button createHumanBtn;
}
