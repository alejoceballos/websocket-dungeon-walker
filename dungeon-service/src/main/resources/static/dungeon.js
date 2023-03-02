const STOMP_ENDPOINT = "/dungeon-ws";
const REST_ENDPOINT = "/v1/dungeon";
const SIMPLE_BROKER = "/dungeon";

const socket = new SockJS(STOMP_ENDPOINT);

stompClient = Stomp.over(socket);

const start = () => {
    $.ajax({
        url: `${REST_ENDPOINT}/map`,
        type: "GET",
        dataType: "json",
        success: dungeonMap => {
            createDungeon(dungeonMap);
            webSocketConnect(stompClient);
        },

        fail: () => setMessage("Encountered an error")
    });
}

const createDungeon = dungeonMap => {
    const {width, height} = dungeonMap;

    for (let idx = 0; idx < height; idx++) {
        $("#dungeon-header>tr").append(`<th>${idx}</th>`);
    }

    for (let y = 0; y < width; y++) {
        const rowId = `dungeon-row-${y}`;

        $("#dungeon-body").append(`<tr id='${rowId}'></tr>`);

        const tr = $(`#${rowId}`);

        tr.append(`<td>${y}</td>`);

        for (let x = 0; x < height; x++) {
            const content = isLimit(x, y, width, height) ? "W" : "";
            tr.append(`<td id='${getTdId(x, y)}'>${content}</td>`);
        }
    }
}

const webSocketConnect = stompClient => {
    stompClient.connect({}, frame => {
        console.log(frame);
        stompClient.subscribe(
            `/user${SIMPLE_BROKER}`,
            result => reRender(JSON.parse(result.body)));
    });
}

const reRender = walker => {
    const {id, previous, current} = walker;
    const previousId = getTdId(previous.x, previous.y)
    const currentId = getTdId(current.x, current.y)

    setMessage(`[${id}] From (${previous.x},${previous.y}) to (${current.x},${current.y})`);

    $(`#${previousId}`).html("");
    $(`#${currentId}`).html(id);
}

const getTdId = (x, y) => `coord-${x}-${y}`;

const setMessage = msg => $("#p-message").text(msg);

const isLimit = (x, y, width, height) => x === 0
    || x === width - 1
    || y === 0
    || y === height - 1;
