

Realizzato con https://pandao.github.io/editor.md/en.html

## Descrizione
Libreria di routines in java percalcoli termodinamici e fluidodinamici.
Vi sono diversi package 
#### com.antoiovi.unicig.tubi
Classe astratta Tubo che incapsula proprietà 5 proprietà : lunghezza. altezza, resistenzatermica, rugosita, spessore;  Ha due implementazione TuboC(circolare) TuboR(rettangolare); ed in base a queste implementazioni vengono calcoltai area, diametro idraulico, ecc;
####com.antoiovi.unicig.fluidi
Implementazione di classi per calcolare le proprietà delle sostanze; Vi sono implementate le proprietà delle seguenti sostanze allo stato gassoso :N2, CO2, O2 ,H2O.
Vengono forniiti metodi e classi per creare miscele;
Vengono calcolate le principali propieta dei gas : Costente R, massa volumica, capacita termica, viscosita dinamica diffusivita termica; 
Vengono implementati i fluidi prodotti della combustione dei principali combustibili (metodo norma UNI EN  13384-1)

####com.antoiovi.unicig.condotti

Nelle tre classi di tipo condotto vengono calcolate , in base al fluido e alle dimensioni (tubo), i parametri tipici dei calcoli fuidodinamici, e trmodinamici : numero di raynold, numero di Nusselt, fattore di attrito, perdite di carico ; inoltre , in base alla classe , viene considerato come scambiatore e vengono calcolate tempèerature iniziali, medie e di uscita;


####com.antoiovi.unicig.impianti
Classe Caldaia:  classe wrapper di  Combustibile, che inizzializza un combustibile UNI EN 143384-1 in base ai parametri del generatore di calore;

#####com.antoiovi.unicig.Formule.class
Formule e costanti ;


## Demo
I file demo sono nel package demo (src\main\java\demo) ;
Sono programmi che mostarno l'utilizzo delle varie classi e mostrato i dati di calcolo;
   <build>     <plugins>   <plugin>  <configuration> <excludes>
		
  ```xml
 <build>
          ....
     <plugins>
	....
       <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
		<configuration>
			...
			<excludes>
				.....
				<exclude>**/demo/*.*</exclude>
				<exclude>**/demo</exclude>
			</excludes>
		</configuration>
	 </plugin> 
	 ...
	</plugins>
```   

###Eseguire i demo
Per eseguire i demo è sufficiente compilare senza fara il package;

`$ mvn compile`
`$ cd target\classes\`
`$ java demo.Nomefiledemo`

####FluidiDemo
`$ java demo.FluidiDemo`
Permette di visualizzare le proprietà delle implementazoni delle classi fluido;
Scegliendo un fluido viene mostrata una tabella con le proprietà calcolate per varie temperature;
Selezionando una miscela viene richiesto l'inserimento della quantità e della sostanza, viene creata una miscela e vengono mostrate le prorpietà;

####CondottiDemo
`target\classes$ java demo.CondottiDemo`
Mostra l'utilizzo delle classi condotto ;
##### Calcolo prevalenza
> Utilizzo della classe com.antoiovi.unicig.CondottoFL, per calcolare la prevalenza di una pompa , utilizzando aria o acqua;
