package Country.website;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "city_website")
@DynamicUpdate
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "websiteCategoryId")
    private int websiteCategoryId;

    @Column(name = "cityId")
    private int cityId;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Column(name = "website")
    private String website;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getWebsiteCategoryId() {
        return websiteCategoryId;
    }

    public void setWebsiteCategoryId(int websiteCategoryId) {
        this.websiteCategoryId = websiteCategoryId;
    }

    @Override
    public String toString() {
        return "Website{" +
                "id=" + id +
                ", websiteCategoryId=" + websiteCategoryId +
                ", cityId=" + cityId +
                ", website='" + website + '\'' +
                '}';
    }
}