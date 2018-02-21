package it.iovino.fluidi;

import it.iovino.fluidi.Miscela.Frazione;

import java.util.List;

public class MetanoCH4 extends AbstractFluido {
public final double massamolare=16.04;//g/mole 
private static MetanoCH4 instance=null;

private MetanoCH4() {
	//init("azoto");
}

public static MetanoCH4 getInstance() {
    if(instance==null){
        instance=new MetanoCH4();
    }
    return instance;
}

	@Override
	public double MassaMolare() {
		// TODO Auto-generated method stub
		return massamolare;
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
