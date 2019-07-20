package tw.hd.com.guessnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GuessTool {
    public boolean CheckIsDouble(String checknum){
        boolean b = true;
        for (int i = 0; i < checknum.length(); i++) {
            //取得第一個位元字
            String s1 = checknum.substring(i,i+1);
            //取得第一個位元字之後的所有字串
            String s2 = checknum.substring(i+1,checknum.length());
            if(s2.indexOf(s1) >= 0){ //沒找到返回-1值
                b = false;
                break;

            }
        }
    return b;
    }
    public HashMap<String , Integer > IsSame(String guessNum , String answer){
        HashMap<String,Integer> stringHashMap = new HashMap<>();
        int a = 0 ;
        int b = 0 ;
        int countab;
        for (int i = 0; i < guessNum.length(); i++) {
            countab = answer.indexOf(guessNum.substring(i,i+1));
            if(countab >=0){
                if (countab == i){
                    a++;
                }else {
                    b++;
                }
            }
        }
        stringHashMap.put("OneA",a);
        stringHashMap.put("TwoB",b);
        return  stringHashMap;
    }
    public String getGuessNume(int maxNum){
        int[] num = {0,1,2,3,4,5,6,7,8,9};
        int[] array = new int[maxNum];
        int n;
        for (int i = 0; i < array.length; i++) {
            n = (int) (Math.random()*(10-1));
            array[i] = num[n];
            for(int j =n ; j <num.length-1;j++){
                num[j] = num[j+1];

            }
        }
        String stringuess="";
        for (int i = 0; i <array.length; i++) {
            stringuess = stringuess+array[i];
        }
//        System.out.println("取得亂數不重覆的數字 : "+stringuess);

        return stringuess;

    }
    public List<String> getAiguessArray(int range){
        List<String> temparray = new ArrayList();

        return temparray;
    }
    public int chooesStrNum(String[] getchooesStr ,boolean[] chooescheck , int range){
        int count = 0;
        boolean chooseNotYet = true;
        for (int index = 0; index < getchooesStr.length; index++) {
            if(chooescheck[index] == true){
                count++;
                }
        }
        int random =(int)(Math.random()*range) , index  = random;
        if(count == 0){
            System.out.println("輸入有錯,停止吧");
        }else if(count == 1){
            System.out.println("答對了");
        }else{
            while (chooseNotYet){
                if(chooescheck[index] == false) {
                    if (index == range - 1) {
                        index = 0;
                    } else {
                        index++;
                    }
                }else {
                    chooseNotYet =false;

                }
            }
        }
        System.out.println("index "+index);
        return index;
    }
    public boolean[] delChooseNum(String guessStr ,String countAB ,String[] getchooesStr ,boolean[] chooescheck){
        for (int i = 0; i < getchooesStr.length; i++) {
            HashMap<String , Integer> AB = IsSame(getchooesStr[i],guessStr);
            String typeAB = AB.get("OneA")+"A"+AB.get("TwoB")+"B";
            if(typeAB.charAt(0) != countAB.charAt(0) || typeAB.charAt(2) != countAB.charAt(2)){
                chooescheck[i] = false;
            }
        }


        return  chooescheck;
    }
    public String[] initGetGuessNum(int level,int range){
        String[] array = new String[range];
        for (int i = 0; i < array.length; i++) {
            if(i > 0){
               array[i] = getGuessinit(level,array,i);
            }else{
                array[i] = getGuessNume(level);
            }
        }
        return array;
    }
    private String getGuessinit(int level, String[] array , int i){
        boolean abc = true;
        String guessStr="";
        guessStr = getGuessNume(level);

        for (int j = 0; j < i; j++) {
            do{
                if(array[j].equals(guessStr)){
                    guessStr = getGuessNume(level);
                    j = 0;
//                    abc = true;
                }else{
                    abc = false;
                }

            }while (abc);


        }

        return guessStr;
    }

}
