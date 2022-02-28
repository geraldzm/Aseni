package model;

import javax.persistence.*;

@Entity
@Table(name = "political_party", schema = "dbo", catalog = "aseni")
public class PoliticalPartyEntity {
    private int ppId;
    private String name;
    private Integer flagImage;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pp_id", nullable = false)
    public int getPpId() {
        return ppId;
    }

    public void setPpId(int ppId) {
        this.ppId = ppId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "flag_image", nullable = true)
    public Integer getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(Integer flagImage) {
        this.flagImage = flagImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticalPartyEntity that = (PoliticalPartyEntity) o;

        if (ppId != that.ppId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (flagImage != null ? !flagImage.equals(that.flagImage) : that.flagImage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ppId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (flagImage != null ? flagImage.hashCode() : 0);
        return result;
    }
}
