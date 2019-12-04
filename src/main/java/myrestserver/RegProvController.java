package myrestserver;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import myrestserver.data.Response;
import myrestserver.data.FeaturesCollection;
import myrestserver.data.ResponseItem;

@CrossOrigin(origins = "*")
@RestController
public class RegProvController {

   private static final Logger logger = LoggerFactory.getLogger(RegProvController.class);

   @Value("${HABITAT.URL}")
   private String HABITAT_URL;
   @Value("${HABITAT.FILTER}")
   private String HABITAT_FILTER;
   @Value("${HABITAT.GETFEATURESURL}")
   private String HABITAT_GETFEATURESURL;
      
   @Value("${REGIONI.URL}")
   private String REGIONI_URL;
   @Value("${REGIONI.FILTER}")
   private String REGIONI_FILTER;
   @Value("${REGIONI.GETFEATURESURL}")
   private String REGIONI_GETFEATURESURL;

   @Value("${PROVINCE.URL}")
   private String PROVINCE_URL;
   @Value("${PROVINCE.FILTER}")
   private String PROVINCE_FILTER;
   @Value("${PROVINCE.GETFEATURESURL}")
   private String PROVINCE_GETFEATURESURL;
   
   @Value("${CITTAMETROPOLITANE.URL}")
   private String CITTAMETROPOLITANE_URL;
   @Value("${CITTAMETROPOLITANE.FILTER}")
   private String CITTAMETROPOLITANE_FILTER;
   @Value("${CITTAMETROPOLITANE.GETFEATURESURL}")
   private String CITTAMETROPOLITANE_GETFEATURESURL;

   private static Map<String, ResponseItem> habitat = new HashMap();
   private static Map<String, ResponseItem> regioni = new HashMap();
   private static Map<String, ResponseItem> province = new HashMap();
   private static Map<String, ResponseItem> cittaMetropolitane = new HashMap();

   @GetMapping("/regprov")
   public ResponseEntity<Object> getSuggestions() {

      if (habitat.isEmpty()) {
         synchronized (this) {
            if (habitat.isEmpty()) {
               habitat = getHabitat(HABITAT_URL, HABITAT_FILTER, HABITAT_GETFEATURESURL);
            }
         }
      }

      if (regioni.isEmpty()) {
         synchronized (this) {
            if (regioni.isEmpty()) {
               regioni = getRegioni(REGIONI_URL, REGIONI_FILTER, REGIONI_GETFEATURESURL);
            }
         }
      }

      if (province.isEmpty()) {
         synchronized (this) {
            if (province.isEmpty()) {
               province = getProvince(PROVINCE_URL, PROVINCE_FILTER, PROVINCE_GETFEATURESURL);
            }
         }
      }

      if (cittaMetropolitane.isEmpty()) {
         synchronized (this) {
            if (cittaMetropolitane.isEmpty()) {
               cittaMetropolitane = getCittaMetropolitane(CITTAMETROPOLITANE_URL, CITTAMETROPOLITANE_FILTER, CITTAMETROPOLITANE_GETFEATURESURL);
            }
         }
      }

      Response retValue = new Response();
      retValue.getItems().addAll(regioni.values());
      retValue.getItems().addAll(province.values());
      retValue.getItems().addAll(cittaMetropolitane.values());
      retValue.getItems().addAll(habitat.values());
      logger.info("ritorno " + retValue.getItems().size() + " items");
      return new ResponseEntity<>(retValue, HttpStatus.OK);
   }

   private Map<String, ResponseItem> getHabitat(String url, String filter, String getFeaturesUrl) {
      logger.info("recupero gli habitat ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      FeaturesCollection features = restTemplate.getForObject(url,
            FeaturesCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCodice(),
                     new ResponseItem(item.getProperties().getCodice(),
                           item.getProperties().getCodice() + " " + item.getProperties().getDescrizione(),
                           "habitat",
                           String.format(filter, item.getProperties().getCodice()),
                           String.format(getFeaturesUrl, item.getProperties().getCodice())
                     )
               );
            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   private Map<String, ResponseItem> getRegioni(String url, String filter, String getFeaturesUrl) {
      logger.info("recupero le regioni ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      FeaturesCollection features = restTemplate.getForObject(url,
            FeaturesCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCodice(),
                     new ResponseItem(item.getProperties().getCodice(),
                           item.getProperties().getDescrizione(),
                           "regione",
                           String.format(filter, item.getProperties().getDescrizione()),
                           String.format(getFeaturesUrl, item.getProperties().getDescrizione())
                     )
               );

            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   private Map<String, ResponseItem> getProvince(String url, String filter, String getFeaturesUrl) {
      logger.info("recupero le province ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      FeaturesCollection features = restTemplate.getForObject(url,
            FeaturesCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCodice(),
                     new ResponseItem(item.getProperties().getCodice(),
                           item.getProperties().getDescrizione(),
                           "provincia",
                           String.format(filter, item.getProperties().getCodice()),
                           String.format(getFeaturesUrl, item.getProperties().getCodice())
                     )
               );
            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   private Map<String, ResponseItem> getCittaMetropolitane(String url, String filter, String getFeaturesUrl) {
      logger.info("recupero le citta metropolitane ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      FeaturesCollection features = restTemplate.getForObject(url,
            FeaturesCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCodice(),
                     new ResponseItem(
                           item.getProperties().getCodice(),
                           item.getProperties().getDescrizione(),
                           "den_cmpro",
                           String.format(filter, item.getProperties().getCodice()),
                           String.format(getFeaturesUrl, item.getProperties().getCodice())
                     )
               );
            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   //
}
