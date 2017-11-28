/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třída PrikazZnic implementuje pro hru příkaz znič, který zničí zadanou věc, 
 * pokud je to možné.
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazZnic implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "znic";
    private HerniPlan hPlan;
    private Kapsa kapsa;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor.
     */
    public PrikazZnic(HerniPlan hPlan, Kapsa kapsa) {
        this.hPlan = hPlan;
        this.kapsa = kapsa;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda zničí danou věc, pokud to jde
     * 
     *@param parametr - název věci co zničit
     *@return fráze, která se vypíše hráči
     */ 
    public String proved(String... parametry) {   
        if (parametry.length == 0) {
            return "Nevím co chceš zničit.";
        }
        
        Prostor aktualniProstor = hPlan.getAktualniProstor();
        Prostor sousedniProstor = hPlan.getAktualniProstor().vratSousedniProstor("tunel");
        
        String nazev = parametry[0];
        Vec vec = hPlan.getAktualniProstor().odeberVec(nazev);
         
        if (vec == null) {
            return nazev + " tu není.";
        }
               
        if (vec.isNeznicitelna()) {
            aktualniProstor.vlozVec(vec);
            return nazev + " nejde zničit.";
        }
        
        if (!hPlan.isKouri()) {
            aktualniProstor.vlozVec(vec);
            return "Proč bys ničil " + nazev + "?";
        }
        
        if (!hPlan.isZapaluje()) {
            aktualniProstor.vlozVec(vec);
            return "Proč bys ničil " + nazev + "?";
        }
        
        if (kapsa.obsahKapsy().contains("nuz") || kapsa.obsahKapsy().contains("ziletka")) {
            sousedniProstor.budeVidet(true);
            updateHerniPlan();
            return "Zničil jsi " + nazev + " a objevil jsi vchod do tunelu!\n"+
                    aktualniProstor.dlouhyPopis();
        }
        
        aktualniProstor.vlozVec(vec);
        return "Holýma rukama " + nazev + " nezničíš.";
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
