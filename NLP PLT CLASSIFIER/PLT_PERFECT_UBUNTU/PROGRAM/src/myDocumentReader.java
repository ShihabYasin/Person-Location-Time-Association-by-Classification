package shihab;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;
import edu.illinois.cs.cogcomp.lbjava.*;
 
/**
 * Reads documents, given a directory
 */
public class myDocumentReader implements Parser {
   
    public List files;
    public int currentFileId=0;
   
    public myDocumentReader(String directory) {
        File d = new File(directory);
       
        if (!d.exists()) {
            System.err.println(directory + " does not exist!");
            System.exit(-1);
        }
       
        if (!d.isDirectory()) {
            System.err.println(directory + " is not a directory!");
            System.exit(-1);
        }
       
        files = new ArrayList();
        for (File f : d.listFiles()) {
            if (f.isDirectory()) {
                files.addAll(Arrays.asList(f.listFiles()));
            }
        }
       
        Collections.shuffle(files);
        currentFileId = 0;
    }
   
    public void close() {
    }
   
    /**
    * Notice that this relies on the files having the label in their paths, as in
    *        data/spam/train/[label]/[filename].txt
    */
    @Override
    public Object next() {
        if (currentFileId < files.size()) {
            File file = (File) files.get(currentFileId++);
          
                return new myDocument(file);
            } 
            else {
            return null;
        }
    }
   
    public void reset() {
        currentFileId = 0;
    }
}
