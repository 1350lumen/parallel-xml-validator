/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Александр
 */
public class EventBlockingQueue<T> extends LinkedBlockingQueue<T> {
    
    
    
    @Override
    public T take() throws InterruptedException{
        return super.take();
    }
    
}
