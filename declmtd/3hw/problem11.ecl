% Kyle Wong
% Declarative Methods Homework 3
% 14.4.11
% Problem 11
%
% Inorder traversal
%
% compile and run $ rlwrap eclps
% compile('problem11.ecl').
% 
% [eclipse 2]: inorder1(t(d,t(b,t(a,nil,nil),t(c,nil,nil)),t(e,nil,nil)),List).
% 
% List = [a, b, c, d, e]
% Yes (0.00s cpu)
% 

:- lib(ic).
:- lib(branch_and_bound).

% Empty tree gives empty list traversal
inorder1(nil,[]).

% Inorder traversal does left, middle, then right
% Find the left, then append middle, and then find the rightright
inorder1(t(Label,L,R),Keys) :- 
    inorder1(L,Ls), 
    append(Ls, [Label|Rs],Keys),
    inorder1(R,Rs).

% Inorder traversal improved - find the children after specifying the relationship
inorder2(nil,[]).
inorder2(t(Label,L,R),Keys) :- 
    append(Ls, [Label|Rs],Keys),
    inorder2(L,Ls), 
    inorder2(R,Rs).


total_depth(nil,0).
total_depth(t(_,nil,nil),0).
total_depth(t(_,L,R),Total) :- 
  depth(L,Ldepth),
  depth(R,Rdepth),
  Total is Ldepth + Rdepth. 


depth(nil,0).
depth(t(_,Left,Right), D) :-
  depth(Left,Leftdepth), 
  depth(Right,Rightdepth), 
  D is max(Leftdepth,Rightdepth)+1.

inorder3(nil,[]).
inorder3(t(Label,L,R),Keys) :-
  minimize( (total_depth(t(Label,L,R),Depth), Cost is Depth), Cost).
  




