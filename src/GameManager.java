

import javax.swing.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class GameManager {
    private HashMap<ButtonGame, Integer> buttonMap;
    private ButtonGame buttonFirstClicked;
    private ButtonGame buttonSecondClicked;
    private Integer intQtdOpened;
    private Integer intCombined;
    private ArrayList<Integer> listShuffle;
    private Images images;
    private Timer timerHideButtons;
    private Timer gameTimer;
    private JLabel labelTitle;
    private JLabel labelTime;
    private int totalTime;

    public GameManager(JLabel labelTitle, JLabel labelTime) {
        buttonMap = new HashMap<>();
        images = new Images();
        intQtdOpened = 0;
        intCombined = 0;
        listShuffle = new ArrayList<>();
        this.labelTitle = labelTitle;
        this.labelTime = labelTime;
        totalTime = 120; // 2 minutes

        for (int i = 1; i <= 12; i++) {
            listShuffle.add(i);
            listShuffle.add(i);
        }
        Collections.shuffle(listShuffle);
    }

    public void addButton(ButtonGame button) {
        int index = buttonMap.size();
        buttonMap.put(button, listShuffle.get(index));
        button.setIcon(images.IconFactory(-1));
        button.addActionListener(e -> handleButtonClick(button));
    }

    private void handleButtonClick(ButtonGame button) {
        if (buttonMap.get(button) == 0 || button.equals(buttonFirstClicked) || button.equals(buttonSecondClicked)) {
            return;
        }

        // Show the icon of the clicked button
        button.setIcon(images.IconFactory(buttonMap.get(button)));

        if (buttonSecondClicked != null) {
            hideButtons();
        }

        if (buttonFirstClicked == null) {
            buttonFirstClicked = button;
            intQtdOpened++;
            labelTitle.setText("Number of Clicks: " + intQtdOpened);
        } else if (buttonSecondClicked == null) {
            buttonSecondClicked = button;
            intQtdOpened++;
            labelTitle.setText("Number of Clicks: " + intQtdOpened);

            if (Objects.equals(buttonMap.get(buttonFirstClicked), buttonMap.get(buttonSecondClicked))) {
                buttonFirstClicked.setIcon(images.IconFactory(0));
                buttonSecondClicked.setIcon(images.IconFactory(0));
                buttonMap.put(buttonFirstClicked, 0); // Update the state to matched
                buttonMap.put(buttonSecondClicked, 0); // Update the state to matched
                buttonFirstClicked = null;
                buttonSecondClicked = null;
                intCombined++;

                if (intCombined >= 12) {
                    solve(true);
                }
            } else {
                timerHideButtons = new Timer(1500, e -> hideButtons());
                timerHideButtons.setRepeats(false);
                timerHideButtons.start();
            }
        }
    }

    private void hideButtons() {
        if (buttonFirstClicked != null && buttonSecondClicked != null) {
            buttonFirstClicked.setIcon(images.IconFactory(-1));
            buttonSecondClicked.setIcon(images.IconFactory(-1));
        }
        buttonFirstClicked = null;
        buttonSecondClicked = null;
        if (timerHideButtons != null) {
            timerHideButtons.stop();
        }
    }

    public void startGameTimer() {
        gameTimer = new Timer(1000, e -> {
            totalTime--;
            labelTime.setText("Time Remaining: " + totalTime + " seconds");
            if (totalTime <= 0) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Time's up! Game Over.");
                newGame();
            }
        });
        gameTimer.start();
    }

    public void newGame() {
        Collections.shuffle(listShuffle);
        intQtdOpened = 0;
        intCombined = 0;
        buttonFirstClicked = null;
        buttonSecondClicked = null;
        if (timerHideButtons != null) {
            timerHideButtons.stop();
        }
        if (gameTimer != null) {
            gameTimer.stop();
        }
        totalTime = 120; // 2 minutes
        startGameTimer();

        int index = 0;
        for (ButtonGame button : buttonMap.keySet()) {
            buttonMap.put(button, listShuffle.get(index));
            button.setIcon(images.IconFactory(-1)); // Set initial icon to question mark
            index++;
        }
    }

    public void solve(Boolean bMostrarCliques) {
        if (intQtdOpened == -1) return;

        intQtdOpened = -1;
        intCombined = 12;
        buttonFirstClicked = null;
        buttonSecondClicked = null;
        if (timerHideButtons != null) {
            timerHideButtons.stop();
        }
        if (gameTimer != null) {
            gameTimer.stop();
        }

        for (ButtonGame button : buttonMap.keySet()) {
            button.setIcon(images.IconFactory(buttonMap.get(button)));
            buttonMap.put(button, 0);
        }
    }

    public Images getImages() {
        return images;
    }
}
