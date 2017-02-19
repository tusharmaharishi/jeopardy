<html>
<head>
  <title>Display Page</title>
</head>

<body bgcolor="#EEEEEE">

  <center><h2>Display Page</h2></center>
  <p>
    The following table lists all parameter names and their values that were submitted from your form.
  </p>

  <table cellSpacing=1 cellPadding=1 width="75%" border=1 bgColor="lavender">
    <tr bgcolor="#FFFFFF">
      <td align="center"><strong>Parameter</strong></td>
      <td align="center"><string>Value</string></td>
    </tr>
    <tr>
      <td width="20%">Multiple Choice - Question</td>
      <td>
        <?php
        if(isset($_POST['multipleChoiceQuestion']))
          echo $_POST['multipleChoiceQuestion'];
        ?>
      </td>      
    </tr>
    <tr>
      <td width="20%">Multiple Choice - Answer</td> 
      <td>
        <?php 
        if(isset($_POST['multipleChoiceAnswer1'])) {
          echo $_POST['multipleChoiceAnswer1'];
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
        if(isset($_POST['trueOrFalseQuestion']))
          echo $_POST['trueOrFalseQuestion'];
        ?>
      </td>      
    </tr>
    <tr>
      <td width="20%">True or False - Answer</td> 
      <td>
        <?php 
	       //$variable = 5;
	       //file_put_contents("data.txt","FROM TRUE OR FALSE ANSWER", FILE_APPEND | LOCK_EX);
        if(isset($_POST['trueOrFalseAnswer'])) {
          $ans = $_POST['trueOrFalseAnswer'];
          if($ans == 'true')
            echo "True (correct)";
          else
            echo "True";
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
          if($ans == 'true')
            echo "False";
          else
            echo "False (correct)";
        }
        ?>
      </td>      
    </tr>
    <tr>
      <td width="20%">Short Answer - Question</td>
      <td>
        <?php 
        if(isset($_POST['shortAnswerQuestion']))
          echo $_POST['shortAnswerQuestion'];
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
        }
       ?>
     </td>      
   </tr>
   <!--<tr>
    <td width="20%">submit</td>
    <td><?PHP ECHO $_POST['submit']?></td>      
  </tr>-->    
</table>
</br>
<button onclick="history.go(-1);">Back </button>
<form method='post' action="formHandler.php" name="functionPrint">
  <input type="hidden" value="print" name="actionprint" />
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
 
if(isset($_POST['actionprint'])) {
    printToFile();
  }

  function printToFile() {
    $txt = 'HELLOOOOO';
    file_put_contents('data.txt', $txt.PHP_EOL, FILE_APPEND | LOCK_EX);
  }
?>
