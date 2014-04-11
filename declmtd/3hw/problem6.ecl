% Kyle Wong
% Declarative Methods Homework 3
% 14.4.10
% Problem 6
%
% Cut operator and cutmember
%
% compile and run $ rlwrap eclps
% compile('problem6.ecl').
% 

cutmember(X,List) :- member(X,List), !.


