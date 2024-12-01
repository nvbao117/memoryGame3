package gameLevel;

import javax.swing.*;

import game.MemoryGame;

import java.awt.*;
import menuGame.*;

public class HardGame extends JFrame {
	private ButtonGroup roundButtonGroup;
	private String difficultyLevel;
	private int selectedRound = 1;

	// Constructor
	public HardGame(String difficultyLevel) {
		setTitle("Memory Game - Difficulty: " + difficultyLevel);
		this.difficultyLevel = difficultyLevel;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLayout(new BorderLayout(10, 10));
		getContentPane().setBackground(new Color(240, 248, 255)); // Nền xanh nhạt

		// Thiết lập giao diện
		initializeTitlePanel(difficultyLevel);
		initializeRoundSelectionPanel();
		initializeControlPanel();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initializeTitlePanel(String difficultyLevel) {
		JLabel titleLabel = new JLabel("Game Difficulty Level: " + difficultyLevel, JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(255, 223, 186)); // Màu vàng nhạt
		titleLabel.setForeground(Color.DARK_GRAY);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding
		add(titleLabel, BorderLayout.NORTH);
	}

	private void initializeRoundSelectionPanel() {
		JPanel roundPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 dòng, khoảng cách 10px
		roundPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding trong panel
		roundPanel.setBackground(new Color(240, 248, 255)); // Nền đồng màu

		roundButtonGroup = new ButtonGroup();
		for (int i = 1; i <= 2; i++) {
			JRadioButton roundButton = createRoundRadioButton("Round " + i, i == 1);
			roundButtonGroup.add(roundButton);
			roundPanel.add(roundButton);
		}

		add(roundPanel, BorderLayout.CENTER);
	}

	private JRadioButton createRoundRadioButton(String text, boolean selected) {
		JRadioButton roundButton = new JRadioButton(text);
		roundButton.setFont(new Font("Arial", Font.BOLD, 16));
		roundButton.setBackground(Color.WHITE);
		roundButton.setFocusPainted(false);

		String roundValue = text.split(" ")[1]; // "Round X", phần tử thứ 2 là số
		roundButton.setActionCommand(roundValue); // Gán số vào ActionCommand

		roundButton.addActionListener(e -> {
			sounds.SoundManager.playSound("src/sounds/flip.wav");
			try {
				// Lấy giá trị từ ActionCommand và chuyển thành int
				selectedRound = Integer.parseInt(roundButton.getActionCommand());
			} catch (NumberFormatException ex) {
				// Xử lý khi không thể chuyển đổi
				System.out.println("Giá trị không hợp lệ: " + roundButton.getActionCommand());
			}
		});
		roundButton.setSelected(selected); // Mặc định chọn nếu là true
		return roundButton;
	}

	private void initializeControlPanel() {
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		controlPanel.setBackground(new Color(240, 248, 255));

		JButton playButton = createControlButton("Play", new Color(144, 238, 144));
		JButton backButton = createControlButton("Go Back", new Color(173, 216, 230));
		JButton exitButton = createControlButton("Exit", new Color(255, 99, 71));

		controlPanel.add(playButton);
		controlPanel.add(backButton);
		controlPanel.add(exitButton);

		add(controlPanel, BorderLayout.SOUTH);
	}

	private JButton createControlButton(String text, Color color) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setBackground(color);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(120, 40));
		button.addActionListener(e -> controlButtonAction(text));

		// Hiệu ứng hover
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(color.darker());
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(color);
			}
		});

		return button;
	}

	private void controlButtonAction(String action) {
		switch (action) {
		case "Play": {
			int round = selectedRound;
			String level = difficultyLevel;
			this.dispose();
			SwingUtilities.invokeLater(() -> new MemoryGame(level, round));
			break;
		}
		case "Go Back": {
			this.dispose();
	        SwingUtilities.invokeLater(() -> new MenuGame());
			break;
		}
		case "Exit": {
			System.exit(0);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

}
