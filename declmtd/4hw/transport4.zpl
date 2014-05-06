# Use constant parameters for supply, demand, and transport costs.
# This lets us further simplify and expose the scheme of the
# equations.  We can now write the objective as a sum of param*var products,
# and collapse all 3 supply constraints onto a single line with "forall"
# (similarly for the 4 demand constraints).

set Producer := {"alice","bob","carol"};
set Consumer := {1 to 4};
var send[Producer*Consumer] >= -10000;

param supply[Producer] := <"alice"> 500, <"bob"> 300, <"carol"> 400;
param demand[Consumer] := <1> 200, <2> 400, <3> 300, <4> 100;
param transport_cost[Producer*Consumer] :=         | 1, 2, 3, 4|
		     		           |"alice"|10, 8, 5, 9|
		     		           |"bob"  | 7, 5, 5, 3|
		     		           |"carol"|11,10, 8, 7|;

subto supply: forall <p> in Producer: (sum <c> in Consumer: send[p,c]) <= supply[p];
subto demand: forall <c> in Consumer: demand[c] == (sum <p> in Producer: send[p,c]);
minimize cost: sum <p,c> in Producer*Consumer: transport_cost[p,c] * send[p,c];
