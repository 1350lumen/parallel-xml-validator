/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import concurrent.CharBufferFactory;
import concurrent.ObjectPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Александр
 */
public class ReadTask implements Runnable {
    
    private ChunkDelegate delegate;
    private File source;
    private List<Segment> partitions;      
    private static final int CHUNK_BUFFERS = 10;  
    private static final int CHUNK_BUFFER_SIZE = 1024;
    
    ObjectPool<CharBuffer> pool;
 
    @Override
    public void run(){
        
        try (RandomAccessFile fis = new RandomAccessFile(this.source, "r")) {
            
            //ChunkReader reader = new ChunkReader(fis.getChannel());
        
            
            
        } catch (Exception ex) {
            
        }   
    }
         
    public ReadTask(File source, ChunkDelegate delegate, List<Segment> partitions){
        
        this.delegate = delegate;
        this.source = source;
        this.partitions = partitions;
               
        pool = new ObjectPool(new CharBufferFactory(CHUNK_BUFFER_SIZE), CHUNK_BUFFERS);        
    }
       
    public void readChunk(FileChannel channel) throws InterruptedException{
        CharBuffer cbuf = pool.borrow();
        
        
    }
          
    public CharBuffer read(File source, Long offset) throws Exception {     
        /*
        
        CharBuffer cbuf;// = CharBuffer.allocate(chunkBuffer);
               
        try (RandomAccessFile fis = new RandomAccessFile(source, "r")) {
           
            fis.seek(offset);
            
            FileChannel channel = fis.getChannel();
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
              
            boolean firstChunk = true;
            
            
            
            for(;;) {
                                               
                if(-1 == channel.read(bbuf)) {
                    decoder.decode(bbuf, cbuf, true);
                    decoder.flush(cbuf);
                    break;
                }
                                             
                bbuf.flip();

                CoderResult res = decoder.decode(bbuf, cbuf, false);
                if(CoderResult.OVERFLOW == res) {
                                      
                    if (firstChunk)
                        delegate.onFirstChunk(cbuf);
                    firstChunk = false; 
                    
                    delegate.onNewChunk(cbuf);                    
                    cbuf.clear();                    
                   
                } else if (CoderResult.UNDERFLOW == res) {
                    bbuf.compact();
                }
            }          
        }
        */
        //delegate.onNewChunk(cbuf);  
        //delegate.onLastChunk(cbuf);
        return null;
    }
}
