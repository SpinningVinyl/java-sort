/** @author Pavel Urusov, me@pavelurusov.com
 * My implementation of various sorting algorithms in Java.
 * License: CC BY-NC-SA 4.0 https://creativecommons.org/licenses/by-nc-sa/4.0/
 */

public class Sort {

    // introspective sort
    public static void introSort(int[] arr) {
        introSort(arr, false);
    }
    public static void introSort(int[] arr, boolean descending) {
        if (arr != null && arr.length > 1) {
            int maxDepth = (int) Math.floor(Math.log(arr.length) * 2);
            iSort(arr, maxDepth, 0, arr.length - 1);
            if (descending) {
                reverseArray(arr);
            }
        }
    }
    private static void iSort(int[] arr, int maxDepth, int low, int high) {
        if (maxDepth == 0) { // switch to heapsort when recursion depth exceeds 2*log(arr.length)
            heapSort(arr);
        } else { // begin with quicksort
            if (low < high) {
                int p = divide(arr, low, high);
                iSort(arr, maxDepth - 1, low, p - 1);
                iSort(arr, maxDepth - 1, p + 1, high);
            }
        }
    }

    // ye olde selection sort
    public static void selectionSort(int[] arr) {
        selectionSort(arr, false);
    }
    public static void selectionSort(int[] arr, boolean descending) {
        if (arr != null && arr.length > 1) {
            int i, j;
            int n = arr.length;
            for (i = 0; i < n - 1; i++) {
                int jMin = i;
                for (j = i + 1; j < n; j++) {
                    if(arr[j] < arr[jMin]) {
                        jMin = j;
                    }
                }
                if (jMin != i) {
                    swap(arr, i, jMin);
                }
            }
            if (descending) {
                reverseArray(arr);
            }
        }
    }

    // ye olde insertion sort
    public static void insertionSort(int[] arr) {
        insertionSort(arr, false);
    }
    public static void insertionSort(int[] arr, boolean descending) {
        if (arr != null && arr.length > 1) {
            int i = 1;
            while (i < arr.length) {
                int x = arr[i];
                int j = i - 1;
                while (j >= 0 && arr[j] > x) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                arr[j + 1] = x;
                i = i + 1;
            }
            if (descending) {
                reverseArray(arr);
            }
        }
    }

    // ye olde bubble sort (optimized)
    public static void bubbleSort(int[] arr) {
        bubbleSort(arr, false);
    }
    public static void bubbleSort(int[] arr, boolean descending) {
        if (arr != null && arr.length > 1) {
            boolean sorted = false;
            int n = arr.length;
            while (!sorted) {
                sorted = true;
                for (int i = 1; i < arr.length; i++) {
                    if(arr[i - 1] > arr[i]) {
                        swap(arr, i - 1, i);
                        sorted = false;
                    }
                }
                n = n - 1;
            }
            if (descending) {
                reverseArray(arr);
            }
        }
    }

    // bottom-up merge sort
    // based on the description in "Java for Everyone: Late Objects",
    // 2nd Edition, by Cay Horstmann
    public static void mergeSort(int[] arr) {
        mergeSort(arr, false);
    }
    public static void mergeSort(int[] arr, boolean descending) {
        mSort(arr);
        if(descending) {
            reverseArray(arr);
        }
    }
    private static void mSort(int[] arr) {
        if (arr != null && arr.length > 1) {
            int[] left = new int[arr.length / 2];
            int[] right = new int[arr.length - left.length];
            System.arraycopy(arr, 0, left, 0, left.length);
            System.arraycopy(arr, left.length, right, 0, right.length);
            mSort(left);
            mSort(right);
            merge(left, right, arr);
        }
    }
    private static void merge(int[] a, int[] b, int[] c) {
        int indexA = 0;
        int indexB = 0;
        int indexC = 0;
        while (indexA < a.length && indexB < b.length) {
            if(a[indexA] < b[indexB]) {
                c[indexC] = a[indexA];
                indexA++;
            } else {
                c[indexC] = b[indexB];
                indexB++;
            }
            indexC++;
        }
        while (indexA < a.length) {
            c[indexC] = a[indexA];
            indexA++;
            indexC++;
        }
        while (indexB < b.length) {
            c[indexC] = b[indexB];
            indexB++;
            indexC++;
        }
    }

    // quicksort using Lomuto's partition and Wirth's median-of-three pivot choice
    public static void quickSort(int[] arr) {
        quickSort(arr, false);
    }
    public static void quickSort(int[] arr, boolean descending) {
        if (arr != null && arr.length > 1) {
            qSort(arr, 0, arr.length - 1);
            if (descending) {
                reverseArray(arr);
            }
        }
    }
    private static void qSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = divide(arr, low, high);
            qSort(arr, low, p - 1);
            qSort(arr, p + 1, high);
        }
    }
    private static int divide(int[] arr, int low, int high) {
        int mid = (low + high) / 2;
        if (arr[mid] < arr[low]) {
            swap(arr, low, mid);
        }
        if (arr[high] < arr[low]) {
            swap(arr, low, high);
        }
        if (arr[mid] < arr[high]) {
            swap(arr, mid, high);
        }
        int pivot = arr[high];
        int i = low;
        for (int j = low; j <= high; j ++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    // heapsort using Floyd's heap
    public static void heapSort(int[] arr) {
        heapSort(arr, false);
    }
    public static void heapSort(int[] arr, boolean descending) {
        if (arr != null && arr.length > 1) {
            hSort(arr, arr.length);
            if (descending) {
                reverseArray(arr);
            }
        }
    }
    private static void hSort(int[] arr, int count) {
        heapify(arr, count);
        int end = count - 1;
        while (end > 0) {
            swap(arr, end, 0);
            end--;
            siftDown(arr, 0, end);
        }
    }
    private static void heapify(int[] arr, int count) {
        int start = iParent(count - 1);
        while (start >= 0) {
            siftDown(arr, start, count - 1);
            start--;
        }
    }
    private static void siftDown(int[] arr, int start, int end) {
        int root = start;
        while (iLeftChild(root) <= end) {
            int child = iLeftChild(root);
            int swapIndex = root;
            if (arr[swapIndex] < arr[child]) {
                swapIndex = child;
            }
            if ((child + 1 <= end) && (arr[swapIndex] < arr[child + 1])) {
                swapIndex = child + 1;
            }
            if (swapIndex == root) {
                return;
            } else {
                swap(arr, root, swapIndex);
                root = swapIndex;
            }
        }
    }
    private static int iParent(int i) {
        return (i - 1) / 2;
    }
    private static int iLeftChild(int i) {
        return 2 * i + 1;
    }

    // various private methods
    private static void reverseArray(int[] arr) {
        int[] reverseArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reverseArray[i] = arr[arr.length - 1 - i];
        }
        System.arraycopy(reverseArray, 0, arr, 0, arr.length);
    }
    private static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }
}