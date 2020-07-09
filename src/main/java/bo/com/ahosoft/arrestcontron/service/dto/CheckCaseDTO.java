package bo.com.ahosoft.arrestcontron.service.dto;

import bo.com.ahosoft.arrestcontron.domain.RegisterCase;
import bo.com.ahosoft.arrestcontron.domain.enumeration.StateCase;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link RegisterCase} entity.
 */
@Getter @Setter
public class CheckCaseDTO implements Serializable {

    @NotNull
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckCaseDTO registerCaseDTO = (CheckCaseDTO) o;
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
            "}";
    }
}
