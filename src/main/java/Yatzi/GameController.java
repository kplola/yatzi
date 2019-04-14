/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author kplola
 */
public class GameController  {

    private Player player;
    private List<Player> players = new ArrayList<>();
     
    
    // Setup the GAME PLayers
    public void setPlayers(List<Player> Myplayers) {
        this.players = Myplayers;
    } 
    
    // Setup the Game board
}
