% Kyle Wong
% Declarative Methods Homework 2
% 14.3.13
% Problem 2 b using 2 variables
%
% Find 3-digit integers x and y such that 6 * (x || y) = (y || x) where || is concatenation
%
% run as [kwong23@ugrad12 2hw]$ eclps -b concat2.ecl -e 'go'  

:- lib(ic).

go :-
        cputime(T0),
        ( solve(Solution), writeln(Solution), fail ; true ),
        T is cputime - T0,
        writeln([T, 'seconds']).

solve(Pattern) :-
        Pattern = [
                 X,Y
                  ],
        Pattern :: 100 .. 999,
        % Problem constraints
          6*(1000*X + Y) #= 1000*Y + X,

%       Choice=input_order,             % slowest
        Choice=first_fail,              % fastest
%       Choice=most_constrained,        % medium
        search(Pattern, 0, Choice, indomain, complete, []).

