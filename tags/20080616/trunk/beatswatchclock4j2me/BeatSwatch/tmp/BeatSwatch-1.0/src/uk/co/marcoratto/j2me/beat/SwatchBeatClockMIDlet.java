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

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import uk.co.marcoratto.j2me.i18n.I18N;
import uk.co.marcoratto.j2me.log.Logger;
import uk.co.marcoratto.j2me.about.About;

public class SwatchBeatClockMIDlet extends MIDlet implements CommandListener {

	private static Logger log;
	
	private static ClockCanvas clockGUI = null;
	
    // display manager
    private Display display;
    private Displayable currentDisplayable; 

    private final Command logCommand;
    private final Command aboutCommand;
    private final Command exitCommand;
        
    public SwatchBeatClockMIDlet() {
    	super();  	       	

    	log = Logger.getLogger(this);    	
    	this.display = Display.getDisplay(this);

    	clockGUI = new ClockCanvas(display);
    	clockGUI.addCommand(exitCommand = new Command("Exit", Command.EXIT, 2));
    	clockGUI.addCommand(logCommand = new Command("Log", Command.BACK, 1));
    	clockGUI.addCommand(aboutCommand = new Command("About", Command.BACK, 2));
    	clockGUI.setCommandListener(this);
    	clockGUI.setTicker(new Ticker(I18N.getInstance().translate("appl.title")));
    	currentDisplayable = clockGUI;	    

    	log.trace("end");
	}

    /**
     * Called when this MIDlet is started for the first time,
     * or when it returns from paused mode.
     * If a player is visible, and it was playing
     * when the MIDlet was paused, call its playSound method.
     */
    public void startApp() {
        display.setCurrent(currentDisplayable);        	
    }

   /*
    * simple implementation, not reflected actual state
    * of player.
    */
   public void commandAction(Command c, Displayable s) {
	   if (c == exitCommand) {
           destroyApp(true);
           notifyDestroyed();
       } else if (c == logCommand) {
    	   log.show(this);
       } else if (c == aboutCommand) {
    	   About.getInstance(this).show();
       }
   }

   public void destroyApp(boolean b) {
	   display.setCurrent(null);
	   notifyDestroyed();
	 }

   /**
    * Called when this MIDlet is paused.
    * If the player GUI is visible, call its pauseSound method.
    * For consistency across different VM's
    * it's a good idea to stop the player if it's currently
    * playing.
    */
   public void pauseApp() {	
   }

}
