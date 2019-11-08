package myrestserver.data;

import java.util.List;

public class TheFeatureCollection {
   String type;
   Long totalFeatures;   
   List<TheFeature> features;

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

   public List<TheFeature> getFeatures() {
      return features;
   }

   public void setFeatures(List<TheFeature> features) {
      this.features = features;
   }
   
   @Override
   public String toString() {
      return "{type=" + type + ", totalFeatures=" + totalFeatures + ", features=" + features + '}';
   }   
}
