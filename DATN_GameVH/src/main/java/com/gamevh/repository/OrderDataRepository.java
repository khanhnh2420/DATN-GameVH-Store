package com.gamevh.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gamevh.entities.OrderData;
import org.springframework.data.repository.query.Param;

public interface OrderDataRepository extends JpaRepository<OrderData, Long> {

	@Query("SELECT o FROM OrderData o WHERE o.account.username = ?1")
	List<OrderData> findByUsernameContaining(String search);

	Optional<OrderData> findByOrderId(String orderId);
	@Query(value = "SELECT * FROM order_data order by create_date desc LIMIT 5",nativeQuery = true)
	List<OrderData> getTop5OrderDataOrderCreatedDate();

	@Query("SELECT o FROM OrderData o WHERE " +
			"(:username IS NULL or COALESCE(o.account.username, '') LIKE CONCAT('%', LOWER(:username), '%')) AND " +
			"(:phone IS NULL or COALESCE(o.phone, '') LIKE CONCAT('%', LOWER(:phone), '%')) AND " +
			"(:createdAt IS NULL or o.createDate = :createdAt)")
	Page<OrderData> findAllByFilterPagination(Pageable pageable ,@Param("username") String username, @Param("phone") String phone,
									@Param("createdAt") LocalDate createdAt);
}
