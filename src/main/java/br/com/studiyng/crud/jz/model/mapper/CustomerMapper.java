package br.com.studiyng.crud.jz.model.mapper;

import br.com.studiyng.crud.jz.model.dto.CustomerDTO;
import br.com.studiyng.crud.jz.model.entity.Customer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class, builder = @Builder(disableBuilder = true))
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    List<CustomerDTO> toDTO(List<Customer> customers);
    Customer toEntity(CustomerDTO dto);
    void updateEntityFromDTO(CustomerDTO dto, @MappingTarget Customer entity);

    @AfterMapping
    default void linkAddress(@MappingTarget Customer customer) {
        if (customer.getAddress() != null) {
            customer.getAddress().setCustomer(customer);
        }
    }

}
