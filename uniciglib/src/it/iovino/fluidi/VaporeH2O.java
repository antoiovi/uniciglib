package it.iovino.fluidi;

public class VaporeH2O extends AbstractFluido {
	private final double massamolare=18.015;
	private static VaporeH2O instance=null;
	private VaporeH2O(){
	// coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
	//INUTILIZZATI
		a=1;
	b=-0.1571*10e-2;;
	c=0.8081*10e-5;
	d=-2.873*10e-9;
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

}
