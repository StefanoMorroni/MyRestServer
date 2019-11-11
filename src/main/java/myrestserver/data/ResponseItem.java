package myrestserver.data;

public class ResponseItem {

   private String codice;
   private String label;
   private String sublabel;
   private String intersectfilter;
   private String wfsGetFeaturesUrl;

   public ResponseItem() {
   }

   public ResponseItem(String codice, String label, String sublabel, String intersectfilter, String wfsGetFeaturesUrl) {
      this.codice = codice;
      this.label = label;
      this.sublabel = sublabel;
      this.intersectfilter = intersectfilter;
      this.wfsGetFeaturesUrl = wfsGetFeaturesUrl;
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

   public String getWfsGetFeaturesUrl() {
      return wfsGetFeaturesUrl;
   }

   public void setWfsGetFeaturesUrl(String wfsGetFeaturesUrl) {
      this.wfsGetFeaturesUrl = wfsGetFeaturesUrl;
   }
}
