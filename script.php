<?php
$myFile = "data.txt";
$fh = fopen($myFile, 'w') or die("can't open file");
fwrite($fh, "HELLO WORLD");
fclose($fh);
?>