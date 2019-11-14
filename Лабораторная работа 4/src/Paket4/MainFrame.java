package Paket4;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

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
	
	public MainFrame(){
		super("Построение графиков функций на основе подготовленных файлов");
		setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();
		setLocation((kit.getScreenSize().width - WIDTH)/2,(kit.getScreenSize().height - HEIGHT)/2);
		setExtendedState(MAXIMIZED_BOTH);
		
		// add menu to our frame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// add to our menu item "Файл"
		JMenu fileMenu = new JMenu("Файл");
		menuBar.add(fileMenu);
		
		// create action "Открыть файл"
		Action openGraphicsAction = new AbstractAction("Открыть файл") {
		public void actionPerformed(ActionEvent event) {
		if (fileChooser==null) {
		    fileChooser = new JFileChooser();
		    fileChooser.setCurrentDirectory(new File("."));
		}
		if (fileChooser.showOpenDialog(MainFrame.this) ==
		    JFileChooser.APPROVE_OPTION)
		    openGraphics(fileChooser.getSelectedFile());
		}
		};
		fileMenu.add(openGraphicsAction);
		
	}
	
	protected void openGraphics(File selectedFile) {
	try {
	    // Step 1 - open the data read stream associated with the file
	    DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
	    
	    /* Step 2-Knowing the amount of data in the input stream, you can calculate,
	    how much memory should be reserved in the array:
	    Total bytes in stream - in.available () bytes;
	    The size of the number is Double-Double.SIZE bit, or Double.SIZE/8 bytes;
	    Since numbers are written in pairs, the number of pairs is less than 2 times */
	    Double[][] graphicsData = new Double[in.available()/(Double.SIZE/8)/2][];
	    
	    // Step 3 – read the data
	    int i = 0;
	    while (in.available()>0) {
	        // 1) read X
	        Double x = in.readDouble();
	        // 2) read Y
	        Double y = in.readDouble();
	        // add the pair of coordinates in massiv
	        graphicsData[i++] = new Double[] {x, y};
	    }
	    
	    // Step 4 - сheck the massiv
	    if (graphicsData!=null && graphicsData.length>0) {
	        fileLoaded = true;
	        display.showGraphics(graphicsData);
	    }
	    
	    // Step 5 - close the stream
	    in.close();
	    
	} catch (FileNotFoundException ex) {
	    JOptionPane.showMessageDialog(MainFrame.this,"Указанный файл не найден", "Ошибка загрузки данных",JOptionPane.WARNING_MESSAGE);
	    return;
	} catch (IOException ex) {
	    JOptionPane.showMessageDialog(MainFrame.this,"Ошибка чтения координат точек из файла","Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);return;
	}
	}
	
	public static void main(String[] args) {
		
	}
}
