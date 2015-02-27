/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antoiovi
 */
public class OssigenoO2 extends AbstractFluido{
private final double massamolare=31.999;
private static OssigenoO2 instance=null;

    protected OssigenoO2() {
       init("ossigeno");
       a=25.48;
       b=1.520e-2;
       c=-0.7155e-5;
       d=1.312e-9;
    // moltiplicatore per valori colonna presi da tabella
   	expDiffTerm=1e-5;
   	expViscDin=1e-5;
	// non si utilizza se visc cinematica la calcolo invece che prenderla dalla tabella
   	expViscCin=10e-5;
    }
    
     public static OssigenoO2 getInstance() {
        if(instance==null){
            instance=new OssigenoO2();
        }
        return instance;
    }
   
    @Override
 public double MassaMolare() {
    return massamolare;
    }
 

    @Override
    public String toString() {
        return "O2"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
