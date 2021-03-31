<?php
require "DataBase.php";

$db = new DataBase();// create a new database

// if username and password are not null 
if (isset($_POST['username']) && isset($_POST['password'])) {

    if ($db->dbConnect()) { // and a connection exists
        
        // collect username and password data
        if ($db->signUp("users", $_POST['username'], $_POST['password'])) {
            echo "Success";

        } else echo "Sign up Failed. Please try again using a different username.";
    } else echo "Error: Database connection failed";
} else echo "All fields are required";
?>
