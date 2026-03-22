package hawk.hotel.dto; // Il est de bonne pratique de les mettre dans un package séparé

import hawk.hotel.domain.Continent;

public class HotelCreateDTO {

    private String name;
    private String description;
    private int rating;
    private String city;
    private Continent continent;

    public HotelCreateDTO() {  // Constructeur vide nécessaire pour la désérialisation JSON
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Continent getContinent() { return continent; }
    public void setContinent(Continent continent) { this.continent = continent; }
}