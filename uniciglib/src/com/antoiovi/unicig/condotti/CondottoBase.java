/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antoiovi.unicig.condotti;

import com.antoiovi.unicig.tubi.Tubo;

import it.iovino.fluidi.Aria_base;
import it.iovino.fluidi.FluidiMath;
import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.PairMassaFluido;

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
 */
public class CondottoBase implements Condotto {

    double Haltezza;// tubo orizzontale Haltezza=0
    Tubo tubo;
    protected double Patm;

    protected double CoefLimE;
    protected double M1;// Portata massica fluido interno
    protected double Ta;//Temperatura ambiente
    protected double Tfi;//Temperatura iniziale
    protected double Tm;//Temperatura media
    protected Fluido FluidoI;
    // Aria esterna
    protected Fluido aria;

    double SE = 1.2;
    // Verso di percorrenza per il calcolo delle pressioni statiche : +1 slaita, -1 discesa
    // puÚ essere modificato quando si calcola la Pressione statica
    protected double B=1.0;
    // La calcola
    double Peffettiva=0;
    
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
    static Logger logger;

    {
        logger = Logger.getLogger("uncig_logger");
        logger.setLevel(Level.OFF);

    }
/**
 * Utilizzo : 
 * 	1-creare il condotto  con i parametri del costruttore
 * 2- chiamare CalcolaTemper()
 * 3 - Calcolre Pstatica(int B)
 * 4- Calcolare peeffettiva con parametro Pstatica condotti superiori
 * 5- Reset: ricalcolo pettendo temperatura iniziale=temperatura media
 * 6- Usando Somma(c1,c2) metto nel condotto la somma dei fluidi di c1 e c2 , e ricalcolo la temperatura
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
        this.CoefLimE = CoefLimE;
        this.M1 = M1;
        this.Ta = Ta;
        this.Tfi = Tfi;
        this.FluidoI = FluidoI;
        // Tubo verticale
        Haltezza=tubo.Lung();
        velocitaFluido = new VelocitaFluido();
        vartempnoncoas = new VarTempNonCoas();
        Tm = Tfi;
        CalcolaTemper();
        
    }
    /**
     * Costruttore senza impostare i dati del fluido: per i calcoli bisogna 
     * 			prima chiamare setData(......)
     * @param tubo
     * @param CoefLimE
     */
    public CondottoBase(Tubo tubo,  double CoefLimE,double Halt) {
    	this.Haltezza=Halt;
        this.tubo = tubo;
        this.CoefLimE = CoefLimE;
        // Tubo verticale
        Haltezza=tubo.Lung();
        velocitaFluido = new VelocitaFluido();
        vartempnoncoas = new VarTempNonCoas();
        Tm = Tfi;
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
        this.Ta = Ta;
        this.Tfi = Tfi;
        this.FluidoI = FluidoI;
        // Tubo verticale
        Haltezza=tubo.Lung();
        velocitaFluido = new VelocitaFluido();
        vartempnoncoas = new VarTempNonCoas();
        Tm = Tfi;
        CalcolaTemper();
    }

    public CondottoBase() {
        velocitaFluido = new VelocitaFluido();
        vartempnoncoas = new VarTempNonCoas();

    }

    public void CalcolaTemper() {
        // primo ciclo metto Tm=Ti
        velocitaFluido.Calcola(Patm, M1, Tm, FluidoI, tubo);
        vartempnoncoas.Calcola(tubo, M1, FluidoI, CoefLimE, velocitaFluido, Ta, Tfi);
        Tm = vartempnoncoas.getTfm();
    }

   /* public double getTm() {
        return Tm;
    }

    public double getTfu() {
        return vartempnoncoas.getTfu();
    }*/

    @Override
    public Fluido fl_int() {
        return this.FluidoI;
    }

    /**
     * Ricalcola la temperatura reimpostando la temperatura media uguale alla 
     * temperatura iniziale
     */
    public void Reset() {
        Tm = Tfi;
        velocitaFluido.Calcola(Patm, M1, Tm, FluidoI, tubo);
        vartempnoncoas.Calcola(tubo, M1, FluidoI, CoefLimE, velocitaFluido, Ta, Tfi);
        Tm = vartempnoncoas.getTfm();
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
            this.Tfi = Tf2;
            Tm = Tfi;
            FluidoI=c2.fl_int();
            return;
        } else if (Mf2 == 0) {
            this.Tfi = Tf1;
            Tm = Tfi;
            FluidoI=c1.fl_int();
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
        this.Tfi = tfu;
        Tm = Tfi;
        // Fluido uguale alla miscela
        FluidoI=FluidoU;;
        return;
    }
    /**
     * Modifico il fluido interno 
     */
    @Override
	public void setFluidoI(Fluido fluidoi, double m2, double T2) {
        this.FluidoI = fluidoi;
        Tm = T2;
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
    	double Mtot =M1+m2;
        // se masse condotti vuote allora non fare nulla
        if(m2==0)
            return;
        double Tf1, Tf2, Mf1, Mf2;
        double Tfu0;
        Tf1 =this.Tm;
        double cp1 = FluidoI.CapTerm(Tm);
        double cp2 = f2.CapTerm(T2);
        // Creo la miscela di uscita
        PairMassaFluido mf_1,mf_2;
        mf_1=new PairMassaFluido(M1,FluidoI);
        mf_2=new PairMassaFluido(m2,f2);
        List<PairMassaFluido> listmassefluido=new ArrayList();
        listmassefluido.add(mf_1);
        listmassefluido.add(mf_2);
        Fluido FluidoU=new Miscela(listmassefluido);
        
         /*Primpo tentativo : Temperatura dopo confluenza = media Ponderata*/
        Tfu0 = (Tm * M1) + (T2 * m2);
        Tfu0 = Tfu0 / (M1 + m2);
        double cpU =FluidoU.CapTerm(Tfu0);
        double tfu = 0;
        do {
        	tfu = (M1 * cp1 * Tf1) + (m2 * cp2 * T2);
            tfu = tfu / (Mtot * cpU);
            double dT = tfu - Tfu0;
            double modulo = dT>0? dT: -dT;
            if (modulo < 0.5) {
                break;
            }
            Tfu0 = tfu;
            cpU =FluidoU.CapTerm(Tfu0);
            M1=Mtot;
        } while (true);
        this.Tfi = tfu;
        Tm = Tfi;
        // Fluido uguale alla miscela
        FluidoI=FluidoU;;
        return;
    }

    @Override
    public void Serie(Condotto c1) {
        M1 = c1.M1();
        Tfi = c1.Tu();
        Tm = Tfi;
        FluidoI=c1.fl_int();
        this.CalcolaTemper();
    }

    public double getM1() {
        return M1;
    }

    public Fluido getFluidoI() {
        return FluidoI;
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
        double ps = (aria.MassaVolumica(Patm, Ta) - velocitaFluido.getMassaVolumicaMedia()) * Haltezza * 9.81 * B;
      // System.out.println("delta Aria= "+aria.MassaVolumica(Patm,Ta)+"deltaGAs= "+velocitaFluido.getMassaVolumicaMedia()
        //+"  Patm "+Patm+"  Ta "+Ta+"Haltezza= "+Haltezza+"   ps="+ps);
        return ps;//(aria.MassaVolumica(Patm,Ta)-velocitaFluido.getMassaVolumicaMedia())*Haltezza*9.81*B;
    }
    
    /**
     *
     * @return perdita di carico come somma di perdite distribuite e perdite
     * localizzate
     */
    @Override
    public double d_P() {

        double densita = velocitaFluido.getMassaVolumicaMedia();
        double csi_sum = 0;
        double p_conc;
        for (int x = 0; x < csi.size(); x++) {
            csi_sum += csi.get(x); // somma perdite concentrate
        }
        p_conc = d_P(csi_sum);
        double f_attr = velocitaFluido.getFattAttrRuvido();
        double csi_dist = f_attr * tubo.Lung() / tubo.Dh();
        double p_dist = d_P(csi_dist);
        double d_p = SE * (p_conc + p_dist);
        //String s="Densita= "+densita+" W= "+Wm()+"Lung= "+tubo.Lung()+" Dh= "+tubo.Dh()+" fattr= "+f_attr+" csi= "+csi_sum;
        //System.out.println(s);
        return d_p;

    }
    /**
     *Calcola  la caduta di pressione data la perdita localizzata  
     * @param csi
     * @return
     */
    @Override
    public double d_P(double csi) {
        double dp = 0.5 * ro_m() * Math.pow(Wm(), 2) * csi;
        return dp;
    }
    
    /**
     * Pressione effettiva : deve essere calcolata in base alla pressione 
     * dei condotti superiori : questa formula Ë da rivedere
     */
    @Override
    public double Peff(double PressStaticSup) {
        Peffettiva= Ps(B) - d_P()+PressStaticSup;
        return Peffettiva;
    }
    /**
     * Portata d'aria inerruttore di tiraggio,
     * @return
     */
    double  Ma=0;
    @Override
    public double MaIntTir(double csi_i,double csi_d,double Ai,double Ad){
    	double ti=csi_i/(2*aria.MassaVolumica(Patm, Ta)*Ai*Ai);
    	double td=csi_d/(2*aria.MassaVolumica(Patm, Ta)*Ad*Ad);
    	double a=Peffettiva*(ti+td);
    	double b=ti*td*M1*M1;
    	double c=a+b;
    	c= c>0 ?c:1;
    	c=Math.sqrt(c);
    	Ma=(-M1*ti+c)/(ti+td);
       	return Ma;
    }
    @Override
    public double getMa(){
    	return Ma;
    }

    // Restituisce la pressione ffettiva( gi‡ calcolata in prcedemnza )
	@Override
	public double Peff() {
		return Peffettiva;
	}
    
    @Override
    public double Wm() {
        return velocitaFluido.getVelocitaMedia();
    }

    @Override
    public double Tm() {
        return Tm;
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
        this.Haltezza = Haltezza;
    }

    public void setTubo(Tubo tubo) {
        this.tubo = tubo;
    }

    public void setPatm(double Patm) {
        this.Patm = Patm;
    }

    public void setCoefLimE(double CoefLimE) {
        this.CoefLimE = CoefLimE;
    }

    public void setM1(double M1) {
        this.M1 = M1;
    }

    public void setTa(double Ta) {
        this.Ta = Ta;
    }

    public void setTfi(double Tfi) {
        this.Tfi = Tfi;
        this.Tm = Tfi;
    }

    public void setTm(double Tm) {
        this.Tm = Tm;
    }

    public void setFluidoI(Fluido FluidoI) {
        this.FluidoI = FluidoI;
    }

    public void setAria(Fluido aria) {
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
// Coefficiente globale di scambio termico
    public double getK() {
        return vartempnoncoas.getK();
    }
// Fattore di raffreddamento
    public double getKR() {
        return vartempnoncoas.getKR();
    }

    public double getCoefLiminI() {
        return vartempnoncoas.getCoefLiminI();
    }

    public List<Double> getCsi() {
        return csi;
    }

	@Override
	public void setH(double h) {
	if(h>=tubo.Lung()){
		Haltezza=tubo.Lung();
	}else{
		Haltezza=h;
	}
		
	}
    @Override
    public double H() {
        return this.Haltezza;
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

	




}
