package bo.com.ahosoft.arrestcontron.service.dto;

import bo.com.ahosoft.arrestcontron.domain.TypeCase;
import bo.com.ahosoft.arrestcontron.web.rest.TypeCaseResource;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link TypeCase} entity. This class is used
 * in {@link TypeCaseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipe-cases?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeCaseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    public TypeCaseCriteria() {
    }

    public TypeCaseCriteria(TypeCaseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
    }

    @Override
    public TypeCaseCriteria copy() {
        return new TypeCaseCriteria(this);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeCaseCriteria that = (TypeCaseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name
        );
    }

    @Override
    public String toString() {
        return "TipeCaseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
            "}";
    }

}
