package myrestserver.v2;

public class TheData {

   private String value;
   private String source;

   public TheData(String value, String source) {
      this.value = value;
      this.source = source;
   }

   public TheData() {
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getSource() {
      return source;
   }

   public void setSource(String source) {
      this.source = source;
   }

}
