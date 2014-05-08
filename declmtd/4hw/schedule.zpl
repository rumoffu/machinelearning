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
param edays[E] := read "events.txt" as "<1s> 2n"; # event names, days
#param einfo[E*Day] := read "events.txt" as "<1s> 2n, 3n"; # event names, days and hours
param ehours[E] := read "events.txt" as "<1s> 3n"; # event names, hours
param efunrate[E] := read "events.txt" as "<1s> 4n"; # event names and funrates

var goevent[E] binary; #whether or not we go
var gohours[E] real; #hours we spent actually going
var eventfun real;
var playfun real;
var workpenalty real;
var totalfun real;

subto gohour: forall <e> in E: gohours[e] == goevent[e]*ehours[e];

var eperday[Day*E]; # event hours per day
subto eusehours: forall <e> in E: eperday[edays[e], e] == goevent[e]*ehours[e]; 
subto goevents: forall <d> in Day: events[d] == sum <e> in E: eperday[d, e];

subto calcfun: eventfun == sum <e> in E: efunrate[e] * gohours[e];


# part e assignments
set A := { read "assignments.txt" as "<1s>" }; # set of assignments
param adays[A] := read "assignments.txt" as "<1s> 2n"; # assignment name and duedate
param ahours[A] := read "assignments.txt" as "<1s> 3n"; # assignment name and hours used
param apenalty[A] := read "assignments.txt" as "<1s> 4n"; # assignment name and penaltyrates

do print A;

#var ahoursleft[A] real; # hours unfinished work
var workdone[A] real; # hours of effective work done
var wperday[Day*A]; # efftive work hours done per day

##var workloss[Day];
##subto workpenalty: forall <a> in A : workloss[ adays[a] ] == - (sum <a> in A : ahoursleft[a] * apenalthy[a]);
subto workpenalty: workpenalty == sum <a> in A : apenalty[a] * (ahours[a] - workdone[a]);

# hours unfinished is equal to required hours minus effective work done
#subto dowork: forall <a> in A : ahoursleft[a] == ahours[a] - workdone[a];

# work done is equal to hours put into work -- not considering sleep deficit at the moment
##subto workhours: forall <a> in A : workdone[a] == sum <d> in Day : work[d]; 
#subto workhours: forall <a, d> in A*Day : if (d <= adays[a]) then workdone[a] == sum <d> in Day : work[d]; 

# part f
##subto tired: forall <d> in Day without {1, 2} :vif (sleep[d-2] + sleep[d-1] + sleep[d] < 24) then sleepy[d] == 1 end;
param m := -25;
subto tired: forall <d> in Day without {1, 2} : sleep[d-2] + sleep[d-1] + sleep[d] >= 24 + 0.001 + (m-0.001)*sleepy[d];



subto daylimit: forall <d> in Day : work[d] + sleep[d] + play[d] + events[d] == 24; # part a
#subto daylimit: forall <d> in Day :  sleep[d] + play[d] + events[d] == 24; # part a
subto minsleep: forall <d> in Day without {1, 2} : sleep[d-2] + sleep[d-1] + sleep[d] >= 18; # part c
#subto totfun: forall <d> in Day : totalfun == base_fun_rate*play[d] + funevents[d] - workpenalty;
#subto totfun: forall <d> in Day : totalfun == base_fun_rate*play[d] + eventfun;
subto totfun: playfun == sum <d> in Day: base_fun_rate*play[d];
#subto totfun: forall <d> in Day : totalfun == base_fun_rate*play[d];
#subto addfun: totalfun == playfun + eventfun - workpenalty;
subto addfun: totalfun == playfun + eventfun;
#maximize fun: playfun + eventfun - workpenalty;
maximize fun: totalfun;
