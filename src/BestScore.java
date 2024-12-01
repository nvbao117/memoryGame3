import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.*;

public class BestScore extends JFrame {

    private JTable tableEasy, tableMedium, tableHard;
    private JPanel panelEasy, panelMedium, panelHard;

    public BestScore() {
        setTitle("Best Score");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header của cửa sổ
        JLabel labelTitle = new JLabel("Best Score", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setOpaque(true);
        labelTitle.setBackground(new Color(255, 69, 0));  // Màu cam
        labelTitle.setPreferredSize(new Dimension(600, 50));
        add(labelTitle, BorderLayout.NORTH);

        // Panel chứa các bảng
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        panelContainer.setBackground(new Color(240, 240, 240));  // Màu nền sáng
        panelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEasy = createScorePanel("Easy Best Score", new String[]{"Round No#", "Best Score", "Best Time", "Total Time"});
        panelMedium = createScorePanel("Medium Best Score", new String[]{"Round No#", "Best Score", "Best Time", "Total Time"});
        panelHard = createScorePanel("Hard Best Score", new String[]{"Round No#", "Best Score", "Best Time", "Total Time"});

        panelContainer.add(panelEasy);
        panelContainer.add(panelMedium);
        panelContainer.add(panelHard);

        add(panelContainer, BorderLayout.CENTER);

        // Set kích thước phù hợp
        setSize(650, 660);  // Chỉnh kích thước cửa sổ sao cho vừa với các bảng
        setLocationRelativeTo(null); 
        setMinimumSize(new Dimension(650, 400));  // Đảm bảo cửa sổ không quá nhỏ
        setVisible(true);
    }

    // Tạo panel cho mỗi bảng với thông tin khác nhau
    private JPanel createScorePanel(String title, String[] columnNames) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBackground(new Color(255, 215, 0));  // Màu vàng
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(true);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Tạo bảng
        JTable table = new JTable(getTableData(), columnNames);
        styleTable(table);  // Áp dụng kiểu dáng cho bảng

        panel.add(table, BorderLayout.CENTER);

        return panel;
    }

    // Cập nhật kiểu dáng cho bảng
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 16));  // Cập nhật font cho bảng
        table.setRowHeight(30);  // Tăng chiều cao hàng cho dễ nhìn
        table.setBackground(new Color(255, 250, 205));  // Màu nền sáng
        table.setGridColor(Color.BLACK);  // Màu lưới của bảng

        // Căn giữa các ô trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Thay đổi chiều rộng của các cột
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);  // Cột Round No# rộng hơn
        columnModel.getColumn(1).setPreferredWidth(150);  // Cột Best Score rộng hơn
        columnModel.getColumn(2).setPreferredWidth(150);  // Cột Best Time rộng hơn
        columnModel.getColumn(3).setPreferredWidth(150);  // Cột Total Time rộng hơn
    }

    // Dữ liệu mẫu cho bảng (có thể thay đổi tùy theo yêu cầu)
    private Object[][] getTableData() {
        return new Object[][] {
            {"1", "67", "0 Min 59 Sec", "3 Min"},
            {"2", "Not Available", "Not Available", "Not Available"},
            {"3", "Not Available", "Not Available", "Not Available"},
            {"4", "Not Available", "Not Available", "Not Available"},
            {"5", "Not Available", "Not Available", "Not Available"}
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BestScore());
    }
}