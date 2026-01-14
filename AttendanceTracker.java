import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * AttendanceTracker - A Java Swing application for tracking attendance
 * This application displays a form with fields for name, course, time in, and e-signature
 * 
 * @author Your Name
 * @version 1.0
 */
public class AttendanceTracker {
    
    // Declare GUI components as class members
    private JFrame frame;
    private JTextField nameField;
    private JTextField courseField;
    private JTextField timeInField;
    private JTextField eSignatureField;
    
    /**
     * Constructor - initializes and displays the attendance tracker window
     */
    public AttendanceTracker() {
        // Create the main application window
        createAndShowGUI();
    }
    
    /**
     * Creates and displays the GUI components
     */
    private void createAndShowGUI() {
        // Create the main frame (window)
        frame = new JFrame("Attendance Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window on screen
        
        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create title label
        JLabel titleLabel = new JLabel("Attendance Tracking System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Create form panel with GridBagLayout for better control
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 5, 3, 5); // Padding between components
        
        // Row 0: Attendance Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel nameLabel = new JLabel("Attendance Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 11));
        formPanel.add(nameField, gbc);
        
        // Row 1: Course/Year
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel courseLabel = new JLabel("Course/Year:");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        formPanel.add(courseLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        courseField = new JTextField(15);
        courseField.setFont(new Font("Arial", Font.PLAIN, 11));
        formPanel.add(courseField, gbc);
        
        // Row 2: Time In
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel timeLabel = new JLabel("Time In:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        formPanel.add(timeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        timeInField = new JTextField(15);
        timeInField.setFont(new Font("Arial", Font.PLAIN, 11));
        timeInField.setEditable(false); // Make it read-only
        timeInField.setBackground(Color.WHITE);
        // Get current date and time and format it nicely
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timeInField.setText(now.format(formatter));
        formPanel.add(timeInField, gbc);
        
        // Row 3: E-Signature
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel signatureLabel = new JLabel("E-Signature:");
        signatureLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        formPanel.add(signatureLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        eSignatureField = new JTextField(15);
        eSignatureField.setFont(new Font("Arial", Font.PLAIN, 9));
        eSignatureField.setEditable(false); // Make it read-only
        eSignatureField.setBackground(Color.WHITE);
        // Generate unique E-Signature using UUID
        String eSignature = UUID.randomUUID().toString();
        eSignatureField.setText(eSignature);
        formPanel.add(eSignatureField, gbc);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit Attendance");
        submitButton.setFont(new Font("Arial", Font.BOLD, 11));
        submitButton.setPreferredSize(new Dimension(150, 30));
        
        // Add action listener to submit button
        submitButton.addActionListener(e -> {
            // Get the course input
            String course = courseField.getText().trim();
            
            // Define valid courses
            String[] validCourses = {
                "BSIT-GD 1st Year", "BSIT-GD 2nd Year", "BSIT-GD 3rd Year", "BSIT-GD 4th Year",
                "BSCS-DS 1st Year", "BSCS-DS 2nd Year", "BSCS-DS 3rd Year", "BSCS-DS 4th Year"
            };
            
            // Check if course is valid
            boolean isValidCourse = false;
            for (String validCourse : validCourses) {
                if (course.equalsIgnoreCase(validCourse)) {
                    isValidCourse = true;
                    break;
                }
            }
            
            // Validate that name and course are filled
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter your name!", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (course.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter your Course/Year!", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!isValidCourse) {
                JOptionPane.showMessageDialog(frame, 
                    "Invalid Course/Year!\n\n" +
                    "Please enter one of the following:\n" +
                    "• BSIT-GD 1st Year\n" +
                    "• BSIT-GD 2nd Year\n" +
                    "• BSIT-GD 3rd Year\n" +
                    "• BSIT-GD 4th Year\n" +
                    "• BSCS-DS 1st Year\n" +
                    "• BSCS-DS 2nd Year\n" +
                    "• BSCS-DS 3rd Year\n" +
                    "• BSCS-DS 4th Year", 
                    "Invalid Course", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                // Show success message
                JOptionPane.showMessageDialog(frame, 
                    "Attendance recorded successfully!\n" +
                    "Name: " + nameField.getText() + "\n" +
                    "Course: " + course + "\n" +
                    "Time: " + timeInField.getText(),
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        buttonPanel.add(submitButton);
        
        // Add all panels to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        frame.add(mainPanel);
        
        // Make the frame visible
        frame.setVisible(true);
    }
    
    /**
     * Main method - entry point of the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            new AttendanceTracker();
        });
    }
}