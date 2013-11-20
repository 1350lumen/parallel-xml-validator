/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

/**
 *
 * @author Александр
 */
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ObjectPool<T> {

    private final BlockingQueue<T> objects;

    public ObjectPool(Factory<T> factory, int count) {
        
        List<T> list = new LinkedList<>();
        for (int i=0; i<count; i++){
            list.add(factory.create());
        }
        this.objects = new ArrayBlockingQueue<>(list.size(), false, list);
    }

    public T borrow() throws InterruptedException {
        return this.objects.take();
    }

    public void giveBack(T object) throws InterruptedException {
        this.objects.put(object);
    }
}