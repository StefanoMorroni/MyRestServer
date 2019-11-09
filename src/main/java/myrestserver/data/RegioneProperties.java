package myrestserver.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegioneProperties {

   @JsonProperty("codice")
   @JsonAlias({"COD_REG", "COD_PRO", "COD_CM", "cod_reg", "cod_pro", "cod_cm"})
   String codice;

   @JsonProperty("descrizione")
   @JsonAlias({"REGIONE", "PROVINCIA", "DEN_CMPRO", "regione", "provincia", "den_cmpro"})
   String descrizione;

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

   @Override
   public String toString() {
      return "RegioneProperties{" + "codice=" + codice + ", descrizione=" + descrizione + '}';
   }
}
