package br.com.studiyng.crud.jz.model.mapper;

import br.com.studiyng.crud.jz.model.dto.AddressDTO;
import br.com.studiyng.crud.jz.model.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO toDTO(Address address);
    Address toEntity(AddressDTO dto);
}

