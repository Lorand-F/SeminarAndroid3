package com.example.seminar3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.seminar3.util.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_STUDENT_REQUEST_CODE = 210;
    private FloatingActionButton fabAdd;
    private ListView lvStudents;
    private List<Student> students=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addLvStudentsAdapter();
        fabAdd.setOnClickListener(addRequestStudentClickEvent());
    }

    private View.OnClickListener addRequestStudentClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, ADD_STUDENT_REQUEST_CODE);
            }
        };
    }

    private void addLvStudentsAdapter() {
        ArrayAdapter<Student> arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,students);
        lvStudents.setAdapter(arrayAdapter);
    }

    private void initComponents() {
        fabAdd=findViewById(R.id.fab_main_add);
        lvStudents=findViewById(R.id.lv_main_students);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_STUDENT_REQUEST_CODE && resultCode==RESULT_OK&&data!=null){
            Student student= (Student) data.getSerializableExtra(AddActivity.STUDENT_KEY);
            if (student!=null){
                Toast.makeText(getApplicationContext(),getString(R.string.student_added_message,student.toString()),Toast.LENGTH_SHORT).show();
                students.add(student);
                ArrayAdapter arrayAdapter= (ArrayAdapter) lvStudents.getAdapter();
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}