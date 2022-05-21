function closeButton() {

    javaConnector.close();
}

function minimizeButton() {
    javaConnector.minimize()
}

var jsConnector = {
    showResult: function (result) {
        document.getElementById('result').innerHTML = result;
    }
};

function getJsConnector() {
    return jsConnector;
}