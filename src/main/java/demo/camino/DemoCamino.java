package demo.camino;

import com.antoiovi.unicig.fluidi.*;
import com.antoiovi.unicig.fluidi.comb.*;
import com.antoiovi.unicig.tubi.*;
import com.antoiovi.unicig.condotti.*;
import com.antoiovi.unicig.impianti.*;
import com.antoiovi.unicig.Formule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;

import java.util.Scanner;

public class DemoCamino{

	static final int QUIT=-1;


	public static void main(String[] args){
		DemoCamino demo=new DemoCamino();
		demo.start();
	}

	void start(){
		input_dati();
	}

void quit(){
	return;
}

void input_dati(){
	double T_esterna=273.0+5.0;
		log("Inpu dati generatore ");
		int COMB=input_combustibile();
		if(COMB==QUIT)
				quit();
		double Q = input_double("Input potenza in kW: ");
		double T_fumi = input_double("Input temperatura fumi [K]: ");

		Caldaia cald=new Caldaia(Q,COMB,T_fumi);

FluidoComb fluido= new FluidoComb(cald.getComb(),cald.getCO2()) ;


		log("Input dati canale da fumo ");
double lcan=input_double("Lunghezza canale da fumo [m]:");
double diamcanal=input_double("Diametro interno canale da fuomo [m]:");
TuboC tubo1=new TuboC(diamcanal ,diamcanal+0.01 ,lcan , 0.016, 0.002);
	CondottoCamino canale=new  CondottoCamino(tubo1,fluido, cald.portataMassicaFumi()/1000.0,0.5 );

canale.Calcolatemperature(T_fumi,T_esterna,101000.0);

double T1=canale.getTemperaturaMedia();
 double T3=canale.getTemperaturaUscita();

 log("Canale da fumo : ");
 logCondotto(canale);

		log("Input dati condotto ");

		double lcondot=input_double("Altezza camino [m]:");
		double diamcond=input_double("Diametro interno camino [m]:");
		TuboC tubo2=new TuboC(diamcond,diamcond+0.05,lcondot, 0.016, 0.002);
		CondottoCamino condotto=new  CondottoCamino(tubo2,fluido, cald.portataMassicaFumi()/1000.0,1.0 );
		condotto.Calcolatemperature(T3,T_esterna,101000.0);
log("Camino : ");
logCondotto(condotto);
}





void logCondotto(CondottoCamino cond){

	 String header=String.format("%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s|","Pstatica","Tmedia","TUscita","Velocita","Nre","FattAttrL","FatAttR","Nusselt");
	 //	 getCsi()
	String out=String.format("%10.3f|%10.3f|%10.3f|%10.3f|%10.0f|%10.5f|%10.3f|%10.3f|",
	cond.pressioneStatica(),cond.getTemperaturaMedia(),cond.getTemperaturaUscita(),
	cond.getVelMedia(), cond.getNumeroReynolds(), 	 cond.getFattattrLiscio() ,  cond.getFattattrRuvido(), cond.getNumeroNusselt());
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
	*		Input
	********************************************/

	double input_double(String prompt){
	  do{
	    double val=0.0;
	    log(prompt);
	    try{
	      Scanner sc = new Scanner(System.in);
	       val = sc.nextDouble();

	      }catch(java.util.InputMismatchException e){
	        log("Input NON VALIDO !! Inserire un numero ");
	        continue;
	      }
	        return val;
	    }  while(true);
	}

int input_combustibile(){
		String combName[]=CombustibiliFactory.combustibileName;
		boolean test=true;
		int input=0;
		do{
			for(int x=0;x<combName.length;x++){
				log(String.format("%d)  : %s",x,combName[x]));
			}
			log(String.format("Inserire combustibile (%d per annullare) :",QUIT));
			try{
				Scanner sc = new Scanner(System.in);
				input = sc.nextInt();
				if(input<-1 || input > combName.length){
					log("Input non valido  !!");
					continue;
					}
					return input;
				}catch(java.util.InputMismatchException e){
					log("Input NON VALIDO !! Inserire un numero ");
					continue;
				}

		}while(true);
}
	/*******************************************
	*		LOG
	********************************************/

	void log(String s){
		System.out.println(s);
	}
}
