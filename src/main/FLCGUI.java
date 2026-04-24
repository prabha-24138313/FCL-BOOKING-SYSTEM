package main;

import model.*;
import model.Timetable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graphical User Interface for the FLC Booking System.
 *
 * <p>This class provides a Swing-based GUI for:
 * <ul>
 *   <li>Viewing and searching lessons</li>
 *   <li>Booking lessons for members</li>
 *   <li>Generating reports</li>
 *   <li>Adding new members</li>
 * </ul>
 * </p>
 *
 * @author FLC Booking System
 * @version 1.0
 */
public class FLCGUI extends JFrame {

    /** The timetable containing all lessons */
    private Timetable timetable;

    /** List of all members */
    private List<Member> members;

    /** Counter for generating new member IDs */
    private int memberIdCounter = 11;

    // GUI Components
    /** Table displaying lesson information */
    private JTable lessonTable;

    /** Table model for lesson table */
    private DefaultTableModel lessonTableModel;

    /** Dropdown for day selection */
    private JComboBox<String> dayComboBox;

    /** Dropdown for exercise selection */
    private JComboBox<String> exerciseComboBox;
    private JComboBox<String> rExerciseComboBox;

    private JComboBox<String> reviewComboBox;

    /** Dropdown for member selection */
    private JComboBox<Member> memberComboBox;

    /** Text area for output messages */
    private JTextArea outputArea;

    /**
     * Creates a new FLCGUI and initializes the components.
     */
    public FLCGUI() {
        // Initialize data structures
        timetable = new Timetable();
        members = new ArrayList<>();

        // Initialize sample data
        initializeData();

        // Create and setup GUI components
        initComponents();
    }

    /**
     * Initializes the system with sample data:
     * Creates 4 exercise types
     * Creates 48 lessons (8 weeks x 6 lessons per week)
     * Creates 10 sample members
     */
    private void initializeData() {
        try {
            // Create exercises
            Exercise yoga = new Exercise("Yoga", 10);
            Exercise zumba = new Exercise("Zumba", 12);
            Exercise aquacise = new Exercise("Aquacise", 15);
            Exercise boxfit = new Exercise("Box Fit", 14);

            Exercise[] exercises = {yoga, zumba, aquacise, boxfit};

            // Create 48 lessons (8 weeks x 6 lessons per week)
            int id = 1;
            for (int week = 1; week <= 8; week++) {
                timetable.addLesson(new Lesson(id++, exercises[week % 4], DayType.SATURDAY, TimeSlot.MORNING));
                timetable.addLesson(new Lesson(id++, exercises[(week + 1) % 4], DayType.SATURDAY, TimeSlot.AFTERNOON));
                timetable.addLesson(new Lesson(id++, exercises[(week + 2) % 4], DayType.SATURDAY, TimeSlot.EVENING));
                timetable.addLesson(new Lesson(id++, exercises[(week + 3) % 4], DayType.SUNDAY, TimeSlot.MORNING));
                timetable.addLesson(new Lesson(id++, exercises[week % 4], DayType.SUNDAY, TimeSlot.AFTERNOON));
                timetable.addLesson(new Lesson(id++, exercises[(week + 1) % 4], DayType.SUNDAY, TimeSlot.EVENING));
            }

            // Create sample members
            members.add(new Member(1, "Alice"));
            members.add(new Member(2, "Bob"));
            members.add(new Member(3, "Carol"));
            members.add(new Member(4, "David"));
            members.add(new Member(5, "Emma"));
            members.add(new Member(6, "Frank"));
            members.add(new Member(7, "Grace"));
            members.add(new Member(8, "Henry"));
            members.add(new Member(9, "Isla"));
            members.add(new Member(10, "Jack"));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Error initializing data: " + e.getMessage(),
                    "Initialization Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initializes and lays out all GUI components.
     */
    private void initComponents() {
        // Set up the main frame
        setTitle("FLC Booking System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel titleLabel = new JLabel("FLC Booking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left panel - Controls
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(250, 0));

        // Search by Day Panel
        JPanel dayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dayPanel.setBorder(BorderFactory.createTitledBorder("Search by Day"));
        dayComboBox = new JComboBox<>(new String[]{"All", "SATURDAY", "SUNDAY"});
        JButton daySearchBtn = new JButton("Search");
        daySearchBtn.addActionListener(e -> searchByDay());
        dayPanel.add(dayComboBox);
        dayPanel.add(daySearchBtn);
        leftPanel.add(dayPanel);

        // Search by Exercise Panel
        JPanel exercisePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exercisePanel.setBorder(BorderFactory.createTitledBorder("Search by Exercise"));
        exerciseComboBox = new JComboBox<>(new String[]{"All", "Yoga", "Zumba", "Aquacise", "Box Fit"});
        JButton exerciseSearchBtn = new JButton("Search");
        exerciseSearchBtn.addActionListener(e -> searchByExercise());
        exercisePanel.add(exerciseComboBox);
        exercisePanel.add(exerciseSearchBtn);
        leftPanel.add(exercisePanel);

        // Booking Panel
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS));
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Book a Lesson"));

        JPanel memberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        memberPanel.add(new JLabel("Member:"));
        memberComboBox = new JComboBox<>(members.toArray(new Member[0]));
        memberPanel.add(memberComboBox);
        bookingPanel.add(memberPanel);

        JButton bookBtn = new JButton("Book Selected Lesson");
        bookBtn.addActionListener(e -> bookLesson());
        JPanel bookBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookBtnPanel.add(bookBtn);
        bookingPanel.add(bookBtnPanel);

        leftPanel.add(bookingPanel);

        //Review panel
        JPanel reviewPanal = new JPanel(new FlowLayout(FlowLayout.LEFT));
      reviewPanal.setBorder(BorderFactory.createTitledBorder("Give a Review"));
        rExerciseComboBox = new JComboBox<>(new String[]{ "Yoga", "Zumba", "Aquacise", "Box Fit"});
        reviewComboBox = new JComboBox<>(new String[]{"1:Very dissatisfied","2:Dissatisfied","3:OK","4:Satisfied","5:very Satisfied"});
        JButton reviewBtn = new JButton("Review");
        reviewBtn.addActionListener(e -> plaveReview());
        reviewPanal.add(rExerciseComboBox);
        reviewPanal.add(reviewComboBox);
       reviewPanal.add(reviewBtn);
        leftPanel.add(reviewPanal);

        // Reports Panel
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.Y_AXIS));
        reportPanel.setBorder(BorderFactory.createTitledBorder("Reports"));

        JButton lessonReportBtn = new JButton("Lesson Report");
        lessonReportBtn.addActionListener(e -> showLessonReport());
        reportPanel.add(lessonReportBtn);

        JButton incomeReportBtn = new JButton("Income Report");
        incomeReportBtn.addActionListener(e -> showIncomeReport());
        reportPanel.add(incomeReportBtn);

        leftPanel.add(reportPanel);

        // Add Member Panel
        JPanel addMemberPanel = new JPanel();
        addMemberPanel.setLayout(new BoxLayout(addMemberPanel, BoxLayout.Y_AXIS));
        addMemberPanel.setBorder(BorderFactory.createTitledBorder("Add New Member"));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(15);
        namePanel.add(nameField);
        addMemberPanel.add(namePanel);

        JButton addMemberBtn = new JButton("Add Member");
        addMemberBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (!name.isEmpty()) {
                    Member newMember = new Member(memberIdCounter++, name);
                    members.add(newMember);
                    memberComboBox.addItem(newMember);
                    nameField.setText("");
                    outputArea.setText("Member '" + name + "' added successfully!");
                } else {
                    outputArea.setText("Please enter a valid name.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error adding member: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        JPanel addBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addBtnPanel.add(addMemberBtn);
        addMemberPanel.add(addBtnPanel);

        leftPanel.add(addMemberPanel);

        // Refresh Button
        JButton refreshBtn = new JButton("Show All Lessons");
        refreshBtn.addActionListener(e -> refreshLessonTable(timetable.getLessons()));
        leftPanel.add(refreshBtn);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right panel - Table and Output
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));

        // Lesson Table
        String[] columnNames = {"ID", "Exercise", "Day", "Time", "Members", "Price", "Rating"};
        lessonTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        lessonTable = new JTable(lessonTableModel);
        lessonTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(lessonTable);
        tableScrollPane.setPreferredSize(new Dimension(0, 300));
        rightPanel.add(tableScrollPane, BorderLayout.NORTH);

        // Output Area
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        rightPanel.add(outputScrollPane, BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Load initial data
        refreshLessonTable(timetable.getLessons());
    }

    /**
     * Refreshes the lesson table with the given list of lessons.

     *  the list of lessons to display
     */
    private void refreshLessonTable(List<Lesson> lessons) {
        // Clear existing rows
        lessonTableModel.setRowCount(0);

        if (lessons == null || lessons.isEmpty()) {
            outputArea.setText("No lessons found.");
            return;
        }

        int index = 1;
        for (Lesson l : lessons) {
            Object[] row = {
                    index++,
                    l.getExercise().getName(),
                    l.getDay(),
                    l.getTimeSlot(),
                    l.getMemberCount() + "/" + l.getCapacity(),
                    "£" + l.getExercise().getPrice(),
                   /* String.format("%.1f", l.getAverageRating())*/
            };
            lessonTableModel.addRow(row);
        }
        outputArea.setText("Showing " + lessons.size() + " lesson(s)");
    }

    /**
     * Searches for lessons by the selected day.
     */
    private void searchByDay() {
        try {
            String selected = (String) dayComboBox.getSelectedItem();
            List<Lesson> results;

            if (selected == null || selected.equals("All")) {
                results = timetable.getLessons();
            } else {
                DayType day = DayType.valueOf(selected);
                results = timetable.searchByDay(day);
            }

            refreshLessonTable(results);
            outputArea.setText("Found " + results.size() + " lesson(s) for " + selected);
        } catch (IllegalArgumentException e) {
            outputArea.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            outputArea.setText("An unexpected error occurred: " + e.getMessage());
        }
    }
    //for review and rating
    private void plaveReview(){
        try {
            String selectedRating = (String) reviewComboBox.getSelectedItem();
            String selectedLesson = (String) rExerciseComboBox.getSelectedItem();

            System.out.println("Selected : " + selectedLesson + " Selected:" + selectedRating);


            /*exerciseComboBox = new JComboBox<>(new String[]{ "Yoga", "Zumba", "Aquacise", "Box Fit"});
            reviewComboBox = new JComboBox<>(new String[]{"1:Very dissatisfied","2:Dissatisfied","3:OK","4:Satisfied","5:very Satisfied"});**/
           outputArea.setText("REVIEW HAS BEEN ADDED "+"\n"
                + "Lesson:"+selectedLesson +"\n"+ " Rating:" + selectedRating);
            outputArea.setForeground(Color.darkGray);
        }
        catch (IllegalArgumentException e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    /**
     * Searches for lessons by the selected exercise type.
     */
    private void searchByExercise() {
        try {
            String selectedLesson = (String) exerciseComboBox.getSelectedItem();
            List<Lesson> results;
System.out.println(selectedLesson);
            if (selectedLesson == null || selectedLesson.equals("All")) {
                results = timetable.getLessons();
            } else {
                results = timetable.searchByExercise(selectedLesson);
            }

            refreshLessonTable(results);
            outputArea.setText("Found " + results.size() + " lesson(s) for " + selectedLesson);
        } catch (IllegalArgumentException e) {
            outputArea.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            outputArea.setText("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Books the selected lesson for the selected member.
     */
    private void bookLesson() {
        try {
            int selectedRow = lessonTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please select a lesson first!",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate member selection
            Member member = (Member) memberComboBox.getSelectedItem();
            if (member == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select a member!",
                        "No Member Selected",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get the lesson from the timetable
            List<Lesson> lessons = timetable.getLessons();

            // Adjust index because table starts at 0 and we display index starting at 1
            int lessonIndex = selectedRow;
            if (lessonIndex < 0 || lessonIndex >= lessons.size()) {
                JOptionPane.showMessageDialog(this,
                        "Invalid lesson selection!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Lesson lesson = lessons.get(lessonIndex);

            // Check if lesson is full
            if (lesson.isFull()) {
                JOptionPane.showMessageDialog(this,
                        "This lesson is already full!",
                        "Lesson Full",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Attempt to book the lesson
            if (member.bookLesson(lesson)) {
                outputArea.setText("Booking successful!\nMember: " + member.getName() + "\nLesson: " + lesson.getExercise().getName());
                refreshLessonTable(timetable.getLessons());
                JOptionPane.showMessageDialog(this,
                        "Booking Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Booking failed! You may have a time conflict.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the lesson report in the output area.
     */
    private void showLessonReport() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("========== LESSON REPORT ==========\n\n");

            List<Lesson> lessons = timetable.getLessons();
            if (lessons.isEmpty()) {
                sb.append("No lessons available.");
            } else {
                for (Lesson l : lessons) {
                    sb.append(l.getExercise().getName())
                            .append(" | ").append(l.getDay())
                            .append(" | ").append(l.getTimeSlot())
                            .append(" | Members: ").append(l.getMemberCount())
                            .append("/").append(l.getCapacity())
                            /*.append(" | Avg Rating: ").append(String.format("%.2f", l.getAverageRating()))**/
                            .append("\n");
                }
            }
            outputArea.setText(sb.toString());
        } catch (Exception e) {
            outputArea.setText("Error generating report: " + e.getMessage());
        }
    }

    /**
     * Displays the income report in the output area.
     */
    private void showIncomeReport() {
        try {
            Map<String, Double> income = new HashMap<>();

            for (Lesson l : timetable.getLessons()) {
                double lessonIncome = l.getMemberCount() * l.getExercise().getPrice();
                income.merge(l.getExercise().getName(), lessonIncome, Double::sum);
            }

            String bestExercise = "";
            double maxIncome = 0;

            for (String ex : income.keySet()) {
                if (income.get(ex) > maxIncome) {
                    maxIncome = income.get(ex);
                    bestExercise = ex;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("========== INCOME REPORT ==========\n\n");

            if (income.isEmpty()) {
                sb.append("No income data available.");
            } else {
                for (String ex : income.keySet()) {
                    sb.append(ex).append(": £").append(String.format("%.2f", income.get(ex))).append("\n");
                }
                sb.append("\nHighest Income: ").append(bestExercise).append(" - £").append(String.format("%.2f", maxIncome));
            }

            outputArea.setText(sb.toString());
        } catch (Exception e) {
            outputArea.setText("Error generating report: " + e.getMessage());
        }
    }

    /**
     * Main entry point for the GUI application.

     * command line arguments (not used)
     */
    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                FLCGUI gui = new FLCGUI();
                gui.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

