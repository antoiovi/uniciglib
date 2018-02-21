/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.UNI_EN_13384_1;

/**
 *
 * @author antoiovi
 */
public class TabellaB5 {
    
   Materiali MaterialeCamino;
   
   private static final double[][] Dati ={
       {2800.00, 0.88,  0.00, 160.00} ,
       {7800.00, 0.45, 10.00, 50.00} ,
       {7900.00, 0.46, 10.00, 17.00} ,
       //Muratura clinker
       {1800.00, 1.00, 10.00, 0.55} ,
       {2000.00, 0.00,  0.00, 0.64} ,
       {2200.00, 0.00,  0.00, 0.74} 
       
   };
   
   public static final double MassaVolumica(Materiali mater){
       return Dati[mater.indice()][0];
       }

   public static final double ConduttivitaTermica(Materiali mater)
    throws MATER_EXC_01{
 switch(mater){
       case ALLUMINIO:
       case ACCIAIO:
       return Dati[mater.indice()][3];
     case CLINKER:
         throw new MATER_EXC_01("Richiesto valore massa volumica");
         }
 return 0;
   }


public static final double ConduttivitaTermica(Materiali mater, double MassaVolumica)  throws MATER_EXC_01
{
    {
switch(mater){
      case ALLUMINIO:
      case ACCIAIO:
       return Dati[mater.indice()][3];
      case CLINKER:
         switch ((int)MassaVolumica){
             case 1800 :
                 return 0.55;
             case 2000 :
                 return 0.65;
             case 2200 :
                 return 0.74;
             default :
                 throw new MATER_EXC_01("Richiesto valore massa volumica");
         }

}

    }

return 0;}
}


 




