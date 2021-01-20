# Some notes from Sedgewick algorithm book

## Sort algorithm notes

* Selection sort
  * Insensitive to input
  * For i => N-1-i compare, oveall N^^2/2 comparision
  * N exchanges (least among known sort algorithms)
* Insertion sort
  * Sensitive to input (if already sorted, this is fastest)
  * Slowest for reverse sorted
  * Works well for partially sorted array (If the number of inversions in an array is less than a constant multiple of the array size)
  * N^2/N^2 (worst-case compare and exchange), N^4/N^4 (average-case compare and exchange)
  

## Follow-up questions

1. find where is largeT.txt and largeW.txt
2. find % modulo operation for -ve numbers
3. Why numbers starts in 0 index
4. Integer.toBinaryString(N)
5. Can you assign void to variable (if void is returned part of method call)
  * Not possible

## Red-black tree

* We refer to BSTs that represent 2-3 trees in following way as red-black BSTs.
* Height of 2-3 node trees are considerably smaller than BST, and inserting ascending order wouldn't create link list like tree in Red-Black-Tree
* Red-black BSTs is to encode 2-3 trees by starting with standard BSTs
* Red links, which bind together two 2-nodes to represent 3-nodes,
  * We represent 3-nodes as two 2-nodes connected by a single red link 
  * Red-link leans left (one of the 2-nodes is the left child of the other).
* Black links, which bind together the 2-3 tree. 
* Red-black tree allows us to use our get() code for standard BST search without modification. 
* Given any 2-3 tree, we can derive a corresponding BST, just by converting each node as specified.

## Hashing

* To convert integer into hashcode, ignore the sign-bit and convert into 31 bit integer
  * (any_integer & 0x7fffffff) % m
  * Math.abs() returns a negative result for the largest negative number, hence we use above technique, and % can be negative too (hence mast sign bit)
  * s.hashCode() is −2^   31 for the Java String value "polygenelubricants". 
* Above turns 32 bit into positive 31 bit integer
* hashCode can be cached instead of computing.
* How to hash floatingpoint numbers (it is buggy)
  * If the keys are real numbers between 0 and 1, just multiply by M and round off to the nearest integer to get an index between 0 and M − 1. 
  * Although this approach is intuitive, it is defective because it gives more weight to the most significant bits of the keys; 
  * The least significant bits play no role. One way to address this situation is to use modular hashing on the binary representation of the key (this is what Java does).
* In separate-chaining hash table with M lists and N keys, the probability that the number of keys in a list is within a small constant factor of N/M is extremely close to 1.
  * Binomial distribution, try to find if the average length of the lists is 10, what is the probability that we will hash to some list with more than 20 keys on it
    * It is less than (10 e/2)2e−10

## Specification problem? how to specify program without ambiguity
* Easier to add methods, but exponentially complex to remove methods (without breaking backward compatibility)
* Note that we can use == to tell us whether two objects of type Class are equal because getClass() is guaranteed to return the same reference for all objects in any given class.
* assert index >= 0 : "Negative index in method X"; (assertion with message)