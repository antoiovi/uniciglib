package com.antoiovi.unicig.camini;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.antoiovi.unicig.condotti.Condotto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *      Rappresenta una pila di condotti aventi differenti portate di fluido;
 * @author antoiovi
 */
public class Camino {
    List<Condotto> condotti;
    double csi_com;
    double perd_com;
    static Logger logger;
    {
        logger=Logger.getLogger("CAMINO");
     
    }
    /**
     * Calcola la pressione statica alla base di ogni condotto facendo la somma
     * di tutte le pressioni statiche dei condotti sovrastanti, e sottraendo la 
     * perdita causata da csi_com calcolata per l'ultimo condotto
     * @return 
     */
    public List<Double> PressioniStatiche(){
        
        List<Double> Pressioni;
        
        Pressioni = new ArrayList<Double>();
        
       
         double p_0=0;
        
         perd_com=0;
         Condotto condotto_last= condotti.get(condotti.size()-1);
         perd_com=condotto_last.d_P(csi_com);
         for(int x=0;x<condotti.size();x++){
           //  System.out.println("Piano: "+x);
             for(int z=x;z<condotti.size();z++){
             //    System.out.println("\t condotto: "+z);
                 Condotto condotto= condotti.get(z);
                 double ps=condotto.Ps(+1);
                 p_0+=ps;
                // double p=ps-dp;
                //System.out.println("\t\tps= "+ps+"  dp= "+dp+"  p=ps-dp: "+p+"   perd_com= "+perd_com);
            }
             //p_0-=d_p;
             Pressioni.add(p_0);
             p_0=0;
        }
       return Pressioni;
    }
public List<Double> DeltaP(){
        List<Double> deltaP;

        deltaP = new ArrayList<Double>();

         double d_p=0;
         perd_com=0;
         Condotto condotto_last= condotti.get(condotti.size()-1);
         perd_com=condotto_last.d_P(csi_com);
         for(int x=0;x<condotti.size();x++){
           //  System.out.println("Piano: "+x);
             for(int z=x;z<condotti.size();z++){
             //    System.out.println("\t condotto: "+z);
                 Condotto condotto= condotti.get(z);
                 double dp=condotto.d_P();
                d_p+=dp;                
                // double p=ps-dp;
                //System.out.println("\t\tps= "+ps+"  dp= "+dp+"  p=ps-dp: "+p+"   perd_com= "+perd_com);
            }
             //p_0-=d_p;
            deltaP.add(d_p);
             d_p=0;
        }
       return deltaP;
    }
    
    public List<Double> Pressioni(List<Double> Pressioni,List<Double> deltaP){
        logger.setLevel(Level.OFF);   
         Pressioni.clear();
            deltaP.clear();
        
         double p_0=0;
         double d_p=0;
         perd_com=0;
         Condotto condotto_last= condotti.get(condotti.size()-1);
         perd_com=condotto_last.d_P(csi_com);
         for(int x=0;x<condotti.size();x++){
           //  System.out.println("Piano: "+x);
             for(int z=x;z<condotti.size();z++){
             //    System.out.println("\t condotto: "+z);
                 Condotto condotto= condotti.get(z);
                 double ps=condotto.Ps(+1);
                 p_0+=ps;
                 double dp=condotto.d_P();
                d_p+=dp;                
                // double p=ps-dp;
                //System.out.println("\t\tps= "+ps+"  dp= "+dp+"  p=ps-dp: "+p+"   perd_com= "+perd_com);
            }
             //p_0-=d_p;
             Pressioni.add(p_0);
             deltaP.add(d_p);
             p_0=0;
             d_p=0;
        }
       return Pressioni;
    }
    
    public Camino(List<Condotto> condotti, double csi_com) {
        this.condotti = condotti;
        this.csi_com = csi_com;
    }

    public void add(int index, Condotto element) {
        condotti.add(index, element);
    }

    public Condotto remove(int index) {
        return condotti.remove(index);
    }

    public double getPerd_com() {
        return perd_com;
    }
    
    public double altezza(){
                double H=0; 
        for(int x=0;x<condotti.size();x++){
             Condotto c=condotti.get(x);
             H+=c.H();
               }
        return H;
    }
    
}
