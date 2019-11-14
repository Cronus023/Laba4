package Paket4;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	// the size of window
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	// the object of dialog box to select files
	private JFileChooser fileChooser = null;
	// menu items
	private JCheckBoxMenuItem showAxisMenuItem;
	private JCheckBoxMenuItem showMarkersMenuItem;
	// component - graphics display
	private GraphicsDisplay display = new GraphicsDisplay();
	// flag, which show file loaded or no
	private boolean fileLoaded = false;
	
	
	public static void main(String[] args) {
		
	}
}
