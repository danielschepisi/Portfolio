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
    * @throws    fileIllegalArgumentExceptionName    If the fileName is blank
    */
    public String readFile(String fileName)
    {
        if(Validator.isBlank(fileName))
        {
            System.out.println("Please enter a FileName");
            throw new IllegalArgumentException("Can't read file if fileName is blank.");
        }
        
        String fileBody = "";
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
        catch (Exception e)
        {
            System.out.println("There was an error reading the file.");
        }

        return fileBody;
    }

    /**
    * Writes the provided text to a file with the provided file name
    * @param    text    The data to write
    * @param    fileName    The name of the file
    * @throws    fileIllegalArgumentExceptionName    If the fileName is blank
    */
    public void writeFile(String text, String fileName)
    {
        try
        {
            if(Validator.isBlank(fileName))
            {
                System.out.println("Please enter a FileName");
                throw new IllegalArgumentException("Can't write to file with blank fileName.");
            }
            else
            {
                FileWriter writer = new FileWriter(fileName);
                writer.append(text);
                writer.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("There was an error writing to " + fileName);
        }
    }
}