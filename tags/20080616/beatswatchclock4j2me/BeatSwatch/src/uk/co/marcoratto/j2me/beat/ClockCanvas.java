/*
 * Copyright (C) 2008 Marco Ratto
 *
 * This file is part of the project Beat Swatch Clock For J2ME.
 *
 * Beat Swatch Clock For J2ME is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 *
 * Beat Swatch Clock For J2ME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Beat Swatch Clock For J2ME; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uk.co.marcoratto.j2me.beat;

import java.io.IOException;

import javax.microedition.lcdui.*;

import uk.co.marcoratto.beat.BeatTime;
import uk.co.marcoratto.j2me.log.Logger;

/**
 * The component for AudioPlayer.
 * It will create a player for the selected url, play and display
 * some properties of the player.
 *
 * Use star key to increase the volume, pound key to decrease the volume
 *
 **/
public class ClockCanvas extends Canvas implements Runnable {
    private Thread  thread;

    private boolean interrupted;
    private Display parentDisplay;
    
    private static Logger log = Logger.getLogger(ClockCanvas.class);

    /** buffer image of the screen */
    private Image image;
    
    /** Pattern image used for border */
    private Image bimage;    
	private Image background = null;

    public ClockCanvas(Display display) {
        super();
        image = Image.createImage(getWidth(), getHeight());
        try {
			background = Image.createImage("/background.png");
		} catch (IOException e) {
			log.warn(e);
		}
        this.parentDisplay = display;
        initialize();
    }

    private void initialize() {
        interrupted = false;
        thread = new Thread(this);
        thread.start();        
        this.parentDisplay.setCurrent(this);
    }

    public void paint(Graphics g) {
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        
        log.debug("screenWidth=" + screenWidth);
        log.debug("screenHeight=" + screenHeight);

        g.setColor(0xFFFFFF);
        g.fillRect(0, 0, screenWidth, screenHeight);
                
		int imageWidth = background.getWidth();
        int imageHeight = background.getHeight();
        log.debug("imageWidth=" + imageWidth);
        log.debug("imageHeight=" + imageHeight);
        
        int imageX = (screenWidth - imageWidth) / 2;
        int imageY = (screenHeight - imageHeight) / 2;
        log.debug("imageX=" + imageX);
        log.debug("imageY=" + imageY);

        genFrame(imageX, imageY, imageWidth, imageHeight);

        g.drawImage(image, 0, 0, Graphics.LEFT | Graphics.TOP);
        
        g.drawImage(background, imageX, imageY, Graphics.LEFT | Graphics.TOP);
    }

    public void run() {
        // mtime update loop
        while (!interrupted) {
            try {
                this.repaint();
                Thread.sleep(1000);
            } catch (Exception ex) {
            	// Ignore
            }
        }
    }

    protected void keyPressed(int keycode) {
    }

    /**
     * Paint the photo frame into the buffered screen image.
     * This will avoid drawing each of its parts on each repaint.
     * Paint will only need to put the image into the frame.
     * @param style the style of frame to draw.
     * @param x the x offset of the image.
     * @param y the y offset of the image
     * @param width the width of the animation image
     * @param height the height of the animation image
     */
    private void genFrame(int x, int y, int width, int height) {
    	log.debug("width=" + width);
    	log.debug("height=" + height);
    	Graphics g = image.getGraphics();

        // Clear the entire image to white
        g.setColor(0xffffff);
        g.fillRect(0, 0, image.getWidth() + 1, image.getHeight() + 1);

        // Set the origin of the image and paint the border and image.
        g.translate(x, y);
        paintBorder(g, width, height);

        g.setColor(0x000000);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        int fontSize = Font.getDefaultFont().getHeight();
        log.debug("fontSize=" + fontSize);
        BeatTime beat = new BeatTime();

        int stringX = width / 2;
        int stringY = ((height - fontSize) / 2) + fontSize;
        log.debug("stringX=" + stringX);
        log.debug("stringY=" + stringY);
        g.drawString(beat.toString(), stringX, stringY, Graphics.TOP | Graphics.HCENTER);
        
    }
    
    /**
     * Draw a border of the selected style.
     * @param g graphics context to which to draw.
     * @param style of the border to display
     * @param w the width reserved for the image
     * @param h the height reserved of the image
     * @see setStyle
     */
    private void paintBorder(Graphics g, int w, int h) {
        // Draw fancy border with image between outer and inner rectangles
        if (bimage == null) {
            bimage = genBorder(); // Generate the border image
        }

        int bw = bimage.getWidth();
        int bh = bimage.getHeight();
        int i;
        // Draw the inner and outer solid border
        g.setGrayScale(128);
        g.drawRect(-1, -1, w + 1, h + 1);
        g.drawRect(-bw - 2, -bh - 2, w + (bw * 2) + 3, h + (bh * 2) + 3);

        // Draw it in each corner
        g.drawImage(bimage, -1, -1, Graphics.BOTTOM | Graphics.RIGHT);
        g.drawImage(bimage, -1, h + 1, Graphics.TOP | Graphics.RIGHT);
        g.drawImage(bimage, w + 1, -1, Graphics.BOTTOM | Graphics.LEFT);
        g.drawImage(bimage, w + 1, h + 1, Graphics.TOP | Graphics.LEFT);

        // Draw the embedded image down left and right sides
        for (i = ((h % bh) / 2); i < (h - bh); i += bh) {
            g.drawImage(bimage, -1, i, Graphics.RIGHT | Graphics.TOP);
            g.drawImage(bimage, w + 1, i, Graphics.LEFT | Graphics.TOP);
        }

        // Draw the embedded image across the top and bottom
        for (i = ((w % bw) / 2); i < (w - bw); i += bw) {
            g.drawImage(bimage, i, -1, Graphics.LEFT | Graphics.BOTTOM);
            g.drawImage(bimage, i, h + 1, Graphics.LEFT | Graphics.TOP);
        }
    }

    /**
     * Create an image for the border.
     * The border consists of a simple "+" drawn in a 5x5 image.
     * Fill the image with white and draw the "+" as magenta.
     * @return the image initialized with the pattern
     */
    private Image genBorder() {
        Image image = Image.createImage(5, 5);
        Graphics g = image.getGraphics();
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, 5, 5);
        g.setColor(128, 0, 255);
        g.drawLine(2, 1, 2, 3); // vertical
        g.drawLine(1, 2, 3, 2); // horizontal

        return image;
    }    
}
