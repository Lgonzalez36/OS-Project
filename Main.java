/**
 * @author
 * @since November 11, 2021
 */

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class Main {
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException{
        System.out.println("\nHello, Welcome to the Dublicate file finder");
        System.out.println("Enter the whole Directory you wish to search\n");
        // ___________________________________________________________
        // User inputs working Directory here and has error checking
        String directory = "C:\\Users\\luisg\\Documents\\Fall 2021\\OS-Project\\";
        // ___________________________________________________________
        
        SearchDubFiles sdf = new SearchDubFiles(directory);
        sdf.search();
    }
 }
