% Kyle Wong
% Declarative Methods Homework 3
% 14.4.11
% Problem 8
%
% Increasing subsequence
%
% compile and run $ rlwrap eclps
% compile('problem8.ecl').
% 
:- lib(ic).
:- lib(branch_and_bound).

% Empty set is an increasing subsequence
inc_subseq([],[]).

% If X is the front of the solution, then Y is empty or Y has Z greater than X
% which is to say the rest of the list must be strictly increasing
inc_subseq([X|Xs],[X|Y]) :- inc_subseq(Xs, Y), (Y = [] ; Y = [Z|_], X#<Z).

% remove the first element and ensure the rest is increasing subsequence
inc_subseq([_|Xs],Y) :- inc_subseq(Xs,Y).

max_inc_subseq(X,Y) :-

  minimize( (inc_subseq(X,Y), length(Y,N), Cost is -N), Cost).

