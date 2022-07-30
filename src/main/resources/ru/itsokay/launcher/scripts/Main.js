// Отключает вывод контекстного меню ПКМ
document.oncontextmenu = function () {
    return false;
}

let serversCount = 3 // Количество спаршеных серверов (пока что здесь просто число, потом будет значение браться с сайта)

let launcherPage = "Play"; // Addons / Play / Settings
let SelectedServer = "Server1"; // Выбранный сервер

let is_closing = false; // Закрывается на данный момент, чтобы не дрочили кнопку
let is_minimizing = false; // Тоже самое, что и выше

var closebtn = document.getElementById("close"); // Объект кнопки закрытия приложения
var minimizebtn = document.getElementById("minimize"); // Объект кнопки сворачивания приложения
var lockSpace = document.getElementById("lockSpace"); // При появлении левой минюшки блокирует доступ к остальным кнопкам и затемняет экран
// var play = document.getElementById("play");

// Выводит инфу в консоль
function info(message) {
    javaConnector.test(message);
}

// Изменение страницы
function changePage(page) {
    launcherPage = page;
    loadPage();
}

// Выключение выбранного сервера
function SelectSelectedServer() {
    let front = document.getElementById(SelectedServer).getElementsByClassName("ServerFront")[0];
    front.style.left = "-3px";
    front.style.top = "-3px";
    front.style.border = "solid #b9b9b9";
}

// Загрузка страницы
function loadPage() {
    var page_content = javaConnector.loadPage(launcherPage);
    document.getElementById("launcherPage").innerHTML = "";
    document.getElementById("launcherPage").innerHTML += page_content;

    // Отображения выбранной страницы в менюшке
    switch (launcherPage) {
        case "Addons":
            document.getElementById("LMenuSelected").style.top = "23.5%";
            break;
        case "Play":
            document.getElementById("LMenuSelected").style.top = "50%";
            SelectSelectedServer()
            break;
        case "Settings":
            document.getElementById("LMenuSelected").style.top = "76.5%";
            break;
    }
}

// Блокировка перемещения окна при нажатии на кнопки закрытия или сворачивания
function blockMove() {
    javaConnector.blockMove();
}

// Обработка клика на кнопку закрытия
closebtn.onclick = function (e) {
    if (e.button !== 0 || is_closing) {
        return
    }
    is_closing = true;
    closeAnimation();
}

// Анимация закрытия приложения
function closeAnimation() {
    var HTMLwindow = document.getElementById("window");
    var radius = 1200;
    var x = 0;
    var id = setInterval(frame, 15);
    var stopAnimation = 0;
    lockSpace.style.background = "rgba(22,22,22,1)";
    lockSpace.style.top = "30px";

    function frame() {
        if (stopAnimation === 2) {
            close();
            clearInterval(id);
        } else if (radius > 0) {
            HTMLwindow.style.clipPath = `circle(${radius}px at right 21px top 15px)`;
            radius -= Math.exp(4 - x / 16);
            x += 0.1;
        } else {
            HTMLwindow.style.visibility = "hidden";
            stopAnimation++;
        }
    }
}

// Закрытие приложения
function close() {
    javaConnector.close();
}

// Оброботка клика на кнопку сворачивания приложения и анимация сворачивания
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
        height += Math.exp(3.8 - x / 16)
        x += 0.1;
        if (stopAnimation === 2) {
            minimize();
            clearInterval(id);
        } else if (height >= 600) {
            HTMLwindow.style.visibility = "hidden";
            HTMLwindow.style.clipPath = null; // удаление clipPath из css для исправления бага
            stopAnimation++;
        } else {
            HTMLwindow.style.clipPath = `inset(0 0 ${height}px 0)`;
        }
    }
}

// Сворачивание приложения
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

// Обротка закрытия экрана при наведении на левую менюшку
function lockScreen() {
    lockSpace.style.background = "rgba(22,22,22,0.5)";
    lockSpace.style.top = "30px";
}

// Возобнавление функциональности экрана приложения
function unlockScreen() {
    lockSpace.style.background = "rgba(0, 0, 0, 0)";
    setTimeout(function () {
        lockSpace.style.top = "100%";
    }, 200);
}

// Сброс выбора предыдущего сервера
function selectServer(serverID) {
    let front = document.getElementById(SelectedServer).getElementsByClassName("ServerFront")[0];
    front.style.left = "0";
    front.style.top = "0";
    front.style.border = null;
    SelectedServer = serverID;
    SelectSelectedServer();
}

// Подключение JSConnector (хз чё это, честно)
var jsConnector = {
    showResult: function (result) {
        document.getElementById('result').innerHTML = result;
    }
};

// Абсолютно бесполезная хрень, тк она не работает. Должна вызывать функции JS из Java.
function getJsConnector() {
    return jsConnector;
}