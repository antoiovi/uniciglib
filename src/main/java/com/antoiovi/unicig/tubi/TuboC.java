/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.antoiovi.unicig.tubi;

/**
 *
 * @author antoiovi
 */
public class TuboC extends TuboAbstract{
    double diam_int;
    double diam_est;

    

    public TuboC(double diam_int,double diam_est,double lung, double resterm, double rug) {
		super(lung, resterm, rug);
		this.diam_est=diam_est;
		this.diam_int=diam_int;
	}

	public TuboC() {
		// TODO Auto-generated constructor stub
	}

	public double getDiam_int() {
        return diam_int;
    }

    public void setDiam_int(double diam_int) {
        this.diam_int = diam_int;
    }

    public double getDiam_est() {
        return diam_est;
    }

    public void setDiam_est(double diam_est) {
        this.diam_est = diam_est;
    }

   

    @Override
    public double Spessore() {
    	return (diam_est-diam_int)/2;
    }

    @Override
    public double Area_int() {
        return Math.PI*(diam_int/2)*(diam_int/2);
    }

    @Override
    public double Area_est() {
        return Math.PI*(diam_est/2)*(diam_est/2);
    }

    @Override
    public double Per_est() {
        return Math.PI*diam_est;
    }

    @Override
    public double Per_int() {
        return Math.PI*diam_int;
    }

// I metodi seguenti non vengono utilizzati, in quanto i valori sdebono essere calcolati
// vengono inseriti solo per potere creare un tubo base per i tubi cpassiali
	@Override
	public void setDh(double dh) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setDhe(double dhe) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setSpessore(double spess) {
		diam_est=diam_int+2*spess;
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setPer_est(double per_est) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setPer_int(double per_int) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setArea_int(double area_int) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setArea_est(double area_est) {
		// TODO Auto-generated method stub
		
	}
    
    
}
