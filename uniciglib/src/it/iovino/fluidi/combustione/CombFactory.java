package it.iovino.fluidi.combustione;

public class CombFactory {
	static  CombFactory instance;
	CombFactory (){
		//instance=new CombFactory();
	}
	
	public static CombFactory getInstance(){
		if(instance==null){
			instance=new CombFactory();
		}
		return instance;
	}
	
	public Combustione getCombustione(String gas){
		if(gas.equals("METANO")){
		Combustione c=new CombustioneMetano();
		return c;
		}
		return null;
	}
}
