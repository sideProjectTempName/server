package com.tripplannerai.repository.destination;

import com.tripplannerai.dto.response.destination.DestinationQuery;
import com.tripplannerai.entity.destination.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    @Query(value = "select d.contentId from destination d order by d.destination_id asc limit :limit offset :offset"
            ,nativeQuery = true)
    List<DestinationQuery> fetchDestinations(int offset, int limit);
    @Query(value = "select d.contentId from destination d left join category c where c.name = :category order by d.destination_id asc limit :limit offset :offset"
            ,nativeQuery = true)
    List<DestinationQuery> fetchDestinationByCategory(int offset,int limit,String category);
    //TODO : 쿼리 작성
    List<DestinationQuery> fetchDestinationsByTotal();
    //TODO : count Query
    @Query(value = ""
            ,nativeQuery = true)
    int fetchDestinationsCount(Integer page, Integer size);
}
