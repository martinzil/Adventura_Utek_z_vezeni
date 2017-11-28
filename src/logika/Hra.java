package logika;

/**
 * Třída Hra - třída představující logiku adventury.
 * 
 * Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan,
 * která inicializuje místnosti hry a vytváří seznam platných příkazů a instance tříd
 * provádějící jednotlivé příkazy. Vypisuje uvítací a ukončovací text hry. Také
 * vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Martin Zilinsky
 * @version    9.0
 * @created    leden 2017
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private Kapsa kapsa;
    private boolean konecHry = false;

    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        kapsa = new Kapsa();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazVezmi(herniPlan, kapsa));
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKapsa(kapsa));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(herniPlan, kapsa));
        platnePrikazy.vlozPrikaz(new PrikazMluv(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPredej(herniPlan, kapsa));
        platnePrikazy.vlozPrikaz(new PrikazZnic(herniPlan, kapsa));
        platnePrikazy.vlozPrikaz(new PrikazNasyp(herniPlan, kapsa));
        platnePrikazy.vlozPrikaz(new PrikazOdemkni(herniPlan, kapsa));
    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "Jsi vězeň a díky nepozornému hlídači, který nezamkl tvoji celu, máš šanci utéct! \n"+ 
               "Prozkoumej okolí a najdi cestu na svobodu! \n"+
               herniPlan.getAktualniProstor().dlouhyPopis();
    }
    
    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        if (herniPlan.hracVyhral()) {
            konecHry=true;
            return "Vyhrál jsi. Užívej si svobody!\n";
        }
        
        if (herniPlan.hracChytnut()) {
            konecHry=true;
            return "Chytil tě hlídač! Příště buď opatrnější.\n";  
        }
        
        if (herniPlan.hracUmrel()) {
            konecHry=true;
            return "Zakousl tě hlídací pes! Příště si rozmysli kam jdeš.\n";  
        }
        
        return "Příště to snad dotáhneš do konce.\n";
    }
    
    /** 
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry() {
        return konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++) {
            parametry[i]= slova[i+1];   
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.proved(parametry);

            if (herniPlan.hracVyhral()) {
                konecHry=true;
            }
            if (herniPlan.hracUmrel()) {
                konecHry=true;
            }
            if (herniPlan.hracChytnut()) {
                konecHry=true;
            }
            if (herniPlan.getAktualniProstor() == herniPlan.getViteznyProstor()  ) {
                konecHry = true;
                textKVypsani = textKVypsani + "\n Hurá";
            }
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
    
     /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní IPrikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
    
     /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     *  @return     odkaz na herní plán
     */
    public HerniPlan getHerniPlan() {
        return herniPlan;
    }
    
    /**
     * Metoda vrací odkaz na seznam věcí v kapse
     *
     * @return seznam věcí v košíku
     */ 
    public Kapsa getKapsa() {
        return kapsa;
    }
}
