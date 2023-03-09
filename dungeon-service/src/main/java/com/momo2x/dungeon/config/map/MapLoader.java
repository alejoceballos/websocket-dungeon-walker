package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.engine.map.DungeonCoord;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MapLoader {

    private static final String CELL_FORMAT = "[###]";

    private static final String CELL_FORMAT_ERR_MSG = """
            Cells must start with "[" and end with "[" and its content must be 3 characters long""";

    private final List<String> rawMap;

    public Map<String, List<DungeonCoord>> load() throws MalformedMapException {
        final var map = new HashMap<String, List<DungeonCoord>>();

        for (var y = 0; y < this.rawMap.size(); y++) {
            final var line = this.rawMap.get(y).stripTrailing();

            validateLine(line);

            var x = -1;
            for (var col = 0; col < line.length(); col += CELL_FORMAT.length()) {
                final var cell = line.substring(col, col + CELL_FORMAT.length());
                x++;

                if (cell.trim().isBlank()) {
                    continue;
                }

                validateCell(cell.trim());

                final var cellId = getElementId(cell);
                final var coordList = map.getOrDefault(cellId, new LinkedList<>());

                coordList.add(new DungeonCoord(x, y));
                map.put(cellId, coordList);
            }
        }

        return map;
    }

    private void validateLine(final String line) throws MalformedLineException {
        final var trimmedLine = line.trim();

        if (trimmedLine.isBlank()) {
            throw new MalformedLineException("Line cannot be empty");
        }

        if (!trimmedLine.contains("[") && !trimmedLine.contains("]")) {
            throw new MalformedLineException("No cells found. %s.".formatted(CELL_FORMAT_ERR_MSG));
        }

        if (trimmedLine.length() % CELL_FORMAT.length() != 0) {
            throw new MalformedLineException(
                    "Each cell must correspond to a X, Y coordinate. " +
                            "Each coordinate must have %d ".formatted(CELL_FORMAT.length()) +
                            "characters long. Ensure you have the right spaces when placing a cell into the map.");
        }
    }

    private void validateCell(final String cell) throws MalformedCellException {
        if (!cell.contains("[") && !cell.contains("]") && cell.length() != CELL_FORMAT.length()) {
            throw new MalformedCellException("Malformed cell. %s.".formatted(CELL_FORMAT_ERR_MSG));
        }
    }

    private String getElementId(final String cell) {
        return cell.substring(1, CELL_FORMAT.length() - 1);
    }

}
