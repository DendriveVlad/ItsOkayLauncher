// Отключает вывод контекстного меню ПКМ
document.oncontextmenu = function () {
    return false;
}

var closebtn = document.getElementById("close");
var minimizebtn = document.getElementById("minimize");

function blockMove() {
    javaConnector.blockMove();
}


closebtn.onclick = function () {
    closeAnimation()
}

function closeAnimation() {
    var HTMLwindow = document.getElementById("window");
    var radius = 1200;
    var x = -4.95;
    var id = setInterval(frame, 15);

    function frame() {
        if (radius > 0) {
            HTMLwindow.style.clipPath = `circle(${radius}px at right 21px top 15px)`;
            x += 0.1;
            radius -= Math.exp(-x);
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
    var height = 100;
    var id = setInterval(frame, 10);

    function frame() {
        height -= 10;
        if (!height) {
            HTMLwindow.style.visibility = "hidden";
            clearInterval(id);
        } else {
            HTMLwindow.style.height = height + "%";
        }
    }

    window.setTimeout(minimize, 200);
}

function minimize() {
    document.getElementById("window").style.height = "100%";
    document.getElementById("window").style.visibility = "visible";
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