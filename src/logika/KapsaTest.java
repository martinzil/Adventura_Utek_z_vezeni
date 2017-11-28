/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída KapsaTest slouží ke komplexnímu otestování třídy Kapsa.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class KapsaTest {
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
     * Metoda testuje vkládání věcí do kapsy, odebírání věcí z kapsy, obsah kapsy a kapacitu kapsy.
     */
    public void testKapsy() {
        Kapsa kapsa = new Kapsa();
        Vec vec1 = new Vec("vec1", "obyčejná věc", true, true, false, false);
        Vec vec2 = new Vec("vec2", "taky obyčejná věc", false, true, false, false);
        Vec vec3 = new Vec("vec3", "zase obyčejná věc", true, true, false, false);
        Vec vec4 = new Vec("vec4", "další obyčejná věc", true, true, false, false);
        Vec vec5 = new Vec("vec5", "a ještě jedna obyčejná věc", true, true, false, false);
        assertEquals(true, kapsa.vlozVec(vec1));
        assertEquals(false, kapsa.vlozVec(vec2));
        assertEquals(true, kapsa.vlozVec(vec3));
        assertEquals(true, kapsa.vlozVec(vec4));
        assertEquals(false, kapsa.vlozVec(vec5));
        kapsa.vyndejVec("vec1");
        assertEquals(false, kapsa.obsahujeVec("vec1"));
        assertEquals(false, kapsa.obsahujeVec("vec2"));
        assertEquals(true, kapsa.obsahujeVec("vec3"));
        assertEquals(true, kapsa.obsahujeVec("vec4"));
        assertEquals(false, kapsa.obsahujeVec("vec5"));
    }
}
