package test;

import it.iovino.fluidi.AbstractFluido;
import it.iovino.fluidi.AnidrideCO2;
import it.iovino.fluidi.Aria_base;
import it.iovino.fluidi.AzotoN2;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.OssigenoO2;
import it.iovino.fluidi.VaporeH2O;

public class Testfluidi {
AzotoN2 n2=AzotoN2.getInstance();
OssigenoO2 o2=OssigenoO2.getInstance();
AnidrideCO2 co2=AnidrideCO2.getInstance();
VaporeH2O h2o=VaporeH2O.getInstance();
Aria_base aria=new Aria_base();
double temper=373.0;
double patm=101105.0;

public static void main(String[] args){
	Testfluidi tf=new Testfluidi();
	tf.test();
}
	public void test(){
		
		System.out.println("Temperatura "+temper+" K  "+(temper-273.0)+" °C");
		printFluido(o2);
		printFluido(n2);
		printFluido(co2);
		printFluido(h2o);
		printFluido(aria);
		
		
		
	}
	
	void printFluido(AbstractFluido f){
		double viscdin;
		double visccin;
		double capter;
		double R;
		double condterm;
		double lambda= f.lamdaSutherland();
		viscdin=f.ViscositaDinamica(temper);
		visccin=f.ViscCin(patm, temper);
		capter=f.CapTerm(temper);
		R=f.CostElasticita();
		condterm=f.CondicTermica(temper);
		
		String s=String.format("\n%s\tR%f\tLambda %f\t viscdin %1.3e\tCAPterm %1.1f\tConducterm %f",f, R,lambda,viscdin,capter,condterm);
		
		System.out.print(s);
	}
	
}
