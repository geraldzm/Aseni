package model;

import javax.persistence.*;

@Entity
@Table(name = "canton", schema = "dbo", catalog = "aseni")
public class CantonEntity {
    private int cantonId;
    private String name;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "canton_id", nullable = false)
    public int getCantonId() {
        return cantonId;
    }

    public void setCantonId(int cantonId) {
        this.cantonId = cantonId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CantonEntity that = (CantonEntity) o;

        if (cantonId != that.cantonId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cantonId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
