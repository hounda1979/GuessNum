package tw.hd.com.guessnum;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class GuessNumPage extends AppCompatActivity {

    private List<ShowData> showDataList;
    private ArrayList<String> numButtonList;
    private HashMap<String, Integer> getresultstr;
    private ArrayList<String> stringshow;
    private TextView showTxetView;
    private GuessTool guessTool;
    private int levelMax;
    private String guessanswer;
    private int id;
    private ShowAdapter showAdapter;
    private RecyclerView recyclerView_re;
    private TextView leveltext;
    private long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        levelMax = getIntent().getIntExtra("Level",2);
        Calendar calendar_now = Calendar.getInstance();
        startTime = calendar_now.getTimeInMillis();




        setContentView(R.layout.activity_guess_num_page);
        leveltext = findViewById(R.id.level_text);
        switch (levelMax){
            case 2:
                leveltext.setText("0");
                break;
            case 3:
                leveltext.setText("1");
                break;
            case  4:
                leveltext.setText("2");
                break;
            case  5:
                leveltext.setText("3");
                break;
        }
        id = 0;
        guessTool = new GuessTool();
        //get guessNum
        guessanswer = guessTool.getGuessNume(levelMax);
        showTxetView = findViewById(R.id.guess_num_text);
        showDataList = new ArrayList<>(); //showdate list
        numButtonList = new ArrayList<>(); //numbutton list
        //取得猜數字結果ex 1A0B之類;
        getresultstr = new HashMap<>();
        //set showText
        stringshow = new ArrayList<>();
        //set listdate into numbuttonlist
        String[] numarray = getResources().getStringArray(R.array.numarray);
        for (int i = 0; i < numarray.length; i++) {
            numButtonList.add(numarray[i]);
        }
        //set numberButton recycler
        RecyclerView recyclerView_num = findViewById(R.id.recycler_button);
        recyclerView_num.setHasFixedSize(true);
        recyclerView_num.setLayoutManager(new GridLayoutManager(this,5));
        NumButtonAdapter buttonAdapter = new NumButtonAdapter();
        recyclerView_num.setAdapter(buttonAdapter);
        //set result_recycler
        recyclerView_re = findViewById(R.id.recycler_result);
        recyclerView_re.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView_re.setLayoutManager(linearLayoutManager);
        //set line
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView_re.addItemDecoration(itemDecoration);
        //setAdapter
        showAdapter = new ShowAdapter();
        recyclerView_re.setAdapter(showAdapter);



    }
    public class NumButtonAdapter extends RecyclerView.Adapter<NumButtonAdapter.NumHolder> {
        @NonNull
        @Override
        public NumHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.guess_bu_icom,viewGroup,false);
            return new NumHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NumHolder numHolder, final int i) {
            if(numButtonList.get(i).equals("enter")){
                numHolder.button_num.setText(numButtonList.get(i).toString());
            }else if(numButtonList.get(i).equals("back")){
                numHolder.button_num.setText(numButtonList.get(i).toString());
            }else {
                numHolder.button_num.setText(numButtonList.get(i).toString());
                numHolder.button_num.setTextSize(TypedValue.COMPLEX_UNIT_PT,20);
            }
            numHolder.button_num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getButtonvalue(numButtonList.get(i));

                }
            });

        }

        @Override
        public int getItemCount() {
            return numButtonList.size();
        }

        public class NumHolder extends RecyclerView.ViewHolder {
            Button button_num ;
            public NumHolder(@NonNull View itemView) {
                super(itemView);
                button_num = itemView.findViewById(R.id.button_gu_icon);
            }
        }
    }

    private void getButtonvalue(String buttonNum) {


        if(buttonNum.equals("back")){
            stringshow.remove(stringshow.size()-1);
            TextViewShow();
        }else if(buttonNum.equals("enter")){
            id++;
            int count = showTxetView.getText().length();
            if(stringshow.size() < levelMax){
                Toast.makeText(this,"猜的數字小於"+levelMax+"位數喔",Toast.LENGTH_SHORT).show();
            }else {
                //送出資料存檔比較
                Toast.makeText(this,"要猜的數字是 "+guessanswer,Toast.LENGTH_LONG).show();
                String gess = showTxetView.getText().toString();
                getresultstr = guessTool.IsSame(gess,guessanswer);
                if(getresultstr.get("OneA").toString().equals(String.valueOf(levelMax))){
                    Calendar calendar_end = Calendar.getInstance();
                    long endTime = calendar_end.getTimeInMillis();
                    long sepndTime = endTime-startTime;

                    System.out.println("總共花了多少時間" + sepndTime);
                    Toast.makeText(this,"答對了 共花了多少"+sepndTime/1000+"毫秒",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    //給showlist 值
                    showDataList.add(new ShowData(id, gess, String.valueOf(getresultstr.get("OneA")), String.valueOf(getresultstr.get("TwoB"))));
                    showdialog(showDataList);
                    stringshow.clear();
                    showTxetView.setText("0");


                    showAdapter.notifyDataSetChanged();
                    recyclerView_re.smoothScrollToPosition(showAdapter.getItemCount());
                }
            }

        }else{
            stringshow.add(buttonNum);
            TextViewShow();

        }
    }

    private void showdialog(List<ShowData> showData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.customdialog,null);
        TextView gussnum_text = view.findViewById(R.id.guessnum_dialog);
        TextView re_oneA = view.findViewById(R.id.oneA_dialog);
        TextView re_twoB = view.findViewById(R.id.towB_dialog);
        builder.setView(view);
        int g = showData.size();
        gussnum_text.setText(showData.get(g-1).getResult());
        re_oneA.setText(showData.get(g-1).getOneA()+"A");
        re_twoB.setText(showData.get(g-1).getTwoB()+"B");

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }

    private void TextViewShow() {
        String show ="";
        if(stringshow.size()>levelMax){
            stringshow.remove(levelMax);
            Toast.makeText(this,"猜的數字大於"+levelMax+"位數喔",Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < stringshow.size(); i++) {
            show = show+stringshow.get(i);
            if( ! guessTool.CheckIsDouble(show)){
                Toast.makeText(this,"重覆了,請在重選一個數字",Toast.LENGTH_SHORT).show();
                stringshow.remove(i);
            }
        }

        showTxetView.setText(show);
    }

    public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowHolder> {
        @NonNull
        @Override
        public ShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.guess_result_item,viewGroup,false);
            return new ShowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowHolder showHolder, int i) {
            showHolder.id.setText(showDataList.get(i).getId()+"");
            showHolder.resultstr.setText(showDataList.get(i).getResult());
            showHolder.onea.setText(showDataList.get(i).getOneA()+"A");
            showHolder.twob.setText(showDataList.get(i).getTwoB()+"B");


        }

        @Override
        public int getItemCount() {
            return showDataList.size();
        }

        public class ShowHolder extends RecyclerView.ViewHolder {
            TextView id;
            TextView onea;
            TextView twob;
            TextView resultstr;
            public ShowHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id_result_text);
                onea = itemView.findViewById(R.id.result_A);
                twob = itemView.findViewById(R.id.result_B);
                resultstr = itemView.findViewById(R.id.guess_num_result);
            }
        }
    }
}
