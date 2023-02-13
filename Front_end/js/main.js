import {
    navigationPart,
    loginPart,
    courseModalPart,
    studentsModalPart,
    trainerModalPart,
    divFormOfShortInfo,
    divFormOfShortInfoCourse,
    userDetailsPage,
    openCourseInfoPage,
    attendanceStudent,
    taskStudent,
    createCheckbox,
    checkBoxDiv,
    checkboxBtn,
    studentFeedbackModal,
    taskResponseDto,
    singleTask
} from './render.js'

import {
    trainerListeners,
    inputValidator,
    findInput100,
    studentsListener,
    studentsList,
    trainersList,
    separateUsers
} from './index.js'


import {
    get_users,
    get_courses,
    get_spec_user,
    get_user_by_uuid,
    get_course_details,
    add_task_to_course,
    update_attendance,
    update_grade,
    update_comment,
    get_feedback,
    sendMail
} from './request.js'
    
    window.onload = () => {
        console.log(location.pathname + " " + "here am i");
        if(location.pathname.includes('/home')){
            context.innerHTML = navigationPart();
            openHomePage();
        } else if (location.pathname.includes('/courses')) {
            context.innerHTML = navigationPart();
            openCoursesPage();
        } else if (location.pathname.includes('/students')){
            context.innerHTML = navigationPart();
            openStudentsPage();
        }else if (location.pathname.includes('/trainers')){
            context.innerHTML = navigationPart();
            openTrainersPage();
        }
        else if (location.pathname.includes("/") || location.pathname.includes("/login")){
            console.log("onload");
            context.innerHTML = loginPart();
            findInput100();
            inputValidator();
        }
    }

    window.addEventListener("popstate", e => {
        if (location.pathname.includes("home")){
            context.innerHTML = navigationPart();
        } else if (location.pathname.includes("student")){
            openStudentsPage();
        } else if (location.pathname.includes("course")) {
            openCoursesPage();
        } else if (location.pathname.includes("trainer")){
            openTrainersPage();
        }
        else if (location.pathname.includes("") || location.pathname.includes("login")){
            context.innerHTML = loginPart();
            findInput100();
            inputValidator();
        }
    }) 
 
    window.logOut = () => {
        context.innerHTML = loginPart();
        findInput100();
        inputValidator();
        localStorage.removeItem('jwt');
        history.pushState(null, null, `/`)
    }
 
 
    
 
    window.validate = (input) => { 
        if (input.getAttribute('type') == 'email' || input.getAttribute('name') == 'email') { 
            if (!input.value.trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/)) { 
                return false; 
            } 
        } else if (input.getAttribute('type') == 'password' || input.getAttribute('name') == 'pass') { 
            if (!input.value.trim().match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/)) { 
                return false; 
            } 
        } else if (input.getAttribute('type') == 'phone' || input.getAttribute('name') == 'phone') { 
            if (!input.value.trim().match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{2})[-. ]?([0-9]{2})?[-. ]?([0-9]{2})/) && 
                !input.value.trim().match(/^\d{9}$/)) 
                return false; 
        } 
        else { 
            if (input.value.trim() == '') { 
                return false; 
            } 
        } 
    } 

    let val = -1;
    window.openCourseDetailsPage = (uuid) => {
        let response = get_course_details(uuid);
        response.then(data => {
            history.pushState(data, null, `/courses/${data.name}`)
            main.innerHTML = openCourseInfoPage(data);
            let option = document.createElement("option");
            option.value = 0;
            option.text = 'Sessions';
            chooseSession.appendChild(option);
            for (let i = 1; i <= data.numberOfSessions; ++i){
                option = document.createElement("option");
                option.value = i;
                option.text = 'Session ' + i;
                chooseSession.appendChild(option);
            }

            let sessionArray = data.sessions;
            for (let i = 0; i < sessionArray.length; ++i) {
                let session = document.createElement("div");
                session.classList.add("session");
                let name = document.createElement("h3");
                name.innerHTML = 'Session ' + sessionArray[i].attendance[0].sessionNumber;
                session.appendChild(name);

                let attendanceList = sessionArray[i].attendance;
                let tasksList = sessionArray[i].homeworks;
                for (let j = 0; j < attendanceList.length; ++j) {
                   let student = document.createElement("div");
                   student.classList.add("student");
                   student.innerHTML += attendanceStudent(attendanceList[j], uuid);

                   if (tasksList.length == 0) {
                    student.innerHTML += `<div class="studentTask">No Homework yet.</div>`
                   } else {
                    student.innerHTML += taskStudent(tasksList[j], uuid);
                   }
                   session.appendChild(student);
                }

                addSessionsHere.appendChild(session);
            }

                let childNodes = addSessionsHere.childNodes;
                for (let i = 0; i < childNodes.length; i++) {
                    if(childNodes[i].nodeName === "DIV" && val != i){
                        childNodes[i].style.display = 'none';
                    }

                chooseSession.addEventListener("change", function(event) {
                    val = event.target.value;
                    if (val != 0)
                        childNodes[val].style.display = 'flex';
                    for (let i = 0; i < childNodes.length; i++) {
                        if(childNodes[i].nodeName === "DIV" && i != val){
                            childNodes[i].style.display = 'none';
                        }
                    }
                })

            }
        });
    }

    window.addTaskToCourse = (courseUuid) => {
        let obj = {};
        obj.course_uuid = courseUuid;
        obj.number = numberOfSession.value;
        obj.taskType = chooseTask.value;
        let response = add_task_to_course(obj);
        response.then(data => {
            openCourseDetailsPage(courseUuid);
        })
    }

    window.updateTaskComment = (number, sUudi, cUuid) => {
        let comment = document.getElementById(sUudi + number).value;
        let obj = {};
        obj.courseUuid = cUuid;
        obj.userUuid = sUudi;
        obj.sessionNumber = number;
        obj.comment = comment;
        let resop = update_comment(obj);
        resop.then(data => {
            openCourseDetailsPage(cUuid);
        })
    }

    window.updateTaskGrade = (number, sUudi, cUuid, state) => {
        let grade = document.getElementById(sUudi + number + "grade").value;
        let obj = {};
        obj.courseUuid = cUuid;
        obj.userUuid = sUudi;
        obj.sessionNumber = number;
        obj.passed = state;
        obj.grade = grade;
        let response = update_grade(obj);
        response.then(r => {
            openCourseDetailsPage(cUuid);
        })
    }

    window.updateStudentAttendance = (state, number, stUuid, cUuid) => {
        let obj = {};
        obj.courseUuid = cUuid;
        obj.userUuid = stUuid;
        obj.sessionNumber = number;
        obj.isPresent = state;
        let response = update_attendance(obj);
        response.then(r => {
            openCourseDetailsPage(cUuid);
        })
    }

    window.openUserDetailsPage = (uuid) => {
        let response = get_user_by_uuid(uuid);
        response.then(r => {
            history.pushState(r, null, `${r.role.toLowerCase().split("_")[1]}s/${r.firstName + r.lastName}`)
            main.innerHTML = userDetailsPage(r);
            main.innerHTML += checkBoxDiv();
            r.courses.forEach(course => {
                trainerCourses.innerHTML += divFormOfShortInfoCourse(course)
                if (r.role == 'ROLE_STUDENT')
                    courseCheckbox.innerHTML += createCheckbox(course);
            })
            if (r.role == 'ROLE_STUDENT')
                courseCheckbox.innerHTML += studentFeedbackModal(uuid);
        })
    }


    let userInfo = {};
    window.sendFeedback = (uuid) => {
        console.log(userInfo);
        sendMail(userInfo);
        
    }

    window.getUserData = (uuid) => {
        generalResponseDtoId.innerHTML = "";
        const checkboxes = courseCheckbox.querySelectorAll("input[type='checkbox']");
        const selectedValues = [];
        for (let i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
              selectedValues.push(checkboxes[i].value);
              checkboxes[i].checked = false;
            }
        }
        let obj = {};
        obj.userUuid = uuid;
        obj.courseUuids = selectedValues;
        let resp = get_feedback(obj);
        resp.then(r => {
            userInfo = r;
            let courses = r.courseFeedbackDtos;
            userAtt.innerHTML = "Attendance: " + r.attendancePercentage;
            userEmail.innerHTML = "Email: " +r.userEmail;
            courses.forEach(course => {
                generalResponseDtoId.innerHTML += taskResponseDto(course);
                let tasks = course.taskFeedbackDtos;
                let tasksDto = document.getElementById(course.name);
                tasks.forEach(task => {
                    tasksDto.innerHTML += singleTask(task);
                })
            })
            
            
        })
    }

    window.removieStudent = (target) => {
            target.parentNode.remove();
      }


    window.openTrainersPage = () => {
        if(!(location.pathname === "/trainers"))
            history.pushState(null, null, "/trainers");
        main.innerHTML = trainerModalPart();
        findInput100();

        let trainers = get_spec_user("trainer");
        trainers.then(data => {
            data.forEach(e => {
                addTrainersHere.innerHTML += divFormOfShortInfo(e);
            })
        })

        inputValidator();
    }


    window.openStudentsPage = () => {
        if(!(location.pathname === "/students"))
            history.pushState(null, null, "/students");
        main.innerHTML = studentsModalPart();
        findInput100();

        let students = get_spec_user("student");
        students.then(data => {
            data.forEach(e => {
                addStudentsHere.innerHTML += divFormOfShortInfo(e);
            })
        })

        inputValidator();
    }

      
    window.openCoursesPage = () => {
        if(!(location.pathname === "/courses"))
            history.pushState(null, null, "/courses");
        console.log("openCoursesPage");
        main.innerHTML = courseModalPart();
        let dates = document.querySelectorAll("input[type='date']");
        dates.forEach(date => {
            date.addEventListener("change", e => {
                if (e.target.value != "")
                    e.target.style.color = "gray";
                else 
                    e.target.style.color = "white";
            })
        })
        findInput100();
        
        let courseList = get_courses();
        courseList.then(courses => courses.forEach(element => {
            addCoursesHere.innerHTML += divFormOfShortInfoCourse(element);
        }))

       
        let userList = get_users();
        userList.then(data => {
            separateUsers(data);
            trainerListeners();
            studentsListener();
            inputValidator();
        });
    }

    window.openHomePage = () => {
        if(!(location.pathname === "/home"))
            history.pushState(null, null, "/home");
        main.innerHTML = "";
    }
    
 
    window.showValidate = (input) => { 
        var thisAlert = input.parentNode; 
 
        thisAlert.classList.add('alert-validate'); 
    }
    window.hideValidate = (input) => { 
        var thisAlert = input.parentNode; 
 
        thisAlert.classList.remove('alert-validate'); 
    } 
 
    var showPass = 0; 
    var btnShowPass = document.querySelector('.btn-show-pass'); 
    if(btnShowPass != null){
        btnShowPass.addEventListener('click', function () { 
            if (showPass == 0) { 
                var input = this.nextElementSibling; 
                input.setAttribute('type', 'text'); 
                this.querySelector('i').classList.remove('zmdi-eye'); 
                this.querySelector('i').classList.add('zmdi-eye-off'); 
                showPass = 1; 
            } 
            else { 
                var input = this.nextElementSibling; 
                input.setAttribute('type', 'password'); 
                this.querySelector('i').classList.add('zmdi-eye'); 
                this.querySelector('i').classList.remove('zmdi-eye-off'); 
                showPass = 0; 
            } 
        }); 
    }
    
 