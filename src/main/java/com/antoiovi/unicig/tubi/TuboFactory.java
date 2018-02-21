package com.antoiovi.unicig.tubi;

public class TuboFactory {
private static TuboFactory instance=null;
double  lung; 
double resterm ;
double rug;
private TuboFactory(){

}
	
public static TuboFactory getInstace(){
		if (instance==null)
			instance=new TuboFactory();
		return instance;
}

	public  Tubo TuboC(double diam_int,double diam_est,double  lung, double resterm , double rug){
		return new TuboC(diam_int,diam_est, lung, resterm, rug) ;
	}
	public  Tubo TuboR(double lato_lung,double lato_cort,double spess,double  lung, double resterm , double rug){
		return new TuboR(lato_lung,lato_cort, spess,lung, resterm, rug) ;
	}
	public Tubo TuboQ(double lato,double spess,double  lung, double resterm , double rug){
		return new TuboQ(lato,spess, lung, resterm, rug) ;
	}
	
}
