import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**********************************************************************
 * Main class
 *
 * @author Luis Gonzales, Zarrukh Bazarov, Austin Krause
 * @version November, 2021
 *
 **********************************************************************/
public class Main {

    /**
     * Main method within the main class. There is an output for the user
     * to welcome to the program. The program also asks for user to input
     * Directory name. The main method uses SearchDubFiles class to help
     * with searching duplicate files.
     *
     * @throws IOException if operation fails to execute
     * @throws NoSuchAlgorithmException when requested algorithm is not available
     *
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        boolean valid = false;
        System.out.println("\nDUPLICATE FILE FINDER");

        //Repeats until valid input
        while(!valid) {
            Scanner s = new Scanner(System.in); //Scanner object
            System.out.print("Enter The Directory Name: ");
            String directory = s.nextLine(); //Lets user input directory name

            Path path = Path.of(directory); //Used to check if path exists

            //Runs search() method if directory exists. Lets user input again otherwise
            if (Files.exists(path)) {
                valid = true;
                SearchDupFiles sdf = new SearchDupFiles(directory);
                sdf.search();
            }
            else
                System.out.println("\nDirectory does not exist. Try again.\n");
        }
    }
 }
