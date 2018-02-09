package android.jjdeveloper.com.lienzo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar tamPincelSeekBar;
    private Pintar lienzoView;
    private TextView TamPincelNum;
    private Spinner spinnerPincelColor;
    private int SIZE_PINCEL = 10;
    private String dColor;
    private int COLOR = Color.BLACK;

    private void init(){
        lienzoView = findViewById(R.id.lienzoView);
        tamPincelSeekBar = findViewById(R.id.TamPincelSeekBar);
        TamPincelNum = findViewById(R.id.TamPincelNum);
        spinnerPincelColor = findViewById(R.id.spinnerPincelColor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        SIZE_PINCEL = Integer.parseInt(TamPincelNum.getText().toString());

        spinnerPincelColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int pos, long id) {
                dColor = parent.getItemAtPosition(pos).toString();
                switch (dColor){
                    case "Negro":
                        COLOR = Color.BLACK;
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                    break;
                    case "Amarillo":
                        COLOR = Color.YELLOW;
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                    break;
                    case "Morado":
                        COLOR = Color.rgb(204, 102, 255);
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                    break;
                    case "Marron":
                        COLOR = Color.rgb(102, 51, 0);
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                    break;
                    case "Azul":
                        COLOR = Color.BLUE;
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                    break;
                    case "Naranja":
                        COLOR = Color.rgb(255, 153, 51);
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                    break;
                    case "Rojo":
                        COLOR = Color.RED;
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                    break;
                    case "Blanco":
                        COLOR = Color.WHITE;
                        lienzoView.init(metrics, SIZE_PINCEL, COLOR);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        tamPincelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                TamPincelNum.setText(String.valueOf(progress));
                SIZE_PINCEL = Integer.parseInt(TamPincelNum.getText().toString());
                lienzoView.init(metrics, SIZE_PINCEL, COLOR);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.normal:
                lienzoView.pincel_normal();
                return true;
            case R.id.blur:
                lienzoView.pincel_blur();
                return true;
            case R.id.clear:
                lienzoView.clear();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
