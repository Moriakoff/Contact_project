package csp.backfront.service;

import csp.backfront.exception.UserNotFoundException;
import csp.backfront.model.dto.AddressDto;
import csp.backfront.model.dto.ContactDto;
import csp.backfront.model.entity.Address;
import csp.backfront.model.entity.PhoneNumber;
import csp.backfront.model.entity.User;
import csp.backfront.repository.AddressRepository;
import csp.backfront.repository.PhoneNumberRepository;
import csp.backfront.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    @Transactional
    public void createContact(ContactDto contactDto) {
        User user = User.builder()
                .fullName(contactDto.getFulName())
                .email(contactDto.getEmail())
                .createdDate(LocalDateTime.now())
                .build();

        List <PhoneNumber> phoneNumbers = createPhoneNumbersFromContactDto(contactDto, user);

        List <Address> addresses = createAddressesFromContactDto(contactDto, user);

        userRepository.save(user);
        phoneNumberRepository.saveAll(phoneNumbers);
        addressRepository.saveAll(addresses);
    }

    private Address convertAddressDtoToAddress(User user, AddressDto address) {
        return new Address(0L, LocalDateTime.now(), address.getCountry(), address.getCity(), address.getStreet(),
                address.getHouseNumber(), address.getApartment(),user);
    }

    @Override
    @Transactional
    public void updateContact(Long id, ContactDto contactDto) {
        // TODO: 2019-06-11 Think how change phoneNumber and address without delete

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist by id"));

        List <PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByUser(user);
        List <Address> addresses = addressRepository.findAllByUser(user);

        phoneNumberRepository.deleteAll(phoneNumbers);
        addressRepository.deleteAll(addresses);

        User userToChange = User.builder()
                .id(user.getId())
                .fullName(contactDto.getFulName())
                .email(contactDto.getEmail())
                .createdDate(user.getCreatedDate())
                .build();

        List <PhoneNumber> phoneNumbersToChange = createPhoneNumbersFromContactDto(contactDto, user);

        List <Address> addressesToChange = createAddressesFromContactDto(contactDto, user);

        userRepository.save(userToChange);
        phoneNumberRepository.saveAll(phoneNumbersToChange);
        addressRepository.saveAll(addressesToChange);

    }

    private List <Address> createAddressesFromContactDto(ContactDto contactDto, User user) {
        return contactDto.getAddresses().stream()
                .map(address -> convertAddressDtoToAddress(user, address)).collect(Collectors.toList());
    }

    private List <PhoneNumber> createPhoneNumbersFromContactDto(ContactDto contactDto, User user) {
        return contactDto.getPhoneNumbers().stream()
                .map(phoneNumber -> new PhoneNumber(0L, LocalDateTime.now(), phoneNumber, user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteContact(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist by id"));

        phoneNumberRepository.deleteAllByUser(user);
        addressRepository.deleteAllByUser(user);
        userRepository.delete(user);

    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto getContact(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist by id"));

        List <PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByUser(user);
        List <Address> addresses = addressRepository.findAllByUser(user);

        ContactDto contactDto = ContactDto.builder()
                .fulName(user.getFullName())
                .email(user.getEmail())
                .phoneNumbers(phoneNumbers.stream().map(PhoneNumber::getPhoneNumber).collect(Collectors.toList()))
                .addresses(addresses.stream().map(address -> convertAddressToAddressDto(address)).collect(Collectors.toList()))
                .build();

        return contactDto;

    }

    private AddressDto convertAddressToAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .apartment(address.getApartment())
                .houseNumber(address.getHouseNumber())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List <ContactDto> getAllContacts() {
        List <Long> ids = userRepository.getAllIds();

        return ids.stream().map(id -> getContact(id)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto getContactByName(String name) {
        User user = userRepository.findUserByFullName(name);
        return getContact(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto getContactByPhoneNumber(String number) {
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(number);
        return getContact(phoneNumber.getUser().getId());
    }
}
