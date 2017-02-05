# topological-persistence

Implementation of an algorithm to compute persistence homology.

 - [Code structure](#code-structure)
 - [Answers](#answers)

## Code structure 

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

### Q2 & Q3 - W
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

### Q6 - W
### Q7 
Timings prove a complexity under O(n<sup>3</sup>)
### Q8 - W


[Link to repository] [Repo]


   [Repo]: <https://github.com/Edouard360/topological-persistence>
  