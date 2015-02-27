/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.antoiovi.unicig.tubi;

/**
 *Implementa l'interfaccia tubo, e non dipende dalla forma; 
 *viene utilizzato in tuboCoasAbstract per creare un tubo indipendentemente dalla 
 *forma, con tutte le proietà prese da tubi di forma definita
 * @author antoiovi
 */
public class Tubo_base implements Tubo{
double Lung;
double Resterm;
double Dh;
double Dhe;
double Per_est;
double Per_int;
double Rug;


    public Tubo_base(double Lung, double Resterm, double Dh, double Dhe, double Per_est, double Per_int, double Rug) {
        this.Lung = Lung;
        this.Resterm = Resterm;
        this.Dh = Dh;
        this.Dhe = Dhe;
        this.Per_est = Per_est;
        this.Per_int = Per_int;
        this.Rug = Rug;
    }

    Tubo_base() {
        
    }

    public Tubo_base(double Lung, double Resterm, double Rug) {
        this.Lung = Lung;
        this.Resterm = Resterm;
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
    public double Dh() {
        return this.Dh;
    }

    @Override
    public double Dhe() {
        return this.Dhe;
    }

    

    @Override
    public double Per_est() {
        return this.Per_est;
    }

    @Override
    public double Per_int() {
        return this.Per_int;
    }

    @Override
    public double Rug() {
        return this.Rug;
    }

    @Override
    public double Area_int() {
        return Per_int*Dh/4;
    }

    @Override
    public double Area_est() {
        return Per_est*Dhe/4;
    }
    
	@Override
	public double Spessore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRug(double rugosita) {
		this.Rug=rugosita;
		
	}

	@Override
	public void setResterm(double restermica) {
		this.Resterm=restermica;
		
	}

	@Override
	public void setLung(double lunghezza) {
		this.Lung=lunghezza;		
	}

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
