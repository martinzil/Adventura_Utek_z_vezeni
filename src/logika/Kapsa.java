/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import utils.Observer;
import utils.Subject;

/*******************************************************************************
 * Třída Kapsa - představuje inventář s omezenou kapacitou 3 věcí.
 * 
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class Kapsa implements Subject {
    //== Datové atributy (statické i instancí)======================================
    private Map <String, Vec> veci;   //Seznam věcí v kapse.
    private static final int KAPACITA = 3;   //Maximální počet věcí v kapse.
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor.
     */
    public Kapsa() {
        veci = new HashMap<String, Vec>();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     *  Metoda, která najde věc.
     *  
     *  @param  vec  Parametrem je věc, kterou chceme získat.
     */     
    public Vec getVec(String nazevVeci) {
        return veci.get(nazevVeci);
    }
    
    /**
     *  Metoda zjistí, zda se věc vejde do kapsy.
     *
     *  @return   Vrátí true, pokud se vejde do kapsy.
     */
    public boolean volno() {
        return (veci.size() < KAPACITA);
    }
    
    /**
     * Metoda přidá věc do kapsy, pokud v ní je místo a pokud je věc přenositelná.
     * 
     * 
     * @param vec věc, která se má přidat do kapsy.
     * @return true, pokud se věc podaří přidat do kapsy.
     */

    public boolean vlozVec (Vec vec) {
        notifyObservers();
        if(volno()== true && (vec.isPrenositelna())) {
            veci.put(vec.getNazev(), vec);
            notifyObservers();
            return true;
        } 
        return false;
    }
    
     /**
     * Metoda odebere věc z kapsy.
     * 
     * @param  vec  Parametrem je věc, která sa má odebrat z kapsy.
     * 
     * @return   Vrátí jméno věci, která byla odebrána z kapsy.
     */   
    public Vec vyndejVec(String nazev) {
            
        Vec vyndanaVec = null;
        if (veci.containsKey(nazev)) {
            vyndanaVec = veci.get(nazev);
            veci.remove(nazev);
            notifyObservers();
            
        }
        return vyndanaVec;
    } 
    
     /**
     *  Metoda zjišťuje, zda se daná věc vyskytuje v kapse.
     */   
    public boolean obsahujeVec (String nazev) {
        if (this.veci.containsKey(nazev)) {
            return true;
        }
        return false;
    }    

    /**
     *  Metoda zjistí obsah kapsy.
     *  Jednotlivé věci jsou odděleny mezerou.
     *  
     *  @return seznam věcí v kapse.
     */   
    public String obsahKapsy() {
        String obsah = "V kapse máš ";
        for (String nazevVeci : veci.keySet()){
            obsah += nazevVeci + " ";
        }
        return obsah;
    }
    
    public Collection<String> getVeciKapsa() {
        return Collections.unmodifiableCollection(veci.keySet());
    }
    
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }
    /**
     * Zrušení observeru
     * @param observer Observer
     */
    @Override
    public void removeObserver(Observer observer) {
        listObserveru.remove(observer);
    }
    /**
     * Oznámení observeru
     */   
    @Override
    public void notifyObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }
    }

    //== Soukromé metody (instancí i třídy) ========================================
}
