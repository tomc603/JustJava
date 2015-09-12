package com.postboxdinosaur.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int coffeeCount = 0;
    double coffeePrice = 2.75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayQuantity();
    }

    /**
     * This method is called when the Order button is clicked
     */
    public void orderClicked(View view) {
//        displayQuantity(1);
//        displayPrice();
    }

    public void quantityIncrement(View view) {
        updateQuantity(1);
    }

    public void quantityDecrement(View view) {
        updateQuantity(-1);
    }

    private void updateQuantity(int number) {
        coffeeCount += number;
        if (coffeeCount < 0) {
            coffeeCount = 0;
        }
        displayQuantity();
        displayPrice();
    }

    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);

        quantityTextView.setText("" + coffeeCount);
        displayPrice();
    }

    private void displayPrice() {
        TextView priceTextView = (TextView) findViewById(
                R.id.price_text_view);

        priceTextView.setText(NumberFormat.getCurrencyInstance().format(coffeePrice * coffeeCount));
    }
}
