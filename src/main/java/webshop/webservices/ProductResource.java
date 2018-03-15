package webshop.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import webshop.domain.klant.Klant;
import webshop.domain.klant.Product;
import webshop.domain.persistence.KlantDAO;
import webshop.domain.persistence.ProductDAO;

@Path("/product")
public class ProductResource {
	ProductDAO ProductDAO = new ProductDAO();
	KlantDAO KlantDAO = new KlantDAO();

	@GET
	@Produces("application/json")
	public String CountryList() {
		JsonArrayBuilder jab = Json.createArrayBuilder();

		for (Product p : ProductDAO.getAllProducts()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", p.getId());
			job.add("Naam", p.getNaam());
			job.add("Omschrijving", p.getOmschrijving());
			job.add("Prijs", p.getPrijs());
			job.add("Fabrikant", p.getFabrikant());

			jab.add(job);
		}

		JsonArray array = jab.build();

		return (array.toString());
	}

	@Path("{id}")
	@GET
	@Produces("application/json")
	public String CountryList(@PathParam("id") int id) {

		Product p = ProductDAO.getProductByID(id);
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", p.getId());
		job.add("Naam", p.getNaam());
		job.add("Omschrijving", p.getOmschrijving());
		job.add("Prijs", p.getPrijs());
		job.add("Fabrikant", p.getFabrikant());

		return (job.build().toString());
	}

	@Path("getid/{id}")
	@GET
	@Produces("application/json")
	public String getProductIDbyName(@PathParam("id") String name) {
		Product target = null;
		name = name.replace("%20", " ");
		name = name.toLowerCase();
		for (Product p : ProductDAO.getAllProducts()) {
			if (p.getNaam().toLowerCase().equals(name)) {
				target = p;
			}
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", target.getId());

		return (job.build().toString());
	}

	@Path("wishlist/{id}")
	@GET
	@Produces("application/json")
	public String getWishlist(@PathParam("id") int klantid) {
		JsonArrayBuilder jab = Json.createArrayBuilder();

		for (Product p : ProductDAO.getWishlistByKlantID(klantid)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", p.getId());
			job.add("Naam", p.getNaam());
			job.add("Omschrijving", p.getOmschrijving());
			job.add("Prijs", p.getPrijs());
			job.add("Fabrikant", p.getFabrikant());

			jab.add(job);
		}

		JsonArray array = jab.build();

		return (array.toString());
	}

	@Path("basket/{id}")
	@GET
	@Produces("application/json")
	public String getCart(@PathParam("id") int klantid) {
		JsonArrayBuilder jab = Json.createArrayBuilder();

		for (Product p : ProductDAO.getCartByKlantID(klantid)) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", p.getId());
			job.add("Naam", p.getNaam());
			job.add("Omschrijving", p.getOmschrijving());
			job.add("Prijs", p.getPrijs());
			job.add("Fabrikant", p.getFabrikant());

			jab.add(job);
		}

		JsonArray array = jab.build();

		return (array.toString());
	}

	@Path("basket/{klantid}/add/{productid}/{aantal}")
	@POST
	public void addToCart(@PathParam("klantid") int klantid, @PathParam("productid") int productid,
			@PathParam("aantal") int aantal) {
		ProductDAO.addToCart(klantid, productid, aantal);
	}

	@DELETE
	@Path("wishlist/delete/{klantid}+{productid}")
	public Response removeFromWishlist(@PathParam("klantid") int klantid, @PathParam("productid") int ProdId) {
		Klant found = KlantDAO.getKlantByID(klantid);
		Product found2 = ProductDAO.getProductByID(ProdId);
		if (found == null || found2 == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			ProductDAO.deletefromWishlist(found.getId(), found2.getId());
			return Response.ok().build();
		}
	}

}
