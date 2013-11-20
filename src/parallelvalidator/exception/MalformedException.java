/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator.exception;

import parallelvalidator.Segment;

/**
 *
 * @author Александр
 */
public class MalformedException extends Exception {
    
    private Segment s;
    private long relStart;
    private long length;
    
    public MalformedException(Segment s, long relStart, long length){
        this.s = s;
        this.relStart = relStart;
        this.length = length;
    }

    /**
     * @return the s
     */
    public Segment getSegment() {
        return s;
    }

    /**
     * @return the relStart
     */
    public long getRelativeStart() {
        return relStart;
    }

    /**
     * @return the length
     */
    public long getLength() {
        return length;
    }
}
