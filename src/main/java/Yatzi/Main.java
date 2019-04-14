/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author kplola
 * 
 * Entry point of the application
 */
public class Main extends Application {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
     
            Parent first = FXMLLoader.load(getClass().getResource("menu.fxml"));
            
            Scene scene = new Scene(first);
            
            primaryStage.getIcons().add(new Image("images/logo.png"));
            primaryStage.setTitle("Yatzi");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
    }
    
}
