/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třída PrikazVloz implementuje pro hru příkaz nasyp, který spojí dvě věci a 
 * vytvoří novou věc.
 * 
 * Tato třída je součástí jednoduché textové hry.
 * 
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazNasyp implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "nasyp";
    private HerniPlan hPlan;
    private Kapsa kapsa;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor.
     */
    public PrikazNasyp(HerniPlan hPlan, Kapsa kapsa) {
        this.hPlan = hPlan;
        this.kapsa = kapsa;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    public String proved(String... parametry){     
        if (parametry.length == 0) {
            return "Nevím co chceš nasypat.";
        }
        
        Prostor aktualniProstor = hPlan.getAktualniProstor();
        String nazev = parametry[0];
        Vec vec = kapsa.getVec(nazev);
        
        if (vec == null) {
            return nazev + " v kapse nemáš.";
        }
        
        if (!vec.isSpojitelna()) {
            return nazev + " nemůžeš nasypat.";
        }
        
        Vec silnaKava = new Vec("silnaKava", "Exkluziivně pro hlídače.", false, true, false, false);
        
        if (aktualniProstor.getNazev().equals("jidelna") && kapsa.obsahKapsy().contains("neospan")) {
            kapsa.vyndejVec(nazev);
            aktualniProstor.odeberVec("kava");
            aktualniProstor.vlozVec(silnaKava);
            hPlan.usnul=true;
            updateHerniPlan();
            return "Vytvořil jsi 'silnou' kávu.\n"+
                    hPlan.getAktualniProstor().dlouhyPopis();
        }
        
        return "Asi ti to vypadlo, v kapse to nemáš.";        
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
