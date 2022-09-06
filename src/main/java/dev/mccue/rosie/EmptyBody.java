package dev.mccue.rosie;

import java.io.OutputStream;

enum EmptyBody implements Body {
    INSTANCE;

    @Override
    public void writeToStream(OutputStream outputStream) {

    }

    @Override
    public String toString() {
        return "EmptyBody[]";
    }
}
