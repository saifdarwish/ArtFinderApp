package at.ac.univie.hci.MyA2App;


//class to hold wished artwork-attributes
public class Artwork {
    private String imageURL;
    private String title;
    private String artist;
    private String description;
    private String year;
    private String medium;
    private String dimensions;
    private String gallery;


    public Artwork(String imageURL, String title, String artist, String description, String year, String medium, String dimensions,String gallery) {
        this.imageURL=imageURL;
        this.title=title;
        this.artist=artist;
        this.description=description;
        this.year=year;
        this.medium=medium;
        this.dimensions=dimensions;
        this.gallery=gallery;
    }


    public String getDimensions() {
        return dimensions;
    }


    public String getMedium() {
        return medium;
    }

    public String getGallery() {
        return gallery;
    }

    public String getYear() {
        return year;
    }


    public String getDescription() {
        return description;
    }


    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getArtist() {
        return artist;
    }


}
