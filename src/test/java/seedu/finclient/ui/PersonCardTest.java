package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.finclient.model.person.Person;
import seedu.finclient.testutil.TypicalPersons;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
public class PersonCardTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        Person testPerson = TypicalPersons.ALICE;
        PersonCard personCard = new PersonCard(testPerson, 1);

        StackPane root = new StackPane(personCard.getRoot());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    Not working, for Siliang to fix
//    @Test
//    public void testPersonCardDisplaysCorrectInfo() {
//        Label phone = lookup("#phone").query();
//        Label address = lookup("#address").query();
//        Label email = lookup("#email").query();
//        Label remark = lookup("#remark").query();
//        assertEquals("Phone: 94351253", phone.getText());
//        assertEquals("Address: 123, Jurong West Ave 6, #08-111", address.getText());
//        assertEquals("Email: alice@example.com", email.getText());
//        assertEquals("Remark: Remarkable", remark.getText());
//    }
}
