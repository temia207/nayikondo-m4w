<?php
require('dbcn.php');

// Opens a connection to a MySQL server.

$connection = mysql_connect ($server, $username, $password);

if (!$connection) 
{
  die('Not connected : ' . mysql_error());
}
// Sets the active MySQL database.
$db_selected = mysql_select_db($database, $connection);

if (!$db_selected) 
{
  die('Can\'t use db : ' . mysql_error());
}

// Selects all the relevant rows in db using an inner join.
$sbcntquery = 'SELECT DISTINCT subcounty.subcounty_name
FROM district 
	INNER JOIN county ON district.district_id = county.district_id
	INNER JOIN subcounty ON county.county_id = subcounty.county_id
	INNER JOIN parish ON subcounty.subcounty_id = parish.subcounty_id
	INNER JOIN village ON parish.parish_id = village.parish_id
	INNER JOIN waterpoint ON village.village_id = waterpoint.village_id
	WHERE subcounty.boundary_data !=""';
$sbcntres = mysql_query($sbcntquery);

$disquery = 'SELECT DISTINCT subcounty.subcounty_name, subcounty.boundary_data
FROM district 
	INNER JOIN county ON district.district_id = county.district_id
	INNER JOIN subcounty ON county.county_id = subcounty.county_id
	INNER JOIN parish ON subcounty.subcounty_id = parish.subcounty_id
	INNER JOIN village ON parish.parish_id = village.parish_id
	INNER JOIN waterpoint ON village.village_id = waterpoint.village_id
	
	WHERE subcounty.boundary_data !=""';
$disres = mysql_query($disquery);

$wtpntquery = 'SELECT district.name, county.county_name, subcounty.subcounty_name, subcounty.boundary_data,
 parish.parish_name, village.villagename, waterpoint.eastings, waterpoint.northings, waterpoint.waterpoint_type_id, waterpoint.waterpoint_id,
 waterpoint.date_installed, waterpoint_types.type_id
FROM district 
	INNER JOIN county ON district.district_id = county.district_id
	INNER JOIN subcounty ON county.county_id = subcounty.county_id
	INNER JOIN parish ON subcounty.subcounty_id = parish.subcounty_id
	INNER JOIN village ON parish.parish_id = village.parish_id
	INNER JOIN waterpoint ON village.village_id = waterpoint.village_id
	INNER JOIN waterpoint_types ON waterpoint.waterpoint_type_id = waterpoint_types.type_id
	WHERE subcounty.boundary_data !=""';
	
	$wtpntres = mysql_query($wtpntquery );

if (!$disres) 
{
  die('Invalid query: ' . mysql_error());
}

//function to create random style clors for sub counties

function randomColor() {
    $str = '#';
    for($i = 0 ; $i < 8 ; $i++) {
        $randNum = rand(0 , 15);
        switch ($randNum) {
            case 10: $randNum = 'A'; break;
            case 11: $randNum = 'B'; break;
            case 12: $randNum = 'C'; break;
            case 13: $randNum = 'D'; break;
            case 14: $randNum = 'E'; break;
            case 15: $randNum = 'F'; break;
        }
        $str .= $randNum;
    }
    return $str;
}
//function to convert eastings and northings to latitudes and longtudes
function UTMtoGeog($eee,$nnn)
    {
		//Convert UTM Coordinates to ugandan latitudes and longtudes that is utm zone(36)
	$DatumEqRad = array (6378137.0,6378137.0,6378137.0,6378135.0,6378160.0,6378245.0,6378206.4,
	6378388.0,6378388.0,6378249.1,6378206.4,6377563.4,6377397.2,6377276.3);	
	$DatumFlat = array (298.2572236, 298.2572236, 298.2572215,	298.2597208, 298.2497323, 298.2997381, 294.9786982,
	296.9993621, 296.9993621, 293.4660167, 294.9786982, 299.3247788, 299.1527052, 300.8021499); 
	$Item = 0;//Default
	$a = $DatumEqRad[$Item];//equatorial radius, meters. 
	$f = 1/$DatumFlat[$Item];//polar flattening.
	$k0 = 0.9996;//scale on central meridian
	$b = $a*(1-$f);//polar axis.
	$e = sqrt(1 - ($b/$a)*($b/$a));//eccentricity
	$drad = M_PI/180;//Convert degrees to radians)
	$e0 = $e/sqrt(1 - ($e*$e));//Called e prime in reference
	$esq = (1 - ($b/$a)*($b/$a));//e squared for use in expansions
	$e0sq = ($e*$e)/(1-($e*$e));// e0 squared - always even powers
	$x = $eee;
	$y = $nnn;
	$utmz = 36; // ugandan utm zone
	$zcm = 3 + 6*($utmz-1) - 180;//Central meridian of zone
	$e1 = (1 - sqrt(1 - ($e*$e)))/(1 + sqrt(1 - ($e*$e)));//Called e1 in USGS PP 1395 also
	$M0 = 0;//In case origin other than zero lat - not needed for standard UTM
	$M = $M0 + ($y/$k0);//Arc length along standard meridian. 
	$mu = $M/($a*(1 - $esq*(1/4 + $esq*(3/64 + 5*$esq/256))));
	$phi1 = $mu + $e1*(3/2 - (27*$e1*$e1/32))*sin(2*$mu) + $e1*$e1*(21/16 -(55*$e1*$e1/32))*sin(4*$mu);//Footprint Latitude
	$phi2 = $phi1 + ($e1*$e1*$e1)*(sin(6*$mu)*(151/96) + $e1*sin(8*$mu)*(1097/512));
	$C1 = ($e0sq)*pow(cos($phi2),2);
	$T1 = pow(tan($phi2),2);
	$N1 = $a/sqrt(1-pow($e*sin($phi2),2));
	$R1 = $N1*(1-($e*$e))/(1-pow($e*sin($phi2),2));
	$D = ($x-500000)/($N1*$k0);
	$phi = ($D*$D)*(1/2 - ($D*$D)*(5 + (3*$T1) + (10*$C1) - (4*$C1*$C1) - (9*$e0sq))/24);
	$phy = $phi + pow($D,6)*(61 + 90*$T1 + 298*$C1 + 45*$T1*$T1 -252*$e0sq - 3*$C1*$C1)/720;
	$phe = $phi2 - ($N1*tan($phi2)/$R1)*$phy;
				
//Output Latitude
	$lata = ceil(1000000*$phe/$drad)/1000000;	
//Longitude
	$lng = $D*(1 + $D*$D*((-1 -2*$T1 -$C1)/6 + $D*$D*(5 - 2*$C1 + 28*$T1 - 3*$C1*$C1 +8*$e0sq + 24*$T1*$T1)/120))/cos($phi2);
	$lngd = $zcm+$lng/$drad;
//Output Longitude
    $lnga = ceil(1000000*$lngd)/1000000;
	$latlng = $lngd.",".$lata.",0";
	return $latlng;
	}

// Creates the Document.
$dom = new DOMDocument('1.0', 'UTF-8');


// Creates the root KML element and appends it to the root document.
$node = $dom->createElementNS('http://www.opengis.net/kml/2.2', 'kml');
$node->setAttributeNS('http://www.w3.org/2000/xmlns/','xmlns:gx','http://www.google.com/kml/ext/2.2');
$node->setAttributeNS('http://www.w3.org/2000/xmlns/','xmlns:atom', 'http://www.w3.org/2005/Atom');
$parNode = $dom->appendChild($node);

// Creates a KML Document element and append it to the KML element.
$dnode = $dom->createElement('Document');
$docNode = $parNode->appendChild($dnode);

//create the subcounty stle
while ($subcntstyrow = @mysql_fetch_assoc($sbcntres))
{
$disStyleNode = $dom->createElement('Style');
$disStyleNode->setAttribute('id', 'districtStyle'.$subcntstyrow['subcounty_name']);
$disIconstyleNode = $dom->createElement('IconStyle');
$disIconstyleNode->setAttribute('id','disicon');
$disIconNode = $dom->createElement('Icon');
$disHref = $dom->createElement('href','file:///C:/xampp/htdocs/cwf/icons/icon27.png');
$disIconNode->appendChild($disHref);
$disIconstyleNode->appendChild($disIconNode);
$disStyleNode->appendChild($disIconstyleNode);
$disPolyStyleNode = $dom->createElement('PolyStyle');
$disPolyStyleColor = $dom->createElement('color',randomColor());
$disPolyStyleNode->appendChild($disPolyStyleColor);
$disStyleNode->appendChild($disPolyStyleNode);
$docNode->appendChild($disStyleNode);

}

while ($subcntpolyrow = @mysql_fetch_assoc($disres))
{
  // Creates a Placemark and append it to the Document.

  $disnode = $dom->createElement('Placemark');

  // Creates the subcounty ploygon and assign boundary points the value of polydata column.
 $disname = $dom->createElement('SubcountyName', $subcntpolyrow['subcounty_name']);
 $disnode->appendChild($disname);
 $styleUrl = $dom->createElement('styleUrl', '#' . 'districtStyle'.$subcntpolyrow['subcounty_name']);
 $disnode->appendChild($styleUrl);
 $multiGeometry = $dom->createElement('MultiGeometry');
 $polygon = $dom->createElement('Polygon');
 $tessellate = $dom->createElement('tessellate','1');
 $polygon->appendChild($tessellate);
 $outerBoundaryIs = $dom->createElement('outerBoundaryIs');
 $linearRing = $dom->createElement('LinearRing');
 $coordinates = $dom->createElement('coordinates', $subcntpolyrow['boundary_data']);
 $linearRing->appendChild($coordinates);
 $outerBoundaryIs->appendChild($linearRing);
 $polygon->appendChild($outerBoundaryIs);
 $multiGeometry->appendChild($polygon);
 $disnode->appendChild($multiGeometry);
 $docNode->appendChild($disnode);
 }
 
 //creating placemark and balloon styles for the various water point types
$waterptStyleNode = $dom->createElement('Style');
$waterptStyleNode->setAttribute('id', 'waterpointStyle2');
$waterptIconStyleNode = $dom->createElement('IconStyle');
$waterptIconNode = $dom->createElement('Icon');
$waterptHref = $dom->createElement('href', 'file:///C:/xampp/htdocs/m4w/img/dark-blue-circle.png');
$waterptLabelStyleNode = $dom->createElement('LabelStyle');
$waterptLabelStyleColor = $dom->createElement('color', '00ffffff');
$waterptIconNode->appendChild($waterptHref);
$waterptIconStyleNode->appendChild($waterptIconNode);
$waterptStyleNode->appendChild($waterptIconStyleNode);
$waterptLabelStyleNode->appendChild($waterptLabelStyleColor);
$waterptStyleNode->appendChild($waterptLabelStyleNode);
$docNode->appendChild($waterptStyleNode);

$waterptStyleNode = $dom->createElement('Style');
$waterptStyleNode->setAttribute('id', 'waterpointStyle1');
$waterptIconStyleNode = $dom->createElement('IconStyle');
$waterptIconNode = $dom->createElement('Icon');
$waterptHref = $dom->createElement('href', 'file:///C:/xampp/htdocs/m4w/img/dark-green-circle.png');
$waterptLabelStyleNode = $dom->createElement('LabelStyle');
$waterptLabelStyleColor = $dom->createElement('color', '00ffffff');
$waterptIconNode->appendChild($waterptHref);
$waterptIconStyleNode->appendChild($waterptIconNode);
$waterptStyleNode->appendChild($waterptIconStyleNode);
$waterptLabelStyleNode->appendChild($waterptLabelStyleColor);
$waterptStyleNode->appendChild($waterptLabelStyleNode);
$docNode->appendChild($waterptStyleNode);

$waterptStyleNode = $dom->createElement('Style');
$waterptStyleNode->setAttribute('id', 'waterpointStyle3');
$waterptIconStyleNode = $dom->createElement('IconStyle');
$waterptIconNode = $dom->createElement('Icon');
$waterptHref = $dom->createElement('href', 'file:///C:/xampp/htdocs/m4w/img/dark-red-circle.png');
$waterptLabelStyleNode = $dom->createElement('LabelStyle');
$waterptLabelStyleColor = $dom->createElement('color', '00ffffff');
$waterptIconNode->appendChild($waterptHref);
$waterptIconStyleNode->appendChild($waterptIconNode);
$waterptStyleNode->appendChild($waterptIconStyleNode);
$waterptLabelStyleNode->appendChild($waterptLabelStyleColor);
$waterptStyleNode->appendChild($waterptLabelStyleNode);
$docNode->appendChild($waterptStyleNode);
 
 
 while ($pointrow = mysql_fetch_assoc($wtpntres)){
 $ptfolder = $dom->createElement('Folder');
 $folderName = $dom->createElement('name', $pointrow['waterpoint_id']);
 $ptfolder->appendChild($folderName);
 $open =$dom->createElement('open', '0');
 $ptfolder->appendChild($open);
 $ptPlacemark =$dom->createElement('Placemark');
 $ptStyleUrl = $dom->createElement('styleUrl', '#' . 'waterpointStyle'.$pointrow['waterpoint_type_id']);
 $pointName = $dom->createElement('name', $pointrow['waterpoint_id']);
 $style = $dom->createElement('Style');
 $balloonStyle = $dom->createElement('BalloonStyle');
 
 
$txt = '<style>.highlightRow{background-color:#FF6600;color:white;}
.parameterText{color:#5F6062;font-size:16pt;font-weight:bold}</style>
<table style="font-family:Trebuchet MS;font-size:12pt;font-color:#000000" width=200 cellpadding=3 cellspacing=3>
<tr><td>
 <table width=100% cellpadding=0 cellspacing=0>
 <tr><td><img src=\'Photos/busoga-trust.jpg\' /></td></tr>
 <tr><td> 
</td></tr><tr><td>&nbsp;</td></tr><tr><td bgColor=black>
<table width=100% border=0 cellpadding=1 cellspacing=1>
<tr bgcolor=white><td colspan=2><b>&nbsp;Water Point Data </b></td></tr>
</table></td></tr><tr><td></td></tr>
<tr><td bgColor=black >
<table width=100% border=0 cellpadding=1 cellspacing=1>
<tr bgcolor=white><td colspan=2><b>Water source information </b></td></tr>
<tr bgcolor=white><td width=175>Reference Number</td><td align=right>'.$pointrow['waterpoint_id'].'</td></tr>
<tr bgcolor=white><td>&nbsp;District</td><td align=right>'.$pointrow['name'].'</td></tr>
<tr bgcolor=white><td>&nbsp;Sub County</td><td align=right>'.$pointrow['subcounty_name'].'</td></tr>
<tr bgcolor=white><td>&nbsp;Parish Name </td><td align=right>'.$pointrow['parish_name'].'</td></tr>
</table></td></tr>
<tr><td bgColor=black >
<table width=100% border=0 cellpadding=1 cellspacing=1>
<tr bgcolor=white><td colspan=2><b>Water source Ticket information </b></td></tr>
<tr bgcolor=white><td width=175>Problem ID</td><td align=right></td></tr>
<tr bgcolor=white><td>Problem Type</td><td align=right></td></tr>
<tr bgcolor=white><td>Date Reported</td><td align=right></td></tr>
<tr bgcolor=white><td>Date Resolved</td><td align=right></td></tr>
<tr bgcolor=white><td>Resolved By</td><td align=right></td></tr>
<tr bgcolor=white><td>Recomendation</td><td align=right></td></tr>
</table></td></tr>
</table>';
   
 $cdata = $dom->createCDATASection($txt); 

 $text = $dom->createElement('text');
 $text->appendChild($cdata);
 $balloonVisibility = $dom->createElement('gx:balloonVisibility', '1');
 $wpoint = $dom->createElement('Point');
 $textColor = $dom->createElement( 'textColor','ff000000');
 $displayMode = $dom->createElement( 'displayMode','default');
 $wcoordinates = $dom->createElement('coordinates', UTMtoGeog($pointrow['eastings'],$pointrow['northings']));
 $wpoint->appendChild($wcoordinates);
 $ptPlacemark->appendChild($wpoint);
 $balloonStyle->appendChild($text);
 $balloonStyle->appendChild($textColor);
 $balloonStyle->appendChild($displayMode);
 $style->appendChild($balloonStyle);
 $ptPlacemark->appendChild($style);
 $ptPlacemark->appendChild($balloonVisibility);
 $ptPlacemark->appendChild($ptStyleUrl);
 $ptPlacemark->appendChild($pointName);
 $ptfolder->appendChild($ptPlacemark);
 $docNode->appendChild($ptfolder);
 
 }
 
 $dom->formatOutPut = TRUE;
$kmlOutput = $dom->saveXML();
header('Content-type: application/vnd.google-earth.kml+xml');
echo $kmlOutput;
?>