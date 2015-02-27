/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.impter;

/**
 * Contiene formule termotecniche e costanti per i calcoli di progetto 
 * termodinamici e fluidodinamici
 * @author antoiovi
 */
public class Formule {
/**
     * TAria private static double TAria=293.15; Utilizzata per per
     * la verifica del punto di rugisada e del tiraggio da:
     * UNICIG 10641 UNICIG 10640
     * @see 
     */
    public static final double TAria=293.15;
    /**
     * KAria private static double KAria=288;
     * Costante R dell'aria J/(kg K)
     * @see 
     */
    public static final double KAria=288;

/**
     * double CapTerMassAria=1004.6;
     * capacità massica termica dell'aria J/(kg K)
     * TODO : da verificare se è correto: ora preso da esempio unicig 
     * uguale a costante
     * @see 
     */
    public static final double CapTerMassAria=1004.6;
/**
     * public static final double PotCalInf=50d;
     * Potere calorifico inferiore
     * TODO : ora preso da esempio unicig 
     *      uguale a costante( solo un combustibile)
     * @see 
     */
    public static final double PotCalInf=50d;
    /**
     * Costante elasticità dei gas : 
     */
    public static final double KFumi=300d;

/**
     * public static final double CapTerMassFumi;
     * Capacità termica massica fumi
     * Utilizzata per calcolo temperature fumi in un condotto
     * @see 
     * /
     */
public static final double CapTerMassFumi=1040;
    
    /** Calcolo massa volumica di un fluido in un condotto;
         *  Espressa in kg al metrocubo;
         *  dipende dalla temperatura del fluido;
         *  serve per calcolare la velocità media;
         * Si utilizza la la legge dei gas perfetti e si approssima la
         pressione media del canale da fumo con quella atmosferica;
     * @param PAtmosferuca
     * @return PAtmosferica/(288*293.15);
         **/
    
    public static final double MassaVolumicaAria(double PAtmosferica){
        return PAtmosferica/(288*293.15);}
    
    /**
     * MassaVolumicaFumi
     * @param Patmosferica
     * @param TFumo
     * @return PAtmosferica/(300*TFumo);
     */
    public static final double MassaVolumicaFumi(double PAtmosferica, double TFumo){
        return PAtmosferica/(300*TFumo);}
    
/**Velocità media di un fluido in un condotto , dal principio di Bernoulli;
 *
 * @param  PortataMassica espressa in kg/s
 * @param      MassaVolumica espressa in kg/m3
 * @param      MassaVolumica X Area= kg/m
 * @return     PortataMassica/(MassaVolumica*AreaSezione)-kg/s diviso kg/m = m/s
 */
    public static final double VelocitaMedia(double PortataMassica,
            double MassaVolumica, double AreaSezione){
        return PortataMassica/(MassaVolumica*AreaSezione);
    }

   /**
     * FORMULA 17 UNI10641
     * @param MassaVolumica
     * @param VelocitaMedia
     * @param Lunghezza
     * @param DiametroIdraulico
     * @param FattoreAttrito
     * @param Pw Variazione di pressione dovuta a variazione di velocità
     * @param csi
     * @return  perdita di carico per resistenza fluidodinamiche
     */
    
   public static final double PerditaCaricoRFluido(double MassaVolumica,
            double VelocitaMedia, double Lunghezza,
            double DiametroIdraulico, double FattoreAttrito, double Pw,double csi
           ){
    double SE=1.2;
    double A=FattoreAttrito*Lunghezza/DiametroIdraulico;
    A=A+csi;
    double B=A*Math.pow(VelocitaMedia,2);
    double C=SE*0.5*MassaVolumica*B;
    return A*B*C+Pw;
     }
    
/**
    *  overload con Velocita 1 e velocita2 invece di Pw
    *  FORMULA 17 UNI 10641
    * 
    * @param MassaVolumica
    * @param VelocitaMedia
    * @param Lunghezza
    * @param DiametroIdraulico
    * @param FattoreAttrito
    * @param csi
    * @param Velocita1
    * @param Velocita2
    * @return  perdita di carico per resistenza fluidodinamiche; viene calcolato 
    * internamente il parametro Pw (pressione dovuta a diff valore di velocità)
    */
    public static final double PerditaCaricoRFluido(
            double MassaVolumica,
            double VelocitaMedia,
            double Lunghezza,
            double DiametroIdraulico,
            double FattoreAttrito,
            double csi,
            double Velocita1,
            double Velocita2
           ){
    double SE=1.2;
    double A=FattoreAttrito*Lunghezza/DiametroIdraulico;
    A=A+csi;
    double B=A*Math.pow(VelocitaMedia,2);
    double C=SE*0.5*MassaVolumica*B;
    // FORMULA 18 O 19
    double Pw=DeltaP(MassaVolumica,VelocitaMedia,Velocita1,Velocita2);
    return A*B*C+Pw;
     }

    
/**
     * FORMULA 18 E 19 UNI 10641
     * Variazione di presione dovuta alla velocità: se velocità 2 è maggiore 
     * di velocià 2 usa formula 19; Utilizaata da Formula 17 
     * @see Formule#PerditaCaricoRFluido
     * @param MassaVolumica
     * @param VelocitaMedia
     * @param Velocita1
     * @param Velocita2
     * @return double Variazione di presione dovuta alla velocità
     */    
    public static final double DeltaP(double MassaVolumica,
    double VelocitaMedia, double Velocita1,double Velocita2){
    double A;
    double B;
    double DP;
    A=1-Math.pow((Velocita1/Velocita2),2);
    B= 0.5*MassaVolumica*Math.pow(VelocitaMedia,2);
    if(Velocita1>Velocita2){
       DP=A*B;}
else if (Velocita2> Velocita1){
    DP=A*B*0.5;
}else
{DP=0;}
System.out.println("**Formule.DeltaP");
System.out.println("  DeltaP = "+DP);
if(DP<0)
        DP=DP*(-1);
System.out.println("  DeltaP = "+DP);
return DP;
    }

/**
     * //Formula 26
     * @param TempAria
     * @param TempFumiI
     * @param KR
     * @return 
     */
    public static final double TempOut(double TempAria,
            double TempFumiI, double KR){
    double A;
    double B;
    B=Math.exp(KR*(-1));
    A=TempFumiI-TempAria;
    return TempAria+(A*B);
    }

/**
     *  Formula 49 Temperatura di parete all'uscita condotto fumi
     * @param TFu temperatura fumi all'uscita del condotto fumi(formule 26 o 28)
     * @param Ta temperatura ambiente
     * @param Kst coefficiente globale di scambio termico ( formule 22 o 23)
     * @param aLim coefficiente liminare (formula 20)
     * @return Temperatura di parete all'uscita condotto fumi
     */
    public static final double TParUsc(double TFu,double Ta,double Kst,double aLim ){
    
double a=Kst/aLim;
double b=(TFu-Ta)*a;
return TFu-b;
    }

/**
    
     * @param iter
     * @param Re
     * @param rug
     * @param Dh
     * @return 
     */
    public static double FattAttRuv(double Re,double rug,double Dh){
    double psi=0;
    double A;
    A=0;
    double B;
    double C;
    double D;
    for(int x=0;x<10;x++){
        B=2.51*A/Re;
        C=rug/(Dh*3.71);
        A=-2*Math.log(B+C);
    }
    D=1/A;
    return Math.pow(D,2);
}

public static double FattAttLisc(double Re,double Dh){
    double psi=0;
    double A;
    A=0.1;
    double B;
    double C;
    double D;
    for(int x=0;x<10;x++){
        B=2.51*A/Re;
        A=-2*Math.log(B);
    }
    D=1/A;
    return Math.pow(D,2);
}
/**
 * Numero di Nusselt : Formula UNICIG 190651
 * @param psi fattore attrito ruvido
 * @param psi0 fattore attrito liscio
 * @param Re numero reynolds
 * @return 
 */
public static double NumeroNusselt(double psi,double psi0, double re){
    
        double psipsi0=psi/psi0;
        double a= Math.pow(psipsi0,0.67 );
        double b=Math.pow(re, 0.75);
        double Nu=a*0.0354*(b-180);
        return Nu;
 }
/**
 * Formula 20 
 * @param CondTer = conducibiltà termica del fluido
 * @param Dh Diametro idraulico
 * @param Nu Mumero di Nusselt
 * @return CoefficenteLiminareInterno; utilizzato per il calcolo del coeff
 *          globale scambio termico
 */
public static double CoeffLamin(double CondTer,double Dh,double Nu){
// TODO VERIFICARE..........
    double x=CondTer*Nu/Dh;
   // if(x<5d)
      //return 5d;
    //else 
        return x;
   
}
/**
 *      Temperatura fumi uscita
 *      Formula 26 -per condotti non coassiali
 * @param Ta
 * @param Tfi
 * @param KR
 * @return 
 */
public static double TemFumiUscita(double Ta,double Tfi,double KR){
        double ekr=Math.exp(-1*KR);
        return Ta+(Tfi-Ta)*ekr;
}


public static double TemFumiMedia(double Ta,double Tfi,double KR){
    /*System.out.print("TemFumiMedia :\n");
    System.out.print("\t Ta="+Ta+"; Tf1+= "+Tfi+"; KR="+KR+"\n");*/
     double ekr=Math.exp(-1*KR);
     //System.out.print("\tdouble ekr=Math.exp(-1*KR)=  "+ekr+"\n");
     double tm= (Tfi-Ta)*(1-ekr)/KR;
     //System.out.print("\ttm=(Tfi-Ta)*(1-ekr)/KR;=  "+tm+"\n");
     tm=Ta+tm;
     //System.out.print("\ttm=   Ta+(Tfi-Ta)*(1-ekr)/KR;=  "+tm+"\n");
     return tm;
}

public static double CoeffGlobScambTerm(double CoeffLimInt,double DiamIdrInt,
        double DiamIdrExt,double KlimEst, double RTerm, double SH){
    double a=1/CoeffLimInt;
     double b=DiamIdrInt/DiamIdrExt;
      double c=1/KlimEst;
     c=c*b;
    c=c+RTerm;
    c=c*SH;
    c=a+c;
    return 1/c;
    } // Coeffici
public static double FattoreRaffreddamento(double U,double k,double L,double M, double cp)
{
double Num;
double Den;
Num=U*k*L;
Den=M*cp;
   return Num/Den;
    
}
}//formule
