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
import java.util.Optional;
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

public class HideCommandTest {

    @Test
    public void execute_hideAll_success() throws Exception {
        ModelStubHideAll modelStub = new ModelStubHideAll();
        HideCommand hideAllCommand = new HideCommand();

        CommandResult result = hideAllCommand.execute(modelStub);

        assertEquals(HideCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(modelStub.hideAllCalled, "hideAllPersons() should have been called on the model.");
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        // The model stub’s list has exactly 1 person: ALICE
        ModelStubWithOnePerson modelStub = new ModelStubWithOnePerson(ALICE);
        HideCommand hideByIndex = new HideCommand(Index.fromOneBased(1));

        CommandResult result = hideByIndex.execute(modelStub);

        assertEquals(HideCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(modelStub.personHidden == ALICE,
                "Should hide the 1st person in the model’s filtered list (ALICE).");
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // The model stub’s list has exactly 1 person: ALICE
        ModelStubWithOnePerson modelStub = new ModelStubWithOnePerson(ALICE);
        // Attempting to hide the 2nd person (index 2) should fail
        HideCommand hideByIndex = new HideCommand(Index.fromOneBased(2));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ()
                -> hideByIndex.execute(modelStub));
    }

    @Test
    public void execute_hideByName_success() throws Exception {
        ModelStubHideByPredicate modelStub = new ModelStubHideByPredicate();
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        HideCommand hideByPredicate = new HideCommand(predicate);

        CommandResult result = hideByPredicate.execute(modelStub);

        assertEquals(HideCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        // The stub just tracks that hidePerson(Predicate) was called with the same predicate
        assertEquals(predicate, modelStub.predicateUsed,
                "Predicate should match the one passed to the model’s hidePerson(Predicate).");
    }

    @Test
    public void equals() {
        HideCommand hideAll = new HideCommand();
        HideCommand hideAliceIndex = new HideCommand(Index.fromOneBased(1));
        HideCommand hideBobIndex = new HideCommand(Index.fromOneBased(2));
        HideCommand hideAliceName = new HideCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        HideCommand hideBobName = new HideCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Bob")));

        // same object -> returns true
        assertTrue(hideAll.equals(hideAll));

        // same values -> returns true
        HideCommand hideAllCopy = new HideCommand();
        assertTrue(hideAll.equals(hideAllCopy));

        // different types -> returns false
        assertFalse(hideAll.equals(5));

        // null -> returns false
        assertFalse(hideAll.equals(null));

        // different index -> false
        assertFalse(hideAliceIndex.equals(hideBobIndex));

        // different name predicate -> false
        assertFalse(hideAliceName.equals(hideBobName));

        // same index -> true
        HideCommand hideAliceIndexCopy = new HideCommand(Index.fromOneBased(1));
        assertTrue(hideAliceIndex.equals(hideAliceIndexCopy));

        // same name predicate -> true
        HideCommand hideAliceNameCopy = new HideCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertTrue(hideAliceName.equals(hideAliceNameCopy));
    }

    //====================
    // Model Stubs below
    //====================

    /**
     * A Model stub that always calls hideAllPersons().
     */
    private static class ModelStubHideAll implements Model {
        private boolean hideAllCalled = false;

        @Override
        public void hideAllPersons() {
            hideAllCalled = true;
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
        public boolean hasPerson(Predicate<Person> predicate) {
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
        public void revealAllPersons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Double> calculateClearingPrice() {
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
        public List<Person> getUpcomingPersons(int count) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPersons(String criteria) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub containing exactly one person. We can hide them by index.
     */
    private static class ModelStubWithOnePerson implements Model {
        private final List<Person> internalList = new ArrayList<>();
        private Person personHidden = null;

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
        public boolean hasPerson(Predicate<Person> predicate) {
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
            this.personHidden = person;
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
        public void revealAllPersons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Double> calculateClearingPrice() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Person> getUpcomingPersons(int count) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPersons(String criteria) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that records when hidePerson(Predicate) is called.
     */
    private static class ModelStubHideByPredicate implements Model {
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
        public boolean hasPerson(Predicate<Person> predicate) {
            return true; // Always returns true for the stub
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
        public void revealPerson(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void revealPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void hidePerson(Predicate<Person> predicate) {
            predicateUsed = predicate;
        }
        @Override
        public void hidePerson(Person person) {
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
        public Optional<Double> calculateClearingPrice() {
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
        public List<Person> getUpcomingPersons(int count) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPersons(String criteria) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
