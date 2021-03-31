<?php
require "DataBase.php";
$db = new DataBase(); // create a new database

// check if username and password are set
if (isset($_POST['username']) && isset($_POST['password'])) {
    // if db connection is not null
    if ($db->dbConnect()) {
        // collect input values
        if ($db->logIn("users", $_POST['username'], $_POST['password'])) {
            echo "Success";
        } else echo "Username or Password is incorrect. Please try again.";
    } else echo "Error: Database connection failed.";
} else echo "All fields are required";
?>
