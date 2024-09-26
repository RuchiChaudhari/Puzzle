import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Puzzle extends JFrame implements ActionListener {

    private JPanel puzzlePanel;
    private List<JButton> puzzleButtons;
    private int emptyButtonIndex;

    public Puzzle() {
        setTitle("Puzzle");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        puzzlePanel = new JPanel(new GridLayout(4, 4));
        puzzleButtons = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.addActionListener(this);
            puzzleButtons.add(button);
            puzzlePanel.add(button);
        }

        // Add an empty button
        JButton emptyButton = new JButton("");
        emptyButton.setEnabled(false);
        puzzleButtons.add(emptyButton);
        puzzlePanel.add(emptyButton);
        emptyButtonIndex = 15;

        // Shuffle the puzzle pieces
        shufflePuzzle();

        add(puzzlePanel);
    }

    private void shufflePuzzle() {
        Collections.shuffle(puzzleButtons);
        for (int i = 0; i < 16; i++) {
            JButton button = puzzleButtons.get(i);
            puzzlePanel.remove(button);
            puzzlePanel.add(button);
            if (button.getText().equals("")) {
                emptyButtonIndex = i;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int clickedIndex = puzzleButtons.indexOf(clickedButton);

        if (isAdjacent(clickedIndex, emptyButtonIndex)) {
            // Swap the empty button and the clicked button
            puzzlePanel.remove(clickedButton);
            puzzlePanel.remove(puzzleButtons.get(emptyButtonIndex));
            puzzlePanel.add(clickedButton, emptyButtonIndex);
            puzzlePanel.add(puzzleButtons.get(emptyButtonIndex), clickedIndex);

            // Update the empty button index
            emptyButtonIndex = clickedIndex;

            // Check if the puzzle is solved
            if (isPuzzleSolved()) {
                JOptionPane.showMessageDialog(this, "You solved the puzzle!");
            }
        }
    }

    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / 4;
        int col1 = index1 % 4;
        int row2 = index2 / 4;
        int col2 = index2 % 4;

        return Math.abs(row1 - row2) + Math.abs(col1 - col2) == 1;
    }

    private boolean isPuzzleSolved() {
        for (int i = 0; i < 15; i++) {
            JButton button = puzzleButtons.get(i);
            if (!button.getText().equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Puzzle puzzleGame = new Puzzle();
            puzzleGame.setVisible(true);
        });
    }
}
