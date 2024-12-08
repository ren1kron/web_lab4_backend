// org.ren1kron.resources.LoginResource.java
package org.ren1kron.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.ren1kron.dao.UserDao;
import org.ren1kron.dto.TokenDTO;
import org.ren1kron.dto.UserDTO;
import org.ren1kron.models.User;
import org.ren1kron.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

@Slf4j

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    private UserDao userDao;

    /**
     * Handles user login.
     *
     * @param request LoginRequest containing username and password.
     * @return TokenDTO with JWT token if authentication is successful.
     */
    @POST
    @Path("/login")
    public Response login(@Valid UserDTO request) {
        User user = userDao.findByUsername(request.getUsername());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }

        // Verify the password using BCrypt
        if (!BCrypt.checkpw(request.getPassword(), user.getPasswordHash())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }

        // Generate JWT token
        String token = JwtUtil.generateToken(user.getUsername());

        // Respond with the token
        return Response.ok(new TokenDTO(token)).build();
    }

    /**
     * Handles user registration.
     *
     * @param userDTO UserDTO containing username and password.
     * @return TokenDTO with JWT token if registration is successful.
     */
    @POST
    @Path("/register")
    public Response register(@Valid UserDTO userDTO) {
//    public Response register( UserDTO userDTO) {
        // Check if user already exists
        User existingUser = userDao.findByUsername(userDTO.getUsername());
        if (existingUser != null) {
            log.warn("Registration failed: Username '{}' already exists.", userDTO.getUsername());
            return Response.status(Response.Status.CONFLICT)
                    .entity("Username already exists")
                    .build();
        }

        try {
            // Hash the password
            String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());

            // Create and persist the new user
            User newUser = new User();
            newUser.setUsername(userDTO.getUsername());
            newUser.setPasswordHash(hashedPassword);
            userDao.saveUser(newUser);

            // Generate JWT token
            String token = JwtUtil.generateToken(newUser.getUsername());

            log.info("User '{}' registered successfully.", userDTO.getUsername());

            // Respond with the token
            return Response.status(Response.Status.CREATED)
                    .entity(new TokenDTO(token))
                    .build();
        } catch (Exception e) {
            log.error("Registration failed for user: {}", userDTO.getUsername(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Registration failed due to server error.")
                    .build();
        }
    }
}
