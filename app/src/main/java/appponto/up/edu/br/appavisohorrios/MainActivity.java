package appponto.up.edu.br.appavisohorrios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ConexaoDB(getApplicationContext(),
                "funcionario.db",null,1);


    }

    public void TelaCadastrar(View view) {
        Intent it = new Intent(MainActivity.this,CadastrarActivity.class);
        startActivity(it);
    }

    public void TelaListar(View view) {
        Intent it = new Intent(MainActivity.this,ListarActivity.class);
        startActivity(it);
    }
}
