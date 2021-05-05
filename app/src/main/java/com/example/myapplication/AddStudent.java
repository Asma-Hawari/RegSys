package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Model.StudentModel;
import com.example.myapplication.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddStudent extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialg";
    private EditText addnewStudent ;
    private Button newStudent;

    private DatabaseHandler db;
    public static AddStudent newInstance(){
        return new AddStudent();
    }
    public static AddStudent AddnewStudent(){

        return new AddStudent();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.DialogStyle);
    }
@Override
    public View onCreateView(LayoutInflater inflater , ViewGroup   container , Bundle savedInstanceState){
View view = inflater.inflate(R.layout.activity_add_new_student,container,false);
getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
return view;
}
@Override
    public void onViewCreated(View view , Bundle savedInstanceState)
{
    super.onViewCreated(view,savedInstanceState);
    addnewStudent  = getView().findViewById(R.id.newStudentText);
    newStudent = getView().findViewById(R.id.newstudentButton);

    db = new DatabaseHandler(getActivity());
    db.openDatabase();
    boolean isUpdate = false;

    final Bundle bundle = getArguments();
    if(bundle != null){
        /*isUpdate = true;
        String task = bundle.getString("task");
        newTaskText.setText(task);
        assert task != null;
        if(task.length()>0)
            newTaskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));

         */
    }
    addnewStudent.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().equals("")){
                newStudent.setEnabled(false);
                newStudent.setTextColor(Color.GRAY);
            }
            else{
                newStudent.setEnabled(true);
                newStudent.setTextColor(ContextCompat.getColor(requireContext(), R.color.greenBold));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

    newStudent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = addnewStudent.getText().toString();
            if(isUpdate){
                //db.(bundle.getInt("id"), text);
            }
            else {
                StudentModel student = new StudentModel();
                student.setFirst_name(text);
                db.insertStudent(student);
            }
            dismiss();
        }
    });
}
    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
