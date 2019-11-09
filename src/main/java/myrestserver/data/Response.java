package myrestserver.data;

import java.util.ArrayList;
import java.util.Collection;

public class Response {
   Collection<ResponseItem> items = new ArrayList();

   public Collection<ResponseItem> getItems() {
      return items;
   }

   public void setItems(Collection<ResponseItem> items) {
      this.items = items;
   }   
}
