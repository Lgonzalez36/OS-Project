import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
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

    /** dir stands for Directory name, which the user inputs */
    private String dir;


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
        //File Variables
        File file = new File(this.dir);
        File[] files = file.listFiles();
        String fileName;

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
    }
}
