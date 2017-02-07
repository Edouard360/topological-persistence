# topological-persistence

Implementation of an algorithm to compute persistence homology as part of the INF563 Topological Data Analysis course.

By **Edouard Mehlman** and **Kevin Riera**.

Please check out the [github version of our repository][link] for a tidier display of this README.md file !

[link]: <https://github.com/Edouard360/topological-persistence>

 - [Code structure](#code-structure)
 - [Answers](#answers)

## Code structure 

#### Directories
The structure of the directories should be kept as it is in our project:

 - src/
 	- *.java (all the .java files are in the src folder)
 - tests/
 	- generated-filtrations/
 	  - assets/
 	  - simplices/
 	  - results/
 	  	 - barcodes/
 	  	 - runtime/
 	  
 	- provided-filtrations/
 	  - results/
 	  	 - barcodes/
 	  	 - runtime/

In particular, the root of the project, which we obtain in Java doing `System.getProperty("user.dir")`,  should contain this `src/` and `tests/` folders.

The filtrations A to D should be put in the `tests/provided-filtrations/` folder (we didn't upload them here for the project to be lighter).

The manually generated filtrations can be found at `tests/generated-filtrations/`.

Running the `printBarcode` function in the `main` of the `MainFiltration` class outputs the result in the `results/barcodes/` folder - which therefore, should exist !

#### Testing another filtration

In case you want to **test the program on a new filtration**, put a *filtrationName.txt* file in the `tests/generated-filtrations/` folder and run, in the the `main` of the `MainFiltration` class:

`printBarcode("filtrationName.txt", GENERATED_FILTRATIONS_PATH, null);`

 The result will be found at:

 `tests/generated-filtrations/results/barcodes/filtrationName.txt`


## Answers 
Questions Q2, Q3, Q6 & Q8 are written by hand, and scanned in PDF in the `/answers` folder.
### Q1 
The `Simplex` class implements the `Comparable` interface, and has a method `derivate` that returns an `ArrayList<TreeSet<Integer>>` representing the corresponding sum of simplices.
The Filtration class, initialized with a `Vector <Simplex>` sorts that vector, and initializes an attribute, named *order*, for every simplex.
This *order* is actually the index of the simplex in the matrix representation.
The Filtration class has a `getSparseRepresentation` function. For every simplex, it computes its derivative and try to match:

 - An `ArrayList<TreeSet<Integer>>`  that represent the derivative, with:
 -  A `TreeSet<Integer>` that represent the indices of the corresponding simplices in the filtration

This is done thanks to a HashMap mapping the `TreeSet<Integer>` that represent a simplex to the `Integer` that represent the index of the corresponding simplex.

`TreeSet<Integer>` therefore represents one column in the matrix representation.

### Q2 & Q3
In the `/answers` folder are the *Q2.pdf* and *Q3.pdf* files for the answers to the questions regarding complexity.

### Q4
The corresponding function is called `printBarcode`.
It's a static function from the `MainFiltration` class.
It takes as input a `filtrationName`, and a `filtrationPath`, and output the result in `/result/barcodes/filtrationName`.
Therefore, there should be a folder `/result` and a folder `/barcodes` within `/result` for the barcode to be printed in a file (the name will be the same as in the original filtration).

### Q5
There is a `GenerateFiltration` class that has a main, that you need to run to print a filtration for the *d-sphere* and the *d-ball*.
These filtrations will be written in the folder `/tests/generated-filtrations/` (which must therefore exists).
As for the *klein-bottle*, the *mobius-strip*, the *projective-plane*, and the *tore*, you should run the `createFiltrations` function (in the `MainFiltration` class) that automatically converts hand-written simplicial complexes into filtrations.
The original triangulations are in the `/generated-filtrations/simplices/` folder and the corresponding *.png* images from which they originate are in the `/generated-filtrations/assets/` folder.

### Q6
The barcodes of the filtration can be found in the fodler `/generated-filtrations/barcodes/` after running the main function of the `MainFiltration` class, and having uncommented the:
`//printBarcodes(GENERATED_FILTRATIONS,GENERATED_FILTRATIONS_PATH);`
In the `/answers` folder is the *Q6.pdf* file for the interpretations of the barcodes.

### Q7 
Statistics for runtime are *independently* printed to files `/tests/provided-filtrations/result/runtime/filtrationName.txt`.
Also, in the `/tests/provided-filtrations/result/runtime/all_stats.txt`, we can find the grouped statistics for filtrations A to D.
We order filtrations by increasing size.
To get the sparse representation of the matrix, we have:
|Sparse representation|Time (ms)|Average (ms)|T/(n^2)|T/(n^3)|
|---|:---:|:---:|:---:|:---:|
|f_B.txt (108161)|4.18E2|3.86E-3|3.57E-8|3.30E-13|
|f_C.txt (180347)|2.22E3|1.23E-2|6.82E-8|3.78E-13|
|f_A.txt (428643)|2.49E3|5.81E-3|1.36E-8|3.16E-14|
|f_D.txt (2716431)|1.32E5|4.85E-2|1.79E-8|6.57E-15|
To perform the reduction operation, we have:
|Reducing Matrix|Time (ms)|Average (ms)|T/(n^2)|T/(n^3)|
|---|:---:|:---:|:---:|:---:|
|f_B.txt (108161)|5.64E2|5.21E-3|4.82E-8|4.46E-13|
|f_C.txt (180347)|1.55E3|8.62E-3|4.78E-8|2.65E-13|
|f_A.txt (428643)|6.50E3|1.52E-2|3.53E-8|8.25E-14|
|f_D.txt (2716431)|2.52E4|9.27E-3|3.41E-9|1.26E-15|

To analyse the complexity of the algorithm, we look at `T/n`, `T/(n^2)`, `T/(n^3)`, where `T` is the running time of the algorithm and `n` is the size of the filtration.
The column where the quotient stays constant gives the complexity of the algorithm. If the quotient shrinks as n grows, then the algorithm is faster than the announced complexity.

For getting **the sparse representation**, `T/(n^3)` shrinks as `n` grows, so the algorithm is better than O(n<sup>3</sup>). Yet `Average` grows as`n` grows, so the algorithm is not O(n). `T/(n^2)` remains pretty constant, so the algorithm is O(n<sup>2</sup>).

For the **reduction of the matrix**, `T/(n^2)` and `T/(n^3)` shrink as well as `n` grows, so the algorithm is better than O(n<sup>2</sup>). Yet `Average` slightly grows as `n` grows, so the algorithm is not O(n).

In theory our **Reducing Matrix algorithm** would be, using the HashMaps as we did, at worse O(n<sup>2</sup>). In practice, it is a bit less probably because in average there are O(1) columns to substract. 

### Q8

In the `/answers` folder is the *Q8.pdf* file for the inference of the topological structure.


[Link to repository][Repo]


   [Repo]: <https://github.com/Edouard360/topological-persistence>
  