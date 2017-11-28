/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PostavaTest slouží ke komplexnímu otestování třídy Postava.
 *
 * @author    Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class PostavaTest {
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
     * Testuje proslovy postavy.
     */
    public void testPostavy() {
        Postava postava1 = new Postava("postava1","proslov postavy");        
        postava1.getProslov();
        assertEquals("proslov postavy", postava1.getProslov());
    }
}
