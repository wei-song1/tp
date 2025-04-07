package seedu.finclient.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.finclient.model.person.Person;

/**
 * A UI component that displays the upcoming events sorted by timestamp.
 */
public class UpcomingEventsPanel extends UiPart<Region> {
    private static final String FXML = "UpcomingEventsPanel.fxml";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML private HBox upcomingContainer;
    @FXML private VBox dateBox;
    @FXML private HBox eventsBox;
    @FXML private Label dayOfWeekLabel;
    @FXML private Label dateLabel;

    /**
     * Creates a {@code UpcomingEventsPanel} with the given upcoming persons.
     */
    public UpcomingEventsPanel(List<Person> upcomingPersons) {
        super(FXML);

        LocalDate today = LocalDate.now();
        dayOfWeekLabel.setText(today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase());
        dateLabel.setText(today.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)));
        for (Person person : upcomingPersons) {
            String name = person.getName().fullName;
            String content = person.getRemark().value;
            String time = person.getRemark().getTimestamp().get().format(FORMATTER);

            VBox eventBox = new VBox();
            eventBox.setSpacing(4);
            eventBox.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 10; -fx-background-radius: 8;");
            eventBox.setMaxWidth(Double.MAX_VALUE);
            eventBox.setMinWidth(0);
            HBox.setHgrow(eventBox, Priority.ALWAYS);

            Label timeLabel = new Label(time);
            timeLabel.getStyleClass().add("event-time-label");
            Label titleLabel;
            if (content.isEmpty()) {
                titleLabel = new Label(name + " - " + "New Event");
            } else {
                titleLabel = new Label(name + " - " + content);
                titleLabel.getStyleClass().add("event-title-label");
            }
            titleLabel.getStyleClass().add("event-title-label");
            titleLabel.setWrapText(true);
            titleLabel.setMaxWidth(Double.MAX_VALUE);
            titleLabel.prefWidthProperty().bind(eventBox.widthProperty().subtract(20));

            eventBox.getChildren().addAll(timeLabel, titleLabel);

            eventsBox.getChildren().add(eventBox);
        }
    }

    HBox getEventsBox() {
        return eventsBox;
    }
}
