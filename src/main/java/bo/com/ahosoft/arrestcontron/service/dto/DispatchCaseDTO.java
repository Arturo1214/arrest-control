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
public class DispatchCaseDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 255)
    private String zone;

    @NotNull
    @Size(max = 255)
    private String acronymPatrol;

    @NotNull
    @Size(max = 255)
    private String patrolLeader;

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

        DispatchCaseDTO registerCaseDTO = (DispatchCaseDTO) o;
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
            ", zone='" + getZone() + "'" +
            ", acronymPatrol='" + getAcronymPatrol() + "'" +
            ", patrolLeader='" + getPatrolLeader() + "'" +
            ", unitId=" + getUnitId() +
            "}";
    }
}
