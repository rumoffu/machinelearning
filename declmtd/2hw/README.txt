Kyle Wong
kwong23@jhu.edu
Declarative Methods HW 2
14.3.13

Problem 1 - bratwurst
=====================================================================================
If the kickoff is at noon, 
you should start preparing the bratwurst 
at least 40 minutes before the game.

output:
Defrost sausages: 0 - 2 
Preheat grill: 0 - 20 
Dice onions: 0 - 3 
Toast buns: 20 - 21 
Grill sausages: 21 - 31 
Add condiments: 39 - 40 
Grill onions: 31 - 39 
Pan-broil sausages: 2 - 17 

EndTime = 40


Problem 2 a) Triangle
======================================================================================
a + b + c + d = b + e + f + g = d + g + h + i = n

to run step by step
ugrad12$ rlwrap eclps
[eclipse 10]: compile('magictriangle.ecl').
[eclipse 10]: go .
note: this outputs many solutions. you can type solve(X, N) and use ; to set through solutions one at a time.

solutions: 
n = 17, 19, 20, 21, 23

with n - [a,b,c,d,e,f,g,h,i] examples as:
17 - [4, 1, 9, 3, 6, 8, 2, 5, 7] works
19 - [1, 2, 9, 7, 6, 8, 3, 4, 5] works 
20 - [1, 2, 9, 8, 6, 7, 5, 3, 4] works
21 - [1, 3, 8, 9, 5, 7, 6, 2, 4] works 
23 - [1, 7, 6, 9, 3, 5, 8, 2, 4] works 


to run and get the answers compiled:
[kwong23@ugrad12 2hw]$ eclps -b magictriangle.ecl -e 'go' | sort | uniq > triangleout

then the file triangleout lists the unique solutions for n 

=====================================

Alternatively, you can solve this problem step-by-step in eclps using
ugrad12$ rlwrap eclps
[eclipse 10]: compile('newmagictriangle.ecl').
[eclipse 10]: setof(N, solve(N), List).

which outputs 

N = N
List = [17, 19, 20, 21, 23]
Yes (0.12s cpu)



Problem 2 b) 6-concatenation
======================================================================================
Find 3-digit integers x and y such that 6 * (x || y) = (y || x) where || is concatenation

Two Variable solution:

to run step by step
ugrad12$ rlwrap eclps
[eclipse 10]: compile('concat2.ecl').
[eclipse 10]: go .

or to run in batch use:
[kwong23@ugrad12 2hw]$ eclps -b concat2.ecl -e 'go'

solution [x,y]:
[142, 857]

which is correct since 6*142857 = 857142

note: this seems to be related to a property of 1/7 = 0.142857...

==========================================

Six Variable solution:

to run step by step
ugrad12$ rlwrap eclps
[eclipse 10]: compile('concat6.ecl').
[eclipse 10]: go .

or to run in batch use:
[kwong23@ugrad12 2hw]$ eclps -b concat6.ecl -e 'go'

solution [x1, x2, x3, y1, y2, y3]:
[1, 4, 2, 8, 5, 7]

which is correct since 6*142857 = 857142

and gives us the same solution as with two variables.


Problem 2 c) 3 isosceles triangles with area = 6*perimeter
======================================================================================
Find three isosceles triangles, no two of which are congruent, with integer sides, 
such that each triangle's area is numerically equal to 6 times its perimeter.

to run step by step
ugrad12$ rlwrap eclps
[eclipse 1]: compile('isosceles.ecl').
[eclipse 2]: go.

or to run in batch use:
[kwong23@ugrad4 2hw]$ eclps -b isosceles.ecl -e 'go'


Solution1:
P: 128 A: 768
X = [40, 40, 48]

Solution2:
P: 162 A: 972
X = [45, 45, 72]

Solution3:
P: 250 A: 1500
X = [65, 65, 120]


Problem 2 d) The number 12148
======================================================================================
Sum of the first four digits equals the units digit.  How many EVEN five-digit numbers have this property?

There are 200 solutions.
[kwong23@ugrad4 2hw]$ eclps -b sum.ecl -e 'go' | wc
    201    1002    3216
so there are 200 lines of solution and the last line is the runtime.

You can also run the code with
rlwrap eclps
compile('sum.ecl').
solve(X).

and use ; to step through the 200 solutions.


Problem 3
======================================================================================
- Was only to read

Problem 4
======================================================================================
problem1.ecl was generated using:
python ecl1.py > problem1.ecl

where ecl1.py wrote constraints by reading from a file in the same directory - rcps.data.

The total time for completing all the tasks was 23489 (minutes)

What I did in the python script to write the ecl file was:
1) to write the header (library imports)
2) read in the rcps.data sections 1 and 2
3) use the read-in data to print the variable declarations using 
the F_ (finishtime) and S_ (starttime) convention and removing the asm_1. prefix
(because eclps did not like the periods)
note: I added 2 empty variables to avoid the issues with trailing commas since
they are unconstrained and do not change the solution anyway
4) I set the task durations based on the section 1 data (just like in bratwurst.ecl)
5) I used the section 2 data to declare precedence (just like in bratwurst.ecl)
6) I declared the solution commands to find the maximum of the finishing times,
to flatten the solution, and to minimize it.  Then I put commands for printing out
all of the variable solutions (the start and end time for each step)

I then ran the ecl code in eclps as:
[kwong23@ugrad19 2hw]$ eclps -b problem1.ecl -e 'solve(EndTime)' > problem1.solution



Problem 5
======================================================================================
- Takes too long.  We don't need to turn anything in

Problem 6
======================================================================================
For Search parameters:
1) for Select - I tried 'occurrence' which selects the entry with the
largest number of attached constraints is selected because it is easier to solve
the problem by first fitting in the most constrained variable.  Less constrained
variables / tasks can just be fitted in at other places.

However, I found that it was better to use 'input_order" which possibly 
works better since the initial steps are more likely to be of higher precedence than
later steps.

2) for Choice - I tried to use 'indomain_middle' since it would be easiest to find
solutions, providing more flexibility to move around the starting and ending times of steps.

3) for Method - I found that lds - Limited Discrepancy Search - gave the best results.
It works well because, when 1-sampling fails, you can follow the heuristic at all but
one or two decision points (or "wrong turns") that got it off track.  So it works
quickly while also being able to search the entire tree exhaustively.

Using input_order and indomain_middle and lds(1) I found a solution of
X = 59380
Yes (1137.19s cpu)

It is run in batch mode as
[kwong23@ugrad19 2hw]$ eclps -b problem2.ecl -e 'solve(EndTime)' > problem2.solution

or you can use 
rlwrap eclps
compile('problem2.ecl').
solve(EndTime).


