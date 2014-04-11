% Kyle Wong
% Declarative Methods Homework 3
% 14.4.10
% Problem 7
%
% Unique elements of a list
%
% compile and run $ rlwrap eclps
% compile('problem7.ecl').
% 


uniq([],[]).
% we can find unique Ys if there is an X that is a member of Xs
% and we can ensure the rest of Xs is unique
uniq([X|Xs], Ys) :- member(X,Xs), uniq(Xs,Ys), !.

% if X is not repeated in Xs, then we include it in the unique
% solution Ys and ensure the rest of Xs is unique
uniq([X|Xs],[X|Ys]) :- uniq(Xs,Ys).


