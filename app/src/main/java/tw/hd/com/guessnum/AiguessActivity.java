package tw.hd.com.guessnum;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AiguessActivity extends AppCompatActivity {

    private Handler handler;
    private int aiguesslevel;
    private GuessTool guessTool;
    boolean[] chooescheck;
    private String[] getchooesStr;
    private int getGuessRange;
    private int id;
    private List<ShowData> showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiguess);
        guessTool = new GuessTool();
        id = 0;
        showData = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyver_ai);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AlartAdapter alartAdapter = new AlartAdapter();
        recyclerView.setAdapter(alartAdapter);

        aiguesslevel = getIntent().getIntExtra("Level",2);
        TextView level = findViewById(R.id.level_ai);
        switch (aiguesslevel){
            case 2:
                level.setText("0");
                break;
            case 3:
                level.setText("1");
                break;
            case 4:
                level.setText("2");
                break;
            case 5:
                level.setText("3");
                break;
        }

        initAiguess();
        initalart();

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case 1:
                        alartAdapter.notifyDataSetChanged();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        initalart();
                        break;
                    case 2:
                        new AlertDialog.Builder(AiguessActivity.this)
                                .setMessage("答對了 !!!!")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                        break;
                    case 3:
                        Toast.makeText(AiguessActivity.this,"__A或__B沒輸入值",Toast.LENGTH_SHORT).show();
                        break;

                        default:
                            break;

                }

            }
        };

    }
    private  void  initalart(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view1 = getLayoutInflater().inflate(R.layout.aialartdialog,null);
        TextView aiText = view1.findViewById(R.id.aiguessText);
        final EditText aioneA = view1.findViewById(R.id.ai_guess_edA);
        final EditText aitwoB = view1.findViewById(R.id.ai_guess_edB);
        Button aibutton = view1.findViewById(R.id.aiguess_but);
        builder.setView(view1);


        final AlertDialog dialog = builder.create();

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);  //點擊dialog外部不消失

        final int index = guessTool.chooesStrNum(getchooesStr,chooescheck,getGuessRange);
        aiText.setText(getchooesStr[index]);



        aibutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String onea = aioneA.getText().toString();
                String twob = aitwoB.getText().toString();
                String countAB = onea + "A" + twob + "B";
                String eqAB = String.valueOf(aiguesslevel);
                if (onea.equals("") || twob.equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message meg = new Message();
                            meg.what = 3;
                            handler.sendMessage(meg);
                        }
                    }).start();
                } else {

                    if (onea.equals(String.valueOf(aiguesslevel))) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message meg = new Message();
                                meg.what = 2;
                                handler.sendMessage(meg);
                            }
                        }).start();
                        dialog.dismiss();

                    } else {
                        chooescheck = guessTool.delChooseNum(getchooesStr[index], countAB, getchooesStr, chooescheck);

                        showData.add(new ShowData(id, getchooesStr[index], onea, twob));
                        id++;
                        System.out.println(countAB);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);



                            }
                        }).start();

                        dialog.dismiss();


                    }
                }
            }
        });



    }

    private void initAiguess() {
        int getlevel = (10 - aiguesslevel);
        getGuessRange = 1;
        for (int i = 10; i > getlevel; i--) {
            getGuessRange = getGuessRange * i;
        }
        chooescheck = new boolean[getGuessRange];
        for (int i = 0; i < chooescheck.length; i++) {
            chooescheck[i] = true;
        }
        getchooesStr = new String[getGuessRange];
        switch (aiguesslevel){
            case 2:
                getchooesStr = getResources().getStringArray(R.array.level0);
                break;
            case 3:
                getchooesStr = getResources().getStringArray(R.array.level1);
                break;
            case 4:
                getchooesStr = getResources().getStringArray(R.array.level2);
                break;
            case 5:
                getchooesStr = getResources().getStringArray(R.array.level3);
                break;
        }

        System.out.println("arraysize = "+getchooesStr.length);

    }


    public class  AlartAdapter extends RecyclerView.Adapter<AlartAdapter.AlartHolder> {
        @NonNull
        @Override
        public AlartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.guess_result_item,viewGroup,false);
            return new AlartHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlartHolder alartHolder, int i) {
            alartHolder.t_id.setText(showData.get(i).getId()+"");
            alartHolder.t_result.setText(showData.get(i).getResult());
            alartHolder.t_onea.setText(showData.get(i).getOneA()+"A");
            alartHolder.t_twob.setText(showData.get(i).getTwoB()+"B");

        }

        @Override
        public int getItemCount() {
            return showData.size();
        }

        public class AlartHolder extends RecyclerView.ViewHolder {
            TextView t_id;
            TextView t_onea;
            TextView t_twob;
            TextView t_result;
            public AlartHolder(@NonNull View itemView) {
                super(itemView);
                t_id = itemView.findViewById(R.id.id_result_text);
                t_result = itemView.findViewById(R.id.guess_num_result);
                t_onea = itemView.findViewById(R.id.result_A);
                t_twob= itemView.findViewById(R.id.result_B);


            }
        }
    }
}
