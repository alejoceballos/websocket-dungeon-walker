const REST_ENDPOINT = "/v1/map";
const STOMP_REGISTRY_ENDPOINT = "/dungeon-ws";
const SIMPLE_BROKER_DESTINATION = "/dungeon-refresh";
const APP_DESTINATION_PREFIX = "/player-action";

const start = () => {
    startConnection();
    setKeyPressEvents();
}

const startConnection = () => {
    const socket = new SockJS(STOMP_REGISTRY_ENDPOINT);

    $.ajax({
        url: `${REST_ENDPOINT}`,
        type: "GET",
        dataType: "json",
        success: dungeonMap => {
            createDungeon(dungeonMap);
            webSocketConnect(socket);
        },
        fail: () => setMessage("Encountered an error")
    });

    return socket;
};

const setKeyPressEvents = () => {
    const ARROW_LEFT = "37";
    const ARROW_UP = "38";
    const ARROW_RIGHT = "39";
    const ARROW_DOWN = "40";

    const arrowsMap = {};
    arrowsMap[ARROW_LEFT] = "W";
    arrowsMap[ARROW_UP] = "N";
    arrowsMap[ARROW_RIGHT] = "E";
    arrowsMap[ARROW_DOWN] = "S";

    const keys = {};

    $(document).keydown(e => {
        keys[e.which] = true;

        const keySet = Object.keys(keys);
        const keySetCount = keySet.length;

        if (keySetCount === 0 || keySetCount > 2) {
            return "";
        }

        const {v, h} = keySet.reduce((acc, key) => {
            if ([ARROW_UP, ARROW_DOWN].includes(key)) {
                acc.v = acc.v === "" ? arrowsMap[key] : "";
            } else if ([ARROW_LEFT, ARROW_RIGHT].includes(key)) {
                acc.h = acc.h === "" ? arrowsMap[key] : "";
            }

            return {v: acc.v, h: acc.h};
        }, {
            v: "",
            h: ""
        });

        let msg = v + h;

        if (msg === "") {
            msg = "-";
        } else {
            send(msg)
        }

        setDirectionIndicator(msg);
    });

    $(document).keyup(e => {
        delete keys[e.which];
    });
};

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
};

let dungeonPlayerClient;

const webSocketConnect = socket => {
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, frame => {
        console.log(frame);
        stompClient.subscribe(
            // `/user${SIMPLE_BROKER_DESTINATION}`,
            SIMPLE_BROKER_DESTINATION,
            result => reRender(JSON.parse(result.body)));
    });

    dungeonPlayerClient = stompClient;
};

const reRender = walker => {
    const {id, avatar, previous, current} = walker;
    const previousId = getTdId(previous.x, previous.y)
    const currentId = getTdId(current.x, current.y)

    const avatarClass = `${avatar}-${calculateDirection(previous, current)}`;

    $(`#${previousId}`).removeClass();
    $(`#${currentId}`).addClass(avatarClass);

    setMessage(`[${id}] From (${previous.x},${previous.y}) to (${current.x},${current.y})`);
};

const getTdId = (x, y) => `coord-${x}-${y}`;

const setMessage = msg => $("#p-message").text(msg);

let directionTextTimeoutId = undefined;

const setDirectionIndicator = msg => {
    clearTimeout(directionTextTimeoutId);
    $("#p-direction").text(msg);

    directionTextTimeoutId = setTimeout(() => {
        $("#p-direction").text("-");
        clearTimeout(directionTextTimeoutId);
    }, 2000);
}

const calculateDirection = (previousCoord, actualCoord) => {
    const coordDirection =
        (previousCoord.y === actualCoord.y ? "" : previousCoord.y > actualCoord.y ? "N" : "S") +
        (previousCoord.x === actualCoord.x ? "" : previousCoord.x > actualCoord.x ? "W" : "E");

    return coordDirection === "" ? "E" : coordDirection;
};

const send = direction => {
    dungeonPlayerClient.send(
        `${APP_DESTINATION_PREFIX}/move`,
        {},
        JSON.stringify(
            {
                'direction': direction
            }));
};