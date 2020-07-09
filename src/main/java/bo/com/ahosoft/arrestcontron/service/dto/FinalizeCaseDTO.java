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
public class FinalizeCaseDTO implements Serializable {

    @NotNull
    private Long id;

    private StateCase stateCase;

    @Size(max = 1024)
    private String descriptionCompletion;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FinalizeCaseDTO registerCaseDTO = (FinalizeCaseDTO) o;
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
            ", stateCase='" + getStateCase() + "'" +
            ", descriptionCompletion='" + getDescriptionCompletion() + "'" +
            "}";
    }
}
