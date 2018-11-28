package com.antoiovi.unicig.fluidi.comb;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.antoiovi.unicig.fluidi.*;
import com.antoiovi.unicig.impianti.*;


public class TestCaldaia{
	 @BeforeClass
     public static void init(){
		 System.out.println("Inizzializzo test");
	 }

	@Test
	public void Test_01()
	{
		 /***
*  Costruttore di default per bruciatore aria soffiata
*
* @param double tm temperatura media prodotti combustione °C
* @param double potenza utile in kW
* @param int combustibile : valore da 0 a 13 ; NON VIENE FATTO IL CHECK
*  			Caldaia.COKE   Caldaia.ANTRACITE  Caldaia.LIGNITE Caldaia.RFO_4   Caldaia.RFO_2 Caldaia.RFO_1 Caldaia.RISC_DOM
*			Caldaia.CHEROSENE  Caldaia.GAS_NAT_H  Caldaia.GAS_NAT_L  Caldaia.GAS_LIQUIDO  Caldaia.LEGNO_23 Caldaia.LEGNO_33
*  			Caldaia.PALLET
 ****/

 double Q=10;

 Caldaia c= new  Caldaia(Q,3,350);

	logCaldaia(c);
 }

private void log(String s){
System.out.println(s);
}

private void logCaldaia(Caldaia c){
	String format="";
	double value;
	value =c.portataMassicaFumi( );
	format=String.format(" portataMassicaFumi  %1.4f |",value);
	log(format);
	/**
	*  Formula B.3 ( R )
	* @param co2 tenore di co2% secco
	* @return R in J/(kgxK)
	**/

	value= c.CostElasticita_1();
	format=String.format("CostElasticita_1 %1.4f |",value);
	log(format);


	/**
	*  Formula B.4 ( cp )
	* @param tm temperatura media fumi
	* @param co2 tenore di co2% secco
	* @return cp in J/(kgxK)
	**/

	value= c.CapTermica();
	format=String.format("CapTermica %1.4f |",value);
	log(format);

	/**
	*  Formula B.5 ( sigma(H2O) )
	* @param co2 tenore di co2% secco
	* @return sigma(H2O) tenore del vapore acqueo nei prodotti combustione in %
	**/

	value= c. TenoreH2O();
	format=String.format("TenoreH2O %1.4f |",value);
	log(format);
	/**
	*  Formula B.6 ( PD) )
	* @param PL pressione dell'aria esterna in Pa
	* @param h20 sigma(H2O) tenore del vapore acqueo nei prodotti combustione in % (Calcolare con B.5)
	* @return PD pressione parziale del vapore acqueo in Pa
	**/
	value= c. PparzialeH2o();
	format=String.format("PparzialeH2o %1.4f |",value);
	log(format);

	/**
	*  Formula B.7 ( tp )
	* @param PD pressione parziale del vapore acqueo in Pa
	*
	* @return tp temperatura punto di rugiada in °C
	**/
	value= c. tempPuntoRugiada();
	format=String.format("tempPuntoRugiada %1.4f |",value);
	log(format);

	/**
	*  Formula B.8 ( delta tsp )
	* @param Kf fattore di conversione da SO2 a SO3 in % ???
	*
	* @return aumento del punto di rugiada in Kelvin
	**/
	value= c. deltaTsp();
	format=String.format("deltaTsp  %1.4f |",value);
	log(format);
	/**
	*  Formula B.9 ( lambda A )
	* @param value= tm temperatura media prodotti combustione
	*
	* @return coefficiente di conducibilità termica in W/(m x K)
	**/
	value= c.lambdaA();
	format=String.format("lambdaA %1.4f |",value);
	log(format);

	/**
	*  Formula B.10 ( eta A viscosità dinamica )
	* @param value= tm temperatura media prodotti combustione
	*
	* @return viscosità dinamica dei prodotti della combustione  in N s/m2
	**/
	value= c. viscDin();
	format=String.format("viscDin %1.4e |",value);
	log(format);

	/**
	*  Formula B.11 ( R )
	* @param co2 tenore di co2% secco
	* @param h2o tenore di vapore acqua
	*
	* @return R in J/(kgxK)
	**/

	value= c. CostElasticita_2();
	format=String.format("CostElasticita_2 %1.4f |",value);
	log(format);


	/**
	*  Formula B.12 ( sigma(H2O) )
	* @param PD pressione parziale del vapore acqueo in Pa
	* @param PL pressione dell'aria esterna in Pa
	* @return sigma(H2O) tenore del vapore acqueo nei prodotti combustione in %
	**/
	value= c. TenoreH2OPD();
	format=String.format("TenoreH2OPD %1.4f |",value);
	log(format);


	/**
	*  Formula B.13 ( PD )
	* @param  tp temperatura punto di rugiada in °C
	*
	* @return PD pressione parziale del vapore acqueo in Pa
	**/
	value= c. PparzialeH2oTp();
	format=String.format("PparzialeH2oTp %f |",value);
	log(format);



}

}
