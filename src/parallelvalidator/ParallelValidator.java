/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;


/**
 *
 * @author Александр
 */
public class ParallelValidator {

    
    public final static String SAMPLE_PATH = "D:\\Projects\\samples";
    public final static String SCHEMA_NAME = "CustomersAndOrders.xsd";
    public final static String FILE_NAME = "10_000rec.xml";
    
    public final static int BLOCK_SIZE = 4 * 1024; // 4 КB
    public final static int SUB_PARTITION_SIZE = 1024 * 1024;
    /**
     * @param args the command line arguments
     */   
   
    public static void main(String[] args) throws IOException, Exception {    
        
        File source = new File(SAMPLE_PATH, FILE_NAME);  
        
        long fileSize = source.length();
        int workersCount = 3; // Runtime.getRuntime().availableProcessors();
        
        long basePartitionSize = fileSize / workersCount;
        basePartitionSize = basePartitionSize - (basePartitionSize % BLOCK_SIZE);
        
        Map<Integer, Long> partitionOffsets = new TreeMap<>();
        for (int i=0; i< workersCount; i++){
            partitionOffsets.put(i, basePartitionSize * i);
        }                
        //test        
        int currentWorkerId = workersCount - 1;      
        
        List<FragmentComposer> parts = new ArrayList<>();
        
        for(int i=0; i < workersCount; i++){
            parts.add(process(source,  partitionOffsets.get(currentWorkerId)));
        } 
        
        //combine(parts);
    } 
    
    private static void combine(List<FragmentComposer> parts) throws Exception{
             
      
    }
    
    private static FragmentComposer process(File source, Long offset) throws Exception{
        
  
        FragmentComposer builder = new FragmentComposer();
        //ReadTask reader = new ReadTask(builder, 
        //        BLOCK_SIZE, SUB_PARTITION_SIZE);
        
        //reader.read(source, offset);  
        
        return builder;
    }
        
    
        
    
}
