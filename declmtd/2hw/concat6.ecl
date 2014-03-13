% Kyle Wong
% Declarative Methods Homework 2
% 14.3.13
% Problem 2 b using 6 variables
%
% Find 3-digit integers x and y such that 6 * (x || y) = (y || x) where || is concatenation
%
% run as [kwong23@ugrad12 2hw]$ eclps -b concat6.ecl -e 'go'  

:- lib(ic).

go :-
        cputime(T0),
        ( solve(Solution), writeln(Solution), fail ; true ),
        T is cputime - T0,
        writeln([T, 'seconds']).

solve(Pattern) :-
        Pattern = [
                 X1, X2, X3, Y1, Y2, Y3
                  ],
        Pattern :: 0 .. 9,
        % Problem constraints
        % No leading zeroes
        X1 #> 0, Y1 #> 0,

          6*(100000*X1 + 10000*X2 + 1000*X3 + 100*Y1 + 10*Y2 + Y3) #= 100000*Y1 + 10000*Y2 + 1000*Y3 + 100*X1 + 10*X2 + X3,

%       Choice=input_order,             % slowest
        Choice=first_fail,              % fastest
%       Choice=most_constrained,        % medium
        search(Pattern, 0, Choice, indomain, complete, []).

