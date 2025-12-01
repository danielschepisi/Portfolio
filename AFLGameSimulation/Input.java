/**
 * This file is responsilble for managing input requests
 * to a user
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
import java.util.Scanner;

public class Input
{
    public Input()
    {

    }

    /**
    * Returns a int received from the user after displaying
    * the provide message
    * @param    message    The message to display to the user
    */
    public int askForIntegerInput(String message)
    {
        Scanner console = new Scanner(System.in);
        System.out.println(message);
        return Integer.parseInt(console.nextLine().trim());
    }

    /**
    * Returns a string received from the user after displaying
    * the provide message
    * @param    message    The message to display to the user
    */
    public String askForStringInput(String message)
    {
        Scanner console = new Scanner(System.in);
        System.out.println(message);
        return console.nextLine().trim();
    }
}