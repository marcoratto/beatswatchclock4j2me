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
package uk.co.marcoratto.j2me.easter;

import java.util.Date;

import javax.microedition.lcdui.*;

import uk.co.marcoratto.j2me.i18n.I18N;
import uk.co.marcoratto.easter.*;

public class VerifyForm {

	private EasterMIDlet parent;
	private static VerifyForm istanza = null;
	private Display display;
	private CommandListener commandListener = null; 

	private Form form;

	// command
	private Command backCommand;
	private Command calcCommand;
	
	private DateField dateField;
	 
    /**
     * Do not allow anyone to create this class
     */
    private VerifyForm() {
    	
    }
    
    private void init() {
		form = new Form(I18N.getInstance().translate("verify.title"));

		backCommand = new Command(I18N.getInstance().translate("verify.button.back"), Command.BACK, 0);
		calcCommand = new Command(I18N.getInstance().translate("verify.button.verify"), Command.STOP, 2);
				
		dateField = new DateField(I18N.getInstance().translate("verify.label.date"), DateField.DATE);
		dateField.setDate(new Date());
		
		form.append(dateField);
		form.addCommand(backCommand);
		form.addCommand(calcCommand);
		form.setCommandListener(this.commandListener);
	 }

    public void verify() {
    	try {
			  boolean b = Easter.isEaster(dateField.getDate());
			  if (b) {
		        	parent.showInfo(I18N.getInstance().translate("alert.msg.easter_true"));
			  } else {
		        	parent.showInfo(I18N.getInstance().translate("alert.msg.easter_false"));
			  }
        } catch (Exception e) {
        	e.printStackTrace();
        	parent.showAlert(I18N.getInstance().translate("alert.msg.easter_exception"));
        }

    }

    public static VerifyForm getInstance(EasterMIDlet owner, Display d, CommandListener cl) {
      if (istanza == null) {
        try {
          istanza = new VerifyForm();
          istanza.display = d;
		  istanza.commandListener = cl;
		  istanza.parent = owner;
		  istanza.init();
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      return istanza;
    }

    public void show() {
	   display.setCurrent(form);
	}

}
