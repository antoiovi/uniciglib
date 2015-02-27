package test;

import java.util.ArrayList;
import java.util.List;

import it.iovino.fluidi.AzotoN2;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.OssigenoO2;
import it.iovino.fluidi.PairMassaFluido;
import it.iovino.fluidi.PairMoliFluido;
import it.iovino.fluidi.VaporeH2O;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
AzotoN2 n2=AzotoN2.getInstance();
OssigenoO2 o2=OssigenoO2.getInstance();
double[] t=o2.getCol(0);
double P=101325.0;

printfluido(n2,P,t);
printfluido(o2,P,t);

PairMassaFluido ox=new PairMassaFluido(25.0,o2); 
PairMassaFluido nx=new PairMassaFluido(75.0,n2);
List<PairMassaFluido> lst=new ArrayList<PairMassaFluido>();
lst.add(ox);
lst.add(nx);
Miscela misc=new Miscela(lst);
printfluido(misc,P,t);

PairMoliFluido oxm=new PairMoliFluido(22.0,o2); 
PairMoliFluido nxm=new PairMoliFluido(78.0,n2);
List<PairMoliFluido> lstm=new ArrayList<PairMoliFluido>();
lstm.add(oxm);
lstm.add(nxm);
 Miscela miscm=new Miscela(lstm,0);
 printfluido(miscm,P,t);
 
 VaporeH2O vh2o=VaporeH2O.getInstance();
 printfluido(vh2o,P,vh2o.getCol(0));
	}
	
	public static void printfluido(Fluido fluido, double P,double[] t){
		System.out.println("Fluido :");
		System.out.println(fluido+"\tMassaMolare= "+fluido.MassaMolare()+"\tR= "+fluido.CostElasticita());
		for(int x=0;x<t.length;x++){
			double Temp=t[x];
			String s=String.format("%1.0f\t %1.3f\t %f\t %f\t %2.2e\t %2.2e\t %2.2e\t %f",
					Temp,fluido.MassaVolumica(P, Temp),fluido.CapTerm(Temp),fluido.CondicTermica(Temp),
					fluido.DiffusivitaTermica(Temp),fluido.ViscositaDinamica(Temp),fluido.ViscCin(P, Temp),
					fluido.NumeroPrandtl(P, Temp));
		System.out.println(s);
			}
		
	}
}

