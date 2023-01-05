package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        try {
            Jsoup.connect("http://10.20.15.38/hlpdsk_pcc/index.php").timeout(6000).get();
        } catch (Exception e) {
            System.exit(0);
        }

        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateStart = now.toString();
        String year = dateStart.substring(0, 4);
        String mount = dateStart.substring(5, 7);
        String day = dateStart.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        int dayOfMount = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
        LocalDate today = LocalDate.now();
        LocalDate lastOfMount = today.withDayOfMonth(today.lengthOfMonth());
        String d = lastOfMount.toString();
        String lastDate = d.substring(8, 10);
        String lastMount = d.substring(5, 7);
        Document getCookie = Jsoup.connect("http://10.20.15.38/hlpdsk_pcc/index.php").timeout(6000).get();
        String cookie = getCookie.connection().cookieStore().getCookies().get(0).getValue();
        Document data = Jsoup.connect("http://10.20.15.38/hlpdsk_pcc/check_login.php").data("username", "tech").data("password", "lhs2541").data("Submit2", "").cookie("PHPSESSID", cookie).timeout(6000).post();
        Document getCall = data.connection().url("http://10.20.15.38/hlpdsk_pcc/helpdesk/report_by_supplier2.php").data("id_project", "ALL").
                data("type_service", "ALL").data("date_start", day + "-" + mount + "-" + year).data("date_end", lastDate + "-" + lastMount + "-" + year).data("start", "0").
                data("submit", "Find").data("User-Agent", " Mozilla/5.0 (Macintosh").cookie("PHPSESSID", cookie).timeout(6000).post();


        //Get total call from 10.20.15.38
        int post = getCall.getElementsByTag("tr").size() - 1;
        String location = args[0];
        //System.out.println(location);

        // Write calls to file


        System.out.println(post);
        //System.out.println(day);
        //System.out.println(getCall.body().getElementsByTag("tr").get(2).text());
        //String sendData = getCall.body().getElementsByTag("tr").get(2).text();
        //System.out.println(send);
        //System.out.println(today.getMonthValue());
        //Jsoup.connect("https://rdcall.herokuapp.com/post").data(" ",send).ignoreContentType(true).post();
        //Document getDate = Jsoup.connect("https://rdcall-production.up.railway.app/welcome").ignoreContentType(true).get();
        // Writcall writcall = new Writcall();
        // writcall.writeDate(getDate.text());
        // String writeToDate = writcall.readDate();
        //System.out.println(writeToDate);

        //System.out.println(writeToDate.substring(1,11));
        // DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // String record = writeToDate.substring(0,10);
        // System.out.println(record);

        //Date recordDate = format.parse(writeToDate.substring(1,11));
        // System.out.println(recordDate + " recordDate");
        // System.out.println(recordDate.toString());
        // System.out.println(now.toString().substring(0,10));

        //Date record1 =  format.parse(now.toString().substring(0,10));
        //System.out.println(record1 + " record1");
        // compare date
        //int checkDate = record1.compareTo(recordDate);
        // System.out.println(checkDate);


        //get count call from Google sheets
        Document countCall = Jsoup.connect("https://script.google.com/macros/s/AKfycbyA4aMWncFv-rD2zscnty1Qh6WL4qK2GwflKrudP4TtxgUxyT1M0srNBCIVc2dQFF2YOw/exec?action=getCall").ignoreContentType(true).get();
        // System.out.println(countCall.text());

        //regex data from Google sheet
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(countCall.text());
        String call = "";
        while (matcher.find()) {
            call += matcher.group();

        }
        //System.out.println(call);
        int oldCall = Integer.parseInt(call.substring(0, call.length() - 1));
        String lastCall = Integer.toString(post);

        //Check call update
        String send = call.substring(0, call.length() - 1);
        //System.out.println(send);
        if (post > oldCall) {
            String compare = Integer.toString(post - (post - oldCall));
            System.out.println(compare);
            Document getCallCompare = data.connection().url("http://10.20.15.38/hlpdsk_pcc/helpdesk/report_by_supplier2.php").data("id_project", "ALL").
                    data("type_service", "ALL").data("date_start", day + "-" + mount + "-" + year).data("date_end", lastDate + "-" + lastMount + "-" + year).data("start", compare).
                    data("submit", "Find").data("User-Agent", " Mozilla/5.0 (Macintosh").cookie("PHPSESSID", cookie).timeout(6000).post();

            //System.out.println(a);
            //System.out.println(post - oldCall);
            Jsoup.connect("https://bot-production-8999.up.railway.app/post").data(" ",location).data("location",location).ignoreContentType(true).post();

            for(int i=0;i < (post-oldCall);i++) {
            String sendData = getCallCompare.body().getElementsByTag("tr").get(i+1).text();
            Jsoup.connect("https://bot-production-8999.up.railway.app/post").data(" ", sendData).data("location", location).ignoreContentType(true).post();
            Thread.sleep(3000);
            }
            String a = Integer.toString(post);
            Jsoup.connect("https://script.google.com/macros/s/AKfycbyA4aMWncFv-rD2zscnty1Qh6WL4qK2GwflKrudP4TtxgUxyT1M0srNBCIVc2dQFF2YOw/exec?").data("action","update").data("name",a).ignoreContentType(true).get();

            }else {
             System.exit(0);
            }
    }

}


