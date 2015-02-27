package it.iovino.fluidi;

public class AnidrideCO2 extends AbstractFluido {

	private final double massamolare=44.010;
	private static AnidrideCO2  instance=null;

	    private AnidrideCO2 () {
	// coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
	    	a=28.9;
	    	b=-0.1571*10e-2;;
	    	c=0.8081*10e-5;
	    	d=-2.873*10e-9;
	   // moltiplicatore per valori colonna presi da tabella
	    	expDiffTerm=1e-5;
	    	expViscDin=1e-5;
	 	// non si utilizza se visc cinematica la calcolo invece che prenderla dalla tabella
	    	expViscCin=1e-5;
	       init("anidridecarbonicaCO2");
	    }

	    public static AnidrideCO2  getInstance() {
	        if(instance==null){
	            instance=new AnidrideCO2 ();
	        }
	        return instance;
	    }
	    
	    
	   
	    @Override
	 public double MassaMolare() {
	    return massamolare;
	    }
	   

	    @Override
	    public String toString() {
	        return "CO2"; //To change body of generated methods, choose Tools | Templates.
	    }


}
