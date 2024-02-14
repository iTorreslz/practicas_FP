package org.iesbelen.mapstruct;

import org.iesbelen.dto.ClienteDTO;
import org.iesbelen.dto.ComercialDTO;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ComercialMapper {

    ComercialMapper INSTANCE = Mappers.getMapper( ComercialMapper.class );
    @Mapping(source = "pedidosCliente", target = "pedidosCliente")
    ComercialDTO comercialAComercialDTO(Comercial comercial, int pedidosCliente);

}