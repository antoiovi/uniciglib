package com.antoiovi.unicig.impianti;
import java.util.List;

import com.antoiovi.caldaie.Caldaia;
import com.antoiovi.unicig.condotti.Condotto;
public abstract class Imp_unicig_base {

int n_piani;
double temp_est[];
double temp_locali[];
double p_atm[];
List<Condotto> condotti;
List<Condotto> canali;
List<Caldaia> caldaie;

	
}
