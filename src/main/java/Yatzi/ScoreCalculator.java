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
        scores.put(Category.ONES, ones());
        scores.put(Category.TWOS, twos());
        scores.put(Category.THREES, threes());
        scores.put(Category.FOURS, fours());
        scores.put(Category.FIVES, fives());
        scores.put(Category.SIXES, sixes());
        scores.put(Category.ONE_PAIR, onePair());
        scores.put(Category.TWO_PAIR, twoPair());
        scores.put(Category.THREE_OF_A_KIND, threeOfAKind());
        scores.put(Category.FOUR_OF_A_KIND, fourOfAKind());
        scores.put(Category.SMALL_STRAIGHT, smallStraight());
        scores.put(Category.LARGE_STRAIGHT, largeStraight());
        scores.put(Category.FULL_HOUSE, fullHouse());
        scores.put(Category.CHANCE, chance());
        scores.put(Category.YATZI, yatzi());

        return scores;
        
    }
    
    // All category methods calculator
    
    public int ones() { return sumOfSingle(FaceValue.ONE); }
    
    public int twos() { return sumOfSingle(FaceValue.TWO); }
    
    public int threes() { return sumOfSingle(FaceValue.THREE); }
    
    public int fours() { return sumOfSingle(FaceValue.FOUR); }
    
    public int fives() { return sumOfSingle(FaceValue.FIVE); }
    
    public int sixes() { return sumOfSingle(FaceValue.SIX); }
    
    public int chance() { return sum();} // return sum of the dice
    
    public int onePair() { return sumOfAkind(2); } //calculate the score of the best pair 
   
    /**
     * Calculate the score of the best two different pair
     * @return sum of the two pairs
     */
    public int twoPair() {
        Map<FaceValue, Integer> frequencies = frequency();
        int minFrequency = 2;
        
        FaceValue firstPair = highestRepeated(minFrequency);
        FaceValue secondPair = null;
        
        for (FaceValue faceValue: frequencies.keySet()) {
            if (faceValue == firstPair) {
                continue;
            }
            
            if (frequencies.get(faceValue) < minFrequency) {
                continue;
            }
            
            if (secondPair == null || faceValue.getValue() > secondPair.getValue()) {
                secondPair = faceValue;
            }
        }
        
        if (firstPair == null || secondPair == null) {
            return 0;
        } else {
            return firstPair.getValue() * minFrequency + secondPair.getValue() * minFrequency;
        }
    }
    
    public int threeOfAKind() { return sumOfAkind(3); }
        
    public int fourOfAKind() {  return sumOfAkind(4); }
       
    /**
     * check whether the face values equals small straight
     * @return 15 if true, else 0
     */
    public int smallStraight() {
        Map<FaceValue, Integer> frequencies = frequency();
        
        for (FaceValue faceValue: frequencies.keySet()) {
            if (faceValue == FaceValue.SIX) {
                return 0;
            } else if (frequencies.get(faceValue) > 1) {
                return 0;
            }
        }
        return 15;
    }
    /**
     * check if face values match with large straight
     * @return 20 if true, else 0
    */
    public int largeStraight() {
        Map<FaceValue, Integer> frequencies = frequency();
        
        for(FaceValue faceValue: frequencies.keySet()) {
            if (faceValue == FaceValue.ONE) {
                return 0;
            } else if (frequencies.get(faceValue) > 1) {
                return 0;
            }
        }
        return 20;
    }
    /**
     * check for a full house and calculate the sum
     * @return sum of the dice if true, else 0
     */
    public int fullHouse() {
        Map<FaceValue, Integer> frequencies = frequency();
        boolean pair = false;
        for (FaceValue faceValue: frequencies.keySet()) {
            if(frequencies.get(faceValue) == 2) {
                pair = true;
            }
        }
        return pair && frequencies.size() == 2 ? sum() : 0;
    }
    /**
     * check if the  face values 
     * @return the key-map of 
     */
    public int yatzi() {
        Map<FaceValue, Integer> frequencies = frequency();
        return frequencies.size() == 1 ? 50 : 0;
    }
    
    /** 
     * @return  sum of face values
     */
    private int sum() {
        int sum = 0;
        for (FaceValue face: faceValues){
            sum += face.getValue();
        }
        return sum;
    }
    
    /**
     * 
     * @param frequency of the face value
     * @return sum of the multiplication of a kind if true else null
     */
    private int sumOfAkind(int frequency) {
        FaceValue faceValue = highestRepeated(frequency);
        return faceValue != null ? faceValue.getValue() * frequency : 0;
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
    
    /**
     * finds the frequency of the different face values
     * @return map of the frequency
     */
    private Map<FaceValue, Integer> frequency() {
        Map<FaceValue, Integer> frequencies = new EnumMap<>(FaceValue.class);
        
        for (FaceValue faceValue: faceValues ) {
            Integer frequency = frequencies.get(faceValue);
            frequencies.put(faceValue, frequency != null ? frequency + 1 : 1);
        }
        return frequencies;
        
    }
    
    /**
     * finds the highest repeated face value
     * @param minFrequency minimum number of repeats to look for
     * @return the highest repeated face value with the given minFrequency
    */
    private FaceValue highestRepeated(int minFrequency) {
        Map<FaceValue, Integer> frequencies = frequency();
        
        FaceValue faceValue = null;
        
        for (Map.Entry<FaceValue, Integer> entry : frequencies.entrySet()) {
            if (entry.getValue() >= minFrequency) {
                if (faceValue == null || entry.getKey().getValue() > faceValue.getValue()) {
                    faceValue = entry.getKey();
                }
            }
        }
        return faceValue;
    }
    
}
