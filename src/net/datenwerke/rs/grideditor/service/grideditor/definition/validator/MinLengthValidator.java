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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition.validator;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.grideditor.service.grideditor.locale.GridEditorMessages;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.grideditor.client.grideditor.dto",
	generateDto2Poso=false,
	createDecorator=true
)
public class MinLengthValidator extends Validator<Object> {

	@ExposeToClient
	private int length;
	
	public MinLengthValidator(){
	}

	public MinLengthValidator(int length, String errorMsg){
		setLength(length);
		setErrorMsg(errorMsg);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String validate(Object casted) {
		if(null == casted)
			return null;
		
		return String.valueOf(casted).length() >= length ? null :
			(null == getErrorMsg() ? GridEditorMessages.INSTANCE.validationFailedDefaultMessage() : getErrorMsg());
	}
	
	
}
