package com.bonus.bonus04;

import java.io.Serializable;
import java.util.*;

/* Свой список
Посмотреть, как реализован LinkedList.
Элементы следуют так: 1->2->3->4  и так 4->3->2->1
По образу и подобию создать Solution.
Элементы должны следовать так:
1->3->7->15
    ->8...
 ->4->9
    ->10
2->5->11
    ->12
 ->6->13
    ->14
Удалили 2 и 9
1->3->7->15
    ->8
 ->4->10
Добавили 16,17,18,19,20 (всегда добавляются на самый последний уровень к тем элементам, которые есть)
1->3->7->15
       ->16
    ->8->17
       ->18
 ->4->10->19
        ->20
Удалили 18 и 20
1->3->7->15
       ->16
    ->8->17
 ->4->10->19
Добавили 21 и 22 (всегда добавляются на самый последний уровень к тем элементам, которые есть.
Последний уровень состоит из 15, 16, 17, 19. 19 последний добавленный элемент, 10 - его родитель.
На данный момент 10 не содержит оба дочерних элемента, поэтому 21 добавился к 10. 22 добавляется в следующий уровень.)
1->3->7->15->22
       ->16
    ->8->17
 ->4->10->19
        ->21

Во внутренней реализации элементы должны добавляться по 2 на каждый уровень
Метод getParent должен возвращать элемент, который на него ссылается.
Например, 3 ссылается на 7 и на 8, т.е.  getParent("8")=="3", а getParent("13")=="6"
Строки могут быть любыми.
При удалении элемента должна удаляться вся ветка. Например, list.remove("5") должен удалить "5", "11", "12"
Итерироваться элементы должны в порядке добавления
Доступ по индексу запрещен, воспользуйтесь при необходимости UnsupportedOperationException
Должно быть наследование AbstractList<String>, List<String>, Cloneable, Serializable
Метод main в тестировании не участвует
*/
public class Solution extends AbstractList<String> implements List<String>, Cloneable, Serializable
{
    public static void main(String[] args)
    {
        //Тест 1
//        List<String> list = new Solution();
//        for (int i = 1; i < 16; i++)
//        {
//            list.add(String.valueOf(i));
//        }
//        System.out.println("Expected 3, actual is " + ((Solution) list).getParent("8"));
//        list.remove("5");
//        System.out.println("Expected null, actual is " + ((Solution) list).getParent("11"));

//        System.out.println(list);
//        Мои тесты
//        list.remove("2");
//        list.remove("9");
//        for (int i = 16; i < 21; i++)
//        {
//            list.add(String.valueOf(i));
//        }
//
//        list.remove("18");
//        list.remove("20");
//        list.add("21");
//        list.add("22");
//
//        System.out.println(((Solution) list).getParent("22"));
//        System.out.println(list);
//        System.out.println(list.size());


        //Тест 2
        System.out.println("===========Adding all 1=============");
        List<String> list = new Solution();
        for (int i = 1; i < 15; i++)
        {
            list.add("1");
        }
        System.out.println(list);
        System.out.println("List is empty? " + list.isEmpty());
        System.out.println("List size is " + list.size() + ", expected 14");
        list.remove("1");
        System.out.println("==========After removing 1===========");
        System.out.println(list);
        System.out.println("List is empty? " + list.isEmpty());
        System.out.println("List size is " + list.size() + ", expected 0");

        System.out.println("=========Adding 1,2 & all 1===========");
        list.add("1");
        list.add("2");
        for (int i = 1; i < 13; i++)
        {
            list.add("1");
        }
        System.out.println(list);
        System.out.println("List is empty? " + list.isEmpty());
        System.out.println("List size is " + list.size() + ", expected 14");
        list.remove("1");
        System.out.println("==========After removing 1===========");
        System.out.println(list);
        System.out.println("List is empty? " + list.isEmpty());
        System.out.println("List size is " + list.size() + ", expected 1");
    }

    public String getParent(String value)
    {
        Node<String> node = root;
        for (int i = 0; i < size; i++)
        {
            Node<String> next = node.next;
            node = next;
            if (value.equals(next.item))
            {
                if (i == 0) return null;
                else
                {
                    return next.parent.item;
                }
            }
        }

        return null;
    }

    private int size = 0;

    private Node<String> root = new Node<>(null, null, null, null);
    private Node<String> first;
    private Node<String> last;

    Node<String> node(int index)
    {
        if (index < (size >> 1))
        {
            Node<String> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else
        {
            Node<String> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    private static class Node<S>
    {
        String item;
        Node<String> prev;
        Node<String> next;

        Node<String> parent;
        Node<String> childLeft;
        Node<String> childRight;

        Node(Node<String> prev, String element, Node<String> next, Node<String> parent)
        {
            this.item = element;
            this.prev = prev;
            this.next = next;
            this.parent = parent;
        }
    }

    void linkLast(String s)
    {
        if (size == 0)
        {
            Node<String> node = new Node<>(null, s, null, root);

            root.childLeft = node;
            root.next = node;
            first = node;
            last = node;

        } else
        {
            Node<String> parent = last.parent;
            Node<String> newNode = new Node<>(last, s, null, null);
            last.next = newNode;

            nodeAdder(parent, newNode);

            last = newNode;
        }
        size++;
        modCount++;
    }

    private void nodeAdder(Node<String> parent, Node<String> newNode)
    {
        //Проверяем, есть ли ветви у узла
        if (null == parent.childLeft)
        {
            newNode.parent = parent;
            parent.childLeft = newNode;
        } else if (null == parent.childRight)
        {
            newNode.parent = parent;
            parent.childRight = newNode;
        } else
        {
            parent = parent.next;
            nodeAdder(parent, newNode);
        }
    }

    @Override
    public boolean remove(Object o)
    {
        boolean flag = false;

        if (o == null)
        {
            for (Node<String> x = first; x != null; x = x.next)
            {
                if (x.item == null)
                {
                    unlink(x);
                    flag = true;
                }
            }
        } else
        {
            for (Node<String> x = first; x != null; x = x.next)
            {
                if (o.equals(x.item))
                {
                    unlink(x);
                    flag = true;

                    if (this.contains(o))
                    {
                        remove(o);
                    }
                }
            }
        }
        return flag;
    }

    private String unlink(Node<String> node)
    {
        String item = node.item;
        //Удаляем свзь между узлом и его родителем.
        //Если узел - левая ветвь, то замещаем ее правой.
        //Если правая - обнуляем ее ссылку.
        Node<String> parent = node.parent;
        if (node == parent.childLeft)
        {
            parent.childLeft = parent.childRight;
            parent.childRight = null;
        } else if (node == parent.childRight)
        {
            parent.childRight = null;
        }

        Node<String> prev = node.prev;
        Node<String> next = node.next;
        if (prev == null)
        {
            first = next;
        } else
        {
            prev.next = next;
            node.prev = null;
        }

        if (next == null)
        {
            last = prev;
        } else
        {
            next.prev = prev;
            node.next = null;
        }

        Node<String> childLeft = node.childLeft;
        Node<String> childRight = node.childRight;
        if (childLeft != null)
            unlink(childLeft);
        if (childRight != null)
            unlink(childRight);

        node.item = null;
        size--;
        modCount++;
        return item;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean add(String s)
    {
        linkLast(s);
        return true;
    }

    @Override
    public void clear()
    {
        for (Node<String> x = first; x != null; )
        {
            Node<String> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x.childLeft = null;
            x.childRight = null;
            x.parent = null;
            x = next;
        }
        first = last = null;
        size = 0;
        modCount++;
    }

    @Override
    public String get(int index)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o)
    {
        int index = 0;
        if (o == null)
        {
            for (Node<String> x = first; x != null; x = x.next)
            {
                if (x.item == null)
                    return index;
                index++;
            }
        } else
        {
            for (Node<String> x = first; x != null; x = x.next)
            {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        int index = size;
        if (o == null)
        {
            for (Node<String> x = last; x != null; x = x.prev)
            {
                index--;
                if (x.item == null)
                    return index;
            }
        } else
        {
            for (Node<String> x = last; x != null; x = x.prev)
            {
                index--;
                if (o.equals(x.item))
                    return index;
            }
        }
        return -1;
    }

    private class ListItr implements ListIterator<String>
    {
        private Node<String> lastReturned;
        private Node<String> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        ListItr(int index)
        {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        public boolean hasNext()
        {
            return nextIndex < size;
        }

        public String next()
        {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious()
        {
            return nextIndex > 0;
        }

        public String previous()
        {
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex()
        {
            return nextIndex;
        }

        public int previousIndex()
        {
            return nextIndex - 1;
        }

        public void remove()
        {
            checkForComodification();
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<String> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            expectedModCount++;
        }

        public void set(String e)
        {
            if (lastReturned == null)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.item = e;
        }

        public void add(String e)
        {

        }

        final void checkForComodification()
        {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public Iterator<String> iterator()
    {
        return new ListItr(0);
    }

    @Override
    public ListIterator<String> listIterator()
    {
        return new ListItr(0);
    }

    @Override
    public ListIterator<String> listIterator(int index)
    {
        return new ListItr(0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Solution clone = (Solution) super.clone();

        clone.first = clone.last = null;
        clone.size = 0;
        clone.modCount = 0;

        for (Node<String> x = first; x != null; x = x.next)
            clone.add(x.item);

        return clone;
    }

    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException
    {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out size
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (Node<String> x = first; x != null; x = x.next)
            s.writeObject(x.item);
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException
    {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in size
        int size = s.readInt();

        // Read in all elements in the proper order.
        for (int i = 0; i < size; i++)
            linkLast((String) s.readObject());
    }
}
