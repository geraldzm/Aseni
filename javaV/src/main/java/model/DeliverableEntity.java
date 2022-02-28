package model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "deliverable", schema = "dbo", catalog = "aseni")
public class DeliverableEntity {
    private int deliverableId;
    private Date deadline;
    private int kpi;
    private Integer actionId;
    private Integer kpiTypeId;
    private Integer cantonId;
    private Integer planId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "deliverable_id", nullable = false)
    public int getDeliverableId() {
        return deliverableId;
    }

    public void setDeliverableId(int deliverableId) {
        this.deliverableId = deliverableId;
    }

    @Basic
    @Column(name = "deadline", nullable = false)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "kpi", nullable = false)
    public int getKpi() {
        return kpi;
    }

    public void setKpi(int kpi) {
        this.kpi = kpi;
    }

    @Basic
    @Column(name = "action_id", nullable = true)
    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    @Basic
    @Column(name = "kpi_type_id", nullable = true)
    public Integer getKpiTypeId() {
        return kpiTypeId;
    }

    public void setKpiTypeId(Integer kpiTypeId) {
        this.kpiTypeId = kpiTypeId;
    }

    @Basic
    @Column(name = "canton_id", nullable = true)
    public Integer getCantonId() {
        return cantonId;
    }

    public void setCantonId(Integer cantonId) {
        this.cantonId = cantonId;
    }

    @Basic
    @Column(name = "plan_id", nullable = true)
    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliverableEntity that = (DeliverableEntity) o;

        if (deliverableId != that.deliverableId) return false;
        if (kpi != that.kpi) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (actionId != null ? !actionId.equals(that.actionId) : that.actionId != null) return false;
        if (kpiTypeId != null ? !kpiTypeId.equals(that.kpiTypeId) : that.kpiTypeId != null) return false;
        if (cantonId != null ? !cantonId.equals(that.cantonId) : that.cantonId != null) return false;
        if (planId != null ? !planId.equals(that.planId) : that.planId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deliverableId;
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + kpi;
        result = 31 * result + (actionId != null ? actionId.hashCode() : 0);
        result = 31 * result + (kpiTypeId != null ? kpiTypeId.hashCode() : 0);
        result = 31 * result + (cantonId != null ? cantonId.hashCode() : 0);
        result = 31 * result + (planId != null ? planId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeliverableEntity{" +
                "deliverableId=" + deliverableId +
                ", deadline=" + deadline +
                ", kpi=" + kpi +
                ", actionId=" + actionId +
                ", kpiTypeId=" + kpiTypeId +
                ", cantonId=" + cantonId +
                ", planId=" + planId +
                '}';
    }
}
