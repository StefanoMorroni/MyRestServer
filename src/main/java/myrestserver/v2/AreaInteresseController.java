package myrestserver.v2;

import java.util.HashMap;
import java.util.Map;
import myrestserver.data.FeaturesCollection;
import myrestserver.data.Response;
import myrestserver.data.ResponseItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v2")
public class AreaInteresseController {

	private static final Logger logger = LoggerFactory.getLogger(AreaInteresseController.class);

	@Value("${SICZPS.URL}")
	private String SICZPS_URL;
	@Value("${SICZPS.FILTER}")
	private String SICZPS_FILTER;
	@Value("${SICZPS.GETFEATURESURL}")
	private String SICZPS_GETFEATURESURL;

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

	@Value("${COMUNI.URL}")
	private String COMUNI_URL;
	@Value("${COMUNI.FILTER}")
	private String COMUNI_FILTER;
	@Value("${COMUNI.GETFEATURESURL}")
	private String COMUNI_GETFEATURESURL;

	private static Map<String, ResponseItem> siczps = new HashMap();
	private static Map<String, ResponseItem> regioni = new HashMap();
	private static Map<String, ResponseItem> province = new HashMap();
	private static Map<String, ResponseItem> cittaMetropolitane = new HashMap();
	private static Map<String, ResponseItem> comuni = new HashMap();

	@GetMapping("/regprov")
	public ResponseEntity<Object> getSuggestions() {

		if (siczps.isEmpty()) {
			synchronized (this) {
				if (siczps.isEmpty()) {
					siczps = getSicZps(SICZPS_URL, SICZPS_FILTER, SICZPS_GETFEATURESURL);
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

		if (comuni.isEmpty()) {
			synchronized (this) {
				if (comuni.isEmpty()) {
					comuni = getComuni(COMUNI_URL, COMUNI_FILTER, COMUNI_GETFEATURESURL);
				}
			}
		}

		Response retValue = new Response();
		retValue.getItems().addAll(regioni.values());
		retValue.getItems().addAll(province.values());
		retValue.getItems().addAll(cittaMetropolitane.values());
		retValue.getItems().addAll(comuni.values());
		retValue.getItems().addAll(siczps.values());
		logger.info("ritorno " + retValue.getItems().size() + " items");
		return new ResponseEntity<>(retValue, HttpStatus.OK);
	}

	private Map<String, ResponseItem> getSicZps(String url, String filter, String getFeaturesUrl) {
		logger.info("recupero i sic zps ...");
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
									"siczps",
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

	private Map<String, ResponseItem> getComuni(String url, String filter, String getFeaturesUrl) {
		logger.info("recupero i comuni ...");
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
									"comune",
									String.format(filter, item.getProperties().getDescrizione()),
									String.format(getFeaturesUrl, item.getProperties().getDescrizione())
							)
					);

				});
		logger.info("ritorno " + retValue.size() + " records");
		return retValue;
	}
}
