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
public final class AzotoN2 extends AbstractFluido{
 Logger logger=Logger.getLogger("it.iovino.fluidi");
private final static double massamolare=28.013;
private static final double R=FluidiMath.Ru/massamolare;


/***
 * 12/05/2015 Punto critico
 **/
double temper_c=126.192;
double press_c=3.3958e6;
private static AzotoN2 instance=null;

    private AzotoN2() {
// coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
    	// coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
    	a=0.36748*10;
    	b=-0.12082*10e-2;;
    	c=0.2324*10e-5;
    	d=-0.63218*10e-9;
    	e=-0.22577*10e-9;
   // moltiplicatore per valori colonna presi da tabella
    	expDiffTerm=1e-5;
    	expViscDin=1e-5;
 	// non si utilizza se visc cinematica la calcolo invece che prenderla dalla tabella
    	expViscCin=1e-5;
    	/**
    	* vISCOSITà DINAMICA : FORMULA DI SUTHERLAND
    	**/
    	mu0=17.81e-6;
    	T0=300.55;
    	C=111.0;
       init("azoto");
    }

    public static AzotoN2 getInstance() {
        if(instance==null){
            instance=new AzotoN2();
        }
        return instance;
    }
    
    
   
    @Override
 public double MassaMolare() {
    return massamolare;
    }
   
 

@Override
public double CapTerm(double Temp) {
String s=String.format("%1.3f\t%1.3f\t%e\t%e",a,b,c,d);
/**
* cp/R [J/K kg]
*/
double x=a+b*Temp+c*Math.pow(Temp,2)+d*Math.pow(Temp, 3)+e*Math.pow(Temp, 4);
/**
 * cp [J/K kg]
 */

return x*R;

}
   
/***
 * 12/06/2015 Conducibilta termica secondo formula trovata valida per basse pressini
 * **/

public double ConducibilitaTermica(double temper){
double a =15e-6;
double b=Math.sqrt(temper);
double c=235.5/temper;
double d=Math.pow(temper,(-12/temper));
double num=a*b;
double den=1+c*d;
double cter_cal_cm_s=1e4*num/den;
return cter_cal_cm_s*4.186*100; // W/(m s)

}

/**
* vISCOSITà DINAMICA : FORMULA DI SUTHERLAND
**/
 
@Override
public double ViscositaDinamica(double temperatura){
  return Sutherland(temperatura);
  
}
    @Override
    public String toString() {
        return "N2"; //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public List<Frazione> getFrazioni_ponderali() {
//Frazione f= new Frazione(1.0,this.getInstance());
return null;
	}

	@Override
	public List<Frazione> getFrazioni_molari() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
     *Costante dei gas J/(K kmol)
     */
     @Override
     public double CostElasticita() {
      return R;
     }  

    

    
}
