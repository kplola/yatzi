package Yatzi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * FXML Controller class --> Controls the menu GUI
 *
 * @author kplola
 */
public class MenuController {
    private TextField[] playerNames; 
    
    @FXML
    private TextField namePlayerOne;
    
    @FXML
    private TextField namePlayerTwo;
    
    @FXML
    private Button playBtn;
    
    @FXML
    private CheckBox gameMode;
    
    @FXML
    public void initialize() {
        playerNames = new TextField[]{ // Defining players
            namePlayerOne,
            namePlayerTwo
        };
        
        playBtn.disableProperty().bind(
                Bindings.isEmpty(namePlayerOne.textProperty())
        );
        
        namePlayerTwo.disableProperty().bind(
                Bindings.isEmpty(namePlayerOne.textProperty())
        );
        
    }  
    
  /*  private boolean enableGameMode() { // checking the game mode
        return gameMode.isSelected();
    } */
    
    private List<Player> setupPlayers() { // Setup players at the beginning of the game from class Player 
        List<Player> players = new ArrayList<>();
        
        for(TextField nameField : playerNames) { 
            if(!nameField.isDisabled() && !nameField.getText().isEmpty()) {
                players.add(new Player(nameField.getText()));
            }
                    
        }
        return players;
    }
    
    public void startGame(Event event) throws IOException {
        List<Player> yatziPlayers = setupPlayers();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game.fxml"));
        
        Parent parent = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();
        gameController.setPlayers(yatziPlayers);  // Getting the entry players to GameController players list
       // gameController.setGameMode(enableGameMode());
       // gameController.setupBoard();
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
