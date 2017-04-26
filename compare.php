<!--
	Telephasic by HTML5 UP	html5up.net | @n33co	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)-->
<html>
  <head>
    <meta content="text/html; charset=utf-8" http-equiv="content-type">
    <title>Compare</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
    <script src="js/jquery.min.js"></script>
    <script src="js/jquery.dropotron.min.js"></script>
    <script src="js/skel.min.js"></script>
    <script src="js/skel-layers.min.js"></script>
    <script src="js/init.js"></script> <noscript>
			<link rel="stylesheet" href="css/skel.css">
			<link rel="stylesheet" href="css/style.css">
		</noscript>
    <!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
  </head>
  <body class="no-sidebar">
    <div id="page-wrapper">
      <div id="header-wrapper" style="margin-bottom:5px">
        <div id="header" class="container" style="padding-bottom:5px">
          <!-- Logo -->
          <h1 id="logo"><a href="search.php">Start Here</a></h1>
          <!-- Nav -->
          <nav id="nav">
            <ul>
              <li><a href="http://www.humanconnectome.org/about/project/">Human Connectome Project</a></li>
              <li class="break"><a href="https://github.com/gw-cs-sd/sd-2017-brain-connectivity">GWU Senior Design Project</a></li>
            </ul>
          </nav>
        </div>
      </div>
      <div class="wrapper" style="padding-top:50px">
        <div class="container" id="main">
          <article id="content">
            <header>
              <?
                $searchID = $_POST['searchID'];
                $file = file_get_contents('./data/smallworld.txt');
                $file = preg_replace("/((\r?\n)|(\r\n?))/", ' : ', $file);
                $lines = explode(' : ', $file);
                $searchKey = array_search($searchID, $lines, FALSE);
                $smallWorld = $lines[$searchKey + 1];
                if($searchKey !== NULL && $searchID !== NULL) :
              ?>
                <h2 align="center">Related to <? echo $searchID; ?></h2>
                <h2 align="center" style="padding-bottom:25px">Small World Measure: <? echo($smallWorld); ?></h2>
                <h2 align="center" style="padding-bottom:0px"><img src="pixelmaps/<?php echo $searchID; ?>map.jpg" width=300 height=25></h2>
                <p style="padding-top:0px; color: black; font-size: 16px" align="center"><a href="#key">1&emsp; 2&emsp; 3&emsp; 4&emsp;&ensp;5&emsp; 6&emsp; 7&emsp; 8&emsp; 9&emsp; 10&emsp;  11</a></p>
                <h2>
                  <table>
                    <thead> 
                      <tr>
                        <th style="font-weight:bold">Subject ID</th>
                        <th style="font-weight:bold">Small World Measure</th>
                        <th style="font-weight:bold">Behavioral Variables</th>
                      </tr>
                    </thead>
                    <tbody align="center">
                        <?php
                          $similarSW = array();
                          $counter = 0;
                          for ($i = 3; $i < count($lines); $i += 2) {
                            if ($lines[$i] >= ($smallWorld - 0.05) && $lines[$i] <= ($smallWorld + 0.05)) {
                               $similarSW[$counter]['subject'] = $lines[$i-1];
                               $similarSW[$counter]['score'] = $lines[$i];
                               $counter++;
                            }
                          }
                          foreach ($similarSW as $row) {
                        ?>
                        <tr>
                          <td><?php echo $row['subject']; ?></td>
                          <td><?php echo $row['score']; ?></td>
                          <td><img src="pixelmaps/<?php echo $row['subject']; ?>map.jpg" width=300 height=25></td>
                        </tr>
                        <?php } ?>
                      </tr>
                    </tbody>
                  </table>
                </h2>
                <p id="key" style="color: black; font-size: 17px;" align="center">1. Gender &nbsp;2. PicVocab &nbsp;3. ProcSpeed  &nbsp;4. AngAffect  &nbsp;5. AngHostile  &nbsp;6. FearAffect  &nbsp;7. FearSomatic  &nbsp;8. Sadness  &nbsp;9. Life Satisfaction  &nbsp;10. Friendship  &nbsp;11. Loneliness</p>

              <?php else : ?>
                <h1 align="center">The subject ID you entered does not correspond to a subject in this dataset.</h1>
              <?php endif; ?>
            </header>
          </article>
        </div>
      </div>
    </div>
  </body>
</html>
