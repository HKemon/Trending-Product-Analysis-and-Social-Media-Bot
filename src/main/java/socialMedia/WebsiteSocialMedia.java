package socialMedia;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "website_Social_Media")
@DynamicUpdate
public class WebsiteSocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cityWebsiteId")
    private int cityWebsiteId;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "pinterest")
    private String pinterest;

    @Column(name = "tumblr")
    private String tumblr;

    @Column(name = "vk")
    private String vk;

    @Column(name = "ok")
    private String ok;

    @Column(name = "snapchat")
    private String snapchat;

    @Column(name = "youTube")
    private String youTube;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityWebsiteId() {
        return cityWebsiteId;
    }

    public void setCityWebsiteId(int cityWebsiteId) {
        this.cityWebsiteId = cityWebsiteId;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getPinterest() {
        return pinterest;
    }

    public void setPinterest(String pinterest) {
        this.pinterest = pinterest;
    }

    public String getTumblr() {
        return tumblr;
    }

    public void setTumblr(String tumblr) {
        this.tumblr = tumblr;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getYouTube() {
        return youTube;
    }

    public void setYouTube(String youTube) {
        this.youTube = youTube;
    }

    @Override
    public String toString() {
        return "WebsiteSocialMedia{" +
                    "id=" + id +
                    ", cityWebsiteId='" + cityWebsiteId + '\'' +
                    ", facebook='" + facebook + '\'' +
                    ", instagram='" + instagram + '\'' +
                    ", twitter='" + twitter + '\'' +
                    ", pinterest='" + pinterest + '\'' +
                    ", tumblr='" + tumblr + '\'' +
                    ", vk='" + vk + '\'' +
                    ", ok='" + ok + '\'' +
                    ", snapchat='" + snapchat + '\'' +
                    ", youTube='" + youTube + '\'' +
                '}';
    }
}
