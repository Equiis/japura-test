package org.japura.util.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * 
 * Copyright (C) 2013 Carlos Eduardo Leite de Andrade
 * <P>
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <P>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <P>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <A
 * HREF="www.gnu.org/licenses/">www.gnu.org/licenses/</A>
 * <P>
 * For more information, contact: <A HREF="www.japura.org">www.japura.org</A>
 * <P>
 * 
 * @author Carlos Eduardo Leite de Andrade
 * 
 */
public class IdentifierNode implements Serializable{

  private static final long serialVersionUID = 1L;

  private String identifier;
  private String name;
  private Collection<IdentifierNode> childrenNodes;

  public IdentifierNode(String identifier, String name) {
	this(identifier, name, null);
  }

  public IdentifierNode(String identifier, String name,
	  Collection<IdentifierNode> childrenNodes) {
	this.name = name;
	this.identifier = identifier;
	if (childrenNodes == null) {
	  childrenNodes = new ArrayList<IdentifierNode>();
	}
	this.childrenNodes = childrenNodes;
  }

  public String getIdentifier() {
	return identifier;
  }

  public String getName() {
	return name;
  }

  public Collection<IdentifierNode> getChildren() {
	return Collections.unmodifiableCollection(childrenNodes);
  }

}
