package myrestserver.data;

public class ResponseItem {

   String codice;
   String descrizione;
   String type;

   public ResponseItem() {
   }

   public ResponseItem(String codice, String descrizione, String type) {
      this.codice = codice;
      this.descrizione = descrizione;
      this.type = type;
   }

   
   public String getCodice() {
      return codice;
   }

   public void setCodice(String codice) {
      this.codice = codice;
   }

   public String getDescrizione() {
      return descrizione;
   }

   public void setDescrizione(String descrizione) {
      this.descrizione = descrizione;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }
}
