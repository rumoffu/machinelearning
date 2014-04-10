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




