# [kwong23@ugradx 4hw]$ rlwrap scip
# SCIP> read gknapsack.zpl
# SCIP> opt
# SCIP> display solution

# A basic knapsack solver.
# 
# 
# The knapsack.txt file should have lines of the form 
# "item,value,weight,radioactivity" -- like this:
#     diamond,3,9999,0
#     soup,50,5,1
#     ...
# (The radioactivity field is not used below but
# will be used in a later part of the problem.)

# -----------

# Read in the data.  1s means "the string in the first column,"
# 2n means "the number in the second column," etc.

set I := { read "knapsack.txt" as "<1s>" };           # set of item names    
param value[I] := read "knapsack.txt" as "<1s> 2n";   # item names and values
param weight[I] := read "knapsack.txt" as "<1s> 3n";  # item names and weights

# -----------

# Let's limit the capacity of our knapsack to 1/3 of the total weight
# of all items.  That means the solver will have to make some hard choices.

param maxweight := (sum <i> in I: weight[i]) / 3;  

# -----------

# Now let's set up the problem.  The goal is to select some subset
# of the items I that has maximum total value, subject to the constraint
# that they must fit in the knapsack.

var take[I] binary; #tracks which items are taken
#var take[I] <= 1; #tracks which items are taken
var count integer;
var totalvalue real;
var spareweight real;
var takenweight real;

maximize totalvalue:   totalvalue - 9000*count; 
subto count:           count == sum<i> in I: take[i];
subto value:   totalvalue == sum<i> in I: take[i]*value[i]; 
subto takenweight:       takenweight == sum<i> in I: take[i]*weight[i];
subto maxweight:       takenweight <= maxweight;
subto spareweight:     spareweight == maxweight - takenweight
