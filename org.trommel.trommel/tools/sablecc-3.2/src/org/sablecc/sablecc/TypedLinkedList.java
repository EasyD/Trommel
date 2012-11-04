/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of SableCC.                             *
 * See the file "LICENSE" for copyright information and the  *
 * terms and conditions for copying, distribution and        *
 * modification of SableCC.                                  *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package org.sablecc.sablecc;

import java.util.*;

public class TypedLinkedList extends LinkedList
{
  Cast cast;

  public TypedLinkedList()
  {
    super();

    cast = NoCast.instance;
  }

  public TypedLinkedList(Collection c)
  {
    super(c);

    cast = NoCast.instance;
  }

  public TypedLinkedList(Cast cast)
  {
    super();

    this.cast = cast;
  }

  public TypedLinkedList(Collection c, Cast cast)
  {
    super(c);

    this.cast = cast;
  }

  public Cast getCast()
  {
    return cast;
  }

  public void addFirst(Object o)
  {
    super.addFirst(cast.cast(o));
  }

  public void addLast(Object o)
  {
    super.addLast(cast.cast(o));
  }

  public ListIterator listIterator(int index)
  {
    return new TypedLinkedListIterator(super.listIterator(index));
  }

  private class TypedLinkedListIterator implements ListIterator
  {
    ListIterator iterator;

    TypedLinkedListIterator(ListIterator iterator)
    {
      this.iterator = iterator;
    }

    public boolean hasNext()
    {
      return iterator.hasNext();
    }

    public Object next()
    {
      return iterator.next();
    }

    public boolean hasPrevious()
    {
      return iterator.hasPrevious();
    }

    public Object previous()
    {
      return iterator.previous();
    }

    public int nextIndex()
    {
      return iterator.nextIndex();
    }

    public int previousIndex()
    {
      return iterator.previousIndex();
    }

    public void remove
      ()
    {
      iterator.remove();
    }

    public void set
      (Object o)
    {
      iterator.set(cast.cast(o));
    }

    public void add
      (Object o)
    {
      iterator.add(cast.cast(o));
    }
  }
}
