package com.antoiovi.unicig.camini;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.antoiovi.unicig.condotti.Condotto;

public class CaminoC {
	private double pcom;
	private StringWriter swr;
	private List<Condotto> condotti;

	public CaminoC(List<Pressioni> tratti, double pcom) {
		super();
	
		this.pcom = pcom;
		swr=new StringWriter();
	}
	public CaminoC(List<Condotto> condotti) {
		super();
		this.condotti=condotti;
		swr=new StringWriter();
	}
	
	public void  calcola(double pcom,int verso){
		String format="%d\tPstatica %1.2f\t Perdite %1.2f\t Peffittive suepriori %1.2f\tPeffettive %1.2f\n";
		int piani=condotti.size();
		for(int x=piani-1;x>=0;x--){
			double psup;
			if(x==piani-1){
				psup=-pcom;
			}else{
				psup=condotti.get(x+1).Peff();
			}
			condotti.get(x).Peff(psup);
			swr.write(String.format("Primario "+format, x,condotti.get(x).Ps(verso),condotti.get(x).d_P(),psup,condotti.get(x).Peff()));
		}
	}

	public void  calcola(Condotto condotto_0,double pcom,int verso){
		String format="%d\tPstatica %1.2f\t Perdite %1.2f\t Peffittive suepriori %1.2f\tPeffettive %1.2f\n";
		int piani=condotti.size();
		for(int x=piani-1;x>=0;x--){
			double psup;
			if(x==piani-1){
				psup=-pcom;
			}else{
				psup=condotti.get(x+1).Peff();
			}
			condotti.get(x).Peff(psup);
			swr.write(String.format("Primario "+format, x,condotti.get(x).Ps(verso),condotti.get(x).d_P(),psup,condotti.get(x).Peff()));
		}
		double psup=condotti.get(0).Peff();
		condotto_0.Peff(psup);
		swr.write(String.format("Primario "+format, -1,condotto_0.Ps(verso),condotto_0.d_P(),psup,condotto_0.Peff()));		
	}
	
public StringWriter getSwr() {
		return swr;
	}

	public void setSwr(StringWriter swr) {
		this.swr = swr;
	}

}
