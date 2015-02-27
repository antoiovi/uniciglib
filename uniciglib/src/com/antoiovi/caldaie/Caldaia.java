package com.antoiovi.caldaie;

import it.iovino.fluidi.Fluido;

public interface Caldaia {
public double getPmax();
public double getPmin();
	
public double getRend_Pmax();
public double getRend_Pmin();
	
public Fluido getFumoPmax();
public Fluido getFumoPmin();

public double getTempFumiPmax();
public double getTempFumiPmin();

public double getPortatfumiPmax();
public double getPortatfumiPmin();

public double getCO2percentPmax();
public double getCO2percentPmin();
	
public double getEccariaPmax();
public double getEccariaPmin();
}
