/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třída PrikazVezmi implementuje pro hru příkaz vezmi, který vkládá 
 * přenositelné věci do kapsy.
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazVezmi implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "vezmi";
    private HerniPlan hPlan;
    private Kapsa kapsa;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor.
     */
    public PrikazVezmi(HerniPlan hPlan, Kapsa kapsa) {
        this.hPlan = hPlan;
        this.kapsa = kapsa;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     *  Metoda pro provedení příkazu ve hře. Metoda se pokusí přidat věc do kapsy. 
     *  
     *  @param parametry počet parametrů =1, zadává se název věci
     *  @return fráze, která se vypíše hráči
     */
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (věc, kterou sebrat), tak ....
            return "Nevím, co mám sebrat";
        }
        
        String nazev = parametry[0];
        Vec vec = hPlan.getAktualniProstor().odeberVec(nazev);
        
        if (vec == null) {
            return nazev + " tady není!";
        }
        //pokud věc není přenositelná, vypíše...
        if (vec.isPrenositelna() == false) {
            hPlan.getAktualniProstor().vlozVec(vec);
            return nazev + " určitě neuneseš.";
        }
        //vloží věc do kapsy, pokud nebyla překročena její kapacita
        if (kapsa.vlozVec(vec)) {
            return "Vložil sis " + nazev + " do kapsy.";
        } else {
            hPlan.getAktualniProstor().vlozVec(vec);
            return "Tolik věcí se už do kapsy nevejde.";        
        }
        
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
