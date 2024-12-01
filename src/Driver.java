import javax.swing.SwingUtilities;  
import menuGame.MenuGame;
import sounds.*;
public class Driver {
    public static void main(String[] args) {
    	
        SoundManager.playBackgroundMusic("src/sounds/background_game.wav");  // Đường dẫn đến tệp âm thanh của bạn

        SwingUtilities.invokeLater(() -> new MenuGame()); // Đảm bảo giao diện chạy mượt mà
    }
}
