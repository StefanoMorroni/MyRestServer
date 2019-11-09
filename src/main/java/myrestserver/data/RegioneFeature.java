package myrestserver.data;

public class RegioneFeature {

   String type;
   String id;
   RegioneProperties properties;

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

   public RegioneProperties getProperties() {
      return properties;
   }

   public void setProperties(RegioneProperties properties) {
      this.properties = properties;
   }

   @Override
   public String toString() {
      return "{type=" + type + ", id=" + id + ", properties=" + properties + '}';
   }
   
   
   
}
