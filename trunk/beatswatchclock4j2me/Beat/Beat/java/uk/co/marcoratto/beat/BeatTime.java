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
package uk.co.marcoratto.beat;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;;

public class BeatTime {

    public BeatTime() {
    }

    public int getTimeAsIntValue() {
    	return this.getTime() / 100;
    }

    private int getTime() {
    	Calendar nowGMT = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    	Date now = new Date();
        now.setTime(now.getTime() + 0x36ee80L);
        nowGMT.setTime(now);

        int hr24 = nowGMT.get(11);
        int min = nowGMT.get(12);
        int sec = nowGMT.get(13);
        int secondInDay = (hr24 * 60 + min) * 60 + sec;
        int beat = (int)(100000D * ((double)secondInDay / 86400D));
        return beat;
    }

    public double getTimeAsDoubleValue() {
        StringBuffer sb = new StringBuffer("");
        int sx = this.getTime() / 100;
        int dx = this.getTime() % 100;
        sb.append(sx);
        sb.append(".");
        sb.append(dx);
    	double d = Double.parseDouble(sb.toString()); 
        return d;
    }

    public String toString() {
    	int beat = this.getTime();
        StringBuffer atNow = new StringBuffer("@");
        String SwatchBeat = "000" + String.valueOf(beat / 100);
        String frac = "00" + String.valueOf(beat % 100);
        atNow.append(SwatchBeat.substring(SwatchBeat.length() - 3));
        atNow.append(".");
        atNow.append(frac.substring(frac.length() - 2));

        return atNow.toString();
    }

}
