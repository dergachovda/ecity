package ua.org.ecity.repository;

import org.springframework.data.repository.CrudRepository;
import ua.org.ecity.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
