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
% compile('problem4.ecl').
% 
% Example:
% [eclipse 12]: swap([a,b,c,d],L).
% 
%

add(z,B,B). %0+B=B
add(s(A),B,Sum) :- add(A,s(B),Sum). %(1+A)+B =Sum if A+(1+B)=Sum

mult(z,B,z). %0*B=0
mult(s(A),B,Sum) :- mult(A,B,Product), add(B, Product, Sum). %(1+A)*B = B + A*B = Sum if A*B=Product and B+Product=Sum

