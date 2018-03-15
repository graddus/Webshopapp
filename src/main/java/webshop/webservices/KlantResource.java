package webshop.webservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import webshop.domain.klant.Klant;
import webshop.domain.persistence.KlantDAO;
 
@Path("Klant")
public class KlantResource {
	KlantDAO dao=new KlantDAO();
	

	@GET
	@Produces("application/json")
	public String CountryList() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		DateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
		
		for (Klant c : dao.getAllKlanten()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("id", c.getId());
			job.add("naam", c.getNaam());
			job.add("email", c.getGeslacht());
			job.add("tel", c.getTelefoonnummer());
			job.add("gebdatum", format.format(c.getGeboortedatum()));
			job.add("woonstraat", c.getWoonAdres().getStraat());
			job.add("woonnummer", c.getWoonAdres().getStraatnummer());
			job.add("wachtwoord", c.getAccount().getWachtwoord());
			job.add("factuurstraat", c.getAccount().getFactuurAdres().getStraat());
			job.add("factuurnummer", c.getAccount().getFactuurAdres().getStraatnummer());
			job.add("opendatum", format.format(c.getAccount().getOpenDatum()));
			
			jab.add(job);
		}

		JsonArray array = jab.build();

		return (array.toString());
	}
	@Path("{id}")
	@GET
	@Produces("application/json")
	public String SeriesByCustID(@PathParam("id") int id) {
		Klant k = dao.getKlantByID(id);
		DateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("id", k.getId());
		job.add("naam", k.getNaam());
		job.add("email", k.getGeslacht());
		job.add("tel", k.getTelefoonnummer());
		job.add("gebdatum", format.format(k.getGeboortedatum()));
		job.add("woonstraat", k.getWoonAdres().getStraat());
		job.add("woonnummer", k.getWoonAdres().getStraatnummer());
		job.add("wachtwoord", k.getAccount().getWachtwoord());
		job.add("factuurstraat", k.getAccount().getFactuurAdres().getStraat());
		job.add("factuurnummer", k.getAccount().getFactuurAdres().getStraatnummer());
		job.add("opendatum", format.format(k.getAccount().getOpenDatum()));
		

		return (job.build().toString());
	}
	
}

