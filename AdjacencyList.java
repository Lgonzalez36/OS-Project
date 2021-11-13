import java.io.File;
import java.io.IOException;
import java.util.*;

/***************************************************************************************
 * AdjacencyList Class is used to hold all the list of files.
 * An adjacency list is a graph, which is essentially an array of linked lists.
 *
 * @author Luis Gonzales, Zarrukh Bazarov, Austin Krause
 * @version November, 2021
 *
 ***************************************************************************************/
public class AdjacencyList<Key> {

    //Variables
    private final int m;
    private int dupCount = 0;
    private final LinkedList<Key>[] l;

    /**
     * Initializes the adjacency list
     */
    public AdjacencyList(int m) {
        this.m = m;
        System.out.println("\nFILES:");
        l = (LinkedList<Key>[]) new LinkedList[m];
        for (int i = 0; i < m; i++)
            l[i] = new LinkedList<>();
    }

    /**
     * hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
     * (from Java 7 implementation, protects against poor quality hashCode() implementations)
     *
     * @param  key the key
     */
    private int hash(Key key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (m-1);
    }

    /**
     * Returns length of linked list
     * @param index index of specific linked list
     */
    public int getLength(int index)
    {
        return l[index].size();
    }

    /**
     * Inserts the specified key-value pair into the adjacency list
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if key is null
     */
    public void put(Key key, File val) throws IOException {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            System.out.println("IN VAL ++ NULL");
            delete(key);
            return;
        }
        for(int i=0; i < m; i++) {
            if(l[i].isEmpty()){
                l[i].put(key, val);
                return;
            }
            else if (!l[i].isEmpty() && l[i].contains(key)) {
                dupCount++;
                l[i].put(key, val);
                return;
            }
        }
    }

    /**
     * Removes the specified key and its associated value from the adjacency list
     *
     * @param  key the key
     * @throws IllegalArgumentException if key is null
     */
    public void delete(Key key) throws IOException {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        if (l[i].contains(key))
        l[i].delete(key);
    }

    /**
     * Prints linked lists
     */
    public void printLists() {
        System.out.println("\nIndex:\tKey:\t\t\t\t\t\t\t" + "\tValues: ");
        System.out.println("-------------------------------------------");
        for (int i=0; i < l.length; i++){
            if (!l[i].isEmpty()){
                System.out.print( i + ":\t");
                System.out.print("\t" + l[i].getKey().toString() + "\t ");
                l[i].print();
                System.out.println("\n-------------------------------------------");
            }
        }
    }

    /**
     * Returns amount of duplicate files
     */
    public int getNumDupFiles() {
        return dupCount;
    }

    /**
     * Confirms that user wants to delete duplicate files
     */
    public boolean confirm() {
        boolean choice = false;
        Scanner s = new Scanner(System.in); //Scanner object
        System.out.println("\n" + getNumDupFiles() + " duplicate files found");

        System.out.print("Delete duplicate files? (Y/N) ");
        String input = s.nextLine(); //User input

        //Will confirm that user wants to delete duplicates. Repeats menu if invalid input
        if(input.equals("Y") || input.equals("y"))
            choice = true;
        else if(input.equals("N") || input.equals("n"))
            System.out.println("\nFiles deletion did not complete");
        else {
            System.out.println("\nInvalid Choice. Type \"Y\" for Yes or \"N\" for No\n");
            confirm();
        }
        return choice;
    }

    /**
     * Deletes duplicate file from linked list
     * @param index index of specific linked list
     */
    public void deleteDupFiles(int index) {
        l[index].deleteLinkedList();
    }

    /**
     * Returns size of bytes deleted
     * @param index index of specific linked list
     */
    public long getTotalBytes(int index) {
        return l[index].getTotalSize();
    }
}
