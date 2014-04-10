% Kyle Wong
% Declarative Methods Homework 3
% 14.4.10
% Problem 2
%
% Write a predicate that compares two Peano integers.  For example, greater_than(s(s(z)),s(s(z))) should return No,
% but greater_than(s(s(s(z))),s(s(z))) should return Yes.
% Assume this predicate will be called with Peano integers as its arguments
%
% run as [kwong23@ugrad12 2hw]$ eclps -b square.ecl -e 'go'  
% compile and run $ rlwrap eclps
% compile('problem2.ecl').
% 
% Example:
% [eclipse 14]: greater_than(z,s(z)).
% No (0.00s cpu)
%
% [eclipse 15]: greater_than(s(z),s(z)).
% No (0.00s cpu)
%
% [eclipse 16]: greater_than(s(s(z)),s(z)).
% Yes (0.00s cpu, solution 1, maybe more) ? ;
% Yes (0.00s cpu, solution 2)
%
% [eclipse 17]: greater_than(s(s(s(z))),s(z)).
% Yes (0.00s cpu)
%


greater_than(s(z),z).
greater_than(s(A),z) :- greater_than(A,z).
greater_than(s(A),s(B)) :- greater_than(A,B).


