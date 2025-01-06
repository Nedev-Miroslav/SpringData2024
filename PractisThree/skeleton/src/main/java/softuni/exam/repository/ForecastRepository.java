package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    Optional<Forecast> findByCityAndDayOfWeekLike(City city, String dayOfWeek);

    @Query(value = "SELECT f FROM Forecast f where f.dayOfWeek = 'SUNDAY' AND f.city.population < 150000 ORDER BY f.maxTemperature DESC, f.id ASC")
    List<Forecast> findAllByDayOfWeek_AndCity_PopulationOrderByMaxTemperatureDescIdAsc();
}
