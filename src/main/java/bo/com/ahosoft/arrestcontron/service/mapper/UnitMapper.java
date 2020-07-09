package bo.com.ahosoft.arrestcontron.service.mapper;


import bo.com.ahosoft.arrestcontron.domain.*;
import bo.com.ahosoft.arrestcontron.service.dto.UnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Unit toEntity(UnitDTO dto);

    default Unit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(id);
        return unit;
    }
}
