/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.antoiovi.unicig.condotti;

import com.antoiovi.unicig.tubi.Tubo;

import it.iovino.fluidi.Fluido;

import java.util.Iterator;
import java.util.List;

/**
 *  Rappresenta la serie di più condotti, aventi qundi la stessa portata di fluido
 * poichè possono avere dverse sezioni, si può avere differente velocità fluido e quindi 
 * la perdita localizzata dovuta a variazione di velocità
 * @author antoiovi
 */
public class CondottoSerie implements Condotto{
    double Haltezza;// Altezza totale condotto H=0 tubo orizzontele
    List<Condotto> condotti;// Più tubi in serie per considerare i cambi di sezione
    protected double Patm;
    double massvolumaria;      
    public double getMassvolumaria() {
		return massvolumaria;
	}

	protected double CoefLimE;
    protected double M1;// Portata massica fluido interno
    protected double Ta;//Temperatura ambiente
    protected double Tfi;//Temperatura iniziale
    protected double Tm;//Temperatura media
    protected Fluido FluidoI;
    protected Fluido aria;
    
    double SE;
    
    
    VelocitaFluido velocitaFluido;
    VarTempNonCoas vartempnoncoas;

    @Override
    public void CalcolaTemper() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Somma(Condotto c1, Condotto c2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double M1() {
        return M1;
    }

   

    @Override
    public double Wm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double Tm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double Tu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    @Override
    public double Ps(double B) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double Peff(double PressStaticSup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public double Peff() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public double d_P() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double d_P(double csi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double ro_m() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Serie(Condotto c1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Fluido fl_int() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setM1(double M1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public void setH(double h) {
	if(h>=Lung()){
		Haltezza=Lung();
	}else{
		Haltezza=h;
	}}
	
    @Override
    public double H() {
        return Haltezza;
    }
    
    
    @Override
    public Fluido fl_est() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Condotto condottoi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Condotto condottoe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public double Lung() {
	double l=0;
		Iterator<Condotto> i=condotti.iterator();
	while(i.hasNext()){
	l+=i.next().Lung();
	}
	return l;}

	@Override
	public double getMa() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double MaIntTir(double csi_i, double csi_d, double Ai, double Ad) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addFluido(Fluido f2, double m2, double T2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFluidoI(Fluido fluidoi, double m2, double T2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double Area() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String Forma() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double Ti() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double Dh() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double Calcola_d_P() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWm() {
		// TODO Auto-generated method stub
		return 0;
	}
}
