package myrestserver;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TassonomiaResponse {

   private List<String> ordo;
   private List<String> family;
   private List<String> genus;
   private List<String> canonicalname;
   private List<String> provider;

   public List<String> getOrdo() {
      return ordo;
   }

   public void setOrdo(List<String> ordo) {
      this.ordo = ordo;
   }

   public List<String> getFamily() {
      return family;
   }

   public void setFamily(List<String> family) {
      this.family = family;
   }

   public List<String> getGenus() {
      return genus;
   }

   public void setGenus(List<String> genus) {
      this.genus = genus;
   }

   public List<String> getCanonicalname() {
      return canonicalname;
   }

   public void setCanonicalname(List<String> canonicalname) {
      this.canonicalname = canonicalname;
   }

   public List<String> getProvider() {
      return provider;
   }

   public void setProvider(List<String> provider) {
      this.provider = provider;
   }
}
