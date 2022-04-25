<?php
/**
 * 
 * @param email String
 * @return Bool  
 */ 
function is_mail($email){
	    $x = '\d\w!\#\$%&\'*+\-/=?\^_`{|}~';    //just for clarity
    return count($email = explode('@', $email, 3)) == 2
        && strlen($email[0]) < 65
        && strlen($email[1]) < 256
        && preg_match("#^[$x]+(\.?([$x]+\.)*[$x]+)?$#", $email[0])
        && preg_match('#^(([a-z0-9]+-*)?[a-z0-9]+\.)+[a-z]{2,6}.?$#', $email[1]);
}

/**
 * 
 * @param date String
 * @return Bool
 * 
 */
function is_date_fr( $date ){
	return preg_match("#^([0-9]){2}/([0-9]){2}/([1-2])([0-9]){3}$#", $date);
}

if ( !function_exists('json_decode') ){
    function json_decode($content, $assoc=false){
                require_once '../class/JSON.php';
                if ( $assoc ){
                    $json = new Services_JSON(SERVICES_JSON_LOOSE_TYPE);
        } else {
                    $json = new Services_JSON;
                }
        return $json->decode($content);
    }
}

if ( !function_exists('json_encode') ){
    function json_encode($content){
                require_once '../class/JSON.php';
                $json = new Services_JSON;
              
        return $json->encode($content);
    }
}

?>