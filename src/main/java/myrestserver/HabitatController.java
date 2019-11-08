package myrestserver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import myrestserver.data.Habitat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import myrestserver.data.TheFeatureCollection;
import myrestserver.data.TheProperties;

@CrossOrigin(origins = "*")
@RestController
public class HabitatController {

   private static final Logger logger = LoggerFactory.getLogger(HabitatController.class);
   private static Map<String, TheProperties> habitat = new HashMap();

   @GetMapping("/habitat")
   public ResponseEntity<Object> getHabitat() {

      if (habitat.isEmpty()) {
         synchronized (this) {
            if (habitat.isEmpty()) {
               logger.info("recupero le features ...");
               RestTemplate restTemplate = new RestTemplate();
               TheFeatureCollection features = restTemplate.getForObject("http://localhost:8080/geoserver/wfs?service=wfs&version=2.0.0&request=GetFeature&typeNames=nnb:habitat_geom&outputFormat=application/json&propertyName=nome_habit,cod_habita",
                     TheFeatureCollection.class);
               logger.info("features lette: " + features.getTotalFeatures());
               features.getFeatures()
                     .forEach(item -> {
                        habitat.put(item.getProperties().getCod_habita(), item.getProperties());
                     });
               logger.info("habitat presenti: " + habitat.size() + " " + habitat);
            }
         }
      }

      logger.info("ritorno " + habitat.size() + " habitat");
      Habitat retValue = new Habitat();
      retValue.setHabitat(habitat.values());
      return new ResponseEntity<>(retValue, HttpStatus.OK);
   }

}
