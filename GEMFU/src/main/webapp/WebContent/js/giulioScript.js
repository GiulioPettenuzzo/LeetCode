
document.getElementById("discussion-tab").addEventListener("click", function(){
   $("#discussion").load("discussion_list_view.html");
   var y = document.getElementById("sub_div");
   y.style.display = "none";

});

