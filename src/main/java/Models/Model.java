package Models;

import Controllers.Helpers.Helper;
//import lombok.Getter;
//
//@Getter
public class Model {
    private String id;
    
    public Model(){
//        if (this.id == null) {
//            System.out.println("id "+ this.id);
//            this.id = Helper.generateUID();
//        }
    }
    
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            "}";
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
