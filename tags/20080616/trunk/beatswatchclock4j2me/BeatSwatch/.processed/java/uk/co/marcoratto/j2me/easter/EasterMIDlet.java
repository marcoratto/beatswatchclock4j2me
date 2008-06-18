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

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import uk.co.marcoratto.j2me.about.About;
import uk.co.marcoratto.j2me.help.Help;
import uk.co.marcoratto.j2me.i18n.I18N;
import uk.co.marcoratto.j2me.info.Info;

public class EasterMIDlet extends MIDlet implements CommandListener {

    // display manager
    private Display display;

    // a menu with items
    // List menu;
	 private List menu;

    // command
    static final Command exitCommand = new Command(I18N.getInstance().translate("menu.button.exit"), Command.BACK, 0);

	 /** An alert to be reused for differemt errors. */
    private final Alert alert;
    private final Alert info;

    public EasterMIDlet() {
		menu = new List(I18N.getInstance().translate("menu.title"), Choice.IMPLICIT);
		menu.append(I18N.getInstance().translate("menu.calc"), null);
		menu.append(I18N.getInstance().translate("menu.verify"), null);
		menu.append(I18N.getInstance().translate("menu.help"), null);
		menu.append(I18N.getInstance().translate("menu.info"), null);
		menu.append(I18N.getInstance().translate("menu.about"), null);
		menu.setTicker(new Ticker(I18N.getInstance().translate("appl.title")));
		menu.addCommand(exitCommand);
		menu.setCommandListener(this);

		alert = new Alert(I18N.getInstance().translate("alert.title.error"), "", null, AlertType.ERROR);
        alert.setTimeout(3000);

        info = new Alert(I18N.getInstance().translate("alert.title.info"), "", null, AlertType.INFO);
        info.setTimeout(3000);
	 }

    public void startApp() {
	   display = Display.getDisplay(this);
	   mainMenu();
    }

    // main menu
    void mainMenu() {
      display.setCurrent(menu);
    }

   public void commandAction(Command c, Displayable d) {
      String label = c.getLabel();
		System.out.println("label=" + label);
      if (c == exitCommand) {
         destroyApp(true);
      } else if (label.equals(I18N.getInstance().translate("verify.button.back")) ||
            label.equals(I18N.getInstance().translate("info.button.back"))) {
			mainMenu();
      } else if (label.equals(I18N.getInstance().translate("calc.button.next"))) {
		  try {
			CalculateForm.getInstance(this, display, this).next();
        } catch (Exception e) {
        	alert.setString(I18N.getInstance().translate("alert.msg.calc_error"));
        	Display.getDisplay(this).setCurrent(alert);
        }
      } else if (label.equals(I18N.getInstance().translate("calc.button.previous"))) {
		  try {
			CalculateForm.getInstance(this, display, this).previous();
        } catch (Exception e) {
        	alert.setString(I18N.getInstance().translate("alert.msg.calc_error"));
        	Display.getDisplay(this).setCurrent(alert);
        }
      } else if (label.equals(I18N.getInstance().translate("verify.button.verify"))) {
    	  VerifyForm.getInstance(this, display, this).verify();
      } else {
	     List down = (List) display.getCurrent();
		 System.out.println("down.getSelectedIndex()=" + down.getSelectedIndex());	
         switch(down.getSelectedIndex()) {
           case 0: 
				   displayCalc();
					break;
           case 1: 
					displayVerify();
					break;
           case 2: 
					displayHelp();
					break;
           case 3:
				   displayProperties();
					break;
           case 4:
					displayAbout();
					break;
         }

      }
  }

   private void displayVerify() {
	      VerifyForm.getInstance(this, display, this).show();
	}

   private  void displayCalc() {
      CalculateForm.getInstance(this, display, this).show();
    }

   private  void displayAbout() {
		About.getInstance(display).show();
	 }

   private  void displayHelp() {
		Help.getInstance(display, this).show();
	 }

   private  void displayProperties() {
		Info.getInstance(display, this).show();
	 }

   public void destroyApp(boolean b) {
		notifyDestroyed();
	 }

    public void pauseApp() {
	 }

    void showAlert(String msg) {
		 alert.setString(msg);
		 Display.getDisplay(this).setCurrent(alert);
	}

    void showInfo(String msg) {
		 info.setString(msg);
		 Display.getDisplay(this).setCurrent(info);
	}
}
