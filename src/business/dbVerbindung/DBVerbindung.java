package business.dbVerbindung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBVerbindung 
{
	private static DBVerbindung instance; // Singleton-Instanz
	private String host, port, databaseName, userName, password, sslMode, connectionUrl;
	private Connection connection;
	
	public DBVerbindung() {
		this.host = "mysql-37c915f9-hamzaouialae5-2638.g.aivencloud.com";
		this.port = "13365";
		this.databaseName = "defaultdb";
		this.userName = "avnadmin";
		this.password  = "AVNS_-LoihoWx8KdPJ7vIn3t";
		this.sslMode = "REQUIRED";
		try {
			this.verbindung();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void notNull()
	{
		System.out.println("Not null");
	}
	
	public void verbindung() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Verbindungs-URI mit SSI-Modus
		this.connectionUrl = String. format ("jdbc:mysql://%s:%s/%s?sslmode=%s", host, port, databaseName, sslMode) ;
		// Versuch, eine Verbindung ZUE Datenbank herzustellen
		try {
			this.connection = DriverManager.getConnection(connectionUrl, userName, password);
			System.out.println("Datenbankverbindung erfolgreich");
		} catch (SQLException e) {
			System.out.println("Verbindung fehlgeschlagen");
			
			e.printStackTrace();
		}
	}
	// Methode zum Ausführen eines SELECT-Statements
	public void executeSelect(String sql) {
	    try (PreparedStatement stmt = connection.prepareStatement(sql);
	         ResultSet resultSet = stmt.executeQuery()) {

	        // Metadaten abrufen, um Spaltennamen und Anzahl der Spalten zu erhalten
	        java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
	        int columnCount = metaData.getColumnCount();

	        // Spaltennamen ausgeben
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();

	        // Zeilen ausgeben
	        while (resultSet.next()) {
	            for (int i = 1; i <= columnCount; i++) {
	                System.out.print(resultSet.getString(i) + "\t");
	            }
	            System.out.println();
	        }

	    } catch (SQLException e) {
	        System.err.println("Fehler beim SELECT: " + e.getMessage());
	    }
	}
    // Methode zum Ausführen von INSERT/UPDATE/DELETE-Statements
    public void executeUpdate(String sql) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " Zeilen betroffen.");
        } catch (SQLException e) {
            System.err.println("Fehler beim Update: " + e.getMessage());
        }
    }
    // Prepared Statements (z. B. mit Parametern)
    public void executePreparedUpdate(String sql, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " Zeilen betroffen.");
        } catch (SQLException e) {
            System.err.println("Fehler beim Prepared Update: " + e.getMessage());
        }
    }
    public String[][] executeSelect(String sql, String... columns) {
        try (PreparedStatement stmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = stmt.executeQuery()) {

            // Zählen der Zeilen (ResultSet)
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }

            // Ergebnis-Array (Zeilen x Spalten)
            String[][] results = new String[rowCount][columns.length];

            // Zurück zum Anfang des ResultSets
            resultSet.beforeFirst();
            int rowIndex = 0;

            // Ergebnisse in das Array einfügen
            while (resultSet.next()) {
                for (int colIndex = 0; colIndex < columns.length; colIndex++) {
                    results[rowIndex][colIndex] = resultSet.getString(columns[colIndex]);
                }
                rowIndex++;
            }

            return results;

        } catch (SQLException e) {
            System.err.println("Fehler beim SELECT: " + e.getMessage());
            return new String[0][0]; // Leeres Array im Fehlerfall
        }
    }

    public String[][] executeSelectNameAndPrice(String tableName, int filter) {
        String sql = "SELECT name, preis FROM " + tableName + " where wunsch_id = "+filter;
        return executeSelect(sql, "name", "preis");
    }



    public static DBVerbindung getInstance() {
        if (instance == null) { // Instanz wird nur erstellt, wenn sie nicht existiert
            synchronized (DBVerbindung.class) { // Thread-Sicherheit
                if (instance == null) {
                    instance = new DBVerbindung();
                }
            }
        }
        return instance;
    }
    
    public String getCustomerLastname(int kundennummer) throws SQLException {
        String query = "SELECT nachname FROM Kunde WHERE kundennummer = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, kundennummer);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nachname");
                }
            }
        }
        return null;
    }

    public List<Map<String, Object>> getSonderwunschData(int kundennummer, String kategorie) throws SQLException {
        String query = """
            SELECT sw.name AS Sonderwunsch_Name, 
                   wo.name AS Wunschoption_Name, 
                   wo.preis AS Preis
            FROM Wunschoption wo
            JOIN Wunschoption_haus wh ON wo.wunschoption_id = wh.wunschoption_id
            JOIN Haus h ON wh.hausnummer = h.hausnummer
            JOIN Kunde k ON k.hausnummer = h.hausnummer
            JOIN Sonderwunschkategorie sw ON wo.wunsch_id = sw.wunsch_id
            WHERE k.kundennummer = ? AND sw.name = ?;
        """;

        List<Map<String, Object>> results = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, kundennummer);
            stmt.setString(2, kategorie);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Sonderwunsch_Name", rs.getString("Sonderwunsch_Name"));
                    row.put("Wunschoption_Name", rs.getString("Wunschoption_Name"));
                    row.put("Preis", rs.getDouble("Preis"));
                    results.add(row);
                }
            }
        }
        return results;
    }
}
