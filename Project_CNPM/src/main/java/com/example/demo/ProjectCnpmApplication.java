package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class ProjectCnpmApplication extends Application{
	private static ConfigurableApplicationContext context;
	@Override
	public void init() throws Exception {
		context = SpringApplication.run(ProjectCnpmApplication.class);
	}
	@Override
	public void stop() throws Exception {
		context.close();
	}
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("view/views/MainView.fxml"));
		Scene scene = new Scene(parent);

		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
	
	public static ConfigurableApplicationContext getContext() {
		return context;
	}
}
