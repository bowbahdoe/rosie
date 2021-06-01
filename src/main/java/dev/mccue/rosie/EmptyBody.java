package dev.mccue.rosie;

import jakarta.servlet.http.HttpServletResponse;

final class EmptyBody extends Body {
    static EmptyBody INSTANCE = new EmptyBody();

    private EmptyBody() {}

    @Override
    void writeTo(HttpServletResponse response) {

    }

    @Override
    public String toString() {
        return "EmptyBody[]";
    }
}
