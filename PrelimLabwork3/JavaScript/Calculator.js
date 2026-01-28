<<<<<<< HEAD
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
=======
// Get form and input elements
const form = document.getElementById('gradesForm');
const attendanceInput = document.getElementById('attendanceCount');
const excusedAbsencesInput = document.getElementById('excusedAbsences');
const lab1Input = document.getElementById('lab1Grade');
const lab2Input = document.getElementById('lab2Grade');
const lab3Input = document.getElementById('lab3Grade');

// Create results display area
const resultsDiv = document.createElement('div');
resultsDiv.id = 'results';
resultsDiv.className = 'results';
document.querySelector('.container').appendChild(resultsDiv);

// Add event listeners to all inputs
[attendanceInput, excusedAbsencesInput, lab1Input, lab2Input, lab3Input].forEach(input => {
  input.addEventListener('input', calculateGrades);
});

function calculateGrades() {
  // Get input values
  let attendanceCount = parseFloat(attendanceInput.value) || 0;
  let excusedAbsences = parseFloat(excusedAbsencesInput.value) || 0;
  let lab1 = parseFloat(lab1Input.value) || 0;
  let lab2 = parseFloat(lab2Input.value) || 0;
  let lab3 = parseFloat(lab3Input.value) || 0;

  // Enforce attendance limit (0-5)
  if (attendanceCount < 0) {
    attendanceCount = 0;
    attendanceInput.value = 0;
  } else if (attendanceCount > 5) {
    attendanceCount = 5;
    attendanceInput.value = 5;
  }

  // Enforce excused absences limit (0-5)
  if (excusedAbsences < 0) {
    excusedAbsences = 0;
    excusedAbsencesInput.value = 0;
  } else if (excusedAbsences > 5) {
    excusedAbsences = 5;
    excusedAbsencesInput.value = 5;
  }

  // Hard limit: attendance + excused absences cannot exceed 5
  if (attendanceCount + excusedAbsences > 5) {
    // Adjust the most recently changed value
    const attendanceMax = 5 - excusedAbsences;
    attendanceCount = Math.max(0, attendanceMax);
    attendanceInput.value = attendanceCount;
  }

  // Calculate total effective attendance (attendance + excused absences)
  let totalAttendance = attendanceCount + excusedAbsences;

  // Enforce lab work grade limits (0-100)
  if (lab1 < 0) {
    lab1 = 0;
    lab1Input.value = 0;
  } else if (lab1 > 100) {
    lab1 = 100;
    lab1Input.value = 100;
  }

  if (lab2 < 0) {
    lab2 = 0;
    lab2Input.value = 0;
  } else if (lab2 > 100) {
    lab2 = 100;
    lab2Input.value = 100;
  }

  if (lab3 < 0) {
    lab3 = 0;
    lab3Input.value = 0;
  } else if (lab3 > 100) {
    lab3 = 100;
    lab3Input.value = 100;
  }

  // Check if all required inputs have values (excused absences is optional)
  if (!attendanceInput.value || !lab1Input.value || !lab2Input.value || !lab3Input.value) {
    resultsDiv.innerHTML = '<p class="info">Please enter all values to see results.</p>';
    return;
  }

  // Validate attendance range
  if (attendanceCount < 0 || attendanceCount > 5) {
    resultsDiv.innerHTML = '<p class="warning">⚠️ Attendance must be between 0 and 5.</p>';
    return;
  }

  // Validate excused absences range
  if (excusedAbsences < 0 || excusedAbsences > 5) {
    resultsDiv.innerHTML = '<p class="warning">⚠️ Excused absences must be between 0 and 5.</p>';
    return;
  }

  // Validate that attendance + excused absences doesn't exceed 5
  if (totalAttendance > 5) {
    resultsDiv.innerHTML = '<p class="warning">⚠️ Total attendance (physical + excused) cannot exceed 5.</p>';
    return;
  }

  // Validate lab grades range
  if (lab1 < 0 || lab1 > 100 || lab2 < 0 || lab2 > 100 || lab3 < 0 || lab3 > 100) {
    resultsDiv.innerHTML = '<p class="warning">⚠️ Lab work grades must be between 0 and 100.</p>';
    return;
  }

  // Calculate unexcused absences
  const unexcusedAbsences = 5 - totalAttendance;

  // Check if student has 4 or more unexcused absences (failed due to absences)
  if (unexcusedAbsences >= 4) {
    let html = '<h2>Results</h2>';
    html += '<div class="result-section">';
    html += '<h3>Course Status</h3>';
    html += `<p class="warning" style="font-size: 1.1em; font-weight: bold;">❌ FAILED DUE TO EXCESSIVE ABSENCES</p>`;
    html += `<p><strong>Physical Attendances:</strong> ${attendanceCount} out of 5</p>`;
    if (excusedAbsences > 0) {
      html += `<p><strong>Excused Absences:</strong> ${excusedAbsences}</p>`;
    }
    html += `<p><strong>Unexcused Absences:</strong> ${unexcusedAbsences} out of 5</p>`;
    html += '<p style="margin-top: 16px; color: #d32f2f;">Students with 4 or more unexcused absences automatically fail the course, regardless of grades.</p>';
    html += '</div>';
    resultsDiv.innerHTML = html;
    return;
  }

  // Calculate Lab Work Average
  const labWorkAverage = (lab1 + lab2 + lab3) / 3;

  // Calculate Attendance Score (5 attendances = 100%)
  const maxAttendances = 5;
  const attendanceScore = (totalAttendance / maxAttendances) * 100;

  // Calculate Class Standing
  const classStanding = (attendanceScore * 0.40) + (labWorkAverage * 0.60);

  // Calculate Required Prelim Exam Scores
  // Formula: Prelim Grade = (Prelim Exam × 0.70) + (Class Standing × 0.30)
  // Rearranged: Prelim Exam = (Prelim Grade - Class Standing × 0.30) / 0.70

  const passingGrade = 75;
  const excellentGrade = 100;

  const requiredForPassing = (passingGrade - (classStanding * 0.30)) / 0.70;
  const requiredForExcellent = (excellentGrade - (classStanding * 0.30)) / 0.70;

  // Determine student's standing
  let standing = '';
  let standingClass = '';
  
  if (classStanding >= 90) {
    standing = 'Excellent! You have a very strong class standing.';
    standingClass = 'excellent';
  } else if (classStanding >= 75) {
    standing = 'Good! You have a passing class standing.';
    standingClass = 'good';
  } else {
    standing = 'You need to improve your class standing.';
    standingClass = 'needs-improvement';
  }

  // Build results HTML
  let html = '<h2>Results</h2>';
  
  html += '<div class="result-section">';
  html += '<h3>Computed Values</h3>';
  html += `<p><strong>Physical Attendances:</strong> ${attendanceCount} out of 5</p>`;
  if (excusedAbsences > 0) {
    html += `<p><strong>Excused Absences:</strong> ${excusedAbsences}</p>`;
  }
  html += `<p><strong>Unexcused Absences:</strong> ${unexcusedAbsences} out of 5</p>`;
  html += `<p><strong>Total Effective Attendance:</strong> ${totalAttendance} out of 5</p>`;
  html += `<p><strong>Attendance Score:</strong> ${attendanceScore.toFixed(2)}%</p>`;
  html += `<p><strong>Lab Work 1:</strong> ${lab1.toFixed(2)}</p>`;
  html += `<p><strong>Lab Work 2:</strong> ${lab2.toFixed(2)}</p>`;
  html += `<p><strong>Lab Work 3:</strong> ${lab3.toFixed(2)}</p>`;
  html += `<p><strong>Lab Work Average:</strong> ${labWorkAverage.toFixed(2)}</p>`;
  html += `<p><strong>Class Standing:</strong> ${classStanding.toFixed(2)}</p>`;
  html += '</div>';

  html += '<div class="result-section">';
  html += '<h3>Required Prelim Exam Scores</h3>';
  
  if (requiredForPassing <= 0) {
    html += `<p class="excellent"><strong>To Pass (75):</strong> You've already secured a passing grade! (Required: ${requiredForPassing.toFixed(2)})</p>`;
  } else if (requiredForPassing > 100) {
    html += `<p class="warning"><strong>To Pass (75):</strong> ${requiredForPassing.toFixed(2)} - Unfortunately, passing is mathematically impossible with your current class standing.</p>`;
  } else {
    html += `<p><strong>To Pass (75):</strong> ${requiredForPassing.toFixed(2)}</p>`;
  }

  if (requiredForExcellent <= 0) {
    html += `<p class="excellent"><strong>For Excellent (100):</strong> You've already secured an excellent grade! (Required: ${requiredForExcellent.toFixed(2)})</p>`;
  } else if (requiredForExcellent > 100) {
    html += `<p class="info"><strong>For Excellent (100):</strong> ${requiredForExcellent.toFixed(2)} - This would require more than 100% on the exam.</p>`;
  } else {
    html += `<p><strong>For Excellent (100):</strong> ${requiredForExcellent.toFixed(2)}</p>`;
  }
  html += '</div>';

  html += '<div class="result-section">';
  html += '<h3>Student Standing</h3>';
  html += `<p class="${standingClass}">${standing}</p>`;
  html += '</div>';

  resultsDiv.innerHTML = html;
}

// Initial calculation if there are default values
calculateGrades();
>>>>>>> e646995 (Submission)
