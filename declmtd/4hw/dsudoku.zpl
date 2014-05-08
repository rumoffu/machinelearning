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

# each digit appears once per block (n by n blocks for each of d digits)
subto block: forall <bh, bv, d> in B*D do
  sum <a, b> in B : x[(bh-1)*n+a, (bv-1)*n+b, d] == 1;

# Some of the digits are given.  Read these from file sudoku.txt and
# further constrain the solution to match these.

#set Givens := { read "sudoku.txt" as "<1n,2n,3n>" comment "#" };
#subto givens: forall <r,c,d> in Givens: x[r,c,d]==1;
#

#subto rotsymm: forall <r, c, d> in R*C*D do
#  x[r, c, d] == x[dim - r + 1, dim - c + 1, d];

var offmatch integer;
#var squaresdifferent integer;

minimize offmatch: offmatch;
subto rotsymm: 
  #offmatch == sum <r, c, d> in R*C*D : vabs(x[r, c, d] - x[dim - r + 1, dim - c + 1, d]);
  offmatch == (sum <r, c, d> in R*C*D : vabs(x[r, c, d] - x[dim - r + 1, dim - c + 1, d])) / 2;
#subto diff: squaresdifferent == offmatch / 2;
  # divide by 2 to account for double counting:
  # for example, if the top left square is a 1, but the bottom right square is a 9,
  # it counts 1 mistake for digit 1, and 1 mistake for digit 9
  # and then it repeats this process for the bottom right square being a 9 and 
  # the top left square being a 1




