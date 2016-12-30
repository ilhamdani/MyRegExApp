package com.lab.ilham.myregexapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    public final static String TAG = MainActivity.class.getName();

    public final static String FOLDER_NAME = "EPGFile";
    public final static String FILE_EXTENSION = ".txt";
    public final static String FILE_NAME_0 = "ORAGIT";
    public final static String FILE_NAME_1 = "PRC_SSP";
    public final static String FILE_NAME_2 = "EXAMPLE";

    private static String eventName;
    private static String evenShortDesc;
    private static String eventLongDesc;

    private static File TxtFile;
    private static String FDate;
    private static String FName;

    private static String fileNameTemp;
    private static String eventShortDescId;
    private static String eventShortDescTotalField;
    private static String eventShortDescField;

    private static String fieldName;

    private EditText edEventName, edEventShortDesc, edEventLongDesc;
    Button btnSave, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEventName = (EditText)findViewById(R.id.eventName);
        edEventShortDesc = (EditText)findViewById(R.id.eventShortDesc);
        edEventLongDesc = (EditText)findViewById(R.id.eventLongDesc);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnReset = (Button)findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edEventName.setText("");
                edEventShortDesc.setText("");
                edEventLongDesc.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventName = edEventName.getText().toString();
                evenShortDesc = edEventShortDesc.getText().toString();
                eventLongDesc = edEventLongDesc.getText().toString();

                // ----------------------------- create new folder and file ------------------------------------------ //
                File newFolder = new File("/sdcard/"+ FOLDER_NAME+"/");
                if (!newFolder.exists()) {
                    newFolder.mkdirs();
                    Log.d(TAG, "Create new folder succes !");
                } else {

                    // split the eventName
                    String[] eventNameSplit = eventName.split("\\.");

                    // get last digit of eventName
                    int numberOfFile = Integer.parseInt(eventNameSplit[2]);

                    // File name array
                    String[] arrFileName = {FILE_NAME_0,FILE_NAME_1,FILE_NAME_2};

                    // Field name array
                    String[][] arrFieldId = {{"1"}, {"2"}, {"3"}, {"4"}, {"5",}, {"6"}, {"7"}, {"8"}, {"9"}, {"10"}, {"11"}};
                    String[][] arrFieldName = {{"NO"}, {"KD_STORE"}, {"FAKTUR"}, {"TGL_KIRIM"}, {"QTY"}, {"NET"}, {"PPN"}, {"TGL_PROSES"}, {"PLU"}, {"KD_DC"}, {"PRICE"}};

                    // Looping for create number of file based on last digit of the eventName
                    fileNameTemp = "";
                    for (int i = 0; i < numberOfFile; i++) {

                        // create format file name
                        FDate = new SimpleDateFormat("ddMMyyyy").format(new Date());
                        FName = eventNameSplit[0]+"_"+arrFileName[i]+"_"+FDate+"_"+i+FILE_EXTENSION;

                        if (i != numberOfFile-1) {
                            fileNameTemp += FName + "-";
                        }else fileNameTemp += FName;

                        // create file
                        TxtFile = new File(newFolder, FName);
                        if (!TxtFile.exists()) {
                            try {
                                TxtFile.createNewFile();
                                Log.d(TAG, "Create New File "+i+" Success");
                            } catch (IOException e) {
                                Log.d(TAG, e.getMessage());
                            }
                        }

                        // break for after condition is match
                        if(i == numberOfFile) {
                            break;
                        }

                    } // --- end of looping
                    // ----------------------------- end of create new folder and file ------------------------------------------ //

                    // ------------------------------------ write header field and data to each file -----------------------------//
                    if(TxtFile.exists()) {
                        // split event short description
                        String[] eventShortDescSplit = evenShortDesc.split("\\*");
                        Log.d(TAG, "file exist!");

                        eventShortDescId = "";
                        eventShortDescTotalField = "";
                        eventShortDescField = "";
                        for (int j = 0; j < eventShortDescSplit.length; j++) {

                            // split and get first part to choose file name
                            String[] eventShortDescDetailSplit = eventShortDescSplit[j].split("\\.");

                            if(j!= eventShortDescSplit.length-1){
                                eventShortDescId+=eventShortDescDetailSplit[0]+"-";
                                eventShortDescTotalField+=eventShortDescDetailSplit[1]+"-";
                                eventShortDescField+=eventShortDescDetailSplit[2]+"-";
                            }else {
                                eventShortDescId+=eventShortDescDetailSplit[0];
                                eventShortDescTotalField+=eventShortDescDetailSplit[1];
                                eventShortDescField+=eventShortDescDetailSplit[2];
                            }

                        }//--- end of create field header

                        Log.d(TAG, eventShortDescId + " = " + fileNameTemp);

                    }// --- end of if file exist


                    // split temp file name
                    String[] fileNameTempSplit = fileNameTemp.split("\\-");

                    //split id event short desc
                    String[] eventShortDescIdSplit = eventShortDescId.split("\\-");

                    // split total event short desc
                    String[] eventShortDescTotalFieldSplit = eventShortDescTotalField.split("\\-");

                    // split each field of file
                    String[] eventShortDescFieldSplit = eventShortDescField.split("\\-");

                    // split each data for each file
                    String[] eventLongDescSplit = eventLongDesc.split("\\*");


                    // matching file
                    for (int x = 0; x < numberOfFile; x++) {

                        // get file name
                        String getFilename;

                        // checking length name of file
                        if (x == 0) {
                            getFilename = fileNameTempSplit[x].substring(21, 22);
                        } else getFilename = fileNameTempSplit[x].substring(22, 23);

                        // convert total event short desc from string to int
                        int totalField = Integer.parseInt(eventShortDescTotalFieldSplit[x]);

                        // matching
                        if (getFilename.equals(eventShortDescIdSplit[x])) {

                            // ---- write field to file --- //
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/"+FOLDER_NAME+"/"+fileNameTempSplit[x], true);

                                String[] fieldShortDesc = eventShortDescFieldSplit[x].split("");

                                // looping for number of field that should created
                                for (int field = 1; field < totalField+1; field++) {

                                    // matching field name
                                    for (int r = 0; r < arrFieldId.length; r++) {
                                        for (int c = 0; c < arrFieldId[r].length; c++) {

                                            if (fieldShortDesc[field].equals(arrFieldId[r][c])) {
                                                fieldName = arrFieldName[r][c];
                                                Log.d(TAG, arrFieldName[r][c]);
                                            }
                                        }
                                    } // end of matching field name

                                    if (field == totalField) {

                                        // looping for each field and create field header
                                        fileOutputStream.write((fieldName + "|" + fieldShortDesc[field]+ System.getProperty("line.separator")).getBytes());
                                        Log.d(TAG, "write field new line to file successed : " + fileNameTempSplit[x] + " | " + fieldShortDesc[field]);
                                    } else {
                                        fileOutputStream.write((fieldName + "|" + fieldShortDesc[field] + "\t\t\t\t").getBytes());
                                        Log.d(TAG, "write field to file successed : " + fileNameTempSplit[x] + " | " + fieldShortDesc[field]);
                                    }
                                } // --- end of looping number of field

                            }  catch(FileNotFoundException ex) {
                                Log.d(TAG, ex.getMessage());
                            }  catch(IOException ex) {
                                Log.d(TAG, ex.getMessage());
                            }
                            // ---- end of write field to file --- //

                            // ---- write data to file --- //
                            try {
                                // split each section of data
                                String[] eventLongDescDetailSplit = eventLongDescSplit[x].split("\\|");
                                FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/"+FOLDER_NAME+"/"+fileNameTempSplit[x], true);
                                for (int data = 0; data < eventLongDescDetailSplit.length; data++) {

                                    //split each section for each column
                                    String[] eachData = eventLongDescDetailSplit[data].split("\\.");

                                    for (int eData = 0; eData < eachData.length; eData++) {
                                        // create new line
                                        if (eData == eachData.length-1){
                                            fileOutputStream.write((eachData[eData] + System.getProperty("line.separator")).getBytes());
                                            Log.d(TAG, "success new line : " + eventLongDescDetailSplit[eData] + eachData.length);
                                        } else {
                                            fileOutputStream.write((eachData[eData] + "\t\t\t\t").getBytes());
                                            Log.d(TAG, "success : " + eventLongDescDetailSplit[eData] + " | " + eachData.length + " | " + eData);
                                        }
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // ---- end of write data to file --- //

                        } else {
                            Log.d(TAG, "write to file failed");
                        } // --- end of writing field to each file

                        Log.d(TAG, getFilename + " | " + eventShortDescIdSplit[x] + " | " + eventShortDescTotalFieldSplit[x] + " | " + eventShortDescFieldSplit[x]);



                    } // --- end matching file
                    // ------------------------------------ write header field and data to each file -----------------------------//

                } // --- end of Create new Folder
            } // --- end of OnClick (View)
        }); // --- end of OnClickListener

    }
}
