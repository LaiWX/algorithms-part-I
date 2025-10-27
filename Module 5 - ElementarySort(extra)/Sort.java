public class Sort {

    public void sort(Comparable[] arr) {

    }

    public void exchange(Comparable[] arr , int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public boolean isSorted(Comparable[] arr) {
        for(int i = 1; i < arr.length; i++) {
            if (less(arr[i], arr[i-1])) {
                return false;
            }
        }
        return true;
    }
}
