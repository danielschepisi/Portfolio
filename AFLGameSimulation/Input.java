import java.util.Scanner;

public class Input
{
    public Input()
    {

    }

    //??mark as throws??
    public int askForIntegerInput(String message)
    {
        Scanner console = new Scanner(System.in);
        System.out.println(message);
        // String input = console.nextLine().trim();
        return Integer.parseInt(console.nextLine().trim());
    }

    public String askForStringInput(String message)
    {
        Scanner console = new Scanner(System.in);
        System.out.println(message);
        return console.nextLine().trim();
    }
}