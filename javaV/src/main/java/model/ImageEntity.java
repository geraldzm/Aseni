package model;

import javax.persistence.*;

@Entity
@Table(name = "image", schema = "dbo", catalog = "aseni")
public class ImageEntity {
    private int imageId;
    private String url;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "image_id", nullable = false)
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 512)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        if (imageId != that.imageId) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageId;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
