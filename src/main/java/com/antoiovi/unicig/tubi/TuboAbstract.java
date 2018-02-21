/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.antoiovi.unicig.tubi;

/**
 * Implementa la logia per il calcolo dei diametri idraulici,
 * indipendentemente dalla forma, 
 * @author antoiovi
 */
abstract class TuboAbstract implements Tubo {
protected double Lung;
/**
 * Resistenza termica 
 * m2/(K W)
 */
protected  double Resterm;
protected double Rug;


public TuboAbstract(){
	super();
}

     public TuboAbstract(double lung, double resterm, double rug) {
	super();
	Lung = lung;
	Resterm = resterm;
	Rug = rug;
}

	public double getLung() {
        return Lung;
    }

    public void setLung(double Lung) {
        this.Lung = Lung;
    }

    public double getResterm() {
        return Resterm;
    }

    public void setResterm(double Resterm) {
        this.Resterm = Resterm;
    }

    public double getRug() {
        return Rug;
    }

    public void setRug(double Rug) {
        this.Rug = Rug;
    }



    
    @Override
    public double Lung() {
        return this.Lung;
    }

    @Override
    public double Resterm() {
        return this.Resterm;
    }



    @Override
    public double Rug() {
        return this.Rug;
    }

    @Override
    public double Dh() {
        return 4*Area_int()/Per_int();
    }

    @Override
    public double Dhe() {
        return 4*Area_est()/Per_est();
    }
    
    
}
