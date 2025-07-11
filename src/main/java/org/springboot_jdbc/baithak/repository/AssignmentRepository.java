package org.springboot_jdbc.baithak.repository;

import org.springboot_jdbc.baithak.model.Assignment;
import org.springboot_jdbc.baithak.model.places;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {

    @Query("SELECT COUNT(a) > 0 FROM Assignment a WHERE a.member.id = :memberId AND a.place.id = :placeId AND a.weekNumber >= :cutoffWeek")
    boolean existsRecentAssignment(@Param("memberId") UUID memberId, @Param("placeId") UUID placeId, @Param("cutoffWeek") int cutoffWeek);

    @Query("SELECT COUNT(a) > 0 FROM Assignment a WHERE a.member.id = :memberId AND a.weekNumber = :week")
    boolean wasAssignedThisWeek(@Param("memberId") UUID memberId, @Param("week") int week);

    @Query("""
    SELECT a FROM Assignment a 
    WHERE a.weekNumber = :week 
      AND a.place.vaarCode = :vaarCode
""")
    List<Assignment> findByWeekAndVaarCode(@Param("week") int week, @Param("vaarCode") int vaarCode);
    boolean existsByPlaceIdAndWeekNumber(UUID placeId, int weekNumber);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Assignment a " +
            "WHERE a.member.id = :memberId AND a.place.id = :placeId AND a.weekNumber BETWEEN :startWeek AND :endWeek")
    boolean existsRecentAssignment(@Param("memberId") UUID memberId,
                                   @Param("placeId") UUID placeId,
                                   @Param("startWeek") int startWeek,
                                   @Param("endWeek") int endWeek);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Assignment a " +
            "WHERE a.member.id = :memberId AND a.place.id = :placeId AND a.place.vaarCode = :vaarCode " +
            "AND a.weekNumber BETWEEN :startWeek AND :endWeek")
    boolean existsInVaarRangeLastWeeks(@Param("memberId") UUID memberId,
                                       @Param("placeId") UUID placeId,
                                       @Param("vaarCode") int vaarCode,
                                       @Param("startWeek") int startWeek,
                                       @Param("endWeek") int endWeek);

    @Query("""
    SELECT a FROM Assignment a
    WHERE a.weekNumber = :week AND a.place.vaarCode = :vaarCode
    AND LOWER(a.member.gender) = LOWER(:gender)
""")
    List<Assignment> findByWeekAndVaarCodeAndGender(@Param("week") int week,
                                                    @Param("vaarCode") int vaarCode,
                                                    @Param("gender") String gender);
        List<Assignment> findByWeekNumber(int weekNumber);
    @Query("""
SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
FROM Assignment a
WHERE a.member.id = :memberId 
  AND a.place.vaarName = :vaarName 
  AND a.weekNumber = :weekNumber
""")
    boolean existsByMemberIdAndVaarNameAndWeekNumber(
            @Param("memberId") UUID memberId,
            @Param("vaarName") String vaarName,
            @Param("weekNumber") int weekNumber);
    @Query("""
    SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
    FROM Assignment a
    WHERE a.member.id = :memberId 
      AND a.place.vaarCode = :vaarCode 
      AND a.weekNumber = :weekNumber
""")
    boolean existsAssignmentByMemberAndVaarCodeAndWeek(
            @Param("memberId") UUID memberId,
            @Param("vaarCode") int vaarCode,
            @Param("weekNumber") int weekNumber);


    @Query("""
    SELECT a.place 
    FROM Assignment a 
    WHERE a.member.id = :memberId 
      AND a.place.vaarCode = :vaarCode 
      AND a.weekNumber = :weekNumber
""")
    places findAssignedPlace(
            @Param("memberId") UUID memberId,
            @Param("vaarCode") int vaarCode,
            @Param("weekNumber") int weekNumber
    );


    List<Assignment> findByMemberNameOrderByWeekNumberAsc(String memberName);


}