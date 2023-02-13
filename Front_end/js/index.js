import {
  navigationPart,
  enrolledStListHTML
} from './render.js'

import {
  get_users,
  add_course,
  add_student,
  authUser
} from './request.js'

export const autocompleateTrainer = (datalist) => {
  let suggestions = [];

  trainersList.forEach(u => {
    if (!suggestions.includes(u.firstName + " " + u.lastName)){
      let name = u.firstName + " " + u.lastName;
      suggestions.push(name);
    }
  })

  if (datalist != null && datalist.options.length === 0) {
    suggestions.forEach(function (suggestion) {
      var option = document.createElement("option");
      option.value = suggestion;
      if (datalist != null) {
        datalist.appendChild(option);
      }
    });
  }
  return datalist;
}


export const autocompleateStudents = (datalist) => {
  let suggestions = [];

  studentsList.forEach(u => {
    if (!suggestions.includes(u.firstName + " " + u.lastName)){
      let name = u.firstName + " " + u.lastName;
      suggestions.push(name);
    }
  })


  if (datalist != null && datalist.options.length === 0) {
    suggestions.forEach(function (suggestion) {
      var option = document.createElement("option");
      option.value = suggestion;
      if (datalist != null) {
        datalist.appendChild(option);
      }
    });
  }

  return datalist;
}

export const trainerListeners = () => {
  let datalist = document.getElementById("suggestions");
  datalist = autocompleateTrainer(datalist);
  window.inputInTrainer = (line) => {
    let datalist = line.parentElement.children.item(2);
    datalist = autocompleateTrainer(datalist);
    var inputValue = line.value;
    for (var i = 0; i < datalist.options.length; i++) {
      var option = datalist.options[i];

      if (option.value.toLowerCase().startsWith(inputValue.toLowerCase())) {
        option.style.display = "block";
      } else {
        option.style.display = "none";
      }
    }
  }
}

export const inputValidator = () => {
  var input = document.querySelectorAll('.validate-input .input100');


  input.forEach(function (input) {
    input.addEventListener('focus', function () {
      hideValidate(input);
    });
  });

  var validateForm = document.querySelector('.validate-form');
  validateForm.addEventListener('submit', function (event) {
    event.preventDefault();

    var check = true;

    for (var i = 0; i < input.length; i++) {
      if (validate(input[i]) == false) {
        showValidate(input[i]);
        check = false;
      }
    }
    if (check) {
      if (location.pathname.includes("students")) {
        console.log("in students");
        let studentDat = {};
        studentDat.name = studentName.value;
        studentDat.surname = studentSurname.value;
        studentDat.email = studentEmail.value;
        studentDat.phone = studentPhone.value;
        studentDat.password = "";
        studentDat.role = "ROLE_STUDENT";
        let resp = add_student(studentDat);
        resp.then(() => openStudentsPage());
      } else if (location.pathname.includes("trainers")) {
        console.log("in trainers");
        let trainerDat = {};
        trainerDat.name =     trainerName.value;
        trainerDat.surname =  trainerSurname.value;
        trainerDat.email =    trainerEmail.value;
        trainerDat.phone =    trainerPhone.value;
        trainerDat.password = trainerPassword.value;
        trainerDat.role = "ROLE_TRAINER";
        let resp = add_student(trainerDat);
        resp.then( () => openTrainersPage());
      } else if (location.pathname.includes("courses")) {
        console.log("in courses");
        let courseDet = {};
        courseDet.name = courseName.value;
        courseDet.numberOfSessions = courseNumber.value;
        courseDet.startDate = courseSdate.value;
        courseDet.endDate = courseEdate.value;
        let uuidList = [];
        trainersList.forEach(t => {
          if (courseTrainer.value === (t.firstName + " " + t.lastName)) {
            uuidList.push(t.uuid);
          }
        })
        for (var i = 0; i < enrolledStudents.children.length; i++) {
          var child = enrolledStudents.children[i];
          studentsList.forEach(s => {
            if (child.innerText === s.firstName + " " + s.lastName) {
              uuidList.push(s.uuid)
            }
          })
        }
        courseDet.userUuids = uuidList;
        console.log(courseDet);
        let response = add_course(courseDet);
        response.then(() => openCoursesPage());

      } else {
        let logObj = {};
        logObj.username = u_username.value;
        logObj.password = u_password.value;
        console.log(logObj);
        let res = authUser(logObj);
        res.then(t => {
          if (t === 200){
            history.pushState(null, null, "/home")
            let userList = get_users();
            userList.then(data => separateUsers(data));
            console.log("in root");
            context.innerHTML = navigationPart();
          }
        })
      }
    }

    return check;
  });
}

export let studentsList = [];
export let trainersList = [];

export let separateUsers = (listOfUsers) => {

  let realStudentList = [];
  let realTrainerList = [];

  for (let i = 0; i < listOfUsers.length; ++i) {
    if (listOfUsers[i].role === "ROLE_TRAINER") {
      realTrainerList.push(listOfUsers[i]);
    } else if (listOfUsers[i].role === "ROLE_STUDENT") {
      realStudentList.push(listOfUsers[i]);
    }
  }
  studentsList = realStudentList;
  trainersList = realTrainerList;
}

export const findInput100 = () => {
  var input100 = document.querySelectorAll('.input100');
  input100.forEach((input) => {
    input.addEventListener('blur', function () {
      if (input.value.trim() != "") {
        input.classList.add('has-val');
      }
      else {
        input.classList.remove('has-val');
      }
    });
  });
}


export const studentsListener = () => {
  let stSuggestions = document.getElementById("stSuggestions");
  stSuggestions = autocompleateStudents(stSuggestions);
  window.inputInStudents = (line) => {
    var inputValue = line.value;
    for (var i = 0; i < stSuggestions.options.length; i++) {
      var option = stSuggestions.options[i];

      if (option.value.toLowerCase().startsWith(inputValue.toLowerCase())) {
        option.style.display = "block";
      } else {
        option.style.display = "none";
      }
    }
  }

  let stList = document.getElementsByName("stList");
  if (stList[0] != null) {
    stList[0].addEventListener("change", function (e) {
      let selectedOption = e.target.value;
      var selectedValueDivChild = document.createElement("div");
      selectedValueDivChild.innerHTML = enrolledStListHTML(selectedOption);
      selectedValueDivChild.classList.add("enSt")
      if (enrolledStudents != null) {
        enrolledStudents.appendChild(selectedValueDivChild);
        e.target.value = "";
      }
    });
  }
}