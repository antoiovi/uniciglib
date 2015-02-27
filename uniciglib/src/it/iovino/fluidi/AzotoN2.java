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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antoiovi
 */
public final class AzotoN2 extends AbstractFluido{
private final double massamolare=28.013;
private static AzotoN2 instance=null;

    private AzotoN2() {
// coefficienti del polinomio di Taylor per calore specifico (non usati se uso tabella)
    	a=28.9;
    	b=-0.1571*10e-2;;
    	c=0.8081*10e-5;
    	d=-2.873*10e-9;
   // moltiplicatore per valori colonna presi da tabella
    	expDiffTerm=1e-5;
    	expViscDin=1e-5;
 	// non si utilizza se visc cinematica la calcolo invece che prenderla dalla tabella
    	expViscCin=1e-5;
       init("azoto");
    }

    public static AzotoN2 getInstance() {
        if(instance==null){
            instance=new AzotoN2();
        }
        return instance;
    }
    
    
   
    @Override
 public double MassaMolare() {
    return massamolare;
    }
   

    @Override
    public String toString() {
        return "N2"; //To change body of generated methods, choose Tools | Templates.
    }

    

    

    
}
