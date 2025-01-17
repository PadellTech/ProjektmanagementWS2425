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
        String sql = "SELECT name, preis, wunschoption_id FROM " + tableName + " where wunsch_id = "+filter;
        return executeSelect(sql, "name", "preis", "wunschoption_id");
    }

    /**
     * Liest die Extrawünsche eines gegebenen Kunden für eine gegebene Kategorie
     *
     * @param customerNumber - die gegebene Kundennummer
     * @param wishCategory - die gegebene Kategorie. Wertebereich 1-8
     * @return - Alle wunschoption_ids, die der mit dem Haus des Kunden assoziiert werden.
     */
    public int[] executeSelectCustomerWishes(int customerNumber, int wishCategory) {
        // SQL query to fetch the IDs of Wunschoption
        String sql = "SELECT w.wunschoption_id " +
                "FROM Kunde k " +
                "JOIN Haus h ON k.hausnummer = h.hausnummer " +
                "JOIN Wunschoption_haus wh ON h.hausnummer = wh.hausnummer " +
                "JOIN Wunschoption w ON wh.wunschoption_id = w.wunschoption_id " +
                "WHERE k.kundennummer = ? AND w.wunsch_id = ?";

        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Setting the parameters for the prepared statement
            stmt.setInt(1, customerNumber);
            stmt.setInt(2, wishCategory);

            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the result set and add each Wunschoption ID to the list
                while (rs.next()) {
                    ids.add(rs.getInt("wunschoption_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Wunschoptionen: " + e.getMessage());
        }
        // Convert the list of IDs to an int array
        return ids.stream().mapToInt(i -> i).toArray();
    }

    // Method to save the selected wishes in the database
    public void speichereSonderwuensche(int[] sonderwunsch_id, int hausnummer)
    {
        // Überprüfen, ob das Array leer ist
        if (sonderwunsch_id == null || sonderwunsch_id.length == 0) {
            System.out.println("Keine Sonderwünsche übergeben.");
            return;
        }
        
        // Erstelle den dynamischen SQL-String
        StringBuilder sql = new StringBuilder("INSERT INTO Wunschoption_haus (wunschoption_id, hausnummer) VALUES ");
        
        // Füge für jede Wunschoption eine Zeile hinzu
        for (int i = 0; i < sonderwunsch_id.length; i++) {
            sql.append("(").append(sonderwunsch_id[i]).append(", ").append(hausnummer).append(")");
            if (i < sonderwunsch_id.length - 1) {
                sql.append(", "); // Komma nur zwischen den Werten, nicht am Ende
            }
        }
        sql.append(";"); // Beende das SQL-Statement mit einem Semikolon
        
        // Debug-Ausgabe des generierten SQL-Strings
        System.out.println("Generiertes SQL: " + sql.toString());
        this.executeUpdate(sql.toString());
    }

    // Method to delete the selected wishes in the database
    public void loescheSonderwuensche(int[] sonderwunsch_id, int hausnummer)
    {
        // Überprüfen, ob das Array leer ist
        if (sonderwunsch_id == null || sonderwunsch_id.length == 0) {
            System.out.println("Keine Sonderwünsche übergeben.");
            return;
        }
        
        // Erstelle den dynamischen SQL-String
        StringBuilder sql = new StringBuilder("DELETE FROM Wunschoption_haus WHERE ");
        
        // Füge für jede Wunschoption eine Zeile hinzu
        for (int i = 0; i < sonderwunsch_id.length; i++) {
            sql.append("wunschoption_id=").append(sonderwunsch_id[i]);
            if (i < sonderwunsch_id.length - 1) {
                sql.append(" OR "); // Komma nur zwischen den Werten, nicht am Ende
            }
        }
        sql.append(" AND hausnummer=").append(hausnummer).append(";"); // Beende das SQL-Statement mit einem Semikolon
        
        // Debug-Ausgabe des generierten SQL-Strings
        System.out.println("Generiertes SQL: " + sql.toString());
        this.executeUpdate(sql.toString());
    }

    /**
     * This method retrieves a map of the names of the wishes to their corresponding wunschoption_ids
     */
    public Map<String, Integer> getWunschoptionIdByName() {
        Map<String, Integer> nameToIdMap = new HashMap<>();

        String query = "SELECT wunschoption_id, name FROM Wunschoption";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int wunschoptionId = rs.getInt("wunschoption_id");
                String name = rs.getString("name");
                nameToIdMap.put(name, wunschoptionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nameToIdMap;
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
    
    public String getCustomerLastname(int hausnummer) throws SQLException {
        String query = "SELECT nachname FROM Kunde WHERE hausnummer = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, hausnummer);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nachname");
                }
            }
        }
        return null;
    }

    public List<Map<String, Object>> getSonderwunschData(int hausnummer, String kategorie) throws SQLException {
        String query = """
            SELECT sw.name AS Sonderwunsch_Name, 
                   wo.name AS Wunschoption_Name, 
                   wo.preis AS Preis
            FROM Wunschoption wo
            JOIN Wunschoption_haus wh ON wo.wunschoption_id = wh.wunschoption_id
            JOIN Haus h ON wh.hausnummer = h.hausnummer
            JOIN Kunde k ON k.hausnummer = h.hausnummer
            JOIN Sonderwunschkategorie sw ON wo.wunsch_id = sw.wunsch_id
            WHERE k.hausnummer = ? AND sw.name = ?;
        """;

        List<Map<String, Object>> results = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, hausnummer);
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
