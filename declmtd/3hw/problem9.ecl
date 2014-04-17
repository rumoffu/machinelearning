% Kyle Wong
% Declarative Methods Homework 3
% 14.4.11
% Problem 9
%
% constraint logic programming
%
% compile and run $ rlwrap eclps
% compile('problem9.ecl').
% 
% try q(L,20)
% [eclipse 5]: q(L,20).
% lists.eco  loaded traceable 0 bytes in 0.00 seconds
% 
% L = [20]
% Yes (0.00s cpu, solution 1, maybe more) ? ;
% 
% L = [2, 10]
% Yes (0.00s cpu, solution 2, maybe more) ? ;
% 
% L = [4, 5]
% Yes (0.00s cpu, solution 3, maybe more) ? ;
% 
% L = [5, 4]
% Yes (0.00s cpu, solution 4, maybe more) ? ;
% 
% L = [10, 2]
% Yes (0.00s cpu, solution 5, maybe more) ? ;
% 
% L = [2, 2, 5]
% Yes (0.00s cpu, solution 6, maybe more) ? ;
% 
% L = [2, 5, 2]
% Yes (0.00s cpu, solution 7, maybe more) ? ;
% 
% L = [5, 2, 2]
% Yes (0.00s cpu, solution 8, maybe more) ? ;
% 
% and q([7,R],20) returns No while q([7|Rs],20) fails to halt
%
% Part e [425] all diff
% ===========================================================
% 
% [eclipse 14]: adiff([X,Y,Z]).
% 
% X = X{-1.0Inf .. 1.0Inf}
% Y = Y{-1.0Inf .. 1.0Inf}
% Z = Z{-1.0Inf .. 1.0Inf}
% 
% 
% Delayed goals:
%         -(Z{-1.0Inf .. 1.0Inf}) + X{-1.0Inf .. 1.0Inf} #\= 0
%         -(Y{-1.0Inf .. 1.0Inf}) + X{-1.0Inf .. 1.0Inf} #\= 0
%         -(Z{-1.0Inf .. 1.0Inf}) + Y{-1.0Inf .. 1.0Inf} #\= 0
% Yes (0.00s cpu, solution 1, maybe more) ? ;
% 
% X = X{-1.0Inf .. 1.0Inf}
% Y = Y{-1.0Inf .. 1.0Inf}
% Z = Z{-1.0Inf .. 1.0Inf}
% 
% 
% Delayed goals:
%         -(Z{-1.0Inf .. 1.0Inf}) + X{-1.0Inf .. 1.0Inf} #\= 0
%         -(Y{-1.0Inf .. 1.0Inf}) + X{-1.0Inf .. 1.0Inf} #\= 0
%         -(Z{-1.0Inf .. 1.0Inf}) + Y{-1.0Inf .. 1.0Inf} #\= 0
% Yes (0.00s cpu, solution 2)
% [eclipse 15]: adiff([V,W,X,Y,Z]).
% 
% V = V{-1.0Inf .. 1.0Inf}
% W = W{-1.0Inf .. 1.0Inf}
% X = X{-1.0Inf .. 1.0Inf}
% Y = Y{-1.0Inf .. 1.0Inf}
% Z = Z{-1.0Inf .. 1.0Inf}
% 
% There are 10 delayed goals. Do you want to see them? (y/n) 
% 
% Delayed goals:
%         -(Z{-1.0Inf .. 1.0Inf}) + V{-1.0Inf .. 1.0Inf} #\= 0
%         -(Y{-1.0Inf .. 1.0Inf}) + V{-1.0Inf .. 1.0Inf} #\= 0
%         -(X{-1.0Inf .. 1.0Inf}) + V{-1.0Inf .. 1.0Inf} #\= 0
%         -(W{-1.0Inf .. 1.0Inf}) + V{-1.0Inf .. 1.0Inf} #\= 0
%         -(Z{-1.0Inf .. 1.0Inf}) + W{-1.0Inf .. 1.0Inf} #\= 0
%         -(Y{-1.0Inf .. 1.0Inf}) + W{-1.0Inf .. 1.0Inf} #\= 0
%         -(X{-1.0Inf .. 1.0Inf}) + W{-1.0Inf .. 1.0Inf} #\= 0
%         -(Z{-1.0Inf .. 1.0Inf}) + X{-1.0Inf .. 1.0Inf} #\= 0
%         -(Y{-1.0Inf .. 1.0Inf}) + X{-1.0Inf .. 1.0Inf} #\= 0
%         -(Z{-1.0Inf .. 1.0Inf}) + Y{-1.0Inf .. 1.0Inf} #\= 0


:- lib(ic).
p([],1).
p([X|Xs],A) :- p(Xs,B), A #= X*B.

q(List,N) :- p(List,N), List::2..N, labeling(List).

adiff([]).
adiff([_]).
%adiff([X|Xs]) :- member(Y,Xs), X #\= Y, restdiff(X,Xs), adiff(Xs).
adiff([X|Xs]) :- restdiff(X,Xs), adiff(Xs).

restdiff(_,[]).
restdiff(X,[Y|Ys]) :- restdiff(X,Ys), X #\= Y.
