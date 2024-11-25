import javax.swing.*;
import java.awt.*;
public class MenuManager extends JFrame{
	public void showMenu() {
		MenuGame menu = new MenuGame(this); 
	}
	public void handleStart(DifficultyLevel selectedDifficulty) {
	        if (selectedDifficulty != null) {
	        	String level = selectedDifficulty.name();
	            new RoundGameSelectionUI(level);
	            dispose();  
	        } else {
	            JOptionPane.showMessageDialog(this, "Please select a difficulty level.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	}
	public void handleBestScore() {
		new BestScore() ; 
	}
}
