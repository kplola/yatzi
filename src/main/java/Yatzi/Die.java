/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;
import java.util.Random;
/**
 *
 * @author kplola
 */
public class Die {
    private static int MAX_FACE_NUMBER = FaceValue.values().length;
    
    private FaceValue faceValue;
    private boolean dieSelected = false;
    
    // Attributes getters & setters
    public FaceValue getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(FaceValue faceValue) {
        this.faceValue = faceValue;
    }

    public boolean isDieSelected() {
        return dieSelected;
    }

    public void setDieSelected(boolean dieSelected) {
        this.dieSelected = dieSelected;
    }
    
    // DIE throwing
    public void roll() {
        int number = new Random().nextInt(MAX_FACE_NUMBER);
        FaceValue faceValue = FaceValue.values()[number]; // setting face's value with the random number
        setFaceValue(faceValue);
    }
    
}
