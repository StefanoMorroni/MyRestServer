package myrestserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@CrossOrigin(origins = "*")
@RestController
public class HabitatController {

	private static final Logger logger = LoggerFactory.getLogger(HabitatController.class);

	@Value("${HABITAT_SELECT}")
	private String HABITAT_SELECT;
	@Value("${HABITAT_CODICE}")
	private String HABITAT_CODICE;
	@Value("${HABITAT_DESCRIZIONE}")
	private String HABITAT_DESCRIZIONE;

	private static List<HabitatRecord> habitat = new ArrayList();

	@GetMapping("/habitat")
	public ResponseEntity<Object> getSuggestions() throws Exception {

		HabitatResponse retValue = new HabitatResponse();

		if (habitat.isEmpty()) {
			synchronized (this) {
				if (habitat.isEmpty()) {
					habitat = execQuery();
				}
			}
		}

		retValue.setHabitat(habitat);

		return new ResponseEntity<>(retValue, HttpStatus.OK);
	}

	private List<HabitatRecord> execQuery() throws Exception, SQLException {
		List<HabitatRecord> retValue = new ArrayList();
		try {
			logger.info(HABITAT_SELECT);

			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/thedb");
			if (ds == null) {
				throw new Exception("Data source not found!");
			}
			Connection c = ds.getConnection();
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(HABITAT_SELECT);
			while (rs.next()) {
				retValue.add(new HabitatRecord(rs.getString(HABITAT_CODICE), rs.getString(HABITAT_DESCRIZIONE)));
			}
			rs.close();
			stmt.close();
			c.close();
			logger.info(HABITAT_SELECT + ", nrec = " + retValue.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retValue;
	}

}
