package seedu.finclient.ui;

import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
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
}
