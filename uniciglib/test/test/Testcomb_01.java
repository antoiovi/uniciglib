package test;

import it.iovino.fluidi.AnidrideCO2;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.combustione.CombMetano;

public class Testcomb_01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CombMetano combmetano=new CombMetano();
		Fluido m=combmetano.Combustione(30);
		System.out.println("eccesso aria aria 30%:\n"+m);
		String s=String.format("perc co2 secc= %1.2f\t perc co2 umid=%1.2f", 100*combmetano.getCo2_perc_secca()
				,100*combmetano.getCo2_perc_umida());
		s=s+String.format("perc o2 secc= %1.2f\t perc o2 umid=%1.2f", 100*combmetano.getO2_perc_secca()
				,100*combmetano.getO2_perc_umida());
		s=s+String.format("\nPortata %f\t Calore= %f", combmetano.getPortata(),combmetano.getPortata()*combmetano.getCalore());
			System.out.println(s);
		m=combmetano.Combustione(150);
		System.out.println("eccesso aria aria 150%:\n"+m);
		 s=String.format("perc co2 secc= %1.2f\t perc co2 umid=%1.2f", 100*combmetano.getCo2_perc_secca()
					,100*combmetano.getCo2_perc_umida());
		 s=s+String.format("perc o2 secc= %1.2f\t perc o2 umid=%1.2f", 100*combmetano.getO2_perc_secca()
					,100*combmetano.getO2_perc_umida());
		 s=s+String.format("\nPortata %f\t Calore= %f", combmetano.getPortata(),combmetano.getCalore());
		 System.out.println(s);
		 
		m=combmetano.Combustione(15);
		System.out.println("eccesso aria aria 15%:\n"+m);
		 s=String.format("perc co2 secc= %1.2f\t perc co2 umid=%1.2f", 100*combmetano.getCo2_perc_secca()
					,100*combmetano.getCo2_perc_umida());
		 s=s+String.format("perc o2 secc= %1.2f\t perc o2 umid=%1.2f", 100*combmetano.getO2_perc_secca()
					,100*combmetano.getO2_perc_umida());
		 s=s+String.format("\nPortata %f\t Calore= %f", combmetano.getPortata(),combmetano.getCalore());	 
		 System.out.println(s);
		
			 m=combmetano.Combustione(0);
		System.out.println("Indice aria 0:\n"+m);
		 s=String.format("perc co2 secc= %1.2f\t perc co2 umid=%1.2f", 100*combmetano.getCo2_perc_secca()
					,100*combmetano.getCo2_perc_umida());
		 s=s+String.format("perc o2 secc= %1.2f\t perc o2 umid=%1.2f", 100*combmetano.getO2_perc_secca()
					,100*combmetano.getO2_perc_umida());
		 s=s+String.format("\nPortata %f\t Calore= %f", combmetano.getPortata(),combmetano.getCalore());			
		 System.out.println(s+"\n\n");
		 
		System.out.println(combmetano.Portata(1,0)*1000);
		System.out.println(combmetano.Portata(1,30)*1000);
		System.out.println(combmetano.Portata(1,150)*1000);
		System.out.println("Stechiometrica :");
		/*m=combmetano.CombustioneStech();
		s=String.format("perc co2 secc= %1.2f\t perc co2 umid=%1.2f", 100*combmetano.getCo2_perc_secca()
				,100*combmetano.getCo2_perc_umida());
	System.out.println(m);*/ 
	double cosecca=combmetano.getCo2_perc_secca();
	
	double p2=combmetano.PortataConCO2(cosecca*100, 1);
	System.out.println(String.format("Cosecca= %1.2f  Portata massica = %1.2f",cosecca,p2*1000));
	p2=combmetano.PortataConCO2(8.7, 1);
	System.out.println(String.format("Cosecca= %1.2f  Portata massica = %1.2f\t O2 secco= %1.2f ",cosecca, p2*1000,
						combmetano.getO2_perc_secca()*100));
	
	
	}

}
