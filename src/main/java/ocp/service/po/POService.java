package ocp.service.po;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import ocp.service.po.model.PurchaseOrder;
import ocp.service.po.model.PurchaseOrders;

/**
Uses JPA to inject persistence context into this JAX-RS resource class.
Can use CDI for injecting the EM into this JAX-RS root resource class.
*/
@Stateless
@Path("/order")
// @RequestScoped
public class POService implements POResource {

	private static final Logger logObj = Logger.getLogger(POService.class.getName());

	@PersistenceContext(unitName = "orders")
	// @Inject
	private EntityManager em;

	@Path("/create")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
   	public Response createOrder(PurchaseOrder order, @Context UriInfo uriInfo) {
		em.persist(order);
		em.flush();

		logObj.info("Created PO : " + order.getId());
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
      		builder.path(Long.toString(order.getId()));
      		return Response.created(builder.build()).build();
	}

        @GET
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public PurchaseOrder getOrder(@PathParam("id") long id) {
		logObj.info("PathParams: id=" + id);
		return(em.find(PurchaseOrder.class,id));
	}

	@Path("/list")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
	public PurchaseOrders getOrders(
		@QueryParam("start") int start,
		@QueryParam("size") @DefaultValue("10") int size,
		@QueryParam("item") String item,
		@Context UriInfo uriInfo) {

		logObj.info("QueryParams: start=" + start +
			", size=" + size + ", item=" + item);

		Query query = null;
		if (item != null) {
			query = em.createQuery(
			"select o from PurchaseOrder o where o.item=:item");
		 	query.setParameter("item", item);
		}
		else 
			query = em.createQuery("select o from PurchaseOrder o");

		List<PurchaseOrder> orders = null;
		orders = query.
			setFirstResult(start).
			setMaxResults(size).
			getResultList();

		PurchaseOrders pos = new PurchaseOrders();
		List<Link> links = new ArrayList<Link>();
		if ( orders.size() != 0 ) {
			pos.setOrders(orders);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();

			// If the size returned is equal then assume there is a next
			if (orders.size() == size) {
         			int next = start + size;
         			URI nextUri = builder.clone().build(next, size);
         			Link nextLink = Link.fromUri(nextUri)
                             		.rel("next")
                             		.type(MediaType.APPLICATION_JSON).build();
         			links.add(nextLink);
      			};

			// previous link
			if (start > 0) {
				int previous = start - size;
			 	if (previous < 0) previous = 0;
				URI previousUri = 
					builder.clone().build(previous, size);
				Link previousLink = Link.fromUri(previousUri)
					.rel("previous")
					.type(MediaType.APPLICATION_JSON).build();
		 		links.add(previousLink);
			};
      			pos.setLinks(links);
		};

		return(pos);
	}

	@Path("/update/{id}")
    	@PUT
    	public PurchaseOrder update(
		@PathParam("id") long id,
                @QueryParam("price") Float price,
                @QueryParam("quantity") Integer quantity,
                @QueryParam("discount") String discount,
                @QueryParam("origin") String origin,
                @QueryParam("description") String description) {
	
		logObj.info("QueryParams: id=" + id +
			", price=" + price + ", quantity=" + quantity +
			", discount=" + discount + ", origin=" + origin +
			", description=" + description);

		PurchaseOrder order = getOrder(id);
		if (order == null) {
            		throw new IllegalArgumentException("Order ID=" + id + ", not found");
        	};
		order.setPrice(price);
		order.setQuantity(quantity);
		order.setDcode(discount);
		order.setOrigin(origin);
		order.setDescription(description);

        	return em.merge(order);
	}

	@Path("{id}")
	@DELETE
	public void delete(@PathParam("id") long id) {
		logObj.info("PathParams: id=" + id);
        	em.remove(em.find(PurchaseOrder.class,id));
	}

}
