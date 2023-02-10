package com.example.cashprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * OutcomeActivity
 * Outcome screen of cash provider app with form for adding new outcomes
 * Last-Change: 02.01.2022
 *
 * @author Antonius Metry Saad
 */
public class OutcomeActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome);
        
        editText=(EditText) findViewById(R.id.et_dateOutcome);
        myCalendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        myCalendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        myCalendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        updateLabel();

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(OutcomeActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ImageButton backFromOutcomeToMainButton = (ImageButton) findViewById(R.id.btn_backFromOutcome);
        backFromOutcomeToMainButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backFromOutcomeToMain();
            }
        });

        ImageButton addOutcomeButton = (ImageButton) findViewById(R.id.btn_addOutcome);
        addOutcomeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addOutcome();
            }
        });


    }

    private void updateLabel(){
        String myFormat="dd.MM.yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.GERMANY);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void addOutcome() {
        EditText date = (EditText) findViewById(R.id.et_dateOutcome);
        EditText title = (EditText) findViewById(R.id.et_titleOutcome);
        EditText amount = (EditText) findViewById(R.id.et_amountOutcome);
        boolean allFilled = true;

        if (TextUtils.isEmpty(date.getText())) {
            Toast.makeText(this, "Date is required!", Toast.LENGTH_SHORT).show();
            date.setError("Date is required!");
            allFilled = false;
        }

        if (TextUtils.isEmpty(title.getText())) {
            Toast.makeText(this, "Title is required!", Toast.LENGTH_SHORT).show();
            title.setError("Title is required!");
            allFilled = false;
        }

        if (TextUtils.isEmpty(amount.getText())) {
            Toast.makeText(this, "Amount is required!", Toast.LENGTH_SHORT).show();
            amount.setError("Amount is required!");
            allFilled = false;
        }

        if (allFilled) {
            Outcome outcome = new Outcome(date.getText().toString(), title.getText().toString(), Double.parseDouble(amount.getText().toString().replace(',', '.')));
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("outcome", outcome);
            startActivity(i);
        }
    }

    private void backFromOutcomeToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
