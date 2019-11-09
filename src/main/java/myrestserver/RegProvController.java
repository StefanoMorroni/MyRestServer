package myrestserver;

import java.util.HashMap;
import java.util.Map;
import myrestserver.data.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import myrestserver.data.HabitatCollection;
import myrestserver.data.RegioniCollection;
import myrestserver.data.ResponseItem;

@CrossOrigin(origins = "*")
@RestController
public class RegProvController {

   private static final Logger logger = LoggerFactory.getLogger(RegProvController.class);
   private static Map<String, ResponseItem> habitat = new HashMap();
   private static Map<String, ResponseItem> regioni = new HashMap();
   private static Map<String, ResponseItem> province = new HashMap();
   private static Map<String, ResponseItem> cittaMetropolitane = new HashMap();

   @GetMapping("/regprov")
   public ResponseEntity<Object> getSuggestions() {

      if (habitat.isEmpty()) {
         synchronized (this) {
            if (habitat.isEmpty()) {
               habitat = getHabitat();
            }
         }
      }

      if (regioni.isEmpty()) {
         synchronized (this) {
            if (regioni.isEmpty()) {
               regioni = getRegioni();
            }
         }
      }
      
      if (province.isEmpty()) {
         synchronized (this) {
            if (province.isEmpty()) {
               province = getProvince();
            }
         }
      }

      if (cittaMetropolitane.isEmpty()) {
         synchronized (this) {
            if (cittaMetropolitane.isEmpty()) {
               cittaMetropolitane = getCittaMetropolitane();
            }
         }
      }
      
      Response retValue = new Response();
      retValue.getItems().addAll(habitat.values());
      retValue.getItems().addAll(regioni.values());
      retValue.getItems().addAll(province.values());
      retValue.getItems().addAll(cittaMetropolitane.values());
      logger.info("ritorno " + retValue.getItems().size() + " items");
      return new ResponseEntity<>(retValue, HttpStatus.OK);
   }

   private Map<String, ResponseItem> getHabitat() {
      logger.info("recupero gli habitat ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      HabitatCollection features = restTemplate.getForObject("http://localhost:8080/geoserver/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=nnb:habitat_geom&outputFormat=application/json&propertyName=nome_habit,cod_habita",
            HabitatCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCod_habita(),
                     new ResponseItem(item.getProperties().getCod_habita(),
                           item.getProperties().getCod_habita() + " " + item.getProperties().getNome_habit(),
                           "habitat")
               );
            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   private Map<String, ResponseItem> getRegioni() {
      logger.info("recupero le regioni ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      RegioniCollection features = restTemplate.getForObject("http://localhost:8080/geoserver/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=nnb:reg_2016_wgs84_g&outputFormat=application/json&propertyName=COD_REG,REGIONE",
            RegioniCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCodice(),
                     new ResponseItem(item.getProperties().getCodice(),
                           item.getProperties().getDescrizione(),
                           "regione")
               );

            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   private Map<String, ResponseItem> getProvince() {
      logger.info("recupero le province ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      RegioniCollection features = restTemplate.getForObject("http://localhost:8080/geoserver/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=nnb:cmprov2016_wgs84_g&outputFormat=application/json&CQL_FILTER=FLAG_CMPRO=2&propertyName=COD_PRO,PROVINCIA",
            RegioniCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCodice(),
                     new ResponseItem(item.getProperties().getCodice(),
                           item.getProperties().getDescrizione(),
                           "provincia")
               );
            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   private Map<String, ResponseItem> getCittaMetropolitane() {
      logger.info("recupero le citta metropolitane ...");
      Map<String, ResponseItem> retValue = new HashMap();
      RestTemplate restTemplate = new RestTemplate();
      RegioniCollection features = restTemplate.getForObject("http://localhost:8080/geoserver/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=nnb:cmprov2016_wgs84_g&outputFormat=application/json&CQL_FILTER=FLAG_CMPRO=1&propertyName=COD_CM,DEN_CMPRO",
            RegioniCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               retValue.put(item.getProperties().getCodice(),
                     new ResponseItem(item.getProperties().getCodice(),
                           item.getProperties().getDescrizione(),
                           "den_cmpro")
               );
            });
      logger.info("ritorno " + retValue.size() + " records");
      return retValue;
   }

   //
}
