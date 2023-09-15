package uk.ac.cam.yhc49.prejava.ex2;

class PackedLong {
    public static long set(long v, int i, boolean val) {
        if (val) {
            v |= 1L << i;
        } else {
            v &= ~(1L << i);
        }
        return v;
    }

    public static boolean get(long v, int i) {
        return (v >> i & 1L) != 0;
    }

}