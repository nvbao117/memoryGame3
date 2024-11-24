import javax.swing.*;
import java.awt.*;

public class MemoryGame extends JFrame {
    private JPanel panelTitle, panelGrid, panelControl;
    private JButton buttonNew, buttonSolve, buttonAbout;
    private JLabel labelTitle, labelTime;
    private GameManager gameManager;


    public MemoryGame() {
        super("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setSize(800, 600); // Set default size
        setMinimumSize(new Dimension(800, 600)); // Set minimum size

        initializeTitlePanel();
        initializeControlPanel();
        initializeGridPanel(4, 6); // Default grid size

        pack();
        setVisible(true);
        gameManager.startGameTimer();
    }

    private void initializeTitlePanel() {
        panelTitle = new JPanel(new BorderLayout()); // Use BorderLayout for panelTitle

        labelTitle = new JLabel("Number of Clicks: 0");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitle.add(labelTitle, BorderLayout.WEST); // Align to the left

        labelTime = new JLabel("Time Remaining: 120 seconds");
        labelTime.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitle.add(labelTime, BorderLayout.EAST); // Align to the right

        panelTitle.setBorder(BorderFactory.createBevelBorder(1));
        getContentPane().add(panelTitle, BorderLayout.NORTH);

        gameManager = new GameManager(labelTitle, labelTime);
    }

    private void initializeControlPanel() {
        panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelControl.setBorder(BorderFactory.createBevelBorder(1));

        buttonNew = createControlButton("New Game");
        buttonSolve = createControlButton("Solve");
        buttonAbout = createControlButton("About");

        panelControl.add(buttonNew);
        panelControl.add(buttonSolve);
        panelControl.add(buttonAbout);

        getContentPane().add(panelControl, BorderLayout.SOUTH);

        buttonNew.addActionListener(e -> gameManager.newGame());
        buttonSolve.addActionListener(e -> gameManager.solve(false));
        buttonAbout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Just For Fun"));
    }

    private JButton createControlButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        return button;
    }

    private void initializeGridPanel(int rows, int cols) {
        panelGrid = new JPanel(new GridLayout(rows, cols));
        panelGrid.setBorder(BorderFactory.createBevelBorder(1));
        for (int i = 0; i < rows * cols; i++) {
            ButtonGame button = new ButtonGame();
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setIcon(gameManager.getImages().IconFactory(-1));
            panelGrid.add(button);
            gameManager.addButton(button);
        }
        getContentPane().add(panelGrid, BorderLayout.CENTER);
        panelGrid.revalidate();
        panelGrid.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}
