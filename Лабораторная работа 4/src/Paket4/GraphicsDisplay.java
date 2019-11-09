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
	
	// сheck boxes that specify the rules for displaying the graph
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
	
	//The method is called from the handler of the menu item "Open file with graph" of the main application window in case of successful data loading
	public void showGraphics(Double[][] graphicsData) {
	    // save massiv of points in class field
	    this.graphicsData = graphicsData;
	    //repaint our component (implicit call to paintComponent)
	    repaint();
	}
	
	public void setShowAxis(boolean showAxis) {
	    this.showAxis = showAxis;
	    repaint();
	}
	
	public void setShowMarkers(boolean showMarkers) {
		this.showMarkers = showMarkers;
		repaint();
	}

	//conversion of coordinates from Cartesian system to display Canvas system
	protected Point2D.Double xyToPoint(double x, double y) {
		// calculate the offset X from the leftmost point (minX)
		double deltaX = x - minX;
		// calculate the offset Y from the point of the top (maxY)
		double deltaY = maxY - y;
		return new Point2D.Double(deltaX*scale, deltaY*scale);
	}

	//a point that is separated from a given point by a certain number of pixels horizontally and vertically.
	protected Point2D.Double shiftPoint(Point2D.Double src,double deltaX, double deltaY) {
		//create a clon of our point
		Point2D.Double dest = new Point2D.Double();
		//new coordinates
		dest.setLocation(src.getX() + deltaX, src.getY() + deltaY);
		return dest;
	}
	
	protected void paintGraphics(Graphics2D canvas) {
		// selection of line for drowing graphics
		canvas.setStroke(graphicsStroke);
		// selection of color for line
		canvas.setColor(Color.RED);
		/* we'll draw a graph line as a path consisting of a set of
		segments (GeneralPath). The start of the path is set to the first point
		graph, after which the line connects to the following points*/
		GeneralPath graphics = new GeneralPath();
		for (int i=0; i<graphicsData.length; i++) {
		    // Преобразовать значения (x,y) в точку на экране point
		    Point2D.Double point = xyToPoint(graphicsData[i][0],
		    graphicsData[i][1]);
		    if (i>0) {
		        // not the first iteration – draw a line to point
		        graphics.lineTo(point.getX(), point.getY());
		    } 
		    else {
		        //the first iteration - set the start of way in point "point"
		        graphics.moveTo(point.getX(), point.getY());
		    }
		}
		// draw the graphics
		canvas.draw(graphics);
	}

}

