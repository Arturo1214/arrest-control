package bo.com.ahosoft.arrestcontron.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link bo.vulcan.superpago.domain.TipeCase} entity.
 */
public class TypeCaseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

        TypeCaseDTO typeCaseDTO = (TypeCaseDTO) o;
        if (typeCaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeCaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipeCaseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
