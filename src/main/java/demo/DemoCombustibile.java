package demo;

import com.antoiovi.unicig.Formule;
import  com.antoiovi.unicig.fluidi.comb.*;
import com.antoiovi.unicig.impianti.Caldaia;
import com.antoiovi.unicig.impianti.Gener;
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
  double tm=200;
  double pmax; //portata massica TempFumi
  double costel;
  double capTerm;
    double rend;
tm=input_double("Temperatura fumo °C");
for(int combustibile=0;combustibile<combName.length;combustibile++){
  Combustibile comb=CombustibiliFactory.getInstance().getNewCombustibile(combustibile);
log(String.format(" %d) Combustibile %s ",combustibile,combName[combustibile]));
Q=10.0;
Caldaia.printHeader();
  for(int x=0;x<10;x++){
    Q=10+20.0*x;
  Caldaia cald=new Caldaia(Q,combustibile,tm);
cald.printValue();
    }
  }
}




public void calc_combustibile(int combustibile){

  double tm=input_double("Inserire temperatura fumi °C :");
  double PL=101000.0;
  double Q; // kW
  double co2;// co2%
  double pmax; //portata massica TempFumi
  double costel;
  double capTerm;
  double rend;


Combustibile comb=CombustibiliFactory.getInstance().getNewCombustibile(combustibile);
log(String.format(" %d) Combustibile %s ",combustibile,combName[combustibile]));
Q=10.0;
Caldaia.printHeader();
  for(int x=0;x<10;x++){
    Q=10+20.0*x;
  Caldaia cald=new Caldaia(Q,combustibile,tm);
   cald.setPL(PL);
   cald.printValue();
   double deltatstp=cald.deltaTsp();
  double R2=cald.CostElasticita_2();
  cald.TenoreH2OPD();
  cald.PparzialeH2oTp();

 }

for(int x=0;x<Comb_1.combustibile.length;x++){
  Comb_1.print(x);
}
//double potenzautile,int combustibile,double tm)
  tm=input_double("Inserire temperatura fumi °C :");
  Q=input_double( "Inserire  potenza in      kW :");; // kW

Gener gener=new Gener(Q,combustibile,tm);

gener.print();

Comb_2 combx=new Comb_2(combustibile,gener.getCo2(),gener.getTm());
combx.print();


String gen_stringHeader=gener.getStringHeader();
String gen_stringValue=gener.getStringValue();
log(gen_stringHeader);
log(gen_stringValue);

String comb_stringHeader=combx.getStringHeader();
String comb_stringValue=combx.getStringValue();
log(comb_stringHeader);
log(comb_stringValue);



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
