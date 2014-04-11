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

e) [425] Prolog does unification, but the copies are different.  So they are partly shared copies
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

c) In the previous question, it is more efficient to process the length=3 constraint 
before the inc_subseq constraint because it sets a bound on the size of the sets that
Prolog will generate and test that match the inc_subseq constraints.

d) The built-in ECLiPSe predicate minimize/2 tries to find the minimum-cost satisfying 
assignment of some query. You can maximize a function by negating the cost.
Give a command to find the longest increasing subsequence of a given list.

max_inc_subseq(X,Y) :-

  minimize( (inc_subseq(X,Y), length(Y,N), Cost is -N), Cost).

running it and getting output gives:
[eclipse 12]: max_inc_subseq([3,5,2,6,7,4,9,1,8,0],S).
Found a solution with cost -5
Found no solution with cost -1.0Inf .. -6

S = [3, 5, 6, 7, 9]
Yes (0.00s cpu)
------------------------------------------------------------

[eclipse 13]: max_inc_subseq([4,6,5,4,8,1],Ans).
Found a solution with cost -3
Found no solution with cost -1.0Inf .. -4

Ans = [4, 6, 8]
Yes (0.00s cpu)

e) [425] Explain the meaning of the following query.

Vars = [A,B,C,D,E], Vars::1..4, inc_subseq(Vars,[E,C,A]),
  labeling(Vars).

This query is finding an assignment to the variables A-E such that the values are
from 1 to 4 such that E < C < A.

Then try it and report the results:
Vars = [A,B,C,D,E], Vars::1..4, inc_subseq(Vars,[E,C,A]),
          labeling(Vars).

Vars = [3, 1, 2, 3, 1]
A = 3
B = 1
C = 2
D = 3
E = 1


Now remove the labeling(Vars). Results:

[eclipse 17]: Vars = [A,B,C,D,E], Vars::1..4, inc_subseq(Vars,[E,C,A]).

Vars = [A{[3, 4]}, B{[1, 2]}, C{[2, 3]}, A, B]
A = A{[3, 4]}
B = B{[1, 2]}
C = C{[2, 3]}
D = A{[3, 4]}
E = B{[1, 2]}


Delayed goals:
        A{[3, 4]} - C{[2, 3]} #> 0
        C{[2, 3]} - B{[1, 2]} #> 0

------------------------------------------------------------

Explanation: Prolog is finding all ranges of values for the variables such that
the constraints will be made true.  So A{[3, 4]} means A can be 3 or 4. And
B = B{[1, 2]} means B can be 1 or 2.  The list (square brackets) indicates that if
A = 3, then B = 1 (all elements must take the value of the correct spot in the list).
Prolog finds these values by backtracking and then simply saves and outputs the
total range of answer values.

The delayed goals show the constraints between the variable assignment answers.

Problem 9 
=====================================================================================

a) explain what p does

:- lib(ic).

p([],1).
p([X|Xs],A) :- p(Xs,B), A #= X*B.

p can calculate the product of a list of integers. It turns each element into a factor,
then recurses down the list to calculate the total product of all the factors in the list.

b) explain what q does:

q(List,N) :- p(List,N), List::2..N, labeling(List).

q finds a list of factors who multiply up to form the product in N.  It restricts the
domain of answers to 2 up to N (so 0 and 1 are excluded).  When the total number of
possible solutions has been found, seeking more solutions (with semicolon) causes
the program to search infinitely for more factors in vain.

c) p appears to set up a constraint program with exactly three numeric variables,
(A,B,X).  Explain why this is false.  How large is the constraint program?

It actually has several more varibles since it is recursing 
and thus adds constraints at each level of recursion:
For every X in the list Xs, there is a constraint added where A #= X*B
and so each step of the recursion actually generates a different X, A, and B.

For each level of recursion, we get a new constraint and 3 different variables.
Thus, since it recurses equal to the length of the list, then the constraint
program is as large as the length of the List.

% [20] gives 20=20*1
% [2, 10] gives recurse p(10,B) which gives B #= 10*1 and so A #= 2*10 and A =20

d) Explain why q([7,R],20) returns no.

The query returns No because there is no solution to R such that 7*R = 20.  This is
basically saying that no R exists that would make 7*R = 20.

Explain why q([7|Rs],20) fails to halt.

This query is asking if there is a list of integers Rs that would make 7*Rs = 20,
so the program fails to halt because it continues searching more and more possible
lists of integers to make 7*Rs = 20.

e) [425] write alldifferent to have all the constraints.

See problem9.ecl --- it gives all the constraints for adiff([X,Y,Z]) and adiff([V,W,X,Y,Z])


Problem 10 isomorphic binary trees
=====================================================================================

a) See problem10.ecl

[eclipse 29]: 
isotree(
  t(d, t(a, nil, nil), t(d, t(b, nil, nil), t(c,nil, nil) ) ),
  t(d, t(d, t(b, nil, nil), t(c, nil, nil) ), t(a, nil, nil) ) ).

Yes (0.00s cpu)

b) Give an example isotree query (on two constant trees) that demonstrate that Prolog 
may have to do an exponential amount of work to determine that two trees are not 
isomorphic.  Your example should "tease" Prolog leading it down many long blind
alleys.

Two trees with children with the same label.  This makes each branch indistinguishable,
so Prolog will have to wander down each branch until the very very bottom leafs to see
there is a difference.

For example:
       d             d
     /   \         /   \
    d     d       d     d
   / \   / \     / \   / \
  d   d d   a   d   d d   z

   The query isotree(
   t(d, t(d,t(d,nil,nil),t(d,nil,nil)),  t(d,t(d,nil,nil),t(a,nil,nil))), 
   t(d, t(d,t(d,nil,nil),t(d,nil,nil)),  t(d,t(d,nil,nil),t(z,nil,nil))). 
   
   would create a mostly symmetric tree where the solver would have to check every 
   subtree and its Left and Right inverted form until the deepest leaf node in order
   to see that the two trees are not isomorphic.  So it will do exponential work since
   each branch creates 2 times as many nodes to check.


Problem 11 
=====================================================================================

a) Please see problem11.ecl

b)  Please see problem11.ecl for the inorder2 predicate.
The original predicate inorder1 ran into infinite recursion
after finding the first solution because it tries to backtrack through append
before it backtracks through the inorder1 left traversal.

Thus, our call to append now simply sets constraints for how to append the 
subtrees into the final resulting list of nodes called Keys.  It no longer operates 
as a source of backtracking where Prolog gets stuck in infinite recursion.



[eclipse 15]: findall(Tree,inorder2(Tree,[a,b,c,d,e]),List), length(List,N).

Tree = Tree
List = [t(a, nil, t(b, nil, t(c, nil, t(d, nil, t(e, nil, nil))))), 
t(a, nil, t(b, nil, t(c, nil, t(e, t(d, nil, nil), nil)))), 
t(a, nil, t(b, nil, t(d, t(c, nil, nil), t(e, nil, nil)))), 
t(a, nil, t(b, nil, t(e, t(c, nil, t(d, nil, nil)), nil))), 
t(a, nil, t(b, nil, t(e, t(d, t(c, nil, nil), nil), nil))), 
t(a, nil, t(c, t(b, nil, nil), t(d, nil, t(e, nil, nil)))), 
t(a, nil, t(c, t(b, nil, nil), t(e, t(d, nil, nil), nil))), 
t(a, nil, t(d, t(b, nil, t(c, nil, nil)), t(e, nil, nil))), 
t(a, nil, t(d, t(c, t(b, nil, nil), nil), t(e, nil, nil))), 
t(a, nil, t(e, t(b, nil, t(c, nil, t(d, nil, nil))), nil)), 
t(a, nil, t(e, t(b, nil, t(d, t(c, nil, nil), nil)), nil)), 
t(a, nil, t(e, t(c, t(b, nil, nil), t(d, nil, nil)), nil)), 
t(a, nil, t(e, t(d, t(b, nil, t(c, nil, nil)), nil), nil)), 
t(a, nil, t(e, t(d, t(c, t(b, nil, nil), nil), nil), nil)), 
t(b, t(a, nil, nil), t(c, nil, t(d, nil, t(e, nil, nil)))), 
t(b, t(a, nil, nil), t(c, nil, t(e, t(...), nil))), 
t(b, t(a, nil, nil), t(d, t(...), t(...))), 
t(b, t(...), t(...)), t(...), ...]
N = 42
Yes (0.00s cpu)


Thus, there are 42 legal binary search trees for [a,b,c,d,e]

c) [425] Searches in a binary tree are more efficient if the node being searched for
is closer to the root of the tree.  The root has depth 0, its children have depth 1,
etc.  Write a function total_depth/2 that computes the total depth of all non-nil 
nodes in a tree. (Not the deepest depth, but the sum of all depths of all nodes)
This is proportional to the amount of time it would take to search once for each element.
Use minimize/2 from branch_and_bound to construct inorder3 which constructs a tree
like inorder2 but requires it to have minimum total depth.
(the result should turn out to be a complete binary tree, except that the deepest 
layer of nodes need not be left-aligned.)



















