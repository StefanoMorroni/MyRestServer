package myrestserver.data;

public class RegioneProperties {

   String COD_REG;
   String REGIONE;

   public String getCOD_REG() {
      return COD_REG;
   }

   public void setCOD_REG(String COD_REG) {
      this.COD_REG = COD_REG;
   }

   public String getREGIONE() {
      return REGIONE;
   }

   public void setREGIONE(String REGIONE) {
      this.REGIONE = REGIONE;
   }

   @Override
   public String toString() {
      return "{COD_REG=" + COD_REG + ", REGIONE=" + REGIONE + '}';
   }
}
