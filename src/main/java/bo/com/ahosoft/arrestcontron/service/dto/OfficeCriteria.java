package bo.com.ahosoft.arrestcontron.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link bo.com.ahosoft.arrestcontron.domain.Office} entity. This class is used
 * in {@link bo.com.ahosoft.arrestcontron.web.rest.OfficeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OfficeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter unitId;

    public OfficeCriteria() {
    }

    public OfficeCriteria(OfficeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
    }

    @Override
    public OfficeCriteria copy() {
        return new OfficeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getUnitId() {
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OfficeCriteria that = (OfficeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(unitId, that.unitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        unitId
        );
    }

    @Override
    public String toString() {
        return "OfficeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (unitId != null ? "unitId=" + unitId + ", " : "") +
            "}";
    }

}
