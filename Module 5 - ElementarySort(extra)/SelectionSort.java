public class SelectionSort extends Sort {
    @Override
    public void sort(Comparable[] arr) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            int minIndex = i;
            for(int j = i + 1; j < len; j++) {
                if (less(arr[j], arr[minIndex])) {
                    minIndex = j;
                }
            }
            exchange(arr, i, minIndex);
        }
    }
}
