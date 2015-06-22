/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antoiovi.unicig.condotti;

import com.antoiovi.unicig.tubi.Tubo;
import com.antoiovi.unicig.tubi.TuboC;
import com.antoiovi.unicig.tubi.TuboR;

import it.iovino.fluidi.Aria_base;
import it.iovino.fluidi.FluidiMath;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.PairMassaFluido;
import it.iovino.utilita.Emptylog;
import it.iovino.utilita.Mylogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antoiovi
 *  
 *  5/11/2014 : CalcolaTempe Ë chiamata dentro le funzioni Somma e Serie
 *  5/11/2014: Pressione effettiva calcolata in base alle pressioni dei condotti superiori 
 *  
 *   3/06/2015 aggiunto forma e area
 */
public class CondottoBase implements Condotto {
 
	String forma;
double area;
double dh;


    double altezza;// tubo orizzontale Haltezza=0
    Tubo tubo;
    protected double Patm;
    double massvolumaria;
    protected double coeffLimEst;
    protected double M1;// Portata massica fluido interno
    protected double temperEst;//Temperatura ambiente
    protected double temper_aria=293;// temperatura aria per verifiche pressioni
    protected double temperIntIniz;//Temperatura iniziale
    protected double temperIntMedia;//Temperatura media
    protected Fluido fluidoInt;
    // Aria esterna
    protected Fluido aria;

    double SE = 1.2;
    // Verso di percorrenza per il calcolo delle pressioni statiche : +1 slaita, -1 discesa
    // puÚ essere modificato quando si calcola la Pressione statica
    protected double B=1.0;
    // La calcola
    double pressEffettiva=0;
    double d_P=0;
    List<Double> csi;

    /**
     * velocit√†Fluido: incapsula 
     *          Massa volumica media; Velocit√† media Numero di Raynolds
     *          Fattore di attrito liscio, Fattore di attrito Ruvido
     */
 VelocitaFluido velocitaFluido;
    
    /**
     * vartempnoncoas : incapsula
     *      Temperatura fluido interno(caldo) Uscita
     *      Temperatura media
     *      Numero di Nusselt
     *      KR fattore di Raffreddamento, k Coeff globale di scambio termico
     *      ai Coefficinet liminare interno
     */
    VarTempNonCoas vartempnoncoas;

    {
        aria = new Aria_base();
        csi = new ArrayList<Double>();
    }
      Mylogger logger=  Emptylog.getInstance();

   
public   void setLogger(Mylogger logger) {
	velocitaFluido.setLogger(logger);
		 this.logger = logger;
	}
/**
 * Utilizzo : 
 * 	1-creare il condotto  con i parametri del costruttore
 * 2- chiamare CalcolaTemper()
 * 3 - Calcolre Pstatica(int B)
 * 4- Calcolare peeffettiva con parametro Pstatica condotti superiori
 * 5- Reset: ricalcolo pettendo temperatura iniziale=temperatura media
 * 6- Usando Somma(c1,c2) metto nel condotto la somma dei fluidi di c1 e c2 , e ricalcolo la temperatura
 * 15/06/2015 Modifiche
 * 	Utilizzare peffettiva(peffsuper)
 * 		vemgono calcolate automaticamte pstatica e perdite
 * 		se devosottrarre le perdite comignolo, mettere peffsuperiore come valore negativo.
 * @param tubo
 * @param Patm
 * @param CoefLimE
 * @param M1 massa fluido interno
 * @param Ta
 * @param Tfi
 * @param FluidoI
 */
    
    public CondottoBase(Tubo tubo, double Patm, double CoefLimE, 
    		double M1, double Ta, double Tfi, Fluido FluidoI) {
        this.tubo = tubo;
        this.Patm = Patm;
        this.coeffLimEst = CoefLimE;
        this.M1 = M1;
        this.temperEst = Ta;
        this.temperIntIniz = Tfi;
        this.fluidoInt = FluidoI;
        // Tubo verticale
        altezza=tubo.Lung();
        velocitaFluido = new VelocitaFluido();
        vartempnoncoas = new VarTempNonCoas();
        temperIntMedia = Tfi;
        massvolumaria=aria.MassaVolumica(Patm, temper_aria);// utlizzata per le verifiche del tiraggio
        setTubo(tubo);
        velocitaFluido.setLogger(logger);
        CalcolaTemper();
        
    }
    /**
     * Costruttore senza impostare i dati del fluido: per i calcoli bisogna 
     * 			prima chiamare setData(......)
     * @param tubo
     * @param CoefLimE
     */
    public CondottoBase(Tubo tubo,  double CoefLimE,double Halt) {
    	this.altezza=Halt;
      setTubo(tubo);
        // Tubo verticale
        altezza=tubo.Lung();
        velocitaFluido = new VelocitaFluido();
        vartempnoncoas = new VarTempNonCoas();
        temperIntMedia = temperIntIniz;
        velocitaFluido.setLogger(logger);
        //CalcolaTemper();
    }
    /**
     * Imposta i valori dipendenti dal fluido interno (e pressione atm esterna) ed esegue i calcoli
     * da usare se si usa il costruttore con solo Tubo e CoefLimesterno
     * @param Patm
     * @param M1
     * @param Ta
     * @param Tfi
     * @param FluidoI
     */
    
    public void setData(double Patm, double M1, double Ta, double Tfi, Fluido FluidoI){
    	 this.Patm = Patm;
        this.M1 = M1;
        this.temperEst = Ta;//per il calcolo emp rugiada
        this.temperIntIniz = Tfi;
        this.fluidoInt = FluidoI;
        // Tubo verticale
       //  Haltezza=tubo.Lung();
        massvolumaria=aria.MassaVolumica(Patm, temper_aria);
        
        velocitaFluido = new VelocitaFluido();
        velocitaFluido.setLogger(logger);
        vartempnoncoas = new VarTempNonCoas();
        vartempnoncoas.setLogger(logger);
        temperIntMedia = Tfi;
        CalcolaTemper();
    }

    public CondottoBase() {
        velocitaFluido = new VelocitaFluido();
        vartempnoncoas = new VarTempNonCoas();

    }

    public void CalcolaTemper() {
        // primo ciclo metto Tm=T
    	logger.appendMessage(Mylogger.FINE, String.format("\nCALCOLA TEMPERATURA start:\n\t Patm %1.0f Portata %1.3f  Temp %1.3f ", Patm,M1,temperIntMedia));
        velocitaFluido.Calcola(Patm, M1, temperIntMedia, fluidoInt, tubo);
        vartempnoncoas.Calcola(tubo, M1, fluidoInt, coeffLimEst, velocitaFluido, temperEst, temperIntIniz);
       
        temperIntMedia = vartempnoncoas.getTfm();
        logger.appendMessage(Mylogger.FINE, String.format("\nCALCOLA TEMPERATURA end: Temp media: %1.2f Portata %1.3f  Velocita %1.3f ",  temperIntMedia,M1,Wm()));
    }

   /* public double getTm() {
        return Tm;
    }

    public double getTfu() {
        return vartempnoncoas.getTfu();
    }*/

    @Override
    public Fluido fl_int() {
        return this.fluidoInt;
    }

    /**
     * Ricalcola la temperatura reimpostando la temperatura media uguale alla 
     * temperatura iniziale
     */
    public void Reset() {
        temperIntMedia = temperIntIniz;
        velocitaFluido.Calcola(Patm, M1, temperIntMedia, fluidoInt, tubo);
        vartempnoncoas.Calcola(tubo, M1, fluidoInt, coeffLimEst, velocitaFluido, temperEst, temperIntIniz);
        temperIntMedia = vartempnoncoas.getTfm();
    }

    /**
     * Somma i due fluidi e li inserisce nel condotto c1
     *
     * @param c1
     * @param c2
     */
    public void Somma(Condotto c1, Condotto c2) {
        M1 = c1.M1() + c2.M1();
        // se masse condotti vuote allora non fare nulla
        if(M1==0)
            return;
        
        double Tf1, Tf2, Mf1, Mf2;
        double Tfu0;
        Tf1 = c1.Tu();
        Tf2 = c2.Tu();
        Mf1 = c1.M1();
        Mf2 = c2.M1();
        double cp1 = c1.fl_int().CapTerm(Tf1);
        double cp2 = c2.fl_int().CapTerm(Tf2);

        /* 
        Uno dei due condotti ha massa fluido = a zero?
            se si allora la temperatura media √® uguale alla temperatura di uscita del 
            condotto che non ha massa = a zero;
        */
        double Mfu = Mf1 + Mf2;
        M1 = Mfu;
        if (Mf1 == 0) {
            this.temperIntIniz = Tf2;
            temperIntMedia = temperIntIniz;
            fluidoInt=c2.fl_int();
            return;
        } else if (Mf2 == 0) {
            this.temperIntIniz = Tf1;
            temperIntMedia = temperIntIniz;
            fluidoInt=c1.fl_int();
            this.CalcolaTemper();
            return;
        }
        /*
            I due condotti hanno entrambi massa diversa da zero :
            Applico formula 12 ( bilancio dell'energia )
        */
        
        // Creo la miscela di uscita
        PairMassaFluido mf_1,mf_2;
        mf_1=new PairMassaFluido(c1.M1(),c1.fl_int());
        mf_2=new PairMassaFluido(c2.M1(),c2.fl_int());
        List<PairMassaFluido> listmassefluido=new ArrayList<PairMassaFluido>();
        listmassefluido.add(mf_1);
        listmassefluido.add(mf_2);
        Fluido FluidoU=new Miscela(listmassefluido);
        
         /*Primpo tentativo : Temperatura dopo confluenza = media Ponderata*/
        Tfu0 = (Tf1 * Mf1) + (Tf2 * Mf2);
        Tfu0 = Tfu0 / (Mf1 + Mf2);
        double cpU =FluidoU.CapTerm(Tfu0);
//        double cpU0 = (Mf1 * cp1 * Tf1) + (Mf2 * cp2 * Tf2);
  //      cpU0 = cpU0 / (Mfu * Tfu0);
        double tfu = 0;
        
        do {
        	tfu = (Mf1 * cp1 * Tf1) + (Mf2 * cp2 * Tf2);
            tfu = tfu / (Mfu * cpU);
            double dT = tfu - Tfu0;
            double modulo = dT>0? dT: -dT;
            if (modulo < 0.5) {
                break;
            }
            Tfu0 = tfu;
            cpU =FluidoU.CapTerm(Tfu0);
        } while (true);
        this.temperIntIniz = tfu;
        temperIntMedia = temperIntIniz;
        // Fluido uguale alla miscela
        fluidoInt=FluidoU;;
        return;
    }
    /**
     * Modifico il fluido interno 
     */
    @Override
	public void setFluidoI(Fluido fluidoi, double m2, double T2) {
        this.fluidoInt = fluidoi;
        M1=m2;
        temperIntMedia = T2;
        CalcolaTemper();
		
	}
    
    /**
     * Aggiungo il fluido con massa m e T2: utilizzato per aggiungere l'aria degli interruttori 
     * di tiraggio; psoos uasrlo anche per miscelar un  fluido proveniente da un altro condott
     * Modifica il fluido interno
     * @param f
     * @param m
     * @param T2
     */
    public void addFluido(Fluido f2,double m2, double T2){
    	String message;
    	if(logger!=null){
    	String s=String.format("\n\tADDFLUIDO: ");
    	logger.appendMessage(Mylogger.FINEST, s);
    	s=String.format("\n\t\tFluido nel condotto PRIMA %s \n\t\t\t massa %1.3f  Temperatura %1.3f ", fluidoInt,M1,temperIntMedia);
    	logger.appendMessage(Mylogger.FINEST, s);
    	s=String.format("\n\t\tNuovo fluido %s \n\t\t\t portata massic %1.3f  Temperatura %1.3f ", f2,m2,T2);
    	logger.appendMessage(Mylogger.FINEST, s);
    }
    	double Mtot =M1+m2;
        // se masse condotti vuote allora non fare nulla
        if(m2==0)
            return;
        double Tf1, Tf2, Mf1, Mf2;
        double Tfu0;
        Tf1 =this.temperIntMedia;
        double cp1 = fluidoInt.CapTerm(temperIntMedia);
        double cp2 = f2.CapTerm(T2);
        // Creo la miscela di uscita
        PairMassaFluido mf_1,mf_2;
        mf_1=new PairMassaFluido(M1,fluidoInt);
        mf_2=new PairMassaFluido(m2,f2);
        List<PairMassaFluido> listmassefluido=new ArrayList();
        listmassefluido.add(mf_1);
        listmassefluido.add(mf_2);
        Fluido FluidoU=new Miscela(listmassefluido);
        
         /*Primpo tentativo : Temperatura dopo confluenza = media Ponderata*/
        Tfu0 = (temperIntMedia * M1) + (T2 * m2);
        Tfu0 = Tfu0 / (M1 + m2);
        double cpU =FluidoU.CapTerm(Tfu0);
        if(logger!=null){
        	logger.appendMessage(Mylogger.FINEST,String.format("\n\t\tFluido in uscita %s", FluidoU));
        	message=String.format("\n\t\t calore specifico fluido interno cp1 %1.1f "
        			+ "calore specifico fluido  aggiunto cp2 %1.1f calore specifico fluido in uscita cpu %1.1f", cp1,cp2,cpU);
        	logger.appendMessage(Mylogger.FINEST,message);
        }
        double tfu = 0;
        do {
        	 if(logger!=null){
             	message=String.format("\n\t\t calore specifico fluido interno cp1 %1.1f "
             			+ "calore specifico fluido  aggiunto cp2 %1.1f calore specifico fluido in uscita cpu %1.1f", cp1,cp2,cpU);
             	logger.appendMessage(Mylogger.FINEST,message);
             }
        	tfu = (M1 * cp1 * Tf1) + (m2 * cp2 * T2);
            tfu = tfu / (Mtot * cpU);
            double dT = tfu - Tfu0;
            double modulo = dT>0? dT: -dT;
            if (modulo < 0.5) {
                break;
            }
            Tfu0 = tfu;
            cpU =FluidoU.CapTerm(Tfu0);
          
             
        } while (true);
        M1=Mtot;
        this.temperIntIniz = tfu;
        temperIntMedia = temperIntIniz;
        // Fluido uguale alla miscela
        fluidoInt=FluidoU;
        if(logger!=null){
        String s=String.format("\n\t\t dopo somma:\n\t\t fluido %s \n\t\t massa %1.3f  Temperatura %1.3f ", fluidoInt,M1,temperIntMedia);
    	logger.appendMessage(Mylogger.FINEST, s);
        }
        CalcolaTemper();
        return;
    }

    @Override
    public void Serie(Condotto c1) {
        M1 = c1.M1();
        temperIntIniz = c1.Tu();
        temperIntMedia = temperIntIniz;
        fluidoInt=c1.fl_int();
        this.CalcolaTemper();
    }

    public double getM1() {
        return M1;
    }

    public Fluido getFluidoI() {
        return fluidoInt;
    }

    @Override
    public double M1() {
        return M1;
    }

    /**
     * Pressione statica
     * peso aria meno peso fumi
     * @param B
     * @return
     */
    @Override
    public double Ps(double B) {
    	this.B=B;
        //  System.out.println("Calcolo PS :");
        double ps = (aria.MassaVolumica(Patm, temperEst) - velocitaFluido.getMassaVolumicaMedia()) * altezza * 9.81 * B;
        if(logger!=null){
        	double mva=aria.MassaVolumica(Patm, temperEst);
        	double mvf=velocitaFluido.getMassaVolumicaMedia();
        	logger.appendMessage(Mylogger.FINE, String.format("\nPS(1/-1) CALCOLO PS: \n\tdensit‡ aria= %1.3f \tdensita fumo= %1.3f \taltezza=%1.3f \tps=%1.3f",mva,mvf,altezza,ps));
        	 
        }
     // System.out.println("delta Aria= "+aria.MassaVolumica(Patm,Ta)+"deltaGAs= "+velocitaFluido.getMassaVolumicaMedia()
    // +"  Patm "+Patm+"  Ta "+Ta+"Haltezza= "+Haltezza+"   ps="+ps);
        return ps;//(aria.MassaVolumica(Patm,Ta)-velocitaFluido.getMassaVolumicaMedia())*Haltezza*9.81*B;
    }
    
    @Override
	public double d_P() {
		// TODO Auto-generated method stub
		return d_P;
	}
    
    /**
     *
     * @return perdita di carico come somma di perdite distribuite e perdite
     * localizzate
     */
    @Override
    public double Calcola_d_P() {

        double densita = velocitaFluido.getMassaVolumicaMedia();
        double csi_sum = 0;
        double p_conc;
        for (int x = 0; x < csi.size(); x++) {
            csi_sum += csi.get(x); // somma perdite concentrate
        }
        
        double f_attr = velocitaFluido.getFattAttrRuvido();
        double csi_dist = f_attr * tubo.Lung() / tubo.Dh();
        if(logger!=null){
        	logger.appendMessage(Mylogger.FINE,"\n\tCalcolo perdite distribuite e concentarte"); 
        	String s1=String.format("\n\tFattore attrito %1.4f \tfattore perdite distribuite %1.2f "
        			+ "\t Fattore perdite concentrate %f    ", f_attr,csi_dist,csi_sum);
       logger.appendMessage(Mylogger.FINEST, s1);
        }
        
       
        p_conc = d_P(csi_sum);
        double p_dist = d_P(csi_dist);
        double d_p = SE * (p_conc + p_dist);
        if(logger!=null){
        	   	String s1=String.format("\n\t perdite concentrate %f perdite distribuite %f [pa]",p_conc,p_dist);
        	logger.appendMessage(Mylogger.FINEST,s1); 
        	s1=String.format("\n\tFattore sicurezza fluidodinamico SE %f  "
        			+ "\t perdite totati  %f [pa]", SE,d_p);
        	logger.appendMessage(Mylogger.FINEST,s1); 
       }
        d_P=d_p;
        return d_p;

    }
    /**
     *Calcola  la caduta di pressione data la perdita localizzata  
     * @param csi
     * @return
     */
    @Override
    public double d_P(double csi) {
    	double densita=ro_m();
        double dp = 0.5 * densita * Math.pow(Wm(), 2) * csi;
        if(logger!=null){
        	String s1=String.format("\n\t\t d_P(csi) calcolo perdite concentrate: \n\t\tcsi %1.2f \tdensita fluido %1.3f \tvelocita %1.3f\t d_P %1.3f"
        			 , csi,densita,Wm(),dp);
        	logger.appendMessage(Mylogger.FINEST,s1); 
  }
        return dp;
    }
    
    
    private double ps;
    @Override
    public double getPs() {
		return ps;
	}
	/**
     * Pressione effettiva : deve essere calcolata in base alla pressione 
     * dei condotti superiori : questa formula Ë da rivedere
     */
    @Override
    public double Peff(double PressStaticSup) {
    	// Ps=p aria estrena - pressione fumo interno
    	// - d_P() deve vincere le perdite di carico
    	if(logger!=null){
    		String message=String.format("\nCalcolo pressione effettiva start: \n\tPressione staica superiore %f", PressStaticSup);
			logger.appendMessage(Mylogger.FINE, message);
    	}
    	ps=Ps(B);
    	Calcola_d_P();
     
    	
        pressEffettiva= ps - d_P+PressStaticSup;
        if(logger!=null){
    		String message=String.format("\nCalcolo pressione effettiva end: \n\tP statica %1.3f \tPerdite carico %1.3f \tPeffettiva=%1.3f", ps,d_P,pressEffettiva);
			logger.appendMessage(Mylogger.FINE, message);
    	}
        return pressEffettiva;
    }
    /**
     * Portata d'aria inerruttore di tiraggio,
     * @return
     */
    double  Ma=0;
    /**
     * perdita int tiraggio
     * perdita apertura
     * area int tiraggio
     * area apertura
     */
    @Override
    @Deprecated
    public double MaIntTir(double csi_i,double csi_d,double Ai,double Ad){
    if(pressEffettiva<0){
    	Ma=M1;
    	if(logger!=null)
    	logger.appendMessage(Mylogger.FINE, String.format("MAINTTIT :Calcolo area tiraggio Pefftiva MINORE DO ZERO =%1.3f Massa fumi=%1.3f"
    			+ " Massa aria=%1.3f", pressEffettiva,M1,Ma));
    return Ma;	
    }
    	double ti=csi_i/(2*aria.MassaVolumica(Patm, temperEst)*Ai*Ai);
    	double td=csi_d/(2*aria.MassaVolumica(Patm, temperEst)*Ad*Ad);
    	double a=pressEffettiva*(ti+td);
    	double b=ti*td*M1*M1;
    	double c=a+b;
    	c= c>0 ?c:1;
    	c=Math.sqrt(c);
    	Ma=(-M1*ti+c)/(ti+td);
    	if(logger!=null)
    	logger.appendMessage(Mylogger.FINE, String.format("MAINTTIT :Calcolo area tiraggio Pefftiva=%1.3f Massa fumi=%1.3f"
    			+ " Massa aria=%1.3f", pressEffettiva,M1,Ma));
       	return Ma;
    }
    public double sommaPerditeLocalizzate(){
    	double csi_sum = 0;
       
        for (int x = 0; x < csi.size(); x++) {
            csi_sum += csi.get(x); // somma perdite concentrate
        }
		return csi_sum;
    }
    
    @Override
    @Deprecated
    public double getMa(){
    	return Ma;
    }

    // Restituisce la pressione ffettiva( gi‡ calcolata in prcedemnza )
	@Override
	public double Peff() {
		return pressEffettiva;
	}
    
    @Override
    public double Wm() {
        return velocitaFluido.getVelocitaMedia();
    }

    @Override
    public double Tm() {
        return temperIntMedia;
    }

    @Override
    public double Tu() {
        return vartempnoncoas.getTfu();
    }

    @Override
    public double ro_m() {
        return velocitaFluido.getMassaVolumicaMedia();
    }

    public void setHaltezza(double Haltezza) {
        this.altezza = Haltezza;
    }

    public void setTubo(Tubo tubo) {
    	 area=tubo.Area_int();
         if(  tubo instanceof TuboC)
         	forma="Circolare";
         else if(tubo instanceof TuboR)
         	forma="Rettangolare";
         else 
          	forma="Indefinita";
         dh=tubo.Dh();
         area=tubo.Area_int();
        this.tubo = tubo;
    }

    public void setPatm(double Patm) {
    	massvolumaria=aria.MassaVolumica(Patm, temperEst);
        this.Patm = Patm;
    }

    public void setCoeffLimEst(double CoefLimE) {
        this.coeffLimEst = CoefLimE;
    }
/**
 * delegated metodo di Tubo
 * @return Resstenza termica  m2/(K W)
 */
    public double Resterm() {
		return tubo.Resterm();
	}
	public double Rug() {
		return tubo.Rug();
	}
	public double Per_est() {
		return tubo.Per_est();
	}
	public double Per_int() {
		return tubo.Per_int();
	}
	public void setM1(double M1) {
        this.M1 = M1;
    }

    public void setTa(double Ta) {
    	massvolumaria=aria.MassaVolumica(Patm, Ta);
        this.temperEst = Ta;
    }

    public void setTfi(double Tfi) {
        this.temperIntIniz = Tfi;
        this.temperIntMedia = Tfi;
    }

    public void setTm(double Tm) {
        this.temperIntMedia = Tm;
    }

    public void setFluidoI(Fluido FluidoI) {
        this.fluidoInt = FluidoI;
    }

    public void setAria(Fluido aria) {
    	massvolumaria=aria.MassaVolumica(Patm, temperEst);
        this.aria = aria;
    }

    public void setSE(double SE) {
        this.SE = SE;
    }

    public void setCsi(List<Double> csi) {
        this.csi = csi;
    }

    public boolean addCsi(Double e) {
        return csi.add(e);
    }

    public VelocitaFluido getVelocitaFluido() {
        return velocitaFluido;
    }

    public VarTempNonCoas getVartempnoncoas() {
        return vartempnoncoas;
    }

    public double getNNusselt() {
        return vartempnoncoas.getNNusselt();
    }
/**
 * 
 * @return Coefficiente globale di scambio termico
 * 		W/(m2 K)
 */
    public double getK() {
        return vartempnoncoas.getK();
    }
// 
    /**
     * 
     * @return Fattore di raffreddamento (adimensionale)
     */
    public double getKR() {
        return vartempnoncoas.getKR();
    }
/**
 * 
 * @return Coefficiente liminare interno W/(m2 K)
 */
    public double getCoefLiminI() {
        return vartempnoncoas.getCoefLiminI();
    }

    public List<Double> getCsi() {
        return csi;
    }

	@Override
	public void setH(double h) {
	if(h>=tubo.Lung()){
		altezza=tubo.Lung();
	}else{
		altezza=h;
	}
		
	}
    @Override
    public double H() {
        return this.altezza;
    }
    @Override
    public double Lung(){
    	return tubo.Lung();
    }
    @Override
    public Fluido fl_est() {
        return aria;
    }

    @Override
    public Condotto condottoi() {
        return this;
    }

    @Override
    public Condotto condottoe() {
        return null;
    }
    @Override
	public double getMassvolumaria() {
		return massvolumaria;
	}
	
    public Tubo getTubo() {
		return tubo;
	}
	@Override
	public double Area() {
		// TODO Auto-generated method stub
		return area;
	}
	@Override
	public String Forma() {
		// TODO Auto-generated method stub
		return forma;
	}
	@Override
	public double Ti() {
		// TODO Auto-generated method stub
		return temperIntIniz;
	}
	@Override
	public double Dh() {
		// TODO Auto-generated method stub
		return this.dh;
	}
	@Override
	public double getWm() {
		// TODO Auto-generated method stub
		return velocitaFluido.getVelocitaMedia();
	}
	

	




}
