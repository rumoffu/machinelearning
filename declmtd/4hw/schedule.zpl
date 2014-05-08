# Constraints for optimizing a week.

set Day := {1..7}; # part a

param base_fun_rate := 1; # part d
param sleep_deficit_rate := 0.75; # part f

# part b decisions
var work[Day];
var sleep[Day];
var play[Day];
var events[Day];

# part f sleep deficit
var sleepy[Day] binary;

# part d events
set E := { read "events.txt" as "<1s>" }; # set of events
## set D := { read "events.txt" as "<2n>" }; # set of event days
param edays[E] := read "events.txt" as "<1s> 2n"; # event days and names
param ehours[E] := read "events.txt" as "<1s> 3n"; # event names and hours used
param efunrate[E] := read "events.txt" as "<1s> 4n"; # event names and funrates

var funevents[Day];
##subto funevents: forall <d, e> in D*E : funevents[d] ==  
subto funevents: forall <e> in E : funevents[ edays[e] ] == sum <e> in E : efunrate[e] * ehours[e];

# part e assignments
set A := { read "assignments.txt" as "<1s>" }; # set of assignments
param adays[A] := read "assignments.txt" as "<1s> 2n"; # assignment name and duedate
param ahours[A] := read "assignments.txt" as "<1s> 3n"; # assignment name and hours used
param apenalty[A] := read "assignments.txt" as "<1s> 4n"; # assignment name and penaltyrates

var ahoursleft[A] real; # hours unfinished work
var workdone[A] real; # hours of effective work done

##var workloss[Day];
##subto workpenalty: forall <a> in A : workloss[ adays[a] ] == - (sum <a> in A : ahoursleft[a] * apenalthy[a]);
var workpenalty integer;
subto workpenalty: forall <a> in A : workpenalty == sum <a> in A : ahoursleft[a] * apenalty[a];

# hours unfinished is equal to required hours minus effective work done
subto dowork: forall <a> in A : ahoursleft[a] == ahours[a] - workdone[a];

# work done is equal to hours put into work -- not considering sleep deficit at the moment
subto workhours: forall <a> in A : workdone[a] == sum <d> in Day : work[d]; 

# part f
subto tired: forall <d> in Day without {1, 2} :vif (sleep[d-2] + sleep[d-1] + sleep[d] < 24) then sleepy[d] end;


var totalfun integer;

subto daylimit: forall <d> in Day : work[d] + sleep[d] + play[d] + events[d] == 24; # part a
subto minsleep: forall <d> in Day without {1, 2} : sleep[d-2] + sleep[d-1] + sleep[d] >= 18; # part c
subto workpenalty: 
subto totfun: forall <d> in Day : totalfun == base_fun_rate*play[d] + funevents[d] - workpenalty;

maximize fun: totfun;

