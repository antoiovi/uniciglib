package it.iovino.fluidi.combustione;

import it.iovino.fluidi.AnidrideCO2;
import it.iovino.fluidi.AzotoN2;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.OssigenoO2;
import it.iovino.fluidi.VaporeH2O;
/**
*   
*  Contine tutte le proprietà che vengono calcolate nelle sue implementazioni e i loro getters.
*	<p> Contiene anche funzioni di servizio basiche.
*   <p> implementa anche in modo standard <br>
*		Combustione.TemperaturaFumiPmax(double rend) e <br>
*		Combustione.TemperaturaFumiPmin(double rend) <br>
*	Implementa :{@link it.iovino.fluidi.combustione.Combustione}	<br>
*   Classi che la estendono  : {@link it.iovino.fluidi.combustione.CombustioneMetano}	<br>
*	@author Antonello iovino
*   @version 1.0
*	@see CombustioneMetano
*	@see Combustione
**/
public abstract class CombustioneBase implements Combustione {
protected Fluido o2= OssigenoO2.getInstance();
protected Fluido co2= AnidrideCO2.getInstance();
protected Fluido n2=AzotoN2.getInstance();
protected Fluido h20=VaporeH2O.getInstance();
	

protected double volume_gas;
protected double co2_perc_secca;
protected double co2_perc_umida;
protected double o2_perc_secca;
protected double o2_perc_umida;
protected double n2_perc_umida;
protected double n2_perc_secca;
protected double h2o_perc;

// xxx_percM_xxx  percentuali MASSICHE
protected double co2_percM_secca;
protected double co2_percM_umida;
protected double o2_percM_secca;
protected double o2_percM_umida;
protected double n2_percM_umida;
protected double n2_percM_secca;
protected double h2o_percM;



protected  double volumi_h20;
protected double volumi_co2;
protected double volumi_o2;
protected double  volumi_n2;
protected double  volumi_fumo_totali;


protected double massa_molare_fumi;
protected double portata_mass_fumi;
protected double calore;
protected double potenza;
protected Fluido fumo;
protected double ecc_aria;

public static final double PressioneNormale=101325;
public static final double TemperaturaNormale=273.15;

protected static  double pot_cal_inf;// MJ/m3


/**
* Formua 49 uni10640 : calcola la temperatura fumi uscita generatore in base al rendimento ed
* un coeffoiciente caratteristico
* Gas natura = 0.95 Alla potenza nomina 1.80 Potenza nomina minima
* GPL =		   1 Alla potenza nomina 1.80 Potenza nomina minima
* Gas natura = 1.05 Alla potenza nomina 1.80 Potenza nomina minima
**/
protected static double C_fatt_calc_temp_max;
protected static double C_fatt_calc_temp_min;


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
@Override
public double TemperaturaFumiPmax(double rend){
	rend/=100;
return 293.15+C_fatt_calc_temp_max*(1-rend)*1000 ;	
}

/* (non-Javadoc)
 * @see it.iovino.fluidi.combustione.Combustione#TemperaturaFumiPmin(double)
 */
@Override
public double TemperaturaFumiPmin(double rend){
	rend/=100;
return 293.15+C_fatt_calc_temp_min*(1-rend)*1000 ;	
}


	
	public double getCo2_perc_secca() {
		return co2_perc_secca;
	}

	public double getH2o_perc() {
		return h2o_perc;
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
	public double getVolumi_fumo_totali() {
		return volumi_fumo_totali;
	}
	public double getVolumi_totali() {
		return volumi_fumo_totali;
	}

	public double getMassa_molare_fumi() {
		return massa_molare_fumi;
	}

	
	public double getCalore() {
		return calore;
	}

	public double getPortata_mass_fumi() {
		return portata_mass_fumi;
	}
	
	public Fluido getFumo() {
		return fumo;
	}

	public double getEcc_aria() {
		return ecc_aria;
	}
	public static double getPressionenormale() {
		return PressioneNormale;
	}
	public static double getTemperaturanormale() {
		return TemperaturaNormale;
	}
	public static double getPot_cal_inf() {
		return pot_cal_inf;
	}

	public double getVolume_gas() {
		return volume_gas;
	}

	public double getPotenza() {
		return potenza;
	}

	// Percentuali massiche
	public double getH2o_percM() {
		return h2o_percM;
	}
	
	public double getCo2_percM_secca() {
		return co2_percM_secca;
	}
	public double getCo2_percM_umida() {
		return co2_percM_umida;
	}

	public double getO2_percM_secca() {
		return o2_percM_secca;
	}

	public double getO2_percM_umida() {
		return o2_percM_umida;
	}

	public double getN2_percM_umida() {
		return n2_percM_umida;
	}

	public double getN2_percM_secca() {
		return n2_percM_secca;
	}

	
	
	
}
