package game;

import javax.swing.JButton;
import java.awt.Color;

public class ButtonGame extends JButton {
    private static final int DEFAULT_CODE = -1;
    private Integer iCod;
    private boolean flipped;

    public ButtonGame() {
        this.iCod = DEFAULT_CODE;
        this.flipped = false;
    }

    public void setCode(Integer iCod) {
        this.iCod = iCod;
    }

    public Integer getCode() {
        return this.iCod;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void flip() {
        this.flipped = !this.flipped;
        updateAppearance();
    }

    public void reset() {
        this.flipped = false;
        updateAppearance();
    }

    public void updateAppearance() {
        if (flipped) {
            this.setBackground(new Color(173, 216, 230)); // Light blue
        } else {
            this.setBackground(Color.LIGHT_GRAY); // Default background
            this.setIcon(null); // Hide image
        }
    }
}
