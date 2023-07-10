package com.gamevh.reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, String>{
	List<Account> findByUsernameContaining(String search);

	@Query("SELECT account FROM Account AS account JOIN account.authority AS auth WHERE " +
			"(:username = '' or :username IS NULL or LOWER(account.username) like CONCAT('%',:username,'%')) AND" +
			"(:name = '' or :name IS NULL or LOWER(account.fullname) like CONCAT('%',:name,'%')) AND " +
			"(:roleId = '' or :roleId IS NULL or auth.role.id = :roleId)")
	Page<Account> filterAccountByUsernameAndNameAndRoleId(Pageable pageable, @Param("username") String username,
														  @Param("name") String name, @Param("roleId") String roleId);

	Page<Account> findById(Pageable pageable, Integer id);

	Page<Account> findByUsername(Pageable pageable, String username);

	Optional<Account> findByUsername(String userName);
}
