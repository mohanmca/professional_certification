## [Algorithms, Part I](https://www.coursera.org/learn/algorithms-part1/)

* Text-book - Algorithms (4th Edition) (Robert Sedgewick, Kevin Wayne)
* [Book site](https://algs4.cs.princeton.edu/home/)
* [Sorting algorithms](https://algs4.cs.princeton.edu/20sorting/)
*  To maximize learning
   * Solve problems
   * Studies the available resources
   * Engages in the discussion forums


## Assignments

* [Local path](C:\git\algorithms\percolation)
* [Percolation-Assignment](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)
* [Queue-Assignment](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php)
* [Collinear-Assignment](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)
* [8puzzle-Assignment](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)
* [kd-Trees-Assignment](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)
* [Local Tex](D:/Apps/texlive/2020 or D:\Apps\pandoc-2.10.1\mitex\miktex\bin\x64)
* [Windows LatexSetup](https://www.youtube.com/watch?v=fLP0QVFaeAU)
* [Mohan's Anki Decks](Mohan::Algorithm::GeeksForGeeks)

## Notes

```java
for(int i=0;i<n;i++){
   for(int j=i+1;j<n;i++){
      for(int k=1;k<n;k=k*2){
         //
      }      
   }
}
```
  * Not all triple loops have cubic running times. For a given value of i and j, the k-loop requires only 3 lg n array access. As in the 2-SUM and 3-SUM analysis, the number of times the k-loop is executed is {n \choose 2}. 
  * Above is 3/2n^2(lg n)
* We can always use an explicit stack to remove recursion.


## Power law
* Plot running time T (N) vs. input size N using log-log scale.
* T(N) = a*N^b, where a = 2^c
* lg(T (N)) = b lg N + c
* Use of power law, It can help to plot Quadradic and Exponetial scale algorithms in st.line using log-log scale


# Java memory usage for objects

* Every object - 16 bytes (8 * times something, hence may be required)
* Padding - 4 bytes
* String uses additional 64bytes compared to equivalent char[]
* [java primitive data-types](https://www.geeksforgeeks.org/data-types-in-java/)



## Algorithm growth

* Growth-rate function or common order of growth classifications
* 1 or Constant, lg N, N, n lg n, n^2, n^2 log n, n^3, 2^n, n!, n^n
* 2^n
  * Combinatorial search or check all subsets or exhaustive search

## Repeated errors

* Multiple if's without propber else would create issue, especially OBO and insufficient else - would make it complext
* if > is covered in one branch, ensure <= is covered in another branch

## Latex extension
* Latex preview

## Reference
* [3-Sum problem](https://en.wikipedia.org/wiki/3SUM)
* [OpenJdk Java-7 code](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/lang/String.java)
* [Java-11 code](https://github.com/AdoptOpenJDK/openjdk-jdk11/blob/master/src/java.base/share/classes/java/lang/String.java)
* [Java data-types](http://orion.towson.edu/~izimand/237/LectureNotes/236-Lecture-DataTypes.htm)
* [Visual algorithm comparision](https://www.toptal.com/developers/sorting-algorithms)

