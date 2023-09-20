package a1_1901040226;

public class ArraySorting {
    public static void sort(Comparable[] a) {
        if (a == null) return;
        partialPosition(a, 0, a.length - 1);
    }

    private static void partialPosition(Comparable[] a, int low, int high) {
        if (low >= high)
            return;

        int mid = compare(a, low, high);
        partialPosition(a, low, mid);
        partialPosition(a, mid + 1, high);
    }

    public static int compare(Comparable[] a, int i, int j) {
        Comparable x = a[i];
        while (true) {
            while (a[i].compareTo(x) < 0)
                i++;
            while (a[j].compareTo(x) > 0)
                j--;
            if (i < j) {
                Comparable temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                j--;
                i++;
            } else {
                return j;
            }
        }
    }
}
