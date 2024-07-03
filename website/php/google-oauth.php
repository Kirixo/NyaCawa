<?php

// Initialize the session
session_start();

// Include the Google API Client library
require __DIR__ . '/../google-api-client/vendor/autoload.php';

// Include database connection
require_once('db.php');

// Update the following variables
$google_oauth_client_id = '48675728508-rn8n65r95gnjd4ni0gqcq5etiao5228f.apps.googleusercontent.com';
$google_oauth_client_secret = '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';
$google_oauth_redirect_uri = 'http://localhost/NyaCawa/php/google-oauth.php';

// Create the Google Client object
$client = new Google_Client();
$client->setClientId($google_oauth_client_id);
$client->setClientSecret($google_oauth_client_secret);
$client->setRedirectUri($google_oauth_redirect_uri);
$client->addScope("https://www.googleapis.com/auth/userinfo.email");
$client->addScope("https://www.googleapis.com/auth/userinfo.profile");

// If the captured code param exists and is valid
if (isset($_GET['code']) && !empty($_GET['code'])) {
    try {
        // Exchange the one-time authorization code for an access token
        $accessToken = $client->fetchAccessTokenWithAuthCode($_GET['code']);
        $client->setAccessToken($accessToken);

        // Make sure access token is valid
        if (isset($accessToken['access_token']) && !empty($accessToken['access_token'])) {
            // Now that we have an access token, we can fetch the user's profile data
            $google_oauth = new Google\Service\Oauth2($client);
            $google_account_info = $google_oauth->userinfo->get();

            // Make sure the profile data exists
            if (isset($google_account_info->email) && isset($google_account_info->name)) {
                // Authenticate the user
                $email = $google_account_info->email;
                $name = $google_account_info->name;

// Check if the user already exists in the database
                $check_query = "SELECT * FROM users WHERE email=?";
                $stmt = $conn->prepare($check_query);
                $stmt->bind_param("s", $email);
                $stmt->execute();
                $result = $stmt->get_result();

                if ($result->num_rows == 0) {
                    // User does not exist, insert them into the database
                    $insert_query = "INSERT INTO users (name, email, password, status) VALUES (?, ?, ?, ?)";
                    $stmt = $conn->prepare($insert_query);
                    $status = 'Кадет'; // Set an appropriate status for the user
                    $fake_password = md5(uniqid(rand(), true)); // Generate a fake password
                    $stmt->bind_param("ssss", $name, $email, $fake_password, $status);
                    $stmt->execute();

                    // Retrieve the user ID of the newly inserted user
                    $user_id = $stmt->insert_id;

                    // Set the user ID in the session
                    $_SESSION['user_id'] = $user_id;

                    // Set the user's name in the session
                    $_SESSION['username'] = $name;
                } else {
                    // User already exists, retrieve their ID and name
                    $existing_user = $result->fetch_assoc();
                    $user_id = $existing_user['user_id'];
                    $name = $existing_user['name'];

                    // Set the user ID in the session
                    $_SESSION['user_id'] = $user_id;

                    $_SESSION['username'] = $name;
                }

// Set session variables
                session_regenerate_id();
                $_SESSION['google_loggedin'] = TRUE;
                $_SESSION['google_email'] = $email;
                $_SESSION['google_name'] = $name; // Save Google name in session
                $_SESSION['google_picture'] = $google_account_info->picture;

// Redirect to profile page or home page
                header('Location: index.php');
                exit;
 } else {
                exit('Could not retrieve profile information! Please try again later!');
            }
        } else {
            exit('Invalid access token! Please try again later!');
        }
    } catch (Exception $e) {
        echo 'Caught exception: ',  $e->getMessage(), "\n";
    }
} else {
    // Redirect to Google Authentication page
    $authUrl = $client->createAuthUrl();
    header('Location: ' . filter_var($authUrl, FILTER_SANITIZE_URL));
    exit;
}
?>
