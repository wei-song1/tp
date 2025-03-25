package seedu.finclient.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.finclient.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.finclient.testutil.Assert.assertThrows;
import static seedu.finclient.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.finclient.commons.exceptions.IllegalValueException;
import seedu.finclient.model.person.Address;
import seedu.finclient.model.person.Email;
import seedu.finclient.model.person.Name;
import seedu.finclient.model.person.Phone;
import seedu.finclient.model.person.PhoneList;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final List<String> INVALID_PHONE = Arrays.asList("+651234");
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final List<String> VALID_PHONE = BENSON.getPhoneList().getPhoneStringList();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_JOB = BENSON.getJob().toString();
    private static final String VALID_STOCK_PLATFORM = BENSON.getStockPlatform().toString();
    private static final String VALID_NETWORTH = BENSON.getNetworth().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PhoneList.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_REMARK,
                VALID_ADDRESS, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_REMARK, VALID_TAGS, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, invalidTags, VALID_COMPANY, VALID_JOB, VALID_STOCK_PLATFORM, VALID_NETWORTH,
                false);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
