/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

/**
 *
 * @author Александр
 */
public class Segment {
    private long start;
    private long length;
    
    public Segment(long start, long length){
        this.start = start;
        this.length = length;
    }
    
    public long length(){
        return length;
    }
    
    public long endPosition(){
        return start + length;
    }
    
    public long startPosition(){
       return start;
    }
    
}
