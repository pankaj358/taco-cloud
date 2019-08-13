package devloafer.app.taco.cloud.controller;


import devloafer.app.taco.cloud.domains.Ingredient;
import devloafer.app.taco.cloud.domains.Order;
import devloafer.app.taco.cloud.domains.Taco;
import devloafer.app.taco.cloud.repository.IngredientRepository;
import devloafer.app.taco.cloud.repository.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static devloafer.app.taco.cloud.domains.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository){
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping
    public String showDesignForm(Model model){
        log.info("Design your taco...");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));
        Type[] types = Ingredient.Type.values();
        for(Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
           }
        model.addAttribute("design", new Taco());
      return  "design";
    }

    @ModelAttribute(name="order")
    public Order order(){
       return new Order();
    }

    @ModelAttribute(name="taco")
    public Taco taco(){
        return new Taco();
    }

    @PostMapping
    public String processDesign(@Valid  Taco design, Errors errors, @ModelAttribute Order order)
    {
        log.info("Processing your taco...");
        if(errors.hasErrors()) return "design";
        Taco saved = tacoRepository.save(design);
        order.setName(null);
        order.getTacoList().add(design);
      return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
          return ingredients.stream().filter( ingredient -> ingredient.getType().equals(type)).collect(Collectors.toList());
    }
}
