package org.ren1kron.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ren1kron.dao.PointDao;
import org.ren1kron.dao.UserDao;
import org.ren1kron.models.Point;
import org.ren1kron.models.User;
import org.ren1kron.util.JwtUtil;

import java.util.List;

@Path("/points")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {

    @Inject
    private PointDao pointDao;

    @Inject
    private UserDao userDao;

    @Context
    private HttpHeaders headers;

    /**
     * Extracts token from Authorization header
     * @return Current user
     */
    private User getCurrentUser() {
        String authHeader = headers.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new WebApplicationException("Unauthorized", Response.Status.UNAUTHORIZED);
        }
        String token = authHeader.substring("Bearer ".length());
        String username = JwtUtil.extractUsername(token);

        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException("User not found", Response.Status.UNAUTHORIZED);
        }
        return user;
    }

    @GET
    public Response getPoints() {
        User currentUser = getCurrentUser();
        List<Point> points = pointDao.getPointsByUser(currentUser);
        return Response.ok(points).build();
    }

    @POST
    public Response addPoint(Point point) {
        User currentUser = getCurrentUser();
        point.calculate();
        point.setUser(currentUser);
        pointDao.addPoint(point);
        return Response.status(Response.Status.CREATED).entity(point).build();
    }

    @DELETE
    public Response clearPoints() {
        User currentUser = getCurrentUser();
        int deletedCount = pointDao.clearUserPoints(currentUser);
        return Response.ok("Deleted " + deletedCount + " points").build();
    }
}
