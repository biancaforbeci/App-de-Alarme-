package appponto.up.edu.br.appavisohorrios;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListarActivity extends AppCompatActivity {

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        ListView listaFun = findViewById(R.id.listaPonto);

        ArrayList<Funcionario> lista = new FuncionarioDAO().listar();
        if (lista.size() == 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Nenhum alarme cadastrado");
            alertDialog.setMessage("Não foi encontrado nenhum alarme ativado !");
            alertDialog.setIcon(R.drawable.relogio);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();



        } else {
            FuncionarioAdapterActivity funAdapter = new FuncionarioAdapterActivity(new FuncionarioDAO().listar(), this);
            listaFun.setAdapter(funAdapter); //adiciona na lista view o array adapter com a lista de contatos.
        }

        listaFun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
                Funcionario f = (Funcionario) adapterView.getItemAtPosition(posicao);
                Intent it = new Intent(ListarActivity.this, CadastrarActivity.class);
                it.putExtra("funcionario", f);
                startActivity(it);
            }
        });


        listaFun.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long l) {
                Funcionario fun= (Funcionario) adapterView.getItemAtPosition(posicao);
                exemplo_simples(fun, (FuncionarioAdapterActivity) adapterView.getAdapter());
                return false;
            }
        });

    }


    public void exemplo_simples(final Funcionario funcio, final FuncionarioAdapterActivity adapterView) {

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Exclusão");
        //define a mensagem
        builder.setMessage("Deseja excluir o alarme ? ");

        builder.setCancelable(false);
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                onDestroyInicio();
                onDestroyAlmoco();
                onDestroySaida();
                new FuncionarioDAO().excluir(funcio);
                adapterView.remove(funcio);
                adapterView.notifyDataSetChanged();
                Toast.makeText(ListarActivity.this, "Removido !", Toast.LENGTH_SHORT).show();
                PesquisarAlarme();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.cancel();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }


    protected void onDestroyInicio() {
        Intent it = new Intent("EXECUTAR_ALARME");
        PendingIntent p = PendingIntent.getBroadcast(this, 0, it, 0);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.cancel(p);
    }


    protected void onDestroyAlmoco(){
        Intent it = new Intent("EXECUTAR_ALMOCO");
        PendingIntent p = PendingIntent.getBroadcast(this, 0, it, 0);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.cancel(p);
    }

    protected void onDestroySaida() {
        Intent it = new Intent("EXECUTAR_SAIDA");
        PendingIntent p = PendingIntent.getBroadcast(this, 0, it, 0);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.cancel(p);
    }

    public void PesquisarAlarme(){
        ArrayList<Funcionario> lista = new FuncionarioDAO().listar();
        if (lista.size() == 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Nenhum alarme cadastrado");
            alertDialog.setMessage("Não foi encontrado nenhum alarme ativado !");
            alertDialog.setIcon(R.drawable.relogio);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        }

    }




    }
