/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzi;

/**
 *
 * @author kplola
 */
public enum Category {
    ONES,
    TWOS,
    THREES,
    FOURS,
    FIVES,
    SIXES,
    SUM,
    BONUS,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FOUR_OF_A_KIND,
    SMALL_STRAIGHT,
    LARGE_STRAIGHT,
    FULL_HOUSE,
    CHANCE,
    YATZI,
    TOTAL;
    
    private static final Category[] categories = values();
    
    public Category next() { // to know the possible remaining category
        return categories[(this.ordinal() + 1) % categories.length];
    } 
}
