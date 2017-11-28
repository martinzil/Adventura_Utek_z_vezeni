/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Trida PrikazKapsa implementuje pro hru příkaz kapsa, který vypíše věci v 
 * kapse. 
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazKapsa implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "kapsa";
    private Kapsa kapsa;
    private HerniPlan hPlan;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor.
     */
    public PrikazKapsa(Kapsa kapsa) {
        this.kapsa = kapsa;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vraci seznam věcí, kterě jsou v kosiku 
     * 
     * @param parametry žádné
     * @return obsah kosiku
     */
    public String proved(String... parametry) {
        return kapsa.obsahKapsy();    
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }
    
    @Override
    public void updateHerniPlan() {
        hPlan.notifyObservers();
    }
    //== Soukromé metody (instancí i třídy) ========================================
}
