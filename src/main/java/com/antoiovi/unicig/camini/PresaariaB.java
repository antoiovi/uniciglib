package com.antoiovi.unicig.camini;

import it.iovino.fluidi.Aria_base;
import it.iovino.fluidi.Fluido;
import it.iovino.utilita.Mylogger;

public class PresaariaB {
	Mylogger logger;
	 /**
     * Portata d'aria inerruttore di tiraggio,
     * @return
     */
    double  port_massaAria=0;
    /**
     * perdita int tiraggio
     * perdita apertura
     * area int tiraggio
     * area apertura
     */
    double csi_i;
    double csi_d;
    double Ai;
    double Ad;
   Fluido aria;
    
    public PresaariaB(double csi_i, double csi_d, double ai, double ad) {
		super();
		this.csi_i = csi_i;
		this.csi_d = csi_d;
		Ai = ai;
		Ad = ad;
		aria=new Aria_base();
	}


	public void setLogger(Mylogger logger) {
		this.logger = logger;
	}


	public PresaariaB(Mylogger logger,   double csi_i,
			double csi_d, double ai, double ad) {
		super();
		this.logger = logger;
		 this.csi_i = csi_i;
		this.csi_d = csi_d;
		Ai = ai;
		Ad = ad;
		aria=new Aria_base();
	}


	public double MaIntTir(double p_effettiva,double port_mass_fumo,double patm, double tamb){
		if(logger!=null){
	    	logger.appendMessage(Mylogger.FINE, String.format("\n\tMAINTTIT :\n\tCalcolo aria parassita tiraggio \n\t\tPefftiva =%1.3f Massa fumi=%1.3f"
	    			+ " Massa aria=%1.3f", p_effettiva,port_mass_fumo,port_massaAria));
	}
		if(p_effettiva<0){
    	port_massaAria=port_mass_fumo*0.5;
    	if(logger!=null)
    	logger.appendMessage(Mylogger.FINE, String.format("\n\tMAINTTIT :Calcolo aria prassita \n\t\t Pefftiva MINORE DI ZERO =%1.3f Massa fumi=%1.3f"
    			+ " Massa aria=%1.3f", p_effettiva,port_mass_fumo,port_massaAria));
    return port_massaAria;	
    }
		double mva=aria.MassaVolumica(patm, tamb);
    	double ti=csi_i/(2*mva*Ai*Ai);
    	double td=csi_d/(2*mva*Ad*Ad);
    	double a=p_effettiva*(ti+td);
    	double b=ti*td*port_mass_fumo*port_mass_fumo;
    	double c=a-b;
    	if(logger!=null){
    		logger.appendMessage(Mylogger.FINEST, 
        			String.format("\n\t\t massa voluica aria %f pressione atmosferica %f  temperatura %f "
        			,mva,patm,tamb));
    		logger.appendMessage(Mylogger.FINEST, 
        			String.format("\n\t\t: fattore perd localiz. interr. tiraggio %f : fattore perd localiz. apertura aria %f"
        			,csi_i,csi_d));
    		logger.appendMessage(Mylogger.FINEST, 
        			String.format("\n\t\tArea interr. tiraggio %f : Area apertura aria %f"
        			,Ai,Ad));
        	logger.appendMessage(Mylogger.FINEST, 
        			String.format("\n\t\t ti=%f  td=%f a=p_effettiva*(ti+td)=%f b=%f  a-b=%f "
        			,ti,td,a,b,c));
    	}
    	if(c<0){
    		port_massaAria=port_mass_fumo*0.5;
        	if(logger!=null)
        	logger.appendMessage(Mylogger.FINE, String.format("\n\tMAINTTIT :\n\t\tCalcolo aria parassita\n\t\t Termine sotto radice < di 0;\n\t\t Pefftiva  =%1.3f Massa fumi=%1.3f"
        			+ " Massa aria=%1.3f", p_effettiva,port_mass_fumo,port_massaAria));
        return port_massaAria;	
       
    	}
    	c=Math.sqrt(c);
    	if(logger!=null)
        	logger.appendMessage(Mylogger.FINE, 
        			String.format("\n\tMAINTTIT :\n\t\t RADQ(c)=%f ",c));
        
    	 double num=(-port_mass_fumo*ti+c);
    	port_massaAria=num/(ti+td);
    	if(logger!=null)
        	logger.appendMessage(Mylogger.FINEST,String.format("\n\tMAINTTIT :\n\t\t-port_mass x ti= %f  num %f den %f ",-port_mass_fumo*ti,num,(ti+td)));
        
    	if(port_massaAria >port_mass_fumo){
    		if(logger!=null)
            	logger.appendMessage(Mylogger.FINEST,String.format("\n\tMAINTTIT :\n\t\tmassa aria maggiore massa fumo %f",port_mass_fumo));
    		//port_massaAria=port_mass_fumo;
    	}
    	if(logger!=null)
    	logger.appendMessage(Mylogger.FINE, String.format("\n\tMAINTTIT :\n\t\tAria parassita CALCOLATA \n\t\tPefftiva=%1.3f Massa fumi=%1.3f"
    			+ " Massa aria=%1.3f", p_effettiva,port_mass_fumo,port_massaAria));
       	return port_massaAria;
    }


	public double getCsi_i() {
		return csi_i;
	}


	public void setCsi_i(double csi_i) {
		this.csi_i = csi_i;
	}


	public double getCsi_d() {
		return csi_d;
	}


	public void setCsi_d(double csi_d) {
		this.csi_d = csi_d;
	}


	public double getAi() {
		return Ai;
	}


	public void setAi(double ai) {
		Ai = ai;
	}


	public double getAd() {
		return Ad;
	}


	public void setAd(double ad) {
		Ad = ad;
	}


	public double getPort_massaAria() {
		return port_massaAria;
	}
	
}
