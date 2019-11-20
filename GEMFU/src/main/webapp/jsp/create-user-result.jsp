<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session = "true" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	    <link href="WebContent/css/style.css" rel="stylesheet">
	    <link href="WebContent/bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
	    <link href="WebContent/css/botticss.css" rel="stylesheet">
	    
	    <link href="WebContent/fontawesome-free-5.0.13/web-fonts-with-css/css/fontawesome-all.min.css" rel="stylesheet">

	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	    <script type="text/javascript" src="WebContent/bootstrap-4.0.0-dist/js/bootstrap.min.js"></script>
	    <script src="WebContent/js/scriptFileMichele.js"></script>
	    <link href="WebContent/css/stylesheetMichele.css" rel="stylesheet">
      <link href="WebContent/css/Umberto.css" rel="stylesheet">

		<title>GEMFU</title>
	</head>

	<body onload="loadResponseCreateUser()">
	
  <div id="navbar-gemfu">
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="index.html"> <img src="WebContent/gemfu.png" style="width: 38px;height: 38px;"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active"> <a class="nav-link" href="#">Problems <span class="sr-only">(current)</span></a> </li>
          <li class="nav-item"> <a class="nav-link" href="#">Discussions</a> </li>
          <li class="nav-item"> <a class="nav-link" href="#">Categories</a> </li>          
           
        </ul>
        <ul class="navbar-nav">
        <li class="nav-item active">
        <form class="form-inline my-2 my-lg-0">
          <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-primary my-2 my-sm-0" type="submit"> Search </button>
            
        </form>
        </li>
         <li class="dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown"> <i class="far fa-user"> </i> </a>
            <div class="dropdown-menu dropdown-menu-right" >
              <div id="login">
                <div id="loginform">
                  
                </div>
                
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#Register">New around here? Sign up</a>
                <a class="dropdown-item" href="#">Forgot password?</a>
              </div>
            </div>

          </li>
        </ul>
    </div>

    </nav>
            
 </div> <!-- chiusura div_menu -->
  
	
  <div>
  <br>
      <div class="container-fluid">
      <div class="row"> 

        <div class="col-md-1"></div>
          <div class="col-md-8">

            <h2>Choose your problem : </h2>
            <br>
              <div id="index-body">
                  <div  id="sub_list">
     
                  </div>

              </div>

                <div class="row">
                    <div class="col-sm-9" class="copy">
                    <a href="CreateProbPage.html" target="_parent" class="btn btn-secondary btn-sm active" role="button" aria-pressed="true"> <i class="fa fa-plus"> Create a new problem</i></a>
                    </div>
                </div>

                <nav  id="sub_nav" aria-label="Problems navigation">
                          <ul id="pages" class="pagination justify-content-end">
                          
                          </ul>
                </nav>  
          
        <footer>
        <hr>
        <div class="row">
          <div class="col-sm-4 copyright">
                  <span>Copyright © 2018: GEMFU</span>
              </div>
            </div>
        </footer>
        </div>
      </div>
      </div>    

  </div>
  <div class="all-screen-lv5" onclick="LoadIndexbyCreateUser()">
  
  </div>

	 <div class="container-fluid col-md-4 internal-div" id="register-div">
	 	<nav class="navbar navbar-expand navbar-dark bg-dark">
			<h1 id="reg">Create User</h1>
		</nav>
		
    <br>

		<!-- display the just created User, if any and no errors -->
	
    <c:choose>
      <c:when test='${not empty user && !message.error}'>
			<ul id="done">
				<img src="${user.profileImage}" class="reg_img" id="profileImage">
				
			<h5 class="myh5">	<li>username: <div id="username"><c:out value="${user.username}"/></div></li>
				<li>name: <div id="name"><c:out value="${user.name}"/></div></li>
				<li>surname: <div id="surname"><c:out value="${user.surname}"/></div></li>
				<li>date: <div id="date"><c:out value="${user.date}"/></div></li>
				<li>gender: <div id="gender">
                                                <c:choose>
                                                  <c:when test="${us.gender=='1'}">
                                                    Male
                                                  </c:when>
                                                  <c:otherwise>
                                                    Female
                                                  </c:otherwise> 
                                                </c:choose>                                                           
                                                                 </div></li>
				<li>email: <div id="email"><c:out value="${user.email}"/></div></li>
			</h5>
			</ul>
		</c:when>
    <c:otherwise> 
      <h5 class="myh5" style="text-align: center;">Username already in use! Try again!</h5>
      <br>
    </c:otherwise>
    </c:choose>
		<nav id="bottom-reg" class="navbar navbar-expand navbar-dark bg-dark justify-content-end">
			<button class="btn btn-light reg-btn" href="WebContent/index.html" onclick="LoadIndexbyCreateUser()"><b>close</b></button>
		</nav>
	</div>
	<script type="text/javascript" src="WebContent/js/bottiJS.js"></script>
	<script src="WebContent/js/scriptFileMichele-end.js"></script>
  
<script>
var page = 0;
var maxPage = 0;
var order = 1;
$(document).ready(function() {
  makePages();
});

function makeSubList(variable) {
  var url = 'http://localhost:8080/GEMFU-2.0/rest/problem/varorder/'+(variable*5) + '/' + order;
  httpRequest = new XMLHttpRequest();

  if (!httpRequest) {
    alert('Giving up :( Cannot create an XMLHTTP instance');
    return false;
  }
  httpRequest.onreadystatechange = alertContentsSub;
  httpRequest.open('GET', url);
  httpRequest.send();
}


function alertContentsSub() {
  if (httpRequest.readyState === XMLHttpRequest.DONE) {
    if (httpRequest.status == 200) {

      
      var div = document.getElementById('sub_list');
      var table = document.createElement('table');
      table.className = "table";
      table.id = "mytable";
      var thead = document.createElement('thead');

      var tr = document.createElement('tr');

      var th = document.createElement('th');
      var a = document.createElement('a');
      a.style.textDecoration = "none";
      a.style.color = "white";
      a.href = "#";
      a.appendChild(document.createTextNode('Id'));
      a.onclick = function(){
        document.getElementById("sub_nav").style.display= "none";

        page = 0;
        document.getElementById('sub_list').innerHTML = "";

        if(order == 2)
          order = 6;
        else
          order = 2;

        if(maxPage>5)
          updatePage(page);
        else
          updatePage2(page);
          makeSubList(page);
        }

        th.appendChild(a);
        tr.appendChild(th);

        var th = document.createElement('th');
        var a = document.createElement('a');
        a.style.textDecoration = "none";
        a.style.color = "white";
        a.href = "#";
        a.appendChild(document.createTextNode('Title'));
        a.onclick = function() {
          document.getElementById("sub_nav").style.display= "none";

          document.getElementById('sub_list').innerHTML = "";
          page = 0;
          if(order == 3)
            order = 7;
          else
            order = 3;
          if(maxPage>5)
            updatePage(page);
          else
            updatePage2(page);
            makeSubList(page);
          }

          th.appendChild(a);
          tr.appendChild(th);

          var th = document.createElement('th');
          var a = document.createElement('a');
          a.style.textDecoration = "none";
          a.style.color = "white";
          a.href = "#";
          a.appendChild(document.createTextNode('Difficulty'));
          a.onclick = function(){
            document.getElementById("sub_nav").style.display= "none";

            document.getElementById('sub_list').innerHTML = "";
            page = 0;
            if(order == 4)
              order = 8;
            else
              order = 4;
            if(maxPage>5)
              updatePage(page);
            else
              updatePage2(page);
            makeSubList(page);
          }

          th.appendChild(a);
          tr.appendChild(th);

          var th = document.createElement('th');
          var a = document.createElement('a');
          a.style.textDecoration = "none";
          a.style.color = "white";
          a.href = "#";
          a.appendChild(document.createTextNode('Date'));
          a.onclick = function() {

            document.getElementById("sub_nav").style.display= "none";

            document.getElementById('sub_list').innerHTML = "";
            page = 0;
            if(order == 1)
              order = 5;
            else
              order = 1;
            if(maxPage>5)
              updatePage(page);
            else
              updatePage2(page);
            makeSubList(page);
          }

          th.appendChild(a);
          tr.appendChild(th);

          thead.appendChild(tr);
          table.appendChild(thead);

          var tbody = document.createElement('tbody');

          var jsonData = JSON.parse(httpRequest.responseText);
          var resource = jsonData['resource-list'];

          for (var i = 0; i < resource.length; i++) {
            var problem = resource[i].problem;

            var tr = document.createElement('tr');
            
            var refff = document.createElement('a');
            refff.style.textDecoration = "none";
            refff.style.color = "black";
            refff.href = "prob_view.html?id=" + problem['id'];
            refff.appendChild(document.createTextNode(problem['id']));
            
            var td_id = document.createElement('td');
            td_id.appendChild(refff);
            tr.appendChild(td_id);


            var td_title = document.createElement('td');
            td_title.appendChild(document.createTextNode(problem['title']));
            tr.appendChild(td_title);

            var td_dif = document.createElement('td');
            td_dif.appendChild(document.createTextNode(problem['difficulty']));
            tr.appendChild(td_dif);

            var td_date = document.createElement('td');
            td_date.appendChild(document.createTextNode(problem['date']));
            tr.appendChild(td_date);

            tbody.appendChild(tr);
          }

          table.appendChild(tbody);
          div.appendChild(table);

          document.getElementById("sub_nav").style.display= "block";
          
          



      } else {
        alert('There was a problem with the request.');
      }
}
}



function makePages() {
  var url = 'http://localhost:8080/GEMFU-2.0/rest/problem';
  httpRequest = new XMLHttpRequest();

  if (!httpRequest) {
    alert('Giving up:( Cannot create an XMLHTTP instance');
    return false;
  }
  httpRequest.onreadystatechange = makePage;
  httpRequest.open('GET',url);
  httpRequest.send();
}

function makePage()
{

  if (httpRequest.readyState === XMLHttpRequest.DONE) {

    if (httpRequest.status == 200) {

      var ul = document.getElementById('pages');
      var li = document.createElement('li');
      li.className = "page-item";
      li.style.visibility = "hidden";
      var a = document.createElement('a');
      a.className = "page-link";
      a.appendChild(document.createTextNode("Previous"));

      a.onclick = function() {
        if (page!=0)
        {
          page--;
        }
        document.getElementById('sub_list').innerHTML = "";
        document.getElementById("sub_nav").style.display = "none";

        makeSubList(page);
        if(maxPage>5)
          updatePage(page);
        else
          updatePage2(page);
      }

      li.appendChild(a);
      ul.appendChild(li);

      var jsonData = JSON.parse(httpRequest.responseText);
      var resource = jsonData['resource-list'];
      var s = 0;
      for (var i = 0; i < resource.length; i++) {
        if(i%5==0)
        {
          if(s<5)
          {
            var li = document.createElement('li');
            li.className = "page-item";
            if(s==0)
            {
              li.className = "page-item active";
            }
            var a = document.createElement('a');
            a.className = "page-link";
            a.appendChild(document.createTextNode(s+1));

            a.onclick = function() {
              page = parseInt(this.text) - 1;

              document.getElementById('sub_list').innerHTML = "";

              document.getElementById("sub_nav").style.display = "none";

              makeSubList(page);
              if(maxPage>5)
              {
                updatePage(page);
              }
              else
                updatePage2(page);
            }
            li.appendChild(a);
            ul.appendChild(li);
            s++;
            }
            maxPage++;
          }
      }
      if (s<2)
        document.getElementById("pages").style.visibility = "hidden";

      var li = document.createElement('li');
      li.className = "page-item";

      var a = document.createElement('a');
      a.className = "page-link";
      a.appendChild(document.createTextNode("Next"));
      a.onclick = function(){

        document.getElementById("sub_nav").style.display="none";

      if(page < maxPage -1)
      {
        page++;
        if(maxPage>5)
          updatePage(page);
        else
          updatePage2(page);
      }
      document.getElementById('sub_list').innerHTML = "";
      makeSubList(page);
      }

      li.appendChild(a);
      ul.appendChild(li);

      makeSubList(page);
      }  else {
            alert('There was a problem with the request.');
      }
  }
}

function updatePage(variable)
{
  var j = variable;
  var z = maxPage;
  if(j<2)
  {
    j=2;
  }
  if(j>z-3)
  {
    j=z-3;
  }
  var t = 2;
  var l;

  if(page == 0)
  {
    l=2;
  }
  else if (page == 1)
  {
    l=3;
  }
  else if (page == maxPage -1)
  {
    l=6;
  }
  else if (page = maxPage -2)
  {
    l=5;
  }
  else
  {
    l=4;
  }

  if(page==0)
  {
    document.getElementById("pages").childNodes[1].style.visibility= "hidden";
  }
  else
  {
    document.getElementById("pages").childNodes[1].style.visibility= "visible";
  }

  if(page == maxPage -1)
  {
    document.getElementById("pages").childNodes[7].style.visibility= "hidden";
  }
  else
  {
    document.getElementById("pages").childNodes[7].style.visibility= "visible";
  }

  for(var i=j;i<j+5;i++)
  {
    document.getElementById("pages").childNodes[t].childNodes[0].text = i -1;
    if(t==l)
    {
      document.getElementById("pages").childNodes[t].className = "page-item active";
    }
    else
    {
      document.getElementById("pages").childNodes[t].className = "page-item";
    }

    t++;
    }
}

function updatePage2(variable)
{
  var j = variable +2;
  var z = maxPage + 2;
  var t = 2;

  if(page==0)
  {
    document.getElementById("pages").childNodes[1].style.visibility = "hidden";
  }
  else
  {
    document.getElementById("pages").childNodes[1].style.visibility= "visible";
  }

  if(page == maxPage -1)
  {
    document.getElementById("pages").childNodes[maxPage + 2].style.visibility= "hidden";
  }
  else
  {
    document.getElementById("pages").childNodes[maxPage + 2].style.visibility="visible";
  }

  for(var i=2;i<z;i++)
  {
    document.getElementById("pages").childNodes[t].childNodes[0].text = i-1;
    if(t == j)
    {
      document.getElementById("pages").childNodes[t].className = "page-item active";
    }
    else
      {
        document.getElementById("pages").childNodes[t].className = "page-item";
      }

      t++;
      }
}

</script>
	</body>
</html>