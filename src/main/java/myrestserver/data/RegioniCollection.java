package myrestserver.data;

import java.util.List;

public class RegioniCollection {
   String type;
   Long totalFeatures;   
   List<RegioneFeature> features;

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

   public List<RegioneFeature> getFeatures() {
      return features;
   }

   public void setFeatures(List<RegioneFeature> features) {
      this.features = features;
   }
   
   @Override
   public String toString() {
      return "{type=" + type + ", totalFeatures=" + totalFeatures + ", features=" + features + '}';
   }   
}
