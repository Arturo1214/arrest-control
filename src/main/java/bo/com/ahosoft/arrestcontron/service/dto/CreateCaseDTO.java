package bo.com.ahosoft.arrestcontron.service.dto;

import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateRegister;
import lombok.Getter;
import lombok.Setter;

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
@Getter @Setter
public class CreateCaseDTO implements Serializable {

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
    private Long typeCaseId;

    @DecimalMax(value = "9999999999.99999999")
    private BigDecimal latitude;

    @DecimalMax(value = "9999999999.99999999")
    private BigDecimal longitude;

    @Size(max = 255)
    private String zone;

    @Size(max = 255)
    private String acronymPatrol;

    @Size(max = 255)
    private String patrolLeader;

    @Size(max = 255)
    private String supportPatrol;

    @NotNull
    private Long unitId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreateCaseDTO registerCaseDTO = (CreateCaseDTO) o;
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
            ", typeCase='" + getTypeCaseId() + "'" +
            ", informer='" + getInformer() + "'" +
            ", phone='" + getPhone() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
