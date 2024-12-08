package org.ren1kron.filters;

import io.jsonwebtoken.JwtException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.ren1kron.util.JwtUtil;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Allow /login and /register without authentication
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("login") || path.startsWith("register")) {
            return;
        }

        // Check the Authorization header
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Missing or invalid Authorization header")
                            .build()
            );
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            // Validate the token
            JwtUtil.validateToken(token);
            // Optionally, we can extract user information and set it in the security context
        } catch (JwtException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid or expired token")
                            .build()
            );
        }
    }
}
