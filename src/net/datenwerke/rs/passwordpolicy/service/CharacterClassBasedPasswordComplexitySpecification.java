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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.service.security.locale.SecurityMessages;

/**
 * Specifies the desired complexity of a password by means of character classes and 
 * the minimum number of characters from each class present in the password. 
 * 
 *
 */
public class CharacterClassBasedPasswordComplexitySpecification implements PasswordComplexitySpecification {
	
	private final static SecurityMessages messages = LocalizationServiceImpl.getMessages(SecurityMessages.class);
	
	private List<CharacterSelectionSpecification> characterSelectionSpecifications = new ArrayList<CharacterSelectionSpecification>();
	private int passwordMinLength;
	private CharacterClassPasswordGenerator characterClassPasswordGenerator;
	
	
	public CharacterClassBasedPasswordComplexitySpecification(int passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}
	
	@Override
	public int getPasswordMinLength() {
		return passwordMinLength;
	}
	
	public void addSelectionSpecification(CharacterSelectionSpecification characterSelectionSpecification) {
		characterSelectionSpecifications.add(characterSelectionSpecification);
		characterClassPasswordGenerator = null;
	}
	
	public List<CharacterSelectionSpecification> getCharacterSelectionSpecifications() {
		return characterSelectionSpecifications;
	}
	
	/**
	 * Checks whether the given password fulfills this specifications requirements
	 * 
	 * @param password
	 * @return
	 */
	public boolean isSatisfiedBy(String password){
		if(password.length() < passwordMinLength)
			return false;
		
		HashMap<Character, Integer> cindex = buildCharacterIndex(password);
		
		for(CharacterSelectionSpecification cse : characterSelectionSpecifications){
			if(cse.getMinOccurrences() > 0){
				if(cse.getMinOccurrences() > sumOccurencesOfClassmembers(cse.getCharacterClass(), cindex)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public List<String> getErrorCause(String password){
		List<String> errors = new ArrayList<String>();

		if(password.length() < passwordMinLength)
			errors.add(messages.passwordLengthSpec(passwordMinLength, password.length()));
			
		HashMap<Character, Integer> cindex = buildCharacterIndex(password);
		
		for(CharacterSelectionSpecification cse : characterSelectionSpecifications)
			if(cse.getMinOccurrences() > 0)
				if(cse.getMinOccurrences() > sumOccurencesOfClassmembers(cse.getCharacterClass(), cindex))
					errors.add(messages.passwordCharacterClassRequirement(cse.getMinOccurrences(), new String(cse.getCharacterClass().getCharacters()), sumOccurencesOfClassmembers(cse.getCharacterClass(), cindex)));
					

		return errors;
	}
	
	
	private int sumOccurencesOfClassmembers(CharacterClass characterClass, HashMap<Character, Integer> characterIndex){
		int sum = 0;
		for(char c : characterClass.getCharacters()){
			if(characterIndex.containsKey(c)){
				sum += characterIndex.get(c);
			}
		}
		
		return sum;
	}
	
	/**
	 * Build a map which contains for each character in the password
	 * its number of occurences
	 * 
	 * @param password
	 * @return
	 */
	private HashMap<Character, Integer> buildCharacterIndex(String password){
		HashMap<Character, Integer> characterCounts = new HashMap<Character, Integer>();
		for(char c : password.toCharArray()){
			int count = 1;
			
			Integer oldCount = characterCounts.get(c);
			if(null != oldCount)
				count = oldCount + 1;
			
			characterCounts.put(c, count);
		}
		return characterCounts;
	}

	
	@Override
	public PasswordGenerator getPasswordGenerator() {
		if(null == characterClassPasswordGenerator)
			characterClassPasswordGenerator = new CharacterClassPasswordGenerator(this);
		
		return characterClassPasswordGenerator;
	}
	
}
