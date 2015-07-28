var rootRef = new Firebase("https://gds-2015.firebaseio.com/");

/**
 * On document load
 */
$(function() {

    //authentication callback
    var authData;
    rootRef.onAuth(function(authData) {
        if (authData) {
            // save the user's profile into the database so we can list users,
            // use them in Security and Firebase Rules, and show profiles
            rootRef.child("users").child(authData.uid).set({
                provider: authData.provider,
                name: authData.facebook.displayName
            });
        }
    });

    //authenticates the user
    if (!authData) {
        rootRef.authWithOAuthPopup("facebook", authHandler);
    } else {
        console.log( 'User already authenticated: ' + authData.uid + ' on provider ' + authData.provider );
    }
});

/**
 * Handles authentication
 */
function authHandler(error, authData) {
    if (error) {
        console.log("Login Failed!", error);
    } else {
        console.log("Authenticated successfully with payload:", authData);
    }
}