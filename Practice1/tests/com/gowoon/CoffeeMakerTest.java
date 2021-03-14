package com.gowoon;

import com.gowoon.exceptions.InventoryException;
import com.gowoon.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoffeeMakerTest {
    private CoffeeMaker coffeeMaker;
    String name;
    String price;
    String amtCoffee;
    String amtMilk;
    String amtSugar;
    String amtChocolate;
    Recipe recipe1;

    @Before
    public void setUp() throws Exception{
        coffeeMaker = new CoffeeMaker();

        name = "name1";
        price = "1000";
        amtCoffee = "1";
        amtMilk = "1";
        amtSugar = "1";
        amtChocolate = "1";

        recipe1 = new Recipe();
        recipe1.setName(name);
        recipe1.setPrice(price);
        recipe1.setAmtCoffee(amtCoffee);
        recipe1.setAmtMilk(amtMilk);
        recipe1.setAmtSugar(amtSugar);
        recipe1.setAmtChocolate(amtChocolate);
    }

    @Test
    public void addRecipe() {
        // addRecipe 성공 테스트
        assertTrue(coffeeMaker.addRecipe(recipe1));

        // 값 일치 테스트
        Recipe recipe = coffeeMaker.getRecipes()[0];
        assertEquals(name, recipe.getName());
        assertEquals(Integer.parseInt(price), recipe.getPrice());
        assertEquals(Integer.parseInt(amtCoffee), recipe.getAmtCoffee());
        assertEquals(Integer.parseInt(amtMilk), recipe.getAmtMilk());
        assertEquals(Integer.parseInt(amtSugar), recipe.getAmtSugar());
        assertEquals(Integer.parseInt(amtChocolate), recipe.getAmtChocolate());
    }

    @Test
    public void addRecipeAlreadyExistFail() {
        // 이미 존재하는 레시피 추가 실패 테스트
        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertTrue(coffeeMaker.addRecipe(recipe1));
    }

    @Test
    public void addRecipeInvalidTypeFail() throws RecipeException {
        // 가격과 커피, 우유, 설탕, 초콜릿의 양을 설정할 때 타입을 맞추지 않아 RecipeException 발생
        Recipe recipe2 = new Recipe();
        recipe1.setName("name2");
        recipe1.setPrice("a");
        recipe1.setAmtCoffee("a");
        recipe1.setAmtMilk("a");
        recipe1.setAmtSugar("a");
        recipe1.setAmtChocolate("a");
    }

    @Test
    public void addRecipeOutOfBoundFail() throws RecipeException {
        // recipeBook의 사이즈 초과시 실패 테스트
        Recipe recipe3 = new Recipe();
        recipe3.setName(name);
        recipe3.setPrice(price);
        recipe3.setAmtCoffee(amtCoffee);
        recipe3.setAmtMilk(amtMilk);
        recipe3.setAmtSugar(amtSugar);
        recipe3.setAmtChocolate(amtChocolate);
        Recipe recipe4 = new Recipe();
        recipe4.setName(name);
        recipe4.setPrice(price);
        recipe4.setAmtCoffee(amtCoffee);
        recipe4.setAmtMilk(amtMilk);
        recipe4.setAmtSugar(amtSugar);
        recipe4.setAmtChocolate(amtChocolate);
        Recipe recipe5 = new Recipe();
        recipe4.setName(name);
        recipe4.setPrice(price);
        recipe4.setAmtCoffee(amtCoffee);
        recipe4.setAmtMilk(amtMilk);
        recipe4.setAmtSugar(amtSugar);
        recipe4.setAmtChocolate(amtChocolate);

        assertTrue(coffeeMaker.addRecipe(recipe3));
        assertTrue(coffeeMaker.addRecipe(recipe4));
        assertTrue(coffeeMaker.addRecipe(recipe5));
    }


    @Test
    public void deleteRecipe() {
        // deleteRecipe 성공 테스트
        coffeeMaker.addRecipe(recipe1);
        assertEquals(name, coffeeMaker.getRecipes()[0].getName());
        assertEquals(name, coffeeMaker.deleteRecipe(0));
        assertEquals("", coffeeMaker.getRecipes()[0].getName());
    }

    @Test
    public void deleteRecipeUnExist(){
        // 존재하지 않는 Recipe delete시 null 반환
        assertEquals(null, coffeeMaker.deleteRecipe(0));
    }

    @Test
    public void editRecipe() throws RecipeException {
        // editRecipe 성공 테스트
        Recipe recipe2 = new Recipe();
        recipe2.setName("name2");
        recipe2.setPrice("2000");

        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.addRecipe(recipe2);

        Recipe[] recipes = coffeeMaker.getRecipes();

        assertEquals(1000,recipes[0].getPrice());
        assertEquals("name1",coffeeMaker.editRecipe(0, recipe2));
        assertEquals(2000,recipes[0].getPrice());
    }

    @Test
    public void editRecipeUnExist(){
        // 존재하지 않는 recipe 수정시 null 반환
        assertEquals(null, coffeeMaker.editRecipe(0, recipe1));
    }

    @Test
    public void addInventory() throws InventoryException {
        // addInventory 성공 테스트
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        coffeeMaker.addInventory("5","5","5","5");
        assertEquals("Coffee: 20\nMilk: 20\nSugar: 20\nChocolate: 20\n", coffeeMaker.checkInventory());
    }

    @Test
    public void addInventoryNegativeInteger() throws InventoryException {
        coffeeMaker.addInventory("-5","5","5","5");
        coffeeMaker.addInventory("a","5","5","5");
    }
    @Test
    public void addInventoryInvalidType() throws InventoryException {
        coffeeMaker.addInventory("-5","5","5","5");
        coffeeMaker.addInventory("a","5","5","5");
    }

    @Test
    public void checkInventory() throws InventoryException {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        coffeeMaker.addInventory("5","5","5","5");
        assertEquals("Coffee: 20\nMilk: 20\nSugar: 20\nChocolate: 20\n", coffeeMaker.checkInventory());
    }

    @Test
    public void makeCoffeee() {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.makeCoffee(0, 1000);
        assertEquals("Coffee: 14\nMilk: 14\nSugar: 14\nChocolate: 14\n", coffeeMaker.checkInventory());
    }

    @Test
    public void makeCoffeeUnExistRecipe(){
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.makeCoffee(1, 1000);
    }

    @Test
    public void makeCoffeeLowerPrice(){
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.makeCoffee(0, 500);
    }

    @Test
    public void makeCoffeeChange() {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        coffeeMaker.addRecipe(recipe1);
        assertEquals(200, coffeeMaker.makeCoffee(0, 1200));
    }

    @Test
    public void getRecipes() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
    }
}