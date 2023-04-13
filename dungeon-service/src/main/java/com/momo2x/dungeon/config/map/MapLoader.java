package com.momo2x.dungeon.config.map;

import com.momo2x.dungeon.engine.map.DungeonCoord;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MapLoader {

    private static final String CELL_FORMAT = "[###]";

    private static final String CELL_FORMAT_ERR_MSG =
            "Cells must start with \"[\" and end with \"]\" and its content must be 3 characters long";

    private final List<LinkedList<String>> rawMaps;

    private static boolean isFirst(final int idx) {
        return idx == 0;
    }

    public Map<String, List<DungeonCoord>> load() throws MalformedMapException {
        final var map = new HashMap<String, List<DungeonCoord>>();

        var previousMapColumnAmount = -1;
        var currentMapColumnAmount = -1;

        for (var mapIdx = 0; mapIdx < this.rawMaps.size(); mapIdx++) {
            final var rawMap = this.rawMaps.get(mapIdx);

            if (!isFirst(mapIdx)) {
                validateLinesAmountBetweenMaps(mapIdx, this.rawMaps.get(mapIdx - 1).size(), rawMap.size());
            }

            var y = -1;
            for (var lineIdx = 0; lineIdx < rawMap.size(); lineIdx++) {
                final var line = rawMap.get(lineIdx).stripTrailing();

                if (isFirst(lineIdx)) {
                    currentMapColumnAmount = validateFirstLineAndGetColumnsAmount(line);
                    continue;
                }

                y++;

                validateLine(lineIdx, line);

                var x = -1;
                for (var charIdx = 0; charIdx < line.length(); charIdx += CELL_FORMAT.length()) {
                    final var cell = line.substring(charIdx, charIdx + CELL_FORMAT.length());

                    if (isFirst(charIdx)) {
                        validateLineYCoord(lineIdx, cell, y);
                        continue;
                    }

                    x++;

                    if (cell.trim().isBlank()) {
                        continue;
                    }

                    validateCell(lineIdx, charIdx, cell.trim());

                    final var cellId = getElementId(cell);

                    if (isIgnorableContent(cellId)) {
                        continue;
                    }

                    final var coordList = map.getOrDefault(cellId, new LinkedList<>());

                    coordList.add(new DungeonCoord(x, y));
                    map.put(cellId, coordList);
                }

                validateColumnsAmount(currentMapColumnAmount, x + 1);

                if (previousMapColumnAmount > -1) {
                    validateColumnsAmountBetweenMaps(previousMapColumnAmount, currentMapColumnAmount);
                }

                previousMapColumnAmount = currentMapColumnAmount;
            }
        }

        return map;
    }

    private boolean isIgnorableContent(final String cellId) {
        return List.of("###", "   ").contains(cellId);
    }

    private void validateLinesAmountBetweenMaps(
            final int mapIndex,
            final int previousMapLinesAmount,
            final int currentMapLinesAmount) throws MalformedMapException {
        if (previousMapLinesAmount != currentMapLinesAmount) {
            throw new MalformedMapException(
                    "Error processing map %d. The number of lines between maps must be the same".formatted(mapIndex));
        }
    }

    private int validateFirstLineAndGetColumnsAmount(final String line) throws MalformedLineException {
        if (line.isBlank()) {
            throw new MalformedLineException("First line cannot be empty");
        }

        var oneSpaceSeparationLine = line.trim();
        while ((oneSpaceSeparationLine = oneSpaceSeparationLine.replace("  ", " ")).contains("  ")) ;

        final var colNumbers = oneSpaceSeparationLine.split(" ");

        if (colNumbers.length < 3) {
            throw new MalformedLineException("Validating first line. Cannot have a dungeon sized smaller than 3 x 3");
        }

        if (!"0".equals(colNumbers[0])) {
            throw new MalformedLineException("Validating first line. Column numbers must start with '0'");
        }

        for (var i = 1; i < colNumbers.length; i++) {
            try {
                if (Integer.parseInt(colNumbers[i]) != Integer.parseInt(colNumbers[i - 1]) + 1) {
                    throw new MalformedLineException("Validating first line. Column numbers must be in ascending order of one");
                }
            } catch (final NumberFormatException e) {
                throw new MalformedLineException("The first line of the map must have numbers only");
            }
        }

        return colNumbers.length;
    }

    private void validateLine(final int lineIndex, final String line) throws MalformedLineException {
        if (line.trim().isBlank()) {
            throw new MalformedLineException("Line %d cannot be empty".formatted(lineIndex));
        }

        if (!line.contains("[") || !line.contains("]")) {
            throw new MalformedLineException(
                    "No cells found in line %d. %s.".formatted(lineIndex, CELL_FORMAT_ERR_MSG));
        }

        if (StringUtils.countOccurrencesOf(line, "[") != StringUtils.countOccurrencesOf(line, "]")) {
            throw new MalformedLineException(
                    "Wrong format for cell in line %d. %s. Ensure that all cells are correctly formed"
                            .formatted(lineIndex, CELL_FORMAT_ERR_MSG));
        }

        if (line.length() % CELL_FORMAT.length() != 0) {
            throw new MalformedLineException(
                    "Each cell must correspond to a X, Y coordinate in line %d. ".formatted(lineIndex) +
                            "Each coordinate must have %d ".formatted(CELL_FORMAT.length()) +
                            "characters long. Ensure you have the right spaces when placing a cell into the map.");
        }
    }

    private void validateLineYCoord(
            final int lineIndex,
            final String data,
            final int yCoord) throws MalformedLineException {
        final var trimmedData = data.trim();

        int yIndexInData;

        try {
            yIndexInData = Integer.parseInt(trimmedData);
        } catch (final NumberFormatException e) {
            throw new MalformedLineException(
                    "Problem in line %d. The first information for each line must be a numeric line index"
                            .formatted(lineIndex));
        }

        if (yIndexInData != yCoord) {
            throw new MalformedLineException(
                    "The line index is not correct in line %d. Should be %d, but it is %d"
                            .formatted(lineIndex, yCoord, yIndexInData));
        }
    }

    private void validateCell(
            final int lineIndex,
            final int charIndex,
            final String cell) throws MalformedCellException {
        if (!cell.startsWith("[") && !cell.endsWith("]") && cell.length() != CELL_FORMAT.length()) {
            throw new MalformedCellException(
                    "Malformed cell in line %d, character %d. %s."
                            .formatted(lineIndex, charIndex, CELL_FORMAT_ERR_MSG));
        }
    }

    private String getElementId(final String cell) {
        return cell.substring(1, CELL_FORMAT.length() - 1);
    }

    private void validateColumnsAmount(
            int columnAmountInFirstLine,
            int cellAmountAfterLineProcess) throws MalformedMapException {
        if (columnAmountInFirstLine != cellAmountAfterLineProcess) {
            throw new MalformedMapException("The number of cell does not correspond to the number of columns defined");
        }
    }

    private void validateColumnsAmountBetweenMaps(
            int previousMapColumnAmount,
            int currentMapColumnAmount) throws MalformedMapException {
        if (previousMapColumnAmount != currentMapColumnAmount) {
            throw new MalformedMapException("The number of columns between maps must be the same");
        }
    }

}
