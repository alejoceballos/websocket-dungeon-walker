const REST_ENDPOINT_MAP = "/v1/maps";
const REST_ENDPOINT_USER = "/v1/users";
const STOMP_REGISTRY_ENDPOINT = "/dungeon-ws";
const SIMPLE_BROKER_DESTINATION = "/dungeon-refresh";
const APP_DESTINATION_PREFIX = "/player-action";

const storage = {};

const start = () => {
    startConnection();
    setKeyPressEvents();
};

const startConnection = () => {
    fetch(`${REST_ENDPOINT_USER}/current`, afterFetchUser);
};

const fetch = (endpoint, onSuccess) =>
    $.ajax({
        url: endpoint,
        type: "GET",
        dataType: "json",
        success: onSuccess
    });

const afterFetchUser = user => {
    storage.user = user.id;
    fetch(REST_ENDPOINT_MAP, afterFetchMap);
};

const afterFetchMap = dungeonMap => {
    createDungeon(dungeonMap);

    storage.socket = new SockJS(STOMP_REGISTRY_ENDPOINT);
    webSocketConnect(storage.socket);
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
    const {width, height, numOfLayers, elements} = dungeonMap;

    for (let idx = 0; idx < width; idx++) {
        $("#dungeon-header>tr").append(`<th>${idx}</th>`);
    }

    for (let y = 0; y < height; y++) {
        const rowId = `dungeon-row-${y}`;

        $("#dungeon-body").append(`<tr id='${rowId}'></tr>`);

        const tr = $(`#${rowId}`);

        tr.append(`<td>${y}</td>`);

        for (let x = 0; x < width; x++) {
            let currId = `${getTdId(x, y, 0)}`;
            tr.append(`<td id=${currId}></td>`);

            for (let layerIdx = 1; layerIdx < numOfLayers; layerIdx++) {
                let currTag = $(`#${currId}`);
                currId = getTdId(x, y, layerIdx);
                currTag.append(`<div id='${currId}'></div>`);
            }
        }
    }

    for (const element of elements) {
        const {avatar, coord, layerIndex: layer} = element;
        const {x, y} = coord;

        $(`#${getTdId(x, y, layer)}`).addClass(avatar);
    }
};

const webSocketConnect = socket => {
    storage.stompClient = Stomp.over(socket);

    storage.stompClient.connect({}, frame => {
        storage.stompClient.subscribe(
            // `/user${SIMPLE_BROKER_DESTINATION}`,
            SIMPLE_BROKER_DESTINATION,
            result => reRender(JSON.parse(result.body)));
    });
};

const reRender = mapUpdate => {
    const {id: walkerId, avatar: walkerAvatar, coord: walkerCoord, layerIndex: walkerLayer} = mapUpdate.elements[0];

    const currentId = getTdId(walkerCoord.x, walkerCoord.y, walkerLayer)

    let prevElemCoord = walkerCoord;

    if (mapUpdate.elements.length > 1) {
        const {coord: prevElemCoord} = mapUpdate.elements[1];
        const previousId = getTdId(prevElemCoord.x, prevElemCoord.y, walkerLayer)
        const previousElement = $(`#${previousId}`);

        previousElement.removeClass();
    }

    const avatarClass = `${walkerAvatar}-${calculateDirection(prevElemCoord, walkerCoord)}`;
    const currentElement = $(`#${currentId}`);
    currentElement.addClass(avatarClass);

    if (walkerId === storage.user) {
        currentElement[0].scrollIntoView({
            behavior: 'auto',
            block: 'center',
            inline: 'center'
        });
    }

    setMessage(`[${walkerId}] From (${prevElemCoord.x},${prevElemCoord.y}) to (${walkerCoord.x},${walkerCoord.y})`);
};

const getTdId = (x, y, layer) => {
    const lyr = layer !== undefined ? `-${layer}` : '';
    return `coord-${x}-${y}${lyr}`
};

const setMessage = msg => $("#p-message").text(msg);

const setDirectionIndicator = msg => {
    clearTimeout(storage.directionTextTimeoutId);

    clearArrows();
    $("#arrow-dir").text(msg);

    const coords = msg.split("");

    $(`#arrow-dir-${coords[0]}`).addClass(`arrow-img-pressed-${coords[0]}`);

    if (msg.length > 1) {
        $(`#arrow-dir-${coords[1]}`).addClass(`arrow-img-pressed-${coords[1]}`);
    }

    storage.directionTextTimeoutId = setTimeout(() => {
        clearArrows();
        $("#arrow-dir").text("");
        clearTimeout(storage.directionTextTimeoutId);
    }, 500);
};

const clearArrows = () => {
    ["N", "E", "S", "W"].forEach(dir => {
        const arrowElem = $(`#arrow-dir-${dir}`);
        arrowElem.removeClass();
        arrowElem.addClass(`arrow-img-released-${dir}`);
    });
};

const calculateDirection = (previousCoord, actualCoord) => {
    const coordDirection =
        (previousCoord.y === actualCoord.y ? "" : previousCoord.y > actualCoord.y ? "N" : "S") +
        (previousCoord.x === actualCoord.x ? "" : previousCoord.x > actualCoord.x ? "W" : "E");

    return coordDirection === "" ? "E" : coordDirection;
};

const send = direction =>
    storage.stompClient.send(
        `${APP_DESTINATION_PREFIX}/move`,
        {},
        JSON.stringify({'direction': direction}));