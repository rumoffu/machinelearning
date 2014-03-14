% Kyle Wong
% Declarative Methods Homework 2
% 14.3.13
% Problem 2 a
%
%  The numbers 1 to 9 are going to be placed in the following pattern:
% 
%                   A
%                 B,C,D
%               E,F,G,H,I
% 
%  so that the sums in each 2 x 2 triangle sums to the same value n:
%  A+B+C+D = B+E+F+G = D+G+H+I = N
% run as [kwong23@ugrad12 2hw]$ eclps -b newmagictriangle.ecl -e 'go' | sort | uniq > triangleout
% or ugrad12$ rlwrap ecpls
% compile('newmagictriangle.ecl').
% [eclipse 11]: setof(N, solve(N), List).
% or use solve(N) and ; to step through solutions one by one

:- lib(ic).

go :-
        cputime(T0),
        ( solve(N), writeln(N), fail ; true ),
        T is cputime - T0,
        writeln([T, 'seconds']).

solve(N) :-
        Pattern = [
                     A,
                   B,C,D,
                 E,F,G,H,I
                  ],
        Pattern :: 1 .. 9,
        % sum of 4 digits 1 to 9 can be minimum 10 or max 30
        N :: 10 .. 30, 
        % Problem constraints
        alldifferent(Pattern),
          A+B+C+D #= N,
          B+E+F+G #= N,
          D+G+H+I #= N, 

%       Choice=input_order,             % slowest
        Choice=first_fail,              % fastest
%       Choice=most_constrained,        % medium
        search(Pattern, 0, Choice, indomain, complete, []).

