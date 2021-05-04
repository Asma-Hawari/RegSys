package com.example.myapplication.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.myapplication.Model.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "StudentDatabase";
    private static final String STUDENT_TABLE = "students";
    private static final String ID = "id";
    private static final String FIRSTNAME = "first_name";
    private static final String LASTNAME = "last_name";
    private static final String REG_YEAR = "reg_year";
    private static final String GENDER = "gender";
    private static final String MOBILE = "mobile";
    private static final String ADDRESS = "address";

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE " + STUDENT_TABLE + 
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRSTNAME + " TEXT, "
            +
            LASTNAME + " TEXT, "
            +
            REG_YEAR + " INTEGER, "
            +
            GENDER + " TEXT, "
            +
            MOBILE + " TEXT, "
            + ADDRESS + " TEXT )";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertStudent(StudentModel studentModel){
        ContentValues student = new ContentValues();
        student.put(FIRSTNAME, studentModel.getFirst_name());
        student.put(LASTNAME, studentModel.getLast_name());
        student.put(GENDER, studentModel.getGender());
        student.put(MOBILE, studentModel.getMobile());
        student.put(ADDRESS, studentModel.getAddress());
        student.put(REG_YEAR, studentModel.getReg_year());
        db.insert(STUDENT_TABLE, null, student);
    }

    public List<StudentModel> getAllTasks(){
        List<StudentModel> studentModels = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(STUDENT_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        StudentModel student = new StudentModel();
                        student.setId(cur.getInt(cur.getColumnIndex(ID)));
                        student.setFirst_name(cur.getString(cur.getColumnIndex(FIRSTNAME)));
                        student.setLast_name(cur.getString(cur.getColumnIndex(LASTNAME)));
                        student.setGender(cur.getString(cur.getColumnIndex(GENDER)));
                        student.setMobile(cur.getString(cur.getColumnIndex(MOBILE)));
                        student.setAddress(cur.getString(cur.getColumnIndex(ADDRESS)));
                        student.setReg_year(cur.getInt(cur.getColumnIndex(REG_YEAR)));
                        studentModels.add(student);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return studentModels;
    }

   /* public void updateStatus(int id, int status){
        ContentValues student = new ContentValues();
        student.put(STATUS, status);
        db.update(STUDENT_TABLE, student, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(STUDENT_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }*/

    public void deleteStudent(int id){
        db.delete(STUDENT_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}
