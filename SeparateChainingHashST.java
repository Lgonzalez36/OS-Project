import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  The SeparateChainingHashST class represents a symbol table of generic
 *  key-value pairs.
 *  It supports the usual put, get, contains,
 *  delete, size, and is-empty methods.
 *  It also provides a keys method for iterating over all of the keys.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike java.util.Map, this class uses the convention that
 *  values cannot be nullâ€”setting the
 *  value associated with a key to null is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a separate chaining hash table. It requires that
 *  the key type overrides the equals() and hashCode() methods.
 *  The expected time per put, contains, or remove
 *  operation is constant, subject to the uniform hashing assumption.
 *  The size, and is-empty operations take constant time.
 *  Construction takes constant time.
 */
public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private int n;                                // number of key-value pairs
    private int m;                                // hash table size
    private int dupCount = 0;                        // number of duplicate files
    private testHash2<Key, Value>[] st;  // array of linked-list symbol tables

    // Initializes an empty symbol table.
    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    } 

    /**
     * Initializes an empty symbol table with m chains.
     * @param m the initial number of chains
     */
    public SeparateChainingHashST(int m) {
        this.m = m;
        System.out.println("\nFILES:");
        st = (testHash2<Key, Value>[]) new testHash2[m];
        for (int i = 0; i < m; i++)
            st[i] = new testHash2<Key, Value>();
    } 

    // resize the hash table to have the given number of chains,
    // rehashing all of the keys
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.st = temp.st;
    }

    // hash function for keys - returns value between 0 and m-1
    private int hashTextbook(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
    private int hash(Key key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (m-1);
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    } 

    /**
     * Returns true if this symbol table is empty.
     *
     * @return true if this symbol table is empty;
     *         false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return true if this symbol table contains key;
     *         false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    } 

    /**
     * Returns the value associated with the specified key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with key in the symbol table;
     *         null if no such value
     * @throws IllegalArgumentException if key is null
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        return st[i].get(key);
    } 

    public int getLength(int index)
    {
        return st[index].size();
    }

    public Value getValue(int index)
    {
        return st[index].getValue();
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is null.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if key is null
     */
    public void put(Key key, Value val)
    {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null)
        {
            System.out.println("IN VAL ++ NULL");
            delete(key);
            return;
        }
        for(int i=0; i < m; i++)
        {
            if(st[i].isEmpty()){
                st[i].put(key, val);
                return;
            }
            else if (!st[i].isEmpty() && st[i].contains(key))
            {
                dupCount++;
                st[i].put(key, val);
                return;
            }
        }
    } 

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if key is null
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        if (st[i].contains(key)) 
            n--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (m > INIT_CAPACITY && n <= 2*m) 
            resize(m/2);
    }

    public void deleteValue(int index)
    {
        st[index].deleteValue();
    }

    // return keys in symbol table as an Iterable
    public Iterable<Key> keys() {
        AbstractQueue<Key> queue = new LinkedBlockingQueue<Key>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()){
                System.out.println(key);
                queue.add(key);
            }
        }
        return queue;
    }

    public testHash2<Key, Value>[] getST(){
        return this.st;
    }

	public void printST() {
        System.out.println("\nIndex:\tKey:\t\t\t\t\t\t\t" + "\tValues: ");
        System.out.println("-------------------------------------------");
        for (int i=0; i < st.length; i++){
            if (!st[i].isEmpty()){
                System.out.print( i + ":\t");
                System.out.print("\t" + st[i].getKey().toString() + "\t ");
                st[i].printST();
                System.out.println("\n-------------------------------------------");
            }
        }
	}

    //Returns amount of duplicate files
    public int getNumDupFiles() {
        return dupCount;
    }

    //Confirms that user wants to delete duplicate files
    public boolean confirm() {
        boolean choice = false;
        Scanner s = new Scanner(System.in); //Scanner object
        System.out.println("\n" + getNumDupFiles() + " duplicate files found");

        System.out.print("Delete duplicate files? (Y/N) ");
        String input = s.nextLine(); //User input

        if(input.equals("Y") || input.equals("y"))
            choice = true;
        else if(input.equals("N") || input.equals("n"))
            choice = false;
        else {
            System.out.println("\nInvalid Choice. Type \"Y\" for Yes or \"N\" for No\n");
            confirm();
        }
        return choice;
    }
}
