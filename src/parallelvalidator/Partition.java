/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import java.nio.ByteBuffer;

/**
 *
 * @author Александр
 */
public class Partition {
 
    private ByteBuffer unresolvedStartBytes;
    private ByteBuffer unresolvedEndBytes;
    
    private long offset;
    private long length;
    
    public final static int SEGMENT_LENGTH = 1024 * 1024;
    
    private Segment segment;
    
    public Partition(long offset, long length){
        this.offset = offset;
        this.length = length;
        segment = new Segment(offset, Math.min(SEGMENT_LENGTH, this.length));
    }
    
    public Segment current(){
        return segment;
    }
    
    public void nextSegment(long unresolvedBytes){
       
       //long segmentOffset = segment.limit() - unresolvedBytes;
       //long segmentLength = Math.min(SEGMENT_LENGTH, );
    }
    
    
    
}
