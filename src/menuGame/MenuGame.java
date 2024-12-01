package menuGame;
import javax.swing.*;
import javax.swing.border.Border;

import gameLevel.MediumGame;

import java.awt.*;
import gameLevel.*;
public class MenuGame extends JFrame {
	private JPanel panelTitle, panelDifficulty, panelControl;
	private JRadioButton rbEasy, rbMedium, rbHard;
	private JButton buttonStart, buttonBestScore, buttonExit, buttonResetAll;
	private ButtonGroup difficultyGroup ; 
	public MenuGame() { 
		
		super("Memory Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(800, 600);
		setMinimumSize(new Dimension(800, 600));
		
		initiallizeTitlePanel();
		initializeDifficultyPanel();
		initializeControlPanel();
		
		setLocationRelativeTo(null);
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

	private void initializeDifficultyPanel() {
	    panelDifficulty = new JPanel(new GridLayout(1, 3, 10, 10)); // Sử dụng GridLayout
	    panelDifficulty.setBackground(new Color(230, 230, 250));

	    // Tạo border đẹp hơn
	    Border outerBorder = BorderFactory.createLineBorder(new Color(0, 0, 128), 4); // Viền ngoài màu xanh đậm
	    Border innerBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20); // Padding bên trong
	    Border titledBorder = BorderFactory.createTitledBorder(
	            outerBorder, 
	            " Select Difficulty Level ", 
	            0,
	            0, 
	            new Font("Arial", Font.BOLD, 20), 
	            new Color(0, 0, 128) 
	    );
	    panelDifficulty.setBorder(BorderFactory.createCompoundBorder(titledBorder, innerBorder));

	    difficultyGroup = new ButtonGroup();

	    // Tạo các nút radio với màu sắc đẹp mắt
	    rbEasy = createDifficultyRadioButton("Easy", new Color(144, 238, 144));
	    rbMedium = createDifficultyRadioButton("Medium", new Color(255, 223, 186));
	    rbHard = createDifficultyRadioButton("Hard", new Color(255, 99, 71));

	    difficultyGroup.add(rbEasy);
	    difficultyGroup.add(rbMedium);
	    difficultyGroup.add(rbHard);
	    rbEasy.setSelected(true);
	    // Thêm các radio button vào panel
	    panelDifficulty.add(rbEasy);
	    panelDifficulty.add(rbMedium);
	    panelDifficulty.add(rbHard);

	    getContentPane().add(panelDifficulty, BorderLayout.CENTER);
	}
	private JRadioButton createDifficultyRadioButton(String text, Color color) {
	    JRadioButton radioButton = new JRadioButton(text);
	    radioButton.setFont(new Font("Arial", Font.BOLD, 20)); // Font lớn và đậm
	    radioButton.setBackground(color); // Màu nền
	    radioButton.setHorizontalAlignment(SwingConstants.CENTER); // Canh giữa nội dung
	    radioButton.setFocusPainted(false); // Loại bỏ viền focus mặc định

	    // Thêm viền đẹp mắt
	    Border outerBorder = BorderFactory.createLineBorder(Color.BLACK, 2); // Viền ngoài màu đen
	    Border innerBorder = BorderFactory.createEmptyBorder(10, 15, 10, 15); // Padding bên trong
	    radioButton.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder)); // Kết hợp viền

	    return radioButton;
	}
	
	
	private void initializeControlPanel() {
		panelControl = new JPanel(new GridLayout(1, 5, 10, 10) ) ;
		panelControl.setBackground(new Color(245, 245, 245)); 
        panelControl.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonStart = createControlButton("Start", new Color(50, 205, 50));
        buttonBestScore = createControlButton("Best Score", new Color(255, 165, 0));
        buttonResetAll = createControlButton("Reset All", new Color(220, 20, 60));
        buttonExit = createControlButton("Exit", new Color(169, 169, 169));
		panelControl.add(buttonStart); 
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
    private String getSelectedDifficulty() {
        if (rbEasy.isSelected()) {
            return "EASY";
        } else if (rbMedium.isSelected()) {
            return "MEDIUM";
        } else if (rbHard.isSelected()) {
            return "HARD";
        } else {
            return null;  
        }
    }

	private void controlButtonAction(String action) {
		String level = getSelectedDifficulty() ; 
		switch (action) {
		case "Start": {
			if(level == "EASY") {
				new EasyGame("EASY");
			}else if(level =="MEDIUM") {
				new MediumGame("MEDIUM");
			}else if(level == "HARD") {
				new HardGame("HARD");
			}else {
                JOptionPane.showMessageDialog(this, "Please select a difficulty level.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
			}
			
			break ; 
		}
		case "Best Score": {
			break ; 
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
}