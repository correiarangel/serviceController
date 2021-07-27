package br.com.rangeldev.servicecontroller.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.rangeldev.servicecontroller.feature.listaOrdensServico.model.OSModel

class HelperDB(context: Context) :SQLiteOpenHelper(context,NOME_DB,null,VERSION_CURRENTY){
   companion object{
       private val NOME_DB = "oscontrol.db"
       private val VERSION_CURRENTY = 1
   }
    val TB_NAME ="os_tb"
    val COLUNMS_ID = "id"
    val COLUNMS_CLIENT = "client"
    val COLUNMS_TECNICO = "tecnico"
    val COLUNMS_DESC = "decriptions"
    val COLUNMS_RESULUTION = "resolotion"
    val COLUNMS_DATE_START = "date_start"
    val COLUNMS_DATE_AND = "date_end"
    val COLUNMS_VALUE = "value"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TB_NAME"

    val CREATE_TABLE = "CREATE TABLE $TB_NAME ("+
            "$COLUNMS_ID INTEGER NOT NULL," +
            "$COLUNMS_CLIENT TEXT NOT NULL,"+
            "$COLUNMS_TECNICO TEXT NOT NULL,"+
            "$COLUNMS_DESC TEXT NOT NULL,"+
            "$COLUNMS_RESULUTION TEXT ,"+
            "$COLUNMS_DATE_START TEXT NOT NULL,"+
            "$COLUNMS_DATE_AND TEXT,"+
            "$COLUNMS_VALUE DOUBLE,"+
            ""+
            "PRIMARY KEY($COLUNMS_ID AUTOINCREMENT)"+
            ")"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       if(oldVersion != newVersion){
           db?.execSQL(DROP_TABLE)
       }
        onCreate(db)
    }

    fun searchOS(serch:String, isSearchByID:Boolean = false):List<OSModel>{
        val db = readableDatabase?: return mutableListOf()
        var listOs = mutableListOf<OSModel>()
        var where:String? = null
        var args:Array<String> = arrayOf()
        if (isSearchByID){
            where = "$COLUNMS_ID = ?"
            args = arrayOf("$serch")
        }else {
            where = "$COLUNMS_CLIENT LIKE ?"
            args = arrayOf("%$serch%")
        }

        var cursor = db.query(
            TB_NAME,
            null,
            where,
            args,
            null,null,null)

        if(cursor == null){
            db.close()
            return mutableListOf()
        }

        while (cursor.moveToNext()){
            val osService = OSModel(
                cursor.getInt(cursor.getColumnIndex(COLUNMS_ID)),
                cursor.getString(cursor.getColumnIndex(COLUNMS_CLIENT)),
                cursor.getString(cursor.getColumnIndex(COLUNMS_TECNICO)),
                cursor.getString(cursor.getColumnIndex(COLUNMS_DESC)),
                cursor.getString(cursor.getColumnIndex(COLUNMS_RESULUTION)),
                cursor.getString(cursor.getColumnIndex(COLUNMS_DATE_START)),
                cursor.getString(cursor.getColumnIndex(COLUNMS_DATE_AND)),
                cursor.getDouble(cursor.getColumnIndex(COLUNMS_VALUE))
            )
            listOs.add(osService)
        }

        return  listOs
    }

    fun saveOS(osModel:OSModel){
        val db = writableDatabase?:return
        var content = ContentValues()
        content.put(COLUNMS_CLIENT,osModel.cliente)
        content.put(COLUNMS_TECNICO,osModel.tecnico)
        content.put(COLUNMS_DESC,osModel.descricao)
        content.put(COLUNMS_RESULUTION,osModel.resulocao)
        content.put(COLUNMS_DATE_START,osModel.dataInical)
        content.put(COLUNMS_DATE_AND,osModel.dataFinal)
        content.put(COLUNMS_VALUE,osModel.valor)
        db.insert(TB_NAME,null,content)
        db.close()
    }

    fun deleteOS(id: Int){
        val db = writableDatabase?:return
        val sql = "DELETE FROM $TB_NAME WHERE $COLUNMS_ID = ?"
        val arg = arrayOf("$id")
        db.execSQL(sql,arg)
        db.close()
    }

    fun updateOS(osModel:OSModel){
        val db = writableDatabase?:return
        val sql = "UPDATE $TB_NAME SET $COLUNMS_CLIENT = ? ,$COLUNMS_TECNICO = ?,$COLUNMS_DESC = ?,$COLUNMS_RESULUTION = ?,$COLUNMS_DATE_START = ?, $COLUNMS_DATE_AND = ?,$COLUNMS_VALUE = ? WHERE $COLUNMS_ID = ?"
        val arg = arrayOf(
            osModel.cliente,
            osModel.tecnico,
            osModel.descricao,
            osModel.resulocao,
            osModel.dataInical,
            osModel.dataFinal,
            osModel.valor)
        db.execSQL(sql,arg)
        db.close()

    }


}