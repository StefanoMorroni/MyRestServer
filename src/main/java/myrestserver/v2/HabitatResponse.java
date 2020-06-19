package myrestserver.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitatResponse {

   private List<HabitatRecord> habitat;

	public List<HabitatRecord> getHabitat() {
		return habitat;
	}

	public void setHabitat(List<HabitatRecord> habitat) {
		this.habitat = habitat;
	}

}
