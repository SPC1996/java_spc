package java_spc.algorithm;

public class Strings {
    public static int strlen(char[] str) {
        return str.length;
    }

    public static char[] strcpy(char[] from) {
        char[] to = new char[from.length];
        System.arraycopy(from, 0, to, 0, from.length);
        return to;
    }

    public static int strstr(char[] all, char[] sub) {
        if (sub == null) {
            return -1;
        }
        if (all.length < sub.length) {
            return -1;
        }
        for (int i = 0; i <= all.length - sub.length; i++) {
            boolean flag = true;
            for (int j = 0, p = i; j < sub.length; j++, p++) {
                if (all[p] != sub[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
        return -1;
    }

    public static int kmp(char[] all, char[] sub) {
        int j = -1;
        if (all.length == 0 && sub.length == 0) {
            return 0;
        }
        if (sub.length == 0) {
            return 0;
        }
        int[] next = new int[sub.length];
        compute_prefix(sub, next);
        for (int i = 0; i < all.length; i++) {
            while (j > -1 && sub[j + 1] != all[i]) {
                j = next[j];
            }
            if (sub[j + 1] == all[i]) {
                j++;
            }
            if (j == sub.length - 1) {
                return i - j;
            }
        }
        return -1;
    }

    private static void compute_prefix(char[] sub, int[] next) {
        int j = -1;
        next[0] = j;
        for (int i = 1; i < sub.length; i++) {
            while (j > -1 && sub[j + 1] != sub[i]) {
                j = next[j];
            }
            if (sub[i] == sub[j + 1]) {
                j++;
            }
            next[i] = j;
        }
    }

    public static int boyer_moore(char[] all, char[] sub) {
        int[] right = new int[256];
        int[] gs = new int[sub.length + 1];
        pre_right(sub, right);
        pre_gs(sub, gs);
        int i = sub.length - 1, j = 0;
        while (j <= all.length - sub.length) {
            for (; i >= 0 && sub[i] == all[i + j]; i--) ;
            if (i < 0) {
                return j;
            } else {
                int max = gs[i] > right[all[i + j]] - sub.length + 1 + i ?
                        gs[i] : i - right[all[i + j]];
                j += max;
            }
        }
        return -1;
    }

    private static void pre_right(char[] sub, int[] right) {
        for (int i = 0; i < right.length; i++) {
            right[i] = -1;
        }
        for (int i = 0; i < sub.length; i++) {
            right[sub[i]] = i;
        }
    }

    private static void suffixes(char[] sub, int[] suff) {
        int f = 0, g = sub.length - 1;
        suff[sub.length - 1] = sub.length;
        for (int i = sub.length - 2; i >= 0; i--) {
            if (i > g && suff[i + sub.length - 1 - f] < i - g) {
                suff[i] = suff[i + sub.length - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;
                while (g >= 0 && sub[g] == sub[g + sub.length - 1 - f]) {
                    g--;
                }
                suff[i] = f - g;
            }
        }
    }

    private static void pre_gs(char[] sub, int[] gs) {
        int[] suff = new int[sub.length + 1];
        suffixes(sub, suff);
        for (int i = 0; i < sub.length; i++) {
            gs[i] = sub.length;
        }
        for (int i = sub.length - 1; i >= 0; i--) {
            if (suff[i] == i + 1) {
                for (int j = 0; j < sub.length - 1 - i; j++) {
                    if (gs[j] == sub.length) {
                        gs[j] = sub.length - 1 - i;
                    }
                }
            }
        }
        for (int i = 0; i < sub.length - 1; i++) {
            gs[sub.length - 1 - suff[i]] = sub.length - 1 - i;
        }
    }

//    public static int rabin_karp(char[] all, char[] sub) {
//        long subHash = hash(sub, sub.length);
//        long allHash = hash(all, sub.length);
//        int rm = 1;
//        for (int i = 0; i < sub.length - 1; i++) {
//            rm = rm * 256 % 0xfff1;
//        }
//        for (int i = 0; i < all.length - sub.length; i++) {
//            if (allHash == subHash) {
//                char[] need = new char[all.length - i];
//                System.arraycopy(all, i, need, 0, all.length - i);
//                if (check(sub, need)) {
//                    return i;
//                }
//                allHash = rehash(allHash, all[i], all[i + sub.length], rm);
//            }
//        }
//        return -1;
//    }
//
//    private static long hash(char[] key, int m) {
//        long h = 0;
//        for (int j = 0; j < m; j++) {
//            h = (h * 256 + key[j]) % 0xfff1;
//        }
//        return h;
//    }
//
//    private static long rehash(long h, char first, char next, long rm) {
//        long newh = (h + 0xfff1 - rm * first % 256) % 256;
//        newh = (newh * 256 + next) % 0xfff1;
//        return newh;
//    }
//
//    private static boolean check(char[] sub, char[] substr) {
//        return Arrays.equals(sub, substr);
//    }

    public static int atoi(char[] str) {
        int num = 0, sign = 1, i = 0;
        while (i < str.length && str[i] == ' ') {
            i++;
        }
        if (i < str.length && str[i] == '+') {
            i++;
        }
        if (i < str.length && str[i] == '-') {
            sign = -1;
            i++;
        }
        while (i < str.length) {
            if (str[i] < '0' || str[i] > '9') {
                break;
            }
            if (num > Integer.MAX_VALUE / 10 ||
                    (num == Integer.MAX_VALUE / 10 && str[i] - '0' > Integer.MAX_VALUE % 10)) {
                return sign == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            num = num * 10 + str[i] - '0';
            i++;
        }
        return num * sign;

    }

    public static void display(char[] arr) {
        if (arr == null) {
            System.out.println("null");
        } else {
            for (char c : arr) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
