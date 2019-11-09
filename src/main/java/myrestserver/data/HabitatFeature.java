package myrestserver.data;

public class HabitatFeature {

   String type;
   String id;
   HabitatProperties properties;

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

   public HabitatProperties getProperties() {
      return properties;
   }

   public void setProperties(HabitatProperties properties) {
      this.properties = properties;
   }

   @Override
   public String toString() {
      return "{type=" + type + ", id=" + id + ", properties=" + properties + '}';
   }
   
   
   
}
