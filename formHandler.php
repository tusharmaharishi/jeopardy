<html>
<head>
  <title>Display Page</title>
</head>

<body bgcolor="#EEEEEE">

  <center><h2>Display Page</h2></center>
  <p align="center">
    The following table displays the question and answers you just created.
  </p>
  <?php
  $GLOBALS['responses'] = array();
  ?>
  <table cellSpacing=1 cellPadding=1 width="75%" border=1 bgColor="lavender"  align="center">
    <tr bgcolor="#FFFFFF">
      <td align="center"><strong>Parameter</strong></td>
      <td align="center"><string>Value</string></td>
    </tr>
    <tr>
      <td width="20%">Multiple Choice - Question</td>
      <td>
        <?php
        if(isset($_POST['multipleChoiceQuestion'])) {
          echo $_POST['multipleChoiceQuestion'];
          $GLOBALS['responses'][] = 'multipleChoiceQuestion'; // same as array.append()
	         $GLOBALS['responses'][] = $_POST['multipleChoiceQuestion']; // same as array.append()
	       }
         ?>
       </td>      
     </tr>
     <tr>
      <td width="20%">Multiple Choice - Answer</td> 
      <td>
        <?php 
        if(isset($_POST['multipleChoiceAnswer1'])) {
         echo $_POST['multipleChoiceAnswer1'];
         $GLOBALS['responses'][] = 'multipleChoiceAnswer1';
         if($_POST['mc_correct'] == "A") {
           echo " (correct)";
           $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer1']." (correct)";
         }
         else 
           $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer1'];
       }
       ?> 
     </td>      
   </tr>
   <tr>
    <td width="20%">Multiple Choice - Answer</td> 
    <td>
      <?php 
      if(isset($_POST['multipleChoiceAnswer2'])) {
        echo $_POST['multipleChoiceAnswer2'];
        $GLOBALS['responses'][] = 'multipleChoiceAnswer2';
        if($_POST['mc_correct'] == "B") {
         echo " (correct)";
         $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer2']." (correct)";
       }
       else 
         $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer2'];
     }      
     ?>
   </td>      
 </tr>
 <tr>
  <td width="20%">Multiple Choice - Answer</td> 
  <td>
    <?php 
    if(isset($_POST['multipleChoiceAnswer3'])) {
      echo $_POST['multipleChoiceAnswer3'];
      $GLOBALS['responses'][] = 'multipleChoiceAnswer3';
      if($_POST['mc_correct'] == "C") {
       echo " (correct)";
       $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer3']." (correct)";
     }
     else 
       $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer3'];
   }
   ?>
 </td>      
</tr>
<tr>
  <td width="20%">Multiple Choice - Answer</td> 
  <td>
    <?php 
    if(isset($_POST['multipleChoiceAnswer4'])) {
      echo $_POST['multipleChoiceAnswer4'];
      $GLOBALS['responses'][] = 'multipleChoiceAnswer4';
      if($_POST['mc_correct'] == "D") {
       echo " (correct)";
       $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer4']." (correct)";
     }
     else 
       $GLOBALS['responses'][] = $_POST['multipleChoiceAnswer4'];
   }
   ?>
 </td>      
</tr>
<tr>
  <td width="20%">True or False - Question</td>
  <td>
    <?php 
    if(isset($_POST['trueOrFalseQuestion'])) {
     echo $_POST['trueOrFalseQuestion'];
     $GLOBALS['responses'][] = 'trueOrFalseQuestion';
     $GLOBALS['responses'][] = $_POST['trueOrFalseQuestion'];
   }
   ?>
 </td>      
</tr>
<tr>
  <td width="20%">True or False - Answer</td> 
  <td>
    <?php 
	   //$GLOBALS['responses'][] = 5;
           //$GLOBALS['responses'][] = 4;
           //$GLOBALS['responses'][] = 3;
    if(isset($_POST['trueOrFalseAnswer'])) {
     $ans = $_POST['trueOrFalseAnswer'];
     if($ans == 'true') {
       echo "True (correct)";
       $GLOBALS['responses'][] = "trueOrFalseAnswer";  
       $GLOBALS['responses'][] = "True (correct)";  
     }
     else {
       echo "True";
       $GLOBALS['responses'][] = "trueOrFalseAnswer";  
       $GLOBALS['responses'][] = "True";
     }
   }
   ?>
 </td>      
</tr>
<tr>
  <td width="20%">True or False - Answer</td> 
  <td>
    <?php 
    if(isset($_POST['trueOrFalseAnswer'])) {
      $ans = $_POST['trueOrFalseAnswer'];
      if($ans == 'true') {
        echo "False";
        $GLOBALS['responses'][] = "trueOrFalseAnswer";  
        $GLOBALS['responses'][] = "False";
      }
      else {
        echo "False (correct)";
        $GLOBALS['responses'][] = "trueOrFalseAnswer";  
        $GLOBALS['responses'][] = "False (correct)";
      }
    }
    ?>
  </td>      
</tr>
<tr>
  <td width="20%">Short Answer - Question</td>
  <td>
    <?php 
    if(isset($_POST['shortAnswerQuestion'])) {
      echo $_POST['shortAnswerQuestion'];
      $GLOBALS['responses'][] = 'shortAnswerQuestion';
      $GLOBALS['responses'][] = $_POST['shortAnswerQuestion'];
    }
    ?>
  </td>      
</tr>
<tr>
  <td width="20%">Short Answer - Answer</td> 
  <td>
    <?php
    if(isset($_POST['shortAnswerAnswer'])) {
     echo $_POST['shortAnswerAnswer'];
     echo " (correct)";
     $GLOBALS['responses'][] = 'shortAnswerAnswer';
     $GLOBALS['responses'][] = $_POST['shortAnswerAnswer']." (correct)";
   }
   ?>
 </td>      
</tr>
</table>
</br>
<form method='post' action="formHandler.php" name="confirmation"  align="center">
  <button onclick="history.go(-1);" style="width: 100px; font-size: 16px;">Back </button>
  <input type="hidden" value="<?php echo implode('~',$GLOBALS['responses']);?>" name="actionprint" />
  <button type="button" onclick="window.document.confirmation.submit()" style="width: 300px; font-size: 16px;">Confirm and Print to File</button>
</form>
</body>
</html> 

<?php

function checkActionPrint() {
 foreach($GLOBALS['responses'] as &$curr)
   echo $curr."<br>";
}

if(isset($_POST['actionprint']))
{
   /*
   echo "FOUND Ids<br>";
   $IdArray = explode('~',$_POST['actionprint']);
   print_r($IdArray);
   */
   printToFile();
 }

 function printToFile() {
   $submission_data_arr = explode('~',$_POST['actionprint']); #change separation values from comma
   file_put_contents('data.txt', "".PHP_EOL, FILE_APPEND | LOCK_EX);
   foreach($submission_data_arr as &$curr) {
     file_put_contents('data.txt', $curr.PHP_EOL, FILE_APPEND | LOCK_EX);
   }
   $url = "http://localhost/CS_4640/jeopardy/confirmation.php"; #hardcoded link :(
   echo "<meta http-equiv='refresh' content='0; url=$url'/>"; #redirect to confirmation page where we display the file we just printed to and include button back to question page "make another question?"
   #echo getcwd()."/jeopardy.html";
 }
   /*
   $txt = '';
   if(isset($_POST['trueOrFalseQuestion'])) {
   $txt = 'HELLOOOOO';
   }
   file_put_contents('data.txt', $txt.PHP_EOL, FILE_APPEND | LOCK_EX);
   */
   ?>
