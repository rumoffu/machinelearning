% Kyle Wong
% Declarative Methods Homework 3
% 14.4.10
% Problem 3
%
% Write a predicate duplicate to duplicate every element of a list.
% For example duplicate([a,b,c],L) should return L = [a,a,b,b,c,d]
% and duplicate(M, [a,a,b,b,c]) should fail and answer No
%
% compile and run $ rlwrap eclps
% compile('problem3.ecl').
% 
% Example:
% [eclipse 2]: duplicate([a,b,c],L).
%
% L = [a, a, b, b, c, c]
% Yes (0.00s cpu)
% [eclipse 3]: duplicate(L,[a,b,c]).
%
% No (0.00s cpu)
% [eclipse 4]: duplicate(L,[a,a,b,b,c]).
%
% No (0.00s cpu)
% [eclipse 5]: duplicate(L,[a,a,b,b,c,c]).
% 
% L = [a, b, c]
% Yes (0.00s cpu)
%


duplicate([],[]).
duplicate([X|Xs],[X,X|Ys]) :- duplicate(Xs, Ys).



