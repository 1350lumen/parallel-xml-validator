/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import java.io.CharArrayReader;
import java.nio.CharBuffer;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Александр
 */
public class Fragment  {

    Deque<String> beginElements = new LinkedList<>();
    Deque<String> endElements   = new LinkedList<>();
          
    public boolean append(Fragment f) throws Exception {
       
        boolean wellformed = true;
        
        while (f.beginElements.size() > 0){
            beginElements.push(f.beginElements.pollLast());
        }
        
        while (f.endElements.size()>0 && beginElements.size()>0 && wellformed){            
            wellformed = f.endElements.pop().equals(beginElements.pop());
        } 
        
        if (endElements.size()>0)
            wellformed = false;
        
        return wellformed;
        
    }

    public void addEndElement(String localName) {

        if (beginElements.size()>0 && beginElements.peek().equals(localName)) {
            beginElements.pop();
        } else {
            endElements.push(localName);
        }
    }
    
    public void addStartElement(String element){
        beginElements.push(element);
    }
   
    
}
