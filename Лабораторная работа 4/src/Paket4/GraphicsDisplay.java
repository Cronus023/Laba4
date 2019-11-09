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
		    // change the value (x,y) to point "point"
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

	protected void paintAxis(Graphics2D canvas) {
		// Step 1 – set the settings of the draw
		
		// set the tracing for the axes
		canvas.setStroke(axisStroke);
		// set the color of the axes
		canvas.setColor(Color.BLACK);
		canvas.setPaint(Color.BLACK);
		// set the typeface of the signature for an axes
		canvas.setFont(axisFont);
		
		/* create object context of the text display  
		to get characteristics of the device(screen)*/
		FontRenderContext context = canvas.getFontRenderContext();
		
		// Step 2 - determine, whether the Y-axis should be visible on the graphics or no
		
		if (minX<=0.0 && maxX>=0.0) {
			
		    /* it is visible, if left border of the displayed area (minX) <=0.0 
		    and righ border (maxX) >= 0.0*/
			
		    // Step 2a - Y-axis is a line between point (0, maxY) and (0, minY)
		    canvas.draw(new Line2D.Double(xyToPoint(0, maxY), xyToPoint(0,minY)));
		
		    // Step 2b - arrow of Y-axis
		    GeneralPath arrow = new GeneralPath();
		    // set the start point of the line exactly at the top end of the Y-axis
		    Point2D.Double lineEnd = xyToPoint(0, maxY);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            // set the left "slope" of the arrow to the point (5,20)
		    arrow.lineTo(arrow.getCurrentPoint().getX()+5,arrow.getCurrentPoint().getY()+20);
		    // set the bottom of the arrow in point (-10, 0)
		    arrow.lineTo(arrow.getCurrentPoint().getX()-10,arrow.getCurrentPoint().getY());
		    // close the triangle of the arrow
		    arrow.closePath();
		    // draw the arrow
		    canvas.draw(arrow);
		    // fill the arrow
		    canvas.fill(arrow);
		
		    // Step 2c - draw the tracing of the Y-axis
		
		    // determine how much space we need for a tracing  “y” 
		    Rectangle2D bounds = axisFont.getStringBounds("y", context);
		    Point2D.Double labelPos = xyToPoint(0, maxY);
		    // show the tracing at the point with colculated coordinates
		    canvas.drawString("y", (float)labelPos.getX() + 10, (float)(labelPos.getY() - bounds.getY()));
		}
		
		// Step 3 - determine, whether the X-axis should be visible on the graphics or no
		
		if (minY<=0.0 && maxY>=0.0) {
			 /* it is visible, if the top border of the displayed area (maxY) >=0.0 
		    and lower (minY) <= 0.0*/
			
		    // Step 3a - X-axis ia a line between point (minX, 0) and (maxX, 0)
		    canvas.draw(new Line2D.Double(xyToPoint(minX, 0),
		    xyToPoint(maxX, 0)));
		    
		    //Step 3b - arrow of X-axis
		    GeneralPath arrow = new GeneralPath();
		    //set the start point of the line exactly at the right end of X-axis
		    Point2D.Double lineEnd = xyToPoint(maxX, 0);
		    arrow.moveTo(lineEnd.getX(), lineEnd.getY());
		    // set the top "slope" of the arrow in point (-20,-5)
		    arrow.lineTo(arrow.getCurrentPoint().getX()-20, arrow.getCurrentPoint().getY()-5);
		    // set the left part of the arrow at the point (0, 10)
		    arrow.lineTo(arrow.getCurrentPoint().getX(), arrow.getCurrentPoint().getY()+10);
		    //close the triangle of the arrow
		    arrow.closePath();
		    //draw the arrow
		    canvas.draw(arrow);
		    //fill the arrow
		    canvas.fill(arrow);
		    
		    // Step 3c - draw the tracing of X-axis
		    // determine, how much space we need for the tracing “x”
		    Rectangle2D bounds = axisFont.getStringBounds("x", context);
		    Point2D.Double labelPos = xyToPoint(maxX, 0);
		    
		    // show the tracing at the point with colculated coordinates
		    canvas.drawString("x",(float)(labelPos.getX()-bounds.getWidth()-10),(float)(labelPos.getY() + bounds.getY()));
		}
	}
	
	protected void paintMarkers(Graphics2D canvas) {
		// Step 1 - set the special pen to draw marker outlines
		canvas.setStroke(markerStroke);
		// set the color of marker outlines
		canvas.setColor(Color.RED);
		canvas.setPaint(Color.RED);
		
		// Step 2 - organize the cycle on all points of the graphics
		for (Double[] point: graphicsData) {
		    // ellipse is an object to represent a marker
		    Ellipse2D.Double marker = new Ellipse2D.Double();
		    
		    /*The ellipse will be specified by specifying it's coordinates:
		    the center and corner of the rectangle in which it is inscribed */
		    
		    // center at the point (x,y)
		    Point2D.Double center = xyToPoint(point[0], point[1]);
		    // corner of the rectangle stand at a distance (3,3)
		    Point2D.Double corner = shiftPoint(center, 3, 3);
		    // set the location of the ellipse at the center and diagonal
		    marker.setFrameFromCenter(center, corner);
		    // draw marker outlines
		    canvas.draw(marker);
		    // draw the inner area of the marker
		    canvas.fill(marker); 
		}
	}

}

