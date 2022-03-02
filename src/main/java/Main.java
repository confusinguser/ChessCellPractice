import processing.core.PApplet;

import java.util.*;

public class Main extends PApplet {

    int currCell = randCell();

    int chessH = 400;

    List<Map.Entry<Integer, Boolean>> history = new ArrayList<>();
    private int yellowHighlightedCell = -1;
    private int redHighlightedCell = -1;
    int highlightTime = -1;

    public void settings() {
        size(400, 440);
    }

    public void setup() {
        noStroke();
    }

    public void draw() {
        translate(0, 20);
        background(28);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fillHex(getCellColor(i, j));
                rect(i * width / 8f, j * chessH / 8f, width / 8f, chessH / 8f);
            }
        }
        for (int i = 0; i < 8; i++) {
            fillHex(i % 2 != 0 ? 0xb58963 : 0xefd9b5);
            text(8 - i, 1, i * chessH / 8f + 11);
            fillHex(i % 2 == 0 ? 0xb58963 : 0xefd9b5);
            text("abcdefgh".charAt(i), (i + 1) * width / 8f - 9, chessH - 3);
        }
        fill(193);
        for (int i = 0; i < history.size(); i++) {
            Map.Entry<Integer, Boolean> cell = history.get(history.size() - i - 1);
            text("abcdefgh".charAt(cell.getKey() % 8) + String.valueOf(8-(cell.getKey()) / 8), width - 16 - i * 16, chessH + 10);
        }
        translate(0, -20);
        text("abcdefgh".charAt(currCell % 8) + String.valueOf(8- currCell / 8), width/2f-5, 15);

        if (highlightTime == 0) redHighlightedCell = yellowHighlightedCell = -1;
        else if (highlightTime > 0) highlightTime--;
    }

    public void mousePressed() {
        int col = (mouseX) * 8 / width;
        int row = (mouseY-20) * 8 / chessH;
        int clickedCell = col + row * 8;
        yellowHighlightedCell = clickedCell;
        redHighlightedCell = currCell;
        highlightTime = 90;
        history.add(new AbstractMap.SimpleEntry<>(currCell, false));
        currCell = randCell();
        if (history.size() > 15) {
            history.remove(0);
        }
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
        int color = (col % 2 == (row % 2 == 0 ? 0 : 1) ? 0xb58963 : 0xefd9b5);
        if (redHighlightedCell == col + row * 8) color = col % 2 == (row % 2 == 0 ? 0 : 1) ? 0x8d4945 : 0x914e4a;
        if (yellowHighlightedCell == col + row * 8) color = col % 2 == (row % 2 == 0 ? 0 : 1) ? 0xd9c44c : 0xf7ec73;
        return color;
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
