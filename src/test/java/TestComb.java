package com.antoiovi.unicig.fluidi.comb;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.antoiovi.unicig.fluidi.*;


public class TestComb{
	
	 @BeforeClass
     public static void init(){
		 System.out.println("Inizzializzo test");
	 }
	
	@Test
	public void Test_01()
	{
		double Q=25.0;
		double rend=0.9;
		double co2=9.0;
	Combustibile c=CombustibiliFactory.getInstance().getNewCombustibile(3);
	
	double qm=c.portataMassicaFumi(Q,rend, co2);
	log(c.toString());
	String n=c.getNome();
	log( String.format("Combustibile  %S  portata massica fumi %f [g/s] ",n,qm));
	
	
	double[] D_C=c.getDatiCombustibile();
	double[] D_F=c.getDatiCombustione();
	String format="";
	
	for(int x=0;x<D_C.length;x++){
			format=format+String.format(" %1.4f |",D_C[x]);
		}
	log(format);
	format="";
	for(int x=0;x<D_F.length;x++){
			format=format+String.format(" %1.4f |",D_F[x]);
		}
	log(format);
	
	
   }

private void log(String s){
System.out.println(s);
}

}
