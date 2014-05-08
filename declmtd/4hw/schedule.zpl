# Constraints for optimizing a week.

set Day := {1..7}; # part a

param base_fun_rate := 1; # part d
# part b decisions
var work[Day];
var sleep[Day];
var play[Day];
var events[Day];

# part d events
set E := { read "events.txt" as "<1s>" }; # set of events
param edays[E] := read "events.txt" as "<1s> 2n"; # event names and day
param ehours[E] := read "events.txt" as "<1s> 3n"; # event names and hours used
param efunrate[E] := read "events.txt" as "<1s> 4n"; # event names and funrates

var funevents[Day];
subto funevents: forall <d> in edays : funevents[d] ==  


var totalfun integer;
var workpenalty integer;
var workloss[Day];

subto daylimit: forall <d> in Day : work[d] + sleep[d] + play[d] + events[d] == 24; # part a
subto minsleep: forall <d> in Day without {1, 2} : sleep[d-2] + sleep[d-1] + sleep[d] >= 18; # part c
subto workpenalty: 
subto totfun: forall <d> in Day : totalfun == base_fun_rate*play[d] + funevents[d] - workpenalty;

maximize fun: totfun;

# Read in the data.  1s means "the string in the first column,"
# 2n means "the number in the second column," etc.

set I := { read "knapsack.txt" as "<1s>" };           # set of item names    
param value[I] := read "knapsack.txt" as "<1s> 2n";   # item names and values
param weight[I] := read "knapsack.txt" as "<1s> 3n";  # item names and weights
param radio[I] := read "knapsack.txt" as "<1s> 4n"; # item names and radioactivity

# -----------

# Let's limit the capacity of our knapsack to 1/3 of the total weight
# of all items.  That means the solver will have to make some hard choices.

param maxweight := (sum <i> in I: weight[i]) / 3;
param maxradio := 20;

# -----------

# Now let's set up the problem.  The goal is to select some subset
# of the items I that has maximum total value, subject to the constraint
# that they must fit in the knapsack.

var take[I] binary; #tracks which items are taken
#var take[I] <= 1; #tracks which items are taken, allows fractions
var count integer;

maximize totalvalue:   totalvalue;
subto maximumweight:   maximumweight == (sum<i> in I: weight[i]) / 3;


#param events[Day] := read "events.txt" as # event name, 

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
  offmatch == sum <r, c, d> in R*C*D : vabs(x[r, c, d] - x[dim - r + 1, dim - c + 1, d]);
subto no_permute_digits: forall <c> in C : x[1, c, c] == 1;
  #offmatch == (sum <r, c, d> in R*C*D : vabs(x[r, c, d] - x[dim - r + 1, dim - c + 1, d])) / 2;
#subto diff: squaresdifferent == offmatch / 2;
  # divide by 2 to account for double counting:
  # for example, if the top left square is a 1, but the bottom right square is a 9,
  # it counts 1 mistake for digit 1, and 1 mistake for digit 9
  # and then it repeats this process for the bottom right square being a 9 and 
  # the top left square being a 1




