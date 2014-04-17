% Kyle Wong
% Declarative Methods Homework 3
% 14.4.11
% Problem 10
%
% Isomorphic binary trees
%
% compile and run $ rlwrap eclps
% compile('problem10.ecl').
% 

isotree(nil,nil).

% Two trees are isomorphic if their Labels are the same and their left and right subtrees match
isotree(t(Label,L1,R1),t(Label,L2,R2)) :- isotree(L1,L2), isotree(R1,R2). 

% Two trees are isomorphic if their children match in mirror fashion
isotree(t(Label,L1,R1),t(Label,L2,R2)) :- isotree(L1,R2), isotree(R1,L2), !. 

