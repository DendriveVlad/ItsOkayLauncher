// Отключает вывод контекстного меню ПКМ
document.oncontextmenu = function () {
    return false;
}

let is_closing = false;
let is_minimizing = false;

var closebtn = document.getElementById("close");
var minimizebtn = document.getElementById("minimize");
// var play = document.getElementById("play");

function blockMove() {
    javaConnector.blockMove();
}


closebtn.onclick = function (e) {
    if (e.button !== 0 || is_closing) {
        return
    }
    is_closing = true;
    closeAnimation();
}

function closeAnimation() {
    var HTMLwindow = document.getElementById("window");
    var radius = 1200;
    var x = 0;
    var id = setInterval(frame, 15);
    var stopAnimation = 0;

    function frame() {
        if (stopAnimation === 2) {
            close();
            clearInterval(id);
        } else if (radius > 0) {
            HTMLwindow.style.clipPath = `circle(${radius}px at right 21px top 15px)`;
            radius -= Math.exp(4-x/16);
            x += 0.1;
        } else {
            HTMLwindow.style.visibility = "hidden";
            stopAnimation++;
        }
    }
}

function close() {
    javaConnector.close();
}

minimizebtn.onclick = function (e) {
    if (e.button !== 0 || is_minimizing) {
        return
    }
    is_minimizing = true;
    var HTMLwindow = document.getElementById("window");
    var height = 0;
    var x = 0;
    var id = setInterval(frame, 10);
    var stopAnimation = 0;

    function frame() {
        height += Math.exp(3.8-x/16)
        x += 0.1;
        if (stopAnimation === 2) {
            minimize();
            clearInterval(id);
        } else if (height >=600) {
            HTMLwindow.style.visibility = "hidden";
            HTMLwindow.style.clipPath = `inset(0 0 0 0)`;
            stopAnimation++;
        } else {
            HTMLwindow.style.clipPath = `inset(0 0 ${height}px 0)`;
        }
    }
}

function minimize() {
    document.getElementById("window").style.visibility = "visible";
    javaConnector.minimize();
    is_minimizing = false;
}

// play.onclick = function (e) {
//     if (e.button !== 0) {
//         return
//     }
//     javaConnector.play();
// }

var jsConnector = {
    showResult: function (result) {
        document.getElementById('result').innerHTML = result;
    }
};

function getJsConnector() {
    return jsConnector;
}