/**
 *
 */


function openDiscussion(id){
    //var x = document.getElementById("view-discussion");
    var y = document.getElementById("discussion_list");
    y.style.display = "none";

    /*x.style.display = "block";
    x.innerHTML='<object type="text/html" data="SingleThreadView.html" ></object>';*/
    $("#discussion").load("single_discussion_view.html");
    loadDiscussion(id);

}

function closeDiscussion(){
    //var y = document.getElementById("back-to-list");
   // y.style.display = "none";
   $("#discussion").load("discussion_list_view.html");


}


function showSolutionWindow() {
    var x = document.getElementById("solution-div");
    x.style.display = "block";
}

function closeSolutionWindow(){
	var x = document.getElementById("solution-div");
	x.style.display = "none";
}

function newThreadDialog(){
    var x = document.getElementById("create-discussion");
    x.style.display = "block";
       $("#create-discussion").load("create-discussion.html");

}

function closeThreadDialog(){
	var x = document.getElementById("create-discussion");
	x.style.display = "none";
}

(function() {
    var httpRequest;
    var sub = document.getElementById("post_discussion");
    if (sub) {
        sub.addEventListener("click", createDiscussion);
    }



    function createDiscussion(){
        var title = document.getElementById("discussion-title").value;
        var description = CKEDITOR.instances["ins-discussion-desc"].getData();
        var date = new Date();
        var strDate = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        var username = getCookie("username");
        var problemId = parseInt(getQueryVariable("id"));
        // alert(getQueryVariable("id"));
        //var problemId = 0;

        if (title == "") {
                alert("Please insert a title for your discussion!");
                return;
        }
        if (description == "") {
                alert("Please insert a description for your discussion!");
                return;
        }


        var discussion = { "thread": { "title": title, "text": description, "user": username, "date": strDate, "problem-id": problemId} };

        var jDiscuss = JSON.stringify(discussion);

        var url = "http://localhost:8080/GEMFU-2.0/rest/thread";

        httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
           alert('Giving up :( Cannot create an XMLHTTP instance');
           return false;
        }
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open("POST", url, true);
        httpRequest.setRequestHeader("Content-type", "application/json");
        httpRequest.send(jDiscuss);
                       // var jsonData = JSON.parse(httpRequest.responseText);

       // window.location.href="single_discussion_view.html";
    }

    function alertContents() {
             console.log();
             if (httpRequest.readyState === XMLHttpRequest.DONE) {

                    if (httpRequest.status == 201) {
                        var jsonData = JSON.parse(httpRequest.responseText);
                        var resource = jsonData["thread"];
                        var id = resource["id"];
                        var title = resource["title"];
                        var text = resource["text"];
                        var username = resource["user"];
                        var date = resource["date"];

                      /*  var y = document.getElementById("discussion_list");
                        y.style.display = "none";
                        $("#discussion").load("single_discussion_view.html");*/


                        openDiscussion(id);


                    } else {
                        alert("Something went wrong!");
                    }
             }
    }

})();

function loadDiscussion(id){
   // var id = 10;
    var discussionURL = 'http://localhost:8080/GEMFU-2.0/rest/thread/'+ id;

    httpRequest = new XMLHttpRequest();

    if(!httpRequest){
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }

     httpRequest.onreadystatechange = alertDiscussContents;
     httpRequest.open('GET', discussionURL);
     httpRequest.send();

}

var discussionID;

function alertDiscussContents(){
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status == 200) {
              var jsonData = JSON.parse(httpRequest.responseText);
              var thread = jsonData['thread'];

              var title = document.getElementById('single_discussion_title');
              var text = document.getElementById('single_discussion_text');
              var date = document.getElementById('single_discussion_date');
              var user = document.getElementById('single_discussion_user');
              var views = document.getElementById('single_discussion_views');
              views.style.color = '#2965a8';

              title.appendChild(document.createTextNode(thread['title']));
              //text.appendChild(document.createTextNode(thread['text']));

              var parser=new DOMParser();
              var newNode = parser.parseFromString(thread['text'], "text/html");
              text.appendChild(newNode.documentElement);

              date.appendChild(document.createTextNode(thread['date']));
              user.appendChild(document.createTextNode(thread['user']));
              views.appendChild(document.createTextNode(thread['views']));

              discussionID=thread["id"];
              sessionStorage.setItem("discussionID",discussionID);


             // makeTitle();

              loadProfileImage(thread['user'],"single_discussion_profile_image");

          } else {
            alert("there was some problems loading the page");
          }
    }

}


function getVoteState(discussID,element){
       // var discussionID = parseInt(sessionStorage.getItem("discussionID"));
      //  alert(discussionID);


        var loadVoteURL = 'http://localhost:8080/GEMFU-2.0/rest/vote/thread/'+ discussID;

        httpRequest = new XMLHttpRequest();

        if(!httpRequest){
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }

        httpRequest.onreadystatechange = function () {
                                                                   alertLoadVote(element);
                                 };
        httpRequest.open('GET', loadVoteURL,false);
        httpRequest.send();
    }


    function alertLoadVote(element){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
               if (httpRequest.status == 200) {
                      var jsonData = JSON.parse(httpRequest.responseText);
                      var resource = jsonData['resource-list'];

                      var totalLike = 0;

                      for(var i=0;i<resource.length;i++){
                            var vote = resource[i].vote;

                            if(vote["like"]==1){
                                totalLike = totalLike + 1;
                            }
                            else if(vote["like"]==-1){
                                totalLike = totalLike - 1;
                            }
                      }
                      element.css('color', '#2965a8');

                      element.text(totalLike);

               } else {
                    alert("there was some problems loading the page");
               }
        }
    }

function loadDiscussionList(){
    //alert('ciao');

    var httpRequest;
    var problemID = getQueryVariable("id");
    var urlDiscussionList = "http://localhost:8080/GEMFU-2.0/rest/thread/problem_id/" + problemID;
    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
         alert('Giving up :( Cannot create an XMLHTTP instance');
         return false;
    }
    httpRequest.onreadystatechange = alertDiscussionList;
    httpRequest.open("GET", urlDiscussionList);
    httpRequest.send();

    function alertDiscussionList(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status == 200) {
                  var jsonData = JSON.parse(httpRequest.responseText);
                  var resource = jsonData['resource-list'];
                  if(resource.length==0){
                        var y = document.getElementById("single_discussion_list");
                        y.style.display = "none";
                        return false;
                  }
                  var threadOne = resource[0].thread;
                  var title = document.getElementById('open-discussion');
                  var date = document.getElementById('list_discussion_date');
                  var viewsNumber = document.getElementById('numberOfVisualization');

                  viewsNumber.style.color = '#2965a8';
                 // var profileImage = document.getElementById('profile_image_list_discussion');
                  title.appendChild(document.createTextNode(threadOne['title']));
                  date.appendChild(document.createTextNode(threadOne['date']));
                  viewsNumber.appendChild(document.createTextNode(threadOne['views']));


                  title.setAttribute("id",threadOne["id"]);

                  loadProfileImage(threadOne["user"],'profile_image_list_discussion');

                  title.addEventListener("click",function(){
                                         openDiscussion(this.id);
                                        });

                  getVoteState(threadOne["id"],$('#numberOfVotes'));



                  for(var i = 1;i<resource.length;i++){
                      var thread = resource[i].thread;
                      var newDiv = $("#single_discussion_list").clone();
                      newDiv.attr("value","id"+thread['id'])
                      // remember this id should be different for each instance. or you can remove it
                      .appendTo("#discussion_list") // depends on where in DOM you want to insert it
                      .find(".list_discussion_title").html(thread['title']);

                      newDiv.find(".list_discussion_title").attr("id",thread["id"]);

                      newDiv.find(".list_discussion_title").click(function(){
                                                                         openDiscussion(this.id);
                                                                  });
                      newDiv.find(".list_discussion_date").html(thread['date']);

                      newDiv.find(".numberOfVisualization").css('color', '#2965a8');

                      newDiv.find(".numberOfVisualization").html(thread['views']);



                      newDiv.find(".profile-picture-image").attr("id",thread["user"]+i);

                      newDiv.find(".numberOfVotes").attr("id","voteME"+thread["id"]);

                   //   newDiv.find(".numberOfVotes").text(getVoteState(thread["id"]));
                      getVoteState(thread["id"],newDiv.find(".numberOfVotes"));

                      newDiv.show();




                  }

            loadAllImage();


            }
            else{
                        alert("there was some problems loading the page");
            }
        }


    }

}

function loadAllImage(){
    var allImage = document.getElementsByClassName('profile-picture-image');
    for(var i = 0; i<allImage.length;i++){
        var currentImage = allImage[i];
        var id = currentImage.id;
        var username = id.replace(/[0-9]/g, '');
        loadProfileImage(username,id);
    }
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

function makeTitle(){
    var id = getQueryVariable("id");
    var urlTitle = 'http://localhost:8080/GEMFU-2.0/rest/problem/'+ id;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
         alert('Giving up :( Cannot create an XMLHTTP instance');
         return false;
    }
    httpRequest.onreadystatechange = alertTitle;
    httpRequest.open('GET', urlTitle);
    httpRequest.send();
}

function alertTitle() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
                    alert("done??" +  httpRequest.status);

        if (httpRequest.status == 200) {
            var jsonData = JSON.parse(httpRequest.responseText);
            var problem = jsonData['problem'];
                    alert("ok??");

            alert(httpRequest.responseText);

            var h = document.getElementById('problem_title');

            //h.innerHTML = problem['title'];
            h.appendChild(document.createTextNode(problem['title']));

        } else {
          alert('There was a problem with the request.');
        }
      }
}


function loadProfileImage(username,id){
   // var user = getCookie("username");
    var userURL = 'http://localhost:8080/GEMFU-2.0/rest/user/' + username;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
         alert('Giving up :( Cannot create an XMLHTTP instance');
         return false;
    }
    httpRequest.onreadystatechange = function () {
            alertUser(id);
        };
    httpRequest.open('GET', userURL, false);
    httpRequest.send();
}


function alertUser(id){
if (httpRequest.readyState === XMLHttpRequest.DONE) {

     if (httpRequest.status == 200) {
          var jsonData = JSON.parse(httpRequest.responseText);
          var user = jsonData['users'];
          var imageText = user['profileImage'];

          if(imageText==null){
            if(id == "single_discussion_profile_image"){
                makeTitle();
            }
            return false;
          }
          var y = document.getElementById(id);
          y.src = imageText;
          if(id == "single_discussion_profile_image"){
          makeTitle();
          }
     }
   }
}


//COMMENTI

function loadComments2() {
    var httpRequest;
    var id = getQueryVariable("id");
    var url = 'http://localhost:8080/GEMFU-2.0/rest/comment/thread/' + discussionID;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert("Giving up :( Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = commentResponse2;
    httpRequest.open("GET", url);
    httpRequest.send();

    function commentResponse2() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {
                var jsonData = JSON.parse(httpRequest.responseText);
                var commentList = jsonData["resource-list"];
                document.getElementById("n-comments2").textContent = commentList.length;
                var commArea = document.getElementById("comments-area2");
                console.log(jsonData);
                for (var i = 0; i < commentList.length; i++) {
                    var comment = commentList[i].comment;

                    var commentDiv = document.createElement("div");
                    commentDiv.classList.add("comment2","row");

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

function addComment2() {
    var user = getCookie("username");
    var text = CKEDITOR.instances["ins-comment2"].getData();
   // var discussionID = parseInt(getQueryVariable("id"));
    var date = new Date();
    var strDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    var url = "http://localhost:8080/GEMFU-2.0/rest/comment";
    var tmp = { "comment": { "thread": discussionID, "username": user, "text": text, "date": strDate} };
    var comment = JSON.stringify(tmp);
    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = alertCommCreation2;
    httpRequest.open("POST", url, true);
    httpRequest.setRequestHeader("Content-type", "application/json");
    httpRequest.send(comment);


    function alertCommCreation2() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 201) {
                var commArea = document.getElementById("comments-area2");
                var listComm = document.getElementsByClassName("comment2");
                for (var i = listComm.length-1; i > -1; i--) {
                    console.log(i);
                    console.log(commArea.removeChild(listComm[i]));
                }
                loadComments2();
                CKEDITOR.instances["ins-comment2"].setData("");

            } else {
                alert("Something went wrong!");
            }
        }
    }



}

