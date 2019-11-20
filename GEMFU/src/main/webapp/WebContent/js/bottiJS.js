$(document).ready(function(){
      //makeTitle();
      $("#home").load("prob_description.html");
      document.getElementById("sub_div").style.display = "block";
      var username = getCookie("username");
       var userSession = sessionStorage.getItem("username");
    	if(username!="" && username!=null && userSession != null)
    	{
    		document.getElementById("submission-tab").style.display = "block";
    	}
    	else
    	{
    		document.getElementById("submission-tab").style.display = "none";
    	}
});

document.getElementById("home-tab").addEventListener("click", function(){
   $("#home").load("prob_description.html");
   document.getElementById("sub_div").style.display = "block";
});

document.getElementById("submission-tab").addEventListener("click", function(){
   //document.getElementById("contact").innerHTML = "";
   $("#submission").load("prob_subs.html");
   document.getElementById("sub_div").style.display = "block";
});

document.getElementById("contact-tab").addEventListener("click", function(){
	//document.getElementById("profile").innerHTML = "";
   $("#contact").load("prob_hint.html");
   document.getElementById("sub_div").style.display = "block";
});

document.getElementById("solution-tab").addEventListener("click", function() {
    $("#solution").load("SolutionPage.html");
    document.getElementById("sub_div").style.display = "none";
    //div.style.display = "none";
});


$(document).on('click','#div_pop',function(){
        $("#sol_description").css("display","none");
        $("#div_pop").css("display","none");

    });

function makeTitle() {
    var id = getQueryVariable("id");
    var url = 'http://localhost:8080/GEMFU-2.0/rest/problem/'+ id;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
      alert('Giving up :( Cannot create an XMLHTTP instance');
      return false;
    }
    httpRequest.onreadystatechange = alertContents2;
    httpRequest.open('GET', url);
    httpRequest.send();
  }

  function alertContents2() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
      
      if (httpRequest.status == 200) {
          var jsonData = JSON.parse(httpRequest.responseText);
          var problem = jsonData['problem'];

          var h = document.getElementById('title');
          
          h.innerHTML = problem['title'];

      } else {
        alert('There was a problem with the request.');
      }
    }
  }

   function getQueryVariable(variable)
  {
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
  }

  document.getElementById("submission_button").addEventListener("click", function(){
   
   var username=getCookie("username");
   var userSession = sessionStorage.getItem("username");
   var des = CKEDITOR.instances["text_sub"].getData();
   if(username!="" && username!=null && userSession != null)
   {
   if(document.getElementById("title_sub").value.length === 0 || !document.getElementById("title_sub").value.trim() || des.length === 0 || !des.trim())
   	{
   		alert("Text field and Title field must not be empty!");
   	}
   else
   {
   	createSub();
   }
	}
	else
	{
		alert("You must have to be logged to submit!");
	}
});

 function createSub(variable)
 {
 	var problem = getQueryVariable("id");
 	var description = CKEDITOR.instances["text_sub"].getData();
 	//var username = "botti93";
 	var username= getCookie("username");
 	var title = document.getElementById("title_sub").value;
	var date = new Date();
	var strDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    var valid = 0;
	var subm = { "submission": { "problem": parseInt(problem), "title": title, "text": description, "date": strDate, "username": username, "valid": valid } };
        
	var jSub = JSON.stringify(subm);

 	var url = 'http://localhost:8080/GEMFU-2.0/rest/submission';

    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = alertContents3;
    httpRequest.open("POST", url, true);
    httpRequest.setRequestHeader("Content-type", "application/json");
    httpRequest.send(jSub);
 
 }

 function alertContents3() {
        console.log();
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 201) {

                alert("Submission successfully created!");

                 	var description = document.getElementById("title_sub").value = "";
				 	      CKEDITOR.instances["text_sub"].setData("");
				 	$("#submission").load("prob_subs.html");


            } else {
                alert("Something went wrong!");
            }
        }
    }
    