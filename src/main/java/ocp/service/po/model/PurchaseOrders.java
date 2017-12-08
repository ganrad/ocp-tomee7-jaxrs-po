package ocp.service.po.model;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.Link;

public class PurchaseOrders {

   protected Collection<PurchaseOrder> orders;
   protected List<Link> links;

   public Collection<PurchaseOrder> getOrders()
   {
      return orders;
   }

   public void setOrders(Collection<PurchaseOrder> orders)
   {
      this.orders = orders;
   }

   public List<Link> getLinks()
   {
      return links;
   }

   public void setLinks(List<Link> links)
   {
      this.links = links;
   }

   public URI getNext()
   {
      if (links == null) return null;
      for (Link link : links)
      {
         if ("next".equals(link.getRel())) return link.getUri();
      }
      return null;
   }

   public URI getPrevious()
   {
      if (links == null) return null;
      for (Link link : links)
      {
         if ("previous".equals(link.getRel())) return link.getUri();
      }
      return null;
   }
}
