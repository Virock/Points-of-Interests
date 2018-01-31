<?php
require_once 'global.php';
//Pass all parameters to javascript
?>
<html>
    <head>
        <title>Get your location</title>
        <meta charset="UTF-8">
        <noscript>
        <meta http-equiv="refresh" content="1; URL=<?php echo SEARCH . "&error=javascript_disabled"; ?>"/>
        </noscript>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="re-location.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            var ids = new Array();
            <?php foreach ($_GET['ID'] as $key => $val) { ?>
                ids.push('<?php echo $val; ?>');
            <?php } ?>
                
                var lons = new Array();
            <?php foreach ($_GET['lon'] as $key => $val) { ?>
                lons.push('<?php echo $val; ?>');
            <?php } ?>
                
            var lats = new Array();
            <?php foreach ($_GET['lat'] as $key => $val) { ?>
                lats.push('<?php echo $val; ?>');
            <?php } ?>    
            var referer = "<?php echo SEARCH; ?>";
        </script>
    </head>
    <body>
    </body>
</html>