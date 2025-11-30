/**
 * This class is reponsible for any validation required
 * on data
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
public class Validator
{
    public Validator()
    {

    }

    /**
    * Checks if an int is within the provided range
    * @param    input    The value to be validated
    * @param    min    The minimum allowed value
    * @param    max    The maximum allowed value
    */
    public boolean intWithinRange(int input, int min, int max)
    {
        return input >= min && input <= max;
    }
    
    /**
    * Checks if an the string is blank
    * @param    input    The string to be validated
    */
    public boolean isBlank(String input)
    {
        return input.trim().length() == 0 ? true : false;
    }
}