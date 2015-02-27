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
public final class ArgonAr extends AbstractFluido{
private final double massamolare=28.013;
private static ArgonAr instance=null;
   
    private ArgonAr() {
        
    }

    public static ArgonAr getInstance() {
        if(instance==null){
            instance=new ArgonAr();
        }
        return instance;
    }
    
    
   
    @Override
 public double MassaMolare() {
    return massamolare;
    }

    @Override
    public String toString() {
        return "Ar"; //To change body of generated methods, choose Tools | Templates.
    }

}
