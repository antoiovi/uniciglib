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
		a=0.40701*10;
    	b=-0.11084*10e-2;;
    	c=0.41521*10e-5;
    	d=-0.29637*10e-8;
    	e=0.80702*10e-12;
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
public String toString() {
    return "VaporeH2O"; 
}

@Override
public double MassaMolare() {
	return massamolare;
}
 

 

@Override
public double CapTerm(double Temp) {
/**
	 * cp/R [J/K kg]
	 */
	double x = a + b * Temp + c * Math.pow(Temp, 2) + d * Math.pow(Temp, 3)
			+ e * Math.pow(Temp, 4);
	/**
	 * cp [J/K kg]
	 */
	return x * R;

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
