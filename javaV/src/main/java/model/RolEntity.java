package model;

import javax.persistence.*;

@Entity
@Table(name = "rol", schema = "dbo", catalog = "aseni")
public class RolEntity {
    private int rolId;
    private String name;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rol_id", nullable = false)
    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
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

        RolEntity rolEntity = (RolEntity) o;

        if (rolId != rolEntity.rolId) return false;
        if (name != null ? !name.equals(rolEntity.name) : rolEntity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rolId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
