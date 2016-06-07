package com.example.administrator.classdesign;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




/**
 * Created by Administrator on 2016/5/6.
 */
public class LoginTool{
    final static String URL_LOGIN = "http://class.sise.com.cn:7001/sise/login.jsp";
    final static String URL_CHECK = "http://class.sise.com.cn:7001/sise/login_check.jsp";
    public static String URL_INFOEMATION = "http://class.sise.com.cn:7001/SISEWeb/pub/course/courseViewAction.do?method=doMain&studentid";
    public static String URL_SCHEDULAR = "http://class.sise.com.cn:7001/sise/module/student_schedular/student_schedular.jsp";
    public static String URL_TEST_TABLE="http://class.sise.com.cn:7001/SISEWeb/pub/exam/studentexamAction.do?method=doMain&studentid=";
    public static String URL_COST="http://class.sise.com.cn:7001/SISEWeb/pub/studentstatus/tuitionUseListAction.do?method=doMain&studentid";
    public static String URL_PENALTIES="http://class.sise.com.cn:7001/sise/module/encourage_punish/encourage_punish.jsp";
    public static String URL_ATTENDANCE="http://class.sise.com.cn:7001/SISEWeb/pub/studentstatus/";
   public static String URL_DEGREEEXAMENROL="http://class.sise.com.cn:7001/sise/module/rankexamsign/rankexamsign.jsp";
   public static String URL_CHECKOPENCOURSE="http://class.sise.com.cn:7001/sise/module/selectclassview/selectclasscourse_view.jsp";
    public static String URL_VIOLATIONRECORD="http://class.sise.com.cn:7001/SISEWeb/pub/studentstatus/lateStudentAction.do";
    public static Connection connection = null;
    private static String md5Code;
    static String username;
    static String password;
    static int id_count=0;
    static String get_table_sid=null;
    static String penalties=null;
    static String attendance=null;

    public LoginTool() {

    }

    public LoginTool(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
    }


    public static Map<String, String> getform() throws IOException {
        connection = Jsoup.connect(URL_LOGIN);
        connection.timeout(60000);
            Document dl = connection.get();
            List<Element> et = dl.select("form");

            Map<String, String> datas = new HashMap<>();

            datas.put(et.get(0).childNode(1).attr("name"), et.get(0).childNode(1).attr("value"));

            datas.put("username", username);
            datas.put("password", password);
            System.out.println(datas.toString());
            return datas;

    }


    public static boolean dologin() throws IOException {
        Map<String, String> map = getform();
        connection.url(URL_CHECK);
        connection.timeout(50000);
        connection.data(map);
        Document dl = connection.post();
        getMD5();
        boolean login = false;
        System.out.println("dl:" + dl.toString());


        if (dl.data().indexOf("/sise/index.jsp") > 1) {


            login = true;

            return login;
        }
        return login;
    }
    //获取个人信息
    static public List<Map<String, Object>> getInfor() throws IOException {
        if (dologin()) {

            if(id_count==0){
                getStudentid();
                id_count=1;
            }

//            connection.timeout(30000);
            connection.url(URL_INFOEMATION);
            connection.cookies(connection.response().cookies());
            System.out.println(connection.response().cookies());

            Document dl = connection.get();



            Elements ets = dl.select("span[class=font12]");
            Elements ets2 = dl.select("td[height=16][class=td_left]");
            Elements ets3 = dl.select("div[align=left] > font[color=#FF0000]");
            Elements ets4 = dl.select("div[align=right] > font[color=#FF0000]");
            Elements ets5 = dl.select("td[colspan=3]");
            //    Elements ets6 = dl.select("table[width=90%][class=table][align=center]").get(0).children();
            //  Elements ets7 = dl.select("table[width=90%][class=table][align=center]").get(1).children();
            List<Map<String, Object>> l=new ArrayList<>();
            Vector v_n = new Vector();
            Vector v_v = new Vector();
            for (Element et : ets) {
                v_n.add(et.text());
                //     System.out.print(et.text());
            }

            for (Element et : ets2) {
                if (!et.text().equals("")) {
                    v_v.add(et.text());
                    //     System.out.print(et.text());

                }
            }

            for(int i=0;i<v_v.size();i++){
                Map<String,Object> map=new HashMap<>();
                map.put(0+"",v_n.get(i).toString());
                map.put(1+"",v_v.get(i).toString());
                l.add(map);
            }

            for (int i = 0; i < ets3.size(); i++) {
                Map<String,Object> map=new HashMap<>();
                Element e1 = ets3.get(i);
                Element e2 = ets4.get(i);
                if (!e1.text().equals("") && !e2.text().equals("")) {
                    v_v.add(e2.text() + " " + e1.text());
                    map.put(0+"",e2.text());
                    map.put(1+"",e1.text());
                    l.add(map);
                }
            }
            Map<String,Object> map=new HashMap<>();
            map.put(0+"",ets5.get(0).text());
            map.put(1+"",ets5.get(0).nextElementSibling().text());
            l.add(map);

//            for (int i = 0; i < ets6.size(); i++) {
//                Element e1 = ets6.get(i);
//                Elements e2 = e1.children();
//                for (Element e3 : e2) {
//                    Elements e4 = e3.getAllElements();
//                    if (e4.get(1).text().indexOf("学期") > -1) {
//                        v_v.add(e4.get(1).text());
//                    }
//                    v_v.add(e4.get(3).text() + " " + e4.get(5).text() + " " + e4.get(10).text());
//                }
//            }
//            for (int i = 0; i < ets7.size(); i++) {
//                Element e1 = ets7.get(i);
//                Elements e2 = e1.children();
//                for (Element e3 : e2) {
//                    Elements e4 = e3.getAllElements();
//                    if (e4.get(1).text().indexOf("学期") > -1) {
//                        v_v.add(e4.get(1).text());
//                    }
//                    v_v.add(e4.get(3).text() + " " + e4.get(4).text() + " " + e4.get(9).text());
//                }
//            }
//            System.out.print(v_v.toString());

            return l;
        } else {
            return null;
        }
    }
    //等级考试报名
    public static List<Map<String, Object>> getDegreeExamEnrol() throws IOException{
        connection.url(URL_DEGREEEXAMENROL);
        connection.cookies(connection.response().cookies());
        connection.timeout(70000);
        Document dl = connection.get();
        //System.out.println(dl.toString());
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        Element est1 = dl.select("form[name=form1] >table").get(3);
        Elements tbody = est1.children();
        int i = 1;
        for (Element tbodys : tbody) {
            while(i<=2) {
                Map<String, Object> map = new HashMap<String, Object>();
                if (i == 1) {
                    Element trs1 = tbodys.children().get(1);
                    Elements tr1 = trs1.children();
                    map.put(0 + "", tr1.get(1).text());
                    map.put(1 + "", tr1.get(2).text());
                    map.put(2 + "", tr1.get(3).text());
                    map.put(3 + "", tr1.get(4).text());
                    map.put(4 + "", tr1.get(5).text());
                    map.put(5 + "", tr1.get(6).text());
                    map.put(6 + "", tr1.get(7).text());
                    map.put(7 + "", tr1.get(8).text());
                    System.out.println(tr1.get(1).text() + " " + tr1.get(2).text());
                }
                if (i == 2) {
                    Element trs2 = tbodys.children().get(2);
                    Elements tr2 = trs2.children();
                    map.put(0 + "", tr2.get(1).text());
                    map.put(1 + "", tr2.get(2).text());
                    map.put(2 + "", tr2.get(3).text());
                    map.put(3 + "", tr2.get(4).text());
                    map.put(4 + "", tr2.get(5).text());
                    map.put(5 + "", tr2.get(6).text());
                    map.put(6 + "", tr2.get(7).text());
                    map.put(7 + "", tr2.get(8).text());
                }
                l.add(map);
                i += 1;
            }
        }
        return l;
    }
    //查看开设课程信息
    public static List<Map<String,Object>>getCheckOpenCourse() throws IOException{
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        connection.url(URL_CHECKOPENCOURSE);
        connection.cookies(connection.response().cookies());
        Document dl = connection.get();

        Elements trs = dl.select("table").select("tr");
        for(int i=5;i<trs.size();i++){
            {  Elements tds=trs.get(i).select("td");
                Map<String, Object> map = new HashMap<String, Object>();
                for(int j=0;j<tds.size();j++)
                {
                    String text=tds.get(j).text();
                    System.out.printf(text);
                    map.put(j+"", text);
                }
                System.out.println();
                l.add(map);
            }

        }
        return l;
    }
    //获取课程表的信息
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static List<Map<String, Object>> getSchedular() throws IOException{
        connection.url(URL_SCHEDULAR);
        connection.cookies(connection.response().cookies());
        Map<String, String> datas = new HashMap<>();
        datas.put("schoolyear", "2015");
        datas.put("semester", "2");
        connection.data(datas);
      // connection.timeout(70000);
        Document dl = connection.get();
        //    System.out.println(dl.toString());
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        Element est1 = dl.select("table[bordercolordark=#ffffff] >tbody").get(1);
        Elements tr = est1.children();

        int i = 1;
        for (Element tds : tr) {
            // System.out.println("tds:" + tds.text());

            Elements td = tds.children();
            Map<String, Object> map = new HashMap<String, Object>();
            int j = 1;
            for (Element t : td) {
                // System.out.println("t:" + t.text());

                map.put(j + "", t.text());
                j += 1;
            }
            // System.out.println(i + ":" + map.toString());
            l.add(map);
            i += 1;
        }


        System.out.print(l.toString());
        return l;
    }

    //获取md5编码
    public static String getMD5() throws IOException {
        String url = "http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp";
        connection.url(url);
        connection.timeout(50000);
        connection.cookies(connection.response().cookies());
        Document dl = connection.get();
        Element td = dl.getElementsByTag("td").get(52);
        md5Code = (String) td.attr("onclick").split("&")[2].subSequence(8, 40);
        return md5Code;
    }
    public static void getStudentid() throws IOException {
        String url = "http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp";
        connection.url(url);
     connection.timeout(50000);
        connection.cookies(connection.response().cookies());
        Document dl = connection.get();
        System.out.print("studentid:");
        String regEx = "studentid";
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Elements sid = dl.getElementsByAttributeValueMatching("onclick", p);
        String studentid = sid.get(0).toString();
        Pattern pattern = Pattern.compile("(?<=(studentid))=[\\S]+=");
        Matcher matcher = pattern.matcher(studentid);

        Pattern pattern_table=Pattern.compile("(?<=(studentid=))[\\d]+(?=('))");
        Matcher matcher_table=pattern_table.matcher(dl.toString());
        while (matcher_table.find()) {
            get_table_sid = matcher_table.group();
            System.out.println("table_sid:"+get_table_sid);
        }

        Pattern pattern_penalties=Pattern.compile("(?<=(serialabc=))[\\S]+(?=('))");
        Matcher matcher_penalties=pattern_penalties.matcher(dl.toString());
        while (matcher_penalties.find()) {
            penalties = matcher_penalties.group();
            System.out.println("penalties:"+penalties);
        }

        Pattern pattern_attendance=Pattern.compile("(?=(attendance))[\\S]+method[\\S]+(?=('))");
        Matcher matcher_attendance=pattern_attendance.matcher(dl.toString());
        while (matcher_attendance.find()) {
            attendance = matcher_attendance.group();
            System.out.println("attendance:"+attendance);
        }


        while (matcher.find()) {
            studentid = matcher.group();
            System.out.println("studentid"+studentid);
        }
        //System.out.print(URL_INFOEMATION.charAt(URL_INFOEMATION.length() -1 ));
        if( !"=".equals( URL_INFOEMATION.charAt(URL_INFOEMATION.length() -1 ))){

            URL_INFOEMATION = URL_INFOEMATION + studentid;
            URL_TEST_TABLE=URL_TEST_TABLE+get_table_sid;
            URL_COST=URL_COST+studentid;
            URL_ATTENDANCE=URL_ATTENDANCE+attendance;
            URL_ATTENDANCE=URL_ATTENDANCE.replaceAll("&amp;","&");
            //URL_PENALTIES=URL_PENALTIES+penalties;
        }


        System.out.println(URL_ATTENDANCE);
    }
               //获取个人成绩
    static public List<Map<String, Object>> getScore() throws IOException {
          connection.timeout(30000);
        connection.url(URL_INFOEMATION);
        connection.cookies(connection.response().cookies());
       // System.out.println(connection.response().cookies());

     Document dl = connection.get();
    //    System.out.println(dl.toString());
        Elements ets6 = dl.select("table[width=90%][class=table][align=center]").get(0).children();
        List list = new ArrayList();
        int k=1;
        int z=1;

        for (int i = 0; i < ets6.size(); i++) {
            Element e1 = ets6.get(i);
            Elements e2 = e1.children();
            for (Element e3 : e2) {
                Map<String, Object> map = new HashMap<>();
                Elements e4 = e3.getAllElements();
                if(k==1){
                    map.put(0+"",e4.get(1).text());
                    map.put(1+"",e4.get(3).text());
                    map.put(2+"",e4.get(4).text());
                    map.put(3+"", e4.get(9).text());
                    list.add(map);
                    k=0;
                }else{
                    if (e4.get(1).text().indexOf("学期") > -1) {
                        map.put(0+"",e4.get(1).text());
                    }
                    map.put(0+"",e4.get(1).text());
                    map.put(1+"",e4.get(3).text());
                    map.put(2+"",e4.get(5).text());
                    map.put(3+"", e4.get(10).text());
                    list.add(map);
                }
            }
        }

        Elements ets7 = dl.select("table[width=90%][class=table][align=center]").get(1).children();

        for (int i = 0; i < ets7.size(); i++) {
            Element e1 = ets7.get(i);
            Elements e2 = e1.children();
            for (Element e3 : e2) {
                Elements e4 = e3.getAllElements();
                Map<String, Object> map = new HashMap<>();

                if (z == 1) {
                    map.put(0 + "", e4.get(1).text());
                    map.put(1 + "", e4.get(3).text());
                    map.put(2 + "", e4.get(3).text());
                    map.put(3 + "", e4.get(6).text());
                    list.add(map);
                    z = 0;
                } else {
                    if (e4.get(1).text().indexOf("学期") > -1) {
                        map.put(0 + "", e4.get(1).text());
                    }
                    map.put(0 + "", e4.get(1).text());
                    map.put(1 + "", e4.get(3).text());
                    map.put(2 + "", e4.get(4).text());
                    map.put(3 + "", e4.get(9).text());
                    list.add(map);
                }
            }
        }
        return list;
    }
    //获取违规记录信息
    public static List<Map<String, Object>> getViolationRecord() throws IOException{
        connection.url(URL_VIOLATIONRECORD);
        connection.cookies(connection.response().cookies());
        Map<String, String> datas = new HashMap<>();
        datas.put("method", "doMain");
        datas.put("gzCode", username);
        datas.put("md5Code",md5Code);
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        connection.data(datas);
        Document dl = connection.get();
        Elements trs = dl.select("table[border=1]").select("tr");
        for(Element tr :trs){
            Map<String, Object> map=new HashMap<>();
            Elements tds=tr.children();
            if(tds.size()==2){
                map.put(0+"", tds.get(0).text());
                map.put(1+"", tds.get(1).text());
                // System.out.println(tds.get(0).text()+"  "+tds.get(1).text());
            }else{
                map.put(0+"", tds.get(0).text());
                //	  System.out.println( tds.get(0).text());

            }
            l.add(map);
        }
        return l;
    }
  //获取考试时间表信息
    public static List<Map<String, Object>> get_text_table() throws IOException {

        connection.url(URL_TEST_TABLE);
        connection.cookies(connection.response().cookies());
        Document dl=connection.get();
        Elements tbodys=dl.select("table[width=90%]").get(3).children();
        List<Map<String, Object>> l=new ArrayList<>();
        for( Element tbody:tbodys){
            Elements trs=tbody.children();
            for(Element tr:trs){
                Map<String, Object> map=new HashMap<>();
                Elements td=tr.children();
                map.put(0+"",td.get(0).text());
                map.put(1+"",td.get(1).text());
                map.put(2+"",td.get(2).text());
                map.put(3+"",td.get(3).text());
                map.put(4+"",td.get(5).text());
                map.put(5+"",td.get(6).text());
                map.put(6+"",td.get(7).text());
                l.add(map);
            }
        }
        return l;
    }
//预交费用
    public static List<Map<String, Object>> get_cost() throws IOException {
        connection.url(URL_COST);
        connection.cookies(connection.response().cookies());
        Document dl=connection.get();
        List<Map<String, Object>> l=new ArrayList<>();
        Elements tbodys=dl.select("table[class=table]").get(0).children();
        for(Element tbody:tbodys){
            Elements trs=tbody.children();
            for(Element tr:trs){
                Map<String, Object> map=new HashMap<>();
                Elements tds=tr.children();


                if(tds.size()==2){
                    map.put(0+"","");
                    map.put(1+"","");
                    map.put(2+"","");
                    map.put(3+"",tds.get(0).text());
                    map.put(4+"",tds.get(1).text());
                }else{
                    map.put(0+"",tds.get(0).text());
                    map.put(1+"",tds.get(1).text());
                    map.put(2+"",tds.get(2).text());
                    map.put(3+"",tds.get(3).text());
                    map.put(4+"",tds.get(4).text());
                }
                l.add(map);
            }
        }
        return l;
    }
  //奖励信息
    public static List<Map<String, Object>> get_penalties() throws IOException {
        connection.url(URL_PENALTIES);
        Map<String, String> datas = new HashMap<>();
        datas.put("stuname","");
        datas.put("gzcode",username);
        datas.put("serialabc",penalties);
        connection.timeout(60000);
        connection.cookies(connection.response().cookies());
        Document dl = connection.data(datas).get();
        List<Map<String, Object>> l = new ArrayList<>();
        Elements tbodys = dl.select("table[class=table1]").get(1).children();
        Elements tbodys2 = dl.select("table[class=table1]").get(2).children();
        for (Element tbody : tbodys) {
            Elements trs = tbody.children();
            for (Element tr : trs) {
                Map<String, Object> map = new HashMap<>();
                Elements tds = tr.children();
                if (tds.size() == 1) {
                    map.put(0 + "", tds.get(0).text());
                    map.put(1 + "", "");
                    map.put(2 + "", "");
                    map.put(3 + "", "");
                    map.put(4 + "", "");
                    map.put(5 + "", "'");
                } else {
                    map.put(0 + "", tds.get(0).text());
                    map.put(1 + "", tds.get(1).text());
                    map.put(2 + "", tds.get(2).text());
                    map.put(3 + "", tds.get(3).text());
                    map.put(4 + "", tds.get(4).text());
                    map.put(5 + "", tds.get(5).text());
                }
                l.add(map);
            }
        }
        for (Element tbody : tbodys2) {
            Elements trs = tbody.children();
            for (Element tr : trs) {
                Map<String, Object> map = new HashMap<>();
                Elements tds = tr.children();
                if (tds.size() == 1) {
                    map.put(0 + "", tds.get(0).text());
                    map.put(1 + "", "");
                    map.put(2 + "", "");
                    map.put(3 + "", "");
                    map.put(4 + "", "");
                    map.put(5 + "", "'");
                } else {
                    map.put(0 + "", tds.get(0).text());
                    map.put(1 + "", tds.get(1).text());
                    map.put(2 + "", tds.get(2).text());
                    map.put(3 + "", tds.get(3).text());
                    map.put(4 + "", tds.get(4).text());
                    map.put(5 + "", tds.get(5).text());
                }
                l.add(map);
            }
        }
        return l;
    }
//考勤信息
    public static List<Map<String, Object>> get_attendance() throws IOException {

        connection.url(URL_ATTENDANCE);
        connection.timeout(60000);
        connection.cookies(connection.response().cookies());
        Document dl = connection.get();
        List<Map<String, Object>> l = new ArrayList<>();
        Elements tbodys = dl.select("table[class=table]").get(0).children();
        Element att = dl.select("td[width=70%]").get(0);
        //  l.add("0",att.text());
        for (Element tbody : tbodys) {
            Elements trs = tbody.children();
            for (Element tr : trs) {
                Map<String, Object> map = new HashMap<>();
                Elements tds = tr.children();
                map.put(0 + "", tds.get(0).text());
                map.put(1 + "", tds.get(1).text());
                map.put(2 + "", tds.get(2).text());
                l.add(map);
            }
        }
        return  l;
    }

}