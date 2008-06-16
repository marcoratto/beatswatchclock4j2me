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

public class TestBeat {

	public static void main(String[] args) {
		BeatTime bt = new BeatTime();
		System.out.println(bt.getTimeAsIntValue());
		System.out.println(bt.getTimeAsDoubleValue());
		System.out.println(bt.toString());
	}

}
