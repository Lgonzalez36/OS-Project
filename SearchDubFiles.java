/**
 * @author
 * @since November 11, 2021
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    public void search() throws NoSuchAlgorithmException, IOException{
        File folder = new File(this.dir);          // in windows is C:\\Desktop\\...
        File[] listOfFiles = folder.listFiles();    // in linux is "/home/lagonzalez/Documents/OS-Project"/...
        hashTableST = new SeparateChainingHashST<>((listOfFiles.length));
        System.out.println("Total files:\t" + listOfFiles.length + "\n");
        System.out.println("#" + "\t" + "HashCode" + "\t\t\t\t" + "File Name\n");
        int i=0;
        MessageDigest shaDigest = MessageDigest.getInstance("MD5");
        for (File file : listOfFiles){
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file.getName());
                byte[] byteArray = new byte[1024];
                int bytesCount = 0; 
                while ((bytesCount = fis.read(byteArray)) != -1) {
                    shaDigest.update(byteArray, 0, bytesCount);
                }
                StringBuilder sb = new StringBuilder();
                byte[] bytes = shaDigest.digest();
                for(int j=0; j< bytes.length ;j++){
                    sb.append(Integer.toString((bytes[j] & 0xff) + 0x100, 16).substring(1));
                }
                hashTableST.put(sb.toString(), file.getName());
                System.out.println((i+1) + "\t" + sb.toString() + "\t" + file.getName());
                i++;
            }
        }
        System.out.println("____________________________________________________________\n"); // page break

        //
        hashTableST.printST();
    }
}