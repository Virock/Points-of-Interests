if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition, showError);
} else {
    window.location = referer + "?error=location_not_supported";
}
function showPosition(position) {
    window.location = referer + "?lat=" + position.coords.latitude + "&lon=" + position.coords.longitude;
    return;
}

function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            window.location = referer + "?error=permission_denied";
            break;
        case error.POSITION_UNAVAILABLE:
            window.location = referer + "?error=position_unavailable";
            break;
        case error.TIMEOUT:
            window.location = referer + "?error=timeout";
            break;
        case error.UNKNOWN_ERROR:
            window.location = referer + "?error=exception";
            break;
    }
}