// Отключает вывод контекстного меню ПКМ
document.oncontextmenu = function () {
    return false;
}

var closebtn = document.getElementById("close");
var minimizebtn = document.getElementById("minimize");
var play = document.getElementById("play");

function blockMove() {
    javaConnector.blockMove();
}


closebtn.onclick = function () {
    closeAnimation();
}

function closeAnimation() {
    var HTMLwindow = document.getElementById("window");
    var radius = 1200;
    var x = 4.95;
    var id = setInterval(frame, 15);

    function frame() {
        if (radius > 0) {
            HTMLwindow.style.clipPath = `circle(${radius}px at right 21px top 15px)`;
            x -= 0.1;
            radius -= Math.exp(x);
        } else {
            HTMLwindow.style.visibility = "hidden";
            clearInterval(id);
        }
    }
    window.setTimeout(close, 400);
}

function close() {
    javaConnector.close();
}

minimizebtn.onclick = function () {
    var HTMLwindow = document.getElementById("window");
    var height = 0;
    var x = 4.3;
    var id = setInterval(frame, 10);

    function frame() {
        height += Math.exp(x)
        x -= 0.1;
        if (height >= 600) {
            HTMLwindow.style.visibility = "hidden";
            HTMLwindow.style.clipPath = `inset(0 0 0 0)`;
            clearInterval(id);
        } else {
            HTMLwindow.style.clipPath = `inset(0 0 ${height}px 0)`;
        }
    }

    window.setTimeout(minimize, 300);
}

function minimize() {
    document.getElementById("window").style.visibility = "visible";
    javaConnector.minimize()
}

play.onclick = function () {
    javaConnector.play();
}

var jsConnector = {
    showResult: function (result) {
        document.getElementById('result').innerHTML = result;
    }
};

function getJsConnector() {
    return jsConnector;
}