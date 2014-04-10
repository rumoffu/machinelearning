Kyle Wong
kwong23@jhu.edu
Declarative Methods HW 3
14.4.10

Problem 1 - Unification Variable Bindings
=====================================================================================
a) [eclipse 1]: foo(X,X,Y) = foo(A,B,B).

X = X
Y = X
A = X
B = X
Yes (0.00s cpu)

b) [eclipse 2]: foo(X,Y)=foo(A,B,C).
No (0.00s cpu)

c) [eclipse 3]: [X,2,X,4,Y]=[1,A|B].

X = 1
Y = Y
A = 2
B = [1, 4, Y]
Yes (0.00s cpu)

[eclipse 4]: f(A,g(B))=f(h(D),E).

A = h(D)
B = B
D = D
E = g(B)
Yes (0.00s cpu)





Problem 2 - Greater Than
=====================================================================================
a) See problem2.ecl

b) What is the runtime of comparing ground Peano integers N and M using greater_than/2?
O(min(N,M)) where min returns the minimum of the two values N and M.  This is because 
each call to greater_than recurses by removing one more s, and it finishes when one
term runs out of s's and becomes z.

c) In mode:
(+,-) it returns all solutions where the solution is a value less than the given value
This is because it is checking through all values that may be less than the given value.

(-,+) it returns the first solution that has value greater than the given value
This is because it checks for the first value that proves the statement true.

(-,-) it returns all solutions (enumerates) where one value is greater than the other.
This is because it backtracks through all numbers and finds solutions where one value
is greater than another, and since it is true, just returns that result and keeps 
backtracking.

d) It has to enumerate solutions because it must ensure that A and B are Peano
integers and not any other value.  But more deeply, it uses unification and backtracking
and so it must use a Generate-and-test method and thus will enumerate solutions.

e) Prolog does unification, but the copies are different.  So they are partly shared copies
(where they have the same z, but different s's).



Problem 3 - Duplicate
=====================================================================================

See problem3.ecl

What should be returned by the query duplicate(M, [f(3),f(X),X,Y])?

Since the only way to make it match is a function where f returns 3, then Y should be 3

[eclipse 6]: duplicate(M, [f(3),f(X),X,Y]).

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
then starting from those solutions, it swaps adjacent elements down the list 
to find new solutions, and then starts from the new solutions to try swapping 
adjacent elements down the list again.

So if there are n elements, it will create n more solutions with n swaps,
then for each of those solutions, it creates n times more solutions, 
then for each of those additional solutions, creates n times more solutions.

Thus, mystery will return n^3 solutions.  
Not all of these values are different from each other because mystery is only
swapping the elements and does not track if the solution is a new discovery.

For example, running 
[eclipse 35]: mystery([a,b],L).

generates:

L = [b, a]
Yes (0.00s cpu, solution 1, maybe more) ? ;
L = [a, b]
Yes (0.00s cpu, solution 2, maybe more) ? ;
L = [a, b]
Yes (0.00s cpu, solution 3, maybe more) ? ;
L = [b, a]
Yes (0.00s cpu, solution 4, maybe more) ? ;
L = [a, b]
Yes (0.00s cpu, solution 5, maybe more) ? ;
L = [b, a]
Yes (0.00s cpu, solution 6, maybe more) ? ;
L = [b, a]
Yes (0.00s cpu, solution 7, maybe more) ? ;
L = [a, b]
Yes (0.00s cpu, solution 8, maybe more) ? ;
No (0.00s cpu)

Even if the input is larger, such as mystery([a,b,c,d,e,f],L),
the solutions will repeat.  



Problem 5 - power
=====================================================================================

a) See problem5.ecl

b) query pow(z,z,C) which is 0^0 should return 1.

c) What will your program answer if A,B, and C are not all Peano integers?
It should return No.

d) In general, how efficient is a query of mode pow(+A,+B,-C)?
What is Prolog doing internally? Give the asymptotic complexity in terms of A and B.


