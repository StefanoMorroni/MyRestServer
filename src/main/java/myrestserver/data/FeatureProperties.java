package myrestserver.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureProperties {

   @JsonProperty("codice")
   @JsonAlias({"cod_habita","cod_habitat","COD_REG", "COD_PRO", "COD_CM", "cod_reg", "cod_pro", "cod_cm", "pro_com"})
   String codice;

   @JsonProperty("descrizione")
   @JsonAlias({"denominazi","nome_habit","nome_habitat","REGIONE", "PROVINCIA", "DEN_CMPRO", "regione", "provincia", "den_cmpro", "comune"})
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
