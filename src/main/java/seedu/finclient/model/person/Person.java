package seedu.finclient.model.person;

import static seedu.finclient.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.finclient.commons.util.ToStringBuilder;
import seedu.finclient.model.order.Order;
import seedu.finclient.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null,
 * field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final PhoneList phoneList;
    private final Email email;

    // Data fields
    private final Address address;
    private final Order order;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    // Hidden detail flag
    private boolean isHidden = false;

    // Optional fields
    private final Company company;
    private final Job job;
    private final StockPlatform stockPlatform;
    private final Networth networth;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, PhoneList phoneList, Email email, Address address, Order order, Remark remark,
                  Set<Tag> tags, Company company, Job job, StockPlatform stockPlatform, Networth networth) {
        requireAllNonNull(name, phoneList, email, address, tags, company, job, stockPlatform, networth);

        this.name = name;
        this.phoneList = phoneList;
        this.email = email;
        this.address = address;
        this.order = order;
        this.remark = remark;
        this.tags.addAll(tags);

        this.company = company;
        this.job = job;
        this.stockPlatform = stockPlatform;
        this.networth = networth;
    }

    /**
     * Alternate constructor to allow hiding of details.
     */
    public Person(Name name, PhoneList phoneList, Email email, Address address, Order order, Remark remark,
                  Set<Tag> tags, Company company, Job job, StockPlatform stockPlatform, Networth networth,
                  boolean isHidden) {
        requireAllNonNull(name, phoneList, email, address, tags, company, job, stockPlatform, networth);

        this.name = name;
        this.phoneList = phoneList;
        this.email = email;
        this.address = address;
        this.order = order;
        this.remark = remark;
        this.tags.addAll(tags);

        this.isHidden = isHidden;

        this.company = company;
        this.job = job;
        this.stockPlatform = stockPlatform;
        this.networth = networth;
    }

    /**
     * Alternate constructor to allow default NONE order.
     */
    public Person(Name name, PhoneList phoneList, Email email, Address address, Remark remark, Set<Tag> tags,
                  Company company, Job job, StockPlatform stockPlatform, Networth networth) {

        requireAllNonNull(name, phoneList, email, address, tags, company, job, stockPlatform, networth);
        this.name = name;
        this.phoneList = phoneList;
        this.email = email;
        this.address = address;
        this.order = new Order("NONE");
        this.remark = remark;
        this.tags.addAll(tags);

        this.company = company;
        this.job = job;
        this.stockPlatform = stockPlatform;
        this.networth = networth;
    }

    public void setHidden() {
        this.isHidden = true;
    }

    public void setUnhidden() {
        this.isHidden = false;
    }

    public Name getName() {
        return name;
    }

    public PhoneList getPhoneList() {
        return isHidden ? new PhoneList(new ArrayList<>(Arrays.asList(new Phone("00000000")))) : phoneList;
    }

    public Email getEmail() {
        return isHidden ? new Email("hidden@example.com") : email;
    }

    public Address getAddress() {
        return isHidden ? new Address("Hidden") : address;
    }

    public Order getOrder() {
        return isHidden ? new Order(Order.OrderType.HIDDEN, "1", 1) : order;
    }

    public Remark getRemark() {
        return isHidden ? new Remark("Sensitive details are hidden", Optional.empty()) : remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return isHidden ? Collections.emptySet() : Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the person is hidden.
     */
    public boolean getIsHidden() {
        return isHidden;
    }

    public Company getCompany() {
        return isHidden ? new Company("Hidden") : company;
    }

    public Job getJob() {
        return isHidden ? new Job("Hidden") : job;
    }

    public StockPlatform getStockPlatform() {
        return isHidden ? new StockPlatform("Hidden") : stockPlatform;
    }

    public Networth getNetworth() {
        return isHidden ? new Networth("Hidden") : networth;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phoneList.equals(otherPerson.phoneList)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && remark.equals(otherPerson.remark)
                && tags.equals(otherPerson.tags)
                && company.equals(otherPerson.company)
                && job.equals(otherPerson.job)
                && stockPlatform.equals(otherPerson.stockPlatform)
                && networth.equals(otherPerson.networth);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phoneList, email, address, remark, tags, company, job, stockPlatform,
                networth, isHidden);
    }

    /**
     * Compares this person with another person based on the given criteria.
     */
    public int compareTo(Person other, String criteria) {
        requireAllNonNull(other, criteria);
        return switch (criteria) {
        case "name" -> name.toString().compareTo(other.name.toString());
        case "price" -> order.compareTo(other.order, "price");
        case "amount" -> order.compareTo(other.order, "amount");
        case "networth" -> networth.compareTo(other.networth);
        default -> 0;
        };
    }

    @Override
    public String toString() {
        // If the person is hidden, return only non-sensitive details.
        if (isHidden) {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("details", "Sensitive details are hidden")
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phones", phoneList)
                    .add("email", email)
                    .add("address", address)
                    .add("order", order)
                    .add("remark", remark)
                    .add("tags", tags)
                    .add("company", company)
                    .add("job", job)
                    .add("stockPlatform", stockPlatform)
                    .add("networth", networth)
                    .toString();
        }
    }
}
