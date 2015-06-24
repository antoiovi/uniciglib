package it.iovino.fluidi;

import it.iovino.fluidi.Miscela.Frazione;

import java.util.List;

public class VaporeH2O extends AbstractFluido {
	private final static double massamolare=18.015;
	private static final double R=FluidiMath.Ru/massamolare;
	private static VaporeH2O instance=null;
	private VaporeH2O(){
	// coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
	//INUTILIZZATI
	/*	a=0.40701*10;
    	b=-0.11084*1e-2;;
    	c=0.41521*1e-5;
    	d=-0.29637*1e-8;
    	e=0.80702*1e-12;*/
// moltiplicatore per valori colonna presi da tabella
	expDiffTerm=1e-5;
	expViscDin=1e-5;
	// non si utilizza se visc cinematica la calcolo invece che prenderla dalla tabella
	expViscCin=1e-5;
   init("vapore_h20");
}

public static VaporeH2O getInstance() {
    if(instance==null){
        instance=new VaporeH2O();
    }
    return instance;
}

@Override
public double CapTerm(double Temp) {
	/**
	*Parametri tratti da Libro cagel . Fonte B.G. Kyle 
	**/
	a=32.24;
	b=0.1923e-2;
	c=1.055e-5;
	d=-3.595e-9;
	
	
/**
	 * cp/R [J/K m]
	 */
	double x = a + b * Temp + c * Math.pow(Temp, 2.0) + d * Math.pow(Temp, 3.0);
	/**
	 * cp [J/K kg]
	 */
	return x*R/10 ;

}	

/**
24/06/2015
*	Conducibilta termica : SENGERS ET AL. (nist.gov)
**/
@Override
public double CondicTermica(double temper){
	/*double a0=1.02811*1e-2;
	double a1=2.99621*1e-2;
	double a2=1.56146*1e-2;
	double a3=-0.00422464;*/
	double k=0;
	double a_i[]={	1.02811*1e-2,2.99621*1e-2,1.56146*1e-2,	-0.00422464	};
	double t_ref=647.3;
	double T=temper/t_ref;
	for(int x=0;x<4;x++){
		k+=(a_i[x]*Math.pow(T,x));
	}
	return k*Math.sqrt(T);
	
	
	
}
@Override
public String toString() {
    return "VaporeH2O"; 
}

@Override
public double MassaMolare() {
	return massamolare;
}
 

 

/*
 *Costante dei gas J/(K kmol)
 */
 @Override
 public double CostElasticita() {
  return R;
 }
@Override
public List<Frazione> getFrazioni_ponderali() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Frazione> getFrazioni_molari() {
	// TODO Auto-generated method stub
	return null;
}

}
