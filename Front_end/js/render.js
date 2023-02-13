export const navigationPart = () => {
    return `
    <div class="nav">
        <input type="checkbox">
        <span></span>
        <span></span>
        <div class="menu">
            <li onclick="openHomePage()">home</li>
            <li onclick="openCoursesPage()">courses</li>
            <li onclick="openTrainersPage()">trainers</li>
            <li onclick="openStudentsPage()">students</li>
            <li onclick="logOut()">logout </li>
        </div>
    </div>
    `
}

export const enrolledStListHTML = (name) => {
    return ` <div onclick="removieStudent(this)">
                ${name}
            </div>
    `
  }

export const divFormOfShortInfoCourse = (obj) => {
    return `<div  onclick="openCourseDetailsPage('${obj.uuid}')" class="shortUsersShowForm">
                ${obj.name}
            </div>
    `
}
export const divFormOfShortInfo = (obj) => {
    return `<div  onclick="openUserDetailsPage('${obj.uuid}')" class="shortUsersShowForm">
                ${obj.firstName + " " + obj.lastName} 
            </div>
    `
}

export const createCheckbox = (obj) => {
    return ` <input class="checkBoxStyle" type="checkbox" id="${obj.uuid}" value="${obj.uuid}">
            <label class="labelBoxStyle" for="${obj.uuid}">${obj.name}</label>
    `
}


export const checkBoxDiv = () => {
    return ` <div id="courseCheckbox">
         
            </div>
    `
}

export const checkboxBtn = (uuid) => {
    return `<div><button onclick="sendFeedback('${uuid}')" class="addbtn feedback">Send feedback</button></div>
    `
}

export const userDetailsPage = (user) => {
    return `<div class="infoContent">
    <div class="userInfoSection">
        <div class="det">${user.role.split("_")[1]}: ${user.firstName + " " + user.lastName} </div>
        <div class="det">EMAIL:  <a href="http://gmail.com">${user.email}</a> </div>
        <div class="det">PHONE:   ${user.phoneNumber}</div>
    </div>
    <div id="trainerCourses" class="usersShortFormDiv1">
         
    </div>    
    `
}

export const openCourseInfoPage = (course) => {
    return `<div class="courseDetails">
    <div class="courseBasicInfo">
        <h2>${course.name}</h2>
        <p onclick="openUserDetailsPage('${course.trainerUuid}')">Trainer: <span class="trainerClick"> ${course.trainerFirstName + " " + course.trainerLastName}</span></p>
        <p>Start date: ${course.startDate}</p>
        <p>End date: ${course.endDate}</p>
        <p>Number of sessions: ${course.numberOfSessions}</p>
    </div>
    <div class="taskAndSession">
        <div class="tasksPart">
            <select class="selectTag" id="chooseTask">
                <option value="HOMEWORK">Homework</option>
                <option value="ASSESSMENT">Assessment</option>
            </select>
                <input id="numberOfSession" class="sessionNumber" placeholder="#5" type="number">
                <button onclick="addTaskToCourse('${course.uuid}')" class="addbtn">ADD</button>
        </div>
        <div class="sessionPart">
            <select class="selectTag" id="chooseSession">
            </select>
        </div>
    </div>
</div>
<div id="addSessionsHere" class="sessionInfo">

</div>

    `
}

export const attendanceStudent = (student,uuid) => {
    return `<div class="attendance">
    <p onclick="openUserDetailsPage('${student.uuid}')" class="parafN clickableName">${student.firstName + " " + student.lastName}</p>
    <div class="abspres">
        <p class="paraf">Attendance</p>
        ${student.present == null ? 
        `<button onclick="updateStudentAttendance(true, '${student.sessionNumber}', '${student.uuid}', '${uuid}')" class="absbtn">Present</button>
        <button onclick="updateStudentAttendance(false, '${student.sessionNumber}', '${student.uuid}', '${uuid}')"class="absbtn">Absent</button>` 
        : student.present == true ? `<p style="color: green;"class="paraf">Present</p>` : `<p style="color: red;" class="paraf">Absent</p>`}
    </div>
</div>
    `
}

export const taskStudent = (task, uuid) => {
    return `<div class="studentTask">
                <div class="taskComment">
                    <p class="paraf">Homework ${task.number}</p>
                    ${task.comment == null ? 
                    `<input id="${task.uuid + task.number}" class="commentInput" type="text">
                    <button onclick="updateTaskComment('${task.number}', '${task.uuid}', '${uuid}')" class="addbtn addbtn1">ADD</button>` : 
                    `<div class="mys">${task.comment}</div>`}
                </div>
                <div class="taskgrade">
                ${task.grade == null ? `<p class="paraf1">Grade:</p>
                <input id="${task.uuid + task.number + "grade"}" class="commentInput gradeInput" type="number">` : 
                `<p class="paraf">Grade: ${task.grade} </p>`}
    
                ${task.passed == null ? `<p class="paraf1">Status:</p>
                <button onclick="updateTaskGrade('${task.number}', '${task.uuid}', '${uuid}', true)" class="absbtn">Passed</button>
                <button onclick="updateTaskGrade('${task.number}', '${task.uuid}', '${uuid}', false)" class="absbtn">Fail</button>` : 
                `<p class="paraf">Status: ${task.passed == true ?  `<span style="color: green;">Passed</span>` : `<span style="color: red;">Failed</span>`}</p>`}
                </div>
            </div>
    `
}


        


export const trainerModalPart = () => {
    return `<div id="addTrainersHere" class="usersShortFormDiv">
        
    </div>
    <div class="content">
    <label class="modal-open modal-label" for="modal-open">
        <p>ADD TRAINER</p>
    </label>
    <input type="radio" name="modal" value="open" id="modal-open" class="modal-radio" />
    <div class="modal">
        <label class="modal-label overlay">
            <input type="radio" name="modal" value="close" class="modal-radio" />
        </label>
        <div class="content">
            <div class="top">
                <h2 class="headerName">TRAINER DETAILS</h2>
                <label class="modal-label close-btn">
                    <input type="radio" name="modal" value="close" class="modal-radio" />
                </label>
            </div>
            <form id="login-btn" class="login100-form validate-form">
            <p class="login100-form validate-form">
                <div class="wrap-input100 validate-input" data-validate = "Enter name">
                    <input id="trainerName" class="input100" type="text" name="name">
                    <span class="focus-input100" data-placeholder="Enter name"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Enter surname">
                    <input id="trainerSurname" class="input100" type="text">
                    <span class="focus-input100" data-placeholder="Enter surname"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Valid email is: a@b.c">
                    <input id="trainerEmail" class="input100" type="text" name="email">
                    <span class="focus-input100" data-placeholder="Email"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Enter phone number">
                    <input id="trainerPhone" class="input100" type="text" name="phone">
                    <span class="focus-input100" data-placeholder="Phone: 012 34 56 78"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate='Password must be at least 8 characters, contain lowercase, uppercase, number and [!@#$%^&*]'>
                    <span class="btn-show-pass">
                        <i class="zmdi zmdi-eye"></i>
                    </span>
                    <input id="trainerPassword" class="input100" type="password" name="pass">
                    <span class="focus-input100" data-placeholder="Password"></span>
                </div>
                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            Register
                        </button>
                    </div>
                </div>
            </p>
            </form>
        </div>
    </div>
</div>
    `
}  

export const studentsModalPart = () => {
    return `<div id="addStudentsHere" class="usersShortFormDiv">
        
    </div>
    <div class="content">
    <label class="modal-open modal-label" for="modal-open">
        <p>ADD STUDENT</p>
    </label>
    <input type="radio" name="modal" value="open" id="modal-open" class="modal-radio" />
    <div class="modal">
        <label class="modal-label overlay">
            <input type="radio" name="modal" value="close" class="modal-radio" />
        </label>
        <div class="content">
            <div class="top">
                <h2 class="headerName">STUDENT DETAILS</h2>
                <label class="modal-label close-btn">
                    <input type="radio" name="modal" value="close" class="modal-radio" />
                </label>
            </div>
            <form id="login-btn" class="login100-form validate-form">
            <p class="login100-form validate-form">
                <div class="wrap-input100 validate-input" data-validate = "Enter name">
                    <input id="studentName" class="input100" type="text" name="name">
                    <span class="focus-input100" data-placeholder="Course name"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Enter surname">
                    <input id="studentSurname" class="input100" type="text">
                    <span class="focus-input100" data-placeholder="Enter surname"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Valid email is: a@b.c">
                    <input id="studentEmail" class="input100" type="text" name="email">
                    <span class="focus-input100" data-placeholder="Email"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Enter phone number">
                    <input id="studentPhone"  class="input100" type="text" name="phone">
                    <span class="focus-input100" data-placeholder="Phone: 012 34 56 78"></span>
                </div>
                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            Register
                        </button>
                    </div>
                </div>
            </p>
            </form>
        </div>
    </div>
</div>
    `
}


export const courseModalPart = () => {
    return `<div id="addCoursesHere" class="usersShortFormDiv">
        
    </div>
    <div class="content">
    <label class="modal-open modal-label" for="modal-open">
        <p>CREATE COURSE</p>
    </label>
    <input type="radio" name="modal" value="open" id="modal-open" class="modal-radio" />
    <div class="modal">
        <label class="modal-label overlay">
            <input type="radio" name="modal" value="close" class="modal-radio" />
        </label>
        <div class="content">
            <div class="top">
                <h2 class="headerName">COURCE DETAILS</h2>
                <label class="modal-label close-btn">
                    <input type="radio" name="modal" value="close" class="modal-radio" />
                </label>
            </div>
            <form id="login-btn" class="login100-form validate-form">
            <p class="login100-form validate-form">
                <div class="wrap-input100 validate-input" data-validate = "Enter name">
                    <input id="courseName" class="input100" type="text" name="name">
                    <span class="focus-input100" data-placeholder="Course name"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Enter number">
                    <input id="courseNumber" class="input100" type="number">
                    <span class="focus-input100" data-placeholder="Number of sessions"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Enter date">
                    <input id="courseSdate"class="input100" placeholder="dd-mm-yyyy" value="" type="date" min="1997-01-01" max="2030-12-31">
                    <span class="focus-input100" data-placeholder="Start date"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Enter date">
                    <input id="courseEdate" class="input100" placeholder="dd-mm-yyyy" value="" type="date" min="1997-01-01" max="2030-12-31">
                    <span class="focus-input100" data-placeholder="End date"></span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Choose trainer">
                    <input id="courseTrainer" list="suggestions"id="autocompleteInput" oninput="inputInTrainer(this)" class="input100" type="text">
                    <span class="focus-input100" data-placeholder="Trainer"></span>
                    <datalist id="suggestions"></datalist>
                </div>
                <div class="wrap-input100">
                    <input list="stSuggestions" id="autocompleteStudent" oninput="inputInStudents(this)" class="input100" type="text" name="stList">
                    <span class="focus-input100" data-placeholder="Enroll students"></span>
                    
                    <datalist id="stSuggestions"></datalist>
              
                    <div id="enrolledStudents" class="studentsRoom">
                        
                    </div>                         
                </div>
                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            Create
                        </button>
                    </div>
                </div>
            </p>
            </form>
        </div>
    </div>
</div>
    `
}

export const loginPart = () => {
    return `<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <form id="login-btn" class="login100-form validate-form">
                <span class="login100-form-title p-b-26">
                    Welcome
                </span>
                <span class="login100-form-title p-b-48">
                    <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOAAAADgCAMAAAAt85rTAAAAgVBMVEX////5AXD5AG35AGn5AGf5AGz5AGT5AGr5AGP+4+3+7PP+2uf6YZn/8/j9ytz5AHL6To/7caP8iLH9v9X8j7T6QYj/+fz5GHf+1eT9tM36R4z9xNX/8Pb7Zpz9zt/+5e/8l7r6NYX8rcj8o8L6Wpb5F3r7gaz7eqn8n8D9ts75K37+d07JAAAEKklEQVR4nO2be1fiMBDFmzR9UHFBhbK6Ik9F+f4fcHHZlYVmphRqk8y5v39JzpnL3OYxSaIIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACpHy8W0wW02XpOpBvYpWYRCudmHjkOpRv4SlV/zDbW9fRtM84UweSiTibHulTKlu7DqhlTvQplf9wHVKrVPSpRFQKq/p2A42gr9CmT6UvrsNqDas+ZX65jqstnqz65AgcxlZ9YixK6ZMyyJD6hEwTpD4hEz2tT8ZSjdYnY7FN69MTCdslRp8qXAfXAoy+gQR9fen6jGx/Mvpk5K+RP8vH6WumxqMbB5FeRjN/LtVnNVHpLF8/Owj2Apr5s59//ZzEPx2E25hm/jz+N9IAVqhM/pKqvlV63MZ4v0tc0/p0r9K6TE9b+b4PnjbSF62qdjZef4d3jD8t+qKhrraMPZ4uGH22/EWRta3xVmFjfWVube1rDml9yupP2xiz/zv8VPhmj5Y13aBpB4e82O32x3LvVKe1ZZDZd/FP4ZiKVakB2WlJudo/l/boBGbMkXxC9vLNpe/kCpSduj8YY/ul8I3ORcztgjbk0KT1rLPoz2DKCGRroNzixyeFd5cKbL48cMOI/gbTmjDDyOEPepqPlzV9my7R3UAtSnZRjuv6BuHS0735f9QXIoJw6ZgcZvSg9iyJ2Sh749LbCakwqTUpV6ryxqWMwmxY27tZscoNJa0wvkqhN8cZt69XKQzgQIpx6XU5HPhS1YdLecJwKbm1l+LSwVUKA7i6UFylcGi/nKh8cmmhrlG4IDvrSQfBnwWXw35d5xmz8dp0Ef05XKVwQxcHUl9MyrnU1Cm8YVI47yL4s3imc1irkNR3xt65O65w6dMlRfLuudylTAnSJ4GXu9R27uujwGimyUhT7gkhU79adBb8WcwSUmFOD/jMRJjddxj9OdAKE3rOHpGLNZX7Up75oke6VFNdbml9Se0ioXvIHKbU6diaLj/GPl71pkYa6u3Shr7K4OldPcKlhMB7Zp3mpz7KpcZaz2f0eXzHy+pS6zwRYv4+6VVzqLeWdqHq2+2AKiOHWVVbhenPPTfxcQ5t1Ydw8/fJiULLLBi2vp3C7DCB67Q6RzD6cs9vAf+lGOZ7iUm6qJ7act9fADfV97xME2NMtn6o/jSnbzyFcBP/QDGzHrkz+kS8hZ1L8CeDGH8SiNcHfwbNu/Dxk7nKnlqmy/Cgz7tzEfoeyAFGhr6oT1XQZPhz51DZ+aPeLonRR70+k+LPHdYxVEz+IvuDJ0n6onn1XlP+4TqoNikqH2Fe9wYhMO6NbH2nd/Xl6YvKxSGHOn90Hc538Jbua8JJuvXrxWBrFKOtNvGgL2l6OKUsfLnOCwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABond/3qzGjKIjGOwAAAABJRU5ErkJggg==" alt="">
                </span>

                <div class="wrap-input100 validate-input" data-validate = "Valid email is: a@b.c">
                    <input id="u_username" class="input100" type="text" name="email">
                    <span class="focus-input100" data-placeholder="Email"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Enter password">
                    <span class="btn-show-pass">
                        <i class="zmdi zmdi-eye"></i>
                    </span>
                    <input id="u_password" class="input100" type="password" name="pass">
                    <span class="focus-input100" data-placeholder="Password"></span>
                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            Login
                        </button>
                    </div>
                </div>
                </div>
            </form>
        </div>
    </div>
    <div id="dropDownSelect1"></div>`
}


export const studentFeedbackModal = (uuid) => {
    return `<div class="content">
    <label onclick="getUserData('${uuid}')" class="modal-open modal-label" for="modal-open">
        <p>FEEDBACK</p>
    </label>
    <input type="radio" name="modal" value="open" id="modal-open" class="modal-radio" />
    <div class="modal">
        <label class="modal-label overlay">
            <input type="radio" name="modal" value="close" class="modal-radio" />
        </label>
        <div class="content">
            <div class="top">
                <h2 class="headerName">STUDENT DETAILS</h2>
                <label class="modal-label close-btn">
                    <input type="radio" name="modal" value="close" class="modal-radio" />
                </label>
            </div>
            <div class="mys" id="userEmail"></div>
            <div class="mys" id="userAtt"></div>
            <div id="generalResponseDtoId" class="courseResponseDto">
                
            </div>
            <div><button onclick="sendFeedback('${uuid}')" class="addbtn feedback">Send feedback</button></div>
        </div>
    </div>
</div>
    `
}

export const taskResponseDto = (course) => {
    return ` <div class="taskDto">
                <p class="mys qw">${course.name}</p>
                <div id="${course.name}" class="tasksStyle">

                </div>
    </div>
    `
}

export const singleTask = (task) => {
    return ` <div class="taskStyle">
        <p class="mys" >${task.type + " " + task.number}</p>
        <div class="mys" >Comment: ${" " + task.comment}</div>
        <p class="mys" >
        <span>Grade:  <span style="color: blue;">${" " + task.grade}</span></span>
        <span style="margin-left: 15px;">Status: ${" " + (task.passed == "true" ? `<span style="color: green;">Passed</span>` : `<span style="color: red;">Failed</span>`)}</span>
        </p>
    </div>
    `
}
