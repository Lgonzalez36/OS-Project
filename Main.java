/**
 * @author
 * @since November 11, 2021
 */
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main{
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException{
        System.out.println("\nHello, Welcome to the Dublicate file finder");
        System.out.println("Enter the whole Directory you wish to search\n");

        // File dir = new File("/home/lagonzalez/Documents/OS-Project");  // in linux is /...
        File folder = new File("/home/lagonzalez/Documents/OS-Project");  // in windows is C:\\Desktop\\...
        File[] listOfFiles = folder.listFiles();
        
        System.out.println("Total files:\t" + listOfFiles.length + "\n");
        int i=0;
        MessageDigest shaDigest = MessageDigest.getInstance("MD5");
        for (File file : listOfFiles){
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file.getName());
                //Create byte array to read data in chunks
                byte[] byteArray = new byte[1024];
                int bytesCount = 0; 
                while ((bytesCount = fis.read(byteArray)) != -1) {
                    shaDigest.update(byteArray, 0, bytesCount);
                };
                StringBuilder sb = new StringBuilder();
                byte[] bytes = shaDigest.digest();
                for(int j=0; j< bytes.length ;j++){
                    sb.append(Integer.toString((bytes[j] & 0xff) + 0x100, 16).substring(1));
                }
                System.out.println((i+1) + "\t" + file.getName() + "\t\t\t" + sb.toString());
                i++;
            }
        }
    }
 }
