/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kplola
 */
public class Player {
    private final String name;
    private Map<Category, Integer> scores = new EnumMap<>(Category.class);
   
    
    public Player(String playerName){ //default constructor
        name = playerName;
    }
    
    // Getters & Setters
    public String getName() {
        return name;
    }
    /**
     * 
     * @return a map of the players current scores
     */
    public Map<Category, Integer> getScores() {
        return scores;
    }
    /**
     * sets the score of the player
     * @param scoreType type of score to set (ONES | TWOES etc.)
     * @param score value of score to set
     */
    public void setScore(Category scoreType, int score) {
        if (scores.containsKey(scoreType)) {
            scores.replace(scoreType, score);
        } else {
            scores.put(scoreType, score);
        }
    }
    
    // reset the player's score to defaults 0
    public void resetScore() {
        scores = new EnumMap<>(Category.class);
        defaultScore();
    }
    
    // Set default score
    private void defaultScore() {
        scores.put(Category.SUM, 0);
        scores.put(Category.BONUS, 0);
        scores.put(Category.TOTAL, 0);

        
    }
 
    
}
