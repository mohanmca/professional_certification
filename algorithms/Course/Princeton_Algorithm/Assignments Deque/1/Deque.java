/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
start -- would pointing the index of the first element, including zero
end -- would pointing the index of the last+1 item, null pointer, including length of the array
*/
public class Deque<Item> implements Iterable<Item> {
    private Item[] items = (Item[]) new Object[3];
    private int start = 1;
    private int end = start;

    // construct an empty deque
    public Deque() {
    }

    // unit testing (required)
    public static void main(String[] args) {
        testIterator();
        testFirstRemove();
        testSequence();
        failureToAdd9();
        testEmptyRemoveFailure();
        testAdditionAndDeletion();
    }

    private static void testSequence() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        checkSize(deque, 1);
        deque.addLast(3);
        deque.addLast(4);
        checkSize(deque, 3);
        Integer element = deque.removeFirst();
        System.out.printf("element expected %d, %b\n", element, element == 1);
        checkSize(deque, 2);
    }

    private static void checkSize(Deque<Integer> queue, int expectedSize) {
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

    private static void testFirstRemove() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        Iterator<Integer> iter = deque.iterator();
        iter.remove();
        try {
            iter.next();
            System.out.println("Failed to throw: UnsupportedOperationException expected");
        }
        catch (NoSuchElementException e) {
            System.out.println("Success: UnsupportedOperationException expected");
        }
        checkSize(deque, 0);
    }

    private static void failureToAdd9() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        checkSize(deque, 2);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(10);
        checkSize(deque, 8);
    }

    private static void testIterator() {
        Deque<String> stringDeque = new Deque<String>();
        Iterator<String> iter = stringDeque.iterator();
        try {
            iter.remove();
            System.out.println("Failure!");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("Success in throwing UnsupportedOperationException");
        }
        try {
            iter.next();
            System.out.println("Failure!");
        }
        catch (NoSuchElementException  e) {
            System.out.println("Success in throwing NoSuchElementException");
        }
        stringDeque = new Deque<String>();
        try {
            stringDeque.addFirst("xyz");
            stringDeque.iterator().remove();
            stringDeque.iterator().remove();
            System.out.println("Failure!, 1 add and 2 removed, should throw exception--UnsupportedOperationException!");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("Success, 1 add and 2 removed, should throw exception--UnsupportedOperationException!");
        }

        stringDeque = new Deque<String>();
        stringDeque.addFirst("4");
        stringDeque.addFirst("3");
        stringDeque.addFirst("2");
        stringDeque.addFirst("1");
        iter = stringDeque.iterator();
        iter.remove();
        System.out.printf("With one element removed using Iterator, expected size: 2 %b\n",
                          stringDeque.size() == 3);
        iter.remove();
        System.out.printf("With two element removed using Iterator, expected size: 1 %b\n",
                          stringDeque.size() == 2);
    }

    private static void testEmptyRemoveFailure() {
        Deque<String> stringDeque = new Deque<String>();

        try {
            stringDeque.removeLast();
        }
        catch (NoSuchElementException e) {
            System.out.println("With No element Deque success!");
        }
    }

    private static void testAdditionAndDeletion() {
        Deque<String> stringDeque = new Deque<String>();
        System.out.printf("Empty Deque %b\n", stringDeque.size() == 0);
        stringDeque.addFirst("3");
        System.out.printf("With one element Deque %b\n", stringDeque.size() == 1);
        stringDeque.addFirst("2");
        System.out.printf("With two element Deque %b\n", stringDeque.size() == 2);
        stringDeque.addFirst("1");
        System.out.println(stringDeque.size());
        stringDeque.addLast("4");
        System.out.println(stringDeque.size());
        stringDeque.addLast("5");
        String item = stringDeque.removeFirst();
        System.out.printf("removeFirst %s - should be 1 %b\n", item, item.equals("1"));
        item = stringDeque.removeLast();
        System.out.printf("removeLast %s should be 5 %b\n", item, item.equalsIgnoreCase("5"));
        System.out.printf("Current size should be 3 %b\n", stringDeque.size() == 3);
        stringDeque = new Deque<String>();
        stringDeque.addFirst("3");
        stringDeque.addFirst("3");
        stringDeque.addLast("3");
        for (String str : stringDeque) {
            System.out.printf("Current iterator element should be 3 %b\n", str);
        }
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null not supported");
        if (items.length == size() || start == 0) {
            tripleTheArray();
        }
        items[--start] = item;
    }

    private void tripleTheArray() {
        int newSize = items.length * 2;
        int oldSize = size();
        Item[] newItems = (Item[]) new Object[newSize];
        int newStart = (newSize - items.length)/2;
        if (!isEmpty()) {
            for (int i = start; i < end; i++) {
                newItems[newStart + i] = items[i];
            }
        }
        start = newStart+start;
        end = start + oldSize;
        items = newItems;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return start == end;
    }

    // return the number of items on the deque
    public int size() {
        return end - start;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null not supported");
        if (items.length == size() || end == (items.length -1)) {
            tripleTheArray();
        }
        items[end++] = item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("No elements");
        Item item = items[start];
        items[start] = null;
        start++;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("No elements");
        Item item = items[end - 1];
        items[end - 1] = null;
        end--;
        return item;
    }

    // remove and return the item from the back
    private void removeNth(int i) {
        // Remove from middle towards the end
        if (i > size() / 2) {
            int oldEnd = end;
            end = i + 1;
            removeLast();
            while (end < oldEnd - 1) {
                Item nextElement = items[++end];
                items[end] = nextElement;
            }
            items[oldEnd] = null;
        }
        else {
            int oldStart = start;
            start = i;
            removeFirst();
            while (start > oldStart) {
                Item priorElementElement = items[start - 2];
                items[start - 1] = priorElementElement;
                start--;
            }
            items[oldStart] = null;
        }
    }

    public String toString() {
        return "Deque{" +
                "items=" + Arrays.toString(items) +
                ", start=" + start +
                ", end=" + end +
                ", size=" + size() +
                '}';
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int iteratorStart = start;
            private int iteratorEnd = end;

            public boolean hasNext() {
                return iteratorStart != iteratorEnd;
            }

            public Item next() {
                if (iteratorStart == iteratorEnd)
                    throw new NoSuchElementException("Empty!");
                return items[iteratorStart++];
            }

            public void remove() {
                if (iteratorStart == end) {
                    throw new UnsupportedOperationException("Can't delete without adding!");
                }
                else if (iteratorStart == start) {
                    iteratorStart++;
                    removeFirst();
                }
                else if (iteratorStart + 1 == end) {
                    iteratorEnd--;
                    removeLast();
                }
                else {
                    Deque.this.removeNth(start - iteratorStart);
                }
            }
        };
    }
}
