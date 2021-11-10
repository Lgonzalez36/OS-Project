import java.util.Hashtable;

public class testHash {
    public static void main(String[] args){
        Hashtable<String, Integer> hash = new Hashtable<String, Integer>();
        SeparateChainingHashST<Integer, String> integerToWordMap = new SeparateChainingHashST<>();
        int i = 1;
        int j = 2;
        int k = 3;
        int l = 4;
        int m = 2;
        integerToWordMap.put(i , "one");      // 1
        integerToWordMap.put(j ,"two" );      // 2
        integerToWordMap.put(k , "three" );   // 3
        integerToWordMap.put(l , "four" );    // 4
        integerToWordMap.put(m , "Five" );     // 2


        System.out.println("Searching for 2: " + integerToWordMap.get(2));
        for (Integer keys : integerToWordMap.keys()){
            System.out.print(integerToWordMap.get(keys));
            System.out.print(" ,");
        }
    }
}
