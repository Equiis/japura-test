package org.japura.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * Copyright (C) 2011-2013 Carlos Eduardo Leite de Andrade
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
public class WeakList<T> implements List<T>{

  private final ReferenceQueue<T> queue = new ReferenceQueue<T>();
  private final List<WeakReference<T>> list = new ArrayList<WeakReference<T>>();

  @Override
  public boolean add(T obj) {
	expunge();
	return list.add(new WeakReference<T>(obj, queue));
  }

  @Override
  public T get(int index) {
	expunge();
	return list.get(index).get();
  }

  @Override
  public int size() {
	expunge();
	return list.size();
  }

  @Override
  public T remove(int index) {
	expunge();
	return list.remove(index).get();
  }

  @Override
  public boolean isEmpty() {
	expunge();
	return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
	expunge();
	for (WeakReference<T> wr : list) {
	  if (wr.get().equals(o)) {
		return true;
	  }
	}
	return false;
  }

  @Override
  public Object[] toArray() {
	expunge();
	Object[] array = new Object[list.size()];
	int index = 0;
	for (WeakReference<T> wr : list) {
	  array[index] = wr.get();
	  index++;
	}
	return array;
  }

  @Override
  public boolean remove(Object o) {
	expunge();
	WeakReference<T> removeMe = null;
	for (WeakReference<T> wr : list) {
	  if (wr.get().equals(o)) {
		removeMe = wr;
		break;
	  }
	}

	if (removeMe != null) {
	  return list.remove(removeMe);
	}
	return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
	expunge();
	int count = 0;
	for (Object obj : c) {
	  for (WeakReference<T> wr : list) {
		if (wr.get().equals(obj)) {
		  count++;
		  break;
		}
	  }
	}
	return (c.size() == count);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
	expunge();
	for (T obj : c) {
	  list.add(new WeakReference<T>(obj, queue));
	}
	return true;
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
	expunge();
	for (T obj : c) {
	  list.add(index, new WeakReference<T>(obj, queue));
	}
	return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
	expunge();
	List<WeakReference<T>> removeList = new ArrayList<WeakReference<T>>();
	for (Object obj : c) {
	  for (WeakReference<T> wr : list) {
		if (obj.equals(wr.get()) == false) {
		  removeList.add(wr);
		  break;
		}
	  }
	}
	return list.removeAll(removeList);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
	expunge();
	List<WeakReference<T>> removeList = new ArrayList<WeakReference<T>>();
	for (WeakReference<T> wr : list) {
	  if (c.contains(wr.get()) == false) {
		removeList.add(wr);
	  }
	}
	list.removeAll(removeList);
	return (removeList.size() > 0);
  }

  @Override
  public void clear() {
	list.clear();
  }

  @Override
  public T set(int index, T element) {
	expunge();
	WeakReference<T> p = list.set(index, new WeakReference<T>(element, queue));
	return p.get();
  }

  @Override
  public void add(int index, T element) {
	expunge();
	list.add(index, new WeakReference<T>(element, queue));
  }

  @Override
  public int indexOf(Object o) {
	expunge();
	for (int i = 0; i < list.size(); i++) {
	  WeakReference<T> wr = list.get(i);
	  if (wr.get().equals(o)) {
		return i;
	  }
	}
	return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
	expunge();
	int i = list.size() - 1;
	while (i > -1) {
	  WeakReference<T> wr = list.get(i);
	  if (wr.get().equals(o)) {
		break;
	  }
	  i--;
	}
	return i;
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
	expunge();
	List<WeakReference<T>> sw = list.subList(fromIndex, toIndex);
	List<T> sl = new ArrayList<T>();
	for (WeakReference<T> wr : sw) {
	  sl.add(wr.get());
	}
	return sl;
  }

  @SuppressWarnings("hiding")
  @Override
  public <T> T[] toArray(T[] a) {
	throw new RuntimeException("method not implemented");
  }

  @Override
  public Iterator<T> iterator() {
	throw new RuntimeException("method not implemented");
  }

  @Override
  public ListIterator<T> listIterator() {
	throw new RuntimeException("method not implemented");
  }

  @Override
  public ListIterator<T> listIterator(int index) {
	throw new RuntimeException("method not implemented");
  }

  private void expunge() {
	Object garbagedObj = queue.poll();
	while (garbagedObj != null) {
	  int index = list.indexOf(garbagedObj);
	  if (index != -1) {
		list.remove(index);
	  }
	  garbagedObj = queue.poll();
	}
  }

}