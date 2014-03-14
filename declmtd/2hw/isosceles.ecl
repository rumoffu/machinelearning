% Kyle Wong
% Declarative Methods Homework 2
% 14.3.13
% Problem 2 c
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
                 X,Y
                  ],
        Pattern :: 0 .. 1000,
        P :: 0 .. 1000,
        A :: 0 .. 100000,
        % Problem constraints
        % assume sides X, X, Y
        X*X #= H*H+(Y/2)*(Y/2),
        P #= 2*X + Y,
        A #= (Y * H)/2,
        6*P #= A,

%       Choice=input_order,             % slowest
        Choice=first_fail,              % fastest
%       Choice=most_constrained,        % medium
        search(Pattern, 0, Choice, indomain, complete, []),
        printf("P: %d A: %d", [P, A]).


