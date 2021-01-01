'use strict';

function logout(){
    alert("logout");
    var Url = "http://localhost:8080/microservicio-usuario/logout/";
    $.ajax({
     url: Url,
     error: function(err) {
        console.error(err);

     },
     success: function(data) {
       console.log(data);
     },
     type: 'GET'
   });

}