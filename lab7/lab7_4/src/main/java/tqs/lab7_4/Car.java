package tqs.lab7_4;

import java.util.Random;

import jakarta.persistence.*;;

@Entity
@Table(name = "Cars")
public class Car {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String maker, model;

    public Car() {
        Random random = new Random();
        int randomNumber = random.nextInt(1, Integer.MAX_VALUE);
        this.id = randomNumber;
        this.maker = "";
        this.model = "";
    }

    public Car(String maker, String model) {
        Random random = new Random();
        int randomNumber = random.nextInt(1, Integer.MAX_VALUE);
        this.id = randomNumber;
        this.maker = maker;
        this.model = model;
    }

    public Car(int id, String maker, String model) {
        this.id = id;
        this.maker = maker;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car [id=" + id + ", maker=" + maker + ", model=" + model + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((maker == null) ? 0 : maker.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        if (id != other.id)
            return false;
        if (maker == null) {
            if (other.maker != null)
                return false;
        } else if (!maker.equals(other.maker))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
