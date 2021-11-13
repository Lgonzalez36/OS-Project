import java.io.File;

/***************************************************************************************
 * Linked List Class holds the key and files from the directory
 *
 * @author Luis Gonzales, Zarrukh Bazarov, Austin Krause
 * @version November, 2021
 *
 ***************************************************************************************/
public class LinkedList<Key> {

    //Variables
    private int n; // number of key-value pairs
    private long totalBytes = 0;
    private Node first; // the linked list of key-value pairs

    // a helper linked list data type
    private class Node {
        private final Key key;
        private final File val;
        private Node next;

        /**
         * Initializes an empty symbol table with m chains.
         * @param key hash code
         * @param val file value
         * @param next next node
         */
        public Node(Key key, File val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    /**
     * Returns the number of key-value pairs in the list.
     * @return the number of key-value pairs in the list
     */
    public int size() {
        return n;
    }

    /**
     * Is this list empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Does this list contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the list
     *     and {@code null} if the key is not in the list
     */
    public File get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    /**
     * Inserts the key-value pair into the list, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the list.
     * @param key the key
     * @param val the value
     */
    public void put(Key key, File val) {

        if (val == null) {
            delete(key);
            return;
        }
        for (Node x = first; x != null; x = x.next) {
            if (x.next == null) {
                x.next = new Node(key, val, null);
                n++;
                return;
            }
        }
        first = new Node(key, val, null);
        n++;
    }

    /**
     * Removes the key and associated value from the list
     * @param key the key
     */
    public void delete(Key key) {
        first = delete(first, key);
    }

    /**
     * Delete key in linked list beginning at Node x
     * @param x node
     * @param key the key
     */
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    /**
     * Prints the individual linked list
     */
    public void print(){
        for (Node x = first; x != null; x = x.next) {
            System.out.print(" | " + x.val.getName());
        }
    }

    /**
     * Gets first key
     */
    public Key getKey(){
        return first.key;
    }

    /**
     * Returns total size of bytes deleted
     */
    public long getTotalSize(){
        return totalBytes;
    }

    /**
     * Deletes files from linked list
     * Adds bytes deleted to total bytes variable
     */
    public void deleteLinkedList() {
        for (Node x = first; x != null; x = x.next) {
            if(x.next == null) {
                first = x;
                break;
            }
            totalBytes += x.val.length();
            x.val.delete();
            n--;
        }
    }
}
