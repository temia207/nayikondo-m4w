<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name = "description" content = "Highlighting polylines and polygons. Import coordinates from KML file. Use php.">

<title>m4w subcounty polygons</title>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAFbKLHRkbmi3YDJMRcjEgGRT6jWcSl7A74Kt9oIKZyN7ZZVaWERTYC9G-ZkS5DLLQveyDf-t-cuKZaw&amp;sensor=false"
      type="text/javascript"></script>
<style type="text/css">
a { color: #21759b; text-decoration: none; font-weight: bold; }

a:hover { color: #d54e21; }

body {
    font-family: "Trebuchet MS", Arial,Helvetica,Sans Serif;
    font-size: 10pt;
}
#map {
    width: 500px;
    height: 500px;
    border: 1px solid gray;
    margin-top: 1px;
    margin-left: 1px;
}
.centertable {
    width: 740px;
    margin: 0px auto;
}
#links {
    border: 2px dotted black;
    padding: 5px;
    vertical-align: top;
}
</style>

<script type="text/javascript">
//<![CDATA[

var map;
var overlayname = new Array();
var curShape = -1;
var shapenumbers;
var Shape = new Array();
var thisshape = new Array();
var polygonlineColor = "#FF0000"; // red line, default colour
var polygonLineWeight = 0.1;
var lineopacity = .8;
var fillColor = "#FF0000"; // red fill, default colour
var fillopacity = .8;
var lineColor = "#FF0000"; // red line, default colour
var opacity = .8;
var lineWeight = 2;
var newfillColor = "#00FF00"; // green fill, highlight colour
var newlineColor = "#00FF00"; // green line, highlight colour
<?php
function getlatlngstring($coordsArray, $splitonnewline)
{
$latlng = "";
$k = count($coordsArray);
for ($i = 0; $i < $k; $i++) {
$latlngArray = explode(",", $coordsArray[$i]);
if (count($latlngArray) == 3) {
$lat = $latlngArray[1];
$lng = $latlngArray[0];
$latlng = $latlng . "new GLatLng(" . $lat . "," . $lng . ")";
if ($splitonnewline == 1)
{
if ($i != $k - 2) $latlng = $latlng . ", ";
}
else
{
if ($i != $k - 1) $latlng = $latlng . ", ";
}
}
}
$latlng = "[".$latlng."]";
return $latlng;
}
// Load kml-file and get coordinates
// Inspired by Google maps api blog, Pamela Fox http://googlemapsapi.blogspot.com/2007/10/clickable-polys-old-school-image-maps.html
$kmlfile = "subcount.kml"; // $address has been given value in a part of the script not shown here
$l = 0;
$coordstring = "";
$xml = simplexml_load_file($kmlfile) or die("url not loading");
$documentname = $xml->Document->name;
foreach($xml->Document->Placemark as $placemark) {
$placemarkname = $placemark->SubcountyName;
$description = $placemark->description;
if ($coords = $placemark->MultiGeometry->Polygon->outerBoundaryIs->LinearRing->coordinates)
{
$linetype = "Polygon";
}else{
if ($coords = $placemark->LineString->coordinates)
{
$linetype = "LineString";
}
}
if ($coords)
{
if (count($coordsArray = explode(" ", $coords)) > 1)
{
$splitonnewline = 1;
}
else
{
$coordsArray = explode(",", $coords); // split on space
}
$latlng = getlatlngstring($coordsArray, $splitonnewline); // function above
// $latlng has the format for use in js, like this:
// $latlng = [new GLatLng(16.130262,-16.523437), .... new GLatLng(15.601875,-16.633301)]
if ($l > 0) // if more than 1 shape
{
$coordstring = $coordstring.",";
}
$coordstring = $coordstring.$latlng; // to be used in js below
$overlayname[$l] = $placemarkname;
$descriptions[$l] = $description;
$overlayshape[$l] = $linetype;
$latlng = "";
$splitonnewline = 0;
$l++; // to be used in js below
}
}
?>
// converting php arrays to javascript arrays
<?php
// converting php arrays to javascript arrays (this is inside the javascript)
foreach ($overlayname as $key => $value) {
echo "overlayname.push(\"$value\");";
}
foreach ($overlayshape as $key => $value) {
echo "thisshape.push(\"$value\");";
}
?>
//Defines custom icons
    var iconBlue = new GIcon(); 
    iconBlue.image = '../img/dark-blue-circle.png';
    iconBlue.iconSize = new GSize(10, 10);
    iconBlue.iconAnchor = new GPoint(6, 20);
    iconBlue.infoWindowAnchor = new GPoint(5, 1);

    var iconRed = new GIcon(); 
    iconRed.image = '../img/dark-red-circle.png';
    iconRed.iconSize = new GSize(10, 10);
    iconRed.iconAnchor = new GPoint(6, 6);
    iconRed.infoWindowAnchor = new GPoint(5, 1);
	
	var iconGreen = new GIcon(); 
    iconGreen.image = '../img/dark-green-circle.png';
    iconGreen.iconSize = new GSize(10, 10);
    iconGreen.iconAnchor = new GPoint(6, 20);
    iconGreen.infoWindowAnchor = new GPoint(5, 1);

    var customIcons = [];
    customIcons["shallow well"] = iconBlue;
    customIcons["Borehole"] = iconRed;
	customIcons["tap"] = iconGreen;
	
function loadmap() {
    if (GBrowserIsCompatible()) {
        map = new GMap2(document.getElementById("map"));
        map.setCenter(new GLatLng(0.685526,30.283230),10); // value comes from php script
        var customUI = map.getDefaultUI();
        customUI.controls.maptypecontrol = false;
        customUI.controls.menumaptypecontrol = true;
        map.setUI(customUI);
        addOverlayFromKML();
				//Load the xml data from our xemel.php file
        GDownloadUrl("php_xemel.php", function(data) {
          var xml = GXml.parse(data);
          var markers = xml.documentElement.getElementsByTagName("marker");
		  var sidebar = document.getElementById('sidebar');
           sidebar.innerHTML = '';
           if (markers.length == 0) {
           sidebar.innerHTML = 'No results found.';
         return;
         }
	   
	   
          for (var i = 0; i < markers.length; i++) {
            var name = markers[i].getAttribute("name");
            var address = markers[i].getAttribute("address");
            var type = markers[i].getAttribute("type");
			if(type==1){
			type="shallow well";
			}
			if(type==2){
			type="Borehole";
			}
			if(type==3){
			type="tap";
			}
            var point = new GLatLng(parseFloat(markers[i].getAttribute("lat")),
                                    parseFloat(markers[i].getAttribute("lng")));
            var marker = createMarker(point, name, address, type);
            map.addOverlay(marker);
			var sidebarEntry = createSidebarEntry(marker, name, address);
         sidebar.appendChild(sidebarEntry);
          }
		  
        });
	
    }
}

// the php function simplexml_load_file() is used to load and read kml-file
// jsfromphp array and shapenumbers are generated in the php script
function addOverlayFromKML() {
    var jsfromphp = [<?php echo $coordstring; ?>]; // php string is pasted here and becomes javascript array
    shapenumbers = <?php echo $l; ?>;
    for (var i = 0; i<shapenumbers; i++) {
        if (thisshape[i] == "Polygon"){
            Shape[i] = new GPolygon(jsfromphp[i],polygonlineColor,polygonLineWeight,lineopacity,fillColor,fillopacity);
        }else{
            Shape[i] = new GPolyline(jsfromphp[i],lineColor,lineWeight,opacity);
        }
        map.addOverlay(Shape[i]);
    }
}
function selectedradiobutton(i){
    var polyPoints = new Array();
    repaint(); // if there is a shape with highlight colour, its colour will be shifted back to default colour
    // collect coordinates array for the chosen shape
    var j = Shape[i].getVertexCount();
    for (k=0; k<j; k++){
        polyPoints[k] = Shape[i].getVertex(k);
    }
    // remove it and replace it with new/highlight colour
    map.removeOverlay(Shape[i]);
    if (thisshape[i] == "Polygon"){
        Shape[i] = new GPolygon(polyPoints,polygonlineColor,polygonLineWeight,lineopacity,newfillColor,fillopacity);
    }else{
        Shape[i] = new GPolyline(polyPoints,newlineColor,lineWeight,opacity);
    }
    map.addOverlay(Shape[i]);
    curShape = i; // remember which number this shape has
}
function repaint() {
    if (curShape > -1){
        // curShape shape has highlight colour, redraw it with default colour
        var polyPoints = new Array();
        var j = Shape[curShape].getVertexCount();
        for (k=0; k<j; k++){
            polyPoints[k] = Shape[curShape].getVertex(k);
        }
        map.removeOverlay(Shape[curShape]);
        if (thisshape[curShape] == "Polygon"){
            Shape[curShape] = new GPolygon(polyPoints,polygonlineColor,polygonLineWeight,lineopacity,fillColor,fillopacity);
        }else{
            Shape[curShape] = new GPolyline(polyPoints,lineColor,lineWeight,opacity);
        }
        map.addOverlay(Shape[curShape]);
    }
}
function hideall() {
    repaint(); // make sure all shapes have default colour before they are hidden
    for (var i=0; i<shapenumbers; i++){
        Shape[i].hide();
    }
    curShape = -1; // no repaint neccessary on next click
}
function showall() {
    repaint(); // if there is a shape with highlight colour, it will be redrawn with default colour before the shapes are displayed
    for (var i=0; i<shapenumbers; i++){
        Shape[i].show();
    }
    curShape = -1;
}

	//create marker fucntion
    function createMarker(point, name, address, type) {
      var marker = new GMarker(point, customIcons[type]);
      //var html = "<b>" + name + "  " + type + "</b> <br/>" + address;
	  var html_info = "<table>" +
                         "<tr><td><b><center>Water Point Information</center></b></td> </tr>" +
						 "<tr><td><b>Name(ID):</b></td> <td>"+name+" </td> </tr>" +
                         "<tr><td><b>SubCounty:</b></td> <td>"+address+"</td> </tr>" +
                         "<tr><td><b>Type:</b></td> <td>"+type+"</td></tr>" +
                         "</table>";
						 
	  var html_ticket = "<table>" +
						 "<tr><td><b><center>Ticket Information</center></b></td> </tr>" +
                         "<tr><td><b>Last Assessment:</b></td> <td></td> </tr>" +
                         "<tr><td><b>Assessed By:</b></td> <td></td></tr>" +
						 "<tr><td><b>Fault Type:</b></td> <td></td></tr>" +
                         "</table>";
						 
      GEvent.addListener(marker, 'click', function() {
        //marker.openInfoWindowHtml(html);
		        marker.openInfoWindowTabsHtml(
    [new GInfoWindowTab("Info",html_info), 
    new GInfoWindowTab("Ticket",html_ticket)],
    {maxUrl:"tab.html"});
      });
	  
      return marker;
    }
	
 
	
	function createSidebarEntry(marker, name, address) {
      var div = document.createElement('div');
      var html = '<b>' + name + '</b>'+' ' + address;
      div.innerHTML = html;
      div.style.cursor = 'pointer';
      div.style.marginBottom = '1px'; 
      GEvent.addDomListener(div, 'click', function() {
        GEvent.trigger(marker, 'click');
      });
      GEvent.addDomListener(div, 'mouseover', function() {
        div.style.backgroundColor = '#eee';
      });
      GEvent.addDomListener(div, 'mouseout', function() {
        div.style.backgroundColor = '#fff';
      });
      return div;
    }
//]]>
</script>
</head>
<body onload="loadmap()" onunload="GUnload()">

<table cellpadding="5" cellspacing="0" border="0">
<tr>
<td>
<h4>Click on the subcounty to view polygons(boundary)</h4>
</td>
</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0">
<tr id="cm_mapTR">
<td width="160" valign="top"><b>Waterpoints</b></br><font color="red">click to view infomation<div id="sidebar" style="overflow: auto; height: 200px; font-size: 11px; color: #000"></div></td>
<td valign="top">
<div id="map" style="width: 600px; height: 600px"></div>
</td>
<form name="shapes" action="#">
<td id="main" valign="top">
<?php
//The sidebar content is written with this php snippet:
echo " <b>Subcounties</b>"."</br>";
for ($i = 0; $i < $l; $i++) {
echo "<input type=\"radio\" name=\"poly\" value=".$i." onclick=\"selectedradiobutton(".$i.");\"/>\n";
echo " ".$descriptions[$i]." ".$overlayname[$i]."<br />\n";
if ($i == 24){
echo "</td><td id=\"extra\" valign=\"top\">\n";
}
}
echo "<input type=\"radio\" name=\"poly\" value=\"hide\" onclick=\"hideall();\"/> Hide all<br />\n";
echo "<input type=\"radio\" name=\"poly\" value=\"show\" onclick=\"showall();\"/> Show all\n";
?>
</td>
</form>
</tr></table>
</body>
</html>