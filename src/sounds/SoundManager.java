package sounds;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private static Clip clip;  // Biến clip toàn cục để quản lý âm thanh nền

    // Phát âm thanh từ file
    public static void playSound(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Phát âm thanh nền (Background Music) liên tục
    public static void playBackgroundMusic(String soundFileName) {
        new Thread(() -> {
            try {
                // Phát âm thanh nền liên tục
                File soundFile = new File(soundFileName);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                while (true) {
                    clip.setFramePosition(0); // Đặt lại vị trí của clip về đầu
                    clip.start();  // Bắt đầu phát âm thanh
                    Thread.sleep(clip.getMicrosecondLength() / 1000);  // Chờ âm thanh kết thúc trước khi phát lại
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Dừng âm thanh nền
    public static void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
