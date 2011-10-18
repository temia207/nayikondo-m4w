<?php  

require('../dbcn.php');

// Start XML file, create parent node

$dom = new DOMDocument("1.0");
$node = $dom->createElement("markers");
$parnode = $dom->appendChild($node); 

// Opens a connection to a MySQL server

$connection=mysql_connect ($server, $username, $password);
if (!$connection) {  die('Not connected : ' . mysql_error());} 

// Set the active MySQL database

$db_selected = mysql_select_db($database, $connection);
if (!$db_selected) {
  die ('Can\'t use db : ' . mysql_error());
} 

// Select all the rows in the markers table

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
if (!$wtpntres) {  
  die('Invalid query: ' . mysql_error());
} 

header("Content-type: text/xml"); 

// Iterate through the rows, adding XML nodes for each

while ($row = @mysql_fetch_assoc($wtpntres)){  
  // ADD TO XML DOCUMENT NODE  
  $node = $dom->createElement("marker");  
  $newnode = $parnode->appendChild($node);   
  $newnode->setAttribute("name",$row['waterpoint_id']);
  $newnode->setAttribute("address", $row['subcounty_name']);  
  // easting/northing to latitude and longtude
  
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
	$x = $row['eastings'];
	$y = $row['northings'];
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
  $newnode->setAttribute("lat", $lata);  
  $newnode->setAttribute("lng", $lngd);  
  $newnode->setAttribute("type", $row['type_id']);
} 

echo $dom->saveXML();

?>
