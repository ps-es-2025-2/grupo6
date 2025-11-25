package model;

public class CheckoutRepositorio extends Repositorio<Checkout, Integer> {

    public CheckoutRepositorio(Database database) {
        super(database, Checkout.class);
    }
}

