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
public class TuboQ extends TuboAbstract{

double lato;
double spess;

	public TuboQ(double lato,double spess,double lung, double resterm, double rug) {
		super(lung, resterm, rug);
		this.lato=lato;
		this.spess=spess;
	}


    public double getLato() {
        return lato;
    }

    @Override
    public double Spessore() {
    return spess;
    }

    @Override
    public double Area_int() {
        return lato*lato;
    }

    @Override
    public double Area_est() {
                return (lato+2*spess)*(lato+2*spess);
    }

    @Override
    public double Per_est() {
        return 4*(lato+2*spess);
    }

    @Override
    public double Per_int() {
        return 4*lato;
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
