package logika;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.Observer;
import utils.Subject;

/**
 * Třída HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory, propojuje je vzájemně pomocí východů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Martin Zilinsky
 * @version    9.0
 * @created    leden 2017
 */
public class HerniPlan implements Subject{
    private Prostor viteznyProstor;
    private Prostor aktualniProstor;
    private Kapsa kapsa;
    private Map<String, Prostor> neviditelneProstory;
    public boolean hracUmrel=false;
    public boolean hracChytnut=false;
    public boolean hracVyhral=false;
    public boolean usnul=false;
    public boolean kouri=false;
    public boolean hori=false;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();
    }
    
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví tvojeCela.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        neviditelneProstory = new HashMap<>();
        Prostor tvojeCela = new Prostor("tvojeCela","Cela, ve které jsi byl uvězněn",45,230);
        Prostor svoboda = new Prostor("svoboda", "svoboda",285,425);
        Prostor chodbaPodzemi = new Prostor("chodbaPodzemi","Chodba v podzemní části vězení",280,230);
        Prostor prazdnaCela = new Prostor("prazdnaCela","Cela, ve které není žádný vězeň",165,333);
        Prostor celaStarehoVezne = new Prostor("celaStarehoVezne","Cela, ve které žije starý vězeň",280,333);
        Prostor zamcenaCela = new Prostor("zamcenaCela","Zamčená cela",405,333);
        Prostor tunel = new Prostor("tunel","Tunel, který tě dovede na svobodu",405,425);
        Prostor chodbaPrizemi = new Prostor("chodbaPrizemi","Chodba v přízemí vězení",280,137);
        Prostor umyvarna = new Prostor("umyvarna","Umývárna pro vězně",400,44);
        Prostor jidelna = new Prostor("jidelna","Jídelna pro hlídače",285,44);
        Prostor osetrovna = new Prostor("osetrovna","Vězeňská ošetřovna, najdeš zde léky",170,44);
        Prostor straznice = new Prostor("straznice","Strážnice vězení, kde sedí hlídač",45,137);
        Prostor nadvori = new Prostor("nadvori","Venkovní, oplocená část vězení",45,44);
     
        // přiřazují se průchody mezi prostory (sousedící prostory)
        tvojeCela.setVychod(chodbaPodzemi);
        chodbaPodzemi.setVychod(tvojeCela);
        chodbaPodzemi.setVychod(prazdnaCela);
        chodbaPodzemi.setVychod(celaStarehoVezne);
        chodbaPodzemi.setVychod(zamcenaCela);
        chodbaPodzemi.setVychod(chodbaPrizemi);
        prazdnaCela.setVychod(chodbaPodzemi);
        celaStarehoVezne.setVychod(chodbaPodzemi);
        zamcenaCela.setVychod(chodbaPodzemi);
        zamcenaCela.setVychod(tunel);
        tunel.setVychod(zamcenaCela);
        tunel.setVychod(svoboda);
        chodbaPrizemi.setVychod(chodbaPodzemi);
        chodbaPrizemi.setVychod(umyvarna);
        chodbaPrizemi.setVychod(jidelna);
        chodbaPrizemi.setVychod(osetrovna);
        chodbaPrizemi.setVychod(straznice);
        umyvarna.setVychod(chodbaPrizemi);
        jidelna.setVychod(chodbaPrizemi);
        osetrovna.setVychod(chodbaPrizemi);
        straznice.setVychod(chodbaPrizemi);
        straznice.setVychod(nadvori);
        nadvori.setVychod(straznice);
        
        aktualniProstor = tvojeCela;  // hra začíná v tvojí cele
        viteznyProstor = svoboda;
        // zamkneme prostory a vytvoříme klíče
        zamcenaCela.zamknout(true);
        Vec klic = new Vec("klic", "Obyčejný klíč", true, true, false, false);
        zamcenaCela.setKlic(klic);
        celaStarehoVezne.zamknout(true);
        Vec klicek = new Vec("klicek", "Klíč, který nenajdeš", true, true, false, false);
        celaStarehoVezne.setKlic(klicek);
        
        // zneviditelníme tunel
        neviditelneProstory.put(tunel.getNazev(), tunel);
        tunel.budeVidet(false);
        
        // vytvoříme několik věcí
        Vec toaletniPapir = new Vec("toaletak", "Luxusni role toaletniho papiru", true, true, false, false);
        Vec zachod = new Vec("zachod", "Uplne normalni zachod", false, true, false, false);
        Vec postel = new Vec("postel", "Stará, škaredá postel", false, true, false, false);
        Vec cigarety = new Vec("cigarety", "Poloprázdná krabička cigaret", true, true, false, true);
        Vec plakat = new Vec("plakat", "Plakát s obrázkem tunelu", true, false, false, false);
        Vec sprcha = new Vec("sprcha", "Obyčejná sprcha", false, true, false, false);
        Vec umyvadlo = new Vec("umyvadlo", "Obyčejné umyvadlo", false, true, false, false);
        Vec kava = new Vec("kava", "Kvalitní turecká káva", false, true, false, false);
        Vec nuz = new Vec("nuz", "Kvalitní kuchyňský nůž", true, true, false, false);
        Vec ziletka = new Vec("ziletka", "Ostrá žiletka", true, true, false, false);
        Vec parky = new Vec("parky", "Výborné vídeňské párečky", true, true, false, false);
        Vec zapalky = new Vec("zapalky", "Krabička zápalek", true, true, false, true);
        Vec neospan = new Vec("neospan", "Silné prášky na spaní", true, true, true, false);

        // umístíme věci do prostorů
        tvojeCela.vlozVec(toaletniPapir);
        tvojeCela.vlozVec(zachod);
        tvojeCela.vlozVec(postel);
        prazdnaCela.vlozVec(zachod);
        prazdnaCela.vlozVec(postel);
        prazdnaCela.vlozVec(cigarety);
        zamcenaCela.vlozVec(postel);
        zamcenaCela.vlozVec(zachod);
        zamcenaCela.vlozVec(plakat);
        umyvarna.vlozVec(sprcha);
        umyvarna.vlozVec(umyvadlo);
        umyvarna.vlozVec(ziletka);
        jidelna.vlozVec(kava);
        jidelna.vlozVec(nuz);
        jidelna.vlozVec(parky);
        jidelna.vlozVec(zapalky);
        osetrovna.vlozVec(neospan);
        straznice.vlozVec(klic);
        
        // vytvoříme postavy
        Postava staryVezen = new Postava("staryVezen","Chtěl bych si zakouřit...");
        Postava pes = new Postava("pes","haf! haf! haf!");
        Postava hlidac = new Postava("hlidac","....");
        
        // vložíme postavy do prostoru
        chodbaPodzemi.vlozPostava(staryVezen);
        nadvori.vlozPostava(pes);
        straznice.vlozPostava(hlidac);
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    /**
     *  Metoda vrací odkaz na vítězný prostor.
     *
     *@return     vítězný prostor
     */
    
    public Prostor getViteznyProstor() {
        return viteznyProstor;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory.
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       notifyObservers();
    }
    
    public void vlozProstor(Prostor prostor){
        
        neviditelneProstory.put(prostor.getNazev(), prostor);
    }
    
    public Prostor vyberProstor(String nazevProstoru){
        
        return neviditelneProstory.get(nazevProstoru);
    }
 
    
    /**
     * Metoda vrátí kapsu.
     */
    public Kapsa getKapsa() {
       return kapsa;
    }
    
    /**
     * Metoda vráti true, pokud hráč vyhrál.
     */
    public boolean hracVyhral() {
        if (aktualniProstor.getNazev().equals(viteznyProstor)) {
            return true;
        }
        return false;
    }
    
    /**
     * Metoda vrátí true, pokud byl hráč chytnut.
     */
    public boolean hracChytnut() {
        if (aktualniProstor.getNazev().equals("straznice") && !isUspany()) {
            hracChytnut=true;
        }
        return hracChytnut; 
    }
    
    /**
     * Metoda vrátí true, pokud hráč umřel.
     */
    public boolean hracUmrel() {
        if (aktualniProstor.getNazev().equals("nadvori")) {
            hracUmrel=true;
        }
        return hracUmrel;
    }
    
    /**
     * Metoda vrátí true, pokud hlídač usnul.
     */
    public boolean isUspany() {
        return usnul;
    }
    
    /**
     * Metoda vrátí true, pokud má starý vězeň cigarety.
     */
    public boolean isKouri() {
        return kouri;
    }
    
    /**
     * Metoda vrátí true, pokud má starý vězeň zápalky.
     */
    public boolean isZapaluje() {
        return hori;
    }

    @Override
    public void registerObserver(Observer observer) {
       listObserveru.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
       listObserveru.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
            
        }
    }

    
}
