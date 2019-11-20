

var buttonUpPressed = 0;
var buttonDownPressed = 0;


function onVoteUp(){

    var username=getCookie("username");
    var userSession = sessionStorage.getItem("username");
    if(username!="" && username!=null && userSession != null)
    {
    var valueToInsert;

    if(buttonUpPressed == 0){
        buttonUpPressed = 1;
        incrementValue(1);
        valueToInsert = 1;
    }
    else{
        buttonUpPressed = 0;
        incrementValue(-1);
        valueToInsert = 0;
    }

    if(buttonDownPressed){
        var x = document.getElementById("vote-down");
        x.classList.toggle('on');
        buttonDownPressed = 0;
        incrementValue(1);
        valueToInsert = 1;
    }
    var y = document.getElementById("vote-up");
    y.classList.toggle('on');
    postVote(valueToInsert);
    }else{
        alert("you need to be logged");
    }
}

function onVoteDown(){
    var username=getCookie("username");
    var userSession = sessionStorage.getItem("username");
    if(username!="" && username!=null && userSession != null)
    {
    var valueToInsertTwo;
    if(buttonDownPressed == 0){
        buttonDownPressed = 1;
        incrementValue(-1);
        valueToInsertTwo = -1;
    }
    else{
        buttonDownPressed = 0;
        incrementValue(1);
        valueToInsertTwo = 0;
    }

    if(buttonUpPressed){
	    var x = document.getElementById("vote-up");
	    x.classList.toggle('on');
	    buttonUpPressed = 0;
	    incrementValue(-1);
        valueToInsertTwo = -1;
    }
    var y = document.getElementById("vote-down");
    y.classList.toggle('on');
    postVote(valueToInsertTwo);
    }else{
            alert("you need to be logged");
    }
}

function incrementValue(incrementValue)
{
    var value = parseInt(document.getElementById('vote-number').value, 10);
    value = isNaN(value) ? 0 : value;
    value = value + incrementValue;
    document.getElementById('vote-number').value = value;
}

function setVoteValue(value){
    document.getElementById('vote-number').value = value;
}

function getVoteState(){
        var discussionID = parseInt(sessionStorage.getItem("discussionID"));
      //  alert(discussionID);


        var loadVoteURL = 'http://localhost:8080/GEMFU-2.0/rest/vote/thread/'+ discussionID;

        httpRequest = new XMLHttpRequest();

        if(!httpRequest){
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }

        httpRequest.onreadystatechange = alertLoadVote;
        httpRequest.open('GET', loadVoteURL);
        httpRequest.send();
    }


    function alertLoadVote(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
               if (httpRequest.status == 200) {
                      var jsonData = JSON.parse(httpRequest.responseText);
                     // alert(httpRequest.responseText);
                      var resource = jsonData['resource-list'];
                      if(resource.length==0){
                            document.getElementById('vote-number').value = '0';
                            return false;
                      }
                      var username = getCookie("username");
                      var userSession = sessionStorage.getItem("username");
                      if(userSession == null){
                            username = "";
                      }

                      var isPresent = -1;
                      var totalLike = 0;

                      for(var i=0;i<resource.length;i++){
                            var vote = resource[i].vote;
                            if(vote["username"] == username){
                                isPresent = 1;
                                if(vote["like"]>0){
                                    var y = document.getElementById("vote-up");
                                    y.classList.toggle('on');
                                    buttonUpPressed = 1;
                                }
                                else if(vote["like"]<0){
                                    var y = document.getElementById("vote-down");
                                    y.classList.toggle('on');
                                    buttonDownPressed = 1
                                }
                            }

                            if(vote["like"]==1){
                                totalLike = totalLike + 1;
                            }
                            else if(vote["like"]==-1){
                                totalLike = totalLike - 1;
                            }
                      }

                      document.getElementById('vote-number').value = totalLike;

               } else {
                    alert("there was some problems loading the page");
               }
        }
    }

    function postVote(vote){

            //TODO update
            var discussionID = parseInt(sessionStorage.getItem("discussionID"));
            var username = getCookie("username");
            var discussionStr = {"vote":{"username":username,"problem-id":-1,"thread-id":discussionID,"like":vote}};

            var jDiscussion = JSON.stringify(discussionStr);
                   //

          //  alert(jDiscussion);

            var discussionVoteUrl = "http://localhost:8080/GEMFU-2.0/rest/vote/thread";

            httpRequest = new XMLHttpRequest();

            if (!httpRequest) {
                    alert('Giving up :( Cannot create an XMLHTTP instance');
                    return false;
            }
            httpRequest.onreadystatechange = alertContentsForPostVote;
            httpRequest.open("POST", discussionVoteUrl,true);
            httpRequest.setRequestHeader("Content-type", "application/json");
            httpRequest.send(jDiscussion);

    }

    function alertContentsForPostVote(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
             if (httpRequest.status == 200) {
                      var jsonData = JSON.parse(httpRequest.responseText);
             }
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




//fill the box to the input number