<html>
<head>
  <title>Simple form handler</title>
</head>

<body bgcolor="#EEEEEE">
  <center><h2>Simple Form Handler</h2></center>
  <p>
    The following table lists all parameter names and their values that were submitted from your form.
  </p>

  <table cellSpacing=1 cellPadding=1 width="75%" border=1 bgColor="lavender">
    <tr bgcolor="#FFFFFF">
      <td align="center"><strong>Parameter</strong></td>
      <td align="center"><string>Value</string></td>
    </tr>
    <tr>
      <td width="20%">questionA</td>
      <td><?php echo $_POST['questionA']?></td>      
    </tr>
    <tr>
      <td width="20%">answerA</td> 
      <td><?php echo $_POST['answerA']?></td>      
    </tr>
    <tr>
      <td width="20%">questionB</td>
      <td><?php echo $_POST['questionB']?></td>      
    </tr>
    <tr>
      <td width="20%">answerB</td> 
      <td><?php echo $_POST['answerB']?></td>      
    </tr>
    <tr>
      <td width="20%">questionC</td>
      <td><?php echo $_POST['questionC']?></td>      
    </tr>
    <tr>
      <td width="20%">answerC</td> 
      <td><?php echo $_POST['answerC']?></td>      
    </tr>
    <tr>
      <td width="20%">questionD</td>
      <td><?php echo $_POST['questionD']?></td>      
    </tr>
    <tr>
      <td width="20%">answerD</td> 
      <td><?php echo $_POST['answerD']?></td>      
    </tr>
    <tr>
      <td width="20%">submit</td>
      <td><?PHP ECHO $_POST['submit']?></td>      
    </tr>    
  </table>

</body>
</html> 
