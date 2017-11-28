/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třída PrikazMluv implementuje pro hru příkaz mluv, který vrátí proslov 
 * postavy v aktuálním prostoru, po zadání jména postavy.
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazMluv implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "mluv";
    private HerniPlan plan;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor ....
     */
    public PrikazMluv(HerniPlan plan) {
        this.plan = plan;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda udělá to, že postava začne mluvit
     * 
     * @param parametry zadává se jméno postavy, která má mluvit
     * @return fráze, která se vypíše hráči
     */ 
    public String proved(String... parametry) { 
        if (parametry.length == 0) {
            return "Zapoměl jsi jak mluvit? Zadej jméno postavy!";
        }
        String jmeno = parametry[0];
        Prostor aktualniProstor = plan.getAktualniProstor();
        Postava postava = aktualniProstor.najdiPostavu(jmeno);
        if (postava == null) {
            return "Asi už máš halucinace, " + jmeno + " tady není.";
        } else {
            return postava.getProslov();
        }
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
        plan.notifyObservers();
    }

    //== Soukromé metody (instancí i třídy) ========================================
}
