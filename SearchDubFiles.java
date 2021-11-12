import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***************************************************************************************
 * SearchDubFiles Class is used to search for Duplicate files, show the user
 * the results of the search and delete them if found. !!!!!! //Is It deleting?
 *
 * @author Luis Gonzales, Zarrukh Bazarov, Austin Krause
 * @version November, 2021
 *
 ***************************************************************************************/
public class SearchDubFiles{

    /** Declaring an instance of SeparateChainingHashST, used in search method */
    private SeparateChainingHashST<String, String> hashTableST;

    //File Variables
    private String dir;
    private File file;
    private File[] files;
    private String fileName;


    /**********************************************************************
     * Constructor helps to create the SearchDubFiles object with the
     * directory specified by the parameter.
     *
     * @param dir is the name of the directory which the user inputs
     **********************************************************************/
    SearchDubFiles(String dir){
        this.dir = dir;
    }

    /***********************************************************************************
     * Search method is used to search for directory for Duplicate files.
     *
     * @throws NoSuchAlgorithmException when requested algorithm is not available
     * @throws IOException if operation fails to execute
     ***********************************************************************************/
    public void search() throws NoSuchAlgorithmException, IOException {
        //Initializing File Variables
        file = new File(this.dir);
        files = file.listFiles();

        hashTableST = new SeparateChainingHashST<>((files.length));

        //Hash Variables
        byte[] bytes, digestedBytes;
        MessageDigest md;

        //Traverses through every file in the files array
        for (int i = 0; i < files.length; i++){

            //Checks if index is a file
            if(files[i].isFile()) {
                fileName = files[i].getName(); //Temporary file name variable
                bytes = Files.readAllBytes(files[i].toPath()); //Reads all bytes from file
                md = MessageDigest.getInstance("MD5"); //MD5 is a cryptographic hash function, unique enough for this program
                md.update(bytes); //Updates the digest
                digestedBytes = md.digest(); //Completes hash computation

                //StringBuilder is faster and uses less memory than String
                StringBuilder sb = new StringBuilder();

                //Appends hash to string
                for (int j = 0; j < digestedBytes.length; j++)
                    sb.append(Integer.toString((digestedBytes[j] & 0xff) + 0x100, 16).substring(1));

                hashTableST.put(sb.toString(), fileName); //Puts file in list to check for duplicates
                System.out.println((i) + ":\t" + sb + "\t" + fileName); //Prints hash and file
            }
        }

        hashTableST.printST();

        if(hashTableST.getNumDupFiles() != 0) {
            if (hashTableST.confirm())
                deleteFiles();
            else {
                System.out.println("\nFiles deletion did not complete");
                restart();
            }
        }
        else {
            System.out.println("\nNo duplicate files found");
            restart();
        }
    }

    //Deletes duplicate files //INCOMPLETE //DOES NOT FULLY FUNCTION
    //Austin - I don't think the linked list needs a key. It knows what to delete with just the value

    public void deleteFiles() throws IOException, NoSuchAlgorithmException {
        int deleted = 0;
        double bytes = 0;
        System.out.println("\nDeleting Files");
        for(int i = 0; i < files.length; i++)
        {
            for(int j = 0; j < 2; j++) //j < 2 is hard coded because I don't know how to find hashTable
            {
                if(hashTableST.getLength(j) > 1)
                {
                    System.out.println(files[i].getName());
                    System.out.println(hashTableST.getValue(j));
                    System.out.println("------------");
                    if(files[i].getName().equals(hashTableST.getValue(j)))
                    {
                        bytes = bytes + files[i].length();
                        files[i].delete();
                        System.out.println("Deleted " + files[i].getName());
                        //There should be a function that deletes value from linked list
                        deleted++;
                    }
                }
                else
                    System.out.println("Skip\n------------");
            }
        }
        System.out.println("\n" + deleted + " Files Deleted. " + bytes + " bytes Storage Saved");
        restart();
    }

    //Asks user if they want to search another directory
    public void restart() throws IOException, NoSuchAlgorithmException {
        Scanner s = new Scanner(System.in); //Scanner object
        System.out.print("\nSearch another directory? (Y/N) ");
        String input = s.nextLine(); //User input

        if(input.equals("Y") || input.equals("y"))
            Main.main(null);
        else if(input.equals("N") || input.equals("n"))
        {
            System.out.println("\nEnding program. Goodbye");
            System.exit(0);
        }
        else {
            System.out.println("\nInvalid Choice. Type \"Y\" for Yes or \"N\" for No\n");
            restart();
        }
    }
}
