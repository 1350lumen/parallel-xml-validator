/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import parallelvalidator.exception.MalformedException;

public class ChunkReaderTest {
    
    private static final String FIXTURE_FOLDER = "fixture/";
    private static final String FIXTURE = FIXTURE_FOLDER + "utf8sequence";
    
    private static final String EXPECTED = "123456789€123456789"; 
    private BufferProcessorImpl proc = new BufferProcessorImpl();
    
    public ChunkReaderTest() {
    }
       
    @Before
    public void setUp() {
         proc.reset();
    }
    
    @After
    public void tearDown() {
    }
    
    class BufferProcessorImpl implements BufferProcessor{

            private String fullString = new String();
            
            @Override
            public void process(CharBuffer cbuf) {
                fullString = fullString.concat(cbuf.toString());
            }
            
            public String getResult(){
                return fullString;
            }  
            
            public void reset(){
                fullString = new String();
            }
        }

    @Test
    public void canReadWholeFileSegment() {
                
        try (RandomAccessFile fis = new RandomAccessFile(FIXTURE, "r")) {           
            
            Segment whole = new Segment(0, fis.length());            
            ChunkReader reader = new ChunkReader(fis.getChannel(), whole, 
                    proc, 10, 30);   
            reader.readNext();              
            assertEquals(EXPECTED, proc.getResult()); 
            
        } catch (Exception ex) {  
            fail(ex.getMessage());
        }   
    }
    
    @Test
    public void throwsExceptionIfUnderflowBytesInSegmentEnd(){
        
        try (RandomAccessFile fis = new RandomAccessFile(FIXTURE, "r")) {           
                     
            Segment s = new Segment(5, 5);            
            ChunkReader reader = new ChunkReader(fis.getChannel(), s, 
                    proc, 8, 10);   
            reader.readNext();         
            fail(); 
            
        } catch (MalformedException e) {  
            assertEquals(e.getLength(), 1);
            assertEquals(e.getRelativeStart(), 4);
        } catch (Exception ex){
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void throwsExceptionIfUnderflowBytesInSegmentStart(){
        
        try (RandomAccessFile fis = new RandomAccessFile(FIXTURE, "r")) {           
                    
            Segment s = new Segment(11, 5);            
            ChunkReader reader = new ChunkReader(fis.getChannel(), s, proc, 5, 10);   
            reader.readNext();         
            fail(); 
            
        } catch (MalformedException e) {  
            assertEquals(e.getLength(), 1);
            assertEquals(e.getRelativeStart(), 0);
        } catch (Exception ex){
            fail(ex.getMessage());
        }
    }
    
    @Test
    @SuppressWarnings("empty-statement")
    public void canReadWithSmallCharBuffer(){
        
        try(RandomAccessFile fis = new RandomAccessFile(FIXTURE, "r")) {           
                     
            Segment s = new Segment(0, fis.length());            
            ChunkReader reader = new ChunkReader(fis.getChannel(), s, proc, 8, 8); 
            while(reader.readNext());     
                     
        }catch(Exception ex){
            fail(ex.getMessage());
        }
        
        assertEquals(EXPECTED, proc.getResult()); 
    }
}