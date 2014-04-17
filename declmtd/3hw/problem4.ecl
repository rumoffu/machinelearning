% Kyle Wong
% Declarative Methods Homework 3
% 14.4.10
% Problem 4
%
%
% Write a predicate that nondeterministically chooses two adjacent elements in a list and swaps them.
% 
% compile and run $ rlwrap eclps
% compile('problem4.ecl').
% 
% Example:
% [eclipse 12]: swap([a,b,c,d],L).
% 
% L = [b, a, c, d]
% Yes (0.00s cpu, solution 1, maybe more) ? ;
% 
% L = [a, c, b, d]
% Yes (0.00s cpu, solution 2, maybe more) ? ;
% 
% L = [a, b, d, c]
% Yes (0.00s cpu, solution 3, maybe more) ? ;
% 
% L = [a, b, c, d]
% Yes (0.00s cpu, solution 4, maybe more) ? ;
% 
% No (0.00s cpu)
% [eclipse 13]: swap([],L).
% 
% L = []
% Yes (0.00s cpu)
% [eclipse 14]: swap([a],L).
% 
% L = [a]
% Yes (0.00s cpu, solution 1, maybe more) ? ;
% 
% No (0.00s cpu)
%

swap([],[]).
swap([X],[X]). 
swap([X,Y|Xs],[Y,X|Xs]). % if there are two, swap
swap([X,Y|Xs],[X|Ys]) :- swap([Y|Xs], Ys). %we recurse down the list, skipping the first element

mystery(W,Z) :- swap(W,X), swap(X,Y), swap(Y,Z).

