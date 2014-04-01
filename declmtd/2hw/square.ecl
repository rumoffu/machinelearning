% Kyle Wong
% Declarative Methods Homework 2
% 14.3.13
% Problem 2 e
%
% Find the smallest square number (perfect square) that uses each digit (0, 1, 2, 3, 4, 5, 6, 7, 8, 9) once and only once. 
%
% run as [kwong23@ugrad12 2hw]$ eclps -b square.ecl -e 'go'  

:- lib(ic).

go :-
        cputime(T0),
        ( solve(Solution), writeln(Solution), fail ; true ),
        T is cputime - T0,
        writeln([T, 'seconds']).

solve(Pattern) :-
        Pattern = [
                 A, B, C, D, E, F, G, H, I, J
                  ],
        Pattern :: 0 .. 9,
        P :: [1..10000000000],
        % Problem constraints
        % no leading zeroes
        A #\= 0, 
        alldifferent(Pattern),
        1000000000*A + 100000000*B +  10000000*C + 1000000*D + 100000*E + 10000*F + 1000*G + 100*H + 10*I + J #= P*P,
%       Choice=input_order,             % slowest
        Choice=first_fail,              % fastest
%       Choice=most_constrained,        % medium
        search(Pattern, 0, Choice, indomain, complete, []).


