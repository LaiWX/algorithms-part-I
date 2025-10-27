public class ShellSort extends Sort{
    @Override
    public void sort(Comparable[] arr) {
        int len = arr.length;

        int h = 1;
        while (h < len) {
            h = h * 3 + 1;
        }
        h /= 3;

        while (h >= 1) {
            for (int i = h; i < len; i++) {
                for (int j = i; j - h >=0; j -= h) {
                    if (less(arr[j], arr[j-h])) {
                        exchange(arr, j, j - h);
                    } else {
                        break;
                    }
                }
            }
            h /= 3;
        }
    }
}
