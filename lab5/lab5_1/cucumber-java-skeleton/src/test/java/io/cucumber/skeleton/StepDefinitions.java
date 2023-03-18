package io.cucumber.skeleton;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {
    @Given("I have {int} cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
        Belly belly = new Belly();
        belly.eat(cukes);
    }

    @When("I wait {int} hour")
    public void i_wait_hour(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        Belly belly = new Belly();
        belly.wait(int1*3600);
        //throw new io.cucumber.java.PendingException();
    }

    @Then("my belly should growl")
    public void my_belly_should_growl() {
        // Write code here that turns the phrase above into concrete actions
        Belly belly = new Belly();
        belly.growl();
        //throw new io.cucumber.java.PendingException();
    }

    private Calculator calc;
    
    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }
    
    @When("I add {int} to {int}")
    public void i_add_to(Integer int1, Integer int2) {
        calc.push(int1);
        calc.push(int2);
        calc.push("+");
    }
    
    @When("I subtract {int} from {int}")
    public void i_subtract_from(Integer int1, Integer int2) {
        calc.push(int1);
        calc.push(int2);
        calc.push("-");
    }
    
    @Then("the result is {int}")
    public void the_result_is(Integer expected) {
        assertEquals(expected, calc.value());
    }

    @Then("the result is {double}")
    public void the_result_is(Double expected) {
        assertEquals(expected, calc.value());
    }

}
