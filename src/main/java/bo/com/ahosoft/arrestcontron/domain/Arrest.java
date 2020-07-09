package bo.com.ahosoft.arrestcontron.domain;

import bo.com.ahosoft.arrestcontron.domain.enumeration.ArrestType;
import bo.com.ahosoft.arrestcontron.domain.enumeration.PaymentStatus;
import bo.com.ahosoft.arrestcontron.domain.enumeration.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Arrest.
 */
@Entity
@Table(name = "arrest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Arrest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ArrestType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "plate")
    private String plate;

    @NotNull
    @Column(name = "arrest_date", nullable = false)
    private ZonedDateTime arrestDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "state_description")
    private String stateDescription;

    @Column(name = "deposit_number")
    private String depositNumber;

    @Column(name = "with_driver")
    private Boolean withDriver;

    @ManyToOne(optional = false)
    @NotNull
    private Office office;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("arrests")
    private User userStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public Arrest documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public Arrest fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public Arrest description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrestType getType() {
        return type;
    }

    public Arrest type(ArrestType type) {
        this.type = type;
        return this;
    }

    public void setType(ArrestType type) {
        this.type = type;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Arrest vehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlate() {
        return plate;
    }

    public Arrest plate(String plate) {
        this.plate = plate;
        return this;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public ZonedDateTime getArrestDate() {
        return arrestDate;
    }

    public Arrest arrestDate(ZonedDateTime arrestDate) {
        this.arrestDate = arrestDate;
        return this;
    }

    public void setArrestDate(ZonedDateTime arrestDate) {
        this.arrestDate = arrestDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Arrest status(PaymentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public Arrest stateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
        return this;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public Arrest depositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
        return this;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public Boolean getWithDriver() {
        return withDriver;
    }

    public Arrest withDriver(Boolean withDriver) {
        this.withDriver = withDriver;
        return this;
    }

    public void setWithDriver(Boolean withDriver) {
        this.withDriver = withDriver;
    }

    public Office getOffice() {
        return office;
    }

    public Arrest office(Office office) {
        this.office = office;
        return this;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public User getUser() {
        return user;
    }

    public Arrest user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserStatus() {
        return userStatus;
    }

    public Arrest userStatus(User user) {
        this.userStatus = user;
        return this;
    }

    public void setUserStatus(User user) {
        this.userStatus = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arrest)) {
            return false;
        }
        return id != null && id.equals(((Arrest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Arrest{" +
            "id=" + getId() +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", vehicleType='" + getVehicleType() + "'" +
            ", plate='" + getPlate() + "'" +
            ", arrestDate='" + getArrestDate() + "'" +
            "}";
    }
}
