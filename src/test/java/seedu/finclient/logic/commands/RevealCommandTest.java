package seedu.finclient.logic.commands;

import static javafx.collections.FXCollections.observableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.testutil.Assert.assertThrows;
import static seedu.finclient.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.finclient.commons.core.GuiSettings;
import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.Messages;
import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.Model;
import seedu.finclient.model.ReadOnlyFinClient;
import seedu.finclient.model.ReadOnlyUserPrefs;
import seedu.finclient.model.person.NameContainsKeywordsPredicate;
import seedu.finclient.model.person.Person;

public class RevealCommandTest {

    @Test
    public void execute_revealAll_success() throws Exception {
        ModelStubRevealAll modelStub = new ModelStubRevealAll();
        RevealCommand revealAllCommand = new RevealCommand();

        CommandResult result = revealAllCommand.execute(modelStub);

        assertEquals(RevealCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(modelStub.revealAllCalled, "revealAllPersons() should have been called on the model.");
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        // The model stub’s list has exactly 1 person: ALICE
        ModelStubWithOnePerson modelStub = new ModelStubWithOnePerson(ALICE);
        RevealCommand revealByIndex = new RevealCommand(Index.fromOneBased(1));

        CommandResult result = revealByIndex.execute(modelStub);

        assertEquals(RevealCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(modelStub.personRevealed == ALICE,
                "Should reveal the 1st person in the model’s filtered list (ALICE).");
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // The model stub’s list has exactly 1 person: ALICE
        ModelStubWithOnePerson modelStub = new ModelStubWithOnePerson(ALICE);
        // Attempting to reveal the 2nd person (index 2) should fail
        RevealCommand revealByIndex = new RevealCommand(Index.fromOneBased(2));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ()
                -> revealByIndex.execute(modelStub));
    }

    @Test
    public void execute_revealByName_success() throws Exception {
        ModelStubRevealByPredicate modelStub = new ModelStubRevealByPredicate();
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        RevealCommand revealByPredicate = new RevealCommand(predicate);

        CommandResult result = revealByPredicate.execute(modelStub);

        assertEquals(RevealCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        // The stub just tracks that revealPerson(Predicate) was called with the same predicate
        assertEquals(predicate, modelStub.predicateUsed,
                "Predicate should match the one passed to the model’s revealPerson(Predicate).");
    }

    @Test
    public void equals() {
        RevealCommand revealAll = new RevealCommand();
        RevealCommand revealAliceIndex = new RevealCommand(Index.fromOneBased(1));
        RevealCommand revealBobIndex = new RevealCommand(Index.fromOneBased(2));
        RevealCommand revealAliceName = new RevealCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        RevealCommand revealBobName = new RevealCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Bob")));

        // same object -> returns true
        assertTrue(revealAll.equals(revealAll));
        // same values -> returns true
        RevealCommand revealAllCopy = new RevealCommand();
        assertTrue(revealAll.equals(revealAllCopy));

        // different types -> returns false
        assertFalse(revealAll.equals(5));

        // null -> returns false
        assertFalse(revealAll.equals(null));

        // different index -> false
        assertFalse(revealAliceIndex.equals(revealBobIndex));

        // different name predicate -> false
        assertFalse(revealAliceName.equals(revealBobName));

        // same index -> true
        RevealCommand revealAliceIndexCopy = new RevealCommand(Index.fromOneBased(1));
        assertTrue(revealAliceIndex.equals(revealAliceIndexCopy));

        // same name predicate -> true
        RevealCommand revealAliceNameCopy = new RevealCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertTrue(revealAliceName.equals(revealAliceNameCopy));
    }

    //====================
    // Model Stubs below
    //====================

    /**
     * A Model stub that always calls revealAllPersons().
     */
    private static class ModelStubRevealAll implements Model {
        private boolean revealAllCalled = false;

        public boolean getRevealAllCalled() {
            return revealAllCalled;
        }

        @Override
        public void revealAllPersons() {
            revealAllCalled = true;
        }

        // The rest of Model's methods throw AssertionError
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getFinClientFilePath() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setFinClientFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setFinClient(ReadOnlyFinClient newData) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyFinClient getFinClient() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hidePerson(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hidePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealPerson(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hideAllPersons() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPersons(String criteria) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub containing exactly one person. We can reveal them by index.
     */
    private static class ModelStubWithOnePerson implements Model {
        private final List<Person> internalList = new ArrayList<>();
        private Person personRevealed = null;

        ModelStubWithOnePerson(Person person) {
            internalList.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return observableList(internalList);
        }

        // The rest throw AssertionError
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getFinClientFilePath() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setFinClientFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setFinClient(ReadOnlyFinClient newData) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyFinClient getFinClient() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hidePerson(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hidePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealPerson(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealPerson(Person person) {
            this.personRevealed = person;
        }
        @Override
        public void hideAllPersons() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealAllPersons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPersons(String criteria) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that records when revealPerson(Predicate) is called.
     */
    private static class ModelStubRevealByPredicate implements Model {
        private Predicate<Person> predicateUsed = null;

        // The rest throw AssertionError
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getFinClientFilePath() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setFinClientFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setFinClient(ReadOnlyFinClient newData) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyFinClient getFinClient() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hidePerson(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hidePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealPerson(Predicate<Person> predicate) {
            predicateUsed = predicate;
        }
        @Override
        public void revealPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hideAllPersons() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealAllPersons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPersons(String criteria) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
