package myrestserver.data;

public class ResponseItem {

   String codice;
   String label;
   String sublabel;
   String intersectfilter;

   public ResponseItem() {
   }

   public ResponseItem(String codice, String label, String sublabel, String intersectfilter) {
      this.codice = codice;
      this.label = label;
      this.sublabel = sublabel;
      this.intersectfilter = intersectfilter;
   }

   public String getCodice() {
      return codice;
   }

   public void setCodice(String codice) {
      this.codice = codice;
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getSublabel() {
      return sublabel;
   }

   public void setSublabel(String sublabel) {
      this.sublabel = sublabel;
   }

   public String getIntersectfilter() {
      return intersectfilter;
   }

   public void setIntersectfilter(String intersectfilter) {
      this.intersectfilter = intersectfilter;
   }
}
