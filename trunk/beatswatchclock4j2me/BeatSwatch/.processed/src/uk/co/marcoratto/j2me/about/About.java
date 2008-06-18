/*
 * Copyright (C) 2008 Marco Ratto
 *
 * This file is part of the project EasterJ2ME.
 *
 * EasterJ2ME is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 *
 * EasterJ2ME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EasterJ2ME; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uk.co.marcoratto.j2me.about;

import javax.microedition.lcdui.*;
import uk.co.marcoratto.j2me.i18n.I18N;

/**
 * Typical about box with a string and an image.
 * In this case the Sun copyright and logo.
 */

public class About {

	private static About istanza = null;
	private Alert alert;
	private Display display;
	private String copyright;

    /**
     * Do not allow anyone to create this class
     */
    private About() {
	}

    public static About getInstance(Display d) {
      if (istanza == null) {
        try {
          istanza = new About();
          istanza.display = d;
			 istanza.init();
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      return istanza;
    }

    private void init() throws Exception {
		copyright = I18N.getInstance().translate("about.copyright");
		alert = new Alert(I18N.getInstance().translate("about.title"));
		alert.setTimeout(Alert.FOREVER);

	if (display.numColors() > 2) {
	    String icon = (display.isColor()) ?  "/icons/JavaPowered-8.png" : "/icons/JavaPowered-2.png";

	    try {
	        Image image = Image.createImage(icon);
			  alert.setImage(image);
	    } catch (java.io.IOException x) {
	   }
		}

   	alert.setString(copyright);
	}

    public void show() {
	   display.setCurrent(alert);
    }

}
