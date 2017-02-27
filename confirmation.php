<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>Confirmation Page</title>

	<style>
	.table {
		margin-left: 30%;
	}

	.text_intro {
		color: #164882;
	}

	.text_details {
		color: #164882;
		padding-left: 25%;
		margin-bottom: 0;
	}

	.line {
		width: 75%;
	}
	</style>

<!--
<link rel="stylesheet" href="jeopardy.css" type="text/css"/>
-->

<script type="text/javascript">  
</script>     
</head>

<body>
	<h1 class="text_intro" align="center">Confirmation Page</h1>
	<p align="center">Below are all of the questions and answers that have been submitted up to this point. The most recent submission will be at the bottom of the page. Thank you!</p>
<form action="http://localhost/CS_4640/jeopardy/jeopardy.html" align="center">
    <input type="submit" value="Make another question?" style="width: 300px; font-size: 16px;"/>
</form>
<br>
<h2 class="text_details">Confirmation Details</h2>

<div class="parent_div" align="center">
	<div class="line"><hr></div>
</div>

<table class="table">
	<col width="225">
	<col width="500">
	<?php

$file = fopen("data.txt", "r");     // r: read only
$counter = 0;
$line = fgets($file); // reads in first line to eliminate the extra space
while (!feof($file)) { // iterate through each line of the file
	$line = fgets($file);
	if($line != "\n" && $line != "") { 
		if($counter % 2 == 0) {
			?>

			<tr>
				<td style='text-align: right;'><b><?php
					echo $line.":&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
					?></b>
				</td>
				<td>
	<?php // uses php to end the brackets
}
else {
	echo $line;
	?>
</td>
</tr>
<?php
}
$counter += 1;
}
else {
	$counter = 0;
	?>
</table>
<br>
<table class="table">
	<col width="225">
	<col width="500">
	<?php
}
//$counter += 1;
}
?>
</table>
<?php



/*
$file = fopen("data.txt", "r");     // r: read only
echo "<table style='border: 1px solid #990000;'>";
while (!feof($file)) {
	$line = fgets($file);
	if($line != "\n") {
		echo "<tr>word";
		echo $line;
		echo "</tr>";
	}
}
echo "</table>";
#echo "<button onclick='history.go(-1);''>Back </button>";
#echo "<table><tr><td>Hello!</td><td>Hi!</td></tr></table>";
#while ( !feof($file) ) {
# 	echo fgets($file), "<br />";
# }
fclose($file);
*/
?>
</body>
</html>
