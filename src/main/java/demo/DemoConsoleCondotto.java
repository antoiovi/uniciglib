package demo;


import com.antoiovi.unicig.fluidi.*;
import com.antoiovi.unicig.condotti.*;
import com.antoiovi.unicig.tubi.*;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;

import java.util.Scanner;

public class DemoConsoleCondotto{
	
	public static void main(String[] args){
		DemoConsoleCondotto dcC=new DemoConsoleCondotto();
		dcC.Calcola_Pressioni();
	}

	
	 
		
		
public void Calcola_Pressioni(){
	log(this.getClass().getName());
	 
	 Aria_base aria=new Aria_base();
		double tempEsterna=293.0;
		double patm=95500.0;
		double massaVolAria;
		aria.setTemperatura(tempEsterna);
		aria.setPressione(patm);
	 
	 double Q=27.0;
	 double T_1=300.0;
	 double lambda=1.5;
	 
	 
	 double portata_fumi;	
		
	//double potenza, double TFumi,double ECCARIA_CO2,int TIPO){	
		log("Inizzializzo fumo ch4");
		log(String.format("Dati input : \n \t Potenza %f [kw] \t Temperatura fumi %f [°C] \t Eccesso d'aria %f",Q,T_1,lambda));
		FumoCh4 fumoch4=new FumoCh4(Q,T_1,lambda,FumoCh4.ECC_ARIA);
		Fluido fluido=fumoch4.getFumo();
		portata_fumi=fumoch4.getPortataFumo();
 		double T=fluido.getTemperatura();
		log(String.format("Dato calcolato  : \n \t portata fumi %f [kg/s] \t Temperatura%f",portata_fumi,T ));
		
		

		double d_int=0.2;
		double d_est=0.25;
		double lunghezza=3.25;
		double restermica=0.34;
		double rugosita=0.001;
		
		log("Inizzializzo Condotto");
		log(String.format("Dati input : \n \t Diametro interno %f [m] \t diametro esterno %f [m] \t lunghezza  %f [m]"+
                                          "\t resistenza termica %f  \t rugosita %f [m] ",d_int,d_est,lunghezza,restermica,rugosita));
		 // TuboC(double diam_int,double diam_est,double lunghezza, double restermica, double rugosita) 
		 TuboC tuboc=new  TuboC(0.2,0.25,3.25, 0.34, 0.001) ;
		 
		  //Condotto(Tubo tubo,Fluido fluido,double temp_input,double portatamassica)
		  double coeffLiminEst=8;
		  
		  Condotto c=new  Condotto(tuboc,fumoch4.getFumo(),portata_fumi,coeffLiminEst);
		  
		  double temp_fumo_input=400;
		  c.Calcolatemperature(temp_fumo_input,tempEsterna,patm);
		  
		log(String.format("Temperature :\n Temperatura iniziale %3.0f  \n Temperatura  media %3.0f \n Temperatura uscita %3.0f \n ",c.getTemperaturaInput(),c.getTemperaturaMedia(),c.getTemperaturaUscita()));
		massaVolAria=aria.MassaVolumica();
		lunghezza=1.0;
		double temp_fumo=temp_fumo_input;
		log("Temperature :\n ");
		double altezzaMax=10.0;
		double pariamax=9.81*1*massaVolAria;
		double piMax=patm-pariamax;
		log(String.format("Pi massimo %f",piMax));
		log("Pc = Pressione atmosferica base - peso fumo;\t Pi=Preesione atmosferica base -altezza aria " );
		//String h1=String.format("%7s\t%7s\t%7sf\t%7s","Altezza","Ti","Tm","Tu");
		String h1=String.format("|%7s|%7s|%7s|%7s","Altezza","Ti","Tm","Tu");
		String h2=String.format("|%7s|%7s|%7s|%7s|%7s","mvf","mvaria","pfumo","paria","tiraggio");
		String h3=String.format("|%7s|%7s|%7s|%7s|%7s|%7s","Pc","Pi","(Pc-Pi)","(Pc-Pimax)","pdin","Pcarico");
		String header=h1+h2+h3;
		log(header);
		/**for(int x=0;x<10;x++){
			//double fraz=1/x;
			double altezza=lunghezza+x;
			c.Calcolatemperature(temp_fumo,tempEsterna,patm,lunghezza+x);
			String strT=String.format("%7.1f %7.0f %7.0f %7.0f",lunghezza+x,c.temperaturaInput(),c.temperaturaMedia(),c.temperaturaUscita());
					
			fluido.setTemperatura(c.temperaturaMedia());
			double mvf=fluido.MassaVolumica();
			double paria=9.81*altezza*massaVolAria;
			double pfumo=9.81*altezza*mvf;
			double tiraggio=paria-pfumo;
			double pc=patm-pfumo;
			double pi=patm-paria;
			double wm=c.velocitaMedia();
			double pdin=0.5 * mvf * Math.pow(wm, 2);
			double pcarico=c.perditediCarico();
			
			String strP=String.format( " %7.2f %7.2f %7.3f %7.2f %7.2f ",mvf,massaVolAria,pfumo,paria,tiraggio);
			String strP2=String.format(" %7.1f %7.1f %7.1f %7.1f %7.2f %7.2f",pc,pi,(pc-pi),(pc-piMax),pdin,pcarico);
			log(strT+strP+strP2);
		
		}
		**/


 }
		
   
   void log_fluido(Fluido fluido){
	   double R;
	double Mm;
    double Mv;
	double Cp;
    double CT;
	double DT;
	double VD;
    double VC;
    double NPr;
 
	    R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
	   
		double T=fluido.getTemperatura();
		String header= String.format("|%5s|%5s|%5s|%5s|%5s|%6s|%10s|%10s|%10s|%5s|  ","T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr");
		System.out.println(header);
		String result = String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%6.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		System.out.println(result);
	   
   }
	 
	 
	void log_condotto(Condotto c){
		  log(String.format("Velocita media %f \n Nu %f \n Nre %f \n climInt %f \n ps:%f \n psi0: %f  \n KR %f \n k=%f",c.getVelMedia(),c.getNumeroNusselt(),c.getNumeroReynolds(),c.getCoeffLiminInterno(),c.getFattattrRuvido(),
		  c.getFattattrLiscio(),c.getFattoreraffreddamento(),c.getCoeffglobalescambterm()));
		 log(String.format("Pedridte carico=%f",c.perditediCarico()));
		 
	 }
	 
	private   void log(String s){
		System.out.println(s);
	}
	
}
