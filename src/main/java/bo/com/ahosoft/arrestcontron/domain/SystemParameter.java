package bo.com.ahosoft.arrestcontron.domain;

import bo.com.ahosoft.arrestcontron.domain.enumeration.TypeSystemParameter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A SystemParameter.
 */
@Entity
@Table(name = "system_parameter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SystemParameter extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "value")
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeSystemParameter type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public SystemParameter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public SystemParameter value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TypeSystemParameter getType() {
        return type;
    }

    public SystemParameter type(TypeSystemParameter type) {
        this.type = type;
        return this;
    }

    public void setType(TypeSystemParameter type) {
        this.type = type;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemParameter)) {
            return false;
        }
        return id != null && id.equals(((SystemParameter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SystemParameter{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
