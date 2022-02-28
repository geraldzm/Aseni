package model;

import javax.persistence.*;

@Entity
@Table(name = "kpi_type", schema = "dbo", catalog = "aseni")
public class KpiTypeEntity {
    private int kpiTypeId;
    private String name;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "kpi_type_id", nullable = false)
    public int getKpiTypeId() {
        return kpiTypeId;
    }

    public void setKpiTypeId(int kpiTypeId) {
        this.kpiTypeId = kpiTypeId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 32)
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

        KpiTypeEntity that = (KpiTypeEntity) o;

        if (kpiTypeId != that.kpiTypeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kpiTypeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
