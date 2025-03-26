package seedu.finclient.logic.commands;

import static javafx.collections.FXCollections.observableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.finclient.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.finclient.commons.core.GuiSettings;
import seedu.finclient.commons.core.index.Index;
import seedu.finclient.logic.commands.exceptions.CommandException;
import seedu.finclient.model.Model;
import seedu.finclient.model.ReadOnlyFinClient;
import seedu.finclient.model.ReadOnlyUserPrefs;
import seedu.finclient.model.order.Order;
import seedu.finclient.model.person.Person;
import seedu.finclient.testutil.PersonBuilder;

public class OrderCommandTest {

    @Test
    public void execute_validIndex_success() throws Exception {
        // Model stub with exactly one person
        Person originalPerson = new PersonBuilder().withName("Alice").build();
        ModelStubWithOnePerson modelStub = new ModelStubWithOnePerson(originalPerson);

        // Create an OrderCommand with index = 1, and some example Order
        Index validIndex = Index.fromOneBased(1);
        Order sampleOrder = new Order(Order.OrderType.BUY, "5.50", 10);
        OrderCommand orderCommand = new OrderCommand(validIndex, sampleOrder);

        // Execute
        CommandResult result = orderCommand.execute(modelStub);

        // Check the feedback message is what we expect
        assertEquals(OrderCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Ensure the person that was saved has been "unhidden" and now has the new Order
        // The stub sets "editedPerson" to the newly created person in setPerson(...)
        Person editedPerson = modelStub.editedPerson;
        assertEquals(sampleOrder, editedPerson.getOrder(),
                "Person’s order should be the one specified in the OrderCommand.");

        // Also check that updateFilteredPersonList was called with PREDICATE_SHOW_ALL_PERSONS
        assertTrue(modelStub.updateListCalled,
                "updateFilteredPersonList(...) should be called once with the 'show all' predicate.");
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Model stub with exactly one person
        Person originalPerson = new PersonBuilder().withName("Alice").build();
        ModelStubWithOnePerson modelStub = new ModelStubWithOnePerson(originalPerson);

        // Using index=2 when there's only 1 person
        Index invalidIndex = Index.fromOneBased(2);
        Order sampleOrder = new Order(Order.OrderType.BUY, "5.50", 10);
        OrderCommand orderCommand = new OrderCommand(invalidIndex, sampleOrder);

        // Should throw CommandException
        assertThrows(CommandException.class,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> orderCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        Order orderA = new Order(Order.OrderType.BUY, "5.00", 10);
        Order orderB = new Order(Order.OrderType.SELL, "6.00", 20);

        OrderCommand orderCommandA = new OrderCommand(index1, orderA);
        OrderCommand orderCommandACopy = new OrderCommand(index1, orderA);
        OrderCommand orderCommandB = new OrderCommand(index2, orderB);

        // same object -> true
        assertTrue(orderCommandA.equals(orderCommandA));

        // same index and order -> true
        assertTrue(orderCommandA.equals(orderCommandACopy));

        // null -> false
        assertFalse(orderCommandA.equals(null));

        // different types -> false
        assertFalse(orderCommandA.equals(5));

        // different index -> false
        assertFalse(orderCommandA.equals(orderCommandB));

        // different order -> false
        OrderCommand orderCommandADifferentOrder = new OrderCommand(index1, orderB);
        assertFalse(orderCommandA.equals(orderCommandADifferentOrder));
    }

    // ====================
    // Model Stubs
    // ====================

    /**
     * A Model stub containing exactly one person.
     * It records calls to setPerson(...) and updateFilteredPersonList(...).
     */
    private static class ModelStubWithOnePerson implements Model {
        private final List<Person> internalList = new ArrayList<>();
        private Person originalPerson;
        private Person editedPerson;
        private boolean updateListCalled = false;

        ModelStubWithOnePerson(Person person) {
            internalList.add(person);
            this.originalPerson = person;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return observableList(internalList);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            // Basic check that the "target" is the correct original
            if (!internalList.contains(target)) {
                throw new AssertionError("setPerson called with person not in the list.");
            }
            this.editedPerson = editedPerson;
            // Replace in the internalList to simulate normal behavior
            internalList.set(internalList.indexOf(target), editedPerson);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // Check that the predicate is PREDICATE_SHOW_ALL_PERSONS
            // For simplicity, we just record that this method was called
            updateListCalled = true;
        }

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
        public void revealAllPersons() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Optional<Double> calculateClearingPrice() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void sortPersons(String criteria) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
