package com.example.demo.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.dto.BillProductDto;
import com.example.demo.entities.models.IBillProduct;
@Repository
public interface BillProductRepository extends JpaRepository<BillProductDto, String>{
	@Query(value = "select p.name, dd.count "
			+ "from "
			+ "( "
			+ "select bp.product_id as id, sum(bp.product_count) as count "
			+ "from ( "
			+ "select id "
			+ "from order_bill "
			+ "where buy_date between :dateStart and :dateEnd) as ob left join bill_product as bp on ob.id = bp.bill_id "
			+ "group by bp.product_id ) as dd, "
			+ "( "
			+ "select id, name "
			+ "from product "
			+ ") as p "
			+ "where dd.id = p.id;", nativeQuery = true)
	List<IBillProduct> getProductCount(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd);
	
	@Query(value = "select date_format(buy_date, '%d-%m-%Y') as name, sum(total) as count "
			+ "from order_bill as ob "
			+ "where ob.buy_date between :dateStart and :dateEnd "
			+ "group by name "
			+ "order by name asc;", nativeQuery = true)
	List<IBillProduct> getProfit(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd);
}
