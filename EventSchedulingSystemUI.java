import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Main Class for Event Scheduling System
public class EventSchedulingSystemUI {

    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JTextField eventNameField, eventDateField, eventTimeField, eventLocationField;
    private static JTextArea eventDescriptionArea, eventsListArea;
    private static JTextField attendeeNameField;

    public static void main(String[] args) {
        // Initialize JFrame and CardLayout
        frame = new JFrame("Event Scheduling System");
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create and add Register Event Panel
        JPanel registerEventPanel = createRegisterEventPanel();
        frame.getContentPane().add(registerEventPanel, "Register Event");

        // Create and add View Events Panel
        JPanel viewEventsPanel = createViewEventsPanel();
        frame.getContentPane().add(viewEventsPanel, "View Events");

        // Create and add Register for Event Panel
        JPanel registerForEventPanel = createRegisterForEventPanel();
        frame.getContentPane().add(registerForEventPanel, "Register For Event");

        // Show the Register Event Panel initially
        cardLayout.show(frame.getContentPane(), "Register Event");

        // Display frame
        frame.setVisible(true);
    }

    // Method to create Register Event Panel
    private static JPanel createRegisterEventPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Event Name
        panel.add(new JLabel("Event Name:"));
        eventNameField = new JTextField();
        panel.add(eventNameField);

        // Event Description
        panel.add(new JLabel("Event Description:"));
        eventDescriptionArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(eventDescriptionArea));

        // Event Date
        panel.add(new JLabel("Event Date:"));
        eventDateField = new JTextField();
        panel.add(eventDateField);

        // Event Time
        panel.add(new JLabel("Event Time:"));
        eventTimeField = new JTextField();
        panel.add(eventTimeField);

        // Event Location
        panel.add(new JLabel("Event Location:"));
        eventLocationField = new JTextField();
        panel.add(eventLocationField);

        JButton registerEventButton = new JButton("Register Event");
        panel.add(registerEventButton);

        // Event Registration ActionListener
        registerEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = eventNameField.getText();
                String description = eventDescriptionArea.getText();
                String date = eventDateField.getText();
                String time = eventTimeField.getText();
                String location = eventLocationField.getText();

                // Validate input fields
                if (name.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled!");
                    return;
                }

                // Create and register new event
                Event newEvent = new Event(name, description, date, time, location);
                EventData.addEvent(newEvent);

                // Clear input fields after registration
                eventNameField.setText("");
                eventDescriptionArea.setText("");
                eventDateField.setText("");
                eventTimeField.setText("");
                eventLocationField.setText("");

                JOptionPane.showMessageDialog(frame, "Event Registered Successfully!");
            }
        });

        // Switch to View Events Panel
        JButton switchToViewEventsButton = new JButton("View Events");
        switchToViewEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "View Events");
            }
        });
        panel.add(switchToViewEventsButton);

        return panel;
    }

    // Method to create View Events Panel
    private static JPanel createViewEventsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        eventsListArea = new JTextArea(10, 40);
        eventsListArea.setEditable(false);
        panel.add(new JScrollPane(eventsListArea), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Events");
        panel.add(refreshButton, BorderLayout.SOUTH);

        // Refresh Button ActionListener
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshEventList();
            }
        });

        // Switch to Register for Event Panel
        JButton switchToRegisterForEventButton = new JButton("Register For Event");
        switchToRegisterForEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Register For Event");
            }
        });
        panel.add(switchToRegisterForEventButton, BorderLayout.NORTH);

        return panel;
    }

    // Method to refresh the list of events in View Events Panel
    private static void refreshEventList() {
        eventsListArea.setText(""); // Clear existing list

        for (Event event : EventData.getEvents()) {
            eventsListArea.append("Event: " + event.getName() + "\n");
            eventsListArea.append("Description: " + event.getDescription() + "\n");
            eventsListArea.append("Date: " + event.getDate() + "\n");
            eventsListArea.append("Time: " + event.getTime() + "\n");
            eventsListArea.append("Location: " + event.getLocation() + "\n");
            eventsListArea.append("Attendees: " + event.getAttendees().size() + "\n");
            eventsListArea.append("------------------------------\n");
        }
    }

    // Method to create Register For Event Panel
    private static JPanel createRegisterForEventPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Attendee Name
        panel.add(new JLabel("Your Name:"));
        attendeeNameField = new JTextField();
        panel.add(attendeeNameField);

        // Event Name (for which to register)
        panel.add(new JLabel("Event Name:"));
        JTextField eventNameForRegistrationField = new JTextField();
        panel.add(eventNameForRegistrationField);

        JButton registerForEventButton = new JButton("Register For Event");
        panel.add(registerForEventButton);

        // Register for Event ActionListener
        registerForEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attendeeName = attendeeNameField.getText();
                String eventName = eventNameForRegistrationField.getText();

                // Validate input fields
                if (attendeeName.isEmpty() || eventName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your name and select an event.");
                    return;
                }

                // Register the attendee for the event
                EventData.registerForEvent(eventName, attendeeName);
                JOptionPane.showMessageDialog(frame, "Registered successfully for the event!");

                // Clear fields after registration
                attendeeNameField.setText("");
                eventNameForRegistrationField.setText("");
            }
        });

        // Switch to View Events Panel
        JButton switchToViewEventsButton = new JButton("View Events");
        switchToViewEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "View Events");
            }
        });
        panel.add(switchToViewEventsButton);

        return panel;
    }
}

// Event class to hold event data
class Event {
    private String name;
    private String description;
    private String date;
    private String time;
    private String location;
    private List<String> attendees;

    public Event(String name, String description, String date, String time, String location) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.attendees = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    // Register attendee to the event
    public void registerAttendee(String attendeeName) {
        attendees.add(attendeeName);
    }
}

// EventData class to store and manage events
class EventData {
    private static List<Event> events = new ArrayList<>();

    public static void addEvent(Event event) {
        events.add(event);
    }

    public static List<Event> getEvents() {
        return events;
    }

    public static void registerForEvent(String eventName, String attendeeName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                event.registerAttendee(attendeeName);
                break;
            }
        }
    }
}