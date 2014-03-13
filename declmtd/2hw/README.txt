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



Problem 3


