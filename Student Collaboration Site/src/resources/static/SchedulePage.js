function populateCourseContainer(studyGroups) {
    var courseContainer = document.querySelector(".courseContainer");
    courseContainer.innerHTML = '';
    console.log(studyGroups);
    const groupedStudyGroups = groupByCoursename(studyGroups);

    groupedStudyGroups.forEach((groups, coursename) => {
        var courseDiv = document.createElement("div");
        courseDiv.classList.add("course");

        var courseTitleDiv = document.createElement("div");
        courseTitleDiv.classList.add("courseTitle");

        // Generate a subtle color for each course
        const hue = (coursename.charCodeAt(0) * 137.508) % 360;
        const saturation = 60 + (coursename.length * 5) % 20;
        const bgColor = `hsla(${hue}, ${saturation}%, 50%, 0.15)`;

        var heading = document.createElement("h2");
        heading.textContent = coursename;
        heading.style.backgroundColor = bgColor;
        heading.style.borderColor = `hsla(${hue}, ${saturation}%, 50%, 0.3)`;
        courseTitleDiv.appendChild(heading);

        courseDiv.appendChild(courseTitleDiv);

        var labelDiv = document.createElement("div");
        labelDiv.classList.add("label");

        ["Status", "Location", "Time", "Size"].forEach(function (labelText) {
            var label = document.createElement("span");
            label.textContent = labelText;
            labelDiv.appendChild(label);
        });

        courseDiv.appendChild(labelDiv);

        var detailsDiv = document.createElement("div");
        detailsDiv.classList.add("details");

        groups.forEach(group => {
            var groupDetailsDiv = document.createElement("div");
            groupDetailsDiv.classList.add("groupDetails");

            let statusBar = document.createElement("span");
            statusBar.textContent = 'available';

            var joinBtn = document.createElement("button");
            joinBtn.classList.add("join-btn");
            joinBtn.textContent = "Join";
            
            if (sessionStorage.getItem('loggedInUser')) {
                joinBtn.style.backgroundColor = '';
                joinBtn.style.background = 'linear-gradient(135deg, #10b981 0%, #059669 100%)';

                let buttonClicked = false;

                function handleJoinButtonClick() {
                    if (!buttonClicked) {
                        let email = sessionStorage.getItem('loggedInUser');
                        if (email) {
                            makeFetchRequest(group.courseId, email, statusBar, groupDetailsDiv, group);
                        } else {
                            console.error('Email not found in sessionStorage');
                        }

                        joinBtn.style.background = 'rgba(255, 255, 255, 0.1)';
                        joinBtn.style.color = 'rgba(255, 255, 255, 0.7)';
                        joinBtn.style.cursor = 'not-allowed';
                        joinBtn.textContent = "Joined";
                        joinBtn.disabled = true;

                        buttonClicked = true;
                    }
                }

                joinBtn.addEventListener('click', handleJoinButtonClick);
            } else {
                joinBtn.style.background = 'rgba(255, 255, 255, 0.1)';
                joinBtn.style.color = 'rgba(255, 255, 255, 0.7)';
                joinBtn.style.cursor = 'not-allowed';
                joinBtn.textContent = "Join";
                joinBtn.disabled = true;
            }

            var detailsParagraph = document.createElement("p");
            detailsParagraph.innerHTML = `${statusBar.textContent} | ${group.location} - ${group.meetingDate} ${group.meetingTimeStart} | Size: ${group.capacity}`;
            
            groupDetailsDiv.appendChild(detailsParagraph);
            groupDetailsDiv.appendChild(joinBtn);
            detailsDiv.appendChild(groupDetailsDiv);
        });

        courseDiv.appendChild(detailsDiv);
        courseContainer.appendChild(courseDiv);
    });
}

function logUserOut() {
    sessionStorage.removeItem('loggedInUser');
}

function groupByCoursename(studyGroups) {
    const groupedStudyGroups = new Map();

    studyGroups.forEach(group => {
        const coursename = group.coursename;

        if (!groupedStudyGroups.has(coursename)) {
            groupedStudyGroups.set(coursename, []);
        }

        groupedStudyGroups.get(coursename).push(group);
    });

    return groupedStudyGroups;
}

function getCookie(name) {
    let cookieArray = document.cookie.split('; ');
    let cookie = cookieArray.find(row => row.startsWith(name + '='));
    return cookie ? cookie.split('=')[1] : null;
}

function makeFetchRequest(courseId, email, statusBar, groupDetailsDiv, group) {
    const requestData = {
        courseId: courseId,
        email: email
    };

    fetch(`/addToStudyMembers?courseId=${courseId}&email=${email}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
        .then(response => {
            if (!response.ok) {
                console.error('Server responded with status:', response.status);
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(text => {
            if (text.startsWith("Failed")) {
                statusBar.textContent = 'Joined Already';

                var p = groupDetailsDiv.querySelector('p');
                console.log(p);

                p.textContent = `${statusBar.textContent} | ${group.location} - ${group.meetingDate} ${group.meetingTimeStart} | Size: ${group.capacity}`;
                
                console.log(statusBar.textContent);
            } else {
                statusBar.textContent = 'Joined';
                requestAnimationFrame(() => {
                    console.log(statusBar.textContent);
                    var p = groupDetailsDiv.querySelector('p');
                    console.log(p);
    
                    p.textContent = `${statusBar.textContent} | ${group.location} - ${group.meetingDate} ${group.meetingTimeStart} | Size: ${group.capacity}`;
                    
                });
                
            }
            console.log('Response text:', text);
        })
        .catch((error) => {
            console.error('Fetch error:', error);
            
        });
}

document.addEventListener('DOMContentLoaded', function () {
    const loggedIn = document.getElementById('LoggedIn');
    const NotloggedIn = document.getElementById('notLoggedIn');
    const userEmail = sessionStorage.getItem('loggedInUser');
    
    if (userEmail) {
        sessionStorage.setItem('loggedInUser', userEmail);
        if (NotloggedIn) NotloggedIn.style.display = 'none';
        if (loggedIn) loggedIn.style.display = 'flex';
    } else {
        if (NotloggedIn) NotloggedIn.style.display = 'flex';
        if (loggedIn) loggedIn.style.display = 'none';
    }

    fetch('/SchedulePageServlet')
        .then(response => response.json())
        .then(data => {
            populateCourseContainer(data);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
});
