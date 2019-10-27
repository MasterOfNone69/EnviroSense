package com.jtjc.EnviroSense.util;

import androidx.annotation.NonNull;

public class Pair<E> {

    public E first, second;

    public Pair(E first, E second) {
        this.first = first;
        this.second = second;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + first.toString() + ", " + second.toString() + ")";
    }
}
