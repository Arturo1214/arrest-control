package bo.com.ahosoft.arrestcontron.service.mapper;


import bo.com.ahosoft.arrestcontron.domain.*;
import bo.com.ahosoft.arrestcontron.service.dto.OfficeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Office} and its DTO {@link OfficeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class})
public interface OfficeMapper extends EntityMapper<OfficeDTO, Office> {

    @Mapping(source = "unit.id", target = "unitId")
    OfficeDTO toDto(Office office);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(source = "unitId", target = "unit")
    Office toEntity(OfficeDTO officeDTO);

    default Office fromId(Long id) {
        if (id == null) {
            return null;
        }
        Office office = new Office();
        office.setId(id);
        return office;
    }
}
