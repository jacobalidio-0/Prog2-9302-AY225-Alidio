import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Prelim Grade Calculator - Window-Based Application
 * Computes the required Prelim Exam score needed to pass or achieve excellent standing
 * Based on the official grading breakdown:
 * - Prelim Grade = 30% Prelim Exam + 70% Class Standing
 * - Class Standing = 40% Attendance + 60% Lab Work Average
 */
public class PrelimGradeCalculatorGUI extends JFrame {
    
    // Constants for grading weights
    private static final double PRELIM_EXAM_WEIGHT = 0.30;
    private static final double CLASS_STANDING_WEIGHT = 0.70;
    private static final double ATTENDANCE_WEIGHT = 0.40;
    private static final double LAB_WORK_WEIGHT = 0.60;
    
    // Target grades
    private static final double PASSING_GRADE = 75.0;
    private static final double EXCELLENT_GRADE = 100.0;
    
    // GUI Components
    private JTextField attendanceField;
    private JTextField labWork1Field;
    private JTextField labWork2Field;
    private JTextField labWork3Field;
    private JTextArea resultArea;
    private JButton calculateButton;
    private JButton clearButton;
    
    public PrelimGradeCalculatorGUI() {
        // Set up the main frame
        setTitle("Prelim Grade Calculator");
        setSize(600, 700);
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
        
        JLabel titleLabel = new JLabel("📚 PRELIM GRADE CALCULATOR");
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
        
        JLabel formulaTitle = new JLabel("📐 Grading Formula:");
        formulaTitle.setFont(new Font("Arial", Font.BOLD, 14));
        formulaTitle.setForeground(new Color(33, 150, 243));
        
        JLabel formula1 = new JLabel("Prelim Grade = 30% Prelim Exam + 70% Class Standing");
        JLabel formula2 = new JLabel("Class Standing = 40% Attendance + 60% Lab Work Average");
        
        formula1.setFont(new Font("Monospaced", Font.PLAIN, 11));
        formula2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        formulaPanel.add(formulaTitle);
        formulaPanel.add(Box.createVerticalStrut(5));
        formulaPanel.add(formula1);
        formulaPanel.add(formula2);
        
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
        
        // Initialize text fields
        attendanceField = new JTextField(15);
        labWork1Field = new JTextField(15);
        labWork2Field = new JTextField(15);
        labWork3Field = new JTextField(15);
        
        // Style text fields
        JTextField[] fields = {attendanceField, labWork1Field, labWork2Field, labWork3Field};
        for (JTextField field : fields) {
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
        }
        
        // Add labels and fields
        addFieldRow(fieldsPanel, gbc, 0, "Number of Attendances (0-100):", attendanceField);
        addFieldRow(fieldsPanel, gbc, 1, "Lab Work 1 Grade (0-100):", labWork1Field);
        addFieldRow(fieldsPanel, gbc, 2, "Lab Work 2 Grade (0-100):", labWork2Field);
        addFieldRow(fieldsPanel, gbc, 3, "Lab Work 3 Grade (0-100):", labWork3Field);
        
        // Results Area
        resultArea = new JTextArea(12, 40);
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
        
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setPreferredSize(new Dimension(150, 40));
        calculateButton.setBackground(new Color(76, 175, 80));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.setBorderPainted(false);
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setPreferredSize(new Dimension(150, 40));
        clearButton.setBackground(new Color(158, 158, 158));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effects
        addButtonHoverEffect(calculateButton, new Color(76, 175, 80), new Color(67, 160, 71));
        addButtonHoverEffect(clearButton, new Color(158, 158, 158), new Color(130, 130, 130));
        
        calculateButton.addActionListener(e -> calculateGrades());
        clearButton.addActionListener(e -> clearFields());
        
        panel.add(calculateButton);
        panel.add(clearButton);
        
        return panel;
    }
    
    private void addButtonHoverEffect(JButton button, Color normal, Color hover) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
            }
        });
    }
    
    private void calculateGrades() {
        try {
            // Get and validate inputs
            double attendances = Double.parseDouble(attendanceField.getText().trim());
            double labWork1 = Double.parseDouble(labWork1Field.getText().trim());
            double labWork2 = Double.parseDouble(labWork2Field.getText().trim());
            double labWork3 = Double.parseDouble(labWork3Field.getText().trim());
            
            // Validate ranges
            if (attendances < 0 || attendances > 100) {
                showError("Attendances must be between 0 and 100");
                return;
            }
            if (labWork1 < 0 || labWork1 > 100 || labWork2 < 0 || labWork2 > 100 || labWork3 < 0 || labWork3 > 100) {
                showError("All lab work grades must be between 0 and 100");
                return;
            }
            
            // Compute values
            double attendanceScore = attendances;
            double labWorkAverage = (labWork1 + labWork2 + labWork3) / 3.0;
            double classStanding = (ATTENDANCE_WEIGHT * attendanceScore) + (LAB_WORK_WEIGHT * labWorkAverage);
            
            // Calculate required Prelim Exam scores
            double requiredForPassing = (PASSING_GRADE - (CLASS_STANDING_WEIGHT * classStanding)) / PRELIM_EXAM_WEIGHT;
            double requiredForExcellent = (EXCELLENT_GRADE - (CLASS_STANDING_WEIGHT * classStanding)) / PRELIM_EXAM_WEIGHT;
            
            // Display results
            displayResults(attendanceScore, labWork1, labWork2, labWork3, 
                          labWorkAverage, classStanding, 
                          requiredForPassing, requiredForExcellent);
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values in all fields");
        }
    }
    
    private void displayResults(double attendanceScore, double labWork1, double labWork2, double labWork3,
                               double labWorkAverage, double classStanding,
                               double requiredForPassing, double requiredForExcellent) {
        StringBuilder result = new StringBuilder();
        
        result.append("==============================================\n");
        result.append("           COMPUTATION RESULTS\n");
        result.append("==============================================\n\n");
        
        result.append(String.format("Attendance Score:        %.2f\n", attendanceScore));
        result.append(String.format("Lab Work 1 Grade:        %.2f\n", labWork1));
        result.append(String.format("Lab Work 2 Grade:        %.2f\n", labWork2));
        result.append(String.format("Lab Work 3 Grade:        %.2f\n", labWork3));
        result.append(String.format("Lab Work Average:        %.2f\n", labWorkAverage));
        result.append(String.format("Class Standing:          %.2f\n", classStanding));
        
        result.append("\n==============================================\n");
        result.append("      REQUIRED PRELIM EXAM SCORES\n");
        result.append("==============================================\n\n");
        
        // For Passing Grade (75)
        result.append(String.format("To PASS (75%%):          %.2f\n", requiredForPassing));
        if (requiredForPassing > 100) {
            result.append("Remark: It is impossible to pass.\n");
            result.append("        You need a score greater than 100.\n");
        } else if (requiredForPassing < 0) {
            result.append("Remark: You have already passed!\n");
            result.append("        Even with 0 on the Prelim Exam.\n");
        } else {
            result.append(String.format("Remark: You need %.2f or higher to pass.\n", requiredForPassing));
        }
        
        result.append("\n");
        
        // For Excellent Grade (100)
        result.append(String.format("To get EXCELLENT (100%%): %.2f\n", requiredForExcellent));
        if (requiredForExcellent > 100) {
            result.append("Remark: It is impossible to achieve excellence.\n");
            result.append("        You need a score greater than 100.\n");
        } else if (requiredForExcellent < 0) {
            result.append("Remark: You have already achieved excellence!\n");
            result.append("        Even with 0 on the Prelim Exam.\n");
        } else {
            result.append(String.format("Remark: You need %.2f for excellence.\n", requiredForExcellent));
        }
        
        result.append("\n==============================================");
        
        resultArea.setText(result.toString());
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void clearFields() {
        attendanceField.setText("");
        labWork1Field.setText("");
        labWork2Field.setText("");
        labWork3Field.setText("");
        resultArea.setText("");
        attendanceField.requestFocus();
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> {
            PrelimGradeCalculatorGUI calculator = new PrelimGradeCalculatorGUI();
            calculator.setVisible(true);
        });
    }
}
