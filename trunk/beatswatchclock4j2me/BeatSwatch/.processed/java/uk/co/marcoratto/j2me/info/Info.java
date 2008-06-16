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
package uk.co.marcoratto.j2me.info;

import javax.microedition.lcdui.*;

import uk.co.marcoratto.j2me.i18n.I18N;

public class Info {

	 private static Info istanza = null;
	 private Display display;
	 private Form form;
     private StringBuffer propbuf;
	 private CommandListener commandListener = null; 

    /**
     * Do not allow anyone to create this class
     */
    private Info() {
	 }

    public static Info getInstance(Display d, CommandListener cl) {
      if (istanza == null) {
        try {
          istanza = new Info();
          istanza.display = d;
			 istanza.commandListener = cl;
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      return istanza;
    }

    public void show() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long free = runtime.freeMemory();
        long total = runtime.totalMemory();

        if (form == null) {
            form = new Form(I18N.getInstance().translate("info.title"));
            propbuf = new StringBuffer(50);
            form.append(I18N.getInstance().translate("info.free.memory") + free + "\n");
            form.append(I18N.getInstance().translate("info.total.memory") + total + "\n");
            form.append(showProp("microedition.configuration"));
            form.append(showProp("microedition.profiles"));
            form.append(showProp("microedition.platform"));
            form.append(showProp("microedition.locale"));
            form.append(showProp("microedition.encoding"));
				form.addCommand(new Command(I18N.getInstance().translate("info.button.back"), Command.EXIT, 0));
				form.setCommandListener(this.commandListener);
        } else {
            form.set(0, new StringItem("", I18N.getInstance().translate("info.free.memory") + free + "\n"));
        }
	   display.setCurrent(form);
	}

    /**
     * Show a property.
     */
    private String showProp(String prop) {
        String value = System.getProperty(prop);
        propbuf.setLength(0);
        propbuf.append(prop);
        propbuf.append("=");
        if (value == null) {
            propbuf.append("<undefined>");
        } else {
            propbuf.append("\"");
            propbuf.append(value);
            propbuf.append("\"");
        }
        propbuf.append("\n");
        return propbuf.toString();
    }

}
