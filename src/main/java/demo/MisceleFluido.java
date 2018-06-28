package demo;

import com.antoiovi.unicig.fluidi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import java.util.Scanner;

public class MisceleFluido{
	
	 
		
	 public MisceleFluido(){
		 
	 }
	 
	void start(){
	 
		
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
	
	
	 

	/*******************************************
	*		MISCELA
	********************************************/
	public void dati_miscela(){
	

	log("\n MISCELA \n");
	log("Test delle classi miscela ed ispezione dei metodi \n");

	HashMap<Fluido, Double> map_p;// map ponderali elemneti base
	HashMap<Fluido, Double> map_m;// map_m molarielementi base 
 

	PairFluidoDouble ossigeno=new PairFluidoDouble(new OssigenoO2(),21.0 );
	PairFluidoDouble azoto=new PairFluidoDouble(new AzotoN2(),79.0);
	
	List<PairFluidoDouble> pair_fluido_moli=new ArrayList<PairFluidoDouble>();
	pair_fluido_moli.add(azoto);
	pair_fluido_moli.add(ossigeno);
	
		
	double  massatotale=0;
	double	molitotali=0;
		
		// Calcolo le moli totale
		for(PairFluidoDouble fluido_moli:pair_fluido_moli){
			molitotali+=fluido_moli.value;
		}
		
	System.out.println("molitotali="+molitotali);

		// CREO HASMMAP CON FRAZIONI MOLARI ELEMENTI BASE
        map_m = new HashMap<Fluido, Double>();
		for(PairFluidoDouble fluido_moli:pair_fluido_moli){
			 double moli = fluido_moli.value;
            Fluido fluido = fluido_moli.fluido;
			log(String.format("AGGIUNGO FLUIDO %10s MOLI %3.3f",fluido,moli));
						
			for (Map.Entry<Fluido, Double> entry : fluido.getMap_m().entrySet()) {
				String.format("\t entry.getKey() %10s entry.getValue() %3.3f",entry.getKey(),entry.getValue());
				double molibase= moli*entry.getValue();
				Fluido fluidobase=entry.getKey();
				if(map_m.containsKey(fluidobase)){
					map_m.put(fluidobase,map_m.get(fluidobase)+molibase);
					System.out.println("\tLa mappa Contiene il fluido :" +fluidobase);					
				}else{
					System.out.println("\tLa mappa non contiene il fluido :" +fluidobase);
					map_m.put(fluidobase,molibase);			
				}
			}
		}
		// Trasformo i valori degli elementi basi in frazioni molari
			for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
				log(String.format("entry.getKey() %10s entry.getValue() %3.3f",entry.getKey(),entry.getValue()));
				
				double fr=entry.getValue()/ molitotali;
				log("Frazione molare fluido entry.getKey():" +entry.getKey()+" uguale a "+fr);
				map_m.put(entry.getKey(),entry.getValue()/molitotali);			
				log("Frazione molare fluido entry.getKey():" +entry.getKey()+" uguale a "+fr);
			}
			
			
		// Creo la mappa Ponderale
		map_p = new HashMap<Fluido, Double>();
		log("Creazione maooa ponderale ");
		//Per ogni elemento base calcolo la massa  	moli*fluido.massamolare=massa (dove moli =molitotali*frazioneMolare)	
		for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
			
			log(String.format("\t entry.getKey() %10s entry.getValue() %3.3f",entry.getKey(),entry.getValue()));
            double frazMolare= entry.getValue();
			double moli=frazMolare*molitotali;
			Fluido fluidobase=entry.getKey();
			log(String.format("\t Frazione molare fluido entry.getKey() %10s : %3.3f",entry.getKey(),frazMolare));
			
			double massa=moli*fluidobase.MassaMolare();
			 massatotale+=massa;
			 log(String.format("\t Fluido %10s  frazione molare %3.3f  numero moli %5.3f massa  %5.3f|",fluidobase,frazMolare,moli,massa));
     		map_p.put(fluidobase,massa);
				
		}
		log(String.format("Massa totale %f :",massatotale));
		log("tRASFORMO IN FRAZIONI PONDERALI");
		// Trasformo in frazioni ponderali
		for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
				log(String.format("\t entry.getKey() %10s entry.getValue() %3.3f",entry.getKey(),entry.getValue()));
				map_p.put(entry.getKey(),entry.getValue()/massatotale);
	
			}

		// CALCOLO LA MASSA MOLARE APPARENTE
        // = soommatoria di frazionemolare_i x Massamolare_i
        double massamolareapparente=0;
        for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
            massamolareapparente+=((entry.getKey().MassaMolare())*entry.getValue());
        }
 		log("massamolareapparente : "+massamolareapparente);
		double temperatura=0;
		int x=0;
		for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
		 	Fluido fluido=entry.getKey();
			log(String.format("\t entry.getKey() %10s entry.getValue() %3.3f",entry.getKey(),entry.getValue()));
		}		
		
	}
	
	
	
	
	
	public void Test_02()
	{
		log("-------");
		log("Test_02 inizzializzo con moli(volumi)");
		log("-------");
	
	HashMap<Fluido, Double> map_p;// map ponderali elemneti base
	HashMap<Fluido, Double> map_m;// map_m molarielementi base 
 
	double mo2=21.0;
	double mn2=79.0;
	PairFluidoDouble ossigeno_moli=new PairFluidoDouble(new OssigenoO2(),mo2 );
	PairFluidoDouble azoto_moli=new PairFluidoDouble(new AzotoN2(),mn2);
	List<PairFluidoDouble> pair_fluido_moli=new ArrayList<PairFluidoDouble>();
	pair_fluido_moli.add(azoto_moli);
	pair_fluido_moli.add(ossigeno_moli);
	 
	log(String.format("\t Miscela moli %3.1f di %s e moli %3.1f di %s ",mo2,"Ossigeno",mn2,"Azoto" ));
	Fluido fluido=new Miscela(pair_fluido_moli,Miscela.FLUIDO_MOLI);
	log("-----------------------------------------------------------------------------------------------");
	logfluido(fluido,300.0);		
	log("-----------------------------------------------------------------------------------------------");
	double mco2=10.0;
	PairFluidoDouble co2_moli=new PairFluidoDouble(new AnidrideCO2(),mco2 );
	pair_fluido_moli.add(co2_moli);
	log(String.format("\t Miscela moli %3.1f di %s,moli %3.1f di %s e moli %3.1f di %s ",mo2,"Ossigeno",mn2,"Azoto",mco2,"CO2" ));
	Fluido fluido2=new Miscela(pair_fluido_moli,Miscela.FLUIDO_MOLI);
	log("-----------------------------------------------------------------------------------------------");
	logfluido(fluido2,300.0);
	log("-----------------------------------------------------------------------------------------------");
	pair_fluido_moli.clear();
	PairFluidoDouble f1_m=new PairFluidoDouble(fluido,1.0 );
	PairFluidoDouble f2_m=new PairFluidoDouble(fluido2,1.0 );
	pair_fluido_moli.add(f1_m);
	pair_fluido_moli.add(f2_m);
	Fluido fluido3=new Miscela(pair_fluido_moli,Miscela.FLUIDO_MOLI);
	 
	log("-----------------------------------------------------------------------------------------------");
	logfluido(fluido3,300.0);
	log("-----------------------------------------------------------------------------------------------");
	}
   
   
 	public void Test_03()
	{
		log("-----------------------------------------------------------------------------------------------");
		log("Test_03 inizzializzo con pesi");
		log("-----------------------------------------------------------------------------------------------");
	
		HashMap<Fluido, Double> map_p;// map ponderali elemneti base
		HashMap<Fluido, Double> map_m;// map_m molarielementi base 
 
		double mo2=21.0;
		double mn2=79.0;
		PairFluidoDouble ossigeno_massa=new PairFluidoDouble(new OssigenoO2(),mo2 );
		PairFluidoDouble azoto_massa=new PairFluidoDouble(new AzotoN2(),mn2);
	
		List<PairFluidoDouble> pair_fluido_massa=new ArrayList<PairFluidoDouble>();
		pair_fluido_massa.add(azoto_massa);
		pair_fluido_massa.add(ossigeno_massa);
	 
		log(String.format("\t Miscela massa %3.1f di %s e massa %3.1f di %s ",mo2,"Ossigeno",mn2,"Azoto" ));
		Fluido fluido=new Miscela(pair_fluido_massa,Miscela.FLUIDO_MASSA);
		log("-----------------------------------------------------------------------------------------------");
		logfluido(fluido,300.0);		
		log("-----------------------------------------------------------------------------------------------");
		double mco2=10.0;
		PairFluidoDouble co2_massa=new PairFluidoDouble(new AnidrideCO2(),mco2 );
		pair_fluido_massa.add(co2_massa);
		log(String.format("\t Miscela massa %3.1f di %s,massa %3.1f di %s e massa %3.1f di %s ",mo2,"Ossigeno",mn2,"Azoto",mco2,"CO2" ));
		Fluido fluido2=new Miscela(pair_fluido_massa,Miscela.FLUIDO_MASSA);
		log("-----------------------------------------------------------------------------------------------");
		logfluido(fluido2,300.0);
		log("-----------------------------------------------------------------------------------------------");
		pair_fluido_massa.clear();
		PairFluidoDouble f1_m=new PairFluidoDouble(fluido,1.0 );
		PairFluidoDouble f2_m=new PairFluidoDouble(fluido2,1.0 );
		pair_fluido_massa.add(f1_m);
		pair_fluido_massa.add(f2_m);
		Fluido fluido3=new Miscela(pair_fluido_massa,Miscela.FLUIDO_MASSA);
	 
		log("-----------------------------------------------------------------------------------------------");
		logfluido(fluido3,300.0);
		log("-----------------------------------------------------------------------------------------------");
	
		PairFluidoDouble ossigeno_massa_2=new PairFluidoDouble(new OssigenoO2(),1.2 );
		pair_fluido_massa.add(ossigeno_massa_2);
		fluido3=new Miscela(pair_fluido_massa,Miscela.FLUIDO_MASSA);
	 
		log("-----------------------------------------------------------------------------------------------");
		logfluido(fluido3,300.0);
		log("-----------------------------------------------------------------------------------------------");
	}
	
		static final int OSSIGENO=1;
		static final int AZOTO=2;
		static final int ANIDRIDE_CARBONICA=3;
		static final int ARIA_BASE=5;
		static final int FUMO_CH4=6;
		static final int VAPORE_H20=7;
	
	 void creaMiscela(){
		 
		 log("\n Creazione miscela\n");
		 
		 int MASSA=0;
		 int MOLI=1;
		
		int NEW_FLUIDO=1;
		 int CALCOLA=2;
		 int RESEST=3;
		 int QUIT=4;
		 
		   
	
		 boolean test_1=true;
		 boolean test_2=true;
		 
		  List<PairFluidoDouble> list_fluidi =new ArrayList<PairFluidoDouble>();
		 
			double quantita=0;
			int FLUIDO=0;
		
			 PairFluidoDouble fluido=null;
			
		
			
			
			
			do{
				Scanner sc_fl = new Scanner(System.in);
				log("1 Aggiungi fluido");
				log("2 Calcola ");
				log("3 Resetta ");
				log(String.format("%d  : ESCI",QUIT));
				int input=0;
				//inputFlush();
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
				
				if(input==QUIT)
					break;
				log("Inserisci quantita");
				try{
					if(input==NEW_FLUIDO){
						inputFlush();
						if(sc_fl.hasNext())
								quantita=sc_fl.nextDouble();
						log(String.format("\t %d  Ossigeno",OSSIGENO));
						log(String.format("\t %d  Azoto",AZOTO));
						log(String.format("\t %d  Anidride carbonica",ANIDRIDE_CARBONICA));
						log(String.format("\t %d  Aria base",ARIA_BASE));
						//log(String.format("\t %d  : Dati fumo combustione metano",FUMO_CH4));
						log(String.format("\t %d  Vapore acqua ",VAPORE_H20));
						inputFlush();
						if(sc_fl.hasNext())
							FLUIDO= sc_fl.nextInt();
							switch (FLUIDO){
								case OSSIGENO:
										fluido=new  PairFluidoDouble(new OssigenoO2(),quantita);
										list_fluidi.add(fluido);
								break;
								case AZOTO:
									  fluido = new PairFluidoDouble(new AzotoN2(),quantita);
									list_fluidi.add(fluido);
								break;
								case ANIDRIDE_CARBONICA:
									  fluido = new PairFluidoDouble(new AnidrideCO2(),quantita);
									list_fluidi.add(fluido);
								break;
								case VAPORE_H20:
									  fluido = new PairFluidoDouble(new VaporeH2O(),quantita);
									list_fluidi.add(fluido);
								break;
							}
							log("Composizione (la quantità verra usata sia come massa che come volume per creare due miscele diverse)");
							for(PairFluidoDouble p_f:list_fluidi){
								Fluido f=p_f.fluido;
								log(String.format("%10s quantità %1.3f",f.toString(),p_f.value));
							}
					}else if(input==CALCOLA){
						if(list_fluidi.size()==0){
							log("Nessun elemento nella miscela ..!!");
							continue;
						}
							
						log_unita_misura();
						Fluido miscela = new Miscela(list_fluidi,Miscela.FLUIDO_MASSA);
						log("\n Miscela creata con quantità coniderate masse :");
						 logfluido(miscela,300.0);
						 log("\n Miscela creata con quantità coniderate moli o volumi:");
						 miscela =new Miscela(list_fluidi,Miscela.FLUIDO_MOLI);
						logfluido(miscela,300.0);
					
					}else if (input== RESEST){
							list_fluidi.clear();
					}else{
						
					}
				}catch(java.util.InputMismatchException e){
					log("Input NON VALIDO !! Inserire un numero ");
					continue;
				}
			}while(test_1);
		
			
			
			
		}
	 
	 
   
   
  /**********************************************
  * Method to make sure no data is available in the
  *   input stream
  **********************************************/
  public static void inputFlush() {
    int dummy;
    int bAvail;

    try {
      while ((System.in.available()) != 0)
        dummy = System.in.read();
    } catch (java.io.IOException e) {
      System.out.println("Input error");
    }
  }
   /*******************************************
	*		LOG  FLUIDO 
	********************************************/
   void logfluido(Fluido fluido,double T){
	
	double R;
	double Mm;
    double Mv;	double Cp;
    double CT;
	double DT;
	double VD;
    double VC;
    double NPr;
	log("Composizione ");
	log(((Miscela)fluido).toString());	
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
	log(String.format("\n Dati a temperatura %f ", T));
	log(String.format("|%5s|%5s|%5s|%5s|%5s|%6s|%10s|%10s|%10s|%5s|  ",                  "T", "R","Mm","Mv","Cp","CT","DT","VD","VC","NPr"));
	log(String.format("|%3.1f|%3.1f|%5.1f|%5.3f|%5.0f|%6.4f|%10.3e|%10.3e|%10.1e|%5.3f|",T,   R, Mm  ,Mv ,Cp  ,CT  , DT , VD , VC , NPr));
	
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