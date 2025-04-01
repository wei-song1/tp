package seedu.finclient.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.finclient.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.finclient.testutil.Assert.assertThrows;
import static seedu.finclient.testutil.TypicalPersons.ALICE;
import static seedu.finclient.testutil.TypicalPersons.BENSON;
import static seedu.finclient.testutil.TypicalPersons.BOB;
import static seedu.finclient.testutil.TypicalPersons.CARL;
import static seedu.finclient.testutil.TypicalPersons.CINDY;
import static seedu.finclient.testutil.TypicalPersons.FIONA;
import static seedu.finclient.testutil.TypicalPersons.GEORGE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.finclient.model.person.exceptions.DuplicatePersonException;
import seedu.finclient.model.person.exceptions.PersonNotFoundException;
import seedu.finclient.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }

    @Test
    public void hidePerson_singlePerson_personIsHidden() {
        uniquePersonList.add(ALICE);
        // ALICE should not be hidden initially
        assertFalse(ALICE.getIsHidden(), "ALICE should start unhidden.");

        // Hide ALICE
        uniquePersonList.hidePerson(ALICE);

        // After hiding, ALICE should be hidden
        assertTrue(ALICE.getIsHidden(), "ALICE should now be hidden.");

        uniquePersonList.revealPerson(ALICE);
    }

    @Test
    public void hidePerson_personNotInList_noChange() {
        uniquePersonList.add(ALICE);
        // BOB is not in the list, so this should do nothing
        uniquePersonList.hidePerson(BOB);

        // ALICE still in the list, still unhidden
        assertFalse(ALICE.getIsHidden(), "ALICE should remain unhidden because we tried to hide BOB.");

        uniquePersonList.revealPerson(BOB);
    }

    @Test
    public void hidePerson_byPredicate_multiplePersonsHidden() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);

        // Both ALICE & BOB unhidden initially
        assertFalse(ALICE.getIsHidden());
        assertFalse(BOB.getIsHidden());

        // Hide all persons whose name contains "bob" (case-insensitive),
        // so BOB definitely matches, ALICE does not.
        NameContainsKeywordsPredicate nameContainsBob = new NameContainsKeywordsPredicate(
                Collections.singletonList("bob"));
        uniquePersonList.hidePerson(nameContainsBob);

        // Verify BOB got hidden, ALICE stayed unhidden
        assertFalse(ALICE.getIsHidden(), "ALICE should still be unhidden.");
        assertTrue(BOB.getIsHidden(), "BOB should now be hidden.");

        uniquePersonList.revealPerson(nameContainsBob);
    }

    @Test
    public void revealPerson_singlePerson_personIsRevealed() {
        // Hide ALICE first, then reveal her
        uniquePersonList.add(ALICE);
        uniquePersonList.hidePerson(ALICE);
        assertTrue(ALICE.getIsHidden(), "ALICE should be hidden after hidePerson.");

        // Now reveal ALICE
        uniquePersonList.revealPerson(ALICE);
        assertFalse(ALICE.getIsHidden(), "ALICE should be revealed now.");
    }

    @Test
    public void revealPerson_byPredicate_multiplePersonsRevealed() {
        // Add ALICE & BOB, hide them both, then reveal only persons matching predicate
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);

        uniquePersonList.hidePerson(ALICE);
        uniquePersonList.hidePerson(BOB);
        assertTrue(ALICE.getIsHidden(), "ALICE hidden.");
        assertTrue(BOB.getIsHidden(), "BOB hidden.");

        // Reveal all persons whose name contains "alice" (case-insensitive)
        NameContainsKeywordsPredicate nameContainsAlice = new NameContainsKeywordsPredicate(
                Collections.singletonList("alice"));
        uniquePersonList.revealPerson(nameContainsAlice);

        // ALICE should now be revealed, BOB should remain hidden
        assertFalse(ALICE.getIsHidden(), "ALICE should be revealed now.");
        assertTrue(BOB.getIsHidden(), "BOB should remain hidden.");

        uniquePersonList.revealPerson(BOB);
    }

    @Test
    public void getUpcomingPersons_returnsCorrectlyFilteredAndSortedList() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);

        Person personYesterday = new PersonBuilder()
                .withRemark("Old Event by/"
                        + yesterday.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Past Person")
                .build();

        Person personToday = new PersonBuilder()
                .withRemark("Today Event by/"
                        + today.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Today Person")
                .build();

        Person personTomorrow = new PersonBuilder()
                .withRemark("Tomorrow Event by/"
                        + tomorrow.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Future Person")
                .build();
        System.out.println(personTomorrow.getRemark().toString());


        uniquePersonList.add(personYesterday);
        uniquePersonList.add(personToday);
        uniquePersonList.add(personTomorrow);

        List<Person> upcoming = uniquePersonList.upcomingPersons(2);

        assertEquals(2, upcoming.size());
        assertEquals("Today Person", upcoming.get(0).getName().fullName);
        assertEquals("Future Person", upcoming.get(1).getName().fullName);
    }

    @Test
    public void sortByName() {
        UniquePersonList unsortedUniquePersonList = new UniquePersonList();
        unsortedUniquePersonList.setPersons(Arrays.asList(GEORGE, BOB, ALICE));
        unsortedUniquePersonList.sortPersons("name");
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(Arrays.asList(ALICE, BOB, GEORGE));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);
    }

    @Test
    public void sortByPrice_singleOrderType() {
        UniquePersonList unsortedUniquePersonList = new UniquePersonList();
        unsortedUniquePersonList.setPersons(Arrays.asList(CARL, ALICE, BENSON));
        unsortedUniquePersonList.sortPersons("price");
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(Arrays.asList(CARL, BENSON, ALICE));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);
    }

    @Test
    public void sortByAmount_singleOrderType() {
        UniquePersonList unsortedUniquePersonList = new UniquePersonList();
        unsortedUniquePersonList.setPersons(Arrays.asList(CARL, BENSON, ALICE));
        unsortedUniquePersonList.sortPersons("amount");
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(Arrays.asList(ALICE, BENSON, CARL));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);
    }

    @Test
    public void sortByAmount_multipleOrderType() {
        UniquePersonList unsortedUniquePersonList = new UniquePersonList();
        unsortedUniquePersonList.setPersons(Arrays.asList(CINDY, ALICE, BENSON));
        unsortedUniquePersonList.sortPersons("amount");
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(Arrays.asList(ALICE, BENSON, CINDY));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);
    }

    @Test
    public void sortByPrice_multipleOrderType() {
        UniquePersonList unsortedUniquePersonList = new UniquePersonList();
        unsortedUniquePersonList.setPersons(Arrays.asList(CINDY, BENSON, ALICE));
        unsortedUniquePersonList.sortPersons("price");
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(Arrays.asList(BENSON, ALICE, CINDY));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);
    }

    @Test
    public void sortByNetworth() {
        UniquePersonList unsortedUniquePersonList = new UniquePersonList();
        unsortedUniquePersonList.setPersons(Arrays.asList(FIONA, BENSON, ALICE));
        unsortedUniquePersonList.sortPersons("networth");
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(Arrays.asList(ALICE, BENSON, FIONA));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);
    }

    @Test
    public void sortByDeadline() {
        UniquePersonList unsortedUniquePersonList = new UniquePersonList();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);

        Person personYesterday = new PersonBuilder()
                .withRemark("Old Event by/"
                        + yesterday.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Past Person")
                .build();

        Person personToday = new PersonBuilder()
                .withRemark("Today Event by/"
                        + today.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Today Person")
                .build();

        Person personTomorrow = new PersonBuilder()
                .withRemark("Tomorrow Event by/"
                        + tomorrow.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Future Person")
                .build();

        // Test all different dates
        unsortedUniquePersonList.setPersons(Arrays.asList(personTomorrow, personToday, personYesterday));
        unsortedUniquePersonList.sortPersons("deadline");
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.setPersons(Arrays.asList(personYesterday, personToday, personTomorrow));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);

        // Test all same dates
        Person personToday2 = new PersonBuilder()
                .withRemark("Today Event by/"
                        + today.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Today Person 2")
                .build();

        Person personToday3 = new PersonBuilder()
                .withRemark("Today Event by/"
                        + today.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .withName("Today Person 3")
                .build();

        unsortedUniquePersonList.setPersons(Arrays.asList(personToday, personToday2, personToday3));
        unsortedUniquePersonList.sortPersons("deadline");
        expectedUniquePersonList.setPersons(Arrays.asList(personToday, personToday2, personToday3));
        assertEquals(expectedUniquePersonList, unsortedUniquePersonList);

        // Test some remarks without deadlines
        Person personWithoutDeadline = new PersonBuilder()
                .withRemark("No Deadline Event")
                .withName("No Deadline Person")
                .build();

        unsortedUniquePersonList.setPersons(Arrays.asList(personWithoutDeadline, personToday, personYesterday));
        unsortedUniquePersonList.sortPersons("deadline");
        expectedUniquePersonList.setPersons(Arrays.asList(personYesterday, personToday, personWithoutDeadline));
    }
}
