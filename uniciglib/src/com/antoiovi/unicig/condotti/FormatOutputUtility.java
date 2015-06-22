package com.antoiovi.unicig.condotti;

import com.antoiovi.unicig.tubi.Tubo;
import com.antoiovi.unicig.tubi.TuboC;
import com.antoiovi.unicig.tubi.TuboQ;
import com.antoiovi.unicig.tubi.TuboR;

public class FormatOutputUtility {



public static final String PrintInputData(CondottoBase cond){
//	Condotto cond=condotto;
	Tubo tubo=cond.getTubo();
	StringBuilder strbuild=new StringBuilder();
	tubo.Resterm();
	tubo.Rug();
	strbuild.append("\n"+PrintInputData(tubo));
	
	
	return strbuild.toString();
}

public static final String PrintInputData(Tubo tubo){
	StringBuilder strbuild=new StringBuilder();
	String s="";
	if(tubo instanceof TuboC ){
		TuboC t=(TuboC) tubo;
		strbuild.append(String.format("\t Sezione circolare , lunghezza %1.2f diametro %1.1f area %1.1f ", t.Lung(),t.getDiam_int(),t.Area_int()))
		.append(String.format("\n\tdiametro idraulico %1.2f perimetro %1.2f spessore %1.3f", t.Dh(),t.Per_int(),t.Spessore()));
	}else if(tubo instanceof TuboQ){
		TuboQ t=(TuboQ) tubo;
		strbuild.append(String.format("\tSezione quadrata   , lunghezza %1.2f \tlato %1.1f \t area %1.1f ", t.Lung(),t.getLato(),t.Area_int()))
				.append(String.format("\n\tdiametro idraulico %1.2f perimetro %1.2f spessore %1.3f", t.Dh(),t.Per_int(),t.Spessore()));
	}else if(tubo instanceof TuboR){
		TuboR t=(TuboR) tubo;
		strbuild.append(String.format("\tSezione rettangolare, lunghezza %1.2f \t lato A %1.3f \t lato B %1.3f \tarea %1.1f ", t.Lung(),t.getLato_lung(),t.getLato_cort(),t.Area_int()))
		.append(String.format("\n\tdiametro idraulico %1.2f perimetro %1.2f spessore %1.3f", t.Dh(),t.Per_int(),t.Spessore()));
	}else{
		s="";
	}
	strbuild.append(String.format("\n\t resistenza termica parete %1.3f rugosità %1.5f ",tubo.Resterm(),tubo.Rug()));
	s=strbuild.toString();
//	s=s+String.format("\n\tspessore %1.2f perimetro %1.2f resistenza termica %1.3f rugosità %1.4f",tubo.Spessore(),tubo.Per_int(),tubo.Resterm(),tubo.Rug());
	return s;
}


public static final String printTemperData(Condotto cond){
//	Condotto cond=condotto;
 
	return String.format("Portata massica %1.3f [kg/s] temperatura iniziale %1.1f [K] temperatura media %1.1f [K] temperatura uscita %1.1f [K]",cond.M1(),cond.Ti(),cond.Tm(),cond.Tu());
	
}
public static String SPEEDDATALABEL=new StringBuilder()
			.append("fattore attrito liscio\tfattore attrito ruvido\tmassa volumica\tvelocit media  [m/s] \n")
			.append("\t  liscio       \t   ruvido      \t      [kg/m3] \t    [m/s]    ").toString();
public static final String printSpeedData(CondottoBase cond){
//	Condotto cond=condotto;
 VelocitaFluido vf=cond.getVelocitaFluido();
	StringBuilder strbuild=new StringBuilder();
	strbuild.append(String.format("%1.3f\t%1.3f\t%1.3f\t%1.3f", vf.getFattAttrLiscio(),vf.getFattAttrRuvido(),vf.getMassaVolumicaMedia(),
			vf.getNre(),vf.getVelocitaMedia()));
	return strbuild.toString();
}

}
