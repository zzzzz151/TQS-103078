package io.cucumber.skeleton;

public class Belly {
    public void eat(int cukes) {
        System.out.println("Ate " + cukes + " cukes");
    }

    public void wait(int seconds)
    {
        System.out.println("Waited " + seconds + " second(s) aka " + seconds/60 + " minute(s)" + " aka " + seconds/3600 + " hour(s)");
    }

    public void growl()
    {
        System.out.println("Belly growls");
    }

    
}
