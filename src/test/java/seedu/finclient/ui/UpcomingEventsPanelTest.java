package seedu.finclient.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.finclient.model.person.Person;
import seedu.finclient.testutil.PersonBuilder;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
public class UpcomingEventsPanelTest extends ApplicationTest {

    private UpcomingEventsPanel panel;

    @Override
    public void start(Stage stage) {
        Person p1 = new PersonBuilder()
                .withName("Alice")
                .withRemark("Meeting by/2025-04-01 15:00")
                .build();

        Person p2 = new PersonBuilder()
                .withName("Bob")
                .withRemark("Deadline by/2025-04-03 09:00")
                .build();

        panel = new UpcomingEventsPanel(List.of(p1, p2));
        Scene scene = new Scene(panel.getRoot(), 600, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void panel_displaysCorrectNumberOfEvents() {
        // This is a structural check
        assertEquals(2, panel.getEventsBox().getChildren().size());
    }

    @Test
    public void panel_displaysCorrectText() {
        VBox firstBox = (VBox) panel.getEventsBox().getChildren().get(0);
        Label timeLabel = (Label) firstBox.getChildren().get(0);
        Label titleLabel = (Label) firstBox.getChildren().get(1);

        assertThat(titleLabel.getText()).contains("Alice");
        assertThat(timeLabel.getText()).contains("2025-04-01 15:00");
    }
}
