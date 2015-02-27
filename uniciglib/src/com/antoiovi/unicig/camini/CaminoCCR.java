package com.antoiovi.unicig.camini;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.StringWriter;

import com.antoiovi.unicig.condotti.Condotto;
public class CaminoCCR {
private List<Pressioni> primario;
private List<Pressioni> secondario;
private List<Pressioni> canale;
private double pcom;
private StringWriter swr;


/**
 * un condotto per ogni piano
 */
List<Condotto> condottiprimari;
/**
 * Un canale da fumo per ogni piano
 */
List<Condotto> condottisecondari;
/**
 * Un canale da fumo per ogni piano
 */
List<Condotto> canalifumo;

List<Double> peff_co_p;
List<Double> peff_co_s;
List<Double> peff_ca;


public CaminoCCR(List<Pressioni> tratti, double pcom) {
	super();
	this.primario = tratti;
	this.pcom = pcom;
	swr=new StringWriter();
}

public CaminoCCR(List<Pressioni> primario, List<Pressioni> secondario,
		List<Pressioni> canale, double pcom) {
	super();
	this.primario = primario;
	this.secondario = secondario;
	this.canale = canale;
	this.pcom = pcom;
	swr=new StringWriter();
}

public CaminoCCR(List<Condotto> condottiprimari,
		List<Condotto> condottisecondari, List<Condotto> canalifumo) {
	super();
	this.condottiprimari = condottiprimari;
	this.condottisecondari = condottisecondari;
	this.canalifumo = canalifumo;
	peff_co_p=new ArrayList(condottiprimari.size());
	peff_co_s=new ArrayList(condottisecondari.size());
	peff_ca=new ArrayList(canalifumo.size());
	
}

public void  calcola(){
	String format="%d\tPstatica %1.2f\t Perdite %1.2f\t Peffittive suepriori %1.2f\tPeffettive %1.2f\n";
	
	int piani=primario.size();
	for(int x=piani-1;x>=0;x--){
		double psup;
		if(x==piani-1){
			psup=-pcom;
		}else{
			psup=primario.get(x+1).peffettiva;
		}
		primario.get(x).peffettiva(psup);
		swr.write(String.format("Primario "+format, x,primario.get(x).pstatica,primario.get(x).perdite,psup,primario.get(x).peffettiva));
		double psup2=secondario.get(x).peffettiva(psup);
		swr.write(String.format("Secondario "+format, x,secondario.get(x).pstatica,secondario.get(x).perdite,psup,secondario.get(x).peffettiva));
		canale.get(x).peffettiva(psup2);
		swr.write(String.format("Canale "+format, x,canale.get(x).pstatica, canale.get(x).perdite,psup2,canale.get(x).peffettiva));
	}
	
}

public void  calcola(double pcom,double pcom_2){
	String format="%d\tPstatica %1.2f\t Perdite %1.2f\t Peffittive suepriori %1.2f\tPeffettive %1.2f\n";
	int piani=condottiprimari.size();
	
	
	for(int x=piani-1;x>=0;x--){
		double psup;
		if(x==piani-1){
			double p1=condottiprimari.get(x).Peff(-pcom);
			double p2=condottisecondari.get(x).Peff(-pcom_2);
			double pc=canalifumo.get(x).Peff(p2);
			// Per verificare la differenza nelle iterazioni successive 
			peff_co_p.set(x, p1);
			 peff_co_s.set(x, p2);
			 peff_ca.set(x, pc);
		}else{
			psup=condottiprimari.get(x+1).Peff();
			double p1=condottiprimari.get(x).Peff(psup);
			double p2=condottisecondari.get(x).Peff(psup);
			double pc=canalifumo.get(x).Peff(p2);
			// Per verificare la differenza nelle iterazioni successive
			peff_co_p.set(x, p1);
			 peff_co_s.set(x, p2);
			 peff_ca.set(x, pc);
		}
		//swr.write(String.format("Primario "+format, x,condotti.get(x).Ps(verso),condotti.get(x).d_P(),psup,condotti.get(x).Peff()));
	}
	
}

public boolean verifica(){
	boolean test=false;
	for(int x=0;x<condottiprimari.size();x++){
		Condotto c=condottiprimari.get(x);
		double t=c.Peff()-peff_co_p.get(x);
		if(t>=0.1){
			return false;		
		}
		c=condottisecondari.get(x);
		t=c.Peff()-peff_co_s.get(x);
		if(t>=0.1){
			return false;		
		}
		c=canalifumo.get(x);
		t=c.Peff()-peff_ca.get(x);
		if(t>=0.1){
			return false;		
		}
	}
return true;
}

public List<Pressioni> getSecondario() {
	return secondario;
}

public void setSecondario(List<Pressioni> tratti_2) {
	this.secondario = tratti_2;
}

public List<Pressioni> getPrimario() {
	return primario;
}

public void setPrimario(List<Pressioni> tratti) {
	this.primario = tratti;
}

public double getPcom() {
	return pcom;
}

public void setPcom(double pcom) {
	this.pcom = pcom;
}


public StringWriter getSwr() {
	return swr;
}

public void setSwr(StringWriter swr) {
	this.swr = swr;
}

public List<Pressioni> getCanale() {
	return canale;
}

public void setCanale(List<Pressioni> canale) {
	this.canale = canale;
}



}
