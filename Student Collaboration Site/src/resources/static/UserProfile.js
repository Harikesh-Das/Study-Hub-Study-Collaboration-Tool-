function editName() {
    document.getElementById('nameDisplay').style.display = 'none';
    document.getElementById('editNameForm').style.display = 'block';
}

function saveName() {
    var newName = document.getElementById('nameInput').value;
    document.getElementById('nameSpan').textContent = newName;
    document.getElementById('nameDisplay').style.display = 'block';
    document.getElementById('editNameForm').style.display = 'none';
}

function cancelNameEdit() {
    document.getElementById('nameDisplay').style.display = 'block';
    document.getElementById('editNameForm').style.display = 'none';
}

function editPhone() {
    document.getElementById('phoneDisplay').style.display = 'none';
    document.getElementById('editPhoneForm').style.display = 'flex';
}

function savePhone() {
    var newPhone = document.getElementById('phoneInput').value;
    if (newPhone.trim() !== '') {
        document.getElementById('phoneSpan').textContent = newPhone;
    }
    document.getElementById('phoneDisplay').style.display = 'flex';
    document.getElementById('editPhoneForm').style.display = 'none';
}

function cancelPhoneEdit() {
    document.getElementById('phoneDisplay').style.display = 'flex';
    document.getElementById('editPhoneForm').style.display = 'none';
    document.getElementById('phoneInput').value = '';
}

function editPass() {
    document.getElementById('passDisplay').style.display = 'none';
    document.getElementById('editPassForm').style.display = 'block';
}

function savePass() {
    var newPass = document.getElementById('passInput').value;
    document.getElementById('passSpan').textContent = '••••••••';
    document.getElementById('passDisplay').style.display = 'block';
    document.getElementById('editPassForm').style.display = 'none';
}

function cancelPassEdit() {
    document.getElementById('passDisplay').style.display = 'block';
    document.getElementById('editPassForm').style.display = 'none';
}

function deleteCourse(button) {
    if (confirm("Are you sure you want to delete this course?")) {
        var row = button.parentNode.parentNode;
        row.parentNode.removeChild(row);
    }
}

function addCourse() {
    var table = document.getElementById("scheduleTable");
    var tbody = table.querySelector('tbody') || table.createTBody();
    var row = tbody.insertRow(-1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);

    var newCourseInput = document.getElementById("newCourseInput");
    var newCourseName = newCourseInput.value.trim();

    if (newCourseName === "") {
        alert("Please enter a course name.");
        return;
    }

    cell1.innerHTML = newCourseName;
    cell2.innerHTML = "<button onclick='deleteCourse(this)'>Delete</button>";

    // Clear the input field after adding the course.
    newCourseInput.value = "";
}

// Populate study groups
document.addEventListener('DOMContentLoaded', function () {
    var email = sessionStorage.getItem('loggedInUser');

    if (email) {
        fetch('/getUserName?email=' + email)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(userName => {
            console.log(`User Name: ${userName}`);
            var fullNameElement = document.getElementById('fullName');
            if (fullNameElement) {
                fullNameElement.innerText = userName;
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });

        fetch('UserStudyGroupServlet?email=' + email)
            .then(response => response.json())
            .then(data => {
                populateStudyGroupContainer(data);
                console.log(email);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });

        var emailValueElement = document.getElementById('emailValue');
        if (emailValueElement) {
            emailValueElement.innerText = email;
        }
    }
});

function populateStudyGroupContainer(studyGroups) {
    var table = document.getElementById("groupsTable");
    var tbody = table.querySelector('tbody');
    
    if (!tbody) {
        tbody = document.createElement('tbody');
        table.appendChild(tbody);
    }

    // Clear existing rows (except header)
    tbody.innerHTML = '';

    for (var i = 0; i < studyGroups.length; i++) {
        var studyGroup = studyGroups[i];

        var row = tbody.insertRow(-1);
        var courseNameCell = row.insertCell(0);
        var meetingDateCell = row.insertCell(1);
        var meetingTimeStartCell = row.insertCell(2);
        var capacityCell = row.insertCell(3);
        var locationCell = row.insertCell(4);
        var actionsCell = row.insertCell(5);

        courseNameCell.innerHTML = studyGroup.coursename;
        meetingDateCell.innerHTML = studyGroup.meetingDate;
        meetingTimeStartCell.innerHTML = studyGroup.meetingTimeStart;
        capacityCell.innerHTML = studyGroup.capacity;
        locationCell.innerHTML = studyGroup.location;

        actionsCell.innerHTML = "<button onclick='deleteGroup(\"" + studyGroup.coursename + "\")'>Delete</button>";
    }
}

// Delete account
function deleteAccount() {
    if (!confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
        return;
    }

    var emailElement = document.getElementById("emailValue");
    if (!emailElement) {
        console.error('Email element not found');
        return;
    }

    var email = emailElement.textContent.trim();
    console.log("Deleting account for:", email);

    fetch('/deleteUser/' + email)
    .then(response => response.text())
    .then(result => {
        alert(result);
        sessionStorage.removeItem('loggedInUser');
        window.location.href = "/";
    })
    .catch(error => {
        console.error('Error deleting account:', error);
        alert('Failed to delete account. Please try again.');
    });
}

// Delete group
function deleteGroup(coursename) {
    if (!confirm("Are you sure you want to leave this study group?")) {
        return;
    }

    var emailElement = document.getElementById("emailValue");
    if (!emailElement) {
        console.error('Email element not found');
        return;
    }

    var email = emailElement.textContent.trim();
    console.log("Removing user from group:", email, coursename);

    fetch('/deleteUserFromGroup/' + email + '/' + coursename)    
    .then(response => response.text())
    .then(result => {
        alert(result);
        location.reload();
    })
    .catch(error => {
        console.error('Error deleting user from group:', error);
        alert('Failed to leave group. Please try again.');
    });
}

function logUserOut() {
    sessionStorage.removeItem('loggedInUser');
}
