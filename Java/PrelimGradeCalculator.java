import java.util.Scanner;

public class PrelimGradeCalculator {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==============================================");
        System.out.println("    PRELIM GRADE CALCULATOR");
        System.out.println("==============================================\n");
        
        try {
            // Get user inputs
            System.out.print("Enter number of attendances (0-100): ");
            int attendances = scanner.nextInt();
            
            // Validate attendances
            while (attendances < 0 || attendances > 100) {
                System.out.println("Invalid input. Attendances must be between 0 and 100.");
                System.out.print("Enter number of attendances (0-100): ");
                attendances = scanner.nextInt();
            }
            
            // Get Lab Work grades
            System.out.print("Enter Lab Work 1 grade (0-100): ");
            double labWork1 = scanner.nextDouble();
            while (labWork1 < 0 || labWork1 > 100) {
                System.out.println("Invalid input. Grade must be between 0 and 100.");
                System.out.print("Enter Lab Work 1 grade (0-100): ");
                labWork1 = scanner.nextDouble();
            }
            
            System.out.print("Enter Lab Work 2 grade (0-100): ");
            double labWork2 = scanner.nextDouble();
            while (labWork2 < 0 || labWork2 > 100) {
                System.out.println("Invalid input. Grade must be between 0 and 100.");
                System.out.print("Enter Lab Work 2 grade (0-100): ");
                labWork2 = scanner.nextDouble();
            }
            
            System.out.print("Enter Lab Work 3 grade (0-100): ");
            double labWork3 = scanner.nextDouble();
            while (labWork3 < 0 || labWork3 > 100) {
                System.out.println("Invalid input. Grade must be between 0 and 100.");
                System.out.print("Enter Lab Work 3 grade (0-100): ");
                labWork3 = scanner.nextDouble();
            }
            
            // Compute values
            double attendanceScore = (double) attendances;
            double labWorkAverage = (labWork1 + labWork2 + labWork3) / 3.0;
            double classStanding = (0.40 * attendanceScore) + (0.60 * labWorkAverage);
            
            // Calculate required Prelim Exam scores
            double requiredForPassing = (75.0 - (0.70 * classStanding)) / 0.30;
            double requiredForExcellent = (100.0 - (0.70 * classStanding)) / 0.30;
            
            // Display results
            System.out.println("\n==============================================");
            System.out.println("    COMPUTATION RESULTS");
            System.out.println("==============================================\n");
            
            System.out.printf("Attendance Score:        %.2f\n", attendanceScore);
            System.out.printf("Lab Work 1 Grade:        %.2f\n", labWork1);
            System.out.printf("Lab Work 2 Grade:        %.2f\n", labWork2);
            System.out.printf("Lab Work 3 Grade:        %.2f\n", labWork3);
            System.out.printf("Lab Work Average:        %.2f\n", labWorkAverage);
            System.out.printf("Class Standing:          %.2f\n", classStanding);
            
            System.out.println("\n==============================================");
            System.out.println("    REQUIRED PRELIM EXAM SCORES");
            System.out.println("==============================================\n");
            
            // For Passing Grade (75)
            System.out.printf("To PASS (75%%):          %.2f\n", requiredForPassing);
            if (requiredForPassing > 100) {
                System.out.println("Remark: It is impossible to pass the Prelim period.");
                System.out.println("        You need a score greater than 100.");
            } else if (requiredForPassing < 0) {
                System.out.println("Remark: You have already passed the Prelim period!");
                System.out.println("        Even with a score of 0 on the Prelim Exam.");
            } else {
                System.out.printf("Remark: You need a Prelim Exam score of %.2f or higher to pass.\n", 
                                requiredForPassing);
            }
            
            System.out.println();
            
            // For Excellent Grade (100)
            System.out.printf("To get EXCELLENT (100%%): %.2f\n", requiredForExcellent);
            if (requiredForExcellent > 100) {
                System.out.println("Remark: It is impossible to achieve an excellent grade.");
                System.out.println("        You need a score greater than 100.");
            } else if (requiredForExcellent < 0) {
                System.out.println("Remark: You have already achieved an excellent standing!");
                System.out.println("        Even with a score of 0 on the Prelim Exam.");
            } else {
                System.out.printf("Remark: You need a Prelim Exam score of %.2f to achieve excellence.\n", 
                                requiredForExcellent);
            }
            
            System.out.println("\n==============================================");
            
        } catch (Exception e) {
            System.out.println("\nError: Invalid input. Please enter numeric values only.");
        } finally {
            scanner.close();
        }
    }
}