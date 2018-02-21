package it.iovino.fluidi.combustione;

import java.util.ArrayList;
import java.util.List;

import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.PairMoliFluido;

public class CombustioneMetano extends CombustioneBase {

	public CombustioneMetano(){
		/**GAS NATURALE
		 * 1 W= 1 J/s    1 J=1 [W]* 1[s]=1 W*S 
		 * 1 MJ= 1000 kW*s
		 * 1kW*s= 1/3600 kW/h
		 * 1 [MJ]*1000/3600 [kW*h]=0.27 [kW*h]
		 * 34 [MJ]= 34*0.27[kW*h]=9.44 [kW*h] al m3
		 * metro cubo Standard : condizioni standard (c.s.) : T=273.15 K(0 °C)P=100 kPa 
		 * densità condizioni standard (c.s.)= 0.71682 kg/m3
		 * 1 kg=1/0.71682 =1.395 m3/kg 
		 * 34 [MJ/m3]= 34/ 0.71682=~47 MJ/kg
		 * 		ovvero per avere un kg devo avere 1.395 m3 
		 * 					1.395 m3/kg*34 MJ/m3= 47 MJ/kg
		 * 
		 */
		pot_cal_inf=34.4;// MJ/m3 = 9.44 kW*h/m3
		/**
		* Formua 49 uni10640 : calcola la temperatura fumi uscita generatore in base al rendimento ed
		* un coeffoiciente caratteristico
		* Gas natura = 0.95 Alla potenza nomina 1.80 Potenza nomina minima
		* GPL =		   1 Alla potenza nomina 1.80 Potenza nomina minima
		* Gas natura = 1.05 Alla potenza nomina 1.80 Potenza nomina minima*/
		C_fatt_calc_temp_max=0.95;
		C_fatt_calc_temp_min=1.8;
		
		/**
		 *  Per la combustione stechiometrica di un metro cubo di metano
		 * 		CH4+ 2O2  --> CO2+2H2O	
		 * 		Per 1 m3 metano occorrono 2 m3 O2
		 * 		Aria N2/O2= 79/21  = 3.76 
		 * 		1 m3 O2-->3.76 m3 N2=4.76 m3 Aria
		 *		2 m3 O2--> 7,52 m3 N2 
		 *	
		 *		 CH4+ 2O2+ 7,52N2 --> CO2+2H2O+ 7,5N2
		 */
		/**
		 * Di deafult calcolo per 1 m3 metano eccesso aria 0;
		 */
		 CombustioneEccAria(0.0);
	}
	
	@Override
	public Fluido CombustioneEccAria(double ecc_aria) {
/**
 * Per la combustione stechiometrica di un metro cubo di metano
 * 		CH4+ 2O2  --> CO2+2H2O	
 * 		Per 1 m3 metano occorrono 2 m3 O2
 * 		Aria N2/O2= 79/21  = 3.76 
 * 		1 m3 O2-->3.76 m3 N2=4.76 m3 Aria
 *		2 m3 O2--> 7,52 m3 N2 
 *	
 *		 CH4+ 2O2+ 7,52N2 --> CO2+2H2O+ 7,5N2
 */
		
		this.volume_gas=1.0;
		this.ecc_aria=ecc_aria;
		double p=ecc_aria/100;
		volumi_h20=2;
		volumi_co2=1;
		volumi_o2=2*p;
		volumi_n2=7.52+7.52*p;
		volumi_fumo_totali=volumi_h20+volumi_co2+volumi_o2+volumi_n2;
		List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
		PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
		listmoli.add(vapore);
		PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
		listmoli.add(anidrideco2);
		PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
		listmoli.add(ossig);
		PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
		listmoli.add(azoto);
		
		co2_perc_umida=volumi_co2/volumi_fumo_totali;
		co2_perc_secca=volumi_co2/(volumi_fumo_totali-volumi_h20);
		
		o2_perc_umida=volumi_o2/volumi_fumo_totali;
		o2_perc_secca=volumi_o2/(volumi_fumo_totali-volumi_h20);
		
		n2_perc_umida=volumi_n2/volumi_fumo_totali;
		n2_perc_secca=volumi_n2/(volumi_fumo_totali-volumi_h20);
		
		h2o_perc=volumi_h20/volumi_fumo_totali;
				
		fumo=new Miscela(listmoli,0);/**
		 * Calcola portata massica fumi Portata massica =densita x portata volumetrica
		 */
		this.portata_mass_fumi=volumi_fumo_totali*fumo.MassaVolumica(PressioneNormale, TemperaturaNormale);
		potenza=volume_gas*(1000*pot_cal_inf);//Potenza in kw
		return fumo;
	}

	@Override
	public Fluido CombustioneCO2Secc(double co2_secco_reale) {
		if(co2_secco_reale>11.74)
			return null;
		/**
		 * m3 metri cubi(3) metano
		 * metri cubi metano Kw /1000=MJ -->/pci ( MJ/m3)--> m3
		 
		 * kw= 1000 w =1000 J/s
		 * 1 kw = 1/1000 MJ/s
		 * potenza[kw]/1000=MJ/s
		 * (potenza[kw]/1000)/(pot_cal_inf[MJ/m3])=[MJ/s]/[MJ/m3]=m3/s;
		 */
		volume_gas=1;
		double tot=1/(co2_secco_reale/100) ;
		double p=(tot-8.52)/9.52;
		this.ecc_aria=p*100;
		// Sono le frazioni di volume dei compnenti: 
		// infatti la legge dei gas è p*v=n RT
		// quindi a uguali volumi è dato lo stesso numero di molecole,
		// MA A AUGUALI CONDIZIONI DI PRESSIONE E TMEPRATURA;	
		// QUINDI PER CALOCLARE LA portata massica devo calcolare òa densità;
		// LA DENSITA(MAssaVolumicA) verra calcolata coin i valori riferiti al NormalMetrocubo
		// Ovvero 101
		volumi_h20=2*volume_gas;//CH4+ 2O2+ 7,52N2 --> CO2+2H2O+ 7,5N2
		volumi_co2=1*volume_gas;//CH4+ 2O2+ 7,52N2 --> CO2+2H2O+ 7,5N2
		volumi_o2=2*p*volume_gas;//CH4+ 2O2+ 7,52N2 --> CO2+2H2O+ 7,5N2
		volumi_n2=(7.52+7.52*p)*volume_gas;
		/**
			su fumi umidi   
				7.52+7.52p+2p+1+2=tot    9.52p=tot-10.52   p=(tot-10.52)/9.52 
		 	su fumi secchi   
			7.52+7.52p+2p+1=tot    9.52p=tot-8.52   p=(tot-8.52)/9.52
		Volumi totatli: ovvero la somma delle frazioni di volumi "specifici"
		***/
		volumi_fumo_totali=(volumi_h20+volumi_co2+volumi_o2+volumi_n2);
		List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
		PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
		listmoli.add(vapore);
		PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
		listmoli.add(anidrideco2);
		PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
		listmoli.add(ossig);
		PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
		listmoli.add(azoto);
		
		co2_perc_umida=volumi_co2/volumi_fumo_totali;
		co2_perc_secca=volumi_co2/(volumi_fumo_totali-volumi_h20);
		
		o2_perc_umida=volumi_o2/volumi_fumo_totali;
		o2_perc_secca=volumi_o2/(volumi_fumo_totali-volumi_h20);
		
		n2_perc_umida=volumi_n2/volumi_fumo_totali;
		n2_perc_secca=volumi_n2/(volumi_fumo_totali-volumi_h20);
		
		h2o_perc=volumi_h20/volumi_fumo_totali;
				
		fumo=new Miscela(listmoli,0);
		/**
		 * Calcola portata massica fumi Portata massica =densita x portata volumetrica
		 */
		this.portata_mass_fumi=volumi_fumo_totali*fumo.MassaVolumica(PressioneNormale, TemperaturaNormale);	
		potenza=volume_gas*(1000*pot_cal_inf);//Potenza in kw
		return fumo;	
	}

	@Override
	public Fluido CombustioneEccAria(double _potenza, double _ecc_aria){	
		this.ecc_aria=_ecc_aria;
		this.potenza=_potenza;
		/**
		 * m3 metri cubi(3) metano
		 * metri cubi metano Kw /1000=MJ -->/pci ( MJ/m3)--> m3
		 */
		volume_gas=potenza/(1000*pot_cal_inf);
		double p=ecc_aria/100;
		// Sono le frazioni di volume dei compnenti: 
		// infatti la legge dei gas è p*v=n RT
		// quindi a uguali volumi è dato lo stesso numero di molecole,
		// MA A AUGUALI CONDIZIONI DI PRESSIONE E TMEPRATURA;	
		// QUINDI PER CALOCLARE LA portata massica devo calcolare òa densità;
		// LA DENSITA(MAssaVolumicA) verra calcolata coin i valori riferiti al NormalMetrocubo
		// Ovvero 101500 pA E 0°C
		volumi_h20=2*volume_gas;
		volumi_co2=1*volume_gas;
		volumi_o2=2*p*volume_gas;
		volumi_n2=(7.52+7.52*p)*volume_gas;
		// su fumi umidi   
		//7.52+7.52p+2p+1+2=tot    9.52p=tot-10.52   p=(tot-10.52)/9.52 
		//Volumi totatli: ovvero la somma delle frazioni di volumi "specifici"
		volumi_fumo_totali=(volumi_h20+volumi_co2+volumi_o2+volumi_n2);
		List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
		PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
		listmoli.add(vapore);
		PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
		listmoli.add(anidrideco2);
		PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
		listmoli.add(ossig);
		PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
		listmoli.add(azoto);
		
		co2_perc_umida=volumi_co2/volumi_fumo_totali;
		co2_perc_secca=volumi_co2/(volumi_fumo_totali-volumi_h20);
		
		o2_perc_umida=volumi_o2/volumi_fumo_totali;
		o2_perc_secca=volumi_o2/(volumi_fumo_totali-volumi_h20);
		
		n2_perc_umida=volumi_n2/volumi_fumo_totali;
		n2_perc_secca=volumi_n2/(volumi_fumo_totali-volumi_h20);
		
		h2o_perc=volumi_h20/volumi_fumo_totali;
				
		fumo=new Miscela(listmoli,0);
		/**
		 * Calcola portata massica fumi
		 */
		this.portata_mass_fumi= volumi_fumo_totali*fumo.MassaVolumica(PressioneNormale, TemperaturaNormale);
		return fumo;
	}

	@Override
	public Fluido CombustioneCO2Secc(double co2_secco_reale ,double _potenza){
		this.potenza=_potenza;
		if(co2_secco_reale>11.73)
			return null;
			//metri cubi metano Kw /1000=MJ -->/pci--> / MJ/m3
		/**
		 * kw= 1000 w =1000 J/s
		 * 1 kw = 1/1000 MJ/s
		 * potenza[kw]/1000=MJ/s
		 * (potenza[kw]/1000)/(pot_cal_inf[MJ/m3])=[MJ/s]/[MJ/m3]=m3/s;
		 */
			 volume_gas=potenza/(1000*pot_cal_inf);
		//double m3=1;
			double tot=1/(co2_secco_reale/100) ;
			double p=(tot-8.52)/9.52;
			this.ecc_aria=p*100;
			// Sono le frazioni di volume dei compnenti: 
			// infatti la legge dei gas è p*v=n RT
			// quindi a uguali volumi è dato lo stesso numero di molecole,
			// MA A AUGUALI CONDIZIONI DI PRESSIONE E TMEPRATURA;	
			// QUINDI PER CALOCLARE LA portata massica devo calcolare òa densità;
			// LA DENSITA(MAssaVolumicA) verra calcolata coin i valori riferiti al NormalMetrocubo
			// Ovvero 101500 pA E 0°C
			volumi_h20=2*volume_gas;
			volumi_co2=1*volume_gas;
			volumi_o2=2*p*volume_gas;
			volumi_n2=(7.52+7.52*p)*volume_gas;
			// su fumi umidi   
			//7.52+7.52p+2p+1+2=tot    9.52p=tot-10.52   p=(tot-10.52)/9.52 
			// su fumi secchi   
			//7.52+7.52p+2p+1=tot    9.52p=tot-8.52   p=(tot-8.52)/9.52
			//Volumi totatli: ovvero la somma delle frazioni di volumi "specifici"
			volumi_fumo_totali=(volumi_h20+volumi_co2+volumi_o2+volumi_n2);
			List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
			PairMoliFluido vapore=new PairMoliFluido(volumi_h20,h20);
			listmoli.add(vapore);
			PairMoliFluido anidrideco2=new PairMoliFluido(volumi_co2,co2);
			listmoli.add(anidrideco2);
			PairMoliFluido ossig=new PairMoliFluido(volumi_o2,o2);
			listmoli.add(ossig);
			PairMoliFluido azoto=new PairMoliFluido(volumi_n2,n2);
			listmoli.add(azoto);
			
			co2_perc_umida=volumi_co2/volumi_fumo_totali;
			co2_perc_secca=volumi_co2/(volumi_fumo_totali-volumi_h20);
			
			o2_perc_umida=volumi_o2/volumi_fumo_totali;
			o2_perc_secca=volumi_o2/(volumi_fumo_totali-volumi_h20);
			
			n2_perc_umida=volumi_n2/volumi_fumo_totali;
			n2_perc_secca=volumi_n2/(volumi_fumo_totali-volumi_h20);
			
			h2o_perc=volumi_h20/volumi_fumo_totali;
					
			fumo=new Miscela(listmoli,0);
			/**
			 * Calcola portata massica fumi [kg/s]
			 */
			this.portata_mass_fumi=	volumi_fumo_totali*fumo.MassaVolumica(PressioneNormale, TemperaturaNormale);
			return fumo;
		}

}
