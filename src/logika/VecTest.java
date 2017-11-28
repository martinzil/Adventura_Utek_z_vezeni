/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída VecTest slouží ke komplexnímu otestování třídy Vec.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class VecTest {
    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp() {
        //třída je prázdná, protože není potřeba nic chystat
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každého testu.
     */
    @After
    public void tearDown() {
        //třída je prázdná, protože po testu není třeba nic uklízet
    }

    //== VLASTNÍ TESTY =========================================================
    @Test
    /**
     * Testuje názvy, popisy, přenositelnost, nezničitelnost, spojitelnost a kouřitelnost věcí
     */
    public void testVeci() {
        Vec vec1 = new Vec("vec","popis", true, true, true, true);        
        vec1.getNazev();
        assertEquals("vec", vec1.getNazev());
        vec1.getPopis();
        assertEquals("popis", vec1.getPopis());
        vec1.isPrenositelna();
        assertEquals(true, vec1.isPrenositelna());
        vec1.isNeznicitelna();
        assertEquals(true, vec1.isNeznicitelna());
        vec1.isSpojitelna();
        assertEquals(true, vec1.isSpojitelna());
        vec1.isKouritelna();
        assertEquals(true, vec1.isKouritelna());
        
        Vec vec2 = new Vec("vec2","popis2", false, false, false, false);        
        vec2.getNazev();
        assertEquals("vec2", vec2.getNazev());
        vec2.getPopis();
        assertEquals("popis2", vec2.getPopis());
        vec2.isPrenositelna();
        assertEquals(false, vec2.isPrenositelna());
        vec2.isNeznicitelna();
        assertEquals(false, vec2.isNeznicitelna());
        vec2.isSpojitelna();
        assertEquals(false, vec2.isSpojitelna());
        vec2.isKouritelna();
        assertEquals(false, vec2.isKouritelna());
    }
}
