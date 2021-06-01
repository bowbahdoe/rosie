package dev.mccue.rosie;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.eclipse.jetty.server.handler.AbstractHandler;

public final class JettyHandler extends AbstractHandler {
    private final Handler handler;

    public JettyHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(
            String target,
            org.eclipse.jetty.server.Request baseRequest,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        if (baseRequest.getDispatcherType() != DispatcherType.ERROR) {
            final var request = Request.fromHttpServletRequest(
                    httpServletRequest
            );
            final var response = this.handler.handle(request).intoResponse();
            response.writeTo(response, httpServletResponse);
            baseRequest.setHandled(true);
        }
    }
}

