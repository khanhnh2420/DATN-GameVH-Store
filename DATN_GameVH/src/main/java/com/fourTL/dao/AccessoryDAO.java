package com.fourTL.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourTL.DTO.AccessoryDTO;
import com.fourTL.entities.Accessory;

public interface AccessoryDAO extends JpaRepository<Accessory, Integer> {
	@Query(value = "SELECT a.id, a.name, a.poster, a.thumbnail, a.salePrice, a.offer, a.details, a.createDate, subquery.rate, subquery.countFeedBack "
			+ "FROM Accessory a " + "INNER JOIN ( "
			+ "    SELECT f.accessoryId AS accessoryId, AVG(f.star) AS rate, COUNT(f.accessoryId) AS countFeedBack "
			+ "    FROM FeedBack f " + "    WHERE f.accessoryId IS NOT NULL AND f.status = true" + " GROUP BY f.accessoryId "
			+ "    HAVING AVG(f.star) IS NOT NULL " + ") subquery ON a.id = subquery.accessoryId", nativeQuery = true)
	List<AccessoryDTO> findAccessoryFeedBack();
}
