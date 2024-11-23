
import javax.swing.JButton;

public class ButtonGame extends JButton {
    Integer iCod;

    public ButtonGame() {
        this.iCod = -1;
    }

    public void setCode(Integer iCod) {
        this.iCod = iCod;
    }

    public Integer getCode() {
        return this.iCod;
    }
}
