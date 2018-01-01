package core.bruteforcing;

public class BruteForceField {
    public int x;
    public int y;
    private int[] possibleNumbers;
    private int currentNumberIndex = 0;

    public BruteForceField(int x, int y, int[] possibleNumbers) {
        this.x = x;
        this.y = y;
        this.possibleNumbers = possibleNumbers;
    }

    public int getNextNumber() {
        currentNumberIndex++;
        if (currentNumberIndex == possibleNumbers.length) {
            currentNumberIndex = 0;
        }
        return possibleNumbers[currentNumberIndex];
    }
}
