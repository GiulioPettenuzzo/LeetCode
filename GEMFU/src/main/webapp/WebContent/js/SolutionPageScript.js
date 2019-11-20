function loadSolution() {
    var httpRequest;
    var id = getQueryVariable("id");
    var url = 'http://localhost:8080/GEMFU-2.0/rest/problem/' + id;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert("Giving up :( Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = solutionResponse;
    httpRequest.open("GET", url);
    httpRequest.send();

    function solutionResponse() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {
                var jsonData = JSON.parse(httpRequest.responseText);
                var problem = jsonData["problem"];

                var div = document.getElementById("solution-textarea");

                div.innerHTML = problem["solution"];

            } else {
                alert("There was a problem with the request.");
            }
        }
    }
}


function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) { return pair[1]; }
    }
    return (false);
}

function loadComments() {
    var httpRequest;
    var id = getQueryVariable("id");
    var url = 'http://localhost:8080/GEMFU-2.0/rest/comment/problem/' + id;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert("Giving up :( Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = commentResponse;
    httpRequest.open("GET", url);
    httpRequest.send();

    function commentResponse() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {
                var jsonData = JSON.parse(httpRequest.responseText);
                var commentList = jsonData["resource-list"];
                document.getElementById("n-comments").textContent = commentList.length;
                var commArea = document.getElementById("comments-area");
                console.log(jsonData);
                for (var i = 0; i < commentList.length; i++) {
                    var comment = commentList[i].comment;

                    var commentDiv = document.createElement("div");
                    commentDiv.classList.add("comment","row");

                    var imgDiv = document.createElement("div");
                    imgDiv.classList.add("col-md-2");
                    var img = document.createElement("img");
                    img.classList.add("com-img");
                    img.src = "gemfu.png";
                    getProfileImage(comment["username"], img);
                    imgDiv.appendChild(img);
                    commentDiv.appendChild(imgDiv);

                    var supportDiv = document.createElement("div");
                    supportDiv.classList.add("col-md-10");

                    var row = document.createElement("div");
                    row.classList.add("row");

                    var info = document.createElement("div");
                    var username = document.createElement("label");
                    username.classList.add("com-user-label");
                    username.textContent = comment["username"];
                    info.appendChild(username);
                    var date = document.createElement("label");
                    date.classList.add("com-info-label");
                    date.textContent = comment["date"];
                    info.appendChild(date);
                    row.appendChild(info);
                    supportDiv.appendChild(row);

                    var row2 = document.createElement("div");
                    row2.classList.add("row");

                    var text = document.createElement("article");
                    text.classList.add("CK-textarea", "text-comment");
                    text.innerHTML = comment["text"];
                    row2.appendChild(text);
                    supportDiv.appendChild(row2);

                    commentDiv.appendChild(supportDiv);

                    commArea.appendChild(commentDiv);
                }

             

            } else {
                alert("There was a problem with the request.");
            }
        }
    }
}

function getProfileImage(username, imgEl){
   // var user = getCookie("username");
    var userURL = 'http://localhost:8080/GEMFU-2.0/rest/user/' + username;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
         alert('Giving up :( Cannot create an XMLHTTP instance');
         return false;
    }
    httpRequest.onreadystatechange = function () {
            setProfileImage(imgEl);
        };
    httpRequest.open('GET', userURL, false);
    httpRequest.send();

    function setProfileImage(imgEl){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status == 200){
                var jsonData = JSON.parse(httpRequest.responseText);
                var user = jsonData['users'];
                var imageText = user['profileImage'];

                if((imageText != null)&&(imageText != "")){
                    imgEl.src = imageText;
                }
            }
        }
    }
}

function addComment() {
    var user = getCookie("username");
    var text = CKEDITOR.instances["ins-comment"].getData();
    var problem = parseInt(getQueryVariable("id"));
    var date = new Date();
    var strDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

    var url = "http://localhost:8080/GEMFU-2.0/rest/comment";
    var tmp = { "comment": { "problem": problem, "username": user, "text": text, "date": strDate} };
    var comment = JSON.stringify(tmp);
    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = alertCommCreation;
    httpRequest.open("POST", url, true);
    httpRequest.setRequestHeader("Content-type", "application/json");
    httpRequest.send(comment);


    function alertCommCreation() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 201) {
                var commArea = document.getElementById("comments-area");
                var listComm = document.getElementsByClassName("comment");
                for (var i = listComm.length-1; i > -1; i--) {
                    console.log(i);
                    console.log(commArea.removeChild(listComm[i]));
                }
                loadComments();
                CKEDITOR.instances["ins-comment"].setData("");

            } else {
                alert("Something went wrong!");
            }
        }
    }
}