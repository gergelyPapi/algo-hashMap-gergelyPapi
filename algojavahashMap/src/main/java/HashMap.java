import java.rmi.NoSuchObjectException;
import java.util.LinkedList;

public class HashMap <K, V> {

    private int bucketSize = 16;

    // This holds all the data. Its a primitive array where every element is a Linked List.
    // The Linked List holds elements of type KeyValue
    private LinkedList< KeyValue<K, V> > [] elements = new LinkedList[bucketSize];


    public void add(K key, V value) {
        // find out which position of the primitive array to use:
        int position = getHash(key);
        if (elements[position] != null) {
            throw new IndexOutOfBoundsException();
        } else {
            LinkedList<KeyValue<K, V>> list = new LinkedList<KeyValue<K, V>>();
            KeyValue<K, V> keyValueToAdd = new KeyValue(key, value);
            list.add(keyValueToAdd);
            elements[position] = list;
        }
        resizeIfNeeded();
    }

    public V getValue(K key) {
        // 1. Calculate the hash of the key. This defines which element to get from the "elements" array
        // 2. Find in the List in this position the KeyValue element that has this key, then return its value.
        //    If none of the items in the list has this key throw error.
        int keyHash = key.hashCode();
        V value = null;
        for (LinkedList< KeyValue<K, V> > element : elements) {
            if (element.get(0).getKey().hashCode() == keyHash) {
                return element.get(0).getValue();
            }
        }
        return value;
    }

    private int getHash(K key) {
        // This function converts somehow the key to an integer between 0 and bucketSize
        // In C# GetHashCode(), in Java hashCode() is a function of Object, so all non-primitive types
        // can easily be converted to an integer.
        LinkedList<Integer> usedHashList = new LinkedList<Integer>();
        int currentKeyHash = key.hashCode() % bucketSize;
        while (true) {
            if (!usedHashList.contains(currentKeyHash)) {
                break;
            } else {
                currentKeyHash = key.hashCode() % bucketSize;
            }
        }
        usedHashList.add(currentKeyHash);
        return currentKeyHash;
    }

    private void resizeIfNeeded() {
    // If it holds more elements than bucketSize * 2, destroy and recreate it
    // with the double size of the elements array.
    // if it holds less elements than bucketSize / 2, destroy and recreate it
    // with half size of the elements array.
    }

    public void remove(K key){
        int keyHash = key.hashCode();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                if (elements[i].get(0).getKey().hashCode() == keyHash) {
                    elements[i] = null;
                }
            }
        }
        try {
            throw new NoSuchObjectException("Item not found");
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
    }

    public String toString() {
        for (LinkedList< KeyValue<K, V> > element : elements) {
            System.out.println(element);
        }
        return null;
    }
}
