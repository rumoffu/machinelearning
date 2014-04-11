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

pow calls mult which calls add.  And add runs in time O(A).
So mult will recurse and use add, and mult will run O(A) times using add each time,
so mult runs in O(A^2).  Then, pow(A,B,C) recurses and runs O(B) times and thus
the overall runtime will be O(B*A^2)

e) How would pow be used for roots or logarithms?  What happens when you try it?
What is the Prolog solver doing internally?

You can use pow to solve logs by using it in mode pow(+,-,+) where B is unknown.
so pow(A+,B-,C+) is asking what is the log base A of C

You can use pow to solve roots by using it in mode pow(-,+,+) where A is unknown.
so pow(A-,B+,C+) is asking what is the C to the root B

You can solve logs and roots if the answer is an integer, 
but if it seeks another solution, it will overflow the stack.
If there is no integer answer, it will search infinitely.

Problem 6 - cut operator
=====================================================================================

cutmember outputs the first solution to member(X, List) and cuts off other potential
solutions.  Thus, the cut is red.
If you are just checking for existence of a ground integer in a long list, using
cutmember will skip over the other unnecessary repeated solutions and allow the rest
of the program to finish more quickly.

Outputs tested:

[eclipse 12]: cutmember(L,[[a,b],[],[c,d]]), member(X,L).

L = [a, b]
X = a
Yes (0.00s cpu, solution 1, maybe more) ? ;

L = [a, b]
X = b
Yes (0.00s cpu, solution 2)

------------------------------------------------------------
[eclipse 13]: member(L,[[a,b],[],[c,d]]), cutmember(X,L).

L = [a, b]
X = a
Yes (0.00s cpu, solution 1, maybe more) ? ;

L = [c, d]
X = c
Yes (0.00s cpu, solution 2)


------------------------------------------------------------
[eclipse 14]: member(L,[[],[a,b],[c,d]]), cutmember(X,L).

L = [a, b]
X = a
Yes (0.00s cpu, solution 1, maybe more) ? ;

L = [c, d]
X = c
Yes (0.00s cpu, solution 2)

------------------------------------------------------------

[eclipse 15]: member(L,[[a,b],[],[c,d]]), !, member(X,L).

L = [a, b]
X = a
Yes (0.00s cpu, solution 1, maybe more) ? ;

L = [a, b]
X = b
Yes (0.00s cpu, solution 2)

------------------------------------------------------------

[eclipse 16]: member(L,[[a,b],[],[c,d]]), member(X,L), !.

L = [a, b]
X = a
Yes (0.00s cpu)
------------------------------------------------------------
[eclipse 17]: member(L,[[],[a,b],[c,d]]), member(X,L), !.

L = [a, b]
X = a
Yes (0.00s cpu)

------------------------------------------------------------

The first query does the same thing, but the second and third
queries do not do the same thing.  This is because the cut
operator stops backtracking through the current statement. Thus, when it is a part
of a subroutine, it prevents backtracking through any part of the subroutine that 
came before it. However, it does not stop backtracking through any statements that
are outside of the subroutine that come before it.  So, it is limited in scope within
subroutine calls to only stop backtracking within that subroutine.

Problem 7 - Unique
=====================================================================================

uniq([],[]).
uniq([X|Xs], Ys) :- member(X,Xs), uniq(Xs,Ys).
uniq([X|Xs],[X|Ys]) :- uniq(Xs,Ys).

[eclipse 19]: uniq([a,a,b,b,c],L).

L = [a, b, c]
Yes (0.00s cpu, solution 1, maybe more) ? ;

L = [a, b, b, c]
Yes (0.00s cpu, solution 2, maybe more) ? ;

L = [a, a, b, c]
Yes (0.00s cpu, solution 3, maybe more) ? ;

L = [a, a, b, b, c]
Yes (0.00s cpu, solution 4)


a) The solution given above allows backtracking which gives solutions that are
not unique.

b) Consider the query uniq([3,X],[3,X]). What would you like it to return? Can Prolog do this?

You would like it to return any number other than 3 and then to enumerate other solutions.
Prolog cannot do this because it can only do unification.

c) Add a cut at the end of the second statement.
uniq([X|Xs], Ys) :- member(X,Xs), uniq(Xs,Ys), !.
This makes sure that we will only find one unique solution and will not backtrack
to get more solutions (which will actually find non-unique solutions).

d) What does the modified program do for uniq([3,X], L)?

[eclipse 22]: uniq([3,X],L).

X = 3
L = [3]
Yes (0.00s cpu)

It just returns the first X and L solution where L contains the unique elements
that are in the list [3,X].

How about uniq([3,X],[3,X])? Why?

[eclipse 23]: uniq([3,X],[3,X]).

X = X
Yes (0.00s cpu)

The output does not change.  This is because Prolog just unifies X with X and does
not understand that there needs to be a constraint on X to not be equal to three.
It does not have delayed constraints. 
The cut operator does not add that meaning to the program.

e) The cut operator stops backtracking from going through the clause (the statement 
which ends with a period).  So in this case, the cut's location puts an ordering
on the constraint clauses.  This is because when the statement is passed, backtracking
is no longer allowed to go past the cut.  So, the order of the clauses becomes 
important.  Normally, in pure Prolog, there are no cuts, and so the order should not
matter.  But with the cut, the clause order matters because of how it stops backtracking.

Problem 8 - increasing subsequence
=====================================================================================

a) See problem8.ecl

b) This query finds and counts all increasing subsequence solutions
[eclipse 4]: findall(S,inc_subseq([3,5,2,6,7,4,9,1,8,0],S),List), length(List,N).

S = S
List = [[3, 5, 6, 7, 9], [3, 5, 6, 7, 8], [3, 5, 6, 7], [3, 5, 6, 9], [3, 5, 6, 8], 
[3, 5, 6], [3, 5, 7, 9], [3, 5, 7, 8], [3, 5, 7], [3, 5, 9], [3, 5, 8], [3, 5], 
[3, 6, 7, 9], [3, 6, 7, 8], [3, 6, 7], [3, 6, 9], [3, 6, ...], [3, ...], [...], ...]
N = 72
Yes (0.00s cpu)


Modify it to find and count only subsequences of length 3.

[eclipse 7]: length(S, 3), findall(S,inc_subseq([3,5,2,6,7,4,9,1,8,0],S),List), length(List,N).

S = [_370, _372, _374]
List = [[3, 5, 6], [3, 5, 7], [3, 5, 9], [3, 5, 8], [3, 6, 7], [3, 6, 9], [3, 6, 8],
[3, 7, 9], [3, 7, 8], [3, 4, 9], [3, 4, 8], [5, 6, 7], [5, 6, 9], [5, 6, 8], [5, 7, 9],
[5, 7, 8], [2, 6, ...], [2, ...], [...], ...]
N = 25
Yes (0.00s cpu)


Problem 9
=====================================================================================



