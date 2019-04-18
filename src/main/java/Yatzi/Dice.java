/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;

/**
 *
 * @author kplola
 * 
 * Setting up the dice used in the game, five die
 */
public class Dice {
    private Die[] dice = new Die[5];
    
    // Constructor
    public Dice() { 
        for (int i=0; i<dice.length; ++i) {
            dice[i] = new Die(); // each element of our dice array is an instance of Die
        }
    }
    
    // Getters
    public Die getDie(int index) { // get one element of dice by his index in argument
        return dice[index];
    }
    
    public Die[] getDice() { // get dice collection
        return dice;
    }
    
    public void roll() { //roll dies which are not selected
        for (Die die: dice) { 
            if(!die.isDieSelected()) {
                die.roll();
            }
        }
    }
    
    public FaceValue[] getFaceValues() { //for getting each die face value in the dice collection
        FaceValue[] faceValues = new FaceValue[dice.length];
        for (int i = 0; i < faceValues.length; i++) {
            faceValues[i] = dice[i].getFaceValue(); //copying each die facevalue in facevalues collection
        }
        return faceValues;
    }
}
