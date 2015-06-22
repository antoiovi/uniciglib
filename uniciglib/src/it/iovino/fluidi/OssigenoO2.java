/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

import it.iovino.fluidi.Miscela.Frazione;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antoiovi
 */
public class OssigenoO2 extends AbstractFluido{
private final double massamolare=31.999;
private   final double R = FluidiMath.Ru / massamolare;

private static OssigenoO2 instance=null;

    protected OssigenoO2() {
       init("ossigeno");
       a=25.48;
       b=1.520e-2;
       c=-0.7155e-5;
       d=1.312e-9;
    // moltiplicatore per valori colonna presi da tabella
   	expDiffTerm=1e-5;
   	expViscDin=1e-5;
	// non si utilizza se visc cinematica la calcolo invece che prenderla dalla tabella
   	expViscCin=10e-5;
   	/**
   	 * VALORI PER CALCOLO VISCOSITA CON FORMULA DI SUTHERLAND
   	 */
   	mu0=20.18e-6;
	T0=292.25;
	C=127.0;
    }
    
     public static OssigenoO2 getInstance() {
        if(instance==null){
            instance=new OssigenoO2();
        }
        return instance;
    }
   
    @Override
 public double MassaMolare() {
    return massamolare;
    }
 
    @Override
	public double CapTerm(double Temp) {

		/**
		 * PER L'OSSIGENO FORMULA TRATTA DA DISPENSE
		 * Formala tratta da Termodinamica e trasmissione del calore Ca
		 * cp/R [J/K kg]
		 */
		  a=0.36256*10;
       b=-0.18782*1e-2;
       c=0.70555*1e-5;
       d=-0.67635*1e-8;
       e=0.21556*1e-11;
       
		double x = a + b * Temp + c * Math.pow(Temp, 2) + d * Math.pow(Temp, 3)
				+ e * Math.pow(Temp, 4);
		/**
		 * cp [J/K kg]
		 */
		return x * R;

	}
	
	@Override
	public double ViscositaDinamica(double temperatura){
	  return Sutherland(temperatura);
	  
	}
    
    /*
     *Costante dei gas J/(K kmol)
     */
     @Override
     public double CostElasticita() {
      return R;
     }
    
    @Override
    public String toString() {
        return "O2"; //To change body of generated methods, choose Tools | Templates.
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
