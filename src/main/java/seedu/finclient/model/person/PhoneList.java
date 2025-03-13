package seedu.finclient.model.person;

import java.util.ArrayList;
import java.util.HashSet;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;

public class PhoneList {
    public final ArrayList<Phone> phoneList;
    public final HashSet<Phone> phoneSet;
    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should be unique";
    public static final String SIZE_CONSTRAINTS =
            "There can only be a maximum of 3 numbers";

    public PhoneList() {
        phoneList = new ArrayList<>();
        phoneSet = new HashSet<>();
    }

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

    public void addPhone(Phone phone) {
        requireNonNull(phone);
        checkArgument(!phoneSet.contains(phone), MESSAGE_CONSTRAINTS);
        phoneList.add(phone);
        phoneSet.add(phone);
    }

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
                builder.append(", " + phoneList.get(i).toString());
            }
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return phoneList.hashCode();
    }
}
