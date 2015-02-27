package com.antoiovi.unicig.tubi;
/**
 * public interface Tubo {
  public double Lung();// Sviluppo del tubo
  public double Resterm();// Resistenza termica parete
  public double Rug();// Rugosita
  
  // Valori calcolati tipici della forma del tubo , vengono calcolate nelle 
  // implementazioni tipiche della forma
  public double Dh();//Diametro idraulico
  public double Dhe();//Diametro idraulico esterno
  public double Spessore();  
  public double Per_est();  
  public double Per_int();  
  public double Area_int();
  public double Area_est();
 * @author antoiovi
 *
 */
public class TuboCoasBase implements TuboCoass{
Tubo tubo_interno;
Tubo tubo_esterno;

public TuboCoasBase(Tubo tuboi,Tubo tuboe){
		 tubo_interno=new Tubo_base(tuboi.Lung(),tuboi.Resterm(),tuboi.Rug());
		 
		 tubo_interno.setDh(tuboi.Dh());
	     tubo_interno.setDhe(tuboi.Dhe());
	     tubo_interno.setSpessore(tuboi.Spessore());
	     	     
	     tubo_interno.setPer_int(tuboi.Per_int());
	     tubo_interno.setPer_est(tuboi.Per_est());
	     tubo_interno.setArea_int(tuboi.Area_int());
	     tubo_interno.setArea_est(tuboi.Area_est());
	     
	        tubo_esterno=new Tubo_base(tuboe.Lung(),tuboe.Resterm(),tuboe.Rug());
	        // Il tubo esterno rappresenta la ciambella :
	        // Poiche viene utilizzato il perimetro interno per il calcolo dello scambio termico
	        // e il perimetro somma dei perimetri interno ed esterno per il calcolo delle
	        // velocità fluido, calcolo il Diametro idraulico ed il perimetro in base all'area della ciambella, e 
	        // alla somma dei perimetri.
	     
	        //  Perimetro interno= Peerimetro esterno tubo interno
	        tubo_esterno.setPer_int(tuboi.Per_est());
	        // Perimetro esterno a contatto con il fluido
	        tubo_esterno.setPer_est(tuboe.Per_int());
	        // Area superfice della ciambella
	        double area=tuboe.Area_int()-tuboi.Area_est();
	        // dh= 4*Area/(perimetro superficie a contatto col fluido)Diametro idraulico considerandpdo il perimetro totale
	        double dh=   4*area/(tuboi.Per_est()+tuboe.Per_int());
	        tubo_esterno.setDh(dh);
	        tubo_esterno.setArea_int(area);
	        // Diametro idraulico esterno....e area esterna, per scambio con l'esterno..
	        tubo_esterno.setDhe(tuboe.Dhe());
	        tubo_esterno.setArea_est(tuboe.Area_est());
}

	@Override
	public Tubo Tubo_interno() {
		return tubo_interno;
	}

	@Override
	public Tubo Tubo_esterno() {
		return tubo_esterno;	}

	@Override
	public double Lung() {
		return tubo_interno.Lung();
	}
	
}
