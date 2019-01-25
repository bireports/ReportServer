/*
 *  ReportServer
 *  Copyright (c) 2018 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.rs.passwordpolicy.service;

/**
 * Combines a class of characters with the number of characters to be chosen from this class
 * 
 *
 */
public class CharacterSelectionSpecification {
	
	private CharacterClass characterClass;
	private int minOccurrences;

	
	public CharacterSelectionSpecification(CharacterClass characterClass, int minOccurrences) {
		this.characterClass = characterClass;
		this.minOccurrences = minOccurrences;
	}


	public CharacterClass getCharacterClass() {
		return characterClass;
	}


	public int getMinOccurrences() {
		return minOccurrences;
	}
	
}
