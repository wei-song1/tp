package seedu.finclient.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.finclient.testutil.Assert.assertThrows;
import static seedu.finclient.testutil.TypicalPersons.ALICE;
import static seedu.finclient.testutil.TypicalPersons.BENSON;
import static seedu.finclient.testutil.TypicalPersons.BOB;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.finclient.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void constructor_validInputs_createsPerson() {
        // Construct a valid person object
        Person person = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.getName(), person.getName());
        assertEquals(ALICE.getPhoneList(), person.getPhoneList());
        assertEquals(ALICE.getEmail(), person.getEmail());
        assertEquals(ALICE.getAddress(), person.getAddress());
        assertEquals(ALICE.getTags(), person.getTags());
        assertEquals(ALICE.getCompany(), person.getCompany());
        assertEquals(ALICE.getJob(), person.getJob());
        assertEquals(ALICE.getStockPlatform(), person.getStockPlatform());
        assertEquals(ALICE.getNetworth(), person.getNetworth());
    }

    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        // Check if null values are handled by throwing NullPointerException
        assertThrows(NullPointerException.class, () ->
                new Person(null, null, null, null, null, null,
                null, null, null, null, null));
    }

    @Test
    public void otherConstructor_nullValues_throwsNullPointerException() {
        // Check if null values are handled by throwing NullPointerException
        assertThrows(NullPointerException.class, () ->
                new Person(null, null, null, null, null,
                        null, null, null, null, null));
    }

    @Test
    public void testHiddenBehavior() {
        // Hide the person
        Person hiddenAlice = new PersonBuilder(ALICE).build();
        hiddenAlice.setHidden();
        assertTrue(hiddenAlice.getIsHidden(), "Person should be hidden.");

        // Now verify that the 'hidden' variants are returned
        assertEquals("00000000", hiddenAlice.getPhoneList().toString(),
                "PhoneList should be replaced with 00000000 when hidden.");
        assertEquals("hidden@example.com", hiddenAlice.getEmail().toString(),
                "Email should be replaced with hidden@example.com when hidden.");
        assertEquals("Hidden", hiddenAlice.getAddress().toString(),
                "Address should read 'Hidden' when hidden.");
        assertEquals("Sensitive details are hidden", hiddenAlice.getRemark().toString(),
                "Remark should read 'Sensitive details are hidden' when hidden.");
        assertEquals("Hidden", hiddenAlice.getCompany().toString(),
                "Company should read 'Hidden' when hidden.");
        assertEquals("Hidden", hiddenAlice.getJob().toString(),
                "Job should read 'Hidden' when hidden.");
        assertEquals("Hidden", hiddenAlice.getStockPlatform().toString(),
                "Stock platform should read 'Hidden' when hidden.");
        assertEquals("Hidden", hiddenAlice.getNetworth().toString(),
                "Networth should read 'Hidden' when hidden.");
        assertTrue(hiddenAlice.getTags().isEmpty(), "Tags should be empty when hidden.");
    }

    @Test
    public void testUnhiddenBehavior() {
        // Unhide after hiding the person
        Person hiddenAlice = new PersonBuilder(ALICE).build();
        hiddenAlice.setHidden();
        hiddenAlice.setUnhidden();
        assertFalse(hiddenAlice.getIsHidden(), "Person should be unhidden.");

        // Verify original details are returned after unhidden
        assertEquals(ALICE.getPhoneList(), hiddenAlice.getPhoneList(), "PhoneList should revert to original.");
        assertEquals(ALICE.getEmail(), hiddenAlice.getEmail(), "Email should revert to original.");
        assertEquals(ALICE.getAddress(), hiddenAlice.getAddress(), "Address should revert to original.");
        assertEquals(ALICE.getRemark(), hiddenAlice.getRemark(), "Remark should revert to original.");
        assertEquals(ALICE.getTags(), hiddenAlice.getTags(), "Tags should revert to original.");
        assertEquals(ALICE.getCompany(), hiddenAlice.getCompany(), "Company should revert to original.");
        assertEquals(ALICE.getJob(), hiddenAlice.getJob(), "Job should revert to original.");
        assertEquals(ALICE.getStockPlatform(), hiddenAlice.getStockPlatform(),
                "Stock platform should revert to original.");
        assertEquals(ALICE.getNetworth(), hiddenAlice.getNetworth(), "Networth should revert to original.");
    }

    @Test
    public void equals_samePerson_returnsTrue() {
        // Compare the same person instance
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // Same object should return true
        assertTrue(ALICE.equals(ALICE));

        // Compare with null should return false
        assertFalse(ALICE.equals(null));

        // Compare with a different type should return false
        assertFalse(ALICE.equals(5));

        // Compare with a different person object
        assertFalse(ALICE.equals(BOB));

        // Different Name
        Person nameAliceCopy = new PersonBuilder(ALICE).withName("test").build();
        assertFalse(ALICE.isSamePerson(nameAliceCopy));

        // Different Email
        Person emailAliceCopy = new PersonBuilder(ALICE).withEmail("test@test").build();
        assertFalse(ALICE.isSamePerson(emailAliceCopy));

        // Different Address
        Person addressAliceCopy = new PersonBuilder(ALICE).withAddress("test").build();
        assertFalse(ALICE.isSamePerson(addressAliceCopy));

        // Different Number, Same Everything Else
        Person numAliceCopy = new PersonBuilder(ALICE).withPhone("123456").build();
        assertTrue(ALICE.isSamePerson(numAliceCopy));
    }

    @Test
    public void isSamePerson_differentAttributes_returnsTrue() {
        // Same name and phonelist, different other attributes
        Person editedAlice = new PersonBuilder(ALICE).withPhone(PersonBuilder.DEFAULT_PHONE)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void compareTo_sameName_returnsEqual() {
        // Compare two persons with same name
        assertEquals(0, ALICE.compareTo(ALICE, "name"));
    }

    @Test
    public void compareTo_differentNames_returnsComparison() {
        // Compare two persons with different names
        assertTrue(ALICE.compareTo(BOB, "name") < 0,
                "ALICE should come before BOB in name comparison.");
        assertTrue(BOB.compareTo(ALICE, "name") > 0,
                "BOB should come after ALICE in name comparison.");
    }

    @Test
    public void compareTo_differentNetworth_returnsComparison() {
        // Compare two persons with different networth
        assertTrue(ALICE.compareTo(BENSON, "networth") < 0,
                "ALICE should come before Benson in networth comparison.");
        assertTrue(BENSON.compareTo(ALICE, "networth") > 0,
                "Benson should come after Alice in networth comparison.");
    }

    @Test
    public void compareTo_sameNetworth_returnsEqual() {
        // Compare two persons with same networth
        assertEquals(0, ALICE.compareTo(ALICE, "networth"));
    }

    @Test
    public void compareTo_differentAmounts_returnsComparison() {
        // Compare two persons with different amounts
        assertTrue(ALICE.compareTo(BENSON, "amount") < 0,
                "Alice should come before Benson in amount comparison.");
        assertTrue(BENSON.compareTo(ALICE, "amount") > 0,
                "Benson should come after Alice in amount comparison.");
    }

    @Test
    public void compareTo_sameAmounts_returnsEqual() {
        // Compare two persons with same amounts
        assertEquals(0, ALICE.compareTo(ALICE, "amount"));
    }

    @Test
    public void compareTo_differentPrices_returnsComparison() {
        // Compare two persons with different amounts
        assertTrue(ALICE.compareTo(BENSON, "price") > 0,
                "Alice should come after Benson in price comparison.");
        assertTrue(BENSON.compareTo(ALICE, "price") < 0,
                "Benson should come before Alice in price comparison.");
    }

    @Test
    public void compareTo_samePrices_returnsEqual() {
        // Compare two persons with same amounts
        assertEquals(0, ALICE.compareTo(ALICE, "price"));
    }

    @Test
    public void compareToNull_throwsNullPointerException() {
        // Check if null values are handled by throwing NullPointerException
        assertThrows(NullPointerException.class, () -> ALICE.compareTo(null, "name"));
    }

    @Test
    public void compareTo_deadline() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);

        Person personYesterday = new PersonBuilder()
                .withRemark("Old Event by/",
                        Optional.of(yesterday.atStartOfDay()))
                .withName("Past Person")
                .build();

        Person personToday = new PersonBuilder()
                .withRemark("Today Event by/",
                        Optional.of(today.atStartOfDay()))
                .withName("Today Person")
                .build();

        Person personTomorrow = new PersonBuilder()
                .withRemark("Tomorrow Event by/",
                        Optional.of(tomorrow.atStartOfDay()))
                .withName("Future Person")
                .build();

        // Compare persons with different deadlines
        assertTrue(personYesterday.compareTo(personToday, "deadline") < 0,
                "Person with yesterday's deadline should come before today's deadline.");
        assertTrue(personToday.compareTo(personTomorrow, "deadline") < 0,
                "Person with today's deadline should come before tomorrow's deadline.");
        assertTrue(personTomorrow.compareTo(personToday, "deadline") > 0,
                "Person with tomorrow's deadline should come after today's deadline.");
        assertTrue(personToday.compareTo(personYesterday, "deadline") > 0,
                "Person with today's deadline should come after yesterday's deadline.");
        assertEquals(0, personToday.compareTo(personToday, "deadline"),
                "Person with same deadline should be equal.");
        assertTrue(personYesterday.compareTo(personTomorrow, "deadline") < 0,
                "Person with yesterday's deadline should come before tomorrow's deadline.");
        assertTrue(personTomorrow.compareTo(personYesterday, "deadline") > 0,
                "Person with tomorrow's deadline should come after yesterday's deadline.");
    }

    @Test
    public void compareToDeadline_oneWithRemarkOneWithoutRemark() {
        // Compare one person with a deadline and another without
        Person personWithRemark = new PersonBuilder()
                .withRemark("Event by/",
                        Optional.of(LocalDate.now().plusDays(1).atStartOfDay()))
                .withName("Person with Remark")
                .build();

        Person personWithoutRemark = new PersonBuilder()
                .withName("Person without Remark")
                .build();

        assertTrue(personWithRemark.compareTo(personWithoutRemark, "deadline") < 0,
                "Person with deadline should come before person without deadline.");
        assertTrue(personWithoutRemark.compareTo(personWithRemark, "deadline") > 0,
                "Person without deadline should come after person with deadline.");
    }

    @Test
    public void compareToDeadline_bothWithoutRemark() {
        // Compare one person with a deadline and another without

        Person personWithoutRemark = new PersonBuilder()
                    .withName("Person without Remark")
                .build();

        Person anotherPersonWithoutRemark = new PersonBuilder()
                .withName("Person without Remark")
                .build();

        assertEquals(0, personWithoutRemark.compareTo(anotherPersonWithoutRemark, "deadline"),
                "Two persons without deadlines should be equal.");
    }

    @Test
    public void compareTo_bothWithoutNetworth() {
        Person personWithoutNetworth = new PersonBuilder()
                .withName("Person without Networth")
                .withNetworth()
                .build();

        Person anotherPersonWithoutNetworth = new PersonBuilder()
                .withName("Another Person without Networth")
                .withNetworth()
                .build();

        assertEquals(0, personWithoutNetworth.compareTo(anotherPersonWithoutNetworth, "networth"),
                "Two persons without networth should be equal.");
        assertTrue(personWithoutNetworth.getNetworth().toString().isEmpty(), "Networth should be empty.");
        assertTrue(anotherPersonWithoutNetworth.getNetworth().toString().isEmpty(), "Networth should be empty.");
    }

    @Test
    public void compareTo_bothWithNetworth() {
        Person personWithNetworth = new PersonBuilder()
                .withName("Person with Networth")
                .withNetworth("1000000")
                .build();

        Person anotherPersonWithNetworth = new PersonBuilder()
                .withName("Another Person with Networth")
                .withNetworth("200000000")
                .build();

        assertTrue(personWithNetworth.compareTo(anotherPersonWithNetworth, "networth") < 0,
                "Person with lower networth should come before person with higher networth.");
        assertTrue(anotherPersonWithNetworth.compareTo(personWithNetworth, "networth") > 0,
                "Person with higher networth should come after person with lower networth.");
        assertFalse(personWithNetworth.getNetworth().toString().isEmpty(), "Networth should not be empty.");
        assertFalse(anotherPersonWithNetworth.getNetworth().toString().isEmpty(), "Networth should not be empty.");
    }

    @Test
    public void compareTo_oneWithNetworthAndOneWithout() {
        Person personWithNetworth = new PersonBuilder()
                .withName("Person with Networth")
                .withNetworth("1000000")
                .build();

        Person personWithoutNetworth = new PersonBuilder()
                .withName("Person without Networth")
                .withNetworth()
                .build();

        assertTrue(personWithNetworth.compareTo(personWithoutNetworth, "networth") < 0,
                "Person with networth should come before person without networth.");
        assertTrue(personWithoutNetworth.compareTo(personWithNetworth, "networth") > 0,
                "Person without networth should come after person with networth.");
        assertFalse(personWithNetworth.getNetworth().toString().isEmpty(), "Networth should not be empty.");
        assertTrue(personWithoutNetworth.getNetworth().toString().isEmpty(), "Networth should be empty.");
    }

    @Test
    public void toString_hiddenPerson_displaysHiddenDetails() {
        // Hide the person and check the toString method
        Person hiddenAlice = new PersonBuilder(ALICE).build();
        hiddenAlice.setHidden();
        String hiddenString = hiddenAlice.toString();
        assertTrue(hiddenString.contains("Sensitive details are hidden"),
                "Hidden person's toString() should indicate that details are hidden.");
    }

    @Test
    public void toString_unhiddenPerson_displaysFullDetails() {
        // Check the toString method for unhidden person
        String unhiddenString = ALICE.toString();
        assertTrue(unhiddenString.contains(ALICE.getName().toString()),
                "Unhidden person's toString() should show full details.");
    }

    @Test
    public void testingHashValue() {
        Person hiddenAlice = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), hiddenAlice.hashCode());
    }
}
