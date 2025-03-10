package seedu.address.testutil;

import seedu.address.model.FinClient;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FinClient ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private FinClient finClient;

    public AddressBookBuilder() {
        finClient = new FinClient();
    }

    public AddressBookBuilder(FinClient finClient) {
        this.finClient = finClient;
    }

    /**
     * Adds a new {@code Person} to the {@code FinClient} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        finClient.addPerson(person);
        return this;
    }

    public FinClient build() {
        return finClient;
    }
}
