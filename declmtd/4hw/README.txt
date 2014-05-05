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
a) See problem2.ecl

b) What is the runtime of comparing ground Peano integers N and M using greater_than/2?
O(min(N,M)) where min returns the minimum of the two values N and M.  This is because 
each call to greater_than recurses by removing one more s, and it finishes when one
term runs out of s's and becomes z.



Problem 3 - Duplicate
=====================================================================================

See problem3.ecl

What should be returned by the query duplicate(M, [f(3),f(X),X,Y])?
M = [f(3), 3]
X = 3
Y = 3
Yes (0.00s cpu)

Problem 4 - Swap
=====================================================================================

a) See problem 4.ecl

swap([a],L) should just return L = a since there are no elements to swap.
swap([],L) should just return L = [] since there are no elements to swap.

b) mystery(W,Z) :- swap(W,X), swap(X,Y), swap(Y,Z).

What does this do?

How many values for L will be returned by mystery([1,2,3,...n],L)?
Are all of these values different from one another? Why or why not?

mystery first swaps adjacent elements down the list to find solutions, 
Problem 5 - power
=====================================================================================

a) See problem5.ecl

b) query pow(z,z,C) which is 0^0 should return 1.

c) What will your program answer if A,B, and C are not all Peano integers?
It should return No.

Problem 6 - cut operator
=====================================================================================

subroutine calls to only stop backtracking within that subroutine.

Problem 7 - Unique
=====================================================================================

uniq([],[]).


