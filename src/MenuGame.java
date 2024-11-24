import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class MenuGame extends JFrame {
	private JPanel panelTitle, panelDifficulty, panelControl;
	private JRadioButton rbEasy, rbMedium, rbHard;
	private JButton buttonStart, buttonLoad, buttonBestScore, buttonExit, buttonResetAll;
	private ButtonGroup difficultyGroup ; 
	private MenuManager menuManager ; 
	public MenuGame(MenuManager menuManager) { 
		
		super("Memory Game");
		this.menuManager = menuManager;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(800, 600);
		setMinimumSize(new Dimension(800, 600));
		
		initiallizeTitlePanel();
		initiallizeDifficultyPanel();
		initializeControlPanel();
		
		pack();
		setVisible(true);
	}

	private void initiallizeTitlePanel() {
		panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelTitle.setBackground(new Color(50, 50, 100));

		JLabel labelTitle = new JLabel("Memory Game");
		labelTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		labelTitle.setForeground(new Color(255, 215, 0));
		Border border = BorderFactory.createLineBorder(new Color(255, 215, 0), 4);
		panelTitle.setBorder(border);
		panelTitle.add(labelTitle);
		getContentPane().add(panelTitle, BorderLayout.NORTH);

	}

	private void initiallizeDifficultyPanel() {
		panelDifficulty = new JPanel() ;
		panelDifficulty.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		panelDifficulty.setBackground(new Color(230, 230, 250));
		 panelDifficulty.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createLineBorder(new Color(0, 0, 128), 2),
	                "Select Difficulty Level",
	                0,
	                0,
	                new Font("Arial", Font.BOLD, 20),
	                new Color(0, 0, 128)
	        ));
		difficultyGroup = new ButtonGroup();
		
		rbEasy = createDifficultyRadioButton("Easy", new Color(144, 238, 144)); 
		rbMedium = createDifficultyRadioButton("Medium", new Color(255, 223, 186)); 
		rbHard = createDifficultyRadioButton("Hard", new Color(255, 99, 71));
		
		difficultyGroup.add(rbEasy); 
		difficultyGroup.add(rbMedium); 
		difficultyGroup.add(rbHard); 
		
		panelDifficulty.add(rbEasy);
		panelDifficulty.add(rbMedium);
		panelDifficulty.add(rbHard);

		getContentPane().add(panelDifficulty,BorderLayout.CENTER);
	}
	private JRadioButton createDifficultyRadioButton(String text , Color color ) {
		JRadioButton radioButton = new JRadioButton(text) ; 
		radioButton.setFont(new Font("Arial", Font.BOLD, 18));
		radioButton.setBackground(color); 
		radioButton.setPreferredSize(new Dimension(150,50));
		
		return radioButton;
		
	}
	
	private void initializeControlPanel() {
		panelControl = new JPanel(new GridLayout(1, 5, 10, 10) ) ;
		panelControl.setBackground(new Color(245, 245, 245)); 
        panelControl.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        buttonStart = createControlButton("Start", new Color(50, 205, 50));
        buttonLoad = createControlButton("Load", new Color(30, 144, 255));
        buttonBestScore = createControlButton("Best Score", new Color(255, 165, 0));
        buttonResetAll = createControlButton("Reset All", new Color(220, 20, 60));
        buttonExit = createControlButton("Exit", new Color(169, 169, 169));
		
		panelControl.add(buttonStart); 
		panelControl.add(buttonLoad); 
		panelControl.add(buttonBestScore); 
		panelControl.add(buttonResetAll); 
		panelControl.add(buttonExit);
		
		getContentPane().add(panelControl, BorderLayout.SOUTH);
	}
    private JButton createControlButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setPreferredSize(new Dimension(120, 40));

        button.addActionListener(e -> controlButtonAction(text));
        return button;
    }

	private void controlButtonAction(String action) {
		switch (action) {
		case "Start": {
			menuManager.handleStart();
			break ; 
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MenuGame::new);
	}
}
