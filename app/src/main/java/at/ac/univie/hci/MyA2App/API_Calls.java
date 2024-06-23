package at.ac.univie.hci.MyA2App;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;




//Class to store all API-call-functions for a generic usage
public class API_Calls {

    //filter artworks for a typed query
    public static List<Artwork> fetchQueryData(String query, int page) {
        List<Artwork> result = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://api.artic.edu/api/v1/artworks/search?q="
                        + query +"&fields=image_id,description,gallery_title,dimensions,medium_display,date_display,title,artist_title&limit=100&page="+page);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                //Convert the API answer to an json-object to work with
                JSONObject jsonResponse = new JSONObject(response.toString());
                //create a json-array at the data field to iterate the artworks
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                for(int i=0;i<dataArray.length();++i) {
                    JSONObject temp = dataArray.getJSONObject(i);

                    //Using the Jsoup-dependency to convert the html-formatted description
                    String description;
                    String descriptionHTML = temp.getString("description");
                    if(descriptionHTML.equals("null")){
                        description="No description.";
                    } else description = Jsoup.parse(descriptionHTML).text();
                    String title = temp.getString("title");
                    if (title.equals("null")) {
                        title = "Unknown title";
                    }
                    String artistTitle = temp.getString("artist_title");
                    if (artistTitle.equals("null")) {
                        artistTitle = "Unknown artist";
                    }
                    String imageCode = temp.getString("image_id");
                    String year = temp.getString("date_display");
                    if (year.equals("null")) {
                        year = "Unknown year";
                    }
                    String medium = temp.getString("medium_display");
                    if (medium.equals("null")) {
                        medium = "Unknown medium";
                    }
                    String dimensions = temp.getString("dimensions");
                    if (dimensions.equals("null")) {
                        dimensions = "Unknown dimensions";
                    }
                    String gallery = temp.getString("gallery_title");
                    if(gallery.equals("null")){
                        gallery = "Unknown gallery";
                    }
                    String imageUrl = "https://www.artic.edu/iiif/2/" + imageCode + "/full/843,/0/default.jpg";
                    Artwork tmp = new Artwork(imageUrl,title, artistTitle,description,year,medium,dimensions,gallery);
                    result.add(tmp);
                }
            } catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        future.join();

        return result;
    }


//filter artworks for the selected artist
    public static List<Artwork> filterArtistsWorks(ArtistOrGallery artist, int page) {
        List<Artwork> result = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://api.artic.edu/api/v1/artworks/search?query[term][artist_id]="
                        +artist.getId()+"&fields=image_id,dimensions,gallery_title,description,medium_display,date_display,title,artist_title&page="+page);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                //Convert the API answer to an json-object to work with
                JSONObject jsonResponse = new JSONObject(response.toString());
                //create a json-array at the data field to iterate the artworks
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); ++i) {
                    String alt_text;
                    JSONObject temp = dataArray.getJSONObject(i);

                    //Using the Jsoup-dependency to convert the html-formatted description
                    String description;
                    String descriptionHTML = temp.getString("description");
                    if(descriptionHTML.equals("null")){
                        description="No description.";
                    } else description = Jsoup.parse(descriptionHTML).text();
                    String title = temp.getString("title");
                    if (title.equals("null")) {
                        title = "Unknown title";
                    }
                    String artistTitle = temp.getString("artist_title");
                    if (artistTitle.equals("null")) {
                        artistTitle = "Unknown artist";
                    }
                    String imageCode = temp.getString("image_id");
                    String year = temp.getString("date_display");
                    if (year.equals("null")) {
                        year = "Unknown year";
                    }
                    String medium = temp.getString("medium_display");
                    if (medium.equals("null")) {
                        medium = "Unknown medium";
                    }
                    String dimensions = temp.getString("dimensions");
                    if (dimensions.equals("null")) {
                        dimensions = "Unknown dimensions";
                    }
                    String gallery = temp.getString("gallery_title");
                    if(gallery.equals("null")){
                        gallery = "Unknown gallery";
                    }
                    String imageUrl = "https://www.artic.edu/iiif/2/" + imageCode + "/full/843,/0/default.jpg";

                    Artwork tmp = new Artwork(imageUrl,title,artistTitle,description,year,medium,dimensions,gallery);
                    result.add(tmp);
                }
                reader.close();
                connection.disconnect();
            } catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        future.join();
        return result;
    }

    //filter artworks for the selected gallery
    public static List<Artwork> filterGallerysWorks(ArtistOrGallery gallery, int page) {
        List<Artwork> result = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://api.artic.edu/api/v1/artworks/search?query[term][gallery_id]="
                        +gallery.getId()+"&fields=image_id,dimensions,gallery_title,description,medium_display,date_display,title,artist_title&page="+page);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                //Convert the API answer to an json-object to work with
                JSONObject jsonResponse = new JSONObject(response.toString());
                //create a json-array at the data field to iterate the artworks
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); ++i) {
                    String alt_text;
                    JSONObject temp = dataArray.getJSONObject(i);

                    //Using the Jsoup-dependency to convert the html-formatted description
                    String description;
                    String descriptionHTML = temp.getString("description");
                    if(descriptionHTML.equals("null")){
                        description="No description.";
                    } else description = Jsoup.parse(descriptionHTML).text();
                    String title = temp.getString("title");
                    if (title.equals("null")) {
                        title = "Unknown title";
                    }
                    String artistTitle = temp.getString("artist_title");
                    if (artistTitle.equals("null")) {
                        artistTitle = "Unknown artist";
                    }
                    String imageCode = temp.getString("image_id");
                    String year = temp.getString("date_display");
                    if (year.equals("null")) {
                        year = "Unknown year";
                    }
                    String medium = temp.getString("medium_display");
                    if (medium.equals("null")) {
                        medium = "Unknown medium";
                    }
                    String dimensions = temp.getString("dimensions");
                    if (dimensions.equals("null")) {
                        dimensions = "Unknown dimensions";
                    }
                    String gallery_title = temp.getString("gallery_title");
                    if(gallery_title.equals("null")){
                        gallery_title = "Unknown gallery";
                    }
                    String imageUrl = "https://www.artic.edu/iiif/2/" + imageCode + "/full/843,/0/default.jpg";

                    Artwork tmp = new Artwork(imageUrl,title,artistTitle,description,year,medium,dimensions,gallery_title);
                    result.add(tmp);
                }
                reader.close();
                connection.disconnect();
            } catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        future.join();
        return result;
    }

    //filter artworks for the selected date
    public static List<Artwork> filterSelectedDate(String selectedDate, int page) {
        List<Artwork> result = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://api.artic.edu/api/v1/artworks/search?query[match][date_end]="
                        +selectedDate+"&fields=image_id,description,gallery_title,dimensions,medium_display,date_display,title,artist_title&page="+page);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                //Convert the API answer to an json-object to work with
                JSONObject jsonResponse = new JSONObject(response.toString());
                //create a json-array at the data field to iterate the artworks
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); ++i) {
                    String alt_text;
                    JSONObject temp = dataArray.getJSONObject(i);
                    String description;

                    //Using the Jsoup-dependency to convert the html-formatted description
                    String descriptionHTML = temp.getString("description");
                    if(descriptionHTML.equals("null")){
                        description="No description.";
                    } else description = Jsoup.parse(descriptionHTML).text();
                    String title = temp.getString("title");
                    if (title.equals("null")) {
                        title = "Unknown title";
                    }
                    String artistTitle = temp.getString("artist_title");
                    if (artistTitle.equals("null")) {
                        artistTitle = "Unknown artist";
                    }
                    String imageCode = temp.getString("image_id");
                    String year = temp.getString("date_display");
                    if (year.equals("null")) {
                        year = "Unknown year";
                    }
                    String medium = temp.getString("medium_display");
                    if (medium.equals("null")) {
                        medium = "Unknown medium";
                    }
                    String dimensions = temp.getString("dimensions");
                    if (dimensions.equals("null")) {
                        dimensions = "Unknown dimensions";
                    }
                    String gallery = temp.getString("gallery_title");
                    if(gallery.equals("null")){
                        gallery = "Unknown gallery";
                    }

                    String imageUrl = "https://www.artic.edu/iiif/2/" + imageCode + "/full/843,/0/default.jpg";

                    Artwork tmp = new Artwork(imageUrl,title,artistTitle,description,year,medium,dimensions,gallery);
                    result.add(tmp);
                }
                reader.close();
                connection.disconnect();
            } catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        future.join();
        return result;
    }


//filter typed artists
    public static List<ArtistOrGallery> searchForArtists(String query) {
        List<ArtistOrGallery> result = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://api.artic.edu/api/v1/agents/search?query[match][title]="+query);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); ++i) {
                    JSONObject temp = dataArray.getJSONObject(i);
                    String artist_name=temp.getString("title");
                    int id=temp.getInt("id");
                    ArtistOrGallery tmp = new ArtistOrGallery(artist_name,id);
                        result.add(tmp);
                }

                reader.close();
                connection.disconnect();
            } catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        future.join();
        return result;
    }

    //filter typed galleries
    public static List<ArtistOrGallery> searchForGalleries(int page) {
        List<ArtistOrGallery> result = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://api.artic.edu/api/v1/galleries?limit=100&page="+page);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); ++i) {
                    JSONObject temp = dataArray.getJSONObject(i);
                    String gallery_name=temp.getString("title");
                    int id=temp.getInt("id");
                    ArtistOrGallery tmp = new ArtistOrGallery(gallery_name,id);
                    result.add(tmp);
                }

                reader.close();
                connection.disconnect();
            } catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        future.join();
        return result;
    }

    public static void loadImageAsync(String imageUrl, ImageView imageID) {
        CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                input.close();
                connection.disconnect();
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).thenAcceptAsync(bitmap -> {
            if (bitmap != null) {
                new Handler(Looper.getMainLooper()).post(() ->
                        imageID.setImageBitmap(bitmap));
            }
        });
    }


}
