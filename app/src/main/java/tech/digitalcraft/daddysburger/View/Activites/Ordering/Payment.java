package tech.digitalcraft.daddysburger.View.Activites.Ordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tech.digitalcraft.daddysburger.R;

public class Payment extends AppCompatActivity {


    TextView totalPrice;
    EditText cardNum,exDate,cvv;

    Button next;

    private ConstraintLayout back,front;
    float TOTAL_PRICE = 0;
    private boolean lock;
    ProgressDialog mProgressDialog;


    String CARD_NUMBER,EX_DATE,CVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("إستكمال الطلب");


        totalPrice = findViewById(R.id.total);
        cardNum = findViewById(R.id.card_number);
        exDate = findViewById(R.id.ex_date);
        cvv = findViewById(R.id.cvv);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        front = findViewById(R.id.front);

        mProgressDialog = new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        mProgressDialog.setMessage("جاري الطلب...");
        mProgressDialog.setCancelable(false);

        TOTAL_PRICE = getIntent().getExtras().getFloat("total");

        totalPrice.setText(TOTAL_PRICE + " جنية");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CARD_NUMBER = cardNum.getText().toString();
                EX_DATE = exDate.getText().toString();
                CVV = cvv.getText().toString();


                if (next.getText().equals("التالي"))
                {
                    if (CARD_NUMBER.isEmpty() || EX_DATE.isEmpty())
                        Toast.makeText(Payment.this, "تأكد من إدخال جميع البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                    else
                    {

                        front.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        next.setText("ابدأ الطلب");
                    }
                }
                else
                {
                    if (CVV.isEmpty())
                        Toast.makeText(Payment.this, "تأكد من إدخال جميع البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                    else
                    {
                        mProgressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                Toast.makeText(Payment.this, "تم الطلب بنجاح...", Toast.LENGTH_LONG).show();

                                mProgressDialog.dismiss();

                                finish();

                            }
                        }, 1500);

                    }
                }
            }
        });

        cardNum.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (lock || s.length() > 16) {
                    return;
                }
                lock = true;
                for (int i = 4; i < s.length(); i += 5) {
                    if (s.toString().charAt(i) != ' ') {
                        s.insert(i, " ");
                    }
                }
                lock = false;
//                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
//
//
//
//                 s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
//                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        exDate.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 5; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 4; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 3; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '/';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

    }


}