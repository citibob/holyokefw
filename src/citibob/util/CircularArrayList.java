/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * <p>
 * Originally written by Dr. Heinz Kabutz in the very excellent
 * <a href="http://www.smotricz.com/kabutz/">The Java Specialists Newsletter</a>
 * </p><p>
 * Cleaned from many infamous bugs and completed (by ProActive project)
 * Modified by Robert Fischer (April 2006)
 * </p>
 *
 * @author  Heinz Kabutz
 * @version 1.0,  2001/10/23
 * @since   ProActive 0.9
 *
 */
package citibob.util;

import java.util.Iterator;
import java.io.*;

public class CircularArrayList extends java.util.AbstractList
    implements java.util.List, java.io.Serializable {
    private static final int DEFAULT_SIZE = 5;
    protected Object[] array;

    // head points to the first logical element in the array, and
    // tail points to the element following the last.  This means
    // that the list is empty when head == tail.  It also means
    // that the array array has to have an extra space in it.
    protected int head = 0;

    // head points to the first logical element in the array, and
    // tail points to the element following the last.  This means
    // that the list is empty when head == tail.  It also means
    // that the array array has to have an extra space in it.
    protected int tail = 0;

    // Strictly speaking, we don't need to keep a handle to size,
    // as it can be calculated programmatically, but keeping it
    // makes the algorithms faster.
    protected int size = 0;

	// Size we pare it down to in addCircular()
	protected int circularSize = 0;
	
    public CircularArrayList() {
//        this(DEFAULT_SIZE);
    }

    public CircularArrayList(int circularSize) {
		setCircularSize(circularSize);
	}
	public int getCircularSize() { return circularSize; }
    public void setCircularSize(int circularSize) {
		this.circularSize = circularSize;
        array = new Object[circularSize+1];
    }

    public CircularArrayList(java.util.Collection c) {
        tail = c.size();
        array = new Object[c.size()];
        c.toArray(array);
    }

//private void writeObject(ObjectOutputStream out) throws IOException
//{
//	super.writeObject(out);
//	out.writeInt(circularSize);
//}
//
//private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
//{
//	super.readObject(in);
//	circularSize = in.readInt();
//}

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("CircularArray size=");
        sb.append(size);
        sb.append("\n");
        for (int i = 0; i < size; i++) {
            sb.append("[");
            sb.append(convert(i));
            sb.append("]=>");
            sb.append(array[convert(i)]);
            sb.append(", ");
        }
        sb.append("\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        CircularArrayList c = new CircularArrayList(5);
        c.add(0, new Integer(8));
        System.out.println(c.toString());
        c.add(0, new Integer(7));
        System.out.println(c.toString());
        c.add(0, new Integer(6));
        System.out.println(c.toString());
        c.add(0, new Integer(5));
        System.out.println(c.toString());
        c.add(0, new Integer(4));
        System.out.println(c.toString());
        c.add(0, new Integer(3));
        System.out.println(c.toString());
        c.add(0, new Integer(2));
        System.out.println(c.toString());
        c.add(0, new Integer(1));
        System.out.println(c.toString());
        c.add(0, new Integer(0));
        System.out.println(c.toString());
    }

    public boolean isEmpty() {
        return head == tail; // or size == 0
    }

    // We use this method to ensure that the capacity of the
    // list will suffice for the number of elements we want to
    // insert.  If it is too small, we make a new, bigger array
    // and copy the old elements in.
    public void ensureCapacity(int minCapacity) {
        int oldCapacity = array.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = ((oldCapacity * 3) / 2) + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            Object[] newData = new Object[newCapacity];
            toArray(newData);
            tail = size;
            head = 0;
            array = newData;
        }
    }

    public int size() {
        // the size can also be worked out each time as:
        // (tail + array.length - head) % array.length
        return size;
    }

    public boolean contains(Object elem) {
        return indexOf(elem) >= 0;
    }

    public int indexOf(Object elem) {
        if (elem == null) {
            for (int i = 0; i < size; i++)
                if (array[convert(i)] == null) {
                    return i;
                }
        } else {
            for (int i = 0; i < size; i++)
                if (elem.equals(array[convert(i)])) {
                    return i;
                }
        }
        return -1;
    }

    public int lastIndexOf(Object elem) {
        if (elem == null) {
            for (int i = size - 1; i >= 0; i--)
                if (array[convert(i)] == null) {
                    return i;
                }
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (elem.equals(array[convert(i)])) {
                    return i;
                }
        }
        return -1;
    }

    public Object[] toArray() {
        return toArray(new Object[size]);
    }

    public Object[] toArray(Object[] a) {
        //System.out.println("head="+head+" tail="+tail+" size="+size);
        if (size == 0) {
            return a;
        }
        if (a.length < size) {
            a = (Object[]) java.lang.reflect.Array.newInstance(a.getClass()
                                                                .getComponentType(),
                    size);
        }
        if (head < tail) {
            System.arraycopy(array, head, a, 0, tail - head);
        } else {
            System.arraycopy(array, head, a, 0, array.length - head);
            System.arraycopy(array, 0, a, array.length - head, tail);
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    public Object get(int index) {
        rangeCheck(index);
        return array[convert(index)];
    }
	public Object getFirst() { return get(0); }
	public Object getLast() { return get(size()-1); }
	/** Gets the nth item from the end. */
	public Object getReverse(int index)
		{ return get(size - 1 - index); }

    public Object set(int index, Object element) {
        modCount++;
        rangeCheck(index);
        int convertedIndex = convert(index);
        Object oldValue = array[convertedIndex];
        array[convertedIndex] = element;
        return oldValue;
    }

    public boolean add(Object o) {
        modCount++;
        // We have to have at least one empty space
        ensureCapacity(size + 1 + 1);
        array[tail] = o;
        tail = (tail + 1) % array.length;
        size++;
        return true;
    }

	public boolean addCircular(Object o)
	{
		while (size >= circularSize) remove(0);
		return add(o);
	}

    // This method is the main reason we re-wrote the class.
    // It is optimized for removing first and last elements
    // but also allows you to remove in the middle of the list.
    public Object remove(int index) {
        modCount++;
        rangeCheck(index);
        int pos = convert(index);

        // an interesting application of try/finally is to avoid
        // having to use local variables
        try {
            return array[pos];
        } finally {
            array[pos] = null; // Let gc do its work
            // optimized for FIFO access, i.e. adding to back and
            // removing from front
            if (pos == head) {
                head = (head + 1) % array.length;
            } else if (pos == tail) {
                tail = (tail - 1 + array.length) % array.length;
            } else {
                if ((pos > head) && (pos > tail)) { // tail/head/pos
                    System.arraycopy(array, head, array, head + 1, pos - head);
                    head = (head + 1) % array.length;
                } else {
                    System.arraycopy(array, pos + 1, array, pos, tail - pos -
                        1);
                    tail = (tail - 1 + array.length) % array.length;
                }
            }
            size--;
        }
    }

    public void clear() {
        modCount++;
        // Let gc do its work
        for (int i = 0; i != size; i++) {
            array[convert(i)] = null;
        }
        head = tail = size = 0;
    }

    public boolean addAll(java.util.Collection c) {
        modCount++;
        int numNew = c.size();

        // We have to have at least one empty space
        ensureCapacity(size + numNew + 1);
        java.util.Iterator e = c.iterator();
        for (int i = 0; i < numNew; i++) {
            array[tail] = e.next();
            tail = (tail + 1) % array.length;
            size++;
        }
        return numNew != 0;
    }

    public void add(int index, Object element) {
        if (index == size) {
            add(element);
            return;
        }
        modCount++;
        rangeCheck(index);
        // We have to have at least one empty space
        ensureCapacity(size + 1 + 1);
        int pos = convert(index);
        if (pos == head) {
            head = (head - 1 + array.length) % array.length;
            array[head] = element;
        } else if (pos == tail) {
            array[tail] = element;
            tail = (tail + 1) % array.length;
        } else {
            if ((pos > head) && (pos > tail)) { // tail/head/pos
                System.arraycopy(array, pos, array, head - 1, pos - head + 1);
                head = (head - 1 + array.length) % array.length;
            } else { // head/pos/tail
                System.arraycopy(array, pos, array, pos + 1, tail - pos);
                tail = (tail + 1) % array.length;
            }
            array[pos] = element;
        }
        size++;
    }

    public boolean addAll(int index, java.util.Collection c) {
        boolean result = true;
        Iterator it = c.iterator();
        while (it.hasNext()) {
            result &= this.add(it.next());
        }
        return result;
    }

    // The convert() method takes a logical index (as if head was
    // always 0) and calculates the index within array
    private int convert(int index) {
        return (index + head) % array.length;
    }

    private void rangeCheck(int index) {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " +
                size);
        }
    }

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        s.writeInt(circularSize);
        s.writeInt(size);
        for (int i = 0; i != size; i++) {
            s.writeObject(array[convert(i)]);
        }
    }

    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in size of list and allocate array
		int csize = s.readInt();
		int size = s.readInt();
		setCircularSize(csize);
		for (int i=0; i<size; ++i) addCircular(s.readObject());
		
//        head = 0;
//        size = tail = s.readInt();
//        if (tail < DEFAULT_SIZE) {
//            array = new Object[DEFAULT_SIZE];
//        } else {
//            array = new Object[tail];
//        }

//        // Read in all elements in the proper order.
//        for (int i = 0; i < tail; i++)
//            array[i] = s.readObject();
    }
}

