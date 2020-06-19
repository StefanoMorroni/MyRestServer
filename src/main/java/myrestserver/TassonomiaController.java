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

@CrossOrigin(origins = "*")
@RestController
public class TassonomiaController {

   private static final Logger logger = LoggerFactory.getLogger(TassonomiaController.class);

   @Value("${QUERY}")
   private String QUERY;

   private static List<TheData> ordo = new ArrayList();
   private static List<TheData> family = new ArrayList();
   private static List<TheData> genus = new ArrayList();
   private static List<TheData> nomeScientifico = new ArrayList();
   private static List<TheData> canonicalname = new ArrayList();
   private static List<TheData> provider = new ArrayList();

   @GetMapping("/tassonomia")
   public ResponseEntity<Object> getSuggestions(@RequestParam(value = "name", required = false) String theName,
         @RequestParam(value = "osservazioni", required = false, defaultValue = "true") String flagOsservazioni,
         @RequestParam(value = "citizenscience", required = false, defaultValue = "true") String flagCitizenscience,
         @RequestParam(value = "provider", required = false, defaultValue = "true") String flagProvider
   ) throws Exception {

      TassonomiaResponse retValue = new TassonomiaResponse();
      String name = theName.toLowerCase();

      logger.info("name: " + name + ", osservazioni:" + flagOsservazioni + ", citizenscience:" + flagCitizenscience + ", provider:" + flagProvider);
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

      retValue.setOrdo(ordo.stream()
            .filter(item -> item.getValue().toLowerCase().contains(name))
            .filter(item -> {
               if (item.getSource().equalsIgnoreCase("osservazioni")) {
                  if (flagOsservazioni.equalsIgnoreCase("true")) {
                     return true;
                  }
                  return false;
               }
               return true;
            })
            .filter(item -> {
               if (item.getSource().equalsIgnoreCase("citizenscience")) {
                  if (flagCitizenscience.equalsIgnoreCase("true")) {
                     return true;
                  }
                  return false;
               }
               return true;
            })
            .map(item -> item.getValue())
            .distinct()
            .limit(5)
            .collect(Collectors.toList())
      );

      retValue.setFamily(
            family.stream()
                  .filter(item -> item.getValue().toLowerCase().contains(name))
                  .filter(item -> {
                     if (item.getSource().equalsIgnoreCase("osservazioni")) {
                        if (flagOsservazioni.equalsIgnoreCase("true")) {
                           return true;
                        }
                        return false;
                     }
                     return true;
                  })
                  .filter(item -> {
                     if (item.getSource().equalsIgnoreCase("citizenscience")) {
                        if (flagCitizenscience.equalsIgnoreCase("true")) {
                           return true;
                        }
                        return false;
                     }
                     return true;
                  })
                  .map(item -> item.getValue())
                  .distinct()
                  .limit(5)
                  .collect(Collectors.toList())
      );

      retValue.setGenus(
            genus.stream()
                  .filter(item -> item.getValue().toLowerCase().contains(name))
                  .filter(item -> {
                     if (item.getSource().equalsIgnoreCase("osservazioni")) {
                        if (flagOsservazioni.equalsIgnoreCase("true")) {
                           return true;
                        }
                        return false;
                     }
                     return true;
                  })
                  .filter(item -> {
                     if (item.getSource().equalsIgnoreCase("CITIZENSCIENCE")) {
                        if (flagCitizenscience.equalsIgnoreCase("true")) {
                           return true;
                        }
                        return false;
                     }
                     return true;
                  })
                  .map(item -> item.getValue())
                  .distinct()
                  .limit(5)
                  .collect(Collectors.toList())
      );

      retValue.setCanonicalname(
            canonicalname.stream()
                  .filter(item -> item.getValue().toLowerCase().contains(name))
                  .filter(item -> {
                     if (item.getSource().equalsIgnoreCase("osservazioni")) {
                        if (flagOsservazioni.equalsIgnoreCase("true")) {
                           return true;
                        }
                        return false;
                     }
                     return true;
                  })
                  .filter(item -> {
                     if (item.getSource().equalsIgnoreCase("CITIZENSCIENCE")) {
                        if (flagCitizenscience.equalsIgnoreCase("true")) {
                           return true;
                        }
                        return false;
                     }
                     return true;
                  })
                  .map(item -> item.getValue())
                  .distinct()
                  .limit(5)
                  .collect(Collectors.toList())
      );

      if (flagProvider.equalsIgnoreCase("true")) {
         retValue.setProvider(
               provider.stream()
                     .filter(item -> item.getValue().toLowerCase().contains(name))                
                     .filter(item -> {
                        if (item.getSource().equalsIgnoreCase("osservazioni")) {
                           if (flagOsservazioni.equalsIgnoreCase("true")) {
                              return true;
                           }
                           return false;
                        }
                        return true;
                     })                     
                     .filter(item -> {
                        if (item.getSource().equalsIgnoreCase("CITIZENSCIENCE")) {
                           if (flagCitizenscience.equalsIgnoreCase("true")) {
                              return true;
                           }
                           return false;
                        }
                        return true;
                     })
                     .map(item -> item.getValue())
                     .distinct()
                     .limit(5)
                     .collect(Collectors.toList())
         );
      }

      return new ResponseEntity<>(retValue, HttpStatus.OK);
   }

   private List<TheData> execQuery(String theField) throws Exception, SQLException {
      List<TheData> retValue = new ArrayList();
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
            retValue.add(new TheData(rs.getString(theField), rs.getString("thesource")));
         }
         rs.close();
         stmt.close();
         c.close();
         logger.info(thequery + ", nrec = " + retValue.size());
      } catch (Exception e) {
         e.printStackTrace();
      }
      return retValue;
   }

}
