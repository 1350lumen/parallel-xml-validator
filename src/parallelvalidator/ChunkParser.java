/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import java.io.CharArrayReader;
import java.nio.CharBuffer;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Александр
 */
public class ChunkParser {
    
    public interface ChunkParserDelegate {
        void onStartElement(String element);
        void onEndElement(String element);
    }
        
    public void parse(CharBuffer cb, ChunkParserDelegate delegate){
        int offset = 0;

        CharArrayReader r = new CharArrayReader(cb.array());
        WstxInputFactory f = new WstxInputFactory();
        f.setProperty(WstxInputProperties.P_INPUT_PARSING_MODE, WstxInputProperties.PARSING_MODE_FRAGMENT);

        try {
            XMLEventReader reader = f.createXMLEventReader(r);
            while (reader.hasNext()) {
                XMLEvent ev = reader.nextEvent();
                offset = ev.getLocation().getCharacterOffset();

                switch (ev.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        delegate.onStartElement(ev.asStartElement().getName().getLocalPart());
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        delegate.onEndElement(ev.asEndElement().getName().getLocalPart());
                        break;
                }
            }
        } catch (XMLStreamException e) {
        }

        cb.position(offset);
        cb.compact();
    }
    
}
