* 1970 - S Cook proved theoretical about particular abstract machine for substring
*  D. E. Knuth and V. R. Pratt - Proved Cook Theorem - KMP
* J. H. Morris had discovered same about what Knuth Pratt discovered
* R. S. Boyer and J. S. Moore  discovered better algorithm
* 1980, M. O. Rabin and R. M. Karp
* KMP - dfa[c][j] - contains the pattern position for character c at j-th value


## RE

* We can implement the modulus operation with an RE:
* For example, (0 | 1(01*0)*1)* describes all strings of 0s and 1s that are the binary representations of numbers that are multiples of three(!):
  * 11, 110, 1001, and 1100 are in the language
  * 1011101 - 93 /  101110011101 - 2973 (are divisible by 3)
  * But 10, 1011, and 10000 are not
* RE can't be used to balance parantheses or if we have equal number of As and Bs

## Compression techniques

* The four methods that we consider exploit, in turn, the following structural characteristics:
  * Small alphabets
    * Genomic data requires just 4 alphabets
  * Long sequences of identical bits/characters
  * Frequently used characters
  * Long reused bit/character sequences

* 7-bit ASCII are prefix-free
* Huffman ecoding requires prefix-free code (and Trie kind of convention to encode)


## B-Tree

* 