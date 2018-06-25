package appponto.up.edu.br.appavisohorrios;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FuncionarioAdapterActivity extends BaseAdapter {

    private List<Funcionario> funcionarios;
    Activity act;

    public FuncionarioAdapterActivity (List<Funcionario> funcionarios, Activity act){
        this.funcionarios=funcionarios;
        this.act=act;                        //cria construtor que obriga a iniciar a classe passando contatos e activity
    }

    @Override
    public int getCount() { //deve retornar tamanho da lista de contatos
        return this.funcionarios.size();
    }

    @Override
    public Object getItem(int i) {
        return this.funcionarios.get(i);  //retorna o contato da posição i da lista.
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {  //monta o pedaço de tela referente a cada tela. Coloca o layout que quer.
        View v= act.getLayoutInflater().inflate(R.layout.funcionario_adapter,viewGroup,false);

        TextView textNome= v.findViewById(R.id.textNome);
        TextView textEmail= v.findViewById(R.id.textEmail);
        TextView textView3 = v.findViewById(R.id.horaEnt);
        TextView textView4 = v.findViewById(R.id.horaAlm);
        TextView textView5 = v.findViewById(R.id.horaSai);
        ImageView imageView= v.findViewById(R.id.imageIcone);

        Funcionario funcio = funcionarios.get(i);  //para cada linha chama o layout referente e coloca o contato dentro.

        textNome.setText(funcio.getNome().toString());
        textEmail.setText(funcio.getEmail().toString());
        textView3.setText(funcio.getHoraEntrada());
        textView4.setText(funcio.getHoraAlmoco());
        textView5.setText(funcio.getHoraSaida());
         imageView.setImageResource(R.drawable.salvo);


        return v;
    }


    public void remove(Funcionario fun) {
        this.funcionarios.remove(fun);
    }
}
