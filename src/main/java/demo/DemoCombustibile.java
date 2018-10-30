package demo;

import com.antoiovi.unicig.Formule;
import  com.antoiovi.unicig.fluidi.comb.*;
import com.antoiovi.unicig.impianti.Caldaia;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;

import java.util.Scanner;

public class DemoCombustibile{

  static final String combName[]=CombustibiliFactory.combustibileName;
  static final int ALL_COMB=combName.length;
	static final int QUIT=ALL_COMB+1;


	public static void main(String[] args){
		DemoCombustibile demo=new DemoCombustibile();
		demo.start();
	}

	void start(){
    String combName[]=CombustibiliFactory.combustibileName;

		boolean test=true;
		int input=0;
		do{
			log("\n\n");
			for(int x=0;x<combName.length;x++){
				log(String.format("%d)  : %s",x,combName[x]));
			}
      log(String.format("%d)  : %s",ALL_COMB," Tutti i combustibili"));
          log(String.format("%d)  : %s",QUIT," per uscire"));
      log("Inserire scelta :");
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
        if(input==QUIT)
          return;

if(input<ALL_COMB){
    calc_combustibile(input);
}else if(input==ALL_COMB){
  calc_allcomb();
}




    }while(test);

	}

public void calc_allcomb(){
  double Q= 10.0; // kW
  double co2;// co2%
  double tm=300;
  double pmax; //portata massica TempFumi
  double costel;
  double capTerm;
    double rend;

for(int combustibile=0;combustibile<combName.length;combustibile++){
  Combustibile comb=CombustibiliFactory.getInstance().getNewCombustibile(combustibile);
log(String.format(" %d) Combustibile %s ",combustibile,combName[combustibile]));
Q=10.0;
log(String.format("%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s","PotkW","CO2%","Rend","TempFumi","---","PortMassica","CostEl","CapTerm"));
  for(int x=0;x<10;x++){
    Q=10+20.0*x;
  Caldaia cald=new Caldaia(Q,combustibile,tm);
  rend=cald.getRendimento();
  co2=cald.getCO2();
  pmax=comb.portataMassicaFumi( Q, rend, co2);
  costel=comb.CostElasticita_1(co2);
  capTerm=comb.CapTermica( tm, co2);
  log(String.format("%10.3f|%10.3f|%10.3f|%10.3f|%10s|%10.3f|%10.3f|%10.3f|",Q,co2,rend,tm,"--",pmax,costel,capTerm));
    }
  }
}




public void calc_combustibile(int combustibile){

  double tm=input_double("Inserire temperatura fumi :");
  double Q; // kW
  double co2;// co2%
  double pmax; //portata massica TempFumi
  double costel;
  double capTerm;
  double rend;


  Combustibile comb=CombustibiliFactory.getInstance().getNewCombustibile(combustibile);
log(String.format(" %d) Combustibile %s ",combustibile,combName[combustibile]));
Q=10.0;
log("PortMassic = Portata massica prodotti combustione [g/s]  per usare kg/s dividere per 1000 \n"+
    "lambdaA   = Conducibilita termica \n"+
    "TenoreH20 = sigma(H2O) tenore del vapore acqueo nei prodotti combustione in %\n"+
    "Pw        = tiraggio minimo");
log(String.format("%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s|%10s",
                  "PotkW","CO2%","Rend","TempFumi","---","PortMassic","CostEl","CapTerm","ViscDin","lambdaA","TenoreH20","ParzH20","Pw" ));
  for(int x=0;x<10;x++){
    Q=10+20.0*x;
  Caldaia cald=new Caldaia(Q,combustibile,tm);
  rend=cald.getRendimento();
  co2=cald.getCO2();
  pmax=comb.portataMassicaFumi( Q, rend, co2);
  costel=comb.CostElasticita_1(co2);
  capTerm=comb.CapTermica( tm, co2);
double viscDin=cald. viscDin();
double lambdaA=cald.lambdaA();

  double tenh2o=cald.TenoreH2O();
  double pparzh20=cald.PparzialeH2o();
  double Trug=cald.tempPuntoRugiada();
  double deltatstp=cald.deltaTsp();


  double R2=cald.CostElasticita_2();
  cald.TenoreH2OPD();
  cald.PparzialeH2oTp();
  cald.getRendimento();
  cald.getCO2();
  double pw=cald.getPW_tiraggiominimo();
  log(String.format("%10.3f|%10.3f|%10.3f|%10.3f|%10s|%10.3f|%10.3f|%10.3f|%10s|%10.3f|%10.3f|%10.3f|%10.3f|"
  ,Q,co2,rend,tm,"--",pmax,costel,capTerm,  viscDin,lambdaA,tenh2o,pparzh20,pw   ));
}

}



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

  /*******************************************
  *		LOG
  ********************************************/

  void log(String s){
    System.out.println(s);
  }
}
