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
   private static Map<String, ResponseItem> responseItems = new HashMap();

   @GetMapping("/regprov")
   public ResponseEntity<Object> getSuggestions() {

      if (responseItems.isEmpty()) {
         synchronized (this) {
            if (responseItems.isEmpty()) {
               //getHabitat();
               getRegioni();
            }
         }
      }

      logger.info("ritorno " + responseItems.size() + " items");
      Response retValue = new Response();
      retValue.setItems(responseItems.values());
      return new ResponseEntity<>(retValue, HttpStatus.OK);
   }

   private void getHabitat() {
      logger.info("recupero gli habitat ...");
      RestTemplate restTemplate = new RestTemplate();
      HabitatCollection features = restTemplate.getForObject("http://localhost:8080/geoserver/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=nnb:habitat_geom&outputFormat=application/json&propertyName=nome_habit,cod_habita",
            HabitatCollection.class);
      logger.info("features lette: " + features.getTotalFeatures());
      features.getFeatures()
            .forEach(item -> {
               responseItems.put(item.getProperties().getCod_habita(),
                     new ResponseItem(item.getProperties().getCod_habita(),
                           item.getProperties().getCod_habita() + " " + item.getProperties().getNome_habit(),
                           "habitat")
               );
            });
      logger.info("habitat presenti: " + responseItems.size() + " " + responseItems);
   }

   private void getRegioni() {
      logger.info("recupero le regioni ...");
      RestTemplate restTemplate = new RestTemplate();
      RegioniCollection features = restTemplate.getForObject("http://localhost:8080/geoserver/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=nnb:reg_2016_wgs84_g&outputFormat=application/json&propertyName=COD_REG,REGIONE",
            RegioniCollection.class);
      logger.info("features lette: " + features);
      features.getFeatures()
            .forEach(item -> {
               responseItems.put(item.getProperties().getCOD_REG(),
                     new ResponseItem(item.getProperties().getCOD_REG(),
                           item.getProperties().getREGIONE(),
                           "regione")
               );
               
            });
      logger.info("regioni presenti: " + responseItems.size() + " " + responseItems);
   }

}
