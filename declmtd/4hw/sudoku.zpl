# Constraints for n^2 x n^2 Sudoku puzzles.

param n := 3;
param dim := n*n;
set R := {1..dim};  # row indices
set C := {1..dim};  # col indices
set D := {1..dim};  # possible digits
set B := {1..n}*{1..n};   # block-internal indices
var x[R*C*D] binary;  # does digit D appear at coordinates (R,C)?

# There are constraints, but nothing to maximize or minimize.

# exactly one digit per cell
subto uniq:  forall <r, c> in R*C do sum <d> in D : x[r,c,d] == 1;  

# each digit appears once per row
subto row:   forall <c, d> in C*D do sum <r> in R : x[r,c,d] == 1;  

# each digit appears once per column
subto col:   forall <r, d> in R*D do sum <c> in C : x[r,c,d] == 1;

# each digit appears once per block
subto block: forall <bh, bv, d> in B*D do
  sum <a, b> in B : x[(bh-1)*n+a, (bv-1)*n+b, d] == 1;

# Some of the digits are given.  Read these from file sudoku.txt and
# further constrain the solution to match these.

set Givens := { read "sudoku.txt" as "<1n,2n,3n>" comment "#" };
subto givens: forall <r,c,d> in Givens: x[r,c,d]==1;
