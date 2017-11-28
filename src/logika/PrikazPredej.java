/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třída PrikazPredej implementuje pro hru příkaz předej, který předá věc 
 * postavě v aktuálním prostoru, pokud má o danou
 * věc postava zájem.
 * 
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PrikazPredej implements IPrikaz {
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "predej";
    private HerniPlan hPlan;
    private Kapsa kapsa;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor ....
     */
    public PrikazPredej(HerniPlan hPlan, Kapsa kapsa) {
        this.hPlan = hPlan;
        this.kapsa = kapsa;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda se pokusí dát zadanou věc osobě v místnosti
     * 
     *@param parametr - název věci
     *@return fraze, která se vypíše hráči.
     */ 
    public String proved(String... parametry){   
        if (parametry.length == 0) {
            return "Nevím co chceš vytáhnout.";
        }
        
        Prostor aktualniProstor = hPlan.getAktualniProstor();
        String nazev = parametry[0];
        Vec vec = kapsa.getVec(nazev);
        
        if (vec == null) {
            return "Tuhle věc v kapse nemáš";
        }
        
        if (!vec.isKouritelna()) {
            return "Starý vězeň: " + "Tak " + nazev + " určitě kouřit nebudu...";
        }
        
        if (aktualniProstor.getNazev().equals("chodbaPodzemi") && kapsa.obsahKapsy().contains("cigarety") && hPlan.isZapaluje()) {
            kapsa.vyndejVec(nazev);
            hPlan.kouri=true;  
            return  "Starý vězeň: Díky za cigarety a zápalky. Za to ti poradím, jak sebrat klíč hlídači. \n"+
                    "Ty: ...To by se mi hodilo. \n"+
                    "Starý vězeň: V přízemí je nehlídaná ošetřovna plná léků a hned vedle je jídelna hlídačů, kde si vaří kafe. \n"+
                    "Ty: To je všechno? \n"+
                    "Starý vězeň: Prozkoumej strážnici...třeba něco najdeš. \n"+
                    "Starý vězeň: Teď mě nech vklidu zakouřit... \n"+
                    "Ty: To mi nestálo za námahu! \n"+
                    "Starý vězeň: Dobře...ještě jednu věc ti řeknu..., svoboda tě čeká v zamčené cele. \n"+
                    "Ty: Další skvělá rada...díky! \n";
        }
        
        if (aktualniProstor.getNazev().equals("chodbaPodzemi") && kapsa.obsahKapsy().contains("zapalky") && hPlan.isKouri()) {
            kapsa.vyndejVec(nazev);
            hPlan.hori=true;
            return  "Starý vězeň: Díky za cigarety a zápalky. Za to ti poradím, jak sebrat klíč hlídači. \n"+
                    "Ty: ...To by se mi hodilo. \n"+
                    "Starý vězeň: V přízemí je nehlídaná ošetřovna plná léků a hned vedle je jídelna hlídačů, kde si vaří kafe. \n"+
                    "Ty: To je všechno? \n"+
                    "Starý vězeň: Prozkoumej strážnici...třeba něco najdeš. \n"+
                    "Starý vězeň: Teď mě nech vklidu zakouřit... \n"+
                    "Ty: To mi nestálo za námahu! \n"+
                    "Starý vězeň: Dobře...ještě jednu věc ti řeknu..., svoboda tě čeká v zamčené cele. \n"+
                    "Ty: Další skvělá rada...díky! \n";
        }
        
        if (aktualniProstor.getNazev().equals("chodbaPodzemi") && vec.getNazev().equals("cigarety")) {
            kapsa.vyndejVec(nazev);
            hPlan.kouri=true;            
            return  "Starý vězeň: Díky za cigarety...dones mi ještě zápalky a poradím ti...  \n"+
                    "Ty: ...... \n";
        }
        
        if (aktualniProstor.getNazev().equals("chodbaPodzemi") && vec.getNazev().equals("zapalky")) {
            kapsa.vyndejVec(nazev);
            hPlan.hori=true;
            return  "Starý vězeň: Díky za zápalky...dones mi ještě cigarety a poradím ti...  \n"+
                    "Ty: ...... \n";
        }
        
        return "Tohle asi nepůjde.";
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
