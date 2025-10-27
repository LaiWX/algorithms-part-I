public class InsertionSort extends Sort{
    @Override
    public void sort(Comparable[] arr) {
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            for (int j = i; j > 0; j--) {
                if (less(arr[j], arr[j-1])) {
                    exchange(arr, j, j-1);
                } else {
                    break;
                }
            }
        }
    }
}
