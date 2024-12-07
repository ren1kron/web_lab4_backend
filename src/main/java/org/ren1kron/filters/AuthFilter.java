package org.ren1kron.filters;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Пропускаем /login без авторизации
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("login")) {
            return;
        }

        // Проверяем заголовок Authorization
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                            .entity("Missing or invalid Authorization header")
                            .build()
            );
            return;
        }

        String token = authHeader.substring("Bearer ".length());
        // Здесь нужно проверить токен. Если вы используете JWT - проверить подпись, истечение срока и т.д.
        // Если у вас просто строка, вы можете проверять, например, находится ли она в кэше допустимых токенов.

        // Для упрощения допустим, что любой токен, начинающийся на "simpletoken" - ок.
        if (!token.startsWith("simpletoken")) {
            requestContext.abortWith(
                    jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                            .entity("Invalid token")
                            .build()
            );
        }

        // Если бы требовалась роль, можно было бы сохранять SecurityContext с информацией о пользователе.
    }
}
