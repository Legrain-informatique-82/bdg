<?php 
header("Content-Type: text/xml;charset=utf-8");  
$ht_user = $_SERVER['PHP_AUTH_USER']; // pour le login
$ht_pass = $_SERVER['PHP_AUTH_PW']; // pour le mot de passe
//echo "$ht_user - $ht_pass";
$ht_user=$_GET['p'];
//if($ht_user=="a") {
if($ht_user==1) {
/*
<?xml version="1.0" encoding="UTF-8"?>
*/
print('<?xml version="1.0" encoding="UTF-8"?>
<site>
   <description url="http://bureaugestion.fr/updates">
      Site de mise à jour du bureau de gestion u1='.$ht_user.'
   </description>
   <feature url="features/ArticlesFeature_1.0.6.jar" id="ArticlesFeature" version="1.0.6">
      <category name="Facturation"/>
   </feature>
   <feature url="features/TiersFeature_1.0.6.jar" id="TiersFeature" version="1.0.6">
      <category name="Facturation"/>
   </feature>
   <feature url="features/FactureFeature_1.0.6.jar" id="FactureFeature" version="1.0.6">
      <category name="Facturation"/>
   </feature>
   <feature url="features/UpdaterFeature_1.0.6.jar" id="UpdaterFeature" version="1.0.6">
      <category name="Librairies"/>
   </feature>
   <feature url="features/LibJbuilderFeature_1.0.6.jar" id="LibJbuilderFeature" version="1.0.6">
      <category name="Librairies"/>
   </feature>
   <feature url="features/LibLgrBirtFeature_1.0.6.jar" id="LibLgrBirtFeature" version="1.0.6">
      <category name="Librairies"/>
   </feature>
   <feature url="features/GestionCommerciale_1.0.6.jar" id="GestionCommerciale" version="1.0.6">
      <category name="Base"/>
   </feature>
   <feature url="features/GestionCommercialeBase_1.0.6.jar" id="GestionCommercialeBase" version="1.0.6">
      <category name="Base"/>
   </feature>
   <category-def name="Facturation" label="Facturation"/>
   <category-def name="Librairies" label="Librairies"/>
   <category-def name="Base" label="Base"/>
</site>
');
} else {
print('<?xml version="1.0" encoding="UTF-8"?>
<site>
   <description url="http://bureaugestion.fr/updates">
       Site de mise à jour du bureau de gestion u2='.$ht_user.'
   </description>
   <feature url="features/ArticlesFeature_1.0.6.jar" id="ArticlesFeature" version="1.0.6">
      <category name="Facturation"/>
   </feature>
   <feature url="features/TiersFeature_1.0.6.jar" id="TiersFeature" version="1.0.6">
      <category name="Facturation"/>
   </feature>
   <feature url="features/FactureFeature_1.0.6.jar" id="FactureFeature" version="1.0.6">
      <category name="Facturation"/>
   </feature>
   <feature url="features/UpdaterFeature_1.0.6.jar" id="UpdaterFeature" version="1.0.6">
      <category name="Librairies"/>
   </feature>
   <feature url="features/LibJbuilderFeature_1.0.6.jar" id="LibJbuilderFeature" version="1.0.6">
      <category name="Librairies"/>
   </feature>
   <feature url="features/LibLgrBirtFeature_1.0.6.jar" id="LibLgrBirtFeature" version="1.0.6">
      <category name="Librairies"/>
   </feature>
   <feature url="features/GestionCommerciale_1.0.6.jar" id="GestionCommerciale" version="1.0.6">
      <category name="Base"/>
   </feature>
   <feature url="features/GestionCommercialeBase_1.0.6.jar" id="GestionCommercialeBase" version="1.0.6">
      <category name="Base"/>
   </feature>
   <category-def name="Facturation" label="Facturation"/>
   <category-def name="Librairies" label="Librairies"/>
   <category-def name="Base" label="Base"/>
</site>
');
}
?>

