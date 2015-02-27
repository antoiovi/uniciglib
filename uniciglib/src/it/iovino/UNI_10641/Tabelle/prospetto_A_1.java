/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.UNI_10641.Tabelle;
import it.iovino.utilita.interPolar;
/** Prospetto a.1 Norma UNI 10641, pagina 162
 * Consente di ottenere il coefficiente di perdita localizzata in un innesto
 * tra due tubiin una convergenza a T rotonda; dipende dal rapporto 
 * tra la portata fluido del tubo entrante e la portata del tubo ricevente
 * 
 *
 * @author antoiovi
 */
public class prospetto_A_1 {
    //public static final double
   
public static final double[] QbQc={  0.0d,0.1d,  0.2d,  0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d,  1d};
public static final double[] csi={  0.0d,0.16d,0.27d, 0.38d,0.46d,0.53d,0.57d,0.59d,0.60d,0.59d,0.55d};
/**
 * Restituisce il coefficiente di perdita localizzata 
 * deve essere Qb(immettente) minore o uguale di QC(ricevente) quindi il
 * rapporto Qb/Qc < 1 viene usato per ricavare csi
 * @param Qb Portata canale da fumo
 * @param Qc Portata condotto fumario
 * @return 
 */

public static double getCsi(double Qb,double Qc){
double a=Qb/Qc;
return interPolar.interPolarLin(a,QbQc,csi);
}



}