package org.ren1kron.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ren1kron.dao.PointDao;
import org.ren1kron.models.Point;

import java.util.List;

@Path("/points")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {

    @Inject
    private PointDao pointDao;

    @GET
    public Response getPoints() {
        List<Point> points = pointDao.getPoints();
        return Response.ok(points).build();
    }

    @POST
    public Response addPoint(Point point) {
        point.calculate(); // Вычисляем попадание
        pointDao.addPoint(point);
        return Response.status(Response.Status.CREATED).entity(point).build();
    }

    @DELETE
    public Response clearPoints() {
        int deletedCount = pointDao.clear();
        return Response.ok("Deleted " + deletedCount + " points").build();
    }
}
