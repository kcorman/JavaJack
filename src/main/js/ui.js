var app;
var board;
var user;

function drawBoard(boardCells) {
    app.todos = boardCells;
}
function takeTurn(id) {
    var parts = id.split("_");
    var row = parseInt(parts[0]);
    var col = parseInt(parts[1]);
    if(board.currentTurn != user) {
        alert("It's not your turn yet!");
        return;
    }
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/games/" + board.id + "/rows/ " + row + "/cols/" + col + "/playerId/" + user,
        "method": "PUT",
        "headers": {
            "content-type": "application/json",
            "cache-control": "no-cache"
        },
        "processData": false,
        "data": "{}"
    }

    $.ajax(settings).done(function (response) {
        board = response;
        drawBoard(response.gameCells);
    });
    console.log("Took turn! row = " + row + ", col = " + col);
}
function onReady() {
    var dpath = window.location.href.split('#')[1];
    var boardId = dpath.split('/')[0];
    user = dpath.split('/')[1];
    console.log("boardId=" + boardId + ", player id = " + user);
    Vue.component('demo-grid', {
        template: '#grid-template',
        props: {
            data: Array,
            columns: Array
        }
    });
    app = new Vue({
        el: '#app',
        data: {
            todos: [],
            turn: "Unknown turn"
        }
    })
    // Set up the game
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/games/" + boardId,
        "method": "GET",
        "headers": {
            "content-type": "application/json",
            "cache-control": "no-cache",
            "postman-token": "eef26263-157c-12f9-ad51-54529302b3aa"
        },
        "processData": false
    }
    var intervalRef = {};
    var refreshBoard = function() {
        $.ajax(settings).done(function (response) {
            board = response;
            if(response.currentTurn == user) {
                app.turn = "Your turn.";
            } else {
                app.turn = response.currentTurn + "'s turn";
            }
            drawBoard(response.gameCells);
            if(board.winner != null) {
                alert("Game over! Winner is " + board.winner);
                app.turn = "Game over. " + board.winner + " wins!";
                clearInterval(intervalRef.interval);
            }
            console.log(response);
        });
    }
    intervalRef.interval = setInterval(refreshBoard, 1000);
    refreshBoard();
    app.todos = [];
    for(var i = 0;i<10;i++) {
        var r = [];
        for(var k = 0;k<10;k++) {
            r.push("a");
        }
        app.todos.push({vals: r});
    }
}

