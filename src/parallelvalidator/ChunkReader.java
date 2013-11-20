/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import parallelvalidator.exception.CharBufferOverflow;
import parallelvalidator.exception.MalformedException;

/**
 *
 * @author Александр
 */
public class ChunkReader {

    private static final int IO_BUFFER_SIZE = 1024 * 4;
    private static final int CHAR_BUFFER_SIZE = 1024 * 1024;
   
    private CharsetDecoder  decoder;
    private ByteBuffer      bbuf;
    private CharBuffer      cbuf;
    private Segment         segment;
    private FileChannel     channel;
    private long            processedBytes;
    private BufferProcessor proc;

    public ChunkReader(FileChannel channel, Segment segment, 
            BufferProcessor proc, int ioBufferSize, int charBufferSize ) {
        
        bbuf = ByteBuffer.allocate(ioBufferSize);
        cbuf = CharBuffer.allocate(charBufferSize);
        processedBytes = 0; 
        this.proc      = proc;
        this.segment   = segment;
        this.channel   = channel;
           
        decoder = Charset.forName("UTF-8").newDecoder()
                     .onMalformedInput(CodingErrorAction.REPORT)
                     .onUnmappableCharacter(CodingErrorAction.REPORT);         
    }
    
    public ChunkReader(FileChannel channel, Segment segment, 
            BufferProcessor proc) {
        this(channel, segment, proc, IO_BUFFER_SIZE, CHAR_BUFFER_SIZE);      
    } 
    
    private void positionChannel() throws IOException{
       channel.position(segment.startPosition() +
                              processedBytes + bbuf.position()); 
    }
            
    public boolean readNext() throws IOException, MalformedException {
             
        positionChannel();        
        for (;;) {                 
            long bytesRemained = segment.length() - processedBytes;            
            if (bytesRemained <= 0)
                break;            
            
            int bytesRead = channel.read(bbuf);  
            
            if (-1 == bytesRead) { //EOF               
                if (bytesRemained > 0){
                    processedBytes += decode(bbuf, cbuf, true);
                }                
                break;
            }          
            long overflow = bytesRead - bytesRemained;            
            if ( overflow > 0 ){                
                bbuf.position((int)(bbuf.position() - overflow));                
            }          
            //bbuf.flip();
            processedBytes += decode(bbuf, cbuf, overflow >= 0);
            
            if (cbuf.position() == cbuf.limit()){
                break;
            }
        }        
        processCharBuffer(cbuf);        
        return processedBytes < segment.length();
    }
    
    private void processCharBuffer(CharBuffer cbuf){
        
        cbuf.flip();        
        if (cbuf.limit() > 0){
           proc.process(cbuf);
        }        
        cbuf.clear();
    }
    
    private int decode(ByteBuffer bbuf, CharBuffer cbuf, boolean end) throws MalformedException {
        
        bbuf.flip();
        
        int decodedBytes = 0;         
        CoderResult res = decoder.decode(bbuf, cbuf, end);
        
        if (res.isOverflow() || res.isUnderflow()) {
            
            decodedBytes = bbuf.position();
            bbuf.compact();                            
        
        } else if (res.isError()){               
            throw new MalformedException(segment,
                    processedBytes + bbuf.position(), res.length());
        }   
               
        return decodedBytes;
    }
}
