const url = "http://10.212.20.48:8989";


export async function get_users() {
    const result = await fetch(`${url}/dashboard/users`, { 
        method: "GET",
        headers: {
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        }
    });

    const parsObj = await result.json();

    return parsObj;
}
export async function sendMail(obj) {
    const result = await fetch(`${url}/sendMailWithAttachment`, { 
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        },
        body: JSON.stringify(obj)
    });

    if (result.status === 200){
        alert("Feedback sent.")
    }
}



export async function get_spec_user(user_type) {
    const result = await fetch(`${url}/dashboard/users/group/${user_type}`, { 
        method: "GET",
        headers: {
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        } 
    });

    const parsObj = await result.json();

    return parsObj;
}

export async function update_grade(obj) {
    const result = await fetch(`${url}/tasks/grade`, { 
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        } ,
        body: JSON.stringify(obj)
    });

    const parsObj = result;

    return parsObj;
}
export async function get_feedback(obj) {
    const result = await fetch(`${url}/feedback`, { 
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        } ,
        body: JSON.stringify(obj)
    });

    const parsObj = await result.json();

    return parsObj;
}


export async function update_comment(obj) {
    const result = await fetch(`${url}/tasks/comment`, { 
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        } ,
        body: JSON.stringify(obj)
    });

    const parsObj = result;

    return parsObj;
}

export async function get_user_by_uuid(uuid) {
    const result = await fetch(`${url}/dashboard/users/${uuid}`, { 
        method: "GET",
        headers: {
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        }
     });

    const parsObj = await result.json();

    return parsObj;
}

export async function add_course(course) {
    const result = await fetch(`${url}/dashboard/courses/register`, { 
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        },
        body: JSON.stringify(course)
    });

    const parsObj = await result.json();

    return parsObj;
}

export async function get_course_details(uuid) {
    const result = await fetch(`${url}/dashboard/courses/${uuid}`, { 
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        },
    });

    const parsObj = await result.json();

    return parsObj;
}

export async function add_task_to_course(obj) {
    const result = await fetch(`${url}/tasks/register`, { 
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        },
        body: JSON.stringify(obj)
    });

    const parsObj = await result.json();

    return parsObj;
}

export async function update_attendance(obj) {
    const result = await fetch(`${url}/attendance`, { 
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        },
        body: JSON.stringify(obj)
    });

    const parsObj = result;

    return parsObj;
}



export async function add_student(student) {
    const result = await fetch(`${url}/register`, { 
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        },
        body: JSON.stringify(student)
    });
    if (result.status === 200){
        alert("Student Registered")
    }
    const parsObj = await result.json();

    return parsObj;
}


export async function authUser(obj) {
    const result = await fetch(`${url}/auth/user/login`, { 
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept' : 'application/json'
        },
        body: JSON.stringify(obj)
    });
    const parsObj = await result.json();
    localStorage.setItem("jwt", parsObj.jwt)
    return result.status;
}


export async function get_courses() {
    const result = await fetch(`${url}/dashboard/courses`, { 
        method: "GET",
        headers: {
            'Authorization': `${"Bearer " + localStorage.getItem("jwt")}`
        }
    });

    const parsObj = await result.json();

    return parsObj;
}