package ocp.service.po;

import java.util.List;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ocp.service.po.model.*;

@Path("/orders")
public interface POResource
{
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response createOrder(PurchaseOrder order, @Context UriInfo uriInfo);

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   // @Formatted
   public PurchaseOrders getOrders(
	@QueryParam("start") int start,
        @QueryParam("size") @DefaultValue("10") int size,
	@QueryParam("item") String item,
	@Context UriInfo uriInfo);

   @GET
   @Path("{id}")
   // @Produces("application/json)
   @Produces(MediaType.APPLICATION_JSON)
   // @Formatted
   public PurchaseOrder getOrder(@PathParam("id") long id);
}
