package model;

import javax.persistence.*;

@Entity
@Table(name = "action", schema = "dbo", catalog = "aseni")
public class ActionEntity {
    private int actionId;
    private String action;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "action_id", nullable = false)
    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    @Basic
    @Column(name = "action", nullable = false, length = 512)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionEntity that = (ActionEntity) o;

        if (actionId != that.actionId) return false;
        if (action != null ? !action.equals(that.action) : that.action != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = actionId;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
