var socket = io.connect('http://localhost:3000');
/* Dummy data. Will get updated on receiving playerId event */
var me = {id: 42, teamId: 42, coord: {x: 42, y: 42}};

socket.on('connect', function () {
    console.log('Connected to Server');
});

// Show team ID
var textnode = document.createTextNode('');
document.getElementById('current_team').appendChild(textnode);
socket.on('playerId', function (data) {
    console.log("Got identity: %o", data);
    console.log("My ID: %d, Team: %d", data.id, data.teamId);

    /* Use the information given from the server */
    me = data;

    document.getElementById('current_team').removeChild(textnode);
    textnode = document.createTextNode(String(me.teamId));
    document.getElementById('current_team').appendChild(textnode);
});

socket.on('disconnect', function (mes) {
    console.log("Player %d of Team %d has disconnect\n", mes.id, mes.teamId);
});

socket.on('gameStart', function () {
    console.log('Game has started');
});

socket.on('playerUpdate', function (data) {
    if (data.player.id == me.id) {
        console.log("I moved from (%d, %d) to (%d, %d)",
            data.player.coord.x,
            data.player.coord.y,
            data.newCoord.x,
            data.newCoord.y);

        me = data.player;
        me.coord = data.newCoord;
    }
    // Someone else
    else {
        console.log("Player ID %d of Team %d moved: (%d, %d) => (%d, %d) %o",
                data.player.id, data.player.teamId,
                data.player.coord.x,
                data.player.coord.y,
                data.newCoord.x,
                data.newCoord.y,
                data);
    }
});

socket.on('foodUpdate', function (data) {
    console.log("Food ID %3d of Team %d Update: %o\n",
            data.food.id, data.food.team, data);
});

function move_up() {
    var currentCoord = me.coord;
    var mes = {
        player: me,
        newCoord: {x: currentCoord.x, y: currentCoord.y - 1},
    };
    socket.emit('move', mes);
}

function move_down() {
    var currentCoord = me.coord;
    var mes = {
        player: me,
        newCoord: {x: currentCoord.x, y: currentCoord.y + 1},
    };
    socket.emit('move', mes);
}

function move_left() {
    var currentCoord = me.coord;
    var mes = {
        player: me,
        newCoord: {x: currentCoord.x - 1, y: currentCoord.y},
    };
    socket.emit('move', mes);
}

function move_right() {
    var currentCoord = me.coord;
    var mes = {
        player: me,
        newCoord: {x: currentCoord.x + 1, y: currentCoord.y},
    };
    socket.emit('move', mes);
}
