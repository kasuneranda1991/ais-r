package Interface;

public interface Database {
    // create entry
    public Boolean create();
    
    // retrieve entry
    public Boolean get();
    
    // remove database entry
    public Boolean delete();
    
    // update database entry
    public Boolean update();
    
    // connect to a database
    public Boolean connect();

}
