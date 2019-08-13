package devloafer.app.taco.cloud.repository;

import devloafer.app.taco.cloud.domains.Ingredient;

public interface IngredientRepository {

    public Iterable<Ingredient> findAll();

    public Ingredient findById(String id);

    public Ingredient save(Ingredient ingredient);
}
