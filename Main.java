
import java.io.*;
import java.security.NoSuchAlgorithmException;

/**********************************************************************
 * Main class to run our program.
 *
 * @author Luis Gonzales, Zarrukh Bazarov, Austin Krause
 * @version November, 2021
 *
 **********************************************************************/
public class Main {

    /**********************************************************************
     * Main method within the main class. There is an output for the user
     * to welcome to the program. The program also asks for user to input
     * Directory name. The main method uses SearchDubFiles class to help
     * with searching duplicate files.
     *
     * @throws IOException if operation fails to execute
     * @throws NoSuchAlgorithmException when requested algorithm is not available
     *
     **********************************************************************/
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException{

        //Output for the user to welcome to the program
        System.out.println("\nHello, Welcome to the Duplicate file finder!");

        //Output for the user to ask for the Directory name
        System.out.println("Enter the whole Directory you wish to search\n");
        // ___________________________________________________________
        // User inputs working Directory here and has error checking
        String directory = "C:\\Users\\luisg\\Documents\\Fall 2021\\OS-Project\\";
        // ___________________________________________________________

        //Declaring and Initializing SearchDubFiles object, which is needed to run our program;
        //followed by "search()" method which is used to search for Dubplicate files.
        SearchDubFiles sdf = new SearchDubFiles(directory);
        sdf.search();
    }
 }
