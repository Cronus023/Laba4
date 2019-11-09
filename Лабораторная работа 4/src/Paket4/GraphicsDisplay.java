package Paket4;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class GraphicsDisplay extends JPanel {
	// list of coordinates of points for plotting
	private Double[][] graphicsData;
	
	// ñheck boxes that specify the rules for displaying the graph
	private boolean showAxis = true;
	private boolean showMarkers = true;
	
	// boundaries of the range of space to be displayed
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	// the display scale
	private double scale;
	
	// different line drawing styles
	private BasicStroke graphicsStroke;
	private BasicStroke axisStroke;
	private BasicStroke markerStroke;
	
	// different fonts display labels
	private Font axisFont;

	public GraphicsDisplay() {
		// the color of the background display area is white
		setBackground(Color.WHITE);
		
		// objects, which used in drawing:
		
		//1) pen for drowing graphics
		graphicsStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f);
		
		//2) pen for drowing axes of coordinates
		axisStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
		
		//3) pen for drowing marker outlines
		markerStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
		
		//4) the font for the axes of coordinates
		axisFont = new Font("Serif", Font.BOLD, 36);
	}

}

