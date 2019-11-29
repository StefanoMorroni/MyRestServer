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
   private static List<String> nomeScientifico = new ArrayList();
   private static List<String> canonicalname = new ArrayList();
   private static List<String> provider = new ArrayList();

   @GetMapping("/tassonomia")
   public ResponseEntity<Object> getSuggestions(@RequestParam(value = "name", required = false) String theName) throws Exception {

      TassonomiaResponse retValue = new TassonomiaResponse();
      String name = theName.toLowerCase();

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
      
      if (nomeScientifico.isEmpty()) {
         synchronized (this) {
            if (nomeScientifico.isEmpty()) {
               nomeScientifico = execQuery("nome_scientifico");
            }
         }
      }      

      if (canonicalname.isEmpty()) {
         synchronized (this) {
            if (canonicalname.isEmpty()) {
               canonicalname = execQuery("canonicalname");
            }
         }
      }

      if (provider.isEmpty()) {
         synchronized (this) {
            if (provider.isEmpty()) {
               provider = execQuery("provider");
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

      retValue.setNome_scientifico(
            nomeScientifico.stream()
                  .filter(item -> item.toLowerCase().contains(name))
                  .distinct()
                  .collect(Collectors.toList())
      );
      
      retValue.setCanonicalName(
            canonicalname.stream()
                  .filter(item -> item.toLowerCase().contains(name))
                  .distinct()
                  .collect(Collectors.toList())
      );

      retValue.setProvider(
            provider.stream()
                  .filter(item -> item.toLowerCase().contains(name))
                  .distinct()
                  .collect(Collectors.toList())
      );
      
      retValue.setProvider(new ArrayList());

      return new ResponseEntity<>(retValue, HttpStatus.OK);
   }

   private List<String> execQuery(String theField) throws Exception, SQLException {
      List<String> retValue = new ArrayList();
      try {
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
      } catch (Exception e) {
      }
      return retValue;
   }

}
