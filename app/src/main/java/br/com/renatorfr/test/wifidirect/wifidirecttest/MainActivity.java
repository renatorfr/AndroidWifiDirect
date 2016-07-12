package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    private TextView txtCommands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnUp = (Button) findViewById(R.id.btnUp);
        this.btnDown = (Button) findViewById(R.id.btnDown);
        this.btnLeft = (Button) findViewById(R.id.btnLeft);
        this.btnRight = (Button) findViewById(R.id.btnRight);

        this.txtCommands = (TextView) findViewById(R.id.txtCommands);
        this.txtCommands.setMovementMethod(new ScrollingMovementMethod());

        setListeners();
    }

    private void setListeners() {
        this.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.UP);
            }
        });

        this.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.DOWN);
            }
        });

        this.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.LEFT);
            }
        });

        this.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.RIGHT);
            }
        });
    }

    private void writeCommand(Commands command) {
        String message = System.getProperty("line.separator") + command.getCommand();
        this.txtCommands.append(message);
    }
}

enum Commands {
    UP("Up"), DOWN("Down"), LEFT("Left"), RIGHT("Right");

    private String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}