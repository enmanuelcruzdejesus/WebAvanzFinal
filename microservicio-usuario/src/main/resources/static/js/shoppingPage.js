'use strict';

$('.toast').toast(option)

function shoppingClickHandler(obj){
    var productId = obj.id;
    var Url = "http://localhost:8080/microservicio-usuario/addToCart/"+productId;


   $.ajax({
     url: Url,
     data: {
       format: 'json'
     },
     error: function(err) {
        console.error(err);

     },
     dataType: 'jsonp',
     success: function(data) {
       console.log(data);
     },
     type: 'GET'
   });

   alert("Paquete agregado al carrito");

//   $('#element').toast('show')

}