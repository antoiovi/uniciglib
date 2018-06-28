package demo;

import com.antoiovi.unicig.fluidi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;

import java.util.Scanner;

public class FluidiDemo{
	
	static final int OSSIGENO=1;
	static final int AZOTO=2;
	static final int ANIDRIDE_CARBONICA=3;
	static final int ANIDRIDE_CARBONICA_CP=4;
	
	static final int ARIA_BASE=5;
	static final int FUMO_CH4=6;
	static final int VAPORE_H20=7;
	
	static final int MISCELA=8;
	
	static final int QUIT=9;
		
	public static void main(String[] args){
		FluidiDemo fluididemo=new FluidiDemo();
		fluididemo.start();
	}
	
	void start(){
		boolean test=true;
		int input=0;
		do{
			log("\n\n");
			log(String.format("%d  : Dati ossigeno",OSSIGENO));
			log(String.format("%d  : Dati azoto",AZOTO));
			log(String.format("%d  : Dati anidride carbonica",ANIDRIDE_CARBONICA));
			log(String.format("%d  : Calore specifico a pressione costante Anidride carbonica",ANIDRIDE_CARBONICA_CP));
			
			log(String.format("%d  : Dati aria base",ARIA_BASE));
			log(String.format("%d  : Dati fumo combustione metano",FUMO_CH4));
			log(String.format("%d  : Dati vapore acqua ",VAPORE_H20));
			log(String.format("%d  : Dati MISCELA ",MISCELA));
			
			
			log(String.format("%d : quit",QUIT));
			try{
				Scanner sc = new Scanner(System.in);
				input = sc.nextInt();
				if(input<1 || input > QUIT){
					log("Input non valido  !!");
					continue;
					}
				}catch(java.util.InputMismatchException e){
					log("Input NON VALIDO !! Inserire un numero ");
					continue;
				}

				
			switch(input){
				case OSSIGENO:
					dati_ossigeno();
					break;
				case AZOTO:
					dati_azoto();
					break;
				case ANIDRIDE_CARBONICA:
					dati_anidride_carbonica();
					break;
				case ANIDRIDE_CARBONICA_CP:
					dati_anidride_carbonica_cp();
					break;
				case ARIA_BASE:
					dati_aria_base();
					break;
				case FUMO_CH4:
					dati_fumo_ch4();
					break;
				case VAPORE_H20:
					dati_vapore_h20();
					break;
				case MISCELA:
					dati_miscela();
					break;
				case QUIT:
					test=false;
					break;
			}
		
		
		}while(test);
		
		
	}
	
		double R;
	double Mm;
    double Mv;
	double Cp;
    double CT;
	double DT;
	double VD;
    double VC;
    double NPr;
	
	
	
	/***
	*		DATI 1  : stampa dati ossigeno
	***/
	void dati_ossigeno(){
		log("\n DATI OSSIGENO\n");
		{
	 
		
		OssigenoO2 fluido=new OssigenoO2();
		
		 R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
					
		log_unita_misura();
		
		String result = String.format("TABELLA OSSIGENO" );
		System.out.println(result);
		
		double T0=200;
		double Tf=400;
		double T=T0;
		String header= String.format("|%5s|%5s|%5s|%5s|%5s|%6s|%10s|%10s|%10s|%5s|  "
				,"T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr");
		System.out.println(header);
		do{
			
			fluido.setTemperatura(T);
			R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
					
		  //result = String.format("T=%3.1f R=%4.2f Mm=%4.1f Mv=%4f  Cp=%1.0f  CT=%4f  DT=%4f  VD=%1.2e  VC=%1.1e  NPr=%4f  ",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  
		  //result = String.format("%3.1f R=%3.1f Mm=%1.1f Mv=%1.3f  Cp=%1.0f  CT=%1.4f  DT=%1.3e  VD=%1.3e  VC=%1.1e  NPr=%1.3f  ",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  	result = String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%6.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  
		log(result);
		T+=10;
		if(T>Tf)
			break;
			}while(true);

		}
	}
	
	/*******
	*		DATI AZOTO
	********/
	void dati_azoto(){
	 
		
		//AnidrideCO2 fluido=new AnidrideCO2();
		AzotoN2 fluido=new AzotoN2();
		
		 R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
					
		log_unita_misura();
		
		String result = String.format("TABELLA AZOTO " );
		System.out.println(result);
		
		double T0=200;
		double Tf=400;
		double T=T0;
		String header= String.format("|%5s|%5s|%5s|%5s|%5s|%10s|%10s|%10s|%10s|%5s|  ","T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr");
		System.out.println(header);
		do{
			
			fluido.setTemperatura(T);
			R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
					
		  //result = String.format("T=%3.1f R=%4.2f Mm=%4.1f Mv=%4f  Cp=%1.0f  CT=%4f  DT=%4f  VD=%1.2e  VC=%1.1e  NPr=%4f  ",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  
		  //result = String.format("%3.1f R=%3.1f Mm=%1.1f Mv=%1.3f  Cp=%1.0f  CT=%1.4f  DT=%1.3e  VD=%1.3e  VC=%1.1e  NPr=%1.3f  ",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  	result = String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%10.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  
		System.out.println(result);
		T+=10;
		if(T>Tf)
			break;
			
			
		}while(true);

   }
	
	
	/***************
	*	anidride carbonica 
	***************/
	
	void dati_anidride_carbonica(){
		
		
		AnidrideCO2 fluido=new AnidrideCO2();
		
		 R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
				
		log_unita_misura();
				
		String result = String.format("TABELLA Anidride Carbonica CO2" );
		System.out.println(result);
		
		double T0=200;
		double Tf=3000;
		double T=T0;
		String header= String.format("|%5s|%5s|%5s|%5s|%5s|%6s|%10s|%10s|%10s|%5s|  ","T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr");
		System.out.println(header);
		do{
			
			fluido.setTemperatura(T);
			R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
		result = String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%6.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  
		System.out.println(result);
		T+=100;
		if(T>Tf)
			break;
			
			
		}while(true);

	}
	
	/*****************************************************
	*	ANIDRIDE CARBONICA : CALORE SPECIFICO
	*****************************************************/
	void dati_anidride_carbonica_cp(){
	
		log("*********************************************************");
		log("Calore specifico a pressione costante anidride carbonica");
		log("*********************************************************");
		
		AnidrideCO2 fluido=new AnidrideCO2();
		
		 R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 
		 NPr=fluido.NumeroPrandtl();
		
		log_unita_misura();
		
		String result = String.format("TABELLA Anidride Carbonica CO2" );
		System.out.println(result);
		
		fluido.setTemperatura(200);
		double ref=7.73;
		double Cp_R=fluido.CapTerm( );
		System.out.println("CP/R"+Cp_R);
		System.out.println("CP_R/ref =R="+Cp_R/ref);
		
		fluido.setTemperatura(400);
		ref=9.87;
		Cp_R=fluido.CapTerm( );
		System.out.println("CP/R"+Cp_R);
		System.out.println("CP_R/ref =R="+Cp_R/ref);
		
		
		double T0=200;
		double Tf=400;
		double T=T0;
		String header= String.format("|%5s|%5s|%10s ","T", "CP","Cp*R");
		System.out.println(header);
		do{
			
			fluido.setTemperatura(T);
			R= fluido.CostElasticita();
		Cp=fluido.CapTerm( );
		
		result = String.format("|%3.1f|%3.5f|%3.5f|",T, Cp,Cp*R);
		  
		System.out.println(result);
		T+=100;
		if(T>Tf)
			break;
			
			
		}while(true);

	}	
	
		
	/*******************************************
	*		ARIA_BASE
	********************************************/
	void dati_aria_base(){
		
		Aria_base fluido=new Aria_base();
		
		 R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
		
		log_unita_misura();
		
		String result = String.format("TABELLA ARIA BASE" );
		System.out.println(result);
		
		double T0=200;
		double Tf=3000;
		double T=T0;
		String header= String.format("|%5s|%5s|%5s|%5s|%5s|%6s|%10s|%10s|%10s|%5s|  ","T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr");
		System.out.println(header);
		do{
			
			fluido.setTemperatura(T);
			R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
		result = String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%6.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  
		System.out.println(result);
		T+=100;
		if(T>Tf)
			break;
			
			
		}while(true);
		
	}
		
	/*******************************************
	*		FUMO CH4
	********************************************/
	void dati_fumo_ch4(){
		FumoCh4 fumoch4=new FumoCh4(35.0,300.0,1.5,FumoCh4.ECC_ARIA);
		
		log_unita_misura();
		
		Fluido fluido=fumoch4.getFumo();
		double portata=fumoch4.getPortataFumo();
		
		 R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
					
		String result = String.format("TABELLA FUMO %s  portata %1.4f",fluido.toString(),portata );
		System.out.println(result);
		
		double T=fluido.getTemperatura();
		String header= String.format("|%5s|%5s|%5s|%5s|%5s|%6s|%10s|%10s|%10s|%5s|  ","T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr");
		System.out.println(header);
		result = String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%6.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		System.out.println(result);
	}
		
	/*******************************************
	*		VAPORE H20
	********************************************/
	void dati_vapore_h20(){
	
		
		VaporeH2O fluido=new VaporeH2O();
		fluido.setPressione(101000.0);
		
		 R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
		
		log_unita_misura();
					
		
		String result = String.format("TABELLA VaporeH2O" );
		System.out.println(result);
		
		double T0=400;
		double Tf=500;
		double step=10;
		double T=T0;
		String header= String.format("|%5s|%5s|%5s|%5s|%5s|%6s|%10s|%10s|%10s|%5s|  ","T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr");
		System.out.println(header);
		do{
			
			fluido.setTemperatura(T);
			R= fluido.CostElasticita();
		 Mm=fluido.MassaMolare();
  
		 Mv=fluido.MassaVolumica( );
		 Cp=fluido.CapTerm( );
		 CT=fluido.CondicTermica( );
		 DT=fluido.DiffusivitaTermica( );
		 VD=fluido.ViscositaDinamica( );
		 VC=fluido.ViscCin();
		 NPr=fluido.NumeroPrandtl();
		result = String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%6.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T, R,Mm,Mv,Cp,CT,DT,VD,VC,NPr);
		  
		System.out.println(result);
		T+=step;
		if(T>Tf)
			break;
			
			
		}while(true);

	}

	/*******************************************
	*		MISCELA
	********************************************/
	void dati_miscela(){
	MisceleFluido testmiscele=new MisceleFluido();
	
	testmiscele.dati_miscela();
	testmiscele.Test_02();
	testmiscele.Test_03();
	testmiscele.creaMiscela();

	}
	
	/*******************************************
	*		LOG UNITA DI MISURA
	********************************************/
	
	void log_unita_misura(){
		log("Costant universale dei gas Ru=8314.472 J/(K kmol)");
		log("Costante del gas R         J/(Kg K)");
		log("Massa molare in u.m.a; poiche considero Ru in J/(K kmol)"+
				"allora li tratto come Kg/kmole");
		log("Massa volumica             kg/m3 ");
		log("Capacita termica           J/(K kg)");

		log("Conducibilita termica       W/(m K)");
		log("Diffusivita termica         m2/s   ");
		log("Viscosita dinamica          Pa s   ");
		log("Viscosita cinematica        m2/s   ");
		
	
	}
	
	
	/*******************************************
	*		LOG
	********************************************/
	
	void log(String s){
		System.out.println(s);
	}
}