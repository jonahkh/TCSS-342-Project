/* Bui, John
 * Howard, Jonah
 * Lai, Henry
 * Taylor, Lisa
 * TCSS 342 - Data Structures
 * Professor Donald Chinn
 * Homework 6B
 * March 8, 2016
 */

package model;

/**
 * This class represents an individual Literal Token.
 */
public class LiteralToken extends Token {
    
    /**
     * Initialize a new Literal Token for int passed as argument.
     * 
     * @param literal the value for this Token
     */
    public LiteralToken(final int literal) {
        this(literal + "");
    }
    
       /**
     * Initialize a new Literal Token for string passed as argument.
     * 
     * @param literal the value for this Token
     */
    public LiteralToken(final String literal) {
        if (literal != null)
            setToken(literal);
    }
    
    /**
     * Set the token to the passed int myValue.
     * 
     * @param theValue The myValue to set this token to
     */
    public void setValue(final int theValue) {
        setToken(theValue + "");
    }
    
    /**
     * Set the token to the passed int myValue.
     * 
     * @param theValue The myValue to set this token to
     */
    public void setValue(final String theValue) {
        setToken(theValue);
    }
    
    /**
     * Returns the value associated with this Literal Token.
     * 
     * @return the value associated with this Literal Token
     */
    public int getValue() {
        return Integer.parseInt(getToken());
    }
}
