/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.UNI_10641.Tabelle;

import java.beans.*;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;
/**
 *
 * @author antoiovi
 */
public class prospetto1 {
public static  HashMap<String,Integer> dati;
public static final Object[][] Dati={
        {"Torino",-8d },
        {"Alessandria",-8d },
        {"Asti",-8d },
        {"Cuneo",-10d },
        {"Alta Valle Cuneese",-15d },
        {"Novara",-5d },
        {"Vercelli",-7d },
        {"Aosta",-10d },
        {"Valle D'Aosta",-15d },
        {"Alta Valle D'Aosta",-20d },
        {"Genova",-0d },
        {"Imperia",-0d },
        {"La Spezia",-0d },
        {"Savona",-0d },
        {"Bergamo",-8d }
};


    public prospetto1() {
        dati=new HashMap();
        for(int x=0;x<Dati.length;x++){
        dati.put((String)Dati[x][0],Integer.parseInt(Dati[x][1].toString()));
        }
      }
    
   public Vector<String> getCitta(){
   Vector<String> V=new Vector();
   for(int x=0;x<Dati.length;x++){
    V.add((String)Dati[x][0]);
       
   }
   return V;
    }

public int getTempMin(String Citta){
return dati.get(Citta);
   }
}
