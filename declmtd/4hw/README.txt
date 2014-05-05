Kyle Wong
kwong23@jhu.edu
Declarative Methods HW 4
14.5.5

Problem 1 - ZIMPL Introduction
=====================================================================================
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



Problem 2 - Greater Than
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


