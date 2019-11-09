package myrestserver.data;

import java.util.List;

public class HabitatCollection {
   String type;
   Long totalFeatures;   
   List<HabitatFeature> features;

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public Long getTotalFeatures() {
      return totalFeatures;
   }

   public void setTotalFeatures(Long totalFeatures) {
      this.totalFeatures = totalFeatures;
   }

   public List<HabitatFeature> getFeatures() {
      return features;
   }

   public void setFeatures(List<HabitatFeature> features) {
      this.features = features;
   }
   
   @Override
   public String toString() {
      return "{type=" + type + ", totalFeatures=" + totalFeatures + ", features=" + features + '}';
   }   
}
