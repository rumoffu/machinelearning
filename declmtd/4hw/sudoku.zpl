# Constraints for n^2 x n^2 Sudoku puzzles.

param n := 3;
param dim := n*n;
set R := {1..dim};  # row indices
set C := {1..dim};  # col indices
set D := {1..dim};  # possible digits
set B := {1..n}*{1..n};   # block-internal indices
var x[R*C*D] binary;  # does digit D appear at coordinates (R,C)?

# There are constraints, but nothing to maximize or minimize.

subto uniq:  FILL THIS IN   # exactly one digit per cell
subto row:   FILL THIS IN   # each digit appears once per row
subto col:   FILL THIS IN   # each digit appears once per column
subto block: FILL THIS IN   # each digit appears once per block

# Some of the digits are given.  Read these from file sudoku.txt and
# further constrain the solution to match these.

set Givens := { read "sudoku.txt" as "<1n,2n,3n>" comment "#" };
subto givens: forall <r,c,d> in Givens: x[r,c,d]==1;
