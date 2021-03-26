package com.example.seminar3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.seminar3.util.DateConverter;
import com.example.seminar3.util.Student;
import com.example.seminar3.util.StudyType;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class AddActivity extends AppCompatActivity {

    public static final String STUDENT_KEY = "STUDENT_KEY";
    private final DateConverter dateConverter=new DateConverter();
    private TextInputEditText tietName;
    private TextInputEditText tietAge;
    private TextInputEditText tietEnrollmentDate;
    private RadioGroup rgStudyType;
    private Spinner spnFaculties;
    private Button btnSave;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initComponents();
        populateSpnFaculties();
        btnSave.setOnClickListener(addSaveClickEvent());
        intent=getIntent();
    }

    private void populateSpnFaculties() {
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getApplicationContext(),R.array.add_faculties, android.R.layout.simple_spinner_dropdown_item);
        spnFaculties.setAdapter(adapter);
    }

    private View.OnClickListener addSaveClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    Student student=buildStudentFromWidgets();
                    intent.putExtra(STUDENT_KEY,student);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        };
    }

    private Student buildStudentFromWidgets() {
        String name=tietName.getText().toString();
        int age=Integer.parseInt(tietAge.getText().toString().trim());
        Date enrollmentDate=dateConverter.fromString(tietEnrollmentDate.getText().toString().trim());
        StudyType studyType=StudyType.DISTANCE;
        if (rgStudyType.getCheckedRadioButtonId()==R.id.rb_add_study_type_full_time){
            studyType=StudyType.FULL_TIME;
        }
        String faculty=spnFaculties.getSelectedItem().toString();
        return new Student(name,age,enrollmentDate,studyType,faculty);
    }

    private boolean validate() {
        if (tietName.getText()==null||tietName.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(),getString(R.string.invalid_name),Toast.LENGTH_SHORT).show();
           return false;
        }
        if (tietAge.getText()==null||tietAge.getText().toString().trim().length()==0
                ||Integer.parseInt(tietAge.getText().toString().trim())<18
                ||Integer.parseInt(tietAge.getText().toString().trim())>70){
            Toast.makeText(getApplicationContext(),getString(R.string.invalid_age),Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietEnrollmentDate.getText()==null||dateConverter.fromString(tietEnrollmentDate.getText().toString().trim())==null){
            Toast.makeText(getApplicationContext(),"Invalid enrollment date. Accepted format dd/MM/yyyy",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initComponents() {
        tietName=findViewById(R.id.tiet_add_name);
        tietAge=findViewById(R.id.tiet_add_age);
        tietEnrollmentDate=findViewById(R.id.tiet_add_enrollment_date);
        rgStudyType=findViewById(R.id.rg_add_study_type);
        spnFaculties=findViewById(R.id.spn_add_faculties);
        btnSave=findViewById(R.id.btn_add_save);
    }


}