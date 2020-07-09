package bo.com.ahosoft.arrestcontron.service.dto;

import bo.com.ahosoft.arrestcontron.domain.enumeration.ArrestType;
import bo.com.ahosoft.arrestcontron.domain.enumeration.VehicleType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link bo.com.ahosoft.arrestcontron.domain.Arrest} entity.
 */
public class ArrestDTO implements Serializable {

    private Long id;

    @NotNull
    private String documentNumber;

    @NotNull
    private String fullName;

    private String description;

    @NotNull
    private ArrestType type;

    private VehicleType vehicleType;

    private String plate;

    @NotNull
    private ZonedDateTime arrestDate;

    private Boolean withDriver;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrestType getType() {
        return type;
    }

    public void setType(ArrestType type) {
        this.type = type;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public ZonedDateTime getArrestDate() {
        return arrestDate;
    }

    public void setArrestDate(ZonedDateTime arrestDate) {
        this.arrestDate = arrestDate;
    }

    public Boolean getWithDriver() {
        return withDriver;
    }

    public void setWithDriver(Boolean withDriver) {
        this.withDriver = withDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArrestDTO arrestDTO = (ArrestDTO) o;
        if (arrestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), arrestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArrestDTO{" +
            "id=" + id +
            ", documentNumber='" + documentNumber + '\'' +
            ", fullName='" + fullName + '\'' +
            ", description='" + description + '\'' +
            ", type=" + type +
            ", vehicleType=" + vehicleType +
            ", plate='" + plate + '\'' +
            ", arrestDate=" + arrestDate +
            ", withDriver=" + withDriver +
            '}';
    }
}
