package org.iesbelen.mapstruct;

import org.iesbelen.dto.ClienteDTO;
import org.iesbelen.dto.ComercialDTO;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(source = "pedidosTrimestre", target = "pedidosTrimestre")
    @Mapping(source = "pedidosSemestre", target = "pedidosSemestre")
    @Mapping(source = "pedidosAnyo", target = "pedidosAnyo")
    @Mapping(source = "pedidosLustro", target = "pedidosLustro")
    ClienteDTO clienteAClienteDTO(Cliente cliente, int pedidosTrimestre, int pedidosSemestre, int pedidosAnyo, int pedidosLustro);

}