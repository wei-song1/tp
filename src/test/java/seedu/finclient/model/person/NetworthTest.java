package seedu.finclient.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NetworthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Networth(null));
    }

    @Test
    public void constructor_invalidNetworth_throwsIllegalArgumentException() {
        String invalidNetworth = "";
        assertThrows(IllegalArgumentException.class, () -> new Networth(invalidNetworth));
    }

    @Test
    public void isValidNetworth() {
        // null networth
        assertThrows(NullPointerException.class, () -> Networth.isValidNetworth(null));

        // invalid networth
        assertFalse(Networth.isValidNetworth("")); // empty string
        assertFalse(Networth.isValidNetworth(" ")); // spaces only

        // valid networth
        assertTrue(Networth.isValidNetworth("10000"));
        assertTrue(Networth.isValidNetworth("< $100k")); // valid bracket name
        assertTrue(Networth.isValidNetworth("$1 million - $5 million")); // valid bracket name
        assertTrue(Networth.isValidNetworth("Hidden")); // valid special name
        assertTrue(Networth.isValidNetworth("delete")); // valid special name
    }

    @Test
    public void isBracket() {
        // valid brackets
        assertTrue(Networth.isBracket("< $100k"));
        assertTrue(Networth.isBracket("$100k - $250k"));
        assertTrue(Networth.isBracket("$250k - $500k"));
        assertTrue(Networth.isBracket("$500k - $1 million"));
        assertTrue(Networth.isBracket("$1 million - $5 million"));
        assertTrue(Networth.isBracket("> $5 million"));
        assertTrue(Networth.isBracket("Hidden"));
        assertTrue(Networth.isBracket("delete"));

        // invalid brackets
        assertFalse(Networth.isBracket("RandomValue"));
        assertFalse(Networth.isBracket("500000"));
    }

    @Test
    public void getNetworthBracket() {
        // check correct bucket assignment
        assertEquals("< $100k", new Networth("50000").value);
        assertEquals("< $100k", new Networth("1").value);
        assertEquals("< $100k", new Networth("0").value);
        assertEquals("< $100k", new Networth("99999").value);
        assertEquals("$100k - $250k", new Networth("100000").value);
        assertEquals("$100k - $250k", new Networth("150000").value);
        assertEquals("$100k - $250k", new Networth("249999").value);
        assertEquals("$250k - $500k", new Networth("300000").value);
        assertEquals("$500k - $1 million", new Networth("750000").value);
        assertEquals("$1 million - $5 million", new Networth("2000000").value);
        assertEquals("> $5 million", new Networth("6000000").value);
        assertEquals("> $5 million", new Networth("9999999").value);
    }

    @Test
    public void isValidAmount() {
        assertThrows(NumberFormatException.class, () -> Networth.isValidAmount(null));
        assertThrows(IllegalArgumentException.class, () -> Networth.isValidAmount(""));
        assertFalse(Networth.isValidAmount("-1"));
        assertFalse(Networth.isValidAmount("-999"));
        assertTrue(Networth.isValidAmount("1"));
        assertTrue(Networth.isValidAmount("999"));
    }

    @Test
    public void equals() {
        Networth networth = new Networth("1000000");

        // same values -> returns true
        assertTrue(networth.equals(new Networth("1000000")));

        // same object -> returns true
        assertTrue(networth.equals(networth));

        // null -> returns false
        assertFalse(networth.equals(null));

        // different types -> returns false
        assertFalse(networth.equals(5.0f));

        // different values -> returns false
        assertFalse(networth.equals(new Networth("500000")));
    }

    @Test
    public void toString_validNetworth() {
        Networth networth = new Networth("150000");
        assertEquals("$100k - $250k", networth.toString());
    }

    @Test
    public void hashCode_validNetworth() {
        Networth networth = new Networth("1000000");
        assertEquals(new Networth("1000000").hashCode(), networth.hashCode());
    }

    @Test
    public void compareTo_validNetworth() {
        Networth networth = new Networth("1000000");
        assertEquals(0, networth.compareTo(new Networth("1000000")));

        Networth networth2 = new Networth("500000");
        assertEquals(1, networth.compareTo(networth2));

        Networth networth3 = new Networth("5000000");
        assertEquals(-1, networth.compareTo(networth3));
    }

    @Test
    public void compareTo_invalidNetworth() {
        Networth networth = new Networth("1000000");
        assertThrows(IllegalArgumentException.class, () -> networth.compareTo(new Networth("")));
    }

    @Test
    public void compareTo_nullNetworth() {
        Networth networth = new Networth("1000000");
        assertThrows(NullPointerException.class, () -> networth.compareTo(null));
    }
}
