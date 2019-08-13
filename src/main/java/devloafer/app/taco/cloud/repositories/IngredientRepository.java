package devloafer.app.taco.cloud.repositories;

import devloafer.app.taco.cloud.domains.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
