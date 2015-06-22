package com.antoiovi.caldaie;

import java.util.ArrayList;
import java.util.List;

import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.PairMoliFluido;
import it.iovino.fluidi.combustione.CombMetano;

public class Caldaiach4 implements Caldaia {
private double rend_pmax;
private double rend_pmin;
private double co2_max_p;
private double co2_min_p;
private double ecc_aria_max;
private double ecc_aria_min;
private double ind_aria_max;
private double ind_aria_min;

private double potenza_max;
private double potenza_min;
private double p_fumi_pmax;
private double p_fumi_pmin;
private Fluido fumoPmax;
private Fluido fumoPmin;
private CombMetano combmax;
{
	combmax=new CombMetano();
}
private CombMetano combmin;
{
	combmin=new CombMetano();
}
	
	
	public Caldaiach4(double rendipmax,double rendpmin,
			double co2max,double co2min,
			double p_max,double p_min) {
	super();
	this.rend_pmax = rendipmax;
	this.rend_pmin=rendpmin;
	this.co2_max_p = co2max;
	this.co2_min_p = co2min;
	this.potenza_max=p_max;
	this.potenza_min=p_min;
	
	p_fumi_pmax=combmax.PortataConCO2(co2max, p_max);
	p_fumi_pmin=combmin.PortataConCO2(co2min, p_min);
	fumoPmax=combmax.getFumo();
	fumoPmin=combmin.getFumo();
	ecc_aria_max=combmax.getEcc_aria();
	ecc_aria_min=combmin.getEcc_aria();
	
}
	
			
			
			
	public Caldaiach4(double rendiment,double rendpmin,
			double ecc_max,double ecc_min,
			double p_max,double p_min ,int tipo) {
	super();
	this.rend_pmax = rendiment;
	this.rend_pmin=rendpmin;
	ecc_aria_max = ecc_max;
	ecc_aria_min = ecc_min;
	this.potenza_max=p_max;
	this.potenza_min=p_min;
	
	p_fumi_pmax=combmax.Portata(p_max,ecc_aria_max);
	p_fumi_pmin=combmin.Portata(p_min,ecc_aria_min);
	fumoPmax=combmax.getFumo();
	fumoPmin=combmin.getFumo();
	co2_max_p=combmax.getCo2_perc_secca();
	co2_min_p=combmin.getCo2_perc_secca();
	ind_aria_max=combmax.getIndice_aria();
	ind_aria_min=combmin.getIndice_aria();
}

	public Caldaiach4() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * imposta i dati caldaia con eccesso d'aria
	 * 	tipo serve solo per distnguere il prototipo  della funzione
	 * @param rendiment
	 * @param rendpmin
	 * @param ecc_max
	 * @param ecc_min
	 * @param p_max
	 * @param p_min
	 * @param tipo
	 */
public void setData(double rendiment,double rendpmin,
			double ecc_max,double ecc_min,
			double p_max,double p_min,int tipo) {
	 
	this.rend_pmax = rendiment;
	this.rend_pmin=rendpmin;
	ecc_aria_max = ecc_max;
	ecc_aria_min = ecc_min;
	
	this.potenza_max=p_max;
	this.potenza_min=p_min;
	
	p_fumi_pmax=combmax.Portata(p_max,ecc_aria_max);
	p_fumi_pmin=combmin.Portata(p_min,ecc_aria_min);
	
	ind_aria_max=combmax.getIndice_aria();
	ind_aria_min=combmin.getIndice_aria();
			
	
	fumoPmax=combmax.getFumo();
	fumoPmin=combmin.getFumo();
	co2_max_p=combmax.getCo2_perc_secca();
	co2_min_p=combmin.getCo2_perc_secca();
}

/**
 * Imposta i dati caldaia con co2co2 da inserire con valore percentuale da 0 a 11.73, viene poi
 * ricalcolato e impostato in valore assoluto
 * @param rendipmax
 * @param rendpmin
 * @param co2max da 0 a 11.73
 * @param co2min
 * @param p_max
 * @param p_min
 */
public void setData(double rendipmax,double rendpmin,
		double co2max,double co2min,
		double p_max,double p_min) {

this.rend_pmax = rendipmax;
this.rend_pmin=rendpmin;
this.co2_max_p = co2max;
this.co2_min_p = co2min;
this.potenza_max=p_max;
this.potenza_min=p_min;

p_fumi_pmax=combmax.PortataConCO2(co2max, p_max);
p_fumi_pmin=combmin.PortataConCO2(co2min, p_min);
fumoPmax=combmax.getFumo();
fumoPmin=combmin.getFumo();
ecc_aria_max=combmax.getEcc_aria();
ecc_aria_min=combmin.getEcc_aria();
ind_aria_max=combmax.getIndice_aria();
ind_aria_min=combmin.getIndice_aria();
co2_max_p=combmax.getCo2_perc_secca();
co2_min_p=combmin.getCo2_perc_secca();

}

public double pressioneParzialeVaporePmax(double patm){
	return combmax.pressioneParzialeVapore(patm);
}


	@Override
	public double getRend_Pmax() {
		return rend_pmax;
	}
	@Override
	public double getRend_Pmin() {
		return rend_pmin;
	}

	@Override
	public Fluido getFumoPmax() {
		return fumoPmax;/*
		PairMoliFluido fumo_c=new PairMoliFluido(1,fumoPmax);
		List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
		listmoli.add(fumo_c);
		return new Miscela(listmoli,0);*/
	}

	@Override
	public Fluido getFumoPmin() {
		return fumoPmin;/*
		PairMoliFluido fumo_c=new PairMoliFluido(1,fumoPmin);
		List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
		listmoli.add(fumo_c);
		return new Miscela(listmoli,0);*/
	}

	@Override
	public double getTempFumiPmax() {
		return CombMetano.TemperaturaFumi(1.8, rend_pmax);
	}

	@Override
	public double getTempFumiPmin() {
		return CombMetano.TemperaturaFumi(0.9, rend_pmin);	}

	@Override
	public double getPortatfumiPmax() {
		return this.p_fumi_pmax;
	}

	@Override
	public double getPortatfumiPmin() {
		return this.p_fumi_pmin;
	}

	@Override
	public double getCO2percentPmax() {
return this.co2_max_p;
}

	@Override
	public double getCO2percentPmin() {
		return this.co2_min_p;	}

	@Override
	public double getEccariaPmax() {
		return this.ecc_aria_max;
	}

	@Override
	public double getEccariaPmin() {
		return this.ecc_aria_max;	}

	@Override
	public double getPmax() {
		return this.potenza_max;
	}

	@Override
	public double getPmin() {
		return this.potenza_min;
	}
	@Override
	public double getIndiceAriaPmax() {
		// TODO Auto-generated method stub
		return ind_aria_max;
	}
	@Override
	public double getIndiceAriaPmin() {
		// TODO Auto-generated method stub
		return ind_aria_min;
	}

	
}
