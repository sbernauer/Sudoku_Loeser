package core.imageRecognition;

import java.io.IOException;

public interface ImageRegcognicer {

    int[][] regocniceFromImage(String path) throws IOException, NoSudokuInImageFoundException;

}
