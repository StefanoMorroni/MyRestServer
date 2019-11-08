package myrestserver.data;

public class TheFeature {

   String type;
   String id;
   TheProperties properties;

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

   public TheProperties getProperties() {
      return properties;
   }

   public void setProperties(TheProperties properties) {
      this.properties = properties;
   }

   @Override
   public String toString() {
      return "{type=" + type + ", id=" + id + ", properties=" + properties + '}';
   }
   
   
   
}
