package com.antoiovi.unicig.camini;

import java.util.Iterator;
import java.util.List;

public class Pressioni {
public double pstatica;
public double perdite;
public  double peffettiva;

public Pressioni(double pstatica, double perdite) {
	super();
	this.pstatica = pstatica;
	this.perdite = perdite;
}

public void calcolapeff(List<Pressioni> lista,double peffsup){
	lista.remove(this);
	Iterator i=lista.iterator();
	peffettiva=peffsup+(pstatica-perdite);
	if(i.hasNext()){
		Pressioni t=(Pressioni )i.next();
		t.calcolapeff(lista, peffettiva);
	}
}
public double peffettiva(double psuperiore){
peffettiva=psuperiore+pstatica-perdite;
return peffettiva;
}

public void peffett(double psuperiore){
peffettiva=psuperiore+pstatica-perdite;

}

}
