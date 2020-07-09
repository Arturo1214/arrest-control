package bo.com.ahosoft.arrestcontron.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link bo.com.ahosoft.arrestcontron.domain.Office} entity.
 */
public class OfficeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long unitId;

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

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfficeDTO officeDTO = (OfficeDTO) o;
        if (officeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), officeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfficeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", unitId=" + getUnitId() +
            "}";
    }
}
