/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

import java.nio.CharBuffer;

/**
 *
 * @author Александр
 */
public class CharBufferFactory implements Factory<CharBuffer>{

    private int bufferSize;
    public CharBufferFactory(int bufferSize){
        this.bufferSize = bufferSize;
    }
    
    @Override
    public CharBuffer create() {
        return CharBuffer.allocate(bufferSize);
    }
    
    
    
}
