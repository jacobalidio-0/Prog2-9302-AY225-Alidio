/**
 * Prelim Grade Calculator - JavaScript Implementation
 * Computes the required Prelim Exam score needed to pass or achieve excellent standing
 * Based on the official grading breakdown:
 * - Prelim Grade = 30% Prelim Exam + 70% Class Standing
 * - Class Standing = 40% Attendance + 60% Lab Work Average
 */

// Constants for grading weights
const PRELIM_EXAM_WEIGHT = 0.30;
const CLASS_STANDING_WEIGHT = 0.70;
const ATTENDANCE_WEIGHT = 0.40;
const LAB_WORK_WEIGHT = 0.60;

// Target grades
const PASSING_GRADE = 75.0;
const EXCELLENT_GRADE = 100.0;

// Maximum values
const MAX_ATTENDANCES = 100;
const MAX_GRADE = 100.0;

/**
 * Main function to run the calculator
 */
function calculatePrelimGrade() {
    // Clear previous results
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = '';
    
    try {
        // Get and validate inputs
        const attendances = getValidatedInput('attendances', 0, MAX_ATTENDANCES);
        const labWork1 = getValidatedInput('labWork1', 0, MAX_GRADE);
        const labWork2 = getValidatedInput('labWork2', 0, MAX_GRADE);
        const labWork3 = getValidatedInput('labWork3', 0, MAX_GRADE);
        
        // Compute values
        const attendanceScore = attendances;
        const labWorkAverage = (labWork1 + labWork2 + labWork3) / 3.0;
        const classStanding = (ATTENDANCE_WEIGHT * attendanceScore) + 
                             (LAB_WORK_WEIGHT * labWorkAverage);
        
        // Calculate required Prelim Exam scores
        const requiredForPassing = calculateRequiredPrelimScore(classStanding, PASSING_GRADE);
        const requiredForExcellent = calculateRequiredPrelimScore(classStanding, EXCELLENT_GRADE);
        
        // Display results
        displayResults(attendanceScore, labWork1, labWork2, labWork3,
                      labWorkAverage, classStanding,
                      requiredForPassing, requiredForExcellent);
        
    } catch (error) {
        resultsDiv.innerHTML = `<div class="error">Error: ${error.message}</div>`;
    }
}

/**
 * Gets and validates input from a form field
 */
function getValidatedInput(fieldId, min, max) {
    const element = document.getElementById(fieldId);
    const value = parseFloat(element.value);
    
    if (isNaN(value)) {
        element.focus();
        throw new Error(`Please enter a valid number for ${fieldId}`);
    }
    
    if (value < min || value > max) {
        element.focus();
        throw new Error(`${fieldId} must be between ${min} and ${max}`);
    }
    
    return value;
}

/**
 * Calculates the required Prelim Exam score to achieve a target grade
 * Formula: Prelim Exam = (Target Grade - 0.70 * Class Standing) / 0.30
 */
function calculateRequiredPrelimScore(classStanding, targetGrade) {
    return (targetGrade - (CLASS_STANDING_WEIGHT * classStanding)) / PRELIM_EXAM_WEIGHT;
}

/**
 * Displays all computed results with appropriate remarks
 */
function displayResults(attendanceScore, labWork1, labWork2, labWork3,
                       labWorkAverage, classStanding,
                       requiredForPassing, requiredForExcellent) {
    const resultsDiv = document.getElementById('results');
    
    let html = `
        <div class="results-container">
            <h2>📊 COMPUTATION RESULTS</h2>
            
            <div class="results-section">
                <h3>Input Summary</h3>
                <table>
                    <tr>
                        <td>Attendance Score:</td>
                        <td>${attendanceScore.toFixed(2)}</td>
                    </tr>
                    <tr>
                        <td>Lab Work 1 Grade:</td>
                        <td>${labWork1.toFixed(2)}</td>
                    </tr>
                    <tr>
                        <td>Lab Work 2 Grade:</td>
                        <td>${labWork2.toFixed(2)}</td>
                    </tr>
                    <tr>
                        <td>Lab Work 3 Grade:</td>
                        <td>${labWork3.toFixed(2)}</td>
                    </tr>
                </table>
            </div>
            
            <div class="results-section">
                <h3>Computed Values</h3>
                <table>
                    <tr>
                        <td>Lab Work Average:</td>
                        <td><strong>${labWorkAverage.toFixed(2)}</strong></td>
                    </tr>
                    <tr>
                        <td>Class Standing:</td>
                        <td><strong>${classStanding.toFixed(2)}</strong></td>
                    </tr>
                </table>
            </div>
            
            <div class="results-section">
                <h3>Required Prelim Exam Scores</h3>
                
                <div class="grade-requirement">
                    <h4>To PASS (75%)</h4>
                    <p class="score">${requiredForPassing.toFixed(2)}</p>
                    <p class="remark">${getRemarkForPassing(requiredForPassing)}</p>
                </div>
                
                <div class="grade-requirement">
                    <h4>To get EXCELLENT (100%)</h4>
                    <p class="score">${requiredForExcellent.toFixed(2)}</p>
                    <p class="remark">${getRemarkForExcellent(requiredForExcellent)}</p>
                </div>
            </div>
        </div>
    `;
    
    resultsDiv.innerHTML = html;
}

/**
 * Generates remark for passing grade
 */
function getRemarkForPassing(requiredScore) {
    if (requiredScore > MAX_GRADE) {
        return "❌ It is impossible to pass the Prelim period. You need a score greater than 100.";
    } else if (requiredScore < 0) {
        return "✅ You have already passed the Prelim period! Even with a score of 0 on the Prelim Exam.";
    } else {
        return `✏️ You need a Prelim Exam score of ${requiredScore.toFixed(2)} or higher to pass.`;
    }
}

/**
 * Generates remark for excellent grade
 */
function getRemarkForExcellent(requiredScore) {
    if (requiredScore > MAX_GRADE) {
        return "❌ It is impossible to achieve an excellent grade. You need a score greater than 100.";
    } else if (requiredScore < 0) {
        return "⭐ You have already achieved an excellent standing! Even with a score of 0 on the Prelim Exam.";
    } else {
        return `🎯 You need a Prelim Exam score of ${requiredScore.toFixed(2)} to achieve excellence.`;
    }
}

/**
 * Resets the form and clears results
 */
function resetForm() {
    document.getElementById('gradeForm').reset();
    document.getElementById('results').innerHTML = '';
}