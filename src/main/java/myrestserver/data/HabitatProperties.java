package myrestserver.data;

public class HabitatProperties {

   String cod_habita;
   String nome_habit;

   public String getCod_habita() {
      return cod_habita;
   }

   public void setCod_habita(String cod_habita) {
      this.cod_habita = cod_habita;
   }

   public String getNome_habit() {
      return nome_habit;
   }

   public void setNome_habit(String nome_habit) {
      this.nome_habit = nome_habit;
   }

   @Override
   public String toString() {
      return "{cod_habita=" + cod_habita + ", nome_habit=" + nome_habit + '}';
   }
   
   
}
