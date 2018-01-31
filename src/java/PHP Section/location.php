<?php
//If the user came here directly, do nothing
/* if (!isset($_SERVER['HTTP_REFERER'])) {
  echo "Make a get request from your website to this page to get your location";
  return;
  }
  $referer = $_SERVER['HTTP_REFERER']; */
//$referer = "http://localhost:8080/CS3220_POI_Project/main";
require_once 'global.php';
?>
<html>
    <head>
        <title>Get your location</title>
        <meta charset="UTF-8">
        <noscript>
        <meta http-equiv="refresh" content="1; URL=<?php echo REFERER . "error=javascript_disabled"; ?>"/>
        </noscript>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="location.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            var referer = "<?php echo REFERER; ?>";
        </script>
    </head>
    <body>
    </body>
</html>
