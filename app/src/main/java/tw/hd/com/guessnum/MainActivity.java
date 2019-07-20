package tw.hd.com.guessnum;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mainArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainArray = new ArrayList<>();
        mainArray.add("Level?");
        mainArray.add("機器猜人");
        mainArray.add("繼續遊戲");
        mainArray.add("歷史記錄");
        RecyclerView recyclerView = findViewById(R.id.main_recycle);
        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);


//        startActivity(new Intent(this,GuessNumPage.class));
    }

    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
        @NonNull
        @Override
        public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().from(MainActivity.this).inflate(R.layout.main_item, viewGroup, false);
//            View view = getLayoutInflater().inflate(R.layout.main_item,viewGroup,false);
            return new MainHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MainHolder mainHolder, final int i) {
            mainHolder.button_main.setText(mainArray.get(i));



            mainHolder.button_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartPage(mainArray.get(i));
                }
            });

        }

        @Override
        public int getItemCount() {
            return mainArray.size();
        }

        public class MainHolder extends RecyclerView.ViewHolder {
            Button button_main;

            public MainHolder(@NonNull View itemView) {
                super(itemView);
                button_main = itemView.findViewById(R.id.main_button);
            }
        }
    }

    private void StartPage(String s) {

        switch (s) {
            case "Level?":
                Intent intent = new Intent(this, GuessNumPage.class);
                initalaret(intent);

                break;
            case "機器猜人":
                Intent intent1 = new Intent(this, AiguessActivity.class);
                initalaret(intent1);
                break;
//            case "Level 0":
//                intent.putExtra("Level", 2);
//                startActivity(intent);
//                break;
//            case "Level 1":
//                intent.putExtra("Level", 3);
//                startActivity(intent);
//                break;
//            case "Level 2":
//                intent.putExtra("Level", 4);
//                startActivity(intent);
//                break;
//            case "Level 3":
//                intent.putExtra("Level", 5);
//                startActivity(intent);
//                break;
            case "繼續遊戲":
                Toast.makeText(this, "繼續遊戲", Toast.LENGTH_LONG).show();
                break;
            case "歷史記錄":
                Toast.makeText(this, "歷史記錄", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initalaret(final Intent intent) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.main_arlert,null);
        Button button_0 = view.findViewById(R.id.button_0);
        Button button_1 = view.findViewById(R.id.button_1);
        Button button_2 = view.findViewById(R.id.button_2);
        Button button_3 = view.findViewById(R.id.button_3);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        button_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Level", 2);
                startActivity(intent);
                dialog.dismiss();

            }
        });
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Level", 3);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Level", 4);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Level", 5);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }
}
