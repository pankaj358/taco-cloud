package devloafer.app.taco.cloud.repository.impl;

import devloafer.app.taco.cloud.domains.Ingredient;
import devloafer.app.taco.cloud.domains.Taco;
import devloafer.app.taco.cloud.repository.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Repository
public class TacoRepositoryImpl implements TacoRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TacoRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco design) {
       long tacoId = saveTacoInfo(design);
       design.setId(tacoId);
       for(String  ingredientId : design.getIngredients())
       {
           saveIngredientToTaco(ingredientId, tacoId);
       }
       return design;
    }

    private void saveIngredientToTaco(String ingredientId, long tacoId){
      jdbcTemplate.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)", tacoId, ingredientId);
    }


    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values ( ?, ? ) ",
                Types.VARCHAR, Types.TIMESTAMP
        );
        pscFactory.setReturnGeneratedKeys(true);
           PreparedStatementCreator psc =  pscFactory.newPreparedStatementCreator(
                        Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
