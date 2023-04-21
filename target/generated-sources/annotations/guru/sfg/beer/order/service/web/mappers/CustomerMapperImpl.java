package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.Customer;
import guru.sfg.beer.order.service.domain.Customer.CustomerBuilder;
import guru.sfg.common.CustomerDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-17T18:22:31+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public CustomerDto customerToDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setId( customer.getId() );
        if ( customer.getVersion() != null ) {
            customerDto.setVersion( customer.getVersion().intValue() );
        }
        customerDto.setCreatedDate( dateMapper.asOffsetDateTime( customer.getCreatedDate() ) );
        customerDto.setLastModifiedDate( dateMapper.asOffsetDateTime( customer.getLastModifiedDate() ) );
        customerDto.setCustomerName( customer.getCustomerName() );

        return customerDto;
    }

    @Override
    public Customer dtoToCustomer(CustomerDto dto) {
        if ( dto == null ) {
            return null;
        }

        CustomerBuilder customer = Customer.builder();

        customer.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            customer.version( dto.getVersion().longValue() );
        }
        customer.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        customer.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        customer.customerName( dto.getCustomerName() );

        return customer.build();
    }
}
