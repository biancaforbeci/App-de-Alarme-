package appponto.up.edu.br.appavisohorrios;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES;

public class CadastrarActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener{

    private EditText Nome;
    private EditText Email;
    private TimePicker HoraEntrada;
    private TimePicker HoraAlmoco;
    private TimePicker HoraSaida;
    private Funcionario funcionario;
    private int tipo;
    private int hour;
    private int minute;
    private int referencia=0;
    final Calendar calendarEnt = Calendar.getInstance();
    final Calendar calendarAlm = Calendar.getInstance();
    final Calendar calendarSai = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        getSupportActionBar().setTitle("Activity Cadastrar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Nome=findViewById(R.id.txtNome);
        Email=findViewById(R.id.txtEmail);
        HoraEntrada = findViewById(R.id.hora1);
        HoraAlmoco=findViewById(R.id.hora2);
        HoraSaida=findViewById(R.id.hora3);

        Intent it = getIntent();
        if(it!=null && it.hasExtra("funcionario")) {
            funcionario = (Funcionario) it.getSerializableExtra("funcionario");
            referencia=1;
            Nome.setText(funcionario.getNome().toString());
            Email.setText(funcionario.getEmail().toString());
        }
    }


    public void SalvarBanco(View view) {

        ArrayList<Funcionario> lista = new FuncionarioDAO().listar();
        if (lista.size() > 0 && referencia==0) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Já foi cadastrado um alarme");
            alertDialog.setMessage("Exclua ele ou edite no botão alarme ativado ! ");
            alertDialog.setIcon(R.drawable.relogio);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }else {
            if (funcionario == null) {
                funcionario = new Funcionario();
            }


            calendarEnt.set(Calendar.HOUR_OF_DAY, HoraEntrada.getCurrentHour());
            calendarEnt.set(Calendar.MINUTE, HoraEntrada.getCurrentMinute());


            calendarAlm.set(Calendar.HOUR_OF_DAY, HoraAlmoco.getCurrentHour());
            calendarAlm.set(Calendar.MINUTE, HoraAlmoco.getCurrentMinute());


            calendarSai.set(Calendar.HOUR_OF_DAY, HoraSaida.getCurrentHour());
            calendarSai.set(Calendar.MINUTE, HoraSaida.getCurrentMinute());


            funcionario.setNome(Nome.getText().toString());
            funcionario.setEmail(Email.getText().toString());
            funcionario.setHoraEntrada(calendarEnt.getTime().toString());
            funcionario.setHoraAlmoco(calendarAlm.getTime().toString());
            funcionario.setHoraSaida(calendarSai.getTime().toString());


            new FuncionarioDAO().salvar(funcionario);
            funcionario = null;

            Toast.makeText(getApplicationContext(), "Salvo com sucesso! ", Toast.LENGTH_SHORT).show();//mostra mensagem que foi salvo com sucesso.

            hour = HoraEntrada.getCurrentHour();
            minute = HoraEntrada.getCurrentMinute();
            tipo = 3;
            onTimeSet(HoraEntrada, hour, minute);

            hour = HoraAlmoco.getCurrentHour();
            minute = HoraAlmoco.getCurrentMinute();
            tipo = 1;
            onTimeSet(HoraAlmoco, hour, minute);

            hour = HoraSaida.getCurrentHour();
            minute = HoraSaida.getCurrentMinute();
            tipo = 2;
            onTimeSet(HoraSaida, hour, minute);

            Intent it = new Intent(CadastrarActivity.this, ListarActivity.class); //abre  janela para exibir contato cadastrado.
            startActivity(it);

        }
    }


    public void NotificacaoEntrada(Calendar c){

        Intent it = new Intent("EXECUTAR_ALARME");

        PendingIntent p = PendingIntent.getBroadcast(CadastrarActivity.this, 0, it, 0);

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarme.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);

    }

    public void NotificacaoAlmoco(Calendar c){
        Intent it = new Intent("EXECUTAR_ALMOCO");

        PendingIntent p = PendingIntent.getBroadcast(CadastrarActivity.this, 0, it, 0);

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarme.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);
    }

    public void NotificacaoSaida(Calendar c){
        Intent it = new Intent("EXECUTAR_SAIDA");

        PendingIntent p = PendingIntent.getBroadcast(CadastrarActivity.this, 0, it, 0);

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarme.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);
    }

    public void AbrirURL(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.institutoslactec.org.br/"));

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.setTimeInMillis(System.currentTimeMillis());
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(tipo==3) {
            NotificacaoEntrada(calSet);
        }else if(tipo==1){
            NotificacaoAlmoco(calSet);
        }else{
            NotificacaoSaida(calSet);
        }

    }
}


