import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***************************************************************************************
 * SearchDupFiles Class is used to search for Duplicate files, show the user
 * the results of the search and delete them if found.
 *
 * @author Luis Gonzales, Zarrukh Bazarov, Austin Krause
 * @version November, 2021
 *
 ***************************************************************************************/
public class SearchDupFiles{

    //Declaring an instance of aList, used in search method
    private AdjacencyList<String> aList;

    //File Variables
    private final String dir;
    private File[] files;

    /**
     * Constructor helps to create the SearchDubFiles object with the
     * directory specified by the parameter.
     *
     * @param dir is the name of the directory which the user inputs
     */
    SearchDupFiles(String dir){
        this.dir = dir;
    }

    /**
     * Search method is used to search for directory for Duplicate files.
     *
     * @throws NoSuchAlgorithmException when requested algorithm is not available
     * @throws IOException if operation fails to execute
     */
    public void search() throws NoSuchAlgorithmException, IOException {
        //Initializes File Variables
        File file = new File(this.dir);
        files = file.listFiles();

        //Declares new Adjacency Table
        assert files != null;
        aList = new AdjacencyList<>((files.length));

        //Hash Variables
        byte[] bytes, digestedBytes;
        MessageDigest md;

        //Traverses through every file in the files array
        for (int i = 0; i < files.length; i++){

            //Checks if index is a file
            if(files[i].isFile()) {
                String fileName = files[i].getName(); //Temporary file name variable
                bytes = Files.readAllBytes(files[i].toPath()); //Reads all bytes from file
                md = MessageDigest.getInstance("MD5"); //MD5 is a cryptographic hash function, unique enough for this program
                md.update(bytes); //Updates the digest
                digestedBytes = md.digest(); //Completes hash computation

                //StringBuilder is faster and uses less memory than String
                StringBuilder sb = new StringBuilder();

                //Appends hash to string
                for (byte digestedByte : digestedBytes)
                    sb.append(Integer.toString((digestedByte & 0xff) + 0x100, 16).substring(1));

                System.out.println((i) + ":\t" + sb + "\t" + fileName); //Prints hash and file
                aList.put(sb.toString(), files[i]); //Puts file in list to check for duplicates
            }
        }

        aList.printLists();

        //Asks user if they want to delete all duplicate files.
        //Restarts if no duplicates are found or user says "No"
        if(aList.getNumDupFiles() != 0) {
            if (aList.confirm())
                deleteFiles();
            else
                restart();
        }
        else {
            System.out.println("\nNo duplicate files found");
            restart();
        }
    }

    /**
     *Deletes duplicate files:
     *Steps go through each index in st in AdjacencyList.java
     * if the length of st[i] > 1
     *      - get bytes
     *      - get file name
     *      - total deleted
     *      - delete from linked list
     *      - delete from directory
     * else skip
     * @throws NoSuchAlgorithmException when requested algorithm is not available
     * @throws IOException if operation fails to execute
     */
    public void deleteFiles() throws IOException, NoSuchAlgorithmException {
        long totalBytes = 0; //Total bytes of storage saved
        int deleted = 0; //Total files deleted
        System.out.println("\nDeleting Files...");
        for (int i=0; i< files.length; i++) {
            //If length of linked list is greater than one, delete duplicates from it
            if (aList.getLength(i) > 1) {
                deleted = deleted + (aList.getLength(i) - 1); //Adds total files deleted
                aList.deleteDupFiles(i);
                totalBytes += aList.getTotalBytes(i); //Adds total bytes deleted
            }
        }

        //Prints out results of deletion
        aList.printLists();
        System.out.println("\n[" + deleted + " Files Deleted]");
        System.out.print("[" + totalBytes + " Bytes (About: " + (totalBytes/1048576) + " MB) Storage Saved]");

        restart(); //Restarts program
    }

    /**
     * Asks the user if they want to search another directory
     *
     * @throws NoSuchAlgorithmException when requested algorithm is not available
     * @throws IOException if operation fails to execute
     */
    public void restart() throws IOException, NoSuchAlgorithmException {
        Scanner s = new Scanner(System.in); //Scanner object
        System.out.print("\nSearch another directory? (Y/N) ");
        String input = s.nextLine(); //User input

        //Will restart or quit program based on user input. Repeats menu if invalid input
        if(input.equals("Y") || input.equals("y"))
            Main.main(null);
        else if(input.equals("N") || input.equals("n")) {
            System.out.println("\nEnding program. Goodbye");
            System.exit(0);
        }
        else {
            System.out.println("\nInvalid Choice. Type \"Y\" for Yes or \"N\" for No\n");
            restart();
        }
    }
}
