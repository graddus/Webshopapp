package webshop.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BestellingDAO extends BaseDAO {
	ProductDAO dao = new ProductDAO();
	KlantDAO dao2 = new KlantDAO();
	public BestellingDAO() {
	}

	public ArrayList<Bestelling> getAllBestellingen() {
		ArrayList<Bestelling> list = new ArrayList<Bestelling>();
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * from  Bestellingen");
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			int klantid;
			ArrayList<Bestellingsregel> lijst = new ArrayList<Bestellingsregel>();
			Adres adres = null;
			;
			Bestelling best = null;

			while (rs.next()) {
				id = rs.getInt("id");
				klantid = rs.getInt("ACCOUNTID");
				lijst = getBestellingsRegelsByOrderID(id);
				adres = dao2.getAdresByID(rs.getInt("adresid"));
				best = new Bestelling(id, klantid, lijst, adres);
				list.add(best);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Bestellingsregel> getBestellingsRegelsByOrderID(int ordid) {
		ArrayList<Bestellingsregel> list = new ArrayList<Bestellingsregel>();
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * from  Bestellingregels where Orderid=?");
			statement.setInt(1, ordid);
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			int aantal;
			double prijs;
			Product product;
			Bestellingsregel regel = null;

			while (rs.next()) {
				id = rs.getInt("id");
				aantal = rs.getInt("aantal");
				product = dao.getProductByID(rs.getInt("Productid"));
				prijs = rs.getInt("prijs");
				regel = new Bestellingsregel(id, aantal, prijs, product);
				list.add(regel);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Bestelling getBestellingByID(int bestid) {
		Bestelling bestel = null;
		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * from  Bestellingen where id=?");
			statement.setInt(1, bestid);
			statement.executeQuery();
			ResultSet rs = statement.executeQuery();

			int id;
			int klantid;
			ArrayList<Bestellingsregel> lijst = new ArrayList<Bestellingsregel>();
			Adres adres = null;
			;
			Bestelling best = null;

			while (rs.next()) {
				id = rs.getInt("id");
				klantid = rs.getInt("ACCOUNTID");
				lijst = getBestellingsRegelsByOrderID(id);
				adres = dao2.getAdresByID(rs.getInt("adresid"));
				best = new Bestelling(id, klantid, lijst, adres);
				bestel = best;
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bestel;
	}

	public void addBestelling(int id, int accountId, int adresId, String Status,int uniekGetal) {

		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO Bestellingen(id,ACCOUNTID,adresid,UNIEKGETAL,STATUS) VALUES(?,?,?,?,?)");
			statement.setInt(1, id);
			statement.setInt(2, accountId);
			statement.setInt(3, adresId);
			statement.setInt(4, uniekGetal);
			statement.setString(5, Status);
			statement.executeQuery();
			
			conn.commit();
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return null;
	}
	public void addBestellingRegel(int id, int aantal, int prijs, int OrderID,int ProductId) {

		try (Connection conn = super.getConnection()) {
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO BESTELLINGREGELS(id,aantal,prijs,OrderID,ProductId) VALUES(?,?,?,?,?)");
			statement.setInt(1, id);
			statement.setInt(2, aantal);
			statement.setInt(3, prijs);
			statement.setInt(4, OrderID);
			statement.setInt(5, ProductId);
			statement.executeQuery();

			conn.commit();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return null;
	}
}
