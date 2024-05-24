package qreol.project.bankingservice.repository.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import qreol.project.bankingservice.domain.User;

import java.time.LocalDate;

public class UserSpecifications {

    @NotNull
    @Contract(pure = true)
    public static Specification<User> dateOfBirthAfter(LocalDate dateOfBirth) {
        return (root, query, builder) -> builder.greaterThan(root.get("dateOfBirth"), dateOfBirth);
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, builder) -> builder.isMember(phoneNumber, root.get("phoneNumbers"));
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, builder) -> builder.isMember(email, root.get("emails"));
    }

    public static Specification<User> fullNameLike(String fullName) {
        return (root, query, builder) -> builder.like(root.get("fullName"), fullName + "%");
    }

}
