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
package uk.co.marcoratto.j2me.help;

import javax.microedition.lcdui.*;

import java.io.*;

import uk.co.marcoratto.j2me.i18n.I18N;

/**
 * Typical about box with a string and an image.
 * In this case the Sun copyright and logo.
 */

public class Help {

	 private static Help istanza = null;
	 private TextBox box;
	 private Display display;
    StringBuffer str = null;
	 private CommandListener commandListener = null;

    private final static String locale = System.getProperty("microedition.locale");

    /**
     * Do not allow anyone to create this class
     */
    private Help() {
	 }

    public static Help getInstance(Display d, CommandListener cl) {
      if (istanza == null) {
        try {
          istanza = new Help();
          istanza.display = d;
			 istanza.commandListener = cl;
			 istanza.init();
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      return istanza;
    }

    private void init() throws Exception {
	   box = new TextBox(I18N.getInstance().translate("help.title"), null, 65535, TextField.UNEDITABLE);	
	   box.addCommand(new Command(I18N.getInstance().translate("help.button.back"), Command.EXIT, 0));
      box.setCommandListener(this.commandListener);

	   InputStream is = null;
      try {
				String file = "/help/help." + locale + ".txt";
            System.out.println("Help.getInstance(): try to read " + file);
            try {
               is = this.getClass().getResourceAsStream(file);
            } catch(Exception e) {
               System.err.println("Help.getInstance(): ERROR! File " + file + " not found.");
            }
            if (is == null) {
                  file = "/help/help.en.txt";
                  System.out.println("Help.getInstance(): try to read " + file);
                  is = this.getClass().getResourceAsStream(file);
            }

            byte b[] = new byte[1];
				str = new StringBuffer();
            while (is.read(b) != -1 ) {
                str.append(new String(b));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
               try {
                  is.close();
               } catch (IOException e) {
               }
            }
        }

   	box.setString(str.toString());
	}

    public void show() {
	   display.setCurrent(box);
    }

}
