/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třídy Postava - popisuje postavy vyskytující se ve hře.
 * 
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class Postava {
    //== Datové atributy (statické i instancí)======================================
    private String jmeno;
    private String proslov;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor nastaví jméno a proslov postavy.
     */
    public Postava(String jmeno, String proslov) {
        this.jmeno = jmeno;
        this.proslov = proslov;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací jméno postavy.
     * 
     * @return   String jméno postavy.
     */
    public String getJméno() {
        return jmeno; 
    }
    
    /**
     * Metoda vrací to, co postavy říkají.
     * 
     * @return  String proslov.
     */
    public String getProslov() {
        return proslov;
    }
       
    //== Soukromé metody (instancí i třídy) ========================================
}
