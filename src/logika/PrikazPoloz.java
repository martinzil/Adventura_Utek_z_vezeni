/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třída PrikazPoloz implementuje pro hru příkaz polož, který vkládá 
 * přenositelné věci z kapsy do prostoru.
 * 
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazPoloz implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV ="poloz";
    private HerniPlan hPlan;
    private Kapsa kapsa;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor.
     */
    public PrikazPoloz(HerniPlan hPlan, Kapsa kapsa) {
        this.hPlan = hPlan;
        this.kapsa = kapsa;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda se pokusí zadanou věc odebrat z kapsy.
     * @param parametry-1, název věci
     * @return text
     */     

    public String proved(String... parametry) {
        if (parametry.length == 0) {
            return "Nevím co chceš vyndat.";
        }

        String nazev = parametry[0];
        Vec vec = kapsa.vyndejVec(nazev);
        if (vec == null) {
            return "Asi ti to vypadlo, v kapse jsem to nemáš.";
        }
        else {
            hPlan.getAktualniProstor().vlozVec(vec);
            return "Vytáhl jsi z kapsy " + nazev;
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
        hPlan.notifyObservers();
    }
    //== Soukromé metody (instancí i třídy) ========================================
}
