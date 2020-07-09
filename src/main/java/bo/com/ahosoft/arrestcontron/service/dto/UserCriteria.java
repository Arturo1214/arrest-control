package bo.com.ahosoft.arrestcontron.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

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
public class UserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter login;

    private StringFilter fullName;

    private StringFilter telephone;

    private StringFilter email;

    private LongFilter unitId;

    private LongFilter officeId;

    public UserCriteria() {
    }

    public UserCriteria(UserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.officeId = other.officeId == null ? null : other.officeId.copy();
    }

    @Override
    public UserCriteria copy() {
        return new UserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getUnitId() {
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
    }

    public LongFilter getOfficeId() {
        return officeId;
    }

    public void setOfficeId(LongFilter officeId) {
        this.officeId = officeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCriteria that = (UserCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(login, that.login) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(officeId, that.officeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, fullName, telephone, email, unitId, officeId);
    }

    @Override
    public String toString() {
        return "UserCriteria{" +
            "id=" + id +
            ", login=" + login +
            ", fullName=" + fullName +
            ", telephone=" + telephone +
            ", email=" + email +
            ", unitId=" + unitId +
            ", officeId=" + officeId +
            '}';
    }
}
