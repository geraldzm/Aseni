package model;

import javax.persistence.*;

@Entity
@Table(name = "[user]", schema = "dbo", catalog = "aseni")
public class UserEntity {
    private int userId;
    private String name;
    private String bio;
    private Integer rolId;
    private Integer politicalPartyId;
    private Integer foto;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    @Column(name = "bio", nullable = true, length = 256)
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Basic
    @Column(name = "rol_id", nullable = true)
    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    @Basic
    @Column(name = "political_party_id", nullable = true)
    public Integer getPoliticalPartyId() {
        return politicalPartyId;
    }

    public void setPoliticalPartyId(Integer politicalPartyId) {
        this.politicalPartyId = politicalPartyId;
    }

    @Basic
    @Column(name = "foto", nullable = true)
    public Integer getFoto() {
        return foto;
    }

    public void setFoto(Integer foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (bio != null ? !bio.equals(that.bio) : that.bio != null) return false;
        if (rolId != null ? !rolId.equals(that.rolId) : that.rolId != null) return false;
        if (politicalPartyId != null ? !politicalPartyId.equals(that.politicalPartyId) : that.politicalPartyId != null)
            return false;
        if (foto != null ? !foto.equals(that.foto) : that.foto != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (rolId != null ? rolId.hashCode() : 0);
        result = 31 * result + (politicalPartyId != null ? politicalPartyId.hashCode() : 0);
        result = 31 * result + (foto != null ? foto.hashCode() : 0);
        return result;
    }
}
