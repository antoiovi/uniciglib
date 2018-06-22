package it.iovino.fluidi.combustione;

import it.iovino.fluidi.Fluido;

/**
 *	
 *  @author antoiovi
 *  @version 1.0
 *	@see CombustioneMetano
 *	@see CombustioneBase
 *			
 */
public interface Combustione {
Fluido CombustioneEccAria(double ecc_aria);
Fluido CombustioneCO2Secc(double CO2_per_secc);
/**
 * 
 * @param ecc_aria (valore assoluto) da 0 in su
 * @param potenza in kilowatt
 * @return
 */
Fluido CombustioneEccAria(double ecc_aria,double potenza);
/**
 * 
 * @param CO2_per_secc
 * @param potenza in kw
 * @return
 */
Fluido CombustioneCO2Secc(double CO2_per_secc,double potenza);
 /**
*Formua 49 uni10640 : calcola la temperatura fumi uscita generatore alla potenza NOMINALE 
*   in base al rendimento ed  un coeffoiciente caratteristico
*		 Gas natura = 0.95 Alla potenza nomina 1.80 Potenza nomina minima
* 		GPL =		   1 Alla potenza nomina 1.80 Potenza nomina minima
* 		Gas natura = 1.05 Alla potenza nomina 1.80 Potenza nomina minima
 * @param rend percentuale (max 100)
 * @return
 */
double TemperaturaFumiPmax(double rend);

 /**
*Formua 49 uni10640 : calcola la temperatura fumi uscita generatore alla potenza MINIMA 
*   in base al rendimento ed  un coeffoiciente caratteristico
*		 Gas natura = 0.95 Alla potenza nomina 1.80 Potenza nomina minima
* 		GPL =		   1 Alla potenza nomina 1.80 Potenza nomina minima
* 		Gas natura = 1.05 Alla potenza nomina 1.80 Potenza nomina minima
 * @param rend valore assoluto massimo=100 
 * @return 
 */
double TemperaturaFumiPmin(double rend);
}
