import processing.core.PApplet;

import java.util.*;

public class Main extends PApplet {

    int currCell = randCell();

    int chessSize = 800;

    List<Map.Entry<Integer, Boolean>> history = new ArrayList<>();
    private int yellowHighlightedCell = -1;
    private int redHighlightedCell = -1;
    int highlightTime = -1;

    final long startTime = System.currentTimeMillis();
    public void settings() {
        size(chessSize, chessSize + 40);
    }

    public void setup() {
        noStroke();
    }

    public void draw() {
        translate(0, 40);
        background(28);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fillHex(getCellColor(i, j));
                rect(i * width / 8f, j * chessSize / 8f, width / 8f, chessSize / 8f);
            }
        }
        textSize(20);
        for (int i = 0; i < 8; i++) {
            fillHex(i % 2 == 0 ? 0xb58963 : 0xefd9b5);
            text(8 - i, 1, i * chessSize / 8f + 17);
            fillHex(i % 2 == 0 ? 0xefd9b5 : 0xb58963);
            text("abcdefgh".charAt(i), (i + 1) * width / 8f - 14, chessSize - 5);
        }

        fill(193);
        textSize(15);
        translate(0, -40);
        int totalDisplacement = 0;
        for (int i = 0; i < Math.min(history.size(), 15); i++) {
            Map.Entry<Integer, Boolean> cell = history.get(history.size() - i - 1);
            String text = "abcdefgh".charAt(cell.getKey() % 8) + String.valueOf(8 - (cell.getKey()) / 8);
            totalDisplacement += getGraphics().textWidth(text);
            if (cell.getValue()) fillHex(0x244a00);
            else fillHex(0x5e0600);
            text(text, width / 2f - 16 - i * 4 - totalDisplacement, 36);
        }
        textSize(36);
        fill(193);
        text("abcdefgh".charAt(currCell % 8) + String.valueOf(8 - currCell / 8), width / 2f - 5, 34);

        textSize(15);
        int numOfRights = (int) history.stream().filter(Map.Entry::getValue).count();
        text(String.format("%s/%s", numOfRights, history.size()), width-60, 16);

        int timeDiff = (int) (System.currentTimeMillis() - startTime);
        String timeText = String.format("%s:%s", addZeroIfNeeded(String.valueOf(timeDiff/60000)), addZeroIfNeeded(String.valueOf(timeDiff/1000%60)));
        text(timeText, 2, 16);

        if (history.size() > 0) {
            int avgTime = timeDiff / history.size();
            String avgTimeText = String.format("%s:%s", addZeroIfNeeded(String.valueOf(avgTime/1000)), addZeroIfNeeded(String.valueOf(avgTime%1000/10)));
            text(avgTimeText, 24, 35);
        }

        if (highlightTime == 0) redHighlightedCell = yellowHighlightedCell = -1;
        else if (highlightTime > 0) highlightTime--;
    }

    public void mousePressed() {
        int col = (mouseX) * 8 / width;
        int row = (mouseY - 40) * 8 / chessSize;
        int clickedCell = col + row * 8;
        yellowHighlightedCell = clickedCell;
        redHighlightedCell = currCell;
        highlightTime = 90;
        history.add(new AbstractMap.SimpleEntry<>(currCell, clickedCell == currCell));
        currCell = randCell();
    }

    void fillHex(int hex) {
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);
        fill(r, g, b);
    }

    int randCell() {
        return new Random().nextInt(63);
    }

    int getCellColor(int col, int row) {
        int color = (col % 2 == (row % 2 == 0 ? 1 : 0) ? 0xb58963 : 0xefd9b5);
        if (redHighlightedCell == col + row * 8) color = col % 2 == (row % 2 == 0 ? 1 : 0) ? 0x8d4945 : 0x914e4a;
        if (yellowHighlightedCell == col + row * 8) color = col % 2 == (row % 2 == 0 ? 1 : 0) ? 0xd9c44c : 0xf7ec73;
        return color;
    }

    String addZeroIfNeeded(String input) {
        if (input.length() < 2) input = "0" + input;
        return input;
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
