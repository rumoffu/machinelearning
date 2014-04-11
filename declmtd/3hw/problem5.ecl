% Kyle Wong
% Declarative Methods Homework 3
% 14.4.10
% Problem 5
%
% Write a Prolog constraint pow(A,B,C) that is satisfied iff C = A^B. 
% Assume A, B, and C are all Peano integers.
%
% 
% compile and run $ rlwrap eclps
% compile('problem5.ecl').
% 
% Example:
% [eclipse 16]: pow(z,z,C).
% 
% C = s(z)
% Yes (0.00s cpu)
% [eclipse 17]: pow(s(s(z)),s(s(z)),C).
% 
% C = s(s(s(s(z))))
% Yes (0.00s cpu)
% [eclipse 18]: pow(s(z),z,C).
% 
% C = s(z)
% Yes (0.00s cpu)
% [eclipse 19]: pow(s(s(z)),s(s(s(z))),C).
% 
% C = s(s(s(s(s(s(s(s(z))))))))
% Yes (0.00s cpu)


add(z,B,B). %0+B=B
% (1+A)+B = Sum if A+(1+B) = Sum
add(s(A),B,Sum) :- add(A,s(B),Sum). %(1+A)+B =Sum if A+(1+B)=Sum

mult(z,_,z). %0*B=0
% (1+A)*B = B
mult(s(A),B,Sum) :- mult(A,B,Product), add(B, Product, Sum). %(1+A)*B = B + A*B = Sum if A*B=Product and B+Product=Sum

% anything to the power 0 is 1
pow(z,z,s(z)).
pow(s(_),z,s(z)).

% A^(B+1) = A^B*A
pow(A,s(B),C) :- pow(A,B,AtotheB), mult(AtotheB,A,C).

