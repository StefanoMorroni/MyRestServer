package myrestserver.data;

import java.util.Collection;

public class Response {
   Collection<ResponseItem> items;

   public Collection<ResponseItem> getItems() {
      return items;
   }

   public void setItems(Collection<ResponseItem> items) {
      this.items = items;
   }   
}
