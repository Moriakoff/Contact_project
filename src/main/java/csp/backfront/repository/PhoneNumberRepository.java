package csp.backfront.repository;

import csp.backfront.model.entity.PhoneNumber;
import csp.backfront.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,Long> {

    List <PhoneNumber> findAllByUser(User user);

    PhoneNumber findPhoneNumberByPhoneNumber(String phoneNumber);

    void deleteAllByUser(User user);
}
