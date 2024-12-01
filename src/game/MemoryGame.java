package game;

import javax.swing.*;
import java.awt.*;
import sounds.*;
public class MemoryGame extends JFrame {
    private JPanel panelTitle, panelGrid, panelControl;
    private JButton buttonNew, buttonSolve, buttonAbout;
    private JLabel labelTitle, labelTime;
    private MemoryGameManager memoryGameManager;
    private String difficultyLevel;

    public MemoryGame(String difficultyLevel, int round) {
        super("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        this.difficultyLevel = difficultyLevel; 
        initializeControlPanel();

        switch (difficultyLevel.toUpperCase()) {
            case "EASY":
                if (round < 1 || round > 5) {
                    throw new IllegalArgumentException("Unexpected value: " + round);
                }
                handleEasyRound(round);
                break;
            case "MEDIUM":
                if (round < 1 || round > 3) {
                    throw new IllegalArgumentException("Unexpected value: " + round);
                }
                handleMediumRound(round);
                break;
            case "HARD":
                if (round < 1 || round > 2) {
                    throw new IllegalArgumentException("Unexpected value: " + round);
                }
                handleHardRound(round);
                break;
            default:
                throw new IllegalArgumentException("Unexpected difficulty level: " + difficultyLevel);
        }

        pack();
        setVisible(true);
        memoryGameManager.startGameTimer();
    }

    private void handleEasyRound(int round) {
        switch (round) {
            case 1:
                initializeTitlePanel(12);
                initializeGridPanel(4, 3);
                break;
            case 2:
                initializeTitlePanel(16);
                initializeGridPanel(4, 4);
                break;
            case 3:
                initializeTitlePanel(20);
                initializeGridPanel(4, 5);
                break;
            case 4:
                initializeTitlePanel(24);
                initializeGridPanel(4, 6);
                break;
            case 5:
                initializeTitlePanel(30);
                initializeGridPanel(5, 6);
                break;
            default:
                throw new IllegalArgumentException("Unexpected round: " + round);
        }
    }

    private void handleMediumRound(int round) {
        switch (round) {
            case 1:
                initializeTitlePanel(9);
                initializeGridPanel(3, 3);
                break;
            case 2:
                initializeTitlePanel(12);
                initializeGridPanel(4, 3);
                break;
            case 3:
                initializeTitlePanel(15);
                initializeGridPanel(5, 3);
                break;
            default:
                throw new IllegalArgumentException("Unexpected round: " + round);
        }
    }

    private void handleHardRound(int round) {
        switch (round) {
            case 1:
                initializeTitlePanel(20);
                initializeGridPanel(5, 4);
                break;
            case 2:
                initializeTitlePanel(24);
                initializeGridPanel(6, 4);
                break;
            default:
                throw new IllegalArgumentException("Unexpected round: " + round);
        }
    }

    private void initializeTitlePanel(int countCard) {
        panelTitle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                GradientPaint gradient = new GradientPaint(0, 0, new Color(123, 104, 238), width, 0, new Color(30, 144, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panelTitle.setLayout(new BorderLayout());

        labelTitle = new JLabel("Number of Clicks: 0");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle, BorderLayout.WEST);

        labelTime = new JLabel("Time Remaining: 120 seconds");
        labelTime.setFont(new Font("Arial", Font.BOLD, 24));
        labelTime.setForeground(Color.WHITE);
        panelTitle.add(labelTime, BorderLayout.EAST);

        panelTitle.setBorder(BorderFactory.createBevelBorder(1));
        getContentPane().add(panelTitle, BorderLayout.NORTH);

        memoryGameManager = new MemoryGameManager(labelTitle, labelTime, countCard, difficultyLevel);
    }

    private void initializeGridPanel(int rows, int cols) {
        panelGrid = new JPanel(new GridLayout(rows, cols)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250), width, height, new Color(176, 224, 230));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panelGrid.setBorder(BorderFactory.createBevelBorder(1));

        for (int i = 0; i < rows * cols; i++) {
            ButtonGame button = new ButtonGame();
            button.setFont(new Font("Arial", Font.BOLD, 24));

            Color fixedColor = new Color(240, 240, 240);
            button.setBackground(fixedColor);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            button.addActionListener(e -> {
            	sounds.SoundManager.playSound("src/sounds/flip.wav") ; 
            });
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(200, 200, 200));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(fixedColor);
                }
            });

            button.setIcon(memoryGameManager.getImages().IconFactory(-1));
            panelGrid.add(button);
            memoryGameManager.addButton(button);
        }
        getContentPane().add(panelGrid, BorderLayout.CENTER);
    }

    private void initializeControlPanel() {
        panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 223, 0), width, height, new Color(255, 140, 0));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panelControl.setBorder(BorderFactory.createBevelBorder(1));

        buttonNew = createControlButton("New Game", Color.GREEN);
        buttonSolve = createControlButton("Solve", Color.ORANGE);
        buttonAbout = createControlButton("About", Color.BLUE);

        panelControl.add(buttonNew);
        panelControl.add(buttonSolve);
        panelControl.add(buttonAbout);

        getContentPane().add(panelControl, BorderLayout.SOUTH);

        buttonNew.addActionListener(e -> {
        	sounds.SoundManager.playSound("src/sounds/click.wav") ; 
        	memoryGameManager.startNewGame();
        	
        });
        buttonSolve.addActionListener(e -> {
        	sounds.SoundManager.playSound("src/sounds/click.wav") ; 
        	memoryGameManager.revealAllCards();
        });
        buttonAbout.addActionListener(e -> {
        	sounds.SoundManager.playSound("src/sounds/click.wav") ; 
        	JOptionPane.showMessageDialog(this, "Just For Fun");
        });
    }

    private JButton createControlButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createBevelBorder(1));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}