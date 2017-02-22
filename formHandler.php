<html>
<head>
  <title>Display Page</title>
</head>

<body bgcolor="#EEEEEE">

  <center><h2>Display Page</h2></center>
  <p>
    The following table lists all parameter names and their values that were submitted from your form.
  </p>
  <?php
     $GLOBALS['information'] = array();
     ?>
  <table cellSpacing=1 cellPadding=1 width="75%" border=1 bgColor="lavender">
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
	   $GLOBALS['information'][] = $_POST['multipleChoiceQuestion']; // same as array.append()
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
	   $GLOBALS['information'][] = $_POST['multipleChoiceAnswer1'];
           if($_POST['mc_correct'] == "A")
           echo " (correct)";
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
	   $GLOBALS['information'][] = $_POST['multipleChoiceAnswer2'];
          if($_POST['mc_correct'] == "B")
            echo " (correct)";
        }
        ?>
      </td>      
    </tr>
    <tr>
      <td width="20%">Multiple Choice - Answer</td> 
      <td>
        <?php 
        if(isset($_POST['multipleChoiceAnswer3'])) {
	   $GLOBALS['information'][] = $_POST['multipleChoiceAnswer3'];
          echo $_POST['multipleChoiceAnswer3'];
          if($_POST['mc_correct'] == "C")
            echo " (correct)";
        }
        ?>
      </td>      
    </tr>
    <tr>
      <td width="20%">Multiple Choice - Answer</td> 
      <td>
        <?php 
        if(isset($_POST['multipleChoiceAnswer4'])) {
           $GLOBALS['information'][] = $_POST['multipleChoiceAnswer4'];
          echo $_POST['multipleChoiceAnswer4'];
          if($_POST['mc_correct'] == "D")
            echo " (correct)";
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
	   $GLOBALS['information'][] = $_POST['trueOrFalseQuestion'];
	   }
        ?>
      </td>      
    </tr>
    <tr>
      <td width="20%">True or False - Answer</td> 
      <td>
        <?php 
	   //$GLOBALS['information'][] = 5;
           //$GLOBALS['information'][] = 4;
           //$GLOBALS['information'][] = 3;
	   if(isset($_POST['trueOrFalseAnswer'])) {
           $ans = $_POST['trueOrFalseAnswer'];
           if($ans == 'true') {
           echo "True (correct)";
	   $GLOBALS['information'][] = "True (correct)";  
	   }
           else {
           echo "True";
	   $GLOBALS['information'][] = "True";
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
           $GLOBALS['information'][] = "False";
	   }
          else {
            echo "False (correct)";
           $GLOBALS['information'][] = "False (correct)";
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
	   $GLOBALS['information'][] = $_POST['shortAnswerQuestion'];
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
	   $GLOBALS['information'][] = $_POST['trueOrFalseQuestion']." (correct)";
        }
       ?>
      </td>      
    </tr>
  </table>
</br>
<button onclick="history.go(-1);">Back </button>
<form method='post' action="formHandler.php" name="functionPrint">
  <input type="hidden" value="<?php echo implode(',',$GLOBALS['information']);?>" name="actionprint" />
  <button type="button" onclick="window.document.functionPrint.submit()">Confirm and Print to File</button>
</form>
<!-- <script type='text/javascript'>
    
    //AJAX function
    function startAjax() {
      $.ajax({
        type: "POST",
        url: "script.php",
        success: function(msg){
          alert( "Data Saved: " + msg );
        }
      });
    }
    
    //Call AJAX:
    //$(document).ready(startAjax());
</script>
<button onclick="startAjax();">Confirm and Print to File</button> -->
</body>
</html> 

<?php

  function checkActionPrint() {
   foreach($GLOBALS['information'] as &$curr)
   echo $curr."<br>";
   }

   if(isset($_POST['actionprint']))
   {
   /*
   echo "FOUND Ids<br>";
   $IdArray = explode(',',$_POST['actionprint']);
   print_r($IdArray);
   */
   printToFile();
   }
   
  function printToFile() {
   $submission_data_arr = explode(',',$_POST['actionprint']);
   foreach($submission_data_arr as &$curr) {
   file_put_contents('data.txt', $curr.PHP_EOL, FILE_APPEND | LOCK_EX);
   }
   }
   /*
   $txt = '';
   if(isset($_POST['trueOrFalseQuestion'])) {
   $txt = 'HELLOOOOO';
   }
   file_put_contents('data.txt', $txt.PHP_EOL, FILE_APPEND | LOCK_EX);
   */
?>
