package com.postboxdinosaur.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final double coffeePrice = 2.75;
    boolean quantityAlert = false;
    int coffeeCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityReset();
    }

    /**
     * This method is called when the Order button is clicked
     */
    public void orderClicked(View view) {
        orderPlace();
    }

    /**
     * Place the coffee order and give the user an order number.
     * <p/>
     * Check for minimum and maximum coffee order quantity, reset the display after
     * the order is placed.
     */
    public void orderPlace() {
        Random r = new Random();

        if (coffeeCount < getResources().getInteger(R.integer.min_coffee)) {
            displayMessage(String.format(Locale.getDefault(), getString(R.string.min_order_message), getResources().getInteger(R.integer.min_coffee)));
        } else if (coffeeCount > getResources().getInteger(R.integer.max_coffee)) {
            displayMessage(String.format(Locale.getDefault(), getString(R.string.max_order_message), getResources().getInteger(R.integer.max_coffee)));
        } else {
            displayMessage(String.format(Locale.getDefault(), getString(R.string.order_thanks), r.nextInt()));
        }
        quantityReset();
    }

    /**
     * This method resets the order quantity, the display, and the alert for orders over 25 coffees
     */
    public void quantityReset() {
        coffeeCount = getResources().getInteger(R.integer.min_coffee);
        quantityAlert = false;
        displayQuantity();
    }

    /**
     * This method increases the coffee quantity by 1
     */
    public void quantityIncrement(View view) {
        updateQuantity(1);
    }

    /**
     * This method decreases the coffee quantity by 1
     */
    public void quantityDecrement(View view) {
        updateQuantity(-1);
    }

    /**
     * Increases or decreases coffee count, checking for minimum (0) and maximum (100)
     * <p/>
     * The user is alerted when they attempt to order more than the maximum, and they're
     * given a special message when they increase their order to more than 25.
     *
     * @param number Integer to add to coffeeCount
     */
    private void updateQuantity(int number) {
        coffeeCount += number;
        if (coffeeCount < getResources().getInteger(R.integer.min_coffee)) {
            coffeeCount = getResources().getInteger(R.integer.min_coffee);
        } else if ((coffeeCount > getResources().getInteger(R.integer.high_coffee_count)) && (!quantityAlert)) {
            displayMessage(getResources().getString(R.string.high_coffee_message));
            quantityAlert = true;
        } else if (coffeeCount > getResources().getInteger(R.integer.max_coffee)) {
            coffeeCount = getResources().getInteger(R.integer.max_coffee);
            displayMessage(getResources().getString(R.string.max_coffee_message));
        }
        displayQuantity();
    }

    /**
     * Calculates the total cost of the order based on the current quantity.
     *
     * @return Total cost
     */
    private double calculateTotal() {
        return coffeeCount * coffeePrice;
    }

    /**
     * Updates the quantity TextView and calls displayPrice() to update the price TextView
     */
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);

        quantityTextView.setText(String.format(Locale.getDefault(), "%d", coffeeCount));
        displayPrice();
    }

    /**
     * Updates the price TextView, formatting the price in the local currency.
     * <p/>
     * Price is calculated by the calculateTotal() method.
     */
    private void displayPrice() {
        TextView priceTextView = (TextView) findViewById(
                R.id.price_text_view);

        priceTextView.setText(NumberFormat.getCurrencyInstance().format(calculateTotal()));
    }

    /**
     * Displays a Toast to the user
     *
     * @param message Text of the Toast
     */
    private void displayMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
