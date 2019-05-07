/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;


import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author kplola
 */
public class GameController  {
    
    private static int MAX_ROLLS = 3;
    private static int MIN_ROLLS = 0;
    private static int BONUS_POINTS = 50;
    
    private int rollsRemaining = MAX_ROLLS;
    private Category currentCategory = Category.ONES;
    private int currentPlayerIndex = 0;
    
    private Dice dice = new Dice();
    private ImageView[] diceImage = new ImageView[dice.getDice().length];
    private ImageView[] diceSelectedImage = new ImageView[dice.getDice().length];
    private List<Player> players = new ArrayList<>();
    private String[] colors = new String[]{"#FDB462", "#B3DE69", "#FB8072", "#BEBADA", "#FFFFB3", "#8DD3C7"};
    
    
    @FXML
    private GridPane grid;
    
    @FXML
    private ImageView die0;
    
    @FXML
    private ImageView die1;
    
    @FXML
    private ImageView die2;
    
    @FXML
    private ImageView die3;
    
    @FXML
    private ImageView die4;
    
    @FXML
    private ImageView die0Selected;
    
    @FXML
    private ImageView die1Selected;
    
    @FXML
    private ImageView die2Selected;
    
    @FXML
    private ImageView die3Selected;
    
    @FXML
    private ImageView die4Selected;
    
    @FXML
    private Button btnRoll;
    
    @FXML
    private VBox rollingPane;
    
    @FXML
    private AnchorPane scorePane;
 
    
    public void initialize() {
        diceImage[0] = die0;
        diceImage[1] = die1;
        diceImage[2] = die2;
        diceImage[3] = die3;
        diceImage[4] = die4;
        
        diceSelectedImage[0] = die0Selected;
        diceSelectedImage[1] = die1Selected;
        diceSelectedImage[2] = die2Selected;
        diceSelectedImage[3] = die3Selected;
        diceSelectedImage[4] = die4Selected;
       
      
       updateRollButton();
    }
    
    private int needForBonus() {
        int Min = 42;
        int Max = 55;
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }
   
    
    // Setup the Game board
    public void setupBoard() {
        String sumText = String.format("Sum [%d]", needForBonus());
        
        grid.add(new Label("Ones"), 0, 1);
        grid.add(new Label("Twos"), 0, 2);
        grid.add(new Label("Threes"), 0, 3);
        grid.add(new Label("Fours"), 0, 4);
        grid.add(new Label("Fives"), 0, 5);
        grid.add(new Label("Sixes"), 0, 6);
        grid.add(new Label(sumText), 0, 7);
        grid.add(new Label("Bonus"), 0, 8);
        grid.add(new Label("1 Pair"), 0, 9);
        grid.add(new Label("2 Pair"), 0, 10);
        grid.add(new Label("3 of a kind"), 0, 11);
        grid.add(new Label("4 of a kind"), 0, 12);
        grid.add(new Label("Small straight"), 0, 13);
        grid.add(new Label("Large Straight"), 0, 14);
        grid.add(new Label("Full house"), 0, 15);
        grid.add(new Label("Chance"), 0, 16);
        grid.add(new Label("Yatzi"), 0, 17);
        grid.add(new Label("Total"), 0, 18);
        
        for (int column = 1; column < players.size() + 1; column++ ) {
            double width = grid.getColumnConstraints().get(0).getPrefWidth();
            double height = grid.getRowConstraints().get(0).getPrefHeight();
            
            StackPane panePlayer = new StackPane();
            grid.add(panePlayer, column, 0);
            
            Rectangle rec = new Rectangle();
            rec.setWidth(width);
            rec.setHeight(height - 5);
            rec.setOpacity(0);
            rec.setFill(Color.web(colors[column - 1]));
            panePlayer.getChildren().add(rec);
            
            Label labelName = new Label();
            panePlayer.getChildren().add(labelName);
            
            String playerName = players.get(column - 1).getName();
            labelName.setText(playerName);
            
            for (Category category: Category.values()) {
                int row = category.ordinal() + 1;
                
                StackPane paneScore = new StackPane();
                grid.add(paneScore, column, row);
                
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(width);
                rectangle.setHeight(height);
                rectangle.setOpacity(0);
                paneScore.getChildren().add(rectangle);
                
                Label labelScore = new Label();
                paneScore.getChildren().add(labelScore);
                
               
                if (category == Category.SUM || category == Category.BONUS || category == Category.TOTAL) {
                    int score = players.get(column - 1).getScores().getOrDefault(category, 0);
                    labelScore.setTextFill(Color.web("C8584A"));
                    labelScore.setText(Integer.toString(score));
                }
            }
        }
        highlightCurrentPlayer();
    }
    
    // set the turn of the next player
    private void nextPlayer() {
        Player lastPlayer = players.get(players.size() - 1);
        currentPlayerIndex = lastPlayer == currentPlayer() ? 0 : currentPlayerIndex + 1;
        highlightCurrentPlayer();

        if (currentPlayerIndex == 0) { 
            nextCategory();
        }
    }
    
    private Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    /**
     * highlight the name of the current player
     */
    private void highlightCurrentPlayer() {
        for (int i = 0; i < players.size(); i++) {
            int column = playerToColumnIndex(players.get(i));
            int row = 0;
            
            StackPane stackPane = (StackPane) getNodeFromGridPane(grid, column, row);
            Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
            
            
            if (i == currentPlayerIndex) {
                rectangle.setOpacity(1.0);
            } else {
                rectangle.setOpacity(0.0);
            }
        }
    }
    
    /**
     * update the display of the scores in the given players score boxes
     * @param player 
     */
    public void updatePlayerCells(Player player) {
        int column = playerToColumnIndex(player);
        Map<Category, Integer> scores = player.getScores();

        for (Category category : Category.values()) {
            int row = category.ordinal() + 1;

            StackPane stackPane = (StackPane) getNodeFromGridPane(grid, column, row);
            stackPane.setCursor(Cursor.DEFAULT);
            stackPane.setOnMouseClicked(null);

            Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
            rectangle.setOpacity(0);

            Label label = (Label) stackPane.getChildren().get(1);
            String text;

            if (category == Category.SUM || category == Category.BONUS || category == Category.TOTAL) {
                int score = scores.getOrDefault(category, 0);
                text = Integer.toString(score);
            } else if (scores.containsKey(category)) {
                int score = scores.get(category);
                text = score > 0 ? Integer.toString(score) : "-";
            } else {
                text = "";
            } 

            label.setText(text); 
        }
                
    }
    /**
     * Roll the unselected dice
     */
    public void roll() {
        Player player = currentPlayer();
        rollsRemaining-- ; // decrease the remaining rolls 
        disableMouseInput(player); //to avoid double rolls
        updateRollButton(); // update number of rolls
        
        dice.roll();
        
        ParallelTransition parallelTransition = new ParallelTransition();
        
        for (int i = 0; i < dice.getDice().length; i++) {
            if (!dice.getDie(i).isDieSelected()) {
                diceImage[i].setImage(new Image("/images/die_roll_animation.gif"));
                diceImage[i].setDisable(true);
                
                double pause = new Random().nextDouble() + 0.5;
                final int diceIndex = i;
                
                PauseTransition delay = new PauseTransition(Duration.seconds(pause));
                delay.setOnFinished(event -> updateDiceImage(diceIndex));
                parallelTransition.getChildren().add(delay);
            }
        }
        
        parallelTransition.play();
        parallelTransition.setOnFinished(event -> showPossibleScores(player));
    }
    
    private void disableMouseInput(Player player) {
        disableRollButton();
        updatePlayerCells(player);
    }
    
    public void resetRollButton() {
        rollsRemaining = MAX_ROLLS;
        updateRollButton();
        enableRollButton();
    }
    
    public void disableRollButton() {
        btnRoll.setDisable(true);
    }
    
    public void enableRollButton() {
        boolean state = rollsRemaining <= MIN_ROLLS;
        btnRoll.setDisable(state);
    }
    
    public void updateRollButton() { 
        String text = String.format("Roll [%d]", rollsRemaining);
        btnRoll.setText(text);
    }
    
      /**
     * Update the image of the right dice by its index
     * @param index dice index (0-4)
     */
    private void updateDiceImage(int index) {
        FaceValue faceValue = dice.getDie(index).getFaceValue();
        
        switch (faceValue) {
            case ONE:
                diceImage[index].setImage(new Image("/images/die1.png"));
                break;
            case TWO:
                diceImage[index].setImage(new Image("/images/die2.png"));
                break;
            case THREE:
                diceImage[index].setImage(new Image("/images/die3.png"));
                break;
            case FOUR:
                diceImage[index].setImage(new Image("/images/die4.png"));
                break;
            case FIVE:
                diceImage[index].setImage(new Image("/images/die5.png"));
                break;
            case SIX:
                diceImage[index].setImage(new Image("/images/die6.png"));
                break;
            default:
                diceImage[index].setImage(null);
                break;
        }
        diceImage[index].setDisable(false);
    }
    
    private void showPossibleScores(Player player) {
        int column = playerToColumnIndex(player);
        ScoreCalculator scoreCalculator = new ScoreCalculator(dice.getFaceValues());
        final Map<Category, Integer> possibleScores = scoreCalculator.scoreMap();
        
        for (Category category: Category.values()) {
            int row = category.ordinal() + 1;
            
            if (category == Category.SUM || category == Category.BONUS || category == Category.TOTAL) {
                continue;
            }
            
            if (player.getScores().containsKey(category)) {
                continue;
            }
            
            makeScoreAvailable(category, player, possibleScores.get(category), column, row);
        }
        
        enableRollButton();
    }

    private void makeScoreAvailable(Category category, Player player, final int score, int column, int row) {
        Node node = getNodeFromGridPane(grid, column, row);

        StackPane stackPane = (StackPane) node;
        Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
        Label label = (Label) stackPane.getChildren().get(1);

        String color = score > 0 ? "40733286" : "#ffd6cc";

        rectangle.setFill(Color.web(color));
        rectangle.setOpacity(1.0);

        String text = score > 0 ? Integer.toString(score) : "-";
        label.setText(text);

        stackPane.setCursor(Cursor.HAND);
        stackPane.setOnMouseClicked(event -> selectScore(category, score, player));
    }
    
    private void selectScore(Category category, int score, Player player) {
        setPlayerScore(category, score, player);
        updatePlayerCells(player);

        boolean done = checkIfDone();

        if (!done) {
            resetDice();
            resetRollButton();
            nextPlayer();
        } else {
            System.out.println("complete game");
            completeGame();
        }
    }
    
    public void completeGame() { 
        
        List<Player> sortedPlayers = new ArrayList<>();
        for (Player player : players) {
            int playerTotal = player.getScores().get(Category.TOTAL);
            int index = 0;
            for (int i = 0; i < sortedPlayers.size(); i++) {
                int sortedTotal = sortedPlayers.get(i).getScores().get(Category.TOTAL);

                if (playerTotal < sortedTotal) {
                    index = i;
                    break;
                }
                index = i + 1;
            }
            sortedPlayers.add(index, player);
        }

        GridPane gridPane = (GridPane) scorePane.getChildren().get(0);
        double width = grid.getColumnConstraints().get(0).getPrefWidth();
        double height = grid.getRowConstraints().get(0).getPrefHeight();

        // put in grid 
        for (int row = 1; row <= sortedPlayers.size(); row++) {
            for (int col = 0; col < 3; col++) {
                int index = sortedPlayers.size() - row;

                StackPane stackPane = new StackPane();
                gridPane.add(stackPane, col, row);

                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(width);
                rectangle.setHeight(height);
                rectangle.setFill(Color.web(colors[realPlayerIndex(sortedPlayers.get(index))]));
                stackPane.getChildren().add(rectangle);

                Label label = new Label();
                String text = "";

                switch (col) {
                    case 0:
                        text = String.format("#%d", row);
                        break;
                    case 1:
                        text = sortedPlayers.get(index).getName();
                        break;
                    case 2:
                        text += sortedPlayers.get(index).getScores().get(Category.TOTAL);
                        break;
                    default:
                        break;
                }

                label.setText(text);
                stackPane.getChildren().add(label);
            }
        }

        rollingPane.setVisible(false);
        scorePane.setVisible(true);
    }
    
    private int realPlayerIndex(Player player) {
        for (int i = 0; i < players.size(); i++) {
            if (player.equals(players.get(i))) {
                return i;
            }
        }
        return -1;
    }
    
    private void nextCategory() {
        do {
            currentCategory = currentCategory.next();
        }
        while (currentCategory == Category.SUM || currentCategory == Category.BONUS || currentCategory == Category.TOTAL);
        
    }
    
    private boolean checkIfDone() {
        int lastPlayerScoreSize = players.get(players.size() - 1).getScores().size();
        return lastPlayerScoreSize == Category.values().length;
    }
    
    private void setPlayerScore(Category category, int score, Player player) {
        player.setScore(category, score);
        Map<Category, Integer> scores = player.getScores();

        int sum = 0;
        int total = 0;

        for (Category type : scores.keySet()) {
            if (type == Category.SUM || type == Category.BONUS || type == Category.TOTAL) {
                continue;
            }

            if (type.ordinal() < Category.SUM.ordinal()) {
                sum += scores.get(type);
            } else {
                total += scores.get(type);
            }
        }

        int bonus = sum >= needForBonus() ? BONUS_POINTS : 0;

        player.setScore(Category.BONUS, bonus);
        player.setScore(Category.SUM, sum);
        player.setScore(Category.TOTAL, sum + bonus + total);
    }
    
    @FXML
    public void selectDie(Event event) { // #SelectDie event game.fxml
        ImageView imageView = (ImageView) event.getSource();
        int cleanId = Integer.parseInt(imageView.getId().substring("die".length()));

        Die die = dice.getDie(cleanId);
        boolean selected = die.isDieSelected();
        die.setDieSelected(!selected);

        double opacity = (die.isDieSelected()) ? 1.0 : 0;

        switch (cleanId) {
            case 0:
                die0Selected.setOpacity(opacity);
                break;
            case 1:
                die1Selected.setOpacity(opacity);
                break;
            case 2:
                die2Selected.setOpacity(opacity);
                break;
            case 3:
                die3Selected.setOpacity(opacity);
                break;
            case 4:
                die4Selected.setOpacity(opacity);
                break;
            default:
                break; // should never occur
        }
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
    private int playerToColumnIndex (Player player) {
        for (int i = 0; i < players.size(); i++ ) {
            if (player == players.get(i)) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Illegal player argument");
    }
   
    public void resetDice() {
        // remove dice images
        for (ImageView dieImage: diceImage ) {
            dieImage.setImage(null);
        }
        // remove dice selected images
        for (ImageView dieSelectedImage: diceSelectedImage) {
            dieSelectedImage.setOpacity(0.0);
        }
        // remove all dice unselected
        for (Die die: dice.getDice()) {
            die.setDieSelected(false);
        }
    }
    
    // Setup the GAME PLayers
    public void setPlayers(List<Player> Myplayers) {
        this.players = Myplayers;
    } 
    
    @FXML
    public void goToMenu(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
    }
 
    @FXML
    public void resetGame() {
        System.out.println("reset game");
        
        resetDice();
        resetRollButton();
        
        currentPlayerIndex = 0;
        
        for (Player player: players) {
            player.resetScore();
        }
        
        clearBoard();
        this.setupBoard();
        
        scorePane.setVisible(false);
        rollingPane.setVisible(true);
    }
    
    private void clearBoard() {
        grid.getChildren().clear();
    }
    
}
