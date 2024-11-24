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
		panelTitle.setBackground(Color.YELLOW);

		JLabel labelTitle = new JLabel("Memory Game");
		labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
		labelTitle.setForeground(Color.BLUE);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 4);
		panelTitle.setBorder(border);
		panelTitle.add(labelTitle);
		getContentPane().add(panelTitle, BorderLayout.NORTH);

	}

	private void initiallizeDifficultyPanel() {
		panelDifficulty = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20)) ;
		panelDifficulty.setBackground(Color.pink); 
		panelDifficulty.setBorder(BorderFactory.createTitledBorder("Select Difficulty Level"));
		difficultyGroup = new ButtonGroup();
		
		rbEasy = createDifficultyRadioButton("Easy", Color.BLUE); 
		rbMedium = createDifficultyRadioButton("Medium", Color.GREEN); 
		rbHard = createDifficultyRadioButton("Hard", Color.ORANGE);
		
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
		panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20) ) ;
		panelControl.setBackground(Color.orange); 
		
		buttonStart = createControlButton("Start"); 
		buttonLoad = createControlButton("Load"); 
		buttonBestScore = createControlButton("Best Score"); 
		buttonResetAll = createControlButton("Reset All"); 
		buttonExit = createControlButton("Exit");
		
		
		panelControl.add(buttonStart); 
		panelControl.add(buttonLoad); 
		panelControl.add(buttonBestScore); 
		panelControl.add(buttonResetAll); 
		panelControl.add(buttonExit);
		
		getContentPane().add(panelControl, BorderLayout.SOUTH);
	}
	private JButton createControlButton(String text) {
		JButton button = new JButton(text); 
		button.setFont(new Font("Arial" , Font.BOLD , 18)); 
		button.addActionListener(e -> controlButtonAction(text) );
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
