import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Prelim Grade Calculator - Window-Based Application
 * Computes the required Prelim Exam score needed to pass or achieve excellent standing
 */
public class PrelimGradeCalculatorGUI extends JFrame {
    // Constants for grading weights
    private static final double PRELIM_EXAM_WEIGHT = 0.70;
    private static final double CLASS_STANDING_WEIGHT = 0.30;
    private static final double ATTENDANCE_WEIGHT = 0.40;
    private static final double LAB_WORK_WEIGHT = 0.60;
    
    // Attendance constants
    private static final int MAX_ATTENDANCES = 5;
    private static final int FAILING_UNEXCUSED_ABSENCES = 4;
    
    // Target grades
    private static final double PASSING_GRADE = 75.0;
    private static final double EXCELLENT_GRADE = 100.0;
    
    // GUI Components
    private JTextField presentDaysField;
    private JTextField excusedAbsencesField;
    private JTextField labWork1Field;
    private JTextField labWork2Field;
    private JTextField labWork3Field;
    private JTextArea resultArea;
    private JButton clearButton;
    
    public PrelimGradeCalculatorGUI() {
        // Set up the main frame
        setTitle("Prelim Grade Calculator");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 250));
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Input Panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(63, 81, 181));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel titleLabel = new JLabel("PRELIM GRADE CALCULATOR");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Calculate your required Prelim Exam score");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 200, 255));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitleLabel);
        
        return panel;
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 250));
        
        // Formula Information Panel
        JPanel formulaPanel = new JPanel();
        formulaPanel.setLayout(new BoxLayout(formulaPanel, BoxLayout.Y_AXIS));
        formulaPanel.setBackground(new Color(232, 245, 255));
        formulaPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel formulaTitle = new JLabel("Grading Formula:");
        formulaTitle.setFont(new Font("Arial", Font.BOLD, 14));
        formulaTitle.setForeground(new Color(33, 150, 243));
        
        JLabel formula1 = new JLabel("Prelim Grade = 70% Prelim Exam + 30% Class Standing");
        JLabel formula2 = new JLabel("Class Standing = 40% Attendance + 60% Lab Work Average");
        JLabel formula3 = new JLabel("Total Attendance = Present + Excused (max 5)");
        JLabel formula4 = new JLabel("4+ Unexcused Absences = Automatic Fail");
        
        formula1.setFont(new Font("Monospaced", Font.PLAIN, 11));
        formula2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        formula3.setFont(new Font("Monospaced", Font.PLAIN, 11));
        formula4.setFont(new Font("Monospaced", Font.BOLD, 11));
        formula4.setForeground(new Color(211, 47, 47));
        
        formulaPanel.add(formulaTitle);
        formulaPanel.add(Box.createVerticalStrut(5));
        formulaPanel.add(formula1);
        formulaPanel.add(formula2);
        formulaPanel.add(formula3);
        formulaPanel.add(formula4);
        
        // Input Fields Panel
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Initialize text fields with input filters
        presentDaysField = createRestrictedTextField(0, MAX_ATTENDANCES);
        excusedAbsencesField = createRestrictedTextField(0, MAX_ATTENDANCES);
        labWork1Field = createRestrictedTextField(0, 100);
        labWork2Field = createRestrictedTextField(0, 100);
        labWork3Field = createRestrictedTextField(0, 100);
        
        // Style text fields
        JTextField[] fields = {presentDaysField, excusedAbsencesField, labWork1Field, labWork2Field, labWork3Field};
        for (JTextField field : fields) {
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            
            // Add document listener for auto-calculation
            field.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) { calculateGrades(); }
                public void removeUpdate(DocumentEvent e) { calculateGrades(); }
                public void insertUpdate(DocumentEvent e) { calculateGrades(); }
            });
        }
        
        // Add labels and fields
        addFieldRow(fieldsPanel, gbc, 0, "Present Days (0-5):", presentDaysField);
        addFieldRow(fieldsPanel, gbc, 1, "Excused Absences (0-5):", excusedAbsencesField);
        addFieldRow(fieldsPanel, gbc, 2, "Lab Work 1 Grade (0-100):", labWork1Field);
        addFieldRow(fieldsPanel, gbc, 3, "Lab Work 2 Grade (0-100):", labWork2Field);
        addFieldRow(fieldsPanel, gbc, 4, "Lab Work 3 Grade (0-100):", labWork3Field);
        
        // Results Area
        resultArea = new JTextArea(14, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            "Results",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12)
        ));
        
        // Combine panels
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(240, 240, 250));
        topPanel.add(formulaPanel, BorderLayout.NORTH);
        topPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Create text field with input restriction
    private JTextField createRestrictedTextField(final int min, final int max) {
        JTextField field = new JTextField(15);
        
        try {
            ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                        throws BadLocationException {
                    if (isValid(fb.getDocument(), offset, string, 0)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                        throws BadLocationException {
                    if (isValid(fb.getDocument(), offset, text, length)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }

                private boolean isValid(Document doc, int offset, String string, int length) {
                    try {
                        String currentText = doc.getText(0, doc.getLength());
                        String beforeOffset = currentText.substring(0, offset);
                        String afterOffset = currentText.substring(Math.min(offset + length, currentText.length()));
                        String newText = beforeOffset + string + afterOffset;
                        
                        if (newText.isEmpty()) return true;
                        if (!newText.matches("\\d*\\.?\\d*")) return false;
                        
                        double value = Double.parseDouble(newText);
                        return value >= min && value <= max;
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Error setting document filter: " + e.getMessage());
        }
        
        return field;
    }
    
    private void addFieldRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.4;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        panel.add(field, gbc);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(240, 240, 250));
        
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setPreferredSize(new Dimension(150, 40));
        clearButton.setBackground(new Color(158, 158, 158));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        clearButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clearButton.setBackground(new Color(130, 130, 130));
            }
            public void mouseExited(MouseEvent e) {
                clearButton.setBackground(new Color(158, 158, 158));
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        panel.add(clearButton);
        
        return panel;
    }
    
    private void calculateGrades() {
        try {
            // Check if all fields have values
            String presentDaysText = presentDaysField.getText().trim();
            String excusedText = excusedAbsencesField.getText().trim();
            String labWork1Text = labWork1Field.getText().trim();
            String labWork2Text = labWork2Field.getText().trim();
            String labWork3Text = labWork3Field.getText().trim();
            
            // If any field is empty, clear results
            if (presentDaysText.isEmpty() || excusedText.isEmpty() ||
                labWork1Text.isEmpty() || labWork2Text.isEmpty() || labWork3Text.isEmpty()) {
                resultArea.setText("");
                return;
            }
            
            // Parse inputs
            int presentDays = Integer.parseInt(presentDaysText);
            int excusedAbsences = Integer.parseInt(excusedText);
            double labWork1 = Double.parseDouble(labWork1Text);
            double labWork2 = Double.parseDouble(labWork2Text);
            double labWork3 = Double.parseDouble(labWork3Text);
            
            // Total Attendance = Present + Excused
            int totalAttendance = presentDays + excusedAbsences;
            
            // Cap at maximum
            if (totalAttendance > MAX_ATTENDANCES) {
                totalAttendance = MAX_ATTENDANCES;
            }
            
            // Calculate unexcused absences
            int unexcusedAbsences = MAX_ATTENDANCES - totalAttendance;
            
            // Check for automatic fail due to absences
            if (unexcusedAbsences >= FAILING_UNEXCUSED_ABSENCES) {
                displayFailedDueToAbsences(presentDays, excusedAbsences, unexcusedAbsences);
                return;
            }
            
            // Attendance percentage (out of 5 sessions)
            double attendancePercentage = (totalAttendance / (double) MAX_ATTENDANCES) * 100.0;
            
            // Lab Work Average
            double labWorkAverage = (labWork1 + labWork2 + labWork3) / 3.0;
            
            // Class Standing = 40% Attendance + 60% Lab Work Average
            double classStanding = (ATTENDANCE_WEIGHT * attendancePercentage) + 
                                  (LAB_WORK_WEIGHT * labWorkAverage);
            
            // Calculate required Prelim Exam scores
            // Prelim Grade = 70% Prelim Exam + 30% Class Standing
            // Solving for Prelim Exam:
            // Prelim Exam = (Prelim Grade - 30% Class Standing) / 70%
            
            double requiredForPassing = (PASSING_GRADE - (CLASS_STANDING_WEIGHT * classStanding)) / 
                                       PRELIM_EXAM_WEIGHT;
            double requiredForExcellent = (EXCELLENT_GRADE - (CLASS_STANDING_WEIGHT * classStanding)) / 
                                         PRELIM_EXAM_WEIGHT;
            
            // Display results
            displayResults(presentDays, excusedAbsences, totalAttendance, unexcusedAbsences,
                         attendancePercentage, labWork1, labWork2, labWork3, labWorkAverage, 
                         classStanding, requiredForPassing, requiredForExcellent);
            
        } catch (NumberFormatException e) {
            // Silently ignore parsing errors during typing
            return;
        }
    }
    
    private void displayFailedDueToAbsences(int presentDays, int excusedAbsences, int unexcusedAbsences) {
        StringBuilder result = new StringBuilder();
        result.append("==============================================\n");
        result.append("           COURSE STATUS\n");
        result.append("==============================================\n\n");
        
        result.append("FAILED DUE TO EXCESSIVE ABSENCES\n\n");
        
        result.append(String.format("Physical Attendances: %d out of %d\n", presentDays, MAX_ATTENDANCES));
        if (excusedAbsences > 0) {
            result.append(String.format("Excused Absences: %d\n", excusedAbsences));
        }
        result.append(String.format("Unexcused Absences: %d out of %d\n", unexcusedAbsences, MAX_ATTENDANCES));
        
        result.append("\nIMPORTANT:\n");
        result.append("Students with 4 or more unexcused absences\n");
        result.append("automatically fail the course, regardless of grades.\n");
        
        result.append("\n==============================================");
        
        resultArea.setText(result.toString());
    }
    
    private void displayResults(int presentDays, int excusedAbsences, int totalAttendance, int unexcusedAbsences,
                               double attendancePercentage, double labWork1, double labWork2, double labWork3,
                               double labWorkAverage, double classStanding, 
                               double requiredForPassing, double requiredForExcellent) {
        StringBuilder result = new StringBuilder();
        result.append("==============================================\n");
        result.append("           COMPUTATION RESULTS\n");
        result.append("==============================================\n\n");
        
        result.append(String.format("Physical Attendances: %d out of %d\n", presentDays, MAX_ATTENDANCES));
        if (excusedAbsences > 0) {
            result.append(String.format("Excused Absences: %d\n", excusedAbsences));
        }
        result.append(String.format("Unexcused Absences: %d out of %d\n", unexcusedAbsences, MAX_ATTENDANCES));
        result.append(String.format("Total Effective Attendance: %d out of %d\n", totalAttendance, MAX_ATTENDANCES));
        result.append(String.format("Attendance Score: %.2f%%\n", attendancePercentage));
        
        result.append(String.format("\nLab Work 1: %.2f\n", labWork1));
        result.append(String.format("Lab Work 2: %.2f\n", labWork2));
        result.append(String.format("Lab Work 3: %.2f\n", labWork3));
        result.append(String.format("Lab Work Average: %.2f\n", labWorkAverage));
        result.append(String.format("\nClass Standing: %.2f\n", classStanding));
        
        result.append("\n==============================================\n");
        result.append("       REQUIRED PRELIM EXAM SCORES\n");
        result.append("==============================================\n\n");
        
        // For Passing Grade (75)
        result.append(String.format("To PASS (75): %.2f\n", requiredForPassing));
        if (requiredForPassing > 100) {
            result.append("Remark: Unfortunately, passing is mathematically\n");
            result.append("        impossible with your current class standing.\n");
        } else if (requiredForPassing <= 0) {
            result.append("Remark: You've already secured a passing grade!\n");
        } else {
            result.append(String.format("Remark: You need %.2f or higher to pass.\n", requiredForPassing));
        }
        
        result.append("\n");
        
        // For Excellent Grade (100)
        result.append(String.format("For EXCELLENT (100): %.2f\n", requiredForExcellent));
        if (requiredForExcellent > 100) {
            result.append("Remark: This would require more than 100%%\n");
            result.append("        on the exam.\n");
        } else if (requiredForExcellent <= 0) {
            result.append("Remark: You've already secured an excellent grade!\n");
        } else {
            result.append(String.format("Remark: You need %.2f for excellence.\n", requiredForExcellent));
        }
        
        result.append("\n==============================================");
        
        resultArea.setText(result.toString());
    }
    
    private void clearFields() {
        presentDaysField.setText("");
        excusedAbsencesField.setText("");
        labWork1Field.setText("");
        labWork2Field.setText("");
        labWork3Field.setText("");
        resultArea.setText("");
        presentDaysField.requestFocus();
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and display the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PrelimGradeCalculatorGUI calculator = new PrelimGradeCalculatorGUI();
                calculator.setVisible(true);
            }
        });
    }
}