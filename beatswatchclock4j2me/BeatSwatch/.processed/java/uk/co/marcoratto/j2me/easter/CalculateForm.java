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

import java.util.Calendar;
import java.util.Date;

import javax.microedition.lcdui.*;

import uk.co.marcoratto.easter.Easter;
import uk.co.marcoratto.easter.EasterException;
import uk.co.marcoratto.j2me.i18n.I18N;

public class CalculateForm {

	private EasterMIDlet parent;
	private static CalculateForm istanza = null;
	private Display display;
	private CommandListener commandListener = null; 

	private List list;

	// command
	private Command previousCommand;
	private Command nextCommand;
	private Command backCommand;
	
	private int currentYear = 0;
    /**
     * Do not allow anyone to create this class
     */
    private CalculateForm() {    	
    }
    
    private void init() {
    	this.list = new List(I18N.getInstance().translate("calc.title"), Choice.IMPLICIT);
		for (int idx=0; idx<10; idx++) {
			list.append("", null); 
		}    	

		this.backCommand = new Command(I18N.getInstance().translate("calc.button.back"), Command.BACK, 0);
		this.nextCommand = new Command(I18N.getInstance().translate("calc.button.next"), Command.STOP, 2);
		this.previousCommand = new Command(I18N.getInstance().translate("calc.button.previous"), Command.STOP, 2);

		this.currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		this.showList();
		
		list.addCommand(backCommand);
		list.addCommand(nextCommand);
		list.addCommand(previousCommand);
		list.setCommandListener(this.commandListener);
	 }

    private void showList() {
		for (int j=-5, i=currentYear-5, idx=0; j<5; i++, j++, idx++) {
			Date d;
			try {
				d = Easter.find(i);
				list.set(idx, this.formatDateToString(d), null); 
			} catch (EasterException e) {
				e.printStackTrace();
				parent.showAlert(I18N.getInstance().translate("alert.msg.easter_exception"));
			}
		}    	
    }

    public void previous() {
		this.currentYear -= 10;    	
		this.showList();
    }

    public void next() {
		this.currentYear += 10;    	
		this.showList();
    }

    public static CalculateForm getInstance(EasterMIDlet owner, Display d, CommandListener cl) {
      if (istanza == null) {
        try {
          istanza = new CalculateForm();
          istanza.display = d;
		  istanza.commandListener = cl;
		  istanza.init();
        } catch (Exception e) {
          System.out.println(e);
        }
      }
      return istanza;
    }

    public void show() {
	   display.setCurrent(list);
	}

   /**
    * Extracts the double number from text field.
    *
    * @param t the text field to be used.
    * @param type the string with argument number.
    * @throws NumberFormatException is case of wrong input.
    */
   private String formatDateToString(Date d) {
       Calendar c = Calendar.getInstance();
       c.setTime(d);
       
	   StringBuffer out = new StringBuffer("");
              
       out.append(this.intToTwoDigit(c.get(Calendar.DAY_OF_MONTH)));
       out.append("/");
       out.append(this.intToTwoDigit(c.get(Calendar.MONTH)));
       out.append("/");
       out.append(c.get(Calendar.YEAR));

       return out.toString();
   }

   private String intToTwoDigit(int i) {
	   return ((i < 10) ? "0" : "") + i;   
   }
   
}
