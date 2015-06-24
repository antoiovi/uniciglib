package it.iovino.fluidi;

import it.iovino.fluidi.Miscela.Frazione;

import java.util.List;
import java.util.Map;

public class AnidrideCO2 extends AbstractFluido {

	private final double massamolare=44.010;
	  /**
     * Costante universale dei gas J/(K kmol) Ru=8314.472;
     * Costante del gas J/(kg K)
     * 
     */
	private    final double R=FluidiMath.Ru/massamolare;
	private static AnidrideCO2  instance=null;

	    private AnidrideCO2 () {
	// coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
	     	/***
	    	 * 12/06/2015 capacità termica ottenuta da tabelle NASA
	    	 *  parametri per il calcolo del polinomio ; erroe massimo 0.72%
	    	 * */
	    	a=0.2407*10;
	    	b=0.87531*e-2;;
	    	c=-0.66071*e-5;
	    	d=0.20022*e-8;
	    	e=0.63274*e-15;
	   // moltiplicatore per valori colonna presi da tabella
	    	expDiffTerm=1e-5;
	    	expViscDin=1e-5;
	 	// non si utilizza se visc cinematica la calcolo invece che prenderla dalla tabella
	    	expViscCin=1e-5;
	    	/**
	    	* vISCOSITà DINAMICA : FORMULA DI SUTHERLAND
	    	**/
	    	mu0=14.8e-6;//Pa s
	    	T0=293.15;
	    	C=240.0;
	       init("anidridecarbonicaCO2");
	    }

	    public static AnidrideCO2  getInstance() {
	        if(instance==null){
	            instance=new AnidrideCO2 ();
	        }
	        return instance;
	    }
	    
	    
	   
	    @Override
		public double CapTerm(double Temp) {
		/*****
		* 12/06/2015 Polinomio NASA
		* cp/R
		********* */
			double x = a + b * Temp + c * Math.pow(Temp, 2) + d * Math.pow(Temp, 3)
					+ e * Math.pow(Temp, 4);
			/**
			 * cp/R R[J/(kg K)]      cp =cp/R x R  [J/(kg K)]
			 */
			return x * R;

		}
	    /**
	     * Polinomio tratto da S.C. SAXENA : University of Illinois, 1970
	     */
	    @Override
	    public double CondicTermica(double t) {
	    	 
	    	 double a1=-0.05413;
		     double b1=0.7123*1e-3;
		     double c1=-0.09456*1e-6;
		     double d1=-0.08449*1e-9;
				double x = a1 + b1 * t -c1 * Math.pow(t, 2.0)
						+d1*Math.pow(t, 3.0);
				
				  
	  		return x/10 ;
	    }
	    @Override
	 public double MassaMolare() {
	    return massamolare;
	    }
	   

	    @Override
	    public String toString() {
	        return "CO2"; //To change body of generated methods, choose Tools | Templates.
	    }
	    
	    /*
	     *Costante dei gas J/(K kmol)
	     */
	     @Override
	     public double CostElasticita() {
	      return R;
	     }
	    
	     /**
	     * vISCOSITà DINAMICA : FORMULA DI SUTHERLAND
	     **/
	      
	     @Override
	     public double ViscositaDinamica(double temperatura){
	       return Sutherland(temperatura);
	       
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
