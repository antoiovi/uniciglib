/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

import it.iovino.fluidi.Miscela.Frazione;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Aria base : implementazione di default dell'interfaccia Fluido, 
 * con valori costatntiti tipici dell'aria a composozione tipica
 * @author antoiovi
 */
public class Aria_base extends AbstractFluido {
Miscela aria;
Fluido o2= OssigenoO2.getInstance();
Fluido n2=AzotoN2.getInstance();
public static final double massamolare=28.96;
public static final double R = FluidiMath.Ru/massamolare;
public static  final double Tcritica=132.52;
public   static final double Pcritica=3.78502e-6;


public   Aria_base(){
	PairMoliFluido ossigeno=new PairMoliFluido(21,o2);
	PairMoliFluido azoto=new PairMoliFluido(79,n2);
	
	List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
	listmoli.add(azoto);
	listmoli.add(ossigeno);
	aria=new Miscela(listmoli,0);
	/** coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
	 * 
	 * Formula Termodinamica eTrasmissione del calore
	 ***/
	    	a=28.11;
	    	b=-0.19671e-2;;
	    	c=0.48021e-5;
	    	d=-1.9661e-9;
	    	/**
	    	* vISCOSITA DINAMICA fORMULA DI sUTHERLKAN
	    	***/

	    	mu0=18.27e-6;
	    	T0=291.15;
	    	C=120.0;
}

@Override
public double CostElasticita() {
	return R;
}

@Override
public double MassaMolare() {
	return massamolare;
}


 
public double CapTerm_(double Temp) {
String s=String.format("%1.3f\t%1.3f\t%e\t%e",a,b,c,d);
/**
* cp/R [J/K kg]
*/
double x=a+b*Temp+c*Math.pow(Temp,2)+d*Math.pow(Temp, 3);
/**
 * cp [J/K kg]
 */
return x*1000/R;

}

/****
* cONDUCIBILITA TERMICA ARIA . fORMULA sTEPHAN AND lAESECKE
* oTTIMO!!
* 15/06/2015
* 
**/
 
 
public double CondicTermica_(double Temper) {
	double coeff[]={33.9729,-164.703,262.1085,-21.5346855,-443.456,607.3396,-368.79,111.2967,-13.4122};
	double espon[]={   -1   ,-2/3, -1/3, 0, 1/3,2/3,1, 4/3,5/3};

	double LAMBDA=4.358*e-3;
double Tr=Temper/Tcritica;
double T_exp[]=new double[espon.length];
for(int x=0;x<espon.length;x++){
  
  T_exp[x]=Math.pow(Tr,espon[x]);
}
double res=0;
for(int i=0;i<coeff.length;i++){
  res+=(coeff[i]*T_exp[i]);
		}
/**
 * W/(m K)
 **/
return res*LAMBDA;
}


/**
* vISCOSITA DINAMICA fORMULA DI sUTHERLKAN
***/

@Override
public double ViscositaDinamica(double temperatura){
  return Sutherland(temperatura);
  
}

public double DiffusivitaTermica(double Temperatura) {
	return aria.DiffusivitaTermica(Temperatura);
}


public double ViscCin(double Pressione, double Temperatura) {
	return aria.ViscCin(Pressione, Temperatura);
}

public double NumeroPrandtl(double Pressione, double Temperatura) {
	return aria.NumeroPrandtl(Pressione, Temperatura);
}

@Override
public void CreaMap(Map<Fluido, Double> map, Double val, List frazioni) {
	aria.CreaMap(map, val, frazioni);
}

@Override
public List<Frazione> getFrazioni_ponderali() {
	// TODO Auto-generated method stub
	return aria.frazioni_ponderali;
}

@Override
public List<Frazione> getFrazioni_molari() {
	// TODO Auto-generated method stub
	return aria.frazioni_ponderali;
}

    
}
