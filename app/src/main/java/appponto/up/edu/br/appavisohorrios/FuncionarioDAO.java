package appponto.up.edu.br.appavisohorrios;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by biafo on 16/06/2018.
 */

public class FuncionarioDAO {


    public void salvar(Funcionario funcionario) {
        SQLiteDatabase conn= ConexaoDB.getInstance().getWritableDatabase(); //consegue escrever no bd, pegando o objeto do banco.

        ContentValues values = new ContentValues();
        values.put("nome",funcionario.getNome());
        values.put("email", funcionario.getEmail());
        values.put("horaEnt",funcionario.getHoraEntrada());
        values.put("horaAlm", funcionario.getHoraAlmoco());
        values.put("horaSai",funcionario.getHoraSaida());

        if(funcionario.getId()==null){
            conn.insert("Funcionario",null,values); //tabela e valores para inserir.
        }else{
            conn.update("Funcionario",values, "id = ?", new String[]{funcionario.getId().toString()});
        }
    }

    public ArrayList<Funcionario> listar(){
        SQLiteDatabase conn = ConexaoDB.getInstance().getReadableDatabase();
        Cursor c = conn.query("Funcionario", new String[]{"id","nome","horaEnt", "horaAlm","horaSai" ,"email"},null,null,null,null,"nome");
        ArrayList<Funcionario> func= new ArrayList<Funcionario>();
        if(c.moveToFirst()){ //move para o primeiro da lista
            do{
                Funcionario funcionario= new Funcionario();
                funcionario.setId(c.getInt(0));
                funcionario.setNome(c.getString(1));
                funcionario.setHoraEntrada(c.getString(2));
                funcionario.setHoraAlmoco(c.getString(3));
                funcionario.setHoraSaida(c.getString(4));
                funcionario.setEmail(c.getString(5));


                func.add(funcionario);
            }while(c.moveToNext());
        }
        return func;
    }

    public void excluir(Funcionario fun){
        SQLiteDatabase conn= ConexaoDB.getInstance().getWritableDatabase();
        conn.delete("Funcionario","id = ?", new String[]{fun.getId().toString()});
    }


}
