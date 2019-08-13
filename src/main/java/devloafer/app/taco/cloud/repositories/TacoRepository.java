package devloafer.app.taco.cloud.repositories;

import devloafer.app.taco.cloud.domains.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
