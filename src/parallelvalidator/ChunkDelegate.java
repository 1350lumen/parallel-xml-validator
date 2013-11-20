/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelvalidator;

import java.nio.CharBuffer;

/**
 *
 * @author Александр
 */
public interface ChunkDelegate {
        void onFirstChunk(CharBuffer b);
        void onNewChunk(CharBuffer b);
        void onLastChunk(CharBuffer b);
        void onAdditionalBytes(CharBuffer cb);
}
