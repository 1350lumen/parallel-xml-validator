/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import java.nio.CharBuffer;
import parallelvalidator.ChunkParser.ChunkParserDelegate;

/**
 *
 * @author Александр
 */
public class FragmentComposer implements ChunkDelegate, ChunkParserDelegate {
    
    private Fragment fragment = new Fragment();
    
    CharBuffer unresolvedStartChars;
    CharBuffer unresolvedFinalChars;
    
    private ChunkParser parser;
    
    public FragmentComposer(){
        parser = new ChunkParser();
    }

    @Override
    public void onNewChunk(CharBuffer buf) {
        parser.parse(buf, this);
    }

    @Override
    public void onLastChunk(CharBuffer b) {
        if (b.limit() > 0) {
            unresolvedFinalChars = b.subSequence(0, b.limit());
        }
    }
    
    @Override
    public void onFirstChunk(CharBuffer b) {
        
        int i = 0;
        while (b.get(i) != '<' && i < b.limit()) {
            i++;
        }
        if (i > 0 && i < b.length()) {
            unresolvedStartChars = b.subSequence(0, i);
            b.position(i);
            b.compact();
        }
    }
    
    @Override
    public void onAdditionalBytes(CharBuffer cb){
        
        if (cb.limit() > 0 && unresolvedFinalChars.limit() > 0) {

            unresolvedFinalChars.append(cb);
            onNewChunk(unresolvedFinalChars);
            onLastChunk(unresolvedFinalChars);

        } 
    }

    @Override
    public void onStartElement(String element) {
        fragment.addStartElement(element);
    }

    @Override
    public void onEndElement(String element) {
        fragment.addEndElement(element);
    }
}
