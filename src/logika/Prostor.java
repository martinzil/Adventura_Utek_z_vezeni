package logika;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import utils.Subject;
import utils.Observer;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author      Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Martin Zilinsky
 * @version     9.0
 * @created     leden 2017
 */
public class Prostor implements Subject {
    private String nazev;
    private String popis;
    private boolean zamcena=false;
    private boolean viditelna=true;
    private Vec klic;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private Map<String, Vec> veci;
    private Map<String, Postava> postavy;
    private double posLeft;
    private double posTop;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     */
    public Prostor(String nazev, String popis, double posLeft, double posTop) {
        this.nazev = nazev;
        this.popis = popis;
        this.posLeft = posLeft;
        this.posTop = posTop;
        vychody = new HashSet<>();
        veci = new HashMap<>();
        postavy = new HashMap<>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
        notifyObservers();
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v místnosti: " + popis + ".\n"
                + popisVychodu() + "\n"
                + popisVeci() + "\n"
                + popisPostav() + "\n";  
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "vychody:";
        for (Prostor sousedni : vychody) {
            //pokud je východ z prostoru zamčen, vypíše se...
            if (sousedni.isZamcena()) {
                 vracenyText += " " + sousedni.getNazev() + "(zamknuto)";
            }
            //pokud je sousední prostor viditelný, vypíše se...
            if (sousedni.isVidet() && !sousedni.isZamcena()) {
                vracenyText += " " + sousedni.getNazev();
            }
            //pokud je sousední prostor neviditelný, vypíše se...
            if (!sousedni.isVidet()) {
                vracenyText +="";
             }
        }
        return vracenyText;
    }
    
    private String popisVeci() {
        String vracenyText = "veci:";
        for (String nazev : veci.keySet()) {
            vracenyText += " " + nazev;
        }
        
        return vracenyText;
    }
    
    private String popisPostav() {
        String vracenyText = "postavy:";
        for (String jmeno : postavy.keySet()) {
            vracenyText += " " + jmeno;
        }
        return vracenyText;
    }
    
    /**
     * Metoda najde postavu.
     */

    public Postava najdiPostavu(String jmeno) {
        return postavy.get(jmeno);
    }
    
    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }
    
    /**
     *  Vloží věc do prostoru.
     *  @param vec
     */
    public void vlozVec(Vec vec) {
        veci.put(vec.getNazev(), vec);
        notifyObservers();
    }
    
    /**
     *  Odebere věc z prostoru.
     *  @param vec
     */
    public Vec odeberVec(String nazev) {
        Vec vec = veci.remove(nazev);
        notifyObservers();
        return vec;
    }
    
    /**
     * Vrací kolekci obsahující názvy věcí v prostoru
     *
     * @return Nemodifikovatelná kolekce názvů věcí, které jsou v prostoru
     */
    public Collection<String> getVeci() {
        
        return Collections.unmodifiableCollection(veci.keySet());
    }
    
    /**
     *  Vloží postavu do prostoru.
     *  @param postava
     */
    public void vlozPostava(Postava postava) {        
        postavy.put(postava.getJméno(), postava);
    }
    
    /**
     *  Odebere postavu z prostoru.
     *  @param postava
     */
    public Postava odeberPostava(String jmeno) {
        return postavy.remove(jmeno);
    }
    
    /**
     * Zamkne prostor.
     * 
     */
    public void zamknout(boolean zamcena) {
        this.zamcena = zamcena;
    }
    
    /**
     * Zamkne prostor.
     * 
     */
    public boolean isZamcena() {
        return zamcena;
    }
    
    /**
     * Nastaví klíč pro zamčený prosor.
     */
    public void setKlic(Vec klic) {
        this.klic = klic;
    }
    
    /**
     * Zjistí klíč pro zamčený prostor.
     */
    public Vec getKlic() {
        return klic;
    }
    
    /**
     * Nastaví viditelnost prostoru.
     */
    public void budeVidet(boolean viditelna) {
        this.viditelna = viditelna;
    }
    
    /**
     * Zjistí viditelnost prostoru.
     */
    public boolean isVidet() {
        return viditelna;
    }   

    /**
     * @return the posLeft
     */
    public double getPosLeft() {
        return posLeft;
    }

    /**
     * @return the posTop
     */
    public double getPosTop() {
        return posTop;
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
