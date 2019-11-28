package myrestserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v2")
public class TassonomiaController {

   private static final Logger logger = LoggerFactory.getLogger(TassonomiaController.class);

   @Value("${QUERY}")
   private String QUERY;

   private static List<String> ordo = new ArrayList();
   private static List<String> family = new ArrayList();
   private static List<String> genus = new ArrayList();
   private static List<String> canonicalName = new ArrayList();
   private static List<String> provider = new ArrayList();

   @GetMapping("/tassonomia")
   public ResponseEntity<Object> getSuggestions(@RequestParam(value = "name", required = false) String name) throws Exception {

      TassonomiaResponse retValue = new TassonomiaResponse();

      if (ordo.isEmpty()) {
         synchronized (this) {
            if (ordo.isEmpty()) {
               ordo = execQuery("ordo");
            }
         }
      }

      if (family.isEmpty()) {
         synchronized (this) {
            if (family.isEmpty()) {
               family = execQuery("family");
            }
         }
      }

      if (genus.isEmpty()) {
         synchronized (this) {
            if (genus.isEmpty()) {
               genus = execQuery("genus");
            }
         }
      }

      if (canonicalName.isEmpty()) {
         synchronized (this) {
            if (canonicalName.isEmpty()) {
               canonicalName = execQuery("canonicalname");
            }
         }
      }
      
      retValue.setOrdo(
            ordo.stream()
                  .filter(item -> item.toLowerCase().contains(name))
                  .distinct()
                  .collect(Collectors.toList())
      );

      retValue.setFamily(
            family.stream()
                  .filter(item -> item.toLowerCase().contains(name))
                  .distinct()
                  .collect(Collectors.toList())
      );

      retValue.setGenus(
            genus.stream()
                  .filter(item -> item.toLowerCase().contains(name))
                  .distinct()
                  .collect(Collectors.toList())
      );

      retValue.setCanonicalName(
            canonicalName.stream()
                  .filter(item -> item.toLowerCase().contains(name))
                  .distinct()
                  .collect(Collectors.toList())
      );

      retValue.setProvider(new ArrayList());

      return new ResponseEntity<>(retValue, HttpStatus.OK);
   }

   private List<String> execQuery(String theField) throws Exception, SQLException {
      List<String> retValue = new ArrayList();

      String thequery = QUERY.replace("<FIELD>", theField);
      logger.info(thequery);

      InitialContext cxt = new InitialContext();
      DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/thedb");
      if (ds == null) {
         throw new Exception("Data source not found!");
      }
      Connection c = ds.getConnection();
      c.setAutoCommit(false);
      Statement stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery(thequery);
      while (rs.next()) {
         retValue.add(rs.getString(theField));
      }
      rs.close();
      stmt.close();
      c.close();
      logger.info(thequery + ", nrec = " + retValue.size());
      return retValue;
   }

}
