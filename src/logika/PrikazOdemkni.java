/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * Třída PrikazOdemkni implementuje pro hru příkaz odemkni, 
 * který odemkne zamčené místnosti.
 *  
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazOdemkni implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "odemkni";
    private HerniPlan hPlan;
    private Kapsa kapsa;
    
    //== Konstruktory a tovární metody =============================================
    /**
    *  Konstruktor.
    */    
    public PrikazOdemkni(HerniPlan hPlan, Kapsa kapsa) {
        this.hPlan = hPlan;
        this.kapsa = kapsa;
    }
    
    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     *  Provádí příkaz "odemkni". Zjišťuji, zda z aktuální místnosti je
     *  východ do zadané místnosti. Pokud místnost existuje a je zamčená,
     *  tak se zjistí, zda v batohu je potřebný klíč. Pokud ano, odemknu
     *  místnost.
     *
     *@param parametry - jako  parametr obsahuje jméno místnosti,
     *                         do které se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední místnost), tak ....
            return "Co mám odemknout? Musíš zadat jméno místnosti";
        }

        String mistnost = parametry[0];

        // hledám zadanou místnost mezi východy
        Prostor sousedniProstor = hPlan.getAktualniProstor().vratSousedniProstor(mistnost);

        if (sousedniProstor == null) {
            return "Odsud nevedou dveře do místnosti "+mistnost+" !";
        }
        else {
            if (sousedniProstor.isZamcena()) {
                if (kapsa.obsahujeVec(sousedniProstor.getKlic().getNazev())) {
                    sousedniProstor.zamknout(false);
                    return "Podařilo se ti otevřít dveře do místnosti "
                           + mistnost + ". Můžeš vstoupit.";
                }
                else {
                    return "Pro odemčení dveří do prostoru "+mistnost+" potřebuješ mít "
                        + "u sebe "+ sousedniProstor.getKlic().getNazev();
                }
            }
            else {
                return "Místnost "+mistnost+" již byla odemčená.";
            }
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