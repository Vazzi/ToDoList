package com.vlasakjakub.ToDoList.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;


/**
 * Border which has rounded corners.
 *
 */
class RoundedCornerBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Paints rounded border.
	 * @param c target component.
	 * @param g Graphics
	 * @param x 
	 * @param y
	 * @param width
	 * @param height
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	    Graphics2D g2 = (Graphics2D)g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    int r = 15;
	    RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width-1, height-1, r, r);
	    Container parent = c.getParent();
	    if(parent!=null) {
	      g2.setColor(parent.getBackground());
	      Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
	      corner.subtract(new Area(round));
	      g2.fill(corner);
	    }
	    g2.draw(round);
	    g2.dispose();
	  }
	
	  
}