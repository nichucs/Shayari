package cs.nizam.shayari.model;

/**
 * Created by nizamcs on 26/6/16.
 */
public class Category {
    int id; String category;

    public Category() {
        //Default constructor
    }

    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
