var app;
var board;

function drawBoard(boardCells) {
    app.todos = boardCells;
}
function takeTurn(id) {
    var parts = id.split("_");
    var row = parseInt(parts[0]);
    var col = parseInt(parts[1]);
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/games/" + board.id + "/rows/ " + row + "/cols/" + col + "/playerId/" + board.currentTurn,
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
        if(board.winner != null) {
            alert("Game over! Winner is " + board.winner);
        }
    });
    console.log("Took turn! row = " + row + ", col = " + col);
}
function onReady() {
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
            todos: []
        }
    })
    // Set up the game
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/games",
        "method": "POST",
        "headers": {
            "content-type": "application/json",
            "cache-control": "no-cache"
        },
        "processData": false,
        "data": "{\n\t\"boardSize\" : 10,\n\t\"players\" : [\n\t\t\"foo\",\n\t\t\"bar\"\n\t]\n}"
    }

    $.ajax(settings).done(function (response) {
        board = response;
        drawBoard(response.gameCells);
        currentTurn = response.currentTurn;
        console.log(response);
    });

    app.todos = [];
    for(var i = 0;i<10;i++) {
        var r = [];
        for(var k = 0;k<10;k++) {
            r.push("a");
        }
        app.todos.push({vals: r});
    }
}

