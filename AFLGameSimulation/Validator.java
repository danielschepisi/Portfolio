public class Validator
{
    public Validator()
    {

    }

    public boolean isBlank(String input)
    {
        return input.trim().length() == 0 ? true : false;
    }

    public boolean intWithinRange(int input, int min, int max)
    {
        return input >= min && input <= max;
    }
}