package seedu.finclient.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents a list of unique phone numbers with constraints.
 */
public class PhoneList {

    /**
     * Error message if duplicate phone numbers are added.
     */
    public static final String MESSAGE_CONSTRAINTS = "Phone numbers should be unique";

    /**
     * Constraint message for maximum phone numbers.
     */
    public static final String SIZE_CONSTRAINTS = "There can only be a maximum of 3 numbers";

    public final ArrayList<Phone> phoneList;
    public final HashSet<Phone> phoneSet;

    /**
     * Constructs an empty PhoneList.
     */
    public PhoneList() {
        phoneList = new ArrayList<>();
        phoneSet = new HashSet<>();
    }

    /**
     * Constructs a PhoneList with given phones.
     *
     * @param phones The list of phone numbers.
     * @throws IllegalArgumentException if there are duplicate numbers.
     */
    public PhoneList(ArrayList<Phone> phones) {
        requireNonNull(phones);
        phoneList = new ArrayList<>();
        phoneSet = new HashSet<>();

        HashSet<Phone> phoneSetCopy = new HashSet<>(phones);
        checkArgument(phones.size() == phoneSetCopy.size(), MESSAGE_CONSTRAINTS);

        for (Phone phone : phones) {
            addPhone(phone);
        }
    }

    /**
     * Adds a phone number to the list.
     *
     * @param phone The phone number to add.
     * @throws IllegalArgumentException if the number already exists.
     */
    public void addPhone(Phone phone) {
        requireNonNull(phone);
        checkArgument(!phoneSet.contains(phone), MESSAGE_CONSTRAINTS);
        phoneList.add(phone);
        phoneSet.add(phone);
    }

    /**
     * Returns the list of phone numbers as strings.
     *
     * @return A list of phone number strings.
     */
    public ArrayList<String> getPhoneStringList() {
        ArrayList<String> phoneStringList = new ArrayList<>();
        for (Phone phone : phoneList) {
            phoneStringList.add(phone.value);
        }
        return phoneStringList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PhoneList)) {
            return false;
        }
        PhoneList otherPhoneList = (PhoneList) other;
        return new HashSet<>(phoneList).equals(new HashSet<>(otherPhoneList.phoneList));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < phoneList.size(); i++) {
            if (i == 0) {
                builder.append(phoneList.get(i).toString());
            } else {
                builder.append(", ").append(phoneList.get(i).toString());
            }
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return phoneList.hashCode();
    }
}
