package com.wellsfargo.LamaBackend.jpaRepos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.LamaBackend.entities.Role;
import com.wellsfargo.LamaBackend.entities.User;
import com.wellsfargo.LamaBackend.entities.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);

}
