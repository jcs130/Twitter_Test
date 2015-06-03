<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<TITLE>Twitter On Map</TITLE>

<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false">
	
</script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.1.3.min.js">
	
</script>



<script type="text/javascript">
	var myCenter = new google.maps.LatLng(51.508742, -0.120850);
	var map;
	var markersArray = [];
	var infowindowArray = [];
	var lat;
	var lon;
	var pos = 'pics/pos32.png';
	pos.height = 32;
	pos.width = 32;
	var neu = 'pics/neu32.png';
	neu.height = 32;
	neu.width = 32;
	var neg = 'pics/neg32.png';
	neg.height = 32;
	neg.width = 32;
	function initialize() {
		var mapProp = {
			center : myCenter,
			zoom : 6,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

	}

	function placeMarker(location, content, emotion) {
		//如果表计数量大于一定值，则删除最后一个
		if (markersArray.length > 15) {
			markersArray.shift().setMap(null);
			infowindowArray.shift().close();
		}
		var ticon;
		//根据情感来选择表情图标
		if (emotion == "positive") {
			ticon = pos;
		} else if (emotion == "negative") {
			ticon = neg;
		} else if (emotion == "neutral") {
			ticon = neu;
		} else {
			ticon = null;
		}
		var marker = new google.maps.Marker({
			position : location,
			animation : google.maps.Animation.DROP,
			icon : ticon,
			map : map
		});
		markersArray.push(marker);
		var infowindow = new google.maps.InfoWindow({
			content : content
		});
		infowindowArray.push(infowindow);

		infowindow.open(map, marker);
		map.panTo(location);
	}

	//加载谷歌地图
	google.maps.event.addDomListener(window, 'load', initialize);

	function getLocAjax() {
		$
				.ajax({
					url : 'ajaxgetloc.html',
					success : function(data) {
						//$('#result').html(data);
						var jsonData;
						try {
							jsonData = $.parseJSON(data);
						} catch (e) {
							alert("解析JSON错误：JSON： " + data);
							alert(e.name + "\n" + e.number + "\n"
									+ e.descripition + "\n" + e.message);
						}
						//var temp="'"+data+"'";
						//	var jsonData=JSON.parse(data);
						//	var jsonData = function(data) {
						//		return eval('(' + data + ')');
						//	}
						// alert(jsonData.userName);
						//$('#result')
						//		.html(jsonData.msg + "----loc:" + jsonData.location);
						if (lat != jsonData.location[0]
								&& lon != jsonData.location[1]) {
							lat = jsonData.location[0];
							lon = jsonData.location[1];
							//在地图上标明
							var loc = new google.maps.LatLng(lat, lon);
							try {
								placeMarker(loc, jsonData.msg, jsonData.emotion);
							} catch (e) {
								alert(e.name + "\n" + e.number + "\n"
										+ e.descripition + "\n" + e.message);
							}
						}

					}
				});

		/* 
		 
		$.ajax({
			dataType : "json",
			url :"ajaxgetloc.html",
			success : function(data) {
				$('#result').html(data.msg);
			},
			error: function (e) {
		        //异常处理
				$('#result').html("error");
		    }
		});
		 */
	}
	// Removes the overlays from the map, but keeps them in the array
	function clearOverlays() {
		if (markersArray) {
			for (i in markersArray) {
				markersArray[i].setMap(null);
			}
		}
	}

	// Shows any overlays currently in the array
	function showOverlays() {
		if (markersArray) {
			for (i in markersArray) {
				markersArray[i].setMap(map);
			}
		}
	}

	// Deletes all markers in the array by removing references to them
	function deleteOverlays() {
		if (markersArray) {
			for (i in markersArray) {
				markersArray[i].setMap(null);
			}
			markersArray.length = 0;
		}
	}
</script>

<script type="text/javascript">
	var intervalId = 0;
	intervalId = setInterval(getLocAjax, 3000);
</script>
</head>

<body>
	<div align="center">
		<br> <br> ${message} <br> <br>
		<div id="result"></div>
		<br>
	</div>
	<!-- 谷歌地图DIV -->
	<div id="googleMap" style="width: 100%; height: 800px;"></div>
	<div id="result"></div>
</body>
</html>