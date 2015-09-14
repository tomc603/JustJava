/*
 *     Copyright 2015 Tom Cameron
 *
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 *
 */

package com.postboxdinosaur.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final double coffeePrice = 2.75;
    boolean quantityAlert = false;
    int coffeeCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Method", "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetQuantity();
    }

    /**
     * Handle Order button click
     *
     * @param view calling view
     */
    public void orderClicked(View view) {
        Log.d("Method", "orderClicked()");
        orderPlace();
    }

    /**
     * Handle topping checkbox checked
     *
     * @param view calling view
     */
    public void toppingChecked(View view) {
        Log.d("Method", "toppingChecked()");
        displayQuantity();
    }

    /**
     * Place the coffee order and give the user an order number.
     * Check for minimum and maximum coffee order quantity, reset the display after
     * the order is placed.
     */
    public void orderPlace() {
        Log.d("Method", "orderPlaced()");

        if (coffeeCount < getResources().getInteger(R.integer.min_coffee)) {
            Log.e("", "coffeeCount < minimum coffee order");
            displayMessage(String.format(Locale.getDefault(), getString(R.string.min_order_message), getResources().getInteger(R.integer.min_coffee)));
        } else if (coffeeCount > getResources().getInteger(R.integer.max_coffee)) {
            Log.e("", "coffeeCount > maximum coffee order");
            displayMessage(String.format(Locale.getDefault(), getString(R.string.max_order_message), getResources().getInteger(R.integer.max_coffee)));
        } else {
            Log.i("", "Order placed: " + coffeeCount + " coffee.");
            displayMessage(String.format(Locale.getDefault(), getString(R.string.order_thanks), 176));
        }
        resetQuantity();
    }

    /**
     * Handle decrease button click
     *
     * @param view calling view
     */
    public void decreaseQuantity(View view) {
        Log.d("Method", "decreaseQuantity()");
        updateQuantity(-1);
    }

    /**
     * Handle increase button click
     *
     * @param view calling view
     */
    public void increaseQuantity(View view) {
        Log.d("Method", "increaseQuantity()");
        updateQuantity(1);
    }

    /**
     * This method resets the order quantity, the display, and the alert for orders over 25 coffees
     */
    public void resetQuantity() {
        Log.d("Method", "resetQuantity()");
        CheckBox cb = (CheckBox) findViewById(R.id.whipped_checkbox_view);

        coffeeCount = getResources().getInteger(R.integer.min_coffee);
        quantityAlert = false;
        cb.setChecked(false);
        displayQuantity();
    }

    /**
     * Increases or decreases coffee count, checking for minimum (0) and maximum (100)
     * The user is alerted when they attempt to order more than the maximum, and they're
     * given a special message when they increase their order to more than 25.
     *
     * @param number Integer to add to coffeeCount
     */
    private void updateQuantity(int number) {
        Log.d("Method", "updateQuantity()");
        coffeeCount += number;
        if (coffeeCount < getResources().getInteger(R.integer.min_coffee)) {
            Log.w("", "coffeeCount < minimum coffee order. Resetting to " + getResources().getInteger(R.integer.min_coffee) + ".");
            coffeeCount = getResources().getInteger(R.integer.min_coffee);
        } else if ((coffeeCount > getResources().getInteger(R.integer.high_coffee_count)) && (!quantityAlert)) {
            Log.i("", "Alerting user about a high coffee count order.");
            displayMessage(getResources().getString(R.string.high_coffee_message));
            quantityAlert = true;
        } else if (coffeeCount > getResources().getInteger(R.integer.max_coffee)) {
            Log.w("", "coffeeCount > maximum coffee order. Resetting to " + getResources().getInteger(R.integer.max_coffee) + ".");
            coffeeCount = getResources().getInteger(R.integer.max_coffee);
            displayMessage(getResources().getString(R.string.max_coffee_message));
        }
        displayQuantity();
    }

    /**
     * Updates the quantity TextView and calls displayPrice() to update the price TextView
     */
    private void displayQuantity() {
        Log.d("Method", "displayQuantity()");
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);

        quantityTextView.setText(String.format(Locale.getDefault(), "%d", coffeeCount));
        displayTotal();
    }

    /**
     * Calculates the total cost of the order based on the current quantity.
     *
     * @return Total cost
     */
    private double calculateTotal() {
        Log.d("Method", "calculateTotal()");

        double extras = 0;
        CheckBox cb = (CheckBox) findViewById(R.id.whipped_checkbox_view);

        if (cb.isChecked()) {
            extras = coffeeCount * 0.75;
        }
        return (coffeeCount * coffeePrice) + extras;
    }

    /**
     * Updates the price TextView, formatting the price in the local currency.
     */
    private void displayTotal() {
        Log.d("Method", "displayTotal()");

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
        Log.d("Method", "displayMessage()");
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
