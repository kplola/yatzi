/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;
import java.util.EnumMap;
import java.util.Map;
/**
 *
 * @author kplola
 */
public class ScoreCalculator {
    private final FaceValue[] faceValues;
    
    // Constructor
    public ScoreCalculator(FaceValue[] faceValues) {
        this.faceValues = faceValues;
    }
    
    public Map<Category, Integer> scoreMap() {
        Map<Category, Integer> scores = new EnumMap<>(Category.class);
        
        // Code to add
        
        return scores;
    }
    
    // All category methods calculator
    /**
     * 
     * @return  sum of face values
     */
    private int sum() {
        int sum = 0;
        for (FaceValue face: faceValues){
            sum += face.getValue();
        }
        return sum;
    }
    
    
    private int sumOfAkind(int frequency) {
      
        // code to add
        return 0;
    }
    
    /**
     * 
     * @param faceValue
     * @return the sum of the same @param value
     */
    private int sumOfSingle(FaceValue faceValue) {
        int sum =0;
        for (FaceValue face: faceValues) {
            if(face == faceValue) {
                sum += face.getValue();
            }
        }
        return sum;
    }
    
    
    
}
