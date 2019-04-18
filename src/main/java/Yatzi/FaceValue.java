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
 * Represents the different faces of a die - the six faces
 */
public enum FaceValue {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);
    
    private int value;

    private FaceValue(int value) { // default constructor
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
}
