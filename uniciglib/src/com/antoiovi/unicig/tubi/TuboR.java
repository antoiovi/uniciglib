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
public class TuboR extends TuboAbstract{
	double lato_lung;
	double lato_cort;
	double spessore;

	public TuboR(double latolungo,double latocorto,double spess,double lung, double resterm, double rug) {
		super(lung, resterm, rug);
		spessore=spess;
		this.lato_cort=latocorto;
		this.lato_lung=latolungo;
	}


    public TuboR() {
		// TODO Auto-generated constructor stub
	}


	public double getLato_lung() {
        return lato_lung;
    }

    public void setLato_lung(double lato_lung) {
        this.lato_lung = lato_lung;
    }

    public double getLato_cort() {
        return lato_cort;
    }

    public void setLato_cort(double lato_cort) {
        this.lato_cort = lato_cort;
    }
    
    
    @Override
    public double Spessore() {
    	return spessore;
    }

    @Override
    public double Per_est() {
    	double spess=Spessore();
          return 2*(lato_lung+2*spess)+2*(lato_cort+2*spess);
    }

    @Override
    public double Per_int() {
        return 2*lato_lung+2*lato_cort;
    }

    @Override
    public double Area_int() {
        return lato_lung*lato_cort;
    }

    @Override
    public double Area_est() {
    	double spess=Spessore();
        return (lato_lung+2*spess)*(lato_cort+2*spess);
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
 		spessore=spess;
 		
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
