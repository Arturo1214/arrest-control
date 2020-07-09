package bo.com.ahosoft.arrestcontron.domain;

import bo.com.ahosoft.arrestcontron.domain.enumeration.StateCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateRegister;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A RegisterCase.
 */
@Getter @Setter
@Entity
@Table(name = "register_case")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegisterCase extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private ZonedDateTime registrationDate;

    @NotNull
    @Size(max = 255)
    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @NotNull
    @Size(max = 255)
    @Column(name = "informer", length = 255, nullable = false)
    private String informer;

    @NotNull
    @Size(max = 255)
    @Column(name = "phone", length = 255, nullable = false)
    private String phone;

    @NotNull
    @Size(max = 1024)
    @Column(name = "description", length = 1024, nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StateRegister state;

    @Size(max = 255)
    @Column(name = "zone", length = 255)
    private String zone;

    @Size(max = 255)
    @Column(name = "acronym_patrol", length = 255)
    private String acronymPatrol;

    @Size(max = 255)
    @Column(name = "patrol_leader", length = 255)
    private String patrolLeader;

    @Size(max = 255)
    @Column(name = "support_patrol", length = 255)
    private String supportPatrol;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_case")
    private StateCase stateCase;

    @Size(max = 1024)
    @Column(name = "description_completion", length = 1024)
    private String descriptionCompletion;

    @DecimalMax(value = "9999999999.99999999")
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @DecimalMax(value = "9999999999.99999999")
    @Column(name = "longitude", precision = 10, scale = 8)
    private BigDecimal longitude;

    @Column(name = "check_date")
    private ZonedDateTime checkDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("registerCases")
    private User receptionist;

    @ManyToOne
    @JsonIgnoreProperties("registerCases")
    private Unit unit;

    @ManyToOne
    @JsonIgnoreProperties("registerCases")
    private User dispatcher;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("registerCases")
    private TypeCase typeCase;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegisterCase)) {
            return false;
        }
        return id != null && id.equals(((RegisterCase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegisterCase{" +
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
            ", supportPatrol='" + getSupportPatrol() + "'" +
            ", stateCase='" + getStateCase() + "'" +
            ", descriptionCompletion='" + getDescriptionCompletion() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", checkDate='" + getCheckDate() + "'" +
            "}";
    }
}
