package org.dnyanyog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dnyanyog.common.EnglishResponseCodes;
import org.dnyanyog.dto.CustomerRequest;
import org.dnyanyog.dto.CustomerResponse;
import org.dnyanyog.dto.SearchCustomerResponse;
import org.dnyanyog.entity.Customer;
import org.dnyanyog.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CustomerServiceImpl implements CustomerService {

  @Autowired CustomerRepository customerRepo;

  @Autowired CustomerResponse customerResponse;

  @Autowired SearchCustomerResponse searchCustomerResponse;

  @Override
  public CustomerResponse addCustomerDetails(CustomerRequest customerRequest) {

    if (customerRepo.existsByEmail_id(customerRequest.getEmailID())) {
      customerResponse.setStatus(EnglishResponseCodes.ADD_CUSTOMER_DUPLICATE_EMAIL_CODE);
      customerResponse.setMessage(EnglishResponseCodes.ADD_CUSTOMER_DUPLICATE_EMAIL_MESSAGE);
      customerResponse.setCustomerCode(0000);
    } else {

      ArrayList al;

      Customer customerTable =
          Customer.getInstance()
              .setFirst_name(customerRequest.getFirstName())
              .setMiddle_name(customerRequest.getMiddleName())
              .setLast_name(customerRequest.getLastName())
              .setDate_of_birth(customerRequest.getDateOfBirth())
              .setAddress_line1(customerRequest.getAddressLine1())
              .setAddress_line2(customerRequest.getAddressLine2())
              .setZip(customerRequest.getZip())
              .setCity(customerRequest.getCity())
              .setState(customerRequest.getState())
              .setCountry(customerRequest.getCountry())
              .setMobile_phone(customerRequest.getMobilePhone())
              .setHome_phone(customerRequest.getHomePhone())
              .setWork_phone(customerRequest.getWorkPhone())
              .setEmail_id(customerRequest.getEmailID())
              .setCustomer_id(customerRequest.getCustomerId())
              .setCreated_date(LocalDateTime.now())
              .setUpdated_date(LocalDateTime.now());

      try {
        customerTable = customerRepo.save(customerTable);
      } catch (Exception e) {
        e.printStackTrace();
      }

      customerResponse.setStatus(EnglishResponseCodes.ADD_CUSTOMER_SUCCESS_CODE[0]);
      customerResponse.setMessage(EnglishResponseCodes.ADD_CUSTOMER_SUCCESS_CODE[1]);
      customerResponse.setCustomerCode(customerTable.getCustomer_code());
    }
    return customerResponse;
  }

  public CustomerResponse updateCustomerDetails(long id, CustomerRequest customerRequest) {

    Optional<Customer> customerTable = customerRepo.findById(id);
    if (customerTable.isEmpty()) {
      customerResponse.setStatus(EnglishResponseCodes.CUSTOMER_NOT_FOUND_CODE);
      customerResponse.setMessage(EnglishResponseCodes.CUSTOMER_NOT_FOUND_MESSAGE);
      customerResponse.setCustomerCode(0000);
    } else {
      Customer customer =
          Customer.getInstance()
              .setFirst_name(customerRequest.getFirstName())
              .setMiddle_name(customerRequest.getMiddleName())
              .setLast_name(customerRequest.getLastName())
              .setDate_of_birth(customerRequest.getDateOfBirth())
              .setAddress_line1(customerRequest.getAddressLine1())
              .setAddress_line2(customerRequest.getAddressLine2())
              .setZip(customerRequest.getZip())
              .setCity(customerRequest.getCity())
              .setState(customerRequest.getState())
              .setCountry(customerRequest.getCountry())
              .setMobile_phone(customerRequest.getMobilePhone())
              .setHome_phone(customerRequest.getHomePhone())
              .setWork_phone(customerRequest.getWorkPhone())
              .setEmail_id(customerRequest.getEmailID())
              .setCustomer_id(customerRequest.getCustomerId())
              .setCreated_date(LocalDateTime.now())
              .setUpdated_date(LocalDateTime.now());
    }

    return customerResponse;
  }

  public CustomerResponse findByMobileNumber(String mobile_number) {

    List<Customer> customerTable = customerRepo.findByMobile(mobile_number);
    if (customerTable.isEmpty()) {
      customerResponse.setStatus(EnglishResponseCodes.CUSTOMER_NOT_FOUND_CODE);
      customerResponse.setMessage(EnglishResponseCodes.CUSTOMER_NOT_FOUND_MESSAGE);
      customerResponse.setCustomerCode(0000);
    } else {
      Customer receivedData = customerTable.get(0);

      searchCustomerResponse.setStatus("Success");
      searchCustomerResponse.setMessage("Customer ddetails are as follows :");
      searchCustomerResponse.setCustomerCode(receivedData.getCustomer_code());
      searchCustomerResponse.getCustomerData().setFirstName(receivedData.getFirst_name());
      searchCustomerResponse.getCustomerData().setMiddleName(receivedData.getMiddle_name());
      searchCustomerResponse.getCustomerData().setMiddleName(receivedData.getLast_name());
      searchCustomerResponse.getCustomerData().setDateOfBirth(receivedData.getDate_of_birth());
      searchCustomerResponse.getCustomerData().setAddressLine1(receivedData.getAddress_line1());
      searchCustomerResponse.getCustomerData().setAddressLine2(receivedData.getAddress_line2());
      searchCustomerResponse.getCustomerData().setZip(receivedData.getZip());
      searchCustomerResponse.getCustomerData().setCity(receivedData.getCity());
      searchCustomerResponse.getCustomerData().setState(receivedData.getState());
      searchCustomerResponse.getCustomerData().setCountry(receivedData.getCountry());
      searchCustomerResponse.getCustomerData().setMobilePhone(receivedData.getMobile_phone());
      searchCustomerResponse.getCustomerData().setHomePhone(receivedData.getHome_phone());
      searchCustomerResponse.getCustomerData().setWorkPhone(receivedData.getWork_phone());
      searchCustomerResponse.getCustomerData().setEmailID(receivedData.getEmail_id());
    }

    return customerResponse;
  }
}
