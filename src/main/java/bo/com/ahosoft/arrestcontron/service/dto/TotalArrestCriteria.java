package bo.com.ahosoft.arrestcontron.service.dto;

import bo.com.ahosoft.arrestcontron.domain.enumeration.ArrestType;
import bo.com.ahosoft.arrestcontron.domain.enumeration.VehicleType;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link bo.com.ahosoft.arrestcontron.domain.Arrest} entity. This class is used
 * in {@link bo.com.ahosoft.arrestcontron.web.rest.ArrestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /arrests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@Getter @Setter @ToString
public class TotalArrestCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private ZonedDateTimeFilter arrestDate;

    public TotalArrestCriteria() {
    }

    public TotalArrestCriteria(TotalArrestCriteria other) {
        this.arrestDate = other.arrestDate == null ? null : other.arrestDate.copy();
    }

    @Override
    public TotalArrestCriteria copy() {
        return new TotalArrestCriteria(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalArrestCriteria that = (TotalArrestCriteria) o;
        return Objects.equals(arrestDate, that.arrestDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrestDate);
    }
}
