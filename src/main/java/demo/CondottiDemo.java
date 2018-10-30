package demo;

import com.antoiovi.unicig.fluidi.*;
import com.antoiovi.unicig.condotti.CondottoFL;
import com.antoiovi.unicig.Formule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;

import java.util.Scanner;

public class CondottiDemo{
	
	static final int CALCOLO_POMPA_H2O=0;
	static final int CALC_DIAGRAMMA_PRESS=1;
	
	static final int QUIT=2;
		
String[] menu={"Calcolo prevalenza pompa h20 ","Diagrammma pressioni","Quit"};
		
	public static void main(String[] args){
		CondottiDemo demo=new CondottiDemo();
		demo.start();
	}
	
	void start(){
		boolean test=true;
		int input=0;
		do{
			log("\n\n");
			for(int x=0;x<menu.length;x++){
				log(String.format("%d  : %s",x,menu[x]));
			}

			try{
				Scanner sc = new Scanner(System.in);
				input = sc.nextInt();
				if(input<0 || input > QUIT){
					log("Input non valido  !!");
					continue;
					}
				}catch(java.util.InputMismatchException e){
					log("Input NON VALIDO !! Inserire un numero ");
					continue;
				}

				
			switch(input){
				case CALCOLO_POMPA_H2O:
				calcolo_prevalenza_pompa();
					break;
				case CALC_DIAGRAMMA_PRESS:
					diagrama_pressioni();
					break;
				case QUIT:
					test=false;
					break;
			}
		
		
		}while(test);
		
		
	}
	
	
void calcolo_prevalenza_pompa(){
		log("Utilizzo della classe CondottoFl ");
		boolean test=true;
		double diametro=0.25;
		double scabrezza=0.001;
		double lunghezza=1.0;
		double altezza=1.0;
		double input=0;
		
		double portata=10;
		int fluido=1;
		double massa_volumica;
		double visc_cin;
		do{
		
		try{
				Scanner sc = new Scanner(System.in);
				log(String.format("Diamtro (0 default (%f))",diametro));
				input = sc.nextDouble();
				diametro=input==0?diametro:input;
				log(String.format("scabrezza relativa (0 default (%f))",scabrezza));
				input = sc.nextDouble();
				scabrezza=input==0?scabrezza:input;
				log(String.format("Lunghezza (0 default (%f))",lunghezza));
				input = sc.nextDouble();
				lunghezza= input==0?lunghezza:input;
				log(String.format("Altezza (-1 default=lunghezza, tubo verticale(%f);  altezza=0 tubo orizzontale; altezza <0 tubo in discesa)",altezza));
				input = sc.nextDouble();
				altezza= input==-1.0?lunghezza:input;
				
 			
				log(String.format("Portata massica kg/s (0 default (%f))",portata));
				input = sc.nextDouble();
				portata=input==0?portata:input;
				log("Fluido \n\t 1 acqua\n\t 2 aria ");
				int int_input = sc.nextInt();
				fluido=int_input;
				
				
				
				CondottoFL cond=new CondottoFL(diametro ,scabrezza ,lunghezza);
				cond.setAltezza(altezza);

				if(fluido==1){
					log("\nCalcoli con liquido interno ACQUA");
					massa_volumica=Formule.H2O_Mvol_4;
					visc_cin=Formule.H2O_visc_cin_20;
				}else{
					log("\nCalcoli con liquido interno ARIA alla Patm");
					massa_volumica=Formule.Aria_Mvol_20;
					visc_cin=Formule.Aria_visc_cin_20;
				}
				cond. calcolaParametri(portata,  massa_volumica,  visc_cin);
				logCondottoFL(cond);
				double caricostatico=cond.caricoStatico(massa_volumica);
				double pdin=cond.pressDin(massa_volumica);
				double perdlin=cond.perditediCaricoLin(massa_volumica);
				double perdconc=cond.perditediCaricoConc(massa_volumica);
				double Ptot=caricostatico+pdin+perdconc+perdlin;
				double Ptot_M=Ptot/(9.81*massa_volumica);
				log("");
				log( String.format("%10s|%10s|%10s|%10s|%10s|%10s|","Pcaric Lin","Perdite concentrate","P dinamica","Carico statico [Pa] ","TOTALE[Pa]","TOTALE[m]" ));
				log(
				 String.format("%10f|%19.2f|%10.2f|%20.2f|%10.2f|%10.2f|",perdlin,perdconc,pdin , caricostatico ,Ptot, Ptot_M)
				);
				log("");
				
				
			}catch(java.util.InputMismatchException e){
					log("Input NON VALIDO !! Inserire un numero (verifcare decimale  se . o , ) ");
					continue;
			}
			
			try{
				
				Scanner sc = new Scanner(System.in);
				log("\nInput   1 per ripetere ; un carattere qualsiasi per tornare a menu condotti");
				input = sc.nextInt();
				if(input==1)
					continue;
				test=false;
					break;
				
				}catch(java.util.InputMismatchException e){
				test=false;
					break;
			}
			
		}while(test);

	
}
/*****
* Usa CondottiFL e calcola le pressioni statica ogni decimo di lunghezza
*******/

void diagrama_pressioni(){
		log("Utilizzo della classe CondottoFl ");
		boolean test=true;
		double diametro=0.25;
		double scabrezza=0.001;
		double lunghezza=1.0;
		double altezza=1.0;
		double input=0;
		
		double portata=10;
		int fluido=1;
		double massa_volumica;
		double visc_cin;
		do{
		
		try{
				Scanner sc = new Scanner(System.in);
				log(String.format("Diamtro (0 default (%f))",diametro));
				input = sc.nextDouble();
				diametro=input==0?diametro:input;
				log(String.format("scabrezza relativa (0 default (%f))",scabrezza));
				input = sc.nextDouble();
				scabrezza=input==0?scabrezza:input;
				log(String.format("Lunghezza (0 default (%f))",lunghezza));
				input = sc.nextDouble();
				lunghezza= input==0?lunghezza:input;
				log(String.format("Altezza (-1 default=lunghezza, tubo verticale(%f);  altezza=0 tubo orizzontale; altezza <0 tubo in discesa)",altezza));
				input = sc.nextDouble();
				altezza= input==-1.0?lunghezza:input;
			
				log(String.format("Portata massica kg/s (0 default (%f))",portata));
				input = sc.nextDouble();
				portata=input==0?portata:input;
				log("Fluido \n\t 1 acqua\n\t 2 aria ");
				int int_input = sc.nextInt();
				fluido=int_input;
				
				
				
				CondottoFL cond=new CondottoFL(diametro ,scabrezza ,lunghezza);
				cond.setAltezza(altezza);
				if(fluido==1){
					log("\nCalcoli con liquido interno ACQUA");
					massa_volumica=Formule.H2O_Mvol_4;
					visc_cin=Formule.H2O_visc_cin_20;
				}else{
					log("\nCalcoli con liquido interno ARIA alla Patm");
					massa_volumica=Formule.Aria_Mvol_20;
					visc_cin=Formule.Aria_visc_cin_20;
				}
				cond. calcolaParametri(portata,  massa_volumica,  visc_cin);
				logCondottoFL(cond);
				double caricostatico=cond.caricoStatico(massa_volumica);
				double pdin=cond.pressDin(massa_volumica);
				double perdlin=cond.perditediCaricoLin(massa_volumica);
				double perdconc=cond.perditediCaricoConc(massa_volumica);
				double Ptot=caricostatico+pdin+perdconc+perdlin;
				double Ptot_M=Ptot/(9.81*massa_volumica);
				log("");
				log( String.format("%10s|%10s|%10s|%10s|%10s|%10s|","Pcaric Lin","Perdite concentrate","P dinamica","Carico statico [Pa] ","TOTALE[Pa]","TOTALE[m]" ));
				log(
				 String.format("%10f|%19.2f|%10.2f|%20.2f|%10.2f|%10.2f|",perdlin,perdconc,pdin , caricostatico ,Ptot, Ptot_M)
				);
				log("");
				/***
				* CALCOLA LA PRESSIONE STATICA OGNI DECIMO DI LUNGHEZZA
				***/
				double fattattrRuvido =cond.getFattattrRuvido();
				double velMedia=cond.getVelMedia();
				double diamIdr=cond.getDiamIdr();
				double L=lunghezza;
				double H=altezza;
				for(int x=0;x<10;x++){
					double x_1=x/10f;
				L=lunghezza-x_1*lunghezza;
				double PS=Formule.perditediCaricoLin(massa_volumica,velMedia, fattattrRuvido,   L , diamIdr);
				H=altezza-x_1*altezza;
				double CS=Formule.caricoStatico(massa_volumica,H);
				log(String.format("percent tratto %3.1f\tLunghezza successiva %7.1f\t Altezza %f\tPstatica %5.3f\tCarico statico %5.3f",x_1,L,H,PS,CS));
				
				}
				
				
			}catch(java.util.InputMismatchException e){
					log("Input NON VALIDO !! Inserire un numero (verifcare decimale  se . o , ) ");
					continue;
			}
			
			try{
				
				Scanner sc = new Scanner(System.in);
				log("\nInput   1 per ripetere ; un carattere qualsiasi per tornare a menu condotti");
				input = sc.nextInt();
				if(input==1)
					continue;
				test=false;
					break;
				
				}catch(java.util.InputMismatchException e){
				test=false;
					break;
			}
			
		}while(test);

	
}




void logCondottoFL(CondottoFL cond){
	 
	 String header=String.format("%10s|%10s|%10s|%10s|%10s|","Velocita","Nre","FattAttrL","FatAttR","Nusselt");
	 //	 getCsi()
	String out=String.format("%10.3f|%10.0f|%10.5f|%10.3f|%10.3f|",
	cond.getVelMedia(), 	 cond.getNumeroReynolds(), 	 cond.getFattattrLiscio() ,  cond.getFattattrRuvido(), cond.getNumeroNusselt());
	 log(header);
	 log(out);
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