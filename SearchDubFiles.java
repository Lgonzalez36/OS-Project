import java.util.Hashtable;

/**
 * @author
 * @since November 11, 2021
 */

public class SearchDubFiles{

    private String dir;
    private Hashtable<Integer,String> hm;  

    SearchDubFiles(String dir){
        this.dir = dir;
        this.hm = new Hashtable<Integer,String>();
    }
}