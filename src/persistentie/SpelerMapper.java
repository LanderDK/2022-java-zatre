package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Speler;
import exceptions.SpelerBestaatAlException;
import exceptions.SpelerNietGevondenException;

public class SpelerMapper {

	private static final String INSERT_SPELER = "INSERT INTO ID372834_G26.Speler (gebruikersNaam, geboorteJaar)"
			+ "VALUES (?, ?)";
	private static final String UPDATE_SPELER = "UPDATE ID372834_G26.Speler SET aantalSpellen = ? WHERE gebruikersNaam = ? AND geboorteJaar = ?";

	public void voegSpelerToe(Speler speler) throws SpelerBestaatAlException {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.getGebruikersNaam());
			query.setInt(2, speler.getGeboorteJaar());
			query.executeUpdate();
		} catch (SQLException ex) {
			if (ex.getErrorCode() == 1062) {// duplicate entry error
				throw new SpelerBestaatAlException("Deze speler bestaat al");
			} else {
				throw new IllegalArgumentException("Er ging iets fout tijdens het registreren van een speler");
			}
		}
	}

	public Speler geefSpeler(Speler speler) throws SpelerNietGevondenException {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(
						"SELECT * FROM ID372834_G26.Speler WHERE gebruikersNaam = ? AND geboorteJaar = ?")) {
			query.setString(1, speler.getGebruikersNaam());
			query.setInt(2, speler.getGeboorteJaar());
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					speler = new Speler(rs.getString("gebruikersNaam"), rs.getInt("geboorteJaar"),
							rs.getInt("aantalSpellen"));
				} else {
					throw new SpelerNietGevondenException();
				}
			}
		} catch (SQLException ex) {
			throw new IllegalArgumentException("Er ging iets fout tijdens het zoeken van een speler");
		}
		return speler;
	}

	public void updateSpelKansen(Speler speler) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(UPDATE_SPELER)) {
			query.setInt(1, speler.getAantalSpellen());
			query.setString(2, speler.getGebruikersNaam());
			query.setInt(3, speler.getGeboorteJaar());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new IllegalArgumentException(
					"Er ging iets fout tijdens het veranderen van de speelkansen van een speler");
		}
	}
}
