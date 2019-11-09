package myrestserver.data;

import java.util.List;

public class FeaturesCollection {
   String type;
   Long totalFeatures;   
   List<Feature> features;

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

   public List<Feature> getFeatures() {
      return features;
   }

   public void setFeatures(List<Feature> features) {
      this.features = features;
   }
   
   @Override
   public String toString() {
      return "{type=" + type + ", totalFeatures=" + totalFeatures + ", features=" + features + '}';
   }   
}
