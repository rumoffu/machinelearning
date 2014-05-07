Kyle Wong
kwong23@jhu.edu
Declarative Methods HW 4
14.5.5

Problem 1 - ZIMPL Introduction 
=====================================================================================

- using transport4.zpl as

$ scip -f transport4.zpl

Results:

SCIP> di so

objective value:                                 6600
send$alice#1                                      100   (obj:10)
send$alice#2                                      100   (obj:8)
send$alice#3                                      300   (obj:5)
send$bob#2                                        300   (obj:5)
send$carol#1                                      100   (obj:11)
send$carol#4                                      100   (obj:7)


a) What is the minimum transportation cost that can be achieved?

The minimum transportation cost is 6600


b) In this optimal solution, which producer is not operating at full capacity?

Carol is the producer that is not operating at full capacity: she is sending only
200 out of 400.


c) Which consumers are using multiple suppliers to meet their demand?

Consumers 1 and 2 are using multiple suppliers to meet their demand.


d) What is the most expensive (cost per unit) transportation route that is actually
getting used?

The most expensive transportation route actually being used is Carol sending to
consumer 1 with a cost per unit of 11.


e) How many iterations (i.e., iterations of the simplex method) did the LP solver 
need to solve this problem?

LP                 :       Time      Calls Iterations  Iter/call   Iter/sec
  primal LP        :       0.00          0          0       0.00          -
  dual LP          :       0.00          1          7       7.00          -

It took 7 iterations of simplex.


f) The reduced domains indicate that Alice will send at most 400 units to 
consumer 2, while Bob will send at most 300 units.  Explain how these limits can 
be easily deduced from the problem in transport4.zpl.

Within the following two lines, the limits can be deduced:

param supply[Producer] := <"alice"> 500, <"bob"> 300, <"carol"> 400;
param demand[Consumer] := <1> 200, <2> 400, <3> 300, <4> 100;

Since <2> has a limit of 400 units, and Alice has a sending limit of 500 units, 
then the overall limit will be that Alice could send 400 units to <2>.
Similarly, since <2> has a limit of 400 units, but Bob has a sending limit of 300
units, then the overall limit will be that Bob can send at most 300 units to <2>.


g) What happens if you allow negative values?  By default, variables are
constrained to be >= 0.  Try modifying the line
var send[Producer*Consumer];
to
var send[Producer*Consumer] >= -10000;

Results:

SCIP> display sol

objective value:                               -83400
send$alice#1                                   -10000   (obj:10)
send$alice#2                                      200   (obj:8)
send$alice#3                                    20300   (obj:5)
send$alice#4                                   -10000   (obj:9)
send$bob#1                                     -10000   (obj:7)
send$bob#2                                      10200   (obj:5)
send$bob#3                                     -10000   (obj:5)
send$bob#4                                      10100   (obj:3)
send$carol#1                                    20200   (obj:11)
send$carol#2                                   -10000   (obj:10)
send$carol#3                                   -10000   (obj:8)

When negative values are allowed, the solver will be able to use unrealistic 
negative values to minimize its cost objective.
This means that the solver will be likely to use negative values since adding a
negative value will add a negative cost which reduces the objective function.
The solution found in this case will not be correct as real producers cannot
send negative amounts of produce to consumers.



Problem 2 - Simple ZIMPL Programming - KNAPSACK problem
=====================================================================================
a) Complete the given ZIMPL program.

See aknapsack.zpl

-- it can be run with 
[kwong23@ugradx 4hw]$ scip -f aknapsack.zpl


b) How fast under SCIP does it solve the 10,000-item problem in knapsack.txt?
It took 1.14 seconds on ugradx.


c) You can use display solution to see the value of the objective function, 
followed by the very long optimal assignment.  What does the objective function
represent?  What does the optimal assignment represent?

The objective function represents the total value of all the items taken into
the knapsack.  The optimal assignment represents which items should be put
into the knapsack in order to get the greatest total value for the items that
can fit together inside the knapsack given the weight constraints.


d) Save the solution you just found by typing write solution sol1. 
Now change the declaration
var take[I] binary;
to
var take[I] <= 1;

Solve this changed problem, which is a pure LP problem with no integrality
requirements, and do write solution sol2.

Now type
comm -3 sol1 sol2

Result:
[kwong23@ugradx 4hw]$ comm -3 sol1 sol2
objective value:                             17785767
  objective value:                     17785770.5842697
take$item0947                                       1   (obj:983)
  take$item3730                                       1   (obj:1627)
take$item6043                       0.999999999998561   (obj:756)
  take$item6456                       0.730337078653831   (obj:3365)
take$item9084                                       1   (obj:2342)

  i. Study the differences and describe in English what changed.  How is sol2
  packing the knapsack differently and why?

  The difference is that the second solution allows for taking fractional
  parts of items and to get their fraction of value and weight.  
  sol1 involved taking items 947, 6043, and 9084 as whole items.
  sol2 did not take those 3, but instead took item 3730, and 0.73 of item 6456.


  ii. How many of the 10,000 take variables turned out to be integers in sol2
  even though they weren't required to be?

  Only 1 of the 10,000 take variables was not an integer.  The other 9,999 remained
  as integers.  (Only 3694 variables were 1's).


  iii. Can you give an intuition as to why this is? (Hint: What would you expect about
  the value-to-weight ratio of the taken items in the non-integer case?)

  In the non-integer case, the value-to-weight ratio of the taken items should be
  higher than in the integer case.  This is because allowing items to be taken
  fractionally allowed a more valuable-per-weight item to be taken when otherwise
  its weight would have prohibited its addition to the knapsack.  
  There is only 1 fractional item because its fractional weight alone fills up
  the remaining space in the knapsack.  Since this is the best remaining
  value-to-weight ratio item, it is the only item that should be put in 
  fractionally to the knapsack to maximize the value when fractional items 
  are allowed.


  iv. How would you expect this property of sol2 to affect the efficiency of sol1?
  
  This means sol2 will have a higher value-to-weight efficiency than sol1, and
  since it can allow fractional items, sol2 runs faster because it simply can add
  items of the greater value-to-weight ratio and just keep going until it needs 
  to add fractions of the last item to fill the rest of the knapsack to the fullest.


e) Find how many items are in the optimally packed knapsack, their total value,
and how much room is left in the knapsack.  So introduce variables:
count, totalvalue, and spareweight, and constrain them to answer those questions.

Now solve the problem again.  What are the values of those three variables?
To find out the values use:
display value count
display value totalvalue
display value spareweight

see eknapsack.zpl

count                                            3695   (obj:0)
totalvalue                                   17785767   (obj:1)
maximumweight                                  251360   (obj:0)
takenweight                                    251360   (obj:0)
spareweight                      2.91038304567337e-11   (obj:0)


f) Does the solver do the same thing every time?  
Were the solver statistics the same as last time? Why or why not?
Were count, totalvalue, and spareweight the same? Why or why not?

Repeating:
SCIP> read eknapsack.zpl
SCIP> opt
SCIP> di statistics

or using 
[kwong23@ugradx 4hw]$ scip -f eknapsack.zpl
repeatedly seems to return the same values every time.  

The solver statistics are also the same.  This is because scip is deterministic
and so it will run the same method to solve the same problem each time it is run.
Thus, it will reach the same optimum. However, there is some minor variation on the
run time which is due to the speed of the machine which fluctuates depending
on other processes that the computer machine is running.

So count, totalvalue, and spareweight are also the same every time.
This is similarly because scip is deterministic and so it will reach the same optimum
every time it is run.


g) It will be easier to unpack the knapsack if it contains fewer items. Change
the linear objective function so that it encourages a large totalvalue (as before)
but also encourages a small count.

What is your new objective function?  How does this affect the values of count, 
totalvalue, and spareweight in the optimal solution?  Why?

Please see gknapsack.zpl

The new objective function is:

maximize totalvalue:   totalvalue - 9500*count;
subto value:   totalvalue == sum<i> in I: take[i]*value[i];

SCIP> read gknapsack.zpl opt disp val count di val totalvalue di val spareweight

Result:

count                                              17   (obj:-9500)
totalvalue                                     163666   (obj:1)
maximumweight                                  251360   (obj:0)
takenweight                                      1246   (obj:0)
spareweight                                    250114   (obj:0)

This new function reduces the value of count and totalvalue.  It increases
the value of spareweight.  This is because I put a high cost on the number
of items (over 9000) so that the number of items taken is less than 100 (I ran
and tested the program with several different cost values per item).
This solution thus has a lower count since adding more items decreases
the objective function.  Since this limits the total value, the total value
is decreased.  Since this constraint was made to be large, it overpowers
the weight constraint and therefore there is more spareweight as the knapsack
is not filled.  

h) Argue that the previous problem is actually just solving a different knapsack
problem.  That is, explain how your fancy revised problem that encourages fewer
items could be reduced to a plain old knapsack problem and solved with a plain
old knapsack solver.

The old knapsack solver actually already solves this new knapsack problem,
the only difference is that the solution is not just the value with the most
total value, but instead the selected solution is the highest value that also
has a small count value. The solver merely needs to solve the same old problem,
then it needs to consider the solution vertices that maximize the total value 
while still having a low overall count.


i) Let's place one additional constraint.  Some items are radioactive.  The total
radioactivity of the knapsack is <= 20.  Add this constraint to the ZIMPL program.

So how fast is SCIP on this problem?  How does the addition of the radioactivity
constraint change count, totalvalue, and spareweight? Why?

Result:

count                                            3544   (obj:0)
totalvalue                                   14775050   (obj:1)
takenweight                                    251360   (obj:0)
maximumweight                                  251360   (obj:0)
spareweight                      -5.82076609134674e-11  (obj:0)
radioactivity                        19.9926114693637   (obj:0)

SCIP takes 2.46 seconds.  The radioactivity constraint has lowered the value of
count and totalvalue.  Spareweight is still the same.  This is because the 
radioactivity constraint is restricting the combination of items that can be put
into the knapsack.  Therefore, fewer items are put into the knapsack.  Still,
the optimal solution involves maximizing the weight of items in the knapsack,
so spareweight stays the same (it is still 0).  Even though the knapsack weight
is maximized, the total value is lower because the best item combination has 
been ruled out by the radioactivity constraint.


j) Final program as knapsack.zpl


k) [extra credit] Study the solver output and try to figure out where the solver
is spending its time on this problem.  Check optimize, disp displaycols, disp stats,
and disp transproblem.  Comment on shape of the branch-and-bound tree, the total 
number of LP (simplex) iterations, the effective presolving and propagation
strategies, etc.

The effective presolving seems to start with some constraint elimination and bounds
propagation (6 deleted constraints, 8 tightened bounds), although it also wastes some
time using probing that turns out to be useless where there is no fixing or bounds found:

---------------------------------------------------------------------------------------------
original problem has 10006 variables (10000 bin, 1 int, 0 impl, 5 cont) and 8 constraints
SCIP> opt

presolving:
(round 1) 5 del vars, 6 del conss, 8 chg bounds, 0 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 0 clqs
   (0.2s) probing: 101/10000 (1.0%) - 0 fixings, 0 aggregations, 0 implications, 0 bound changes
   (0.2s) probing aborted: 100/100 successive totally useless probings
(round 2) 6 del vars, 6 del conss, 8 chg bounds, 1 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 0 clqs
(round 3) 13 del vars, 6 del conss, 8 chg bounds, 1 chg sides, 0 chg coeffs, 1 upgd conss, 0 impls, 0 clqs
   (0.2s) probing: 111/10000 (1.1%) - 0 fixings, 0 aggregations, 0 implications, 0 bound changes
   (0.2s) probing aborted: 100/100 successive totally useless probings
presolving (4 rounds):
 13 deleted vars, 6 deleted constraints, 8 tightened bounds, 0 added holes, 1 changed sides, 0 changed coefficients
 0 implications, 0 cliques
presolved problem has 9993 variables (9993 bin, 0 int, 0 impl, 0 cont) and 2 constraints
      1 constraints of type <knapsack>
      1 constraints of type <linear>
transformed objective value is always integral (scale: 1)
Presolving Time: 0.19
---------------------------------------------------------------------------------------------

Then, the second most effective pre-solving step seems to be using variable elimination
(where 7450 variables are fixed and are thus deleted):

---------------------------------------------------------------------------------------------
(run 1, node 1) restarting after 7450 global fixings of integer variables

(restart) converted 2 cuts from the global cut pool into linear constraints

presolving:
(round 1) 7450 del vars, 0 del conss, 0 chg bounds, 1 chg sides, 0 chg coeffs, 0 upgd conss, 0 impls, 0 clqs
(round 2) 7450 del vars, 0 del conss, 0 chg bounds, 1 chg sides, 0 chg coeffs, 1 upgd conss, 0 impls, 0 clqs
presolving (3 rounds):
 7450 deleted vars, 0 deleted constraints, 0 tightened bounds, 0 added holes, 1 changed sides, 0 changed coefficients
 0 implications, 0 cliques
presolved problem has 2543 variables (2543 bin, 0 int, 0 impl, 0 cont) and 4 constraints
      1 constraints of type <knapsack>
      2 constraints of type <linear>
      1 constraints of type <logicor>
transformed objective value is always integral (scale: 1)
Presolving Time: 0.24

---------------------------------------------------------------------------------------------


In terms of the branch-and-bound tree and LP (simplex) iterations,
it seems like the program spends most of its time doing simplex on the dual problem (making
466 calls and 652 iterations of the dual LP problem).  SCIP also invests time in 
strong branching in order to predict dual bounds on the children nodes to minimize
the size of the branch-and-bound tree and to reduce the average number of LP iterations.

The branch and bound tree itself has 500 nodes and has depth 40 so it is not a fully
balanced tree.  Thus, there is a lot of backtracking and exploration of the tree.

---------------------------------------------------------------------------------------------
LP                 :       Time      Calls Iterations  Iter/call   Iter/sec
  primal LP        :       0.01          0          0       0.00          -
  dual LP          :       0.44        466        652       1.40    1481.82
  lex dual LP      :       0.00          0          0       0.00          -
  barrier LP       :       0.00          0          0       0.00          -
  diving/probing LP:       0.19        201        221       1.10    1163.16
  strong branching :       0.17        149        491       3.30    2888.24
    (at root node) :          -          5         17       3.40          -
  conflict analysis:       0.00          0          0       0.00          -
B&B Tree           :
  number of runs   :          2
  nodes            :        500
  nodes (total)    :        501
  nodes left       :          0
  max depth        :         40
  max depth (total):         40
  backtracks       :        113 (22.6%)
  delayed cutoffs  :         40
  repropagations   :        118 (141 domain reductions, 40 cutoffs)
  avg switch length:       5.26

---------------------------------------------------------------------------------------------


Problem 3 - Planning Next Week with MILP
=====================================================================================

a) Plan for next week
set Day := {1..7};
and assume that each day<i> in Day consists of 24.0 hours.


b) For each day, decide how many hours to spend on work, sleep, and play 
(these do not need to be integers).


c) You need sleep.  In any 3-day period, at least 18 hours is required.
In your README, explain how you interpreted this constraint for 3-day periods
that are only partially contained within the week.


d) Maximize fun.
param base_fun_rate := 1;

Make a short file events.txt with one row for each special event.
Hint: Each special event has a string name, takes place on a particular day,
consumes a certain amount of play time, and provides you with a certain rate of Fun per hour

What would the solver do if an event's fun rate is lower than base_fun_rate?

It would eliminate this event as a possibility since it cannot possibly help maximize
the fun.  Thus, it will always choose regular play instead of the event.


e) You also have homework.  Make a short file assignments.txt:
each assignment has a string name, is due on a particular day, requires a particular
number of hours to complete, and has a particular penalty rate.

Suppose some parts of the assignment are worth more than others.  Can you still
encode this situation using the approach above, or do you need a more
complicated model?

In the real world, assignment parts would be linked.  However, in this simple
model, we can still use the same approach to give different parts of the
assignment more weight than others: simply break the assignment up
into different parts and have them due on the same day and give them
different penalties based on how much they are worth.


f) You are a less efficient worker when you get less sleep.
You have sleep deficit on day i if you had less than 24 hours of sleep
total over the 3 day period i-2, i-1, i.
(Again, in your README, explain how you interpreted this constraint for
3-day periods that are only partially contained within the week)
On days when you have a sleep deficit, your work is only 75% as efficient
as usual.  For example, it takes 8 hours to do 6 hours worth of work.
(The 75% number should be a named parameter)

Hand in your commented ZIMPL model schedule.zpl
along with data files events.txt and assignments.txt.

How to run the solver from the command line:

Solution found:


Discussion of solution found:



Problem 4 - n^2 by n^2 Sudoku solver
=====================================================================================

a) You can find an incomplete ZIMPL program in sudoku.zpl.  Finish it.
How long does SCIP take to solve the "very hard" problem in sudoku.txt?

To view the result use:
scip -f sudoku.zpl | ./sudoku-decode

See sudoku1.zpl for the finished code.

Result:

3 7 9 | 5 4 6 | 1 8 2 
4 1 8 | 3 9 2 | 5 6 7 
2 5 6 | 8 1 7 | 4 3 9 
------+-------+-------
1 4 2 | 6 3 5 | 9 7 8 
9 6 3 | 1 7 8 | 2 4 5 
5 8 7 | 9 2 4 | 6 1 3 
------+-------+-------
7 9 1 | 4 5 3 | 8 2 6 
8 2 5 | 7 6 1 | 3 9 4 
6 3 4 | 2 8 9 | 7 5 1 


SCIP takes 0.02 seconds to solve the "very hard" sudoku.txt problem.


b) Now let's have a little fun.  Copy your program to sudoku2.zpl.
The problem is infeasible (UNSAT).  In your README, give a simple argument
in English that demonstrates that no valid 9 x 9 sudoku can be 180 degrees
rotationally symmetric. (Hint: Think about the constraints being placed on the
central 3 x 3 block.)

added constraint:

subto rotsymm: forall <r, c, d> in R*C*D do 
  x[r, c, d] == x[dim - r + 1, dim - c + 1, d]; 

Result:

SCIP Status        : problem is solved [infeasible]
Solving Time       :       0.01

The central 3 x 3 block would have to be rotationally symmetric which means that 
its top left corner value would be the same as its bottom right corner value.
This would require the same digit to appear twice in the same 3 x 3 block.
Therefore, this is impossible since Sudoku requires a digit to appear
only once within the same 3 x 3 block.


c) The previous argument doesn't apply to the n = 4 case.

So it is possible to have a rotationally symmetric 16 x 16 sudoku?
If yes, include the decoded solution in your README.  If it is still UNSAT,
give a different argument in English that explains why.

How long did SCIP take to finish?

Yes it is possible to have a rotationally symmetric 16 x 16 sudoku.

15  1  6  2 |  7 10 16  8 | 13 14  4  9 | 11  5 12  3 
 5  9  8 13 |  1  6  4  3 |  7  2 12 11 | 15 16 14 10 
 4 10  3 14 | 12  5 11  2 | 16  1 15  6 |  9 13  7  8 
 7 16 11 12 | 14 13 15  9 |  5 10  3  8 |  6  2  4  1 
------------+-------------+-------------+-------------
11  2 10  8 | 13  9  5  4 | 14  3  7 15 |  1 12  6 16 
 6  5  9  3 | 16 14  7 12 |  1 13 11  4 | 10 15  8  2 
12 15  7  4 | 10  1  8 11 |  6  9 16  2 |  5 14  3 13 
14 13  1 16 |  3  2  6 15 | 10 12  8  5 |  7  4 11  9 
------------+-------------+-------------+-------------
 9 11  4  7 |  5  8 12 10 | 15  6  2  3 | 16  1 13 14 
13  3 14  5 |  2 16  9  6 | 11  8  1 10 |  4  7 15 12 
 2  8 15 10 |  4 11 13  1 | 12  7 14 16 |  3  9  5  6 
16  6 12  1 | 15  7  3 14 |  4  5  9 13 |  8 10  2 11 
------------+-------------+-------------+-------------
 1  4  2  6 |  8  3 10  5 |  9 15 13 14 | 12 11 16  7 
 8  7 13  9 |  6 15  1 16 |  2 11  5 12 | 14  3 10  4 
10 14 16 15 | 11 12  2  7 |  3  4  6  1 | 13  8  9  5 
 3 12  5 11 |  9  4 14 13 |  8 16 10  7 |  2  6  1 15 

See sudoku2.zpl for the solution.  SCIP took 27.58 seconds on ugradx to finish it.  


Problem 5 - power
=====================================================================================

b) query pow(z,z,C) which is 0^0 should return 1.

c) What will your program answer if A,B, and C are not all Peano integers?
It should return No.

Problem 6 - cut operator
=====================================================================================

subroutine calls to only stop backtracking within that subroutine.

Problem 7 - Unique
=====================================================================================

uniq([],[]).


