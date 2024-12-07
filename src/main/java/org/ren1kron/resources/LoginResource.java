package org.ren1kron.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ren1kron.dao.UserDao;
import org.ren1kron.dto.LoginRequest;
import org.ren1kron.dto.LoginResponse;
import org.ren1kron.models.User;

// TODO убрать эти комменты перед коммитом
// Для хеширования пароля можно использовать BCrypt, но для примера допустим, что пароль не захеширован.
// В продакшене обязательно храните хэш!
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    private UserDao userDao;

    @POST
    public Response login(LoginRequest request) {
        User user = userDao.findByUsername(request.getUsername());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }

        // Сравниваем пароль (на деле должен быть хэш)
        // Для теста (пока без хеша): if (!user.getPasswordHash().equals(request.getPassword())) ...
        // В будущем:
        // if (!BCrypt.verifyer().verify(request.getPassword().toCharArray(), user.getPasswordHash()).verified) ...

        if (!user.getPasswordHash().equals(request.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }

        // Генерируем простой токен (в реальности лучше JWT)
        // Можно сгенерировать случайную строку, но лучше JWT.
        String token = "simpletoken-" + System.currentTimeMillis() + "-" + user.getUsername();

        // В реальном приложении:
        // - JWT: использовать библиотеку (например, jjwt) и подписывать токен секретом.
        // - Сохранить токен на сервере (в кэше или БД) или использовать JWT без сохранения.

        // Для упрощения сейчас просто вернем token.
        return Response.ok(new LoginResponse(token)).build();
    }
}
