package webshop.domain;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import webshop.domain.Aanbieding;
import webshop.domain.Categorie;
import webshop.domain.Product;

public class ProductDAO extends BaseDAO {

	public ProductDAO() {
	}

	/**
	 * Made it so that this func retrieves all rules and not rules based on id
	 *
	 * @return
	 */
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> list = new ArrayList<Product>();
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * from Producten ORDER BY id desc");
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			String naam;
			String omschrijving;
			double prijs;
			String fabrikant;
			Product product;

			while (rs.next()) {
				id = rs.getInt("id");
				naam = rs.getString("naam");
				omschrijving = rs.getString("omschrijving");
				prijs = rs.getDouble("Prijs");
				fabrikant = rs.getString("fabrikant");
				java.sql.Blob blob = rs.getBlob("Afbeelding");
				InputStream in = blob.getBinaryStream();
				BufferedImage image = ImageIO.read(in);
				product = new Product(naam, image, prijs, id, omschrijving, fabrikant);
				list.add(product);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Product getProductByID(int i) {
		Product r = null;
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * from Producten Where ID=" + i);
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			String naam;
			String omschrijving;
			double prijs;
			String fabrikant;

			while (rs.next()) {
				id = rs.getInt("id");
				naam = rs.getString("naam");
				omschrijving = rs.getString("omschrijving");
				prijs = rs.getDouble("Prijs");
				fabrikant = rs.getString("fabrikant");
				java.sql.Blob blob = rs.getBlob("Afbeelding");
				InputStream in = blob.getBinaryStream();
				BufferedImage image = ImageIO.read(in);
				r = new Product(naam, image, prijs, id, omschrijving, fabrikant);
			}
			rs.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}

	public ArrayList<Product> getProductenbyCategorie(int catid) {
		ArrayList<Product> result = new ArrayList<Product>();
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn
					.prepareStatement("SELECT * from CategorieProduct where categoryid=" + catid);
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			Product prod = null;

			while (rs.next()) {
				prod = getProductByID(rs.getInt("productid"));
				result.add(prod);
			}
			rs.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Categorie getCategoryByID(int i) {
		Categorie cat = null;
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * from Categorie where id=" + i);
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			String naam;
			String omschrijving;

			while (rs.next()) {
				id = rs.getInt("id");
				naam = rs.getString("naam");
				omschrijving = rs.getString("omschrijving");
				java.sql.Blob blob = rs.getBlob("Afbeelding");
				InputStream in = blob.getBinaryStream();
				BufferedImage image = ImageIO.read(in);
				cat = new Categorie(id, naam, image, omschrijving, getProductenbyCategorie(id));
			}
			rs.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cat;
	}

	public ArrayList<Categorie> getAllCategories() {
		ArrayList<Categorie> result = new ArrayList<Categorie>();
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * from Categorie");
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			String naam;
			String omschrijving;
			Categorie cat = null;
			ArrayList<Product> producten = new ArrayList<Product>();

			while (rs.next()) {
				id = rs.getInt("id");
				naam = rs.getString("naam");
				omschrijving = rs.getString("omschrijving");
				java.sql.Blob blob = rs.getBlob("Afbeelding");
				InputStream in = blob.getBinaryStream();
				BufferedImage image = ImageIO.read(in);
				producten = getProductenbyCategorie(id);
				cat = new Categorie(id, naam, image, omschrijving, producten);
				result.add(cat);
			}
			rs.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Categorie> getCategories(String cat) {
		List<String> cats = Arrays.asList(cat.split("\\s*,\\s*"));
		int i = 0;
		ArrayList<Categorie> lijst = new ArrayList<Categorie>();
		for (String s : cats) {
			i = Integer.parseInt(s);
			lijst.add(getCategoryByID(i));
		}
		return lijst;
	}

	public ArrayList<Product> getCartByKlantID(int klantid) {
		ArrayList<Product> list = new ArrayList<Product>();
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * from Producten where id in (select productid from cart where klantid=" + klantid + ")");
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			String naam;
			String omschrijving;
			double prijs;
			String fabrikant;
			Product product;

			while (rs.next()) {
				id = rs.getInt("id");
				naam = rs.getString("naam");
				omschrijving = rs.getString("omschrijving");
				prijs = rs.getDouble("Prijs");
				fabrikant = rs.getString("fabrikant");
				java.sql.Blob blob = rs.getBlob("Afbeelding");
				InputStream in = blob.getBinaryStream();
				BufferedImage image = ImageIO.read(in);
				product = new Product(naam, image, prijs, id, omschrijving, fabrikant);
				list.add(product);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Aanbieding> getAanbiedingByProductID(int productid) throws ParseException {
		ArrayList<Aanbieding> list = new ArrayList<Aanbieding>();
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(
					"SELECT * from Aanbiedingen where id in (select productid from wishlist where productid="
							+ productid + ")");
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			String tekst;
			double prijs;
			Date vand;
			Date totd;
			int prodid;
			Aanbieding aanb;
			DateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);

			while (rs.next()) {
				id = rs.getInt("id");
				prijs = rs.getDouble("prijs");
				tekst = rs.getString("tekst");
				vand = format.parse(rs.getString("vandatum"));
				totd = format.parse(rs.getString("totdatum"));
				prodid = rs.getInt("productid");
				aanb = new Aanbieding(id, prijs, tekst, vand, totd, prodid);
				list.add(aanb);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void addToCart(int klantid, int productid, int aantal) {
		try (Connection conn = super.getConnection()) {
			boolean dupe = false;
			PreparedStatement statement = null;
			for (Product p : getCartByKlantID(klantid)) {
				if (p.getId() == productid) {
					dupe = true;
				}
			}
			if (dupe == true) {
				aantal = aantal / 2;
				statement = conn.prepareStatement("UPDATE cart SET aantal=aantal+" + aantal + " WHERE klantid="
						+ klantid + " AND productid=" + productid);
			} else {
				System.out.println(aantal);
				statement = conn
						.prepareStatement("INSERT into cart values(" + klantid + "," + productid + ", " + aantal + ")");
			}
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			conn.commit();
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletefromCart(int klantid, int productid) {
		try {
			Connection conn = super.getConnection();

			// Een eerste SQL statement maken
			Statement stmt = conn.createStatement();

			// Een tweede statement maken dat een resultaat oplevert
			String queryText = "delete from cart where klantid=" + klantid + "AND productid=" + productid;

			// Een tweede statement uitvoeren
			stmt.executeQuery(queryText);
			conn.commit();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public int getAmount(int klantid, int prodid) {
		int result = 0;
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn
					.prepareStatement("SELECT aantal from Cart Where KlantID=" + klantid + " AND ProductID=" + prodid);
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				result = rs.getInt("aantal");
			}
			rs.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// PRoductbeheer
	public void addProduct(int id, String naam,double prijs,String omschrijving,String fabrikant) {

		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO producten(id,naam,prijs,omschrijving,fabrikant) VALUES("
							+id+",'" +naam+"'," +prijs+",'" +omschrijving+"','" +fabrikant+"')");
			statement.executeQuery();
			//ResultSet rs = statement.executeQuery();
			//rs.close();
			//TODO hier moet ergens een insert voor categorie komen 
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//return null;
	}

	public void editProduct(int id, String naam,double prijs,String omschrijving,String fabrikant) {
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn
					.prepareStatement("UPDATE producten set naam= '"+naam+"',prijs= "+prijs+",omschrijving ='"+omschrijving
							+"',fabrikant= '"+fabrikant+"' WHERE id ="+id);			
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			rs.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//return null;
	}
	
	public void deleteProduct(int id) {
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn
					.prepareStatement("delete from producten where id= " + id);
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			rs.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return result;
	}

}