package Country.code;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Country_Code")
@DynamicUpdate
public class CountryCode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "countryName")
    private String countryName;

    @Column(name = "countryCodeOne")
    private String countryCodeOne;

    @Column(name = "countryCodeTwo")
    private String countryCodeTwo;

    @Column(name = "doNotFollow")
    private int doNotFollow;

    public int getDoNotFollow() {
        return doNotFollow;
    }

    public void setDoNotFollow(int doNotFollow) {
        this.doNotFollow = doNotFollow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCodeOne() {
        return countryCodeOne;
    }

    public void setCountryCodeOne(String countryCodeOne) {
        this.countryCodeOne = countryCodeOne;
    }

    public String getCountryCodeTwo() {
        return countryCodeTwo;
    }

    public void setCountryCodeTwo(String countryCodeTwo) {
        this.countryCodeTwo = countryCodeTwo;
    }
}
