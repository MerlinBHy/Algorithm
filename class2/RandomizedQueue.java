import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by merlin on 17/1/22.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int size;

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
        {
            copy[i] = s[i];
        }
        s = copy;
    }

    public RandomizedQueue()
    {
        s = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public Item sample()
    {
        if (size == 0)
            throw new NoSuchElementException();
        else
        {
            int i = StdRandom.uniform(size);
            return s[i];
        }
    }

    public void enqueue(Item item)
    {
        if (item == null)
            throw new NullPointerException();
        if (size == s.length)
            resize(s.length * 2);
        s[size++] = item;
    }

    public Item dequeue()
    {
        if (size == 0)
            throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item item = s[index];
        s[index] = s[--size];
        s[size] = null;
        if (size > 0 && size == s.length/4)
            resize(s.length/2);
        return item;
    }

    public Iterator<Item> iterator()
    {
        return new ArrayIterator();
    }

    public static void main(String[] args){

    }

    private class ArrayIterator implements Iterator<Item>
    {
        private Item[] items;
        private Item current;
        private int index;

        public ArrayIterator()
        {
            index = 0;
            items = (Item[]) new Object[size];
            for(int i=0; i<size; i++)
                items[i] = s[i];
            StdRandom.shuffle(items);
        }
        public boolean hasNext()
        {
            return index < size;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            Item item = items[index];
            index++;
            return item;
        }
    }
}
