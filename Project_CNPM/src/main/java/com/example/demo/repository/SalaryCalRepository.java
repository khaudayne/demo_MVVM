package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.dto.SalaryCalDto;
import com.example.demo.entities.models.IBillProduct;
import com.example.demo.entities.models.ISalaryCal;

@Repository
public interface SalaryCalRepository extends JpaRepository<SalaryCalDto, String>{
	@Query(value = "select id, name, "
			+ "case when numwork is null then 0 "
			+ "else numwork * salary_per_day "
			+ "end as salary "
			+ "from ( "
			+ "	select id, name, salary_per_day from staff "
			+ ") as s left join ( "
			+ "	select staff_id, count(work_date) as numwork from timekeeping "
			+ "	where work_date between :dateStart and :dateEnd "
			+ "	group by staff_id "
			+ ") as tk on s.id = tk.staff_id;", nativeQuery = true)
	List<ISalaryCal> getAllSalaryCal(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);
	
	@Query(value = "select id, name, "
			+ "case when numwork is null then 0 "
			+ "else numwork * salary_per_day "
			+ "end as salary "
			+ "from ( "
			+ "	select id, name, salary_per_day from staff "
			+ " where id like %:stringSearch% or name like %:stringSearch% "
			+ ") as s left join ( "
			+ "	select staff_id, count(work_date) as numwork from timekeeping "
			+ "	where work_date between :dateStart and :dateEnd "
			+ "	group by staff_id "
			+ ") as tk on s.id = tk.staff_id;", nativeQuery = true)
	List<ISalaryCal> getSalaryBySearch(@Param("stringSearch") String stringSearch, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);
	
	@Query(value = "select work_date as name, sum(salary_per_day) as count "
			+ "from "
			+ "( "
			+ "select s.salary_per_day, t.work_date "
			+ "from "
			+ "( "
			+ "select id, salary_per_day "
			+ "from staff "
			+ ") as s "
			+ ", timekeeping as t "
			+ "where s.id = t.staff_id and t.work_date between :dateStart and :dateEnd "
			+ ") as t "
			+ "group by name "
			+ "order by name asc;", nativeQuery =  true)
	List<IBillProduct> getSalaryPerDay(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);
	
	@Query(value = "select date_format(work_date, '%Y-%m') as name, sum(salary_per_day) as count "
			+ "from "
			+ "( "
			+ "select s.salary_per_day, t.work_date "
			+ "from "
			+ "( "
			+ "select id, salary_per_day "
			+ "from staff "
			+ ") as s "
			+ ", timekeeping as t "
			+ "where s.id = t.staff_id and t.work_date between :dateStart and :dateEnd "
			+ ") as t "
			+ "group by name "
			+ "order by name asc;", nativeQuery =  true)
	List<IBillProduct> getSalaryPerMonth(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);
}
