package org.m4water.server.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * 
 */
@XmlRootElement
public class WaterPointsList implements Iterable<Waterpoint> {

    @XmlElement
    private List<Waterpoint> waterPoints;

    public WaterPointsList() {
    }

    public WaterPointsList(List<Waterpoint> waterPoint) {
        this.waterPoints = waterPoint;
    }

    public <T> T[] toArray(T[] a) {
        return waterPoints.toArray(a);
    }

    public Object[] toArray() {
        return waterPoints.toArray();
    }

    public List<Waterpoint> subList(int fromIndex, int toIndex) {
        return waterPoints.subList(fromIndex, toIndex);
    }

    public int size() {
        return waterPoints.size();
    }

    public Waterpoint set(int index, Waterpoint element) {
        return waterPoints.set(index, element);
    }

    public boolean retainAll(Collection<?> c) {
        return waterPoints.retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return waterPoints.removeAll(c);
    }

    public Waterpoint remove(int index) {
        return waterPoints.remove(index);
    }

    public boolean remove(Object o) {
        return waterPoints.remove(o);
    }

    public ListIterator<Waterpoint> listIterator(int index) {
        return waterPoints.listIterator(index);
    }

    public ListIterator<Waterpoint> listIterator() {
        return waterPoints.listIterator();
    }

    public int lastIndexOf(Object o) {
        return waterPoints.lastIndexOf(o);
    }

    public Iterator<Waterpoint> iterator() {
        return waterPoints.iterator();
    }

    public boolean isEmpty() {
        return waterPoints.isEmpty();
    }

    public int indexOf(Object o) {
        return waterPoints.indexOf(o);
    }

    public int hashCode() {
        return waterPoints.hashCode();
    }

    public Waterpoint get(int index) {
        return waterPoints.get(index);
    }

    public boolean equals(Object o) {
        return waterPoints.equals(o);
    }

    public boolean containsAll(Collection<?> c) {
        return waterPoints.containsAll(c);
    }

    public boolean contains(Object o) {
        return waterPoints.contains(o);
    }

    public void clear() {
        waterPoints.clear();
    }

    public boolean addAll(int index, Collection<? extends Waterpoint> c) {
        return waterPoints.addAll(index, c);
    }

    public boolean addAll(Collection<? extends Waterpoint> c) {
        return waterPoints.addAll(c);
    }

    public void add(int index, Waterpoint element) {
        waterPoints.add(index, element);
    }

    public boolean add(Waterpoint e) {
        return waterPoints.add(e);
    }
    
    
}
