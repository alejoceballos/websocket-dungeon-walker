const STOMP_ENDPOINT = "/dungeon-ws";
const REST_ENDPOINT = "/v1/dungeon";
const SIMPLE_BROKER = "/dungeon";

const start = () => {
    const socket = new SockJS(STOMP_ENDPOINT);

    $.ajax({
        url: `${REST_ENDPOINT}/map`,
        type: "GET",
        dataType: "json",
        success: dungeonMap => {
            createDungeon(dungeonMap);
            webSocketConnect(socket);
        },
        fail: () => setMessage("Encountered an error")
    });

    return socket;
}

const createDungeon = dungeonMap => {
    const {width, height, elements} = dungeonMap;

    for (let idx = 0; idx < width; idx++) {
        $("#dungeon-header>tr").append(`<th>${idx}</th>`);
    }

    for (let y = 0; y < height; y++) {
        const rowId = `dungeon-row-${y}`;

        $("#dungeon-body").append(`<tr id='${rowId}'></tr>`);

        const tr = $(`#${rowId}`);

        tr.append(`<td>${y}</td>`);

        for (let x = 0; x < width; x++) {
            tr.append(`<td id='${getTdId(x, y)}'></td>`);
        }

        for (const element of elements) {
            const {avatar, coord} = element;
            const {x, y} = coord;

            $(`#${getTdId(x, y)}`).addClass(avatar);
        }
    }
}

const webSocketConnect = socket => {
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, frame => {
        console.log(frame);
        stompClient.subscribe(
            `/user${SIMPLE_BROKER}`,
            result => reRender(JSON.parse(result.body)));
    });

    return stompClient;
}

const reRender = walker => {
    const {id, avatar, previous, current} = walker;
    const previousId = getTdId(previous.x, previous.y)
    const currentId = getTdId(current.x, current.y)

    setMessage(`[${id}] From (${previous.x},${previous.y}) to (${current.x},${current.y})`);

    $(`#${previousId}`).removeClass(avatar);
    $(`#${currentId}`).addClass(avatar);
}

const getTdId = (x, y) => `coord-${x}-${y}`;

const setMessage = msg => $("#p-message").text(msg);
