/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.iovino.impter;

/**
 *
 * @author antoiovi
 */
public class StandardData {

    public StandardData() {
    }
    /**
     * Diametro interno dei condotti fumari - dimensioni standard
     * 16/12/11 - Da definire la fonte (EN 1856 ?)
     */
    public static final String[] DiamStd = { "80", "100", "200", "300", "400",
      "500", "600" };
    /**
     * Diametro interno dei canali da fumo caldaie tipo C- dimensioni standard
     * 26/12/11 - Da definire la fonte (EN 1856/2 ?) attualmente valori presi da 
     *              cataloghi costruttori
     * 
     */
   public static final String[] DiamStdCanaliFumoC ={ "60","63","80","100","120"};
    
   /**
     * SPESSORE - dimensioni standard
     * 16/12/11 - Da definire la fonte (EN 1856 ?) 
     */
    public static final String[] SpessStd = { "1", "2", "3", "3", "4",
      "5", "6","7","8","9","10","50" };
/**
*   Rugosità standard in mm : rif. UNI 1338401-2, propspetto B4
*/
    public static final String[] RugosStd = { "0.001", "0.0015", "0.002", "0.003", 
                        "0.004","0.005" };
    
}
