package game;

import javax.swing.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import sounds.*;

public class MemoryGameManager {

    private HashMap<ButtonGame, Integer> buttonStateMap; // Store button states
    private ArrayList<ButtonGame> clickedButtons;   
    private ArrayList<Integer> shuffledCardList; // List of shuffled card IDs

    private int totalClicks; // Total number of clicks
    private int remainingTime; // Remaining time for the game

    private Images images;
    private Timer gameTimer ,hideButtonsTimer; // Timer for game time
    private JLabel clicksLabel,timeLabel; // Label to display number of clicks
    
    private int totalGroups; // Total groups of cards to match
    private int matchedGroups; // Number of matched pairs
    private int timeDelay;
    private String currentLevel; // Current game level
    private int cardsToMatch; // Number of cards to match in each group
    private int countCards;
    
    
    public static final String EASY_LEVEL = "EASY"; // 2 cards to match
    public static final String MEDIUM_LEVEL = "MEDIUM"; // 3 cards to match
    public static final String HARD_LEVEL = "HARD"; // 4 cards to match
    private boolean isCheckingMatch; // Trạng thái kiểm tra khớp

    
    
    /**
     * Constructor for MemoryGameManager.
     *
     * @param clicksLabel Label to display the number of clicks.
     * @param timeLabel   Label to display remaining time.
     * @param totalGroups Total number of groups to find.
     * @param level       Game difficulty level (EASY, MEDIUM, HARD).
     */
    public MemoryGameManager(JLabel clicksLabel, JLabel timeLabel, int countCards, String level) {
        this.buttonStateMap = new HashMap<>();
        this.clickedButtons = new ArrayList<>();
        this.shuffledCardList = new ArrayList<>();
        this.images = new Images();
        this.clicksLabel = clicksLabel;
        this.timeLabel = timeLabel;
        this.countCards = countCards;
        this.totalClicks = 0;
        this.matchedGroups = 0;
        this.isCheckingMatch = false;

        setLevel(level);
        this.totalGroups = calculateTotalGroups(countCards, cardsToMatch);
        initializeCards();
    }
    private int calculateTotalGroups(int countCards, int cardsToMatch) {
        if (countCards % cardsToMatch != 0) {
            throw new IllegalArgumentException("Số thẻ không chia hết cho số thẻ cần ghép (" + cardsToMatch + ")");
        }
        return countCards / cardsToMatch;
    }

    private void setLevel(String level) {
        if (level == null)
            throw new IllegalArgumentException("Level cannot be null");
        this.currentLevel = level.toUpperCase();

        switch (currentLevel) {
            case EASY_LEVEL -> {
            	timeDelay = 1000 ; 
                cardsToMatch = 2;
                remainingTime = 120; // 2 minutes for EASY
            }
            case MEDIUM_LEVEL -> {  	
            	timeDelay = 1500; 
                cardsToMatch = 3;
                remainingTime = 180; // 3 minutes for MEDIUM
            }
            case HARD_LEVEL -> {  	
            	timeDelay = 1500;
                cardsToMatch = 4;
                remainingTime = 240; // 4 minutes for HARD
            }
            default -> throw new IllegalArgumentException("Invalid level: " + level);
        }
    }

    private void initializeCards() {
    	shuffledCardList.clear();
        for (int i = 1; i <= totalGroups; i++) {
            for (int j = 0; j < cardsToMatch; j++) {
                shuffledCardList.add(i);
            }
        }
        Collections.shuffle(shuffledCardList);
        if (shuffledCardList.size() != countCards) {
            throw new IllegalStateException("Số thẻ sinh ra không khớp với số lượng thẻ yêu cầu!");
        }
    }

    /**
     * Add a button to the game.
     *
     * @param button Button to add.
     */
    public void addButton(ButtonGame button) {
        int index = buttonStateMap.size();
        buttonStateMap.put(button, shuffledCardList.get(index));
        button.setIcon(images.IconFactory(-1)); // Show default icon initially
        button.addActionListener(e -> handleButtonClick(button));
    }

    /**
     * Handle button click event.
     *
     * @param button Button clicked.
     */
    private void handleButtonClick(ButtonGame button) {
        // Skip if the button is already clicked or the timer is active
        if (!button.isEnabled() || clickedButtons.contains(button) || isCheckingMatch) {
            return; // Bỏ qua nếu nút đã vô hiệu hóa hoặc đang kiểm tra
        }

        // Show the button's icon
        button.setIcon(images.IconFactory(buttonStateMap.get(button)));
        clickedButtons.add(button);

        // Update the click count
        updateClickCount();

        // If two buttons have been clicked
        if (clickedButtons.size() == cardsToMatch) {
            checkMatch();
        }
    }

    private void updateClickCount() {
        totalClicks++;
        clicksLabel.setText(String.format("🖱️ Số lượt click: %d", totalClicks));
    }

    private void checkMatch() {
    	 isCheckingMatch = true;
        // Check if all clicked buttons have the same card ID
        int firstCardId = buttonStateMap.get(clickedButtons.get(0));
        boolean isMatch = clickedButtons.stream().allMatch(button -> Objects.equals(buttonStateMap.get(button), firstCardId));


        if (isMatch) {
        	sounds.SoundManager.playSound("src/sounds/match.wav");
        	clickedButtons.forEach(button -> {
                button.setIcon(images.IconFactory(0)); // Show "matched" state
                button.setEnabled(false); // Vô hiệu hóa nút
                buttonStateMap.put(button, 0); // Mark button as matched
            });
            matchedGroups++;
            clickedButtons.clear();

            // Check for game win
            if (matchedGroups >= totalGroups) {
                endGame(true);
            }
        } else {
        	hideButtonsTimer = new Timer(timeDelay, e -> {
        		clickedButtons.forEach(button -> button.setIcon(images.IconFactory(-1))); // Hide buttons
                clickedButtons.clear(); // Clear clicked buttons
                isCheckingMatch = false;

        	});
            hideButtonsTimer.setRepeats(false);
            hideButtonsTimer.start();
        }
        isCheckingMatch = false;

    }
    public void endGame(Boolean isWin) {
        totalClicks = -1;
        matchedGroups = totalGroups;
        clickedButtons.clear();
        stopTimers();
        revealAllCards();
        if (isWin) {
        	sounds.SoundManager.stopBackgroundMusic();
        	sounds.SoundManager.playSound("src/sounds/win.wav");
		}else {
			sounds.SoundManager.stopBackgroundMusic();
        	sounds.SoundManager.playSound("src/sounds/lose.wav");
		}
        JOptionPane.showMessageDialog(null, isWin ? "Chúc mừng! Bạn đã thắng!" : "Game Over!");
    }

    public void startGameTimer() {
        gameTimer = new Timer(1000, e -> updateRemainingTime());
        gameTimer.start();
    }

    private void updateRemainingTime() {
        remainingTime--;
        timeLabel.setText(String.format("⏳ Thời gian còn lại: %d giây", remainingTime));
        if (remainingTime <= 0) {
            gameTimer.stop();
        	sounds.SoundManager.playSound("src/sounds/lose.wav");
            JOptionPane.showMessageDialog(null, "Hết giờ! Game Over.");
            endGame(false);
        }
    }

  

    public void startNewGame() {
        Collections.shuffle(shuffledCardList);
        resetGame();
        startGameTimer();
        resetButtons();
    }

    private void resetGame() {
        totalClicks = 0;
        matchedGroups = 0;
        clickedButtons.clear();
        stopTimers();
        setLevel(currentLevel); // Reset the level-specific properties (cardsToMatch, remainingTime)
    }


    private void stopTimers() {
        if (hideButtonsTimer != null)
            hideButtonsTimer.stop();
        if (gameTimer != null)
            gameTimer.stop();
    }

    private void resetButtons() {
        int index = 0;
        for (ButtonGame button : buttonStateMap.keySet()) {
            if (index < shuffledCardList.size()) {
                int cardId = shuffledCardList.get(index++);
                buttonStateMap.put(button, cardId);
                button.setIcon(images.IconFactory(-1)); // Ẩn lại nút
            }
        }
    }


   

    public void revealAllCards() {
        buttonStateMap.forEach((button, value) -> {
            button.setIcon(images.IconFactory(value));
            buttonStateMap.put(button, 0);
        });
    }

    public Images getImages() {
        return images;
    }
}
