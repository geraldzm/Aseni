package model;

import javax.persistence.*;

@Entity
@Table(name = "plan", schema = "dbo", catalog = "aseni")
public class PlanEntity {
    private int planId;
    private String title;
    private String description;
    private Integer ppId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "plan_id", nullable = false)
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 32)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "pp_id", nullable = true)
    public Integer getPpId() {
        return ppId;
    }

    public void setPpId(Integer ppId) {
        this.ppId = ppId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanEntity that = (PlanEntity) o;

        if (planId != that.planId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (ppId != null ? !ppId.equals(that.ppId) : that.ppId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = planId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (ppId != null ? ppId.hashCode() : 0);
        return result;
    }
}
