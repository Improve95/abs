package ru.improve.abs.processing.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.improve.abs.processing.service.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAll();

    Optional<Role> findByName(String name);
}
