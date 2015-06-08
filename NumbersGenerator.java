public class NumbersGenerator {
    public static int[] getNumbers(int start, int total) {
        int N = total / 2;
        int[] numbers = new int[N];
        int k = start;
        for (int i = 0; i < N; i++) {
            if (k > total)
                k -= total;
            if (!(k == total && start == 1))
                numbers[i] = k;
            k += 2;
        }
        return numbers;
    }

    public static void main(String[] args) {
        for(int i : getNumbers(2, 4))
            System.out.printf("%d, ", i);
    }
}
