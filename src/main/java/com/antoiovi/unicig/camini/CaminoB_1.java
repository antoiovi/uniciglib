package com.antoiovi.unicig.camini;

import it.iovino.fluidi.AnidrideCO2;
import it.iovino.fluidi.Aria_base;
import it.iovino.fluidi.AzotoN2;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.OssigenoO2;
import it.iovino.fluidi.VaporeH2O;
import it.iovino.utilita.Mylogger;
import it.iovino.utilita.SystemLogger;

import com.antoiovi.caldaie.Caldaia;
import com.antoiovi.unicig.condotti.Condotto;
import com.antoiovi.unicig.condotti.CondottoBase;
import com.antoiovi.unicig.condotti.FormatOutputUtility;
import com.antoiovi.unicig.condotti.VarTempNonCoas;
import com.antoiovi.unicig.condotti.VelocitaFluido;

public class CaminoB_1 {
	public	Caldaia caldaia;
	public CondottoBase condotto;
	public CondottoBase canale;
	  public double getCsi_comign() {
		return csi_comign;
	}
/**
 * Utilizzato per calcolare la  pressione parziale vapore nel fumo
 */
	  Fluido h20=VaporeH2O.getInstance();
	  Fluido n2=AzotoN2.getInstance();
	  Fluido co2=AnidrideCO2.getInstance();
	Fluido o2=OssigenoO2.getInstance();
	public void setCsi_comign(double csi_comign) {
		this.csi_comign = csi_comign;
	}


	double csi_comign=0;
	
	/*public double csi_int_tir=50;
	public double csi_presa_ar=0.5;
	public double area_presa=0.5;

	public double area_int_tir=0.03;  */

	public double Patm;
	public double temper_est;
	double fraz_per_cont_esterno=0;
	double tpu_temper_par_U;
	public double getFraz_per_cont_esterno() {
		return fraz_per_cont_esterno;
	}


	public void setFraz_per_cont_esterno(double fraz_per_cont_esterno) {
		this.fraz_per_cont_esterno = fraz_per_cont_esterno;
	}
	public double temper_int;
	public double getTemper_int() {
		return temper_int;
	}


	public void setTemper_int(double temper_int) {
		this.temper_int = temper_int;
	}


	public double dp_com;
	public double dp_con;
	public double dp_can;
	public double ps_ca;
	public double peff_ca;
	public double peff_ca_base;
	public double ps_cond;
	public double peff_cond;
	
	public double vel_min_cond;
	public double vel_min_can;
	 private PresaariaB presaaria;
	
	 
	public CaminoB_1( ) {
		super();
		//presaaria=new PresaariaB( csi_int_tir, csi_presa_ar , area_int_tir, area_presa );
		 logger=new SystemLogger();
			logger.setLevel(Mylogger.FINEST);
			//logger.setLevel(Mylogger.INFO);
			
		
			
	}

	
	public void setPresaaria(PresaariaB presaaria) {
		this.presaaria = presaaria;
		presaaria.setLogger(logger);
	}


	Aria_base aria=new Aria_base();
	public double maria;
	private Mylogger logger;
	private boolean vel_con_ok;
	private boolean vel_can_ok;

 static final int REPORT=Mylogger.MESSAGE;
	public void calcola() {
//presaaria.setLogger(logger);
		logger.clean();
		presaaria.setLogger(logger);
String message="--------------------------\n----- CALCOLO CAMINO -------\n-------------------";
logger.appendMessage(REPORT,message);
		         message=String.format("\nDati apertura aria        :\n\t Area %1.4f  Coefficiente perdite localizzate %1.4f", presaaria.Ad,presaaria.csi_d);
		logger.appendMessage(REPORT,message);
		       message=String.format("\n\tDati interruttore tiraggio:\n\t Area %1.4f  Coefficiente perdite localizzate %1.4f ", presaaria.Ai,presaaria.csi_i);
		logger.appendMessage(REPORT,message);
		StringBuilder strbuild=new StringBuilder();
		strbuild.append("\nDati generatore : ")
		.append(String.format("\n\tParametro    | a potena massima\t| a potenza minima"))
		.append(String.format("\n\tPotenza      | %1.1f     kW    \t|  %1.1f kW", caldaia.getPmax(),caldaia.getPmin()))
		.append(String.format("\n\tRendimento   | %1.2f %%        \t|  %1.2f %%", caldaia.getRend_Pmax()*100,caldaia.getRend_Pmin()*100))
		.append(String.format("\n\tCO2          | %1.2f %%        \t|  %1.2f %%", caldaia.getCO2percentPmax()*100,caldaia.getCO2percentPmin()*100))
		.append(String.format("\n\tEccesso aria | %1.2f %%        \t|  %1.2f %%", caldaia.getEccariaPmax(),caldaia.getEccariaPmin()))
		.append(String.format("\n\tIndice d'aria| %1.2f           \t|  %1.2f   ", caldaia.getIndiceAriaPmax(),caldaia.getIndiceAriaPmin()));
		
		
		logger.appendMessage(REPORT,strbuild.toString());
		 
		condotto.setLogger(logger);
		canale.setLogger(logger);
		//maria = 0;
		
	 eseguiCalcolo(PMAX);
	 /* ***02/2018 	 

	 Miscela fumouscita=(Miscela)condotto.getFluidoI();
	 double fraz_mol_h20=fumouscita.getFrazioneMolare(h20);
	double fraz_mol_o2= fumouscita.getFrazioneMolare(o2);
	String frazmol= String.format("\n  Fraazioni molari fumo uscita : O2 %1.3f\tH2O %1.3f", fraz_mol_o2,fraz_mol_h20);
	 logger.appendMessage(Mylogger.INFO, frazmol);
	 double pressParzVapore=fumouscita.PressioneParzial(h20, Patm);
	 logger.appendMessage(Mylogger.INFO,String.format(">\nPressione parziale vapore fumi uscita camino %1.4f", pressParzVapore));
	 
	 
	 ***/
	 /**
	  * VERIFICHE
	  */
	  
	  
	  /* ***02/2018 	 
	 tpu_temper_par_U=verificaTemperatura();
	 logger.appendMessage(REPORT,String.format("\n%-35s\t%1.3f","Temperatura parete uscita camino",tpu_temper_par_U));
	 
	 logger.appendMessage(Mylogger.INFO,"\n\tVERIFICHE");
	 vel_min_cond=velocitaMinima(condotto.Area());
		vel_min_can=velocitaMinima(canale.Area());
		vel_con_ok = ( condotto.Wm()>vel_min_cond);
		vel_can_ok = ( canale.Wm()>vel_min_can);
		String msgcond=vel_con_ok?"Verifica POSITIVA":"Verifica NEGATIVA";
		String msgcan=vel_can_ok?"Verifica POSITIVA":"Verifica NEGATIVA";
	 logger.appendMessage(Mylogger.INFO, String.format(
				"\n Verifica velocità fumi condotto :\t %s"
				+ "\n\t Area %f\tVelocita %f \t Velocita minima %f ",msgcond,condotto.Area(), condotto.Wm(),vel_min_cond));
	 logger.appendMessage(Mylogger.INFO, String.format(
				"\n Verifca velocità fumi canale da fumo:\t %s \n\tArea %f\tVelocita %f \t Velocita minima %f ",msgcan, canale.Area(),canale.Wm(),vel_min_can)); 
	****/
	 
	 // canale.MaIntTir(csi_int_tir, csi_presa_ar, area_int_tir, area_presa);
	}// calcola();

	static final int PMAX=1;
	static final int PMIN=2;
	/**
	 * 
	 * @param TIPO PMAX OPPURE PMIN
	 */
	private void eseguiCalcolo(int TIPO){
		double ta=temperaturaAmbiente();
		 calcolaTemperature(TIPO, ta);
		 logger.appendMessage(REPORT,"\n"+ outDataCondotti());
		 logger.appendMessage(REPORT, String.format("\nCALCOLO CON TEMPERATURA AMBIENTE %1.3f",ta));
		  logger.appendMessage(REPORT,"\n"+ outData());
		 logger.appendMessage(REPORT,"\n"+ outDataFumi());
		 logger.appendMessage(REPORT,"\n"+ outDataFluidi(ta));
	 
		 double kp=condotto.getK();
			double ai=condotto.getCoefLiminI();
			double Tfup=condotto.Tu();
			tpu_temper_par_U=Tfup-(Tfup-ta)*kp/ai;
			  logger.appendMessage(REPORT,String.format("\n%-35s\t%1.1f  [K] \t%1.1f [°C]","Temperatura parete uscita camino",tpu_temper_par_U,(tpu_temper_par_U - 273.0)));
				
		 calcolaTemperature(TIPO, 293.0);
		 logger.appendMessage(REPORT, String.format("\nCALCOLO CON TEMPERATURA AMBIENTE %1.3f PER VERIFICA PRESSIONI",293.0));
		 logger.appendMessage(REPORT,"\n"+ outData());
	  logger.appendMessage(REPORT,"\n"+ outDataFumi());
		 logger.appendMessage(REPORT,"\n"+ outDataFluidi(293.0));
		   kp=condotto.getK();
			  ai=condotto.getCoefLiminI();
			  Tfup=condotto.Tu();
			  tpu_temper_par_U=Tfup-(Tfup-293.0)*kp/ai;
			  logger.appendMessage(REPORT,String.format("\n%-35s\t%1.1f  [K] \t%1.1f [°C]","Temperatura parete uscita camino",tpu_temper_par_U,(tpu_temper_par_U - 273.0)));
 
	}
	
	private void calcolaTemperature(int calcolo,double temperaturaambiente){
		double portatafumo;
		double temperfumo;
		
		Fluido fumo;
		if(calcolo==PMAX){
			portatafumo=caldaia.getPortatfumiPmax();
			temperfumo=caldaia.getTempFumiPmax();
			fumo=caldaia.getFumoPmax();
		}else{
			portatafumo=caldaia.getPortatfumiPmin();
			temperfumo=caldaia.getTempFumiPmin();
			fumo=caldaia.getFumoPmin();
		}
		maria = 0;
		
		// 02/2018
		logger.appendMessage(REPORT,"\n  calcolaTemperature()");
		
		
	 double peff_prev=0;
	 maria=caldaia.getPortatfumiPmax()*0.1;
	 logger.appendMessage(REPORT, String.format("\nMassa aria  %1.3f",maria));
 
	 for(int count=0;count<100 ;count++){
		 logger.appendMessage(REPORT, String.format("\n Iterazione %d",count));
		//int LOG_LEVEL=REPORT;
		  int LOG_LEVEL=Mylogger.INFO;
		 logger.appendMessage(LOG_LEVEL, "\n>Iterazione numero:"+count);
		 logger.appendMessage(LOG_LEVEL, "\n >Canale :calcolo termico ");
		 
		 
		 canale.setData(Patm, portatafumo, temper_int,
				 temperfumo, fumo);
		
		canale.addFluido(aria, maria, temper_int);
		logger.appendMessage(LOG_LEVEL,
				String.format( "\n >Aggiunto massa aria al canle. \tportata massica aria %1.4f [kg/s] temperatura %1.2f  ", maria,temper_int));
		logger.appendMessage(LOG_LEVEL,"\n\t Canale "+FormatOutputUtility.printTemperData(canale));
		logger.appendMessage(LOG_LEVEL, "\n >Condotto :calcolo termico ");
		
		condotto.setData(Patm, canale.M1(), temperaturaambiente, canale.Tu(), canale.fl_int());
		
		
		logger.appendMessage(LOG_LEVEL,"\n condotto: calcola pressioni:");
				
		//condotto.Calcola_d_P();// Calcola perdite statiche e concentrate
	//	ps_cond = condotto.Ps(1);// calcola la pressione statica 
		
		 
		logger.appendMessage(LOG_LEVEL,"\n \t > calcola perdita di carico comignolo:");
		dp_com = condotto.d_P(csi_comign);
		
		peff_cond = condotto.Peff(-dp_com);// Chiama calcola_d_P(); e ps
		ps_cond=condotto.getPs();
		dp_con=condotto.d_P();
		
		logger.appendMessage(LOG_LEVEL,"\n > Canale: calcola pressioni:");
		 
		
		
		
		//canale.Calcola_d_P();
		peff_ca_base = canale.Peff(peff_cond);// Chiama calcola d_P()
		//peff_ca=canale.Peff(0);// Chiama calcola d_P()
		peff_ca=peff_ca_base ;
		dp_can=canale.d_P();
		ps_ca=canale.getPs();
	//	ps_ca=canale.Ps(1);// Calcola la pressione statica
		double test=peff_ca_base-peff_prev;
		test=test<0?-test:test;
		logger.appendMessage(Mylogger.INFO,String.format("\n>Verifica convergenza :\n\t pressione effettiva base canale fumo %1.2f"
				+ " iterazione precedente %1.2f  differenza %1.2f", peff_ca_base,peff_prev,test));
		if(test<0.1){
			logger.appendMessage(LOG_LEVEL,"\n > CONVERGENZA.........iterazione --> "+(int)(count+1));
			break;
		}
		peff_prev=peff_ca_base;
		
		
		
	/*	logger.appendMessage(Mylogger.INFO, String.format(
				"\n PRESSIONE EFFETTIVA CONDOTTO %1.3f  Differenze rispetto a iterazione precedente =%1.3f", peff_ca_base,peff_ca_base-peff_prev));
	 */
		
		
		
		/*******************************************
		logger.appendMessage(Mylogger.INFO, String.format("\n > Calcolo aria parassita...." ));
		maria = presaaria.MaIntTir(peff_ca_base, portatafumo,
				Patm, temper_int);
		if(maria<0){
			logger.appendMessage(Mylogger.INFO, String.format(
					"\n Massa aria parassita < 0 ...modificre parametri" ));
			return;
		}
	 ****************************************************/
	  
	 }// ciclo iterazione
 
	}
	
	private double temperaturaAmbiente(){
		double Ta;
		double omega=fraz_per_cont_esterno/100;
		if(fraz_per_cont_esterno==100){
			Ta=temper_est;
		}else if(fraz_per_cont_esterno==0){
			Ta=293.15;
		}else{
			Ta=293.0*(1-omega)+temper_est*omega;
		}
		return Ta;
	}
	private double verificaTemperatura(){
		double Ta;
		double omega=fraz_per_cont_esterno/100;
		if(fraz_per_cont_esterno==100){
			Ta=temper_est;
		}else if(fraz_per_cont_esterno==0){
			Ta=293.15;
		}else{
			Ta=293.0*(1-omega)+temper_est*omega;
		}
		logger.appendMessage(Mylogger.FINEST, String.format("\n\t\tCalcolo temperatura esterna: Omega %f Temperatura esterna   %f",omega,Ta));
		double kp=condotto.getK();
		double ai=condotto.getCoefLiminI();
		double Tfup=condotto.Tu();
		return Tfup-(Tfup-Ta)*kp/ai;
	}
	
	
	
	private double velocitaMinima(double area){
	 	double coeff_vel_min=1.58;
	 	double rad=Math.pow(area, 0.25);
		// rad quadrica di area
	 	// logger.appendMessage(Mylogger.FINEST, String.format("\n\t\t Velocita minima : Area %f Radice quarta di area %f",area,rad));
		return coeff_vel_min*rad;
		
		
	}
	
	String outDataCondotti(){
		CondottoBase left=condotto;
		CondottoBase right=canale;
		StringBuilder strbuild=new StringBuilder();
		strbuild
		.append(String.format("\n%-35s","Parametri input condotti"))
		.append(String.format("\n%-35s\t%10s\t%10s","Parametro", "Condotto","Canale"))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Diametro idarulico [m]", left.Dh(),right.Dh()))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Area              [m2] ", left.Area(),right.Area()))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Lunghezza totale   [m] ", left.Lung(),right.Lung()))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Altezza            [m] ",left.H(),right.H()))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Perimetro interno  [m] ",left.Per_int(),right.Per_int()))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Rugosità           [m] ",left.Rug(),right.Rug()))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Resistenza termica [m] ",left.Resterm(),right.Resterm()))
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Coefficiente perite localizzate ",left.sommaPerditeLocalizzate(),right.sommaPerditeLocalizzate()));
		
		return strbuild.toString();	
	}
	
	String outDataFumi(){
	/*	Miscela fumou=(Miscela) condotto.getFluidoI();
		Miscela fumoc=  (Miscela) caldaia.getFumoPmax();
		
		StringBuilder strbuild=new StringBuilder();
		strbuild
		.append(String.format("\n%-35s","Composizione fumi"))
		.append(String.format("\nComponente \t%10s\t%10s","Caldaia","Uscita camino"))
		.append(String.format("\n%-10s\t%10.2f %%\t%10.2f %%","O2 ",fumoc.getFrazioneMolare(o2)*100,fumou.getFrazioneMolare(o2)*100))
		.append(String.format("\n%-10s\t%10.2f %%\t%10.2f %%","N2 ",fumoc.getFrazioneMolare(n2)*100,fumou.getFrazioneMolare(n2)*100))
		.append(String.format("\n%-10s\t%10.2f %%\t%10.2f %%","CO2",fumoc.getFrazioneMolare(co2)*100,fumou.getFrazioneMolare(co2)*100))
		.append(String.format("\n%-10s\t%10.2f %%\t%10.2f %%","H2O",fumoc.getFrazioneMolare(h20)*100,fumou.getFrazioneMolare(h20)*100));
		return strbuild.toString();	
		*/
		return "Out data fumi..";
	}
	
	String outDataFluidi(double temperaria){
		/**
		Miscela right=(Miscela) condotto.getFluidoI();
		Miscela left=  (Miscela) caldaia.getFumoPmax();
		double temper1=caldaia.getTempFumiPmax();
		double temper2=condotto.Tm();
		StringBuilder strbuild=new StringBuilder();
		strbuild
		.append(String.format("\n%-35s","Propietà fluidi"))
		.append(String.format("\n%-35s\t%10s\t%10s\t%10s","Parametro", "Fumo caldaia","Fumo camino","Aria"))
		.append(String.format("\n%-35s\t%10.1f\t%10.1f\t%10.1f","Temperatura ", temper1, temper2, temperaria))
		.append(String.format("\n%-35s\t%10.1f\t%10.1f\t%10.1f","Calore specifico ",left.CapTerm(temper1),right.CapTerm(temper2),aria.CapTerm(temperaria)))
		.append(String.format("\n%-35s\t%10.2e\t%10.2e\t%10.2e","Conducibilità termica",left.CondicTermica(temper1),right.CondicTermica(temper2),aria.CondicTermica(temperaria)))
		.append(String.format("\n%-35s\t%10.2e\t%10.2e\t%10.2e","Viscosità dinamica ",left.ViscositaDinamica(temper1),right.ViscositaDinamica(temper2),aria.ViscositaDinamica(temperaria)));
		return strbuild.toString();	
		**/
		return "Out data fluidi..";
	}
	
	String outData(){
		/**
		CondottoBase left=condotto;
		CondottoBase right=canale;
		VelocitaFluido vfco=condotto.getVelocitaFluido();
		VelocitaFluido vfca=canale.getVelocitaFluido();
		VarTempNonCoas vtncco=condotto.getVartempnoncoas();
		VarTempNonCoas vtncca=canale.getVartempnoncoas();
		double temper1=condotto.Tm();
		double temper2=canale.Tm();
		
		StringBuilder strbuild=new StringBuilder();
		strbuild
		.append(String.format("\n%-35s","Propietà calcolate condotti"))
		.append(String.format("\n%-35s\t%10s\t%10s","Parametro", "Condotto","Canale"))
		.append(String.format("\n%-35s\t%10.3f\t%10.3f","Fattore attrito liscio", vfco.getFattAttrLiscio(),vfca.getFattAttrLiscio()))
		.append(String.format("\n%-35s\t%10.3f\t%10.3f", "Fattore attrito ruvido",vfco.getFattAttrRuvido(),vfca.getFattAttrRuvido()))
		.append(String.format("\n%-35s\t%10.3f\t%10.3f","Massa volumica fumi [kg/m3] ", vfco.getMassaVolumicaMedia(),vfca.getMassaVolumicaMedia()))
		.append(String.format("\n%-35s\t%10.0f\t%10.0f","Numero di Raynolds  ", vfco.getNre(),vfca.getNre()))
		.append(String.format("\n%-35s\t%10.3f\t%10.3f","Fattore liminare interno[W/(m2 K)]", vtncco.getCoefLiminI(),vtncca.getCoefLiminI()))
		.append(String.format("\n%-35s","Coefficiente globale" ))
		.append(String.format("\n%35s\t%10.3f\t%10.3f","di scambio termico[W/(m2 K)]", vtncco.getK(),vtncca.getK()))
		
		.append(String.format("\n%-35s\t%10.3f\t%10.3f","Fattore di raffreddamento", vtncco.getKR(),vtncca.getKR()))
		
		.append(String.format("\n%-35s\t%10.2f\t%10.2f","Numero di Nusselt", vtncco.getNNusselt(),vtncca.getNNusselt()))
		.append(String.format("\n%-35s\t%10.4f\t%10.4f","Portata massica fumi [kg/s]",left.M1(),right.M1()))
		.append(String.format("\n%-35s\t%10.0f\t%10.0f","Temperatura iniziale [K]",left.Ti(),right.Ti()))
		.append(String.format("\nTemperatura media fumi  [K]            \t%10.0f\t%10.0f", vtncco.getTfm(),vtncca.getTfm()))
		.append(String.format("\nTemperaturi uscita fumi [K]            \t%10.0f\t%10.0f", vtncco.getTfu(),vtncca.getTfu()));
	
		return strbuild.toString();
	***/
		return "Out data ..";
	}
	
public void setLogger(Mylogger logger) {
	this.logger = logger;
	//presaaria.setLogger(logger2);
}


public boolean isVel_con_ok() {
	return vel_con_ok;
}


public boolean isVel_can_ok() {
	return vel_can_ok;
}
	

}
