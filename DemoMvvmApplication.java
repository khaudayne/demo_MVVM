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
// extends Application để có thể chạy đồng thời giao diện và Spring boot
public class DemoMvvmApplication extends Application{
	// Config context phục vụ cho sử dụng hàm getBean, lấy Bean class để thực hiện hàm mà không phải khởi tạo từ đầu
	private static ConfigurableApplicationContext context;

	// Hàm init của Application, chạy sau khi hàm start xong
	@Override
	public void init() throws Exception {
		// Khởi chạy Spring boot
		context = SpringApplication.run(DemoMvvmApplication.class);
	}

	// Hàm chạy khi kết thúc giao diện javafx
	@Override
	public void stop() throws Exception {
		// Kết thúc luôn Spring boot
		context.close();
	}

	public static void main(String[] args) {
		// Hàm default của javafx
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("View/Views/Sample.fxml"));
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();	
	}

	// Hàm static để các class khác có thể truy nhập tự do vào context của Spring boot để getBean
	public static ConfigurableApplicationContext getContext() {
		return context;
	}
}
