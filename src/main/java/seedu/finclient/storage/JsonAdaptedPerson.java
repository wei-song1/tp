package seedu.finclient.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.finclient.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String email;
    private final String address;
    private final String order;
    private final String remark;
    private final String remarkTimestamp;
    private final List<String> phones;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isHidden;
    private final String company;
    private final String job;
    private final String stockPlatform;
    private final String networth;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phones") List<String> phones,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("order") String order,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("remarkTimestamp") String remarkTimestamp,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("company") String company,
                             @JsonProperty("job") String job,
                             @JsonProperty("stockplatform") String stockPlatform,
                             @JsonProperty("networth") String networth,
                             @JsonProperty("isHidden") boolean isHidden) {
        this.name = name;
        this.phones = (phones != null) ? new ArrayList<>(phones) : new ArrayList<>();
        this.email = email;
        this.address = address;
        this.order = order;
        this.remark = remark;
        this.remarkTimestamp = remarkTimestamp;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isHidden = isHidden;
        this.company = company;
        this.job = job;
        this.stockPlatform = stockPlatform;
        this.networth = networth;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        isHidden = source.getIsHidden();
        source.setUnhidden();
        name = source.getName().fullName;
        phones = source.getPhoneList().phoneList.stream()
                .map(Phone::toString)
                .collect(Collectors.toList());
        email = source.getEmail().value;
        address = source.getAddress().value;
        order = source.getOrder().toString();
        remark = source.getRemark().value;
        remarkTimestamp = source.getRemark().getTimestamp()
                .map(LocalDateTime::toString)
                .orElse(null);
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        if (isHidden) {
            source.setHidden();
        }
        company = source.getCompany().value;
        job = source.getJob().value;
        stockPlatform = source.getStockPlatform().value;
        networth = source.getNetworth().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phones == null || phones.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "PhoneList"));
        }

        PhoneList modelPhoneList = new PhoneList();
        for (String phone : phones) {
            if (!Phone.isValidPhone(phone)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            modelPhoneList.addPhone(new Phone(phone));
        }

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Order modelOrder;

        if (order == null) {
            modelOrder = new Order("NONE");
        } else {
            modelOrder = new Order(order);
        }

        final Remark modelRemark;
        final Optional<LocalDateTime> modelTimestamp;

        if (remarkTimestamp == null || remarkTimestamp.isEmpty()) {
            modelTimestamp = Optional.empty();
        } else {
            modelTimestamp = Optional.of(LocalDateTime.parse(remarkTimestamp));
        }

        modelRemark = new Remark(remark == null ? "" : remark, modelTimestamp);
        final boolean modelIsHidden = isHidden;

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Company modelCompany = company.isEmpty() ? new Company() : new Company(company);

        final Job modelJob = job.isEmpty() ? new Job() : new Job(job);

        final StockPlatform modelStockPlatform = stockPlatform.isEmpty()
                ? new StockPlatform()
                : new StockPlatform(stockPlatform);

        final Networth modelNetworth = networth.isEmpty()
                ? new Networth()
                : new Networth(networth);

        return new Person(modelName, modelPhoneList, modelEmail, modelAddress, modelOrder, modelRemark, modelTags,
                modelCompany, modelJob, modelStockPlatform, modelNetworth, isHidden);
    }
}
