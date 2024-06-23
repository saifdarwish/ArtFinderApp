package at.ac.univie.hci.MyA2App;


//simple class to hold artists or galleries name and id
public class ArtistOrGallery {
    public String name;
    public int id;

    public ArtistOrGallery(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
