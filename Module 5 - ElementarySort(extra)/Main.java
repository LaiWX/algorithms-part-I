import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int n = 1000;
        Integer[] randomInt = new Integer[n];
        for (int i = 0; i < randomInt.length; i++) {
            randomInt[i] = random.nextInt(n * 100);
        }

        Integer[] toSelectionSort = randomInt.clone();
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(toSelectionSort);
        System.out.println(selectionSort.isSorted(toSelectionSort));

        Integer[] toInsertionSort = randomInt.clone();
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(toInsertionSort);
        System.out.println(insertionSort.isSorted(toInsertionSort));

        Integer[] toShellSort = randomInt.clone();
        ShellSort shellSort = new ShellSort();
        shellSort.sort(toShellSort);
        System.out.println(shellSort.isSorted(toShellSort));
    }
}