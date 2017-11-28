/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.*;


/*******************************************************************************
 * Trida Vec - popisuje jednotlivé věci v prostorech hry
 * 
 * Tato třída je součástí jednoduché textové hry.
 * 
 * Každá věc může být: přenositelná, nezničitelná, spojitelná a kouřitelná.
 * Přenositelné věci se dají vložit do "Kapsy", nezničitelné nejdou zničit....
 * Věci jsou rozmístěné v jednotlivých prostorech hry.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class Vec {
    //== Datové atributy (statické i instancí)======================================
    private String nazev;
    private String popis;
    private boolean prenositelna;
    private boolean neznicitelna;
    private boolean spojitelna;
    private boolean kouritelna;
    private Map<String, Vec> veci;
    
    //== Konstruktory a tovární metody =============================================
    /***************************************************************************
     *  Konstruktor.
     */
    public Vec(String nazev, String popis, boolean prenositelna, boolean neznicitelna, boolean spojitelna, boolean kouritelna) {
        this.nazev = nazev;
        this.popis = popis;
        this.prenositelna = prenositelna;
        this.neznicitelna = neznicitelna;
        this.spojitelna = spojitelna;
        this.kouritelna = kouritelna;
        veci = new HashMap<>();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací název věci.
     */
    public String getNazev() {
        return nazev;
    }
    
    /**
     * Metoda vrací popis věci.
     */
    public String getPopis() {
        return popis;
    }
    
    /**
     * Metoda vraci true, pokud je věc přenositelná.
     */
    public boolean isPrenositelna() {
        return prenositelna;
    }
    
    /**
     * Metoda vrací true, pokud je věc nezničitelná.
     */
    public boolean isNeznicitelna() {
        return neznicitelna;
    }
    
    /**
     * Metoda vrací true, pokud se dá věc spojit s jinou věcí.
     */
    public boolean isSpojitelna() {
        return spojitelna;
    }
    
    /**
     * Metoda vrací true, pokud věc patří k předmětům, sloužícím ke kouření.
     */
    public boolean isKouritelna() {
        return kouritelna;
    }
    
    public void vlozVec(Vec vec){   
        veci.put(vec.getNazev(), vec);
    }
    
    public Map<String, Vec> vratSeznamVeci(){
        return veci;
    }
    
    //== Soukromé metody (instancí i třídy) ========================================
}
