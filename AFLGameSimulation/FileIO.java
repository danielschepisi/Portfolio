/**
 * This class is responsile for the readind and writing
 * of data to and from files.
 * 
 * @author Daniel Schepisi
 * @version ver 1.0.0
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class FileIO
{
    public FileIO()
    {

    }

    /**
    * Reads the file with the provided fileName and returns 
    * he contents as a string
    * @param    fileName    The name of the file to be read
    */
    public String readFile(String fileName) //fileName > 0
    {
        // if(fileName.trim().length() > 0) use this, else print please enter file name
        String fileBody = "";

        //think i can simplify all this, check last video vs notes, can all be in one try block
        try
        {
            FileReader reader = new FileReader(fileName);

            try
            {
                Scanner fileInput = new Scanner(reader);
                while (fileInput.hasNextLine())
                {
                    fileBody += fileInput.nextLine() + "\r\n";
                }
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.out.println("Error closing the file");
                }
            }
        }
        catch (Exception e) //FileNotFOundException, IOException
        {
            System.out.println("The was an error reading the file.");
        }

        return fileBody;
    }

    /**
    * Writes the provided text to a file with the provided file name
    * @param    text    The data to write
    * @param    fileName    The name of the file
    */
    public void writeFile(String text, String fileName)
    {
        try
        {
            if(fileName.trim().length() > 0)
            {
                //he used print writer in video, check difference
                FileWriter writer = new FileWriter(fileName);
                writer.append(text);
                writer.close();
            }
            else
            {
                System.out.println("Please enter a FileName");
            }
        }
        catch (Exception e)
        {
            System.out.println("There was an error writing to " + fileName);
        }
    }
}