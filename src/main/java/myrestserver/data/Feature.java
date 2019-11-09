package myrestserver.data;

public class Feature {

   String type;
   String id;
   FeatureProperties properties;

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public FeatureProperties getProperties() {
      return properties;
   }

   public void setProperties(FeatureProperties properties) {
      this.properties = properties;
   }

   @Override
   public String toString() {
      return "{type=" + type + ", id=" + id + ", properties=" + properties + '}';
   }
   
   
   
}
