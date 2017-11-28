/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 * Třída PrikazProzkoumej implementuje pro hru příkaz prozkoumej, který 
 * prozkoumá zvolenou věc v aktuálním
 * prostoru.
 * 
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazProzkoumej implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "prozkoumej";
    private HerniPlan hPlan;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor.
     */
    public PrikazProzkoumej(HerniPlan hPlan) {
        this.hPlan = hPlan;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda prozkoumá zadanou věc.
     * 
     *  @param parametry počet parametrů =1, zadává se název věci
     *  @return fráze, která se vypíše hráči
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím, co mám prozkoumat!";
        }
        
        if (parametry.length > 1) {
            return "Nemůžu prozkoumat více věcí najednou!";
        }
        
        String nazevVeci = parametry[0];
        
        Vec vec = hPlan.getAktualniProstor().odeberVec(nazevVeci);
        if (vec == null) {
            return nazevVeci + " tu není!";
        }
        
        hPlan.getAktualniProstor().vlozVec(vec);
        
        return nazevVeci + ": " + vec.getPopis();
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
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
