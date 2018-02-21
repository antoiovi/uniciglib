package it.iovino.fluidi.combustione;

import java.util.ArrayList;
import java.util.List;

import it.iovino.fluidi.AzotoN2;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.OssigenoO2;
import it.iovino.fluidi.PairMoliFluido;
import it.iovino.fluidi.VaporeH2O;
import it.iovino.fluidi.MetanoCH4;
import it.iovino.fluidi.AnidrideCO2;
/**
 * Dati che mi servono %CO2 %O2 Massa fumi temperatura fumi
 * se consoc Co2 e o2 come calcolo eccesso d'aria?
 * @author antoiovi
 *
 */
public class CombMetano {
	Fluido o2= OssigenoO2.getInstance();
	Fluido co2= AnidrideCO2.getInstance();
	Fluido n2=AzotoN2.getInstance();
	Fluido h20=VaporeH2O.getInstance();
	// Pressione e temperatura in "Condizioni Normali" 
	public static final double PressioneNormale=101325;
	public static final double TemperaturaNormale=273.15;
private double co2_perc_secca;
private double co2_perc_umida;
private double o2_perc_secca;
private double o2_perc_umida;
private double n2_perc_umida;
private double n2_perc_secca;

double volumi_h20;
double volumi_co2;
double volumi_o2;
double  volumi_n2;
double  volumi_totali;

private double massa_molare_fumi;
private double portata;
private double calore;
private Miscela fumo;
private double ecc_aria;
private double indice_aria;
public static final double pot_cal_inf=34.0;// MJ/m3	

public Fluido CombustioneStech(){
	//double moli=massa/MetanoCH4.getInstance().massamolare;
	PairMoliFluido vapore=new PairMoliFluido(2,h20);
	PairMoliFluido anidrideco2=new PairMoliFluido(1,co2);
	PairMoliFluido azoto=new PairMoliFluido(2*3.76,n2);
	List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
	listmoli.add(anidrideco2);
	listmoli.add(vapore);
	listmoli.add(azoto);
	fumo=new Miscela(listmoli,0);
	return fumo;
	
}
/**
 * Creo un Fluido (implemetnazione di Miscela) 
 * @param ecc_aria
 * @return
 */
public Fluido Combustione(double ecc_aria){
	//double moli=massa/MetanoCH4.getInstance().massamolare;
	this.ecc_aria=ecc_aria;
	double ecc_aria_perc=ecc_aria/100;
	indice_aria=1+ecc_aria_perc;
	volumi_h20=2;
	volumi_co2=1;
	volumi_o2=2*ecc_aria_perc;
	volumi_n2=7.52+7.52*ecc_aria_perc;
	volumi_totali=volumi_h20+volumi_co2+volumi_o2+volumi_n2;
	
	PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
	PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
	PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
	PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
	List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
	listmoli.add(anidrideco2);
	listmoli.add(vapore);
	listmoli.add(azoto);
	listmoli.add(ossig);
	co2_perc_umida=volumi_co2/volumi_totali;
	co2_perc_secca=volumi_co2/(volumi_totali-volumi_h20);
	o2_perc_umida=volumi_o2/volumi_totali;
	o2_perc_secca=volumi_o2/(volumi_totali-volumi_h20);
	n2_perc_umida=volumi_n2/volumi_totali;
	n2_perc_secca=volumi_n2/(volumi_totali-volumi_h20);

	fumo=new Miscela(listmoli,0);
	return fumo;
	
}


/**
 * Crero un Miscela e restituisco una portatat:
 * Utilizzo chiamare la funzione, poi tramite getFumo ottenere il Fluido;
 * 
 * @param potenza kw
 * @param ecc_aria
 * @return
 */
public double Portata(double potenza, double ecc_aria){	
//metri cubi metano Kw /1000=MJ -->/pci--> / MJ/m3
	/**
	 * m3 metri cubi(3) metano
	 */
	double m3=potenza/(1000*pot_cal_inf);
//double m3=1;
	
	this.ecc_aria=ecc_aria;
	double ecc_aria_perc=ecc_aria/100;
	indice_aria=1+ecc_aria_perc;
	// Sono le frazioni di volume dei compnenti: 
	// infatti la legge dei gas è p*v=n RT
	// quindi a uguali volumi è dato lo stesso numero di molecole,
	// MA A AUGUALI CONDIZIONI DI PRESSIONE E TMEPRATURA;	
	// QUINDI PER CALOCLARE LA portata massica devo calcolare òa densità;
	// LA DENSITA(MAssaVolumicA) verra calcolata coin i valori riferiti al NormalMetrocubo
	// Ovvero 101
	volumi_h20=2*m3;
	volumi_co2=1*m3;
	volumi_o2=2*ecc_aria_perc*m3;
	volumi_n2=(7.52+7.52*ecc_aria_perc)*m3;
	// su fumi umidi   
	//7.52+7.52p+2p+1+2=tot    9.52p=tot-10.52   p=(tot-10.52)/9.52 
	//Volumi totatli: ovvero la somma delle frazioni di volumi "specifici"
	volumi_totali=(volumi_h20+volumi_co2+volumi_o2+volumi_n2);
	PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
	PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
	PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
	PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
	List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
	listmoli.add(anidrideco2);
	listmoli.add(vapore);
	listmoli.add(azoto);
	listmoli.add(ossig);
	co2_perc_umida=volumi_co2/volumi_totali;
	co2_perc_secca=volumi_co2/(volumi_totali-volumi_h20);
	o2_perc_umida=volumi_o2/volumi_totali;
	o2_perc_secca=volumi_o2/(volumi_totali-volumi_h20);
	n2_perc_umida=volumi_n2/volumi_totali;
	n2_perc_secca=volumi_n2/(volumi_totali-volumi_h20);
	fumo=new Miscela(listmoli,0);
	/**
	 * Calcola portata massica fumi
	 */
	return volumi_totali*fumo.MassaVolumica(PressioneNormale, TemperaturaNormale);
}

/**
 * Data la percentuale di co2 per volumi secchi calcolo i fumi riferiti alla
 *  combustione di un metro cubo di metano
 * @param co2_secco_reale
 * @return portata massica fumi per 1 metro cubo di metano
 */
public double ConbustioneConCO2(double co2_secco_reale ){
	if(co2_secco_reale>11.73)
		return -1;
	/**
 * m3=metri cubi: 1 metro cubo
 */
	double m3=1;
	double tot=1/(co2_secco_reale/100) ;
	double ecc_aria_perc=(tot-8.52)/9.52;
	this.ecc_aria=ecc_aria_perc*100;
	indice_aria=1+ecc_aria_perc;
	// Sono le frazioni di volume dei compnenti: 
	// infatti la legge dei gas è p*v=n RT
	// quindi a uguali volumi è dato lo stesso numero di molecole,
	// MA A AUGUALI CONDIZIONI DI PRESSIONE E TMEPRATURA;	
	// QUINDI PER CALOCLARE LA portata massica devo calcolare òa densità;
	// LA DENSITA(MAssaVolumicA) verra calcolata coin i valori riferiti al NormalMetrocubo
	// Ovvero 101
	volumi_h20=2*m3;
	volumi_co2=1*m3;
	volumi_o2=2*ecc_aria_perc*m3;
	volumi_n2=(7.52+7.52*ecc_aria_perc)*m3;
	// su fumi umidi   
	//7.52+7.52p+2p+1+2=tot    9.52p=tot-10.52   p=(tot-10.52)/9.52 
	// su fumi secchi   
	//7.52+7.52p+2p+1=tot    9.52p=tot-8.52   p=(tot-8.52)/9.52
	//Volumi totatli: ovvero la somma delle frazioni di volumi "specifici"
	volumi_totali=(volumi_h20+volumi_co2+volumi_o2+volumi_n2);
//	String s=String.format("Potenza %f\t metricubi %f\t eccaria %f \n",potenza,m3,p);
	//s=s+String.format("v h20 %f\t vco2 %f\t vo2 %f\t vn2 %f \n",volumi_h20,volumi_co2,volumi_o2,volumi_n2);
	PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
	PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
	PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
	PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
	List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
	listmoli.add(anidrideco2);
	listmoli.add(vapore);
	listmoli.add(azoto);
	listmoli.add(ossig);
	co2_perc_umida=volumi_co2/volumi_totali;
	
	co2_perc_secca=volumi_co2/(volumi_totali-volumi_h20);
	o2_perc_umida=volumi_o2/volumi_totali;
	o2_perc_secca=volumi_o2/(volumi_totali-volumi_h20);
	n2_perc_umida=volumi_n2/volumi_totali;
	n2_perc_secca=volumi_n2/(volumi_totali-volumi_h20);
	fumo=new Miscela(listmoli,0);
	// Portata massica =densita x portata volumetrica
	//s=s+String.format("volumitotali %f Massamolare %f\t volumi x massamolare %f \tPortata in kh/s %f\n",volumi_totali*1000,MassaMolare(),volumi_totali*1000*fumo.MassaMolare(),volumi_totali*1000*fumo.MassaVolumica(101325,273));
	//System.out.println(s);
	/**
	 * Calcola portata massica fumi
	 */
	return volumi_totali*fumo.MassaVolumica(PressioneNormale, TemperaturaNormale);
}
/**
 * Calcolo la composizione fumi e la portata massica riferita al volume 
 * di metano calcolato in base alla potenza del generatore
 * @param co2_secco_reale
 * @param potenza kW
 * @return Portata massica fumi
 */
public double PortataConCO2(double co2_secco_reale ,double potenza){
	if(co2_secco_reale>11.73)
	return -1;
	//metri cubi metano Kw /1000=MJ -->/pci--> / MJ/m3
	double m3=potenza/(1000*pot_cal_inf);
//double m3=1;
	double tot=1/(co2_secco_reale/100) ;
	double ecc_aria_perc=(tot-8.52)/9.52;
	this.ecc_aria=ecc_aria_perc*100;
	indice_aria=1+ecc_aria_perc;
	// Sono le frazioni di volume dei compnenti: 
	// infatti la legge dei gas è p*v=n RT
	// quindi a uguali volumi è dato lo stesso numero di molecole,
	// MA A AUGUALI CONDIZIONI DI PRESSIONE E TMEPRATURA;	
	// QUINDI PER CALOCLARE LA portata massica devo calcolare òa densità;
	// LA DENSITA(MAssaVolumicA) verra calcolata coin i valori riferiti al NormalMetrocubo
	// Ovvero 101
	volumi_h20=2*m3;
	volumi_co2=1*m3;
	volumi_o2=2*ecc_aria_perc*m3;
	volumi_n2=(7.52+7.52*ecc_aria_perc)*m3;
	// su fumi umidi   
	//7.52+7.52p+2p+1+2=tot    9.52p=tot-10.52   p=(tot-10.52)/9.52 
	// su fumi secchi   
	//7.52+7.52p+2p+1=tot    9.52p=tot-8.52   p=(tot-8.52)/9.52
	//Volumi totatli: ovvero la somma delle frazioni di volumi "specifici"
	volumi_totali=(volumi_h20+volumi_co2+volumi_o2+volumi_n2);
//	String s=String.format("Potenza %f\t metricubi %f\t eccaria %f \n",potenza,m3,p);
	//s=s+String.format("v h20 %f\t vco2 %f\t vo2 %f\t vn2 %f \n",volumi_h20,volumi_co2,volumi_o2,volumi_n2);
	PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
	PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
	PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
	PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
	List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
	listmoli.add(anidrideco2);
	listmoli.add(vapore);
	listmoli.add(azoto);
	listmoli.add(ossig);
	co2_perc_umida=volumi_co2/volumi_totali;
	
	co2_perc_secca=volumi_co2/(volumi_totali-volumi_h20);
	o2_perc_umida=volumi_o2/volumi_totali;
	o2_perc_secca=volumi_o2/(volumi_totali-volumi_h20);
	n2_perc_umida=volumi_n2/volumi_totali;
	n2_perc_secca=volumi_n2/(volumi_totali-volumi_h20);
	fumo=new Miscela(listmoli,0);
	/**
	 * Calcola portata massica fumi
	 */
	return volumi_totali*fumo.MassaVolumica(PressioneNormale, TemperaturaNormale);
}


public double pressioneParzialeVapore(double press){
	if(fumo==null)
		return -1;
	return fumo.PressioneParzial(h20, press);
}

/**
 * Formua 49 uni10640 : calcola la temperatura fumi uscita generatore in base al rendimento ed
 * un coeffoiciente caratteristico
 * Gas natura = 0.95 Alla potenza nomina 1.80 Potenza nomina minima
 * GPL =		   1 Alla potenza nomina 1.80 Potenza nomina minima
 * Gas natura = 1.05 Alla potenza nomina 1.80 Potenza nomina minima
 * @param C
 * @param rend
 * @return
 */
public static double TemperaturaFumi(double C,double rend){
return 293.15+C*(1-rend)*1000 ;	
}

public double getCo2_perc_secca() {
	return co2_perc_secca;
}
public double getCo2_perc_umida() {
	return co2_perc_umida;
}
public double getO2_perc_secca() {
	return o2_perc_secca;
}
public double getO2_perc_umida() {
	return o2_perc_umida;
}
public double getPortata() {
	return portata;
}
public double getCalore() {
	return calore;
}
/**
 *  Recuperare il fumo dopo la creazione
 * @return
 */
public Fluido getFumo() {
	return fumo;
}

public double getEcc_aria() {
	return ecc_aria;
}
public double MassaMolare() {
	return fumo.MassaMolare();
}


public Fluido getO2() {
	return o2;
}
public Fluido getCo2() {
	return co2;
}
public Fluido getN2() {
	return n2;
}
public Fluido getH20() {
	return h20;
}
public static double getPressionenormale() {
	return PressioneNormale;
}
public static double getTemperaturanormale() {
	return TemperaturaNormale;
}
public double getN2_perc_umida() {
	return n2_perc_umida;
}
public double getN2_perc_secca() {
	return n2_perc_secca;
}
public double getVolumi_h20() {
	return volumi_h20;
}
public double getVolumi_co2() {
	return volumi_co2;
}
public double getVolumi_o2() {
	return volumi_o2;
}
public double getVolumi_n2() {
	return volumi_n2;
}
public double getVolumi_totali() {
	return volumi_totali;
}
public double getMassa_molare_fumi() {
	return massa_molare_fumi;
}
public static double getPotCalInf() {
	return pot_cal_inf;
}
public double getIndice_aria() {
	return indice_aria;
}





}
