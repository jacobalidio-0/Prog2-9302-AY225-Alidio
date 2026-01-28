// Predefined login credentials - multiple users with different passwords
const validUsers = {
    "kazuaki": "kazu2024",
    "akito": "akito@123",
    "jacob": "jacob456",
    "kazume": "kazume789",
    "ame": "ame!pass"
};

// Valid courses array
const validCourses = [
    "BSIT-GD 1st Year", "BSIT-GD 2nd Year", "BSIT-GD 3rd Year", "BSIT-GD 4th Year",
    "BSCS-DS 1st Year", "BSCS-DS 2nd Year", "BSCS-DS 3rd Year", "BSCS-DS 4th Year"
];

// Get DOM elements
const loginForm = document.getElementById('loginForm');
const alertMessage = document.getElementById('alertMessage');
const attendanceSummary = document.getElementById('attendanceSummary');
const errorBeep = document.getElementById('errorBeep');
const downloadBtn = document.getElementById('downloadBtn');

// Store attendance data
let attendanceData = {};

/**
 * Function to display alert messages
 */
function showAlert(message, type) {
    alertMessage.textContent = message;
    alertMessage.className = `alert ${type}`;
    
    // Auto-hide alert after 3 seconds
    setTimeout(() => {
        alertMessage.style.display = 'none';
    }, 3000);
}

/**
 * Function to play error beep sound
 */
function playErrorBeep() {
    errorBeep.currentTime = 0; // Reset to start
    errorBeep.play().catch(error => {
        console.log('Error audio play failed:', error);
    });
}

/**
 * Function to play success beep sound using Web Audio API
 */
function playSuccessBeep() {
    const audioContext = new (window.AudioContext || window.webkitAudioContext)();
    const oscillator = audioContext.createOscillator();
    const gainNode = audioContext.createGain();
    
    oscillator.connect(gainNode);
    gainNode.connect(audioContext.destination);
    
    oscillator.frequency.value = 800; // Higher frequency for success
    oscillator.type = 'sine';
    
    gainNode.gain.setValueAtTime(0.3, audioContext.currentTime);
    gainNode.gain.exponentialRampToValueAtTime(0.01, audioContext.currentTime + 0.3);
    
    oscillator.start(audioContext.currentTime);
    oscillator.stop(audioContext.currentTime + 0.3);
}

/**
 * Function to get current timestamp
 */
function getCurrentTimestamp() {
    const now = new Date();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const year = now.getFullYear();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    
    return `${month}/${day}/${year} ${hours}:${minutes}:${seconds}`;
}

/**
 * Function to generate UUID for e-signature
 */
function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

/**
 * Function to save attendance to file automatically
 */
function saveAttendanceToFile(data) {
    // Create attendance record text
    const attendanceRecord = `
ATTENDANCE RECORD
==================

Username: ${data.username}
Course/Year: ${data.course}
Login Timestamp: ${data.timestamp}
E-Signature: ${data.eSignature}

==================
Status: Successfully Logged In
    `.trim();
    
    // Create blob and download link
    const blob = new Blob([attendanceRecord], { type: 'text/plain' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    
    // Create filename with username and timestamp
    const fileTimestamp = new Date().toISOString().replace(/[:.]/g, '-').slice(0, -5);
    link.download = `attendance_${data.username}_${fileTimestamp}.txt`;
    
    // Trigger automatic download
    link.click();
    
    // Clean up
    window.URL.revokeObjectURL(link.href);
}

/**
 * Handle form submission
 */
loginForm.addEventListener('submit', function(e) {
    e.preventDefault(); // Prevent form from submitting normally
    
    // Get input values
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    
    // Validate credentials - check if username exists and password matches
    if (validUsers[username] && validUsers[username] === password) {
        // Successful login
        const timestamp = getCurrentTimestamp();
        const eSignature = generateUUID();
        
        // Prompt for course/year
        let course = prompt("Please enter your Course/Year:\n\nValid options:\n" + validCourses.join("\n"));
        
        // Validate course
        if (course) {
            course = course.trim();
            const isValidCourse = validCourses.some(validCourse => 
                validCourse.toLowerCase() === course.toLowerCase()
            );
            
            if (!isValidCourse) {
                showAlert('Invalid Course/Year! Please enter a valid course.', 'error');
                playErrorBeep();
                return;
            }
        } else {
            showAlert('Course/Year is required!', 'error');
            playErrorBeep();
            return;
        }
        
        // Store attendance data
        attendanceData = {
            username: username,
            course: course,
            timestamp: timestamp,
            eSignature: eSignature
        };
        
        // Automatically save attendance to file
        saveAttendanceToFile(attendanceData);
        
        // Display welcome message BEFORE hiding the form
        alert('Welcome, ' + username + '! Your attendance has been recorded successfully.');
        
        // Also show it in the alert box
        showAlert('Welcome, ' + username + '! Attendance recorded.', 'success');
        
        // Hide login form
        loginForm.style.display = 'none';
        
        // Display attendance summary
        document.getElementById('displayUsername').textContent = username;
        document.getElementById('displayCourse').textContent = course;
        document.getElementById('displayTimestamp').textContent = timestamp;
        document.getElementById('displaySignature').textContent = eSignature;
        attendanceSummary.style.display = 'block';
        
    } else {
        // Failed login
        showAlert('Incorrect username or password! Please try again.', 'error');
        playErrorBeep(); // Play beep sound on incorrect password
        
        // Clear password field
        document.getElementById('password').value = '';
    }
});

/**
 * Download attendance summary as text file (manual download option)
 */
downloadBtn.addEventListener('click', function() {
    // Create attendance summary text
    const summaryText = `
ATTENDANCE SUMMARY
==================

Username: ${attendanceData.username}
Course/Year: ${attendanceData.course}
Login Timestamp: ${attendanceData.timestamp}
E-Signature: ${attendanceData.eSignature}

==================
Generated on: ${getCurrentTimestamp()}
    `.trim();
    
    // Create blob and download link
    const blob = new Blob([summaryText], { type: 'text/plain' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = 'attendance_summary.txt';
    
    // Trigger download
    link.click();
    
    // Clean up
    window.URL.revokeObjectURL(link.href);
    
    // Show confirmation
    showAlert('Attendance summary downloaded successfully!', 'success');
});