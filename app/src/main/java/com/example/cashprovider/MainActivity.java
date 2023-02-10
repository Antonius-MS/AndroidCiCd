package com.example.cashprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * MainActivity
 * Main screen of cash provider app with table of incomes and outcomes and opportunities of adding and deleting data
 * Last-Change: 02.01.2022
 *
 * @author Antonius Metry Saad
 */
public class MainActivity extends AppCompatActivity {

    private static ArrayList<MoneyOperation> moneyOperationList = new ArrayList<MoneyOperation>() {
        {
            add(new Income("01.01.2022", "Salary", 1000));
            add(new Outcome("10.01.2022", "Laptop", 500));
        }
    };

    private void setTextViewText(int id, String text, TableRow row) {
        ((TextView) row.findViewById(id)).setText(text);
    }

    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if ((Income) getIntent().getSerializableExtra("income") != null) {
            Income income = (Income) getIntent().getSerializableExtra("income");
            moneyOperationList.add(income);
        } else if ((Outcome) getIntent().getSerializableExtra("outcome") != null) {
            Outcome outcome = (Outcome) getIntent().getSerializableExtra("outcome");
            moneyOperationList.add(outcome);
        }

        TableLayout table = (TableLayout) MainActivity.this.findViewById(R.id.tl_table);
        TableRow row = null;

        for (MoneyOperation m : moneyOperationList) {
            row = (TableRow) LayoutInflater.from(MainActivity.this).inflate(R.layout.attrib_row, null);
            setTextViewText(R.id.attrib_date, m.date, row);
            setTextViewText(R.id.attrib_title, m.title, row);
            setTextViewText(R.id.attrib_amount, Double.toString(m.amount) + "€", row);

            row.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View arg0) {
                    String[] data = new String[3];
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
                    for(int index = 0; index < ((ViewGroup) arg0).getChildCount(); index++) {
                        TextView nextChild = (TextView)(((ViewGroup) arg0).getChildAt(index));
                        data[index] = nextChild.getText().toString();
                    }
                    builder.setMessage("Do you want to delete following data?\n" + data[0] + " | " + data[1] + " | " + data[2]);
                    builder.setTitle("Confirmation of deletion");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            for (int i=0; i < moneyOperationList.size(); i++){
                                if (moneyOperationList.get(i).getDate().equals(data[0]) &&
                                        moneyOperationList.get(i).getTitle().equals(data[1]) &&
                                        Double.toString(moneyOperationList.get(i).getAmount()).equals(removeLastChar(data[2]))){
                                    moneyOperationList.remove(i);
                                    break;
                                }
                            }
                            table.removeView(arg0);
                            table.invalidate();
                            countTotal();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });
            table.addView(row);
        }
        table.requestLayout();

        countTotal();

        Button incomeButton = (Button) findViewById(R.id.btn_income);
        incomeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openIncome();
            }
        });

        Button outcomeButton = (Button) findViewById(R.id.btn_outcome);
        outcomeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openOutcome();
            }
        });

    }

    private void countTotal() {
        TableLayout table = (TableLayout) findViewById(R.id.tl_table);
        TextView total = (TextView) findViewById(R.id.tv_total);
        double totalSum = 0.0;
        boolean valid = true;

        for (int i = 0; i < table.getChildCount(); i++) {
            try {
                View view = table.getChildAt(i);
                TableRow r = (TableRow) view;
                TextView currentAmount = (TextView) r.getChildAt(2);
                String strCurrentAmount = currentAmount.getText().toString();
                totalSum += Double.parseDouble(strCurrentAmount.substring(0, strCurrentAmount.length() - 1));
            } catch (Exception ex) {
                total.setText("Could not be calculated!");
                valid = false;
                break;
            }
        }

        if (valid){
            DecimalFormat df = new DecimalFormat("####0.00");
            total.setText(df.format(totalSum).toString() + "€");
            total.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }
    }

    private void openIncome() {
        Intent intent = new Intent(this, IncomeActivity.class);
        startActivity(intent);
    }

    private void openOutcome() {
        Intent intent = new Intent(this, OutcomeActivity.class);
        startActivity(intent);
    }

}

