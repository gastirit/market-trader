var stompClient = null;
var bombs = [
	
];

var obj = {
	    name: null,
	    radius: 10,
	    yeild: null,
	    country: null,
	    fillKey: 'PIN',
	    significance: null,
	    date: null,
	    latitude: null,
	    longitude: null
	  };


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ws-stomp-hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/trades', function (trades) {
            showGreeting(JSON.parse(trades.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showGreeting(message) {
	console.log("message:" + message);
	var innerHtml = null;
	bombs = [];
	for(var i = 0 ; i< message.length ; i++) {
		var uri = "https://restcountries.eu/rest/v2/alpha/" + message[i].originatingCountry;
		var lan = null;
		var lat = null;
			
			
			$.ajax({
				  url: uri,
				  dataType: 'json',
				  success: function( resp ) {
					  $("#langitute").val(resp.latlng[0]);
				      $("#latitute").val(resp.latlng[1]);
				  },
				  error: function( req, status, err ) {
				    console.log( 'something went wrong', status, err );
				  }
				});
		
		var newObj = Object.assign({ name: message[i].userId }, obj);;
		
		newObj.name = message[i].userId;
		newObj.latitude = $("#langitute").val();
		newObj.longitude =  $("#latitute").val();
		newObj.date = message[i].timePlaced;
		newObj.country = message[i].originatingCountry;
		
		if(newObj.latitude != null && newObj.longitude != null) {
			bombs.push(newObj);
		}
		
		
		innerHtml += "<tr><td>" + message[i].userId  + "</td><td>" 
						+ message[i].currencyFrom + "</td><td>"
						+ message[i].currencyTo + "</td><td>"
						+ message[i].amountSell + "</td><td>"
						+ message[i].amountBuy + "</td><td>"
						+ message[i].rate + "</td><td>"
						+ message[i].timePlaced + "</td><td>"
						+ message[i].originatingCountry + "</td></tr>";
						
	}
	
	bombMap.bubbles(bombs, {
	    popupTemplate:function (geography, data) { 
	            return ['<div class="hoverinfo"><strong>' +  data.name + '</strong>',
	            '<br/>Payload: ' +  data.yeild + ' kilotons',
	            '<br/>Country: ' +  data.country + '',
	            '<br/>Date: ' +  data.date + '',
	            '</div>'].join('');
	    }
	});
    $("#trades").html(innerHtml);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

