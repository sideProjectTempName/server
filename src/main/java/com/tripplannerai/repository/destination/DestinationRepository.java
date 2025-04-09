package com.tripplannerai.repository.destination;

import com.tripplannerai.dto.response.destination.DestinationQuery;
import com.tripplannerai.dto.response.destination.DestinationTotalQuery;
import com.tripplannerai.entity.destination.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    @Query(value = "select d.contentId from destination order by destination_id asc limit :limit offset :offset"
            ,nativeQuery = true)
    List<DestinationQuery> fetchDestinations(int offset, int limit);
    @Query(value = "select d.contentId from " +
            "(select destination_id " +
            "from destination left join category " +
            "where category_name = :category order by destination_id asc limit :limit offset :offset) t " +
            "left join t.destination_id = destination.destination_id"
            ,nativeQuery = true)
    List<DestinationQuery> fetchDestinationByCategory(int offset,int limit,String category);
    @Query(value = "select id,category from ( " +
            "  select d.destination_id as id, ca.name as category, " +
            "         row_number() over (partition by ca.category_id) as rownum " +
            "  from destination d " +
            "  left join category ca on d.category_id = ca.id " +
            ") as subquery " +
            "where rownum between 1 and 11", nativeQuery = true)
    List<DestinationTotalQuery> fetchDestinationsByTotal();
    @Query(value = "select count(*) from (select destination_id from destination limit :limit) t"
            ,nativeQuery = true)
    int fetchDestinationsCount(int limit);
}
