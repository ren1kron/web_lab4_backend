package org.ren1kron.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        // Разрешаем доступ с любого источника
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Разрешенные методы
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // Разрешенные заголовки
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        // Максимальное время кэширования предзапроса
        responseContext.getHeaders().add("Access-Control-Max-Age", "3600");

        // Разрешение передачи учетных данных (если необходимо)
         responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}
