package logika;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída ProstorTest slouží ke komplexnímu otestování
 * třídy Prostor
 *
 * @author    Jarmila Pavlíčková, Martin Zilinsky
 * @version   9.0
 * @created   leden 2017
 */
public class ProstorTest {
    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================
    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @Before
    public void setUp() {
        //třída je prázdná, protože není potřeba nic chystat
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
        //třída je prázdná, protože po testu není třeba nic uklízet
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================
    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře.
     */
    @Test
    public  void testLzeProjit() {
        Prostor prostor1 = new Prostor("hala", "vstupní hala budovy VŠE na Jižním městě",30,50);
        Prostor prostor2 = new Prostor("bufet", "bufet, kam si můžete zajít na svačinku",40,20);
        prostor1.setVychod(prostor2);
        prostor2.setVychod(prostor1);
        assertEquals(prostor2, prostor1.vratSousedniProstor("bufet"));
        assertEquals(null, prostor2.vratSousedniProstor("pokoj"));
    }
    
    @Test
    public void testVeci() {
        logika.Prostor prostor1 = new logika.Prostor(null, null,10,20);
        logika.Vec vec1 = new logika.Vec("a", "popis a", true, true, false, false);
        logika.Vec vec2 = new logika.Vec("b", "popis b", false, true, false, false);
        prostor1.vlozVec(vec1);
        prostor1.vlozVec(vec2);
        assertSame(vec1, prostor1.odeberVec("a"));
        assertNull(prostor1.odeberVec("c"));
    }
    
    /**
     * Testuje, zda je k prostoru přiřazený správný klíč. Prostory
     * nemusí odpovídat vlastní hře.
     */
    @Test
    public void testKlice() {
        Vec klic = new Vec("klic","klic od bufetu", true, true, false, false);
        Prostor prostor1 = new Prostor("hala", "vstupní hala budovy VŠE na Jižním městě",40,80);
        Prostor prostor2 = new Prostor("bufet", "bufet, kam si můžete zajít na svačinku",90,10);
        prostor1.setVychod(prostor2);
        prostor2.setVychod(prostor1);
        prostor2.zamknout(true);
        prostor2.setKlic(klic);
        assertEquals(klic, prostor2.getKlic());
    }
    
    /**
     * Testuje, zda místnost obsahuje žádanou postavu. Prostory
     * nemusí odpovídat vlastní hře.
     */
    public void testPostava()
    {
        Prostor prostor1 = new Prostor("hala", "vstupní hala budovy VŠE na Jižním městě",50,40);
        Postava postava1 = new Postava("student", "Ztracený student");
        prostor1.vlozPostava(postava1);
        assertEquals(postava1, prostor1.najdiPostavu("student"));
        assertEquals(null, prostor1.najdiPostavu("profesor"));
    }
}
