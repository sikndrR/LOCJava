package com.example.locjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ListView listView;


    /**
     * Name: onCreate
     * Initializes the main activity, setting up the user interface for displaying a list of serialized files
     * or starting a new game.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     *
     * <p>Algorithm:
     * 1. Set the content view to the main activity layout.
     * 2. Retrieve and display a list of resource file names in a ListView using an ArrayAdapter.
     * 3. Set up an onItemClick listener for the ListView that calls displayFileContent when a file is selected,
     *    passing the name of the selected file to display its content.
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        List<String> fileNames = getRawResourceNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);
        listView.setAdapter(adapter);

        textView = findViewById(R.id.testing);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedFile = fileNames.get(position);
            displayFileContent(selectedFile);
        });

    }

    /**
     * Name: displayFileContent
     * Reads the content of a specified resource file and displays it in a TextView, also initiating game loading.
     *
     * @param resourceName A String of the resource file to be read, as a String.
     *
     * <p>Algorithm:
     * 1. Retrieve the identifier for the resource based on the provided resource name.
     * 2. Open the resource file as an InputStream and buffer it for reading.
     * 3. Read the file line by line, appending each line to a StringBuilder to form the complete content.
     * 4. Set the TextView's text to the content read from the file.
     * 5. Close the reader and InputStream.
     * 6. Load the game with the content obtained from the resource file.
     * 7. Handle exceptions by printing the stack trace and displaying an error message in the TextView.
     *
     */
    private void displayFileContent(String resourceName) {
        try {
            int resourceId = getResources().getIdentifier(resourceName, "raw", getPackageName());
            InputStream is = getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            textView.setText(builder.toString());
            reader.close();
            is.close();
            loadGame(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText("Error loading file.");
        }
    }

    /**
     * Name: getRawResourceNames
     * Populate field with text names
     *
     * @return List of strings, representing all file names
     * <p>Algorithm
     * 1. Get field in raw file
     * 2. Get all names and append to list
     */
    private List<String> getRawResourceNames() {
        Field[] fields = R.raw.class.getFields();
        List<String> names = new ArrayList<>();

        for (Field field : fields) {
            names.add(field.getName());
        }

        return names;
    }

    /**
     * Name: loadgame
     * Load into the tournament of the game.
     *
     * @param V, View that is used from MainActivity XML
     * <p>Algorithm
     *  1. Get intent from BoardView class
     *  2. start the gameLayout XML
     */
    public void loadGame(View V){
        Intent gameLayout = new Intent(this, BoardView.class);
        startActivity(gameLayout);
    }

    /**
     * Name: loadgame
     * Load into the tournament of the game with serialization information.
     *
     * @param saved, string that holding serialization info
     * <p>Algorithm
     *  1. Get intent from BoardView class
     *  2. Add information from serialized file
     *  2. start the gameLayout XML
     */
    public void loadGame(String saved){
        Intent gameLayout = new Intent(this, BoardView.class);
        gameLayout.putExtra("Information",saved);
        startActivity(gameLayout);
    }

}