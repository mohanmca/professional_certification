import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

// /* *****************************************************************************
//  *  Name:
//  *  Date:
//  *  Description:
//  **************************************************************************** */
//
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items = (Item[]) new Object[1];

    // start is the index-1 of the first available element, it always points to null or -1;
    // end is the indice of the last element, it is always points to non-null if end!=-1;
    private int end = -1;
    private int start = -1;

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // unit testing (required)
    public static void main(String[] args) {
        testSample();
        // testSample2();
        // testSample3();
        // testEmptyRandomizedQueue();
        // testIterator();
    }

    private static void testSample() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        checkSize(rq, 0);
        int size = 0;
        int sampleSize = StdRandom.uniform(500);
        System.out.printf("Number enqueing sample size -%s\n", sampleSize);
        for (int i = 0; i < sampleSize; i++) {
            size++;
            rq.enqueue(i);
            System.out.printf("%d - after enqueing -%s\n", size, rq.toString());
            if (i > 0 && i % 3 == 0) {
                rq.dequeue();
                System.out.printf("%d - ~~ dequeue - %s\n", size, rq.toString());
                size--;
                checkSize(rq, size);
            }
        }
        int oldSize = size;
        for (int i = 0; i < oldSize; i++) {
            rq.dequeue();
            size--;
            System.out.printf("%d - after dequeue -%s\n", size, rq.toString());
            if (i > 0 && i % 3 == 0) {
                int item = rq.size()+1;
                rq.enqueue(item);
                size++;
                System.out.printf("%d -> enqueued, current-size - %d - %s\n", item, size, rq.toString());
                checkSize(rq, size);
            }
        }
    }

    private static <Item> void checkSize(RandomizedQueue<Item> queue, int expectedSize) {
        if (queue.size() == expectedSize) {
            System.out.printf("Success! Expected size validation %d, %b\n", queue.size(),
                              queue.size() == expectedSize);
        }
        else {
            System.out.printf("Failure! Queue size vs expectedSize %d, vs %d, %b\n", queue.size(),
                              expectedSize, queue.size() == expectedSize);
            throw new IllegalArgumentException("size doesn't match");
        }
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null object not allowed");
        if (end + 1 == items.length && size() > items.length / 2) {
            doubleTheArray();
        }
        else if (end + 1 == items.length && size() <= items.length / 2) {
            shifLeft();
        }
        items[++end] = item;
    }

    public String toString() {
        return "RandomizedQueue{" +
                "items=" + Arrays.toString(items) +
                ", capacity=" + items.length +
                ", size=" + size() +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException("Already empty!");
        int random = StdRandom.uniform(end + 1);
        Item item = items[random];
        removeNth(random);
        return item;
    }

    // return the number of items on the randomized queue
    public int size() {
        return (end - start);
    }

    private void doubleTheArray() {
        int newSize = items.length * 2;
        int newEnd = size() - 1;
        Item[] newItems = (Item[]) new Object[newSize];
        for (int i = 0; i < size(); i++) {
            newItems[i] = items[start + 1 + i];
        }
        items = newItems;
        start = -1;
        end = newEnd;
    }

    private void shifLeft() {
        for (int i = 0; i < size(); i++) {
            items[i] = items[start + 1 + i];
        }
        for (int i = size(); i < items.length; i++) {
            items[i] = null;
        }
        int newEnd = size() - 1;
        start = -1;
        end = newEnd;
    }

    // remove and return the item from the back
    private void removeNth(int i) {
        // Remove from middle towards the end
        if (i == -1) throw new UnsupportedOperationException("Already empty!");
        if (size() == 1) {
            items[end] = null;
            resetPointers();
        }
        else if ((start + i) > size() / 2) {
            shiftLeft(i, end);
        }
        else {
            shiftRight(start, i);
        }
    }

    private void resetPointers() {
        start = -1;
        end = -1;
    }

    /*
     * Move all the element from first+1 the position to first.. till end
     * At the end of it, indice last pointing element would become null, and end would be reduced by 1
     */
    private void shiftLeft(int first, int last) {
        for (int i = first; i < last; i++) {
            items[i] = items[i + 1];
        }
        items[end] = null;
        --end;
    }

    /*
     * Move all the element from ith  position to i+1.. till end
     * At the end of it, ith position would be null
     */
    private void shiftRight(int first, int last) {
        for (int i = last - 1; i >= first && i >= 0; i--) {
            items[i + 1] = items[i];
        }
        ++start;
        items[start] = null;
    }

    private static void testSample2() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(9);
        rq.dequeue();
        checkSize(rq, 0);
        System.out.printf("Ensure this is empty : %b\n", rq.isEmpty());
        rq.enqueue(44);
        System.out.printf("Ensure Not empty : %b\n", !rq.isEmpty());
        checkSize(rq, 1);
        rq.enqueue(14);
        checkSize(rq, 2);
        rq.dequeue();
        System.out.printf("Ensure Not empty : %b\n", !rq.isEmpty());
        checkSize(rq, 1);
        Integer item = rq.dequeue();
        System.out.printf("Item should not be null : %d\n", item);
        System.out.printf("Ensure this is empty : %b\n", rq.isEmpty());
        checkSize(rq, 0);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    private static void testIterator() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        randomizedQueue.enqueue("1");
        randomizedQueue.enqueue("2");
        randomizedQueue.enqueue("3");
        randomizedQueue.enqueue("4");
        randomizedQueue.enqueue("5");
        Iterator<String> iter = randomizedQueue.iterator();
        while (iter.hasNext()) {
            System.out.printf("%s,", iter.next());
        }
        iter = randomizedQueue.iterator();
        System.out.println();
        while (iter.hasNext()) {
            System.out.printf("%s,", iter.next());
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int iteratorStart = 0;
            private int[] idxes = new int[end - start];

            {
                for (int i = 0; i < idxes.length; i++) {
                    idxes[i] = start + 1 + i;
                }
                StdRandom.shuffle(idxes);
            }

            public boolean hasNext() {
                return iteratorStart < idxes.length;
            }

            public Item next() {
                if (idxes.length == 0)
                    throw new NoSuchElementException("Empty!");
                return items[idxes[iteratorStart++]];
            }

            public void remove() {
                if (iteratorStart == idxes.length) {
                    throw new UnsupportedOperationException("Can't delete without adding!");
                }
                removeNth(idxes[iteratorStart]);
                iteratorStart++;
            }
        };
    }

    private static void testSample3() {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("APZNVXPHDS");
        rq.dequeue();
        rq.enqueue("ILFOBXYUFY");
        rq.enqueue("JLXDFBBLGY");
        rq.sample();
        rq.enqueue("EYTDZYUQUV");
        rq.sample();
        String item = rq.dequeue();
        System.out.println(item);
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Already empty!");
        int random = StdRandom.uniform(end + 1);
        return items[random];
    }

    private static void testEmptyRandomizedQueue() {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        System.out.printf("Empty Deque %b\n", randomizedQueue.size() == 0);
        try {
            randomizedQueue.dequeue();
        }
        catch (NoSuchElementException e) {
            System.out.printf("Empty Deque %b\n", randomizedQueue.size() == 0);
        }
        System.out.printf("Is empty Deque %b\n", !randomizedQueue.iterator().hasNext());
        try {
            randomizedQueue.iterator().next();
        }
        catch (NoSuchElementException e) {
            System.out.print("Success! failed to iterate empty Queue! \n");
        }
        randomizedQueue.enqueue("1");
        System.out.printf("Element with one  %b\n", randomizedQueue.size() == 1);
        System.out.printf("FirstElement Deque %b\n", randomizedQueue.dequeue().equals("1"));
    }


}
