package bo.com.ahosoft.arrestcontron.service.dto;


import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateRegister;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link RegisterCase} entity.
 */
public class RegisterCaseDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime registrationDate;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 255)
    private String informer;

    @NotNull
    @Size(max = 255)
    private String phone;

    @NotNull
    @Size(max = 1024)
    private String description;

    @NotNull
    private StateRegister state;

    @Size(max = 255)
    private String zone;

    @Size(max = 255)
    private String acronymPatrol;

    @Size(max = 255)
    private String patrolLeader;

    private StateCase stateCase;

    @Size(max = 1024)
    private String descriptionCompletion;

    @DecimalMax(value = "9999999999.99999999")
    private BigDecimal latitude;

    @DecimalMax(value = "9999999999.99999999")
    private BigDecimal longitude;


    private Long receptionistId;

    private String receptionistLogin;

    private Long unitId;

    private Long dispatcherId;

    private Long typeCaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInformer() {
        return informer;
    }

    public void setInformer(String informer) {
        this.informer = informer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StateRegister getState() {
        return state;
    }

    public void setState(StateRegister state) {
        this.state = state;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAcronymPatrol() {
        return acronymPatrol;
    }

    public void setAcronymPatrol(String acronymPatrol) {
        this.acronymPatrol = acronymPatrol;
    }

    public String getPatrolLeader() {
        return patrolLeader;
    }

    public void setPatrolLeader(String patrolLeader) {
        this.patrolLeader = patrolLeader;
    }

    public StateCase getStateCase() {
        return stateCase;
    }

    public void setStateCase(StateCase stateCase) {
        this.stateCase = stateCase;
    }

    public String getDescriptionCompletion() {
        return descriptionCompletion;
    }

    public void setDescriptionCompletion(String descriptionCompletion) {
        this.descriptionCompletion = descriptionCompletion;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(Long userId) {
        this.receptionistId = userId;
    }

    public String getReceptionistLogin() {
        return receptionistLogin;
    }

    public void setReceptionistLogin(String userLogin) {
        this.receptionistLogin = userLogin;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(Long userId) {
        this.dispatcherId = userId;
    }

    public Long getTypeCaseId() {
        return typeCaseId;
    }

    public void setTypeCaseId(Long typeCaseId) {
        this.typeCaseId = typeCaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegisterCaseDTO registerCaseDTO = (RegisterCaseDTO) o;
        if (registerCaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registerCaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegisterCaseDTO{" +
            "id=" + getId() +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", address='" + getAddress() + "'" +
            ", informer='" + getInformer() + "'" +
            ", phone='" + getPhone() + "'" +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            ", zone='" + getZone() + "'" +
            ", acronymPatrol='" + getAcronymPatrol() + "'" +
            ", patrolLeader='" + getPatrolLeader() + "'" +
            ", stateCase='" + getStateCase() + "'" +
            ", descriptionCompletion='" + getDescriptionCompletion() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", receptionistId=" + getReceptionistId() +
            ", receptionistLogin='" + getReceptionistLogin() + "'" +
            ", unitId=" + getUnitId() +
            ", dispatcherId=" + getDispatcherId() +
            ", typeCaseId=" + getTypeCaseId() +
            "}";
    }
}
