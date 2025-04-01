package seedu.finclient.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.finclient.model.order.Order;
import seedu.finclient.model.person.Address;
import seedu.finclient.model.person.Company;
import seedu.finclient.model.person.Email;
import seedu.finclient.model.person.Job;
import seedu.finclient.model.person.Name;
import seedu.finclient.model.person.Networth;
import seedu.finclient.model.person.Person;
import seedu.finclient.model.person.Phone;
import seedu.finclient.model.person.PhoneList;
import seedu.finclient.model.person.Remark;
import seedu.finclient.model.person.StockPlatform;
import seedu.finclient.model.tag.Tag;
import seedu.finclient.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ORDER = "BUY 10 @ $5.50";
    public static final String DEFAULT_REMARK = "Remarkable";
    public static final String DEFAULT_COMPANY = "NUS";
    public static final String DEFAULT_JOB = "Student";
    public static final String DEFAULT_STOCK_PLATFORM = "eduRec";
    public static final String DEFAULT_NETWORTH = "< $100k";

    private Name name;
    private PhoneList phoneList;
    private Email email;
    private Address address;
    private Order order;
    private Remark remark;
    private Set<Tag> tags;
    private Company company;
    private Job job;
    private StockPlatform stockPlatform;
    private Networth networth;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phoneList = new PhoneList();
        phoneList.addPhone(new Phone(DEFAULT_PHONE));
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        order = new Order(DEFAULT_ORDER);
        remark = new Remark(DEFAULT_REMARK, Optional.empty());
        tags = new HashSet<>();
        company = new Company(DEFAULT_COMPANY);
        job = new Job(DEFAULT_JOB);
        stockPlatform = new StockPlatform(DEFAULT_STOCK_PLATFORM);
        networth = new Networth(DEFAULT_NETWORTH);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phoneList = personToCopy.getPhoneList();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        order = personToCopy.getOrder();
        remark = personToCopy.getRemark();
        tags = new HashSet<>(personToCopy.getTags());
        company = personToCopy.getCompany();
        job = personToCopy.getJob();
        stockPlatform = personToCopy.getStockPlatform();
        networth = personToCopy.getNetworth();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        Phone tempPhone = new Phone(phone);
        phoneList = new PhoneList();
        phoneList.addPhone(tempPhone);
        return this;
    }

    /**
     * Sets the {@code PhoneList} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String... phones) {
        ArrayList<Phone> phoneArrayList = new ArrayList<>();

        for (String phone : phones) {
            phoneArrayList.add(new Phone(phone)); // Convert each String to a Phone object
        }

        this.phoneList = new PhoneList(phoneArrayList); // Use PhoneList constructor
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Order} of the {@code Person} that we are building.
     */
    public PersonBuilder withOrder(String order) {
        this.order = new Order(order);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark, Optional<LocalDateTime> timestamp) {
        this.remark = new Remark(remark, timestamp);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the empty {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany() {
        this.company = new Company();
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code Person} that we are building.
     */
    public PersonBuilder withJob(String job) {
        this.job = new Job(job);
        return this;
    }
    /**
     * Sets the empty {@code Job} of the {@code Person} that we are building.
     */
    public PersonBuilder withJob() {
        this.job = new Job();
        return this;
    }

    /**
     * Sets the {@code StockPlatform} of the {@code Person} that we are building.
     */
    public PersonBuilder withStockPlatform(String stockPlatform) {
        this.stockPlatform = new StockPlatform(stockPlatform);
        return this;
    }

    /**
     * Sets the empty {@code StockPlatform} of the {@code Person} that we are building.
     */
    public PersonBuilder withStockPlatform() {
        this.stockPlatform = new StockPlatform();
        return this;
    }

    /**
     * Sets the {@code Networth} of the {@code Person} that we are building.
     */
    public PersonBuilder withNetworth(String networth) {
        this.networth = new Networth(networth);
        return this;
    }

    /**
     * Sets the empty {@code StockPlatform} of the {@code Person} that we are building.
     */
    public PersonBuilder withNetworth() {
        this.networth = new Networth();
        return this;
    }

    public Person build() {
        return new Person(name, phoneList, email, address, order, remark, tags, company, job, stockPlatform, networth);
    }

}
