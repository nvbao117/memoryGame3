import javax.swing.SwingUtilities;  
import menuGame.MenuGame;

public class Driver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuGame()); // Đảm bảo giao diện chạy mượt mà
    }
}
