import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoundGameSelectionUI extends JFrame {
    private ButtonGroup roundButtonGroup; // Nhóm các RadioButton để chọn round
    private String selectedRound = "Round - 1"; // Mặc định là Round 1

    public RoundGameSelectionUI(String difficultyLevel) {
        setTitle("Memory Game - Difficulty: " + difficultyLevel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255)); // Nền màu xanh nhạt

        // Tiêu đề trên cùng
        JLabel titleLabel = new JLabel("Game Difficulty Level: " + difficultyLevel, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 223, 186)); // Màu vàng nhạt
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding
        add(titleLabel, BorderLayout.NORTH);

        // Panel chứa danh sách các round
        JPanel roundPanel = new JPanel();
        roundPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 dòng, khoảng cách 10px
        roundPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding trong panel
        roundPanel.setBackground(new Color(240, 248, 255)); // Nền đồng màu

        // Thêm các RadioButton cho các Round
        roundButtonGroup = new ButtonGroup();
        for (int i = 1; i <= 5; i++) {
            JRadioButton roundButton = new JRadioButton("Round - " + i);
            roundButton.setFont(new Font("Arial", Font.BOLD, 16));
            roundButton.setBackground(Color.WHITE);
            roundButton.setFocusPainted(false);
            roundButton.addActionListener(e -> selectedRound = roundButton.getText());
            if (i == 1) roundButton.setSelected(true); // Mặc định chọn Round 1
            roundButtonGroup.add(roundButton);
            roundPanel.add(roundButton);
        }

        add(roundPanel, BorderLayout.CENTER);

        // Tạo panel chứa các nút điều khiển
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(new Color(240, 248, 255)); // Đồng màu nền

        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        playButton.setBackground(new Color(144, 238, 144)); // Màu xanh lá
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.setPreferredSize(new Dimension(100, 40));

        JButton backButton = new JButton("Go Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(173, 216, 230)); // Màu xanh nước
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(120, 40));

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setBackground(new Color(255, 99, 71)); // Màu đỏ
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(100, 40));

        controlPanel.add(playButton);
        controlPanel.add(backButton);
        controlPanel.add(exitButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Sự kiện nút Play
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Starting " + selectedRound + " at difficulty: " + difficultyLevel);
                // Thực hiện logic để khởi động game với độ khó và round đã chọn
            }
        });

        // Sự kiện nút Exit
        exitButton.addActionListener(e -> System.exit(0));

        // Hiển thị giao diện
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoundGameSelectionUI("Easy"));
    }
}
