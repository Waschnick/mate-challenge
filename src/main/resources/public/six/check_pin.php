<?php
header("Access-Control-Allow-Origin: *");

$pin = $_GET['pin'];

if ($pin === "123456") {
    echo "true";
} else {
    echo "false";
}