/**
 * 
 */
function loadRegister() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 200 || this.status == 0)) {
			document.getElementById("register-div").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "Register.jsp", true);
	xhttp.send();
	document.getElementById("register-div").style.display = "block";
};

function setCookie(cname,cvalue,exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function checkCookie() {
    var email=getCookie("email");
    var surname=getCookie("surname");
    var name=getCookie("name");
    var username=getCookie("username");
    if (username != "") {
        alert("Welcome again \nUsername : " + username+"\nName : " + name+"\nSurname : " + surname+"\nEmail : "+ email);
    } 
}
function dellCookie(){
	document.cookie = "email=;name=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
	sessionStorage.clear();
	location.reload(true);
}

function RegisterUser()
{
	
	var x = document.getElementById("register_div");
	x.style.display = "none";
}

function closeRegisterWindow(){
	var x = document.getElementById("register-div");
	x.style.display = "none";
}

function loadResponseCreateUser(){
	document.getElementById("register-div").style.display = "block";
}
function LoadIndexbyCreateUser(){
	var x = document.getElementById("done");
	if(x == null || x =="")
	{
		dellCookie();
	}
	else
	{
		username = document.getElementById("username").innerHTML;
		name = document.getElementById("name").innerHTML;
		email = document.getElementById("email").innerHTML;
		surname = document.getElementById("surname").innerHTML;
		if (username != "" && username != null && email != "" && email != null) {
			sessionStorage.setItem("username", username);
			setCookie("username", username, 30);
			setCookie("name", name, 30);
			setCookie("email", email, 30);
			setCookie("surname", surname, 30);
			//alert("Welcome again \nUsername : " + username+"\nName : " + name+"\nSurname : " + surname+"\nEmail : "+ email);
		}
	}
	window.location.href = "WebContent/index.html";
}
function LoadIndexbyLoginUser(){
	var x = document.getElementById("done");
	if(x == null || x =="")
	{
		dellCookie();
	}
	else{
		var username = document.getElementById("username").innerHTML;
		var name = document.getElementById("name").innerHTML;
		var email = document.getElementById("email").innerHTML;
		var surname = document.getElementById("surname").innerHTML;
		sessionStorage.setItem("username", username);
		setCookie("username", username, 30);
		setCookie("name", name, 30);
		setCookie("email", email, 30);
		setCookie("surname", surname, 30);
	}
	window.location.href = "WebContent/index.html";
}
function LoadIndex()
{
	var email=getCookie("email");
    var surname=getCookie("surname");
    var name=getCookie("name");
    var username=getCookie("username");	
    if (username != "" && name != "" && surname != "" && email != "") {
    	var lin = document.getElementById("login");
   		lin.style.display = "none";
    	var loff =  document.getElementById("logged");

    	var a = document.createElement("a");
    	loff.appendChild(a);
    	a.classList.add("dropdown-item");
    	a.innerHTML += ""+username+""

    	var d = document.createElement("div"); 
    	loff.appendChild(d);
    	d.classList.add("dropdown-divider");

    	var str = document.createElement("a");
    	loff.appendChild(str);
    	str.classList.add("dropdown-item");
    	str.innerHTML += "Log out";
    	str.setAttribute('onClick', "dellCookie()");

    	loff.style.display = "block";
    }
    else
    {
    	var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && (this.status == 200 || this.status == 0)) {
				document.getElementById("loginform").innerHTML = this.responseText;
			}
		};
		xhttp.open("GET", "login.jsp", true);
		xhttp.send();
		document.getElementById("loginform").style.display = "block";

    }
}





function loadBar()
{
    var xhttp = new XMLHttpRequest();
    var email=getCookie("email");
    var surname=getCookie("surname");
    var name=getCookie("name");
    var username=getCookie("username");
    var userSession = sessionStorage.getItem("username");
    if(username!="" && username!=null && userSession != null)
    {
       var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && (this.status == 200 || this.status == 0)) {
                document.getElementById("nav-bar-gemfu").innerHTML = this.responseText;
                document.getElementById("user-logged").innerHTML = username;
            }
        };
        xhttp.open("GET", "navbar-logout.html", true);
        xhttp.send();

        insertUser();
    }
    else
    {
     	xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && (this.status == 200 || this.status == 0)) {
                document.getElementById("nav-bar-gemfu").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "navbar-login.html", true);
        xhttp.send(); 

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && (this.status == 200 || this.status == 0)) {
                document.getElementById("loginform").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "login.jsp", true);
        xhttp.send();

    }
  
}



function insertUser(){
    var username = getCookie("username");
    var userURL = 'http://localhost:8080/GEMFU-2.0/rest/user/' + username;

    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
         alert('Giving up :( Cannot create an XMLHTTP instance');
         return false;
    }
    httpRequest.onreadystatechange = function () {
            setProfileImage();
        };
    httpRequest.open('GET', userURL);
    httpRequest.send();

    function setProfileImage(imgEl){
        //alert("httpRequest.status: "+httpRequest.status+"\nhttpRequest.readyState: "+httpRequest.readyState+"\nTEXT: "+httpRequest.responseText);
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status == 200){
                var jsonData = JSON.parse(httpRequest.responseText);
                var user = jsonData['users'];
                var imageText = user['profileImage'];
                if(imageText != null && imageText!= ""){

                  document.getElementById("noimage").style.display = "none";
                  var x = document.getElementById("prof-img");
                  var img = document.createElement("img");
                  img.src = imageText;
                  x.appendChild(img);
                  img.className = "reg_img_small";
                }
                else{
                    document.getElementById("noimage").style.display = "none";
                    var x = document.getElementById("prof-img");
                    var img = document.createElement("img");
                    img.src = "noimg-profile.png";
                    x.appendChild(img);
                    img.className = "reg_img_small";
                }
            }
        }
    }
}
function handleFiles(file) {
	    var fileElem = document.getElementById("profile");
	    if (!file.length) {
	        fileElem.innerHTML = "<p>No files selected!</p>";
	    } else {
	        fileElem.innerHTML = "";
	        var img = document.createElement("img");
	        img.src = window.URL.createObjectURL(file[0]);
	        fileElem.appendChild(img);
	        img.classList.add("reg_img");
	        img.classList.add("justify-content-end");
	        

	        var str = document.getElementById("profileImage");
	        var reader = new FileReader();
	        reader.readAsDataURL(file[0]);
	        reader.onload = function () {
	            str.value = reader.result;
	            //alert(str.value);
	        };
	    }
	};
function comingSoon(){
    alert("Coming Soon");
  }