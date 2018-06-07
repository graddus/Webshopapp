package webshop.webservices;

import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import webshop.domain.Bestelling;
import webshop.domain.Bestellingsregel;
import webshop.domain.DomainController;
import webshop.soap.TestService;

@Path("/bestelling")
public class BestellingResource {
	DomainController Controller = new DomainController();

	@GET
	@Produces("application/json")
	public String CountryList() {
		JsonArrayBuilder jab = Json.createArrayBuilder();

		for (Bestelling b : Controller.getAllBestellingen()) {
			ArrayList<String> urllist = new ArrayList<String>();
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", b.getId());
			job.add("Naam", b.getAccount());
			job.add("Afleveradres", b.getAfleverAdres().getStraat() + " " + b.getAfleverAdres().getStraatnummer());
			for (Bestellingsregel br : b.getRegellijst()) {
				urllist.add(br.getProduct().getNaam() + " x" + br.getAantal());
			}
			job.add("Producten", urllist.toString());
			urllist.clear();

			jab.add(job);
		}

		JsonArray array = jab.build();

		return (array.toString());
	}

	@Path("{id}")
	@GET
	@Produces("application/json")
	public String getProductByID(@PathParam("id") int id) {
		Bestelling b = Controller.getBestellingByID(id);
		ArrayList<String> urllist = new ArrayList<String>();
		JsonObjectBuilder job = Json.createObjectBuilder();

		if (b != null) {
			job.add("ID", b.getId());
			job.add("Naam", b.getAccount());
			job.add("Afleveradres", b.getAfleverAdres().getStraat() + " " + b.getAfleverAdres().getStraatnummer());
			for (Bestellingsregel br : b.getRegellijst()) {
				urllist.add(br.getProduct().getNaam() + " x" + br.getAantal());
			}
			job.add("Producten", urllist.toString());
			return (job.build().toString());
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@Path("/{id}/{accountId}/{adresId}")
	@POST
	public Response addBestelling(@PathParam("id") int id, @PathParam("accountId") int accountId,
			@PathParam("adresId") int adresId) throws MalformedURLException {
		String Status = "verzonden";
		int uniekGetal = TestService.getUniekGetal("pietje", 2, 1);

		Controller.addBestelling(id, accountId, adresId, Status, uniekGetal);

		return Response.ok().build();
	}

	@Path("/{id}/{aantal}/{prijs}/{OrderID}/{ProductId}")
	@POST
	public Response addBestellingRegel(@PathParam("id") int id, @PathParam("aantal") int aantal,
			@PathParam("prijs") int prijs, @PathParam("OrderID") int OrderID, @PathParam("ProductId") int ProductId)
			throws MalformedURLException {
		String Status = "verzonden";
		int uniekGetal = TestService.getUniekGetal("pietje", 2, 1);

		Controller.addBestellingRegel(id, aantal, prijs, OrderID, ProductId);

		return Response.ok().build();
	}
}