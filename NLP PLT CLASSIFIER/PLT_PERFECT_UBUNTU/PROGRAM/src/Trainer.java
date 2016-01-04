package shihab;
import edu.illinois.cs.cogcomp.lbjava.*;
import edu.illinois.cs.cogcomp.lbjava.classify.TestDiscrete;
import edu.illinois.cs.cogcomp.lbjava.learn.BatchTrainer;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;

public class Trainer {
    
    
    PLTlassifier sc = new PLTlassifier();
    String trainFolder, testFolder, modelDir;
    
    
    
    

    public Trainer(String trainFolder, String testFolder) {
        this.trainFolder = trainFolder;
        this.testFolder = testFolder;        
        this.modelDir = "bin/";
        String modelPath = modelDir + "PLTlassifier.lc";
        String lexiconPath = modelDir + "PLTlassifier.lex";       
        sc = new PLTlassifier(modelPath, lexiconPath);
        sc.forget();

    }

    /**
     * Performs the training of the classifier
     * @param numRounds The number of training iterations (change this to -1 to train until convergence)
     */
    public void train(int numRounds) {
        // Temporary files that can be deleted
        String trainExampleFilePath = modelDir + "PLTlassifier.ex";
        String testExampleFilePath = modelDir + "PLTlassifier.test.ex";

        // Give an update every 2000 documents
        int outEvery = 2000;

        BatchTrainer batchTrainer = new BatchTrainer(sc, new myDocumentReader(trainFolder), outEvery);
        sc.setLexicon(batchTrainer.preExtract(trainExampleFilePath));

        BatchTrainer batchTester = new BatchTrainer(sc, new myDocumentReader(testFolder), outEvery, "test set: ");
        sc.setLexicon(batchTester.preExtract(testExampleFilePath));

        batchTrainer.train(numRounds);
        sc.save();

        System.out.println("Testing PLTlassifier, final performance: ");
        //overall precision, overall recall, F1, and accuracy.
        TestDiscrete simpleTest = new TestDiscrete();

        Parser testParser = batchTester.getParser();
        TestDiscrete.testDiscrete(simpleTest, sc, null, testParser, true, 0);
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("usage: $>Trainer <train-folder> <test-folder> <training rounds>");
            System.exit(-1);
        }
        Trainer trainer = new Trainer(args[0], args[1]);
        trainer.train(Integer.parseInt(args[2]));
    }
}

