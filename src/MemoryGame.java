import javax.swing.*;
import java.awt.*;

public class MemoryGame extends JFrame {
    private JPanel panelTitle, panelGrid, panelControl;
    private JButton buttonNew, buttonSolve, buttonAbout;
    private JLabel labelTitle, labelTime;
    private MemoryGameManager memoryGameManager;
    private DifficultyLevel difficultyLevel;
    public MemoryGame(DifficultyLevel difficultyLevel) {
        super("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setSize(800, 600); // Set default size
        setMinimumSize(new Dimension(800, 600)); // Set minimum size

        initializeControlPanel();
        if (difficultyLevel == DifficultyLevel.EASY) {
            initializeTitlePanel(4);
            initializeGridPanel(2, 4); // Easy: 4x6 grid
        } else if (difficultyLevel == DifficultyLevel.MEDIUM) {
            initializeTitlePanel(12);

            initializeGridPanel(4, 6); // Medium: 5x6 grid
        } else if (difficultyLevel == DifficultyLevel.HARD) {
            initializeTitlePanel(14);

            initializeGridPanel(4, 7); // Hard: 6x8 grid
        }
        pack();
        setVisible(true);
        memoryGameManager.startGameTimer();
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

        memoryGameManager = new MemoryGameManager(labelTitle, labelTime,countCard);
    }

    private void initializeControlPanel() {
        panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                // Gradient từ vàng sang cam
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

        buttonNew.addActionListener(e -> memoryGameManager.newGame());
        buttonSolve.addActionListener(e -> memoryGameManager.solve(false));
        buttonAbout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Just For Fun"));
    }

    private JButton createControlButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createBevelBorder(1));

        // Hiệu ứng hover
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

}