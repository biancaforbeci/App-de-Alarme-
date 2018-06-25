package appponto.up.edu.br.appavisohorrios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by biafo on 15/06/2018.
 */

public class ConexaoDB extends SQLiteOpenHelper {

    private static ConexaoDB conexao; // passa referencia para essa variável.

    public static ConexaoDB getInstance(){ //retorna a instancia do bd, tipo singleton.
        return conexao;
    }

    public ConexaoDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        conexao=this;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String statement = " create table Funcionario ( " +
                " id integer primary key autoincrement,"+
                " nome varchar(255),  " +
                " horaEnt Text,  " +
                " horaAlm Text,  " +
                " horaSai Text,  " +
                " email varchar(80) "  +
                ")";

        sqLiteDatabase.execSQL(statement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {  //versão nova que atualiza campos caso arrume.
        String statement= "alter table contato " + " add endereco varchar(255)";
        String state= "alter table contato " + " add idade integer";
        sqLiteDatabase.execSQL(statement);
        sqLiteDatabase.execSQL(state);
    }
}
