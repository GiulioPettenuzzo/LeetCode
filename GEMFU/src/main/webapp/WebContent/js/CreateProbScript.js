/**
 * 
 */
var companiesList = [];
var categoriesList = [];
var selectedCategories = [];
var solutionTitle = "";
var solution = "";

function showSolutionWindow() {
    var x = document.getElementById("solution-div");
    x.style.display = "block";
    var back = document.getElementById("obscure");
    back.style.display = "block";
}

function closeSolutionWindow(){
	var x = document.getElementById("solution-div");
    x.style.display = "none";
    document.getElementsByName("sol-title")[0].value = "";
    CKEDITOR.instances["ins-sol-desc"].setData("");
    var back = document.getElementById("obscure");
    back.style.display = "none";
}

function highlightYes(){
	var y = document.getElementById("yes-btn");
	y.classList.add("btn-dark");
	var n = document.getElementById("no-btn");
	showCompanyFields();
	if(n.classList.contains("btn-dark"))
		n.classList.remove("btn-dark");
}

function highlightNo(){
	var n = document.getElementById("no-btn");
	n.classList.add("btn-dark");
	var y = document.getElementById("yes-btn");
	hideCompanyFields();
	if(y.classList.contains("btn-dark"))
		y.classList.remove("btn-dark");
}

function showCompanyFields(){
	var x = document.getElementById("company-div");
    x.style.display = "block";
	var y = document.getElementById("ask-comp-div");
	if(y.classList.contains("final"))
		y.classList.remove("final");
}

function hideCompanyFields(){
	var x = document.getElementById("company-div");
    x.style.display = "none";
    var y = document.getElementById("ask-comp-div");
	if(!y.classList.contains("final"))
		y.classList.add("final");
}

function newThreadDialog(){
    var x = document.getElementById("create-discussion");
    x.style.display = "block";
}

function closeThreadDialog(){
	var x = document.getElementById("create-discussion");
	x.style.display = "none";
}

function deleteOnClick() {
    var company = document.getElementsByName("companies")[0];
    company.value = "";
}

(function() {
    var httpRequest;
    var sub = document.getElementById("submit-prob");
    if (sub) {
        sub.addEventListener("click", createProb);
    }
    var problemID;

    function createProb() { 
        var title = document.getElementsByName("title")[0].value;
        var description = CKEDITOR.instances["ins-desc"].getData();
        var date = new Date();
        var username = getCookie("username");
        var difficulty;
        var tmpDiff = document.getElementsByName("difopt");
        for (var i = 0; i < tmpDiff.length; i++) {
            if (tmpDiff[i].checked) {
                difficulty = tmpDiff[i].value;
            }
        }
        var strDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        var sol = null;
        var problem;
        if(solution != ""){
            if (solutionTitle == "") {
                solutionTitle = "Solution";
            }
            sol = "<h2>" + solutionTitle + "</h2>" + "</br>" + solution;
            problem = { "problem": { "title": title, "description": description, "date": strDate, "username": username, "difficulty": difficulty, "solution":sol} };
        }
        else{
            problem = { "problem": { "title": title, "description": description, "date": strDate, "username": username, "difficulty": difficulty} };
        }

        if (title == "") {
            alert("Please insert a title for your problem!");
            return;
        }
        if (description == "") {
            alert("Please insert a description for your problem!");
            return;
        }

        if (selectedCategories.length < 1) {
            alert("Please choose at least one category for your problem!");
            return;
        }

        if (document.getElementById("company-div").style.display != "none") {
            var company = document.getElementsByName("companies")[0];
            if (company.value == "") {
                alert("Please choose a company or select the no button!");
                return;
            }
            else if (!companiesList.includes(company.value)) {
                alert("The company that you have selected doesn't exist! Please choose a company in the list or add a new company!");
                company.value = "";
                return;
            }
        }

        var jProb = JSON.stringify(problem);
        var url = "http://localhost:8080/GEMFU-2.0/rest/problem";

        httpRequest = new XMLHttpRequest();
        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open("POST", url, true);
        httpRequest.setRequestHeader("Content-type", "application/json");
        httpRequest.send(jProb);
    }

    function alertContents() {
        console.log();
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 201) {
                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData["problem"];
                problemID = resource["id"];
                //document.getElementById("prova").innerHTML = resource["description"];

                console.log(resource["description"]);

                alert("Problem successfully created!");
                associateCategories();
                if (document.getElementById("company-div").style.display != "none") {
                    associateCompany();
                }
                window.location.href = "index.html";
            } else {
                alert("Something went wrong!");
            }
        }
    }

    function associateCategories() {
        var url = "http://localhost:8080/GEMFU-2.0/rest/probcat";
        for (var i = 0; i < selectedCategories.length; i++) {
            var tmp = { "probcat": { "problem": problemID, "category": selectedCategories[i] } };
            var probcat = JSON.stringify(tmp);
            httpRequest = new XMLHttpRequest();
            if (!httpRequest) {
                alert('Giving up :( Cannot create an XMLHTTP instance');
                return false;
            }
            httpRequest.onreadystatechange = alertProbCat;
            httpRequest.open("POST", url, true);
            httpRequest.setRequestHeader("Content-type", "application/json");
            httpRequest.send(probcat);
        }
    }

    function alertProbCat() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 201) {

            } else {
            }
        }
    }

    function associateCompany() {
        var company = document.getElementsByName("companies")[0];
        var url = "http://localhost:8080/GEMFU-2.0/rest/probcomp";
        var tmp = { "probcomp": { "problem": problemID, "company": company.value } };
        var probcomp = JSON.stringify(tmp);
        httpRequest = new XMLHttpRequest();
        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertProbComp;
        httpRequest.open("POST", url, true);
        httpRequest.setRequestHeader("Content-type", "application/json");
        httpRequest.send(probcomp);
    }

    function alertProbComp() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 201) {
            } else {
            }
        }
    }
})();

function loadCategories() {
    var httpRequest;
    var url = "http://localhost:8080/GEMFU-2.0/rest/category";
    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = alertCategory;
    httpRequest.open("GET", url);
    httpRequest.send();


    function alertCategory() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {
                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];
                for (var i = 0; i < resource.length; i++) {
                    var category = resource[i].category;
                    var button = document.createElement("button");
                    button.classList.add("category-btn");
                    button.id = category["name"];
                    categoriesList.push(category["name"]);
                    button.appendChild(document.createTextNode(category["name"]));
                    document.getElementById("categories").appendChild(button);
                }
            } else {
                alert("Something went wrong!");
            }

            var catBtns = document.getElementsByClassName("category-btn");
            console.log(catBtns);
            for (var i = 0; i < catBtns.length; i++) {
                document.getElementById(catBtns[i].getAttribute("id")).addEventListener("click", toggleCategory);
                console.log(document.getElementById(catBtns[i].getAttribute("id")));
            }


            function toggleCategory() {
                var btn = document.activeElement;
                console.log(btn);
                if (btn.classList.contains("selectedcat-btn")) {
                    var name = btn.textContent;
                    var index = selectedCategories.indexOf(name);
                    selectedCategories.splice(index, 1);
                    btn.classList.remove("selectedcat-btn");
                }
                else {
                    var name = btn.textContent;
                    selectedCategories.push(name);
                    btn.classList.add("selectedcat-btn");
                }
                console.log(selectedCategories);
            }
        }
    }
}

(function () {
    var httpRequest;
    var button = document.getElementById("comp-btn");
    if (button) {
        button.addEventListener("click", addCompany);
    }

    function addCompany() {
        var name = document.getElementById("companies2").value;
        var children = document.getElementById("comps").children;
        for (var i = 1; i < children.length; i++) {
            if (children[i].value.toLowerCase() == name.toLowerCase()) {
                document.getElementById("companies2").value = "";
                document.getElementsByName("companies")[0].value = children[i].value;
                return;
            }
        }
        var tmp = { "company": { "name": name } };
        var company = JSON.stringify(tmp);

        var url = "http://localhost:8080/GEMFU-2.0/rest/company";

        httpRequest = new XMLHttpRequest();
        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertCompany;
        httpRequest.open("POST", url, true);
        httpRequest.setRequestHeader("Content-type", "application/json");
        httpRequest.send(company);
    }

    function alertCompany() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 201) {
                document.getElementById("companies2").value = "";
                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData["company"];
                var company = resource["name"];
                var el = document.createElement("option");
                el.value = company;
                document.getElementById("comps").appendChild(el);
                document.getElementsByName("companies")[0].value = company;
                companiesList.push(company);
            } else {
                alert("Something went wrong!");
            }
        }
    }
})();

function loadCompanies() {
    var httpRequest;
    var url = "http://localhost:8080/GEMFU-2.0/rest/company";
    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = alertLoadCompany;
    httpRequest.open("GET", url);
    httpRequest.send();


    function alertLoadCompany() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {
                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];
                for (var i = 0; i < resource.length; i++) {
                    var company = resource[i].company;
                    var el = document.createElement("option");
                    el.value = company["name"];
                    document.getElementById("comps").appendChild(el);
                    companiesList.push(company["name"]);
                }
            } else {
                alert("Something went wrong!");
            }
        }
    }
}

function saveSolution() {
    solutionTitle = document.getElementsByName("sol-title")[0].value;
    solution = CKEDITOR.instances["ins-sol-desc"].getData();

    var cancel = document.createElement("button");
    cancel.setAttribute("type", "button");
    cancel.setAttribute("id", "can-sol-btn");
    cancel.setAttribute("onclick", "deleteSolution()");
    cancel.classList.add("btn", "btn-dark", "sol-btn");
    cancel.appendChild(document.createTextNode("Delete Solution"));

    var modify = document.createElement("button");
    modify.setAttribute("type", "button");
    modify.setAttribute("id", "mod-sol-btn");
    modify.setAttribute("onclick", "modifySolution()");
    modify.classList.add("btn", "btn-dark", "sol-btn");
    modify.appendChild(document.createTextNode("Modify Solution"));

    var parent = document.getElementById("sol-btns");
    parent.removeChild(document.getElementById("add-sol-btn"));
    parent.appendChild(cancel);
    cancel.insertAdjacentElement("afterend", modify);
    document.getElementById("sol-label").textContent = "Nice, you have uploaded a solution!";
    closeSolutionWindow();
}

function deleteSolution() {
    solutionTitle = "";
    document.getElementsByName("sol-title")[0].value = "";
    solution = "";
    CKEDITOR.instances["ins-sol-desc"].setData("");

    var add = document.createElement("button");
    add.setAttribute("type", "button");
    add.setAttribute("id", "add-sol-btn");
    add.setAttribute("onclick", "showSolutionWindow()");
    add.classList.add("btn", "btn-dark", "sol-btn");

    var icon = document.createElement("i");
    icon.classList.add("fa", "fa-plus");
    icon.textContent = "Add a solution";

    add.appendChild(icon);
    document.getElementById("sol-btns").removeChild(document.getElementById("mod-sol-btn"));
    document.getElementById("sol-btns").removeChild(document.getElementById("can-sol-btn"));
    document.getElementById("sol-btns").appendChild(add);
    document.getElementById("sol-label").textContent = "If you have a solution for this problem upload it!";
}

function modifySolution() {
    document.getElementsByName("sol-title")[0].value = solutionTitle;
    CKEDITOR.instances["ins-sol-desc"].setData(solution);

    var submit = document.getElementById("sub-sol-btn");
    submit.setAttribute("onclick", "submitModify()");

    showSolutionWindow();
}

function submitModify() {
    solutionTitle = document.getElementsByName("sol-title")[0].value;
    solution = CKEDITOR.instances["ins-sol-desc"].getData();
    closeSolutionWindow();
}