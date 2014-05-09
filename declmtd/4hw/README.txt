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

$ scip -f knapsack.zpl

Result:

count                                            3544   (obj:0)
totalvalue                                   14775050   (obj:1)
takenweight                                    251360   (obj:0)
maximumweight                                  251360   (obj:0)
radioactivity                        19.9926114693637   (obj:0)

note: ran in 1.73 seconds on ugrad14


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

[kwong23@ugrad14 4hw]$ scip -f schedule.zpl


Solution found:

objective value:                               52.996
sleepy#3                                            1   (obj:0)
sleepy#6                                            1   (obj:0)
goevent$freetiedye                                  1   (obj:0)
goevent$smallgroup                                  1   (obj:0)
goevent$hiking                                      1   (obj:0)
goevent$potluck                                     1   (obj:0)
goevent$coolstuff                                   1   (obj:0)
goevent$church                                      1   (obj:0)
events#1                                            2   (obj:0)
events#2                                            1   (obj:0)
events#4                                            2   (obj:0)
events#6                                           14   (obj:0)
events#7                                            5   (obj:0)
work#1                                             22   (obj:0)
work#2                                             23   (obj:0)
work#4                                         21.999   (obj:0)
work#5                                         13.999   (obj:0)
work#7                                             15   (obj:0)
sleep#3                                            24   (obj:0)
sleep#4                          0.000999999999997669   (obj:0)
sleep#5                                        10.001   (obj:0)
sleep#6                                            10   (obj:0)
sleep#7                                             4   (obj:0)
gohours$freetiedye                                  2   (obj:0)
gohours$smallgroup                                  2   (obj:0)
gohours$hiking                                     14   (obj:0)
gohours$potluck                                     3   (obj:0)
gohours$coolstuff                                   1   (obj:0)
gohours$church                                      2   (obj:0)
eventfun                                           68   (obj:0)
workpenalty                                    15.004   (obj:0)
totalfun                                       52.996   (obj:1)
eperday#1$freetiedye                                2   (obj:0)
eperday#2$coolstuff                                 1   (obj:0)
eperday#4$smallgroup                                2   (obj:0)
eperday#6$hiking                                   14   (obj:0)
eperday#7$potluck                                   3   (obj:0)
eperday#7$church                                    2   (obj:0)
workdone$algorithms                                 3   (obj:0)
workdone$machinelearning                           25   (obj:0)
workdone$declarative                               24   (obj:0)
workdone$finalproject                              28   (obj:0)
workdone$study                      0.998000000000017   (obj:0)
workdone$essay                                     15   (obj:0)
wperday#1$machinelearning                      21.002   (obj:0)
wperday#1$study                     0.998000000000017   (obj:0)
wperday#2$algorithms                                3   (obj:0)
wperday#2$machinelearning            3.99800000000001   (obj:0)
wperday#2$finalproject                         16.002   (obj:0)
wperday#4$declarative                          10.001   (obj:0)
wperday#4$finalproject                         11.998   (obj:0)
wperday#5$declarative                          13.999   (obj:0)
wperday#7$essay                                    15   (obj:0)
workrate#1                                          1   (obj:0)
workrate#2                                          1   (obj:0)
workrate#3                                       0.75   (obj:0)
workrate#4                                          1   (obj:0)
workrate#5                                          1   (obj:0)
workrate#6                                       0.75   (obj:0)
workrate#7                                          1   (obj:0)
workhours                                      95.998   (obj:0)
sleephours                                     48.002   (obj:0)
eventhours                                         24   (obj:0)


Discussion of solution found:

The solver really optimizes fun, but in a non-realistic way.  First of all,
some events are not visited - bore because it has a low fun rate (lower than 
the base play rate), and liondance because it has a relatively low fun rate
and requires too many hours.  Some assignments are skipped because of their
low value (cis and victorian poetry).  Some assignments are unfinished (study
required 5 hours but only 1 hour was done). Sleep is sporadic: day 3 is devoted
solely to sleep.  Day 6 involves being sleepy (having a sleep deficit from 
less than 24 hours of sleep).  

Changing the weights (fun rates and penalty rates) affects which events 
are visited and which assignments are done or not done.

It was difficult getting multiple events to be on the same day at first
in terms of having the code work properly.

All in all, this program solves a cool problem, but it isn't realistic (who would
sleep for 24 hours straight then work for 22 hours straight afterwards?)


Problem 4 - n^2 by n^2 Sudoku solver
=====================================================================================

a) You can find an incomplete ZIMPL program in sudoku.zpl.  Finish it.
How long does SCIP take to solve the "very hard" problem in sudoku.txt?

To view the result use:
scip -f sudoku1.zpl | ./sudoku-decode

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


d) Even though it's not possible to have a rotationally symmetric 9 x 9 grid,
use SCIP to find one that is as close to rotationally symmetric as possible.
Specifically, you should minimize the number of cells that are not equal to their
rotational counterpart. How close can you get to be rotationally symmetric?
(Hint: You may find vabs helpful)

See dsudoku.zpl for the code

Result:

2 4 3 | 6 1 7 | 9 5 8 
1 8 5 | 3 9 2 | 4 7 6 
7 9 6 | 8 5 4 | 2 1 3 
------+-------+-------
9 2 7 | 4 8 3 | 1 6 5 
5 3 1 | 9 7 6 | 8 2 4 
4 6 8 | 1 2 5 | 7 3 9 
------+-------+-------
3 1 2 | 5 4 8 | 6 9 7 
6 7 4 | 2 3 9 | 5 8 1 
8 5 9 | 7 6 1 | 3 4 2 

and using letters to represent mismatches:

2 4 3 | a b 7 | 9 5 8 
1 8 5 | e f 2 | 4 7 6 
7 9 6 | 8 i j | 2 1 3 
------+-------+-------
9 h 7 | m n o | k 6 c 
d g l | p 7 p | l g d 
c 6 k | o n m | 7 h 9 
------+-------+-------
3 1 2 | j i 8 | 6 9 7 
6 7 4 | 2 f e | 5 8 1 
8 5 9 | 7 b a | 3 4 2 

So, it is off by 16 pairs (so 32 total digits are offset in total)

In the code, the offset count is called doubleoffset because of double counting:
For example, if the top left square is a 1, but the bottom right square is a 9,
it counts 1 mistake for digit 1, and 1 mistake for digit 9.
And then it double counts because it repeats this process for the bottom right 
square being a 9 and the top left square being a 1.  So the total number of 
digits that are wrong is simply doubleoffset / 2.

Note: this took 80 seconds to run on ugradx.


e) Your solution to the previous problem is only one of a family of symmetric 
solutions.  Add a symmetry-breaking constraint. Hint: Think about getting
the first row into a standard form.

We can add a symmetry breaking constraint by requiring the first row to be
1, 2, 3, 4, 5, 6, 7, 8, 9

This prevents swapping of any of the digit pairs.

See code sudoku3.zpl

Result:

1 2 3 | 4 5 6 | 7 8 9 
8 5 9 | 1 7 3 | 2 4 6 
4 7 6 | 2 9 8 | 1 3 5 
------+-------+-------
2 6 4 | 3 1 5 | 8 9 7 
7 1 8 | 9 4 2 | 5 6 3 
3 9 5 | 6 8 7 | 4 1 2 
------+-------+-------
5 3 1 | 8 2 9 | 6 7 4 
6 4 2 | 7 3 1 | 9 5 8 
9 8 7 | 5 6 4 | 3 2 1

note: This took 30 seconds on ugradx

Thus, this constraint speeds up SCIP.  This makes sense because it 
reduces the amount of branching and checking that SCIP has to do.
This also does not change the feasibility of the problem as the
first row being set to 1 through 9 is required since all digits
in the row must be different.


f) [extra credit]


g) Now go back to n = 3. Suppose you allow the x variables to be real numbers 
in the range [0, 1].  Now you could have a solution that satisfies all the 
constraints including rotsymm.  What is the "simplest" such solution? (There is no
need to change the program or run SCIP; just think about it!)

To be honest, this question doesn't make much sense at all.
However, it seems like the desired answer is that the simplest solution that 
"satisfies the constraints" would be setting each x variable to be 1/9.

Then the digit constraints add up to 1, the row constraints will add up to 1,
the column constraints add up to 1, and the block constraints will add up to 1.

And since each x variable has the same value of 1/9, it will also fit
the rotational symmetry constraint.  


Problem 5 - Primal to Dual [required for 425; extra credit for 325]
=====================================================================================

a) Why can the relaxed LP problem get a higher value of the objective than the 
original LP problem?  Specifically, how does it make your job easier as a robber with
a physical knapsack?

The relaxed LP problem can get a higher value because it allows for fractionally
taking items.  This means that more possibilities are opened up for taking
different item combinations.  It makes the job easier because fully packing
the knapsack is more flexible.


b) Derive the dual LP problem.


c) What can you say about the minimum value of the dual objective? (Will it equal 
the weight, or the value, of the optimally filled knapsack? Higher? Lower?)


The minimum value of the dual objective will be equal to the value
of the optimally filled knapsack.  This is because of the equality
between the optimum (minimum) of the dual problem with the optimum (maximum) of
the primal problem.

d) What interpretation can you give to dual variable yo?


e) What interpretation can you give to the dual variables y1, ... yn?


f) Suppose for the sake of argument that y0 is fixed to some constant.


g) The above question shows that once y0 is chosen, the values for the other
yi will be fully determined.


