package org.japura.util.i18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * <P>
 * Copyright (C) 2009-2010 Carlos Eduardo Leite de Andrade
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
 */
public class I18nAdapter{

  private static I18nAdapter adapter = new I18nAdapter();

  public static I18nAdapter getAdapter() {
	return adapter;
  }

  public static void setAdapter(I18nAdapter adapter) {
	if (adapter == null) {
	  adapter = new I18nAdapter();
	}
	I18nAdapter.adapter = adapter;
  }

  private Collection<HandlerString> handlers = new ArrayList<HandlerString>();

  public void clearHandlers() {
	this.handlers.clear();
  }

  public Collection<HandlerString> getHandlers() {
	return Collections.unmodifiableCollection(this.handlers);
  }

  public void registerHandler(HandlerString handlerString) {
	if (handlerString != null) {
	  this.handlers.add(handlerString);
	}
  }

  public String getString(String key) {
	if (key == null) {
	  return key;
	}
	for (HandlerString handler : handlers) {
	  String str = handler.getString(key);
	  if (str != null) {
		return str;
	  }
	}
	return key;
  }

}