// Programmer: [Your Full Name] - Student ID: [Your Student ID]
// Student Record System - Java Swing Implementation

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class StudentRecordSystem extends JFrame {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtID, txtName, txtLab1, txtLab2, txtLab3, txtPrelim, txtAttendance;
    private JButton btnAdd, btnDelete, btnBrowse;
    private JLabel lblCurrentFile;
    private String csvFilePath = "MOCK_DATA.csv"; // Default file path
    
    public StudentRecordSystem() {
        // Set window title with programmer identifier
        setTitle("Student Records - [Your Full Name] [Your Student ID]");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeComponents();
        
        // Prompt user to select CSV file or use default
        int choice = JOptionPane.showConfirmDialog(this,
            "Do you want to select your own CSV file?\n(Click 'No' to use default MOCK_DATA.csv)",
            "CSV File Selection",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            selectCSVFile();
        }
        
        loadDataFromCSV();
    }
    
    private void initializeComponents() {
        // Main panel with BorderLayout
        setLayout(new BorderLayout(10, 10));
        
        // Create table with columns
        String[] columns = {"ID", "Name", "Labwork 1", "Labwork 2", "Labwork 3", "Prelim Exam", "Attendance"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Create input panel with GridBagLayout for better organization
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 1: ID and Name
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtID = new JTextField(10);
        inputPanel.add(txtID, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 3;
        txtName = new JTextField(25);
        inputPanel.add(txtName, gbc);
        
        // Row 2: Labwork grades
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Labwork 1:"), gbc);
        gbc.gridx = 1;
        txtLab1 = new JTextField(10);
        inputPanel.add(txtLab1, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Labwork 2:"), gbc);
        gbc.gridx = 3;
        txtLab2 = new JTextField(10);
        inputPanel.add(txtLab2, gbc);
        
        gbc.gridx = 4;
        inputPanel.add(new JLabel("Labwork 3:"), gbc);
        gbc.gridx = 5;
        txtLab3 = new JTextField(10);
        inputPanel.add(txtLab3, gbc);
        
        // Row 3: Prelim and Attendance
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Prelim Exam:"), gbc);
        gbc.gridx = 1;
        txtPrelim = new JTextField(10);
        inputPanel.add(txtPrelim, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Attendance:"), gbc);
        gbc.gridx = 3;
        txtAttendance = new JTextField(10);
        inputPanel.add(txtAttendance, gbc);
        
        // Row 4: Buttons
        gbc.gridx = 4; gbc.gridy = 2;
        btnAdd = new JButton("Add Student");
        btnAdd.setBackground(new Color(76, 175, 80));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        inputPanel.add(btnAdd, gbc);
        
        gbc.gridx = 5;
        btnDelete = new JButton("Delete Student");
        btnDelete.setBackground(new Color(244, 67, 54));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        inputPanel.add(btnDelete, gbc);
        
        // Row 5: File selection
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Current File:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3;
        lblCurrentFile = new JLabel(csvFilePath);
        lblCurrentFile.setForeground(new Color(33, 150, 243));
        inputPanel.add(lblCurrentFile, gbc);
        
        gbc.gridx = 4; gbc.gridwidth = 1;
        btnBrowse = new JButton("Browse CSV");
        btnBrowse.setBackground(new Color(33, 150, 243));
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setFocusPainted(false);
        inputPanel.add(btnBrowse, gbc);
        
        add(inputPanel, BorderLayout.SOUTH);
        
        // Add button listeners
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCSVFile();
                loadDataFromCSV();
            }
        });
    }
    
    private void selectCSVFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
            }
            
            @Override
            public String getDescription() {
                return "CSV Files (*.csv)";
            }
        });
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            csvFilePath = selectedFile.getAbsolutePath();
            lblCurrentFile.setText(csvFilePath);
        }
    }
    
    private void loadDataFromCSV() {
        BufferedReader reader = null;
        
        // Clear existing data
        tableModel.setRowCount(0);
        
        try {
            reader = new BufferedReader(new FileReader(csvFilePath));
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Split the CSV line
                String[] data = line.split(",");
                
                if (data.length >= 7) {
                    // Add row to table
                    tableModel.addRow(new Object[]{
                        data[0].trim(),  // ID
                        data[1].trim(),  // Name
                        data[2].trim(),  // Labwork 1
                        data[3].trim(),  // Labwork 2
                        data[4].trim(),  // Labwork 3
                        data[5].trim(),  // Prelim Exam
                        data[6].trim()   // Attendance
                    });
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Data loaded successfully! " + tableModel.getRowCount() + " records loaded from:\n" + csvFilePath, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: CSV file not found at:\n" + csvFilePath + "\n\nPlease select a valid CSV file.", 
                "File Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error reading file: " + e.getMessage(), 
                "IO Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void saveDataToCSV() {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(csvFilePath));
            
            // Write header
            writer.write("ID,Name,Labwork1,Labwork2,Labwork3,PrelimExam,Attendance");
            writer.newLine();
            
            // Write all rows from table
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    line.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
            
            writer.flush();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving to CSV file: " + e.getMessage(), 
                "Save Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void addStudent() {
        String id = txtID.getText().trim();
        String name = txtName.getText().trim();
        String lab1 = txtLab1.getText().trim();
        String lab2 = txtLab2.getText().trim();
        String lab3 = txtLab3.getText().trim();
        String prelim = txtPrelim.getText().trim();
        String attendance = txtAttendance.getText().trim();
        
        // Validate input
        if (id.isEmpty() || name.isEmpty() || lab1.isEmpty() || 
            lab2.isEmpty() || lab3.isEmpty() || prelim.isEmpty() || attendance.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Validate all grades are numbers and within range
            int lab1Value = Integer.parseInt(lab1);
            int lab2Value = Integer.parseInt(lab2);
            int lab3Value = Integer.parseInt(lab3);
            int prelimValue = Integer.parseInt(prelim);
            int attendanceValue = Integer.parseInt(attendance);
            
            if (lab1Value < 0 || lab1Value > 100 ||
                lab2Value < 0 || lab2Value > 100 ||
                lab3Value < 0 || lab3Value > 100 ||
                prelimValue < 0 || prelimValue > 100 ||
                attendanceValue < 0 || attendanceValue > 100) {
                JOptionPane.showMessageDialog(this, 
                    "All grades must be between 0 and 100!", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Add to table
            tableModel.addRow(new Object[]{id, name, lab1, lab2, lab3, prelim, attendance});
            
            // Save to CSV file
            saveDataToCSV();
            
            // Clear input fields
            txtID.setText("");
            txtName.setText("");
            txtLab1.setText("");
            txtLab2.setText("");
            txtLab3.setText("");
            txtPrelim.setText("");
            txtAttendance.setText("");
            
            JOptionPane.showMessageDialog(this, 
                "Student added and saved to CSV successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "All grade fields must be valid numbers!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a row to delete!", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this student?\nThis will update the CSV file.", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            
            // Save to CSV file
            saveDataToCSV();
            
            JOptionPane.showMessageDialog(this, 
                "Student deleted and CSV updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Use SwingUtilities for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentRecordSystem app = new StudentRecordSystem();
                app.setVisible(true);
            }
        });
    }
}