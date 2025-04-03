package seedu.finclient.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.finclient.model.FinClient;
import seedu.finclient.model.ReadOnlyFinClient;
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

/**
 * Contains utility methods for populating {@code FinClient} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("", Optional.empty());
    public static final Company EMPTY_COMPANY = new Company();
    public static final Job EMPTY_JOB = new Job();
    public static final StockPlatform EMPTY_STOCK_PLATFORM = new StockPlatform();
    public static final Networth EMPTY_NEWWORTH = new Networth();

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), getPhoneList("87438807", "91234567"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Order("BUY 30 @ $5.50"),
                    new Remark("Loves cycling and photography.", Optional.empty()),
                    getTagSet("friends"),
                    new Company("Nimbus Technologies"),
                    new Job("Software Engineer"),
                    new StockPlatform("TradeHubX"),
                    new Networth("120000")),

            new Person(new Name("Bernice Yu"), getPhoneList("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Order("SELL 30 @ $5.70"),
                    new Remark("Volunteers at animal shelters.", Optional.empty()),
                    getTagSet("colleagues", "friends"),
                    new Company("Zenith Finance"),
                    new Job("Financial Analyst"),
                    new StockPlatform("WealthGo"),
                    new Networth("85000")),

            new Person(new Name("Charlotte Oliveiro"), getPhoneList("93210283", "81234567"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                   new Order("BUY 40 @ $5.20"), EMPTY_REMARK,
                    getTagSet("neighbours"),
                    EMPTY_COMPANY,
                    EMPTY_JOB,
                    new StockPlatform("BullBear App"),
                    new Networth("40000")),

            new Person(new Name("David Li"), getPhoneList("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Order("BUY 50 @ $5.60"),
                    new Remark("Looking to switch industries.", Optional.empty()),
                    getTagSet("family"),
                    new Company("BrightSpark Learning"),
                    new Job("Education Consultant"),
                    EMPTY_STOCK_PLATFORM,
                    new Networth("60000")),

            new Person(new Name("Irfan Ibrahim"), getPhoneList("92492021", "82345678"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Order("SELL 50 @ $5.50"),
                    new Remark("Active in coding meetups.", Optional.empty()),
                    getTagSet("classmates"),
                    new Company("Arcade Labs"),
                    new Job("UX Designer"),
                    new StockPlatform("AlphaInvest"),
                    EMPTY_NEWWORTH),

            new Person(new Name("Roy Balakrishnan"), getPhoneList("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Order("SELL 25 @ $5.20"),
                    new Remark("Enjoys photography.", Optional.empty()),
                    getTagSet("colleagues"),
                    EMPTY_COMPANY,
                    EMPTY_JOB,
                    EMPTY_STOCK_PLATFORM,
                    new Networth("22500"))
        };
    }


    public static ReadOnlyFinClient getSampleFinClient() {
        FinClient sampleFC = new FinClient();
        for (Person samplePerson : getSamplePersons()) {
            sampleFC.addPerson(samplePerson);
        }
        return sampleFC;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a {@code PhoneList} containing the list of phone numbers given.
     */
    public static PhoneList getPhoneList(String... phoneNumbers) {
        ArrayList<Phone> phoneArrayList = new ArrayList<>();
        for (String phone : phoneNumbers) {
            phoneArrayList.add(new Phone(phone));
        }
        return new PhoneList(phoneArrayList);
    }
}
