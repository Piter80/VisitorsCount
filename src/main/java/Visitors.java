import java.io.*;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Visitors {
    private static final int HOURSOFWORK = 10;
    private File file;
    private int interval = 30;
    private List<Visitor> visitors = new ArrayList<>();

    public static void main(String[] args) {
        Visitors visitors = new Visitors();
        visitors.getFileName();
        visitors.getNumbersFromFile();
        visitors.getStatistics();
       /* for (Visitor v: visitors.visitors) {
            System.out.println(v);
        }
        System.out.println(visitors.interval);*/

    }

    private void getStatistics() {
        int inervalsInDay = HOURSOFWORK * 60 / interval;
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
        Date currentBegin = null;
        Map<Date, Integer> countVisitors = new HashMap<>();

        try {
            currentBegin = parser.parse("08:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentEnd = DateUtils.addMinutes(currentBegin, interval);

        List<Visitor> visitorsTmp = new ArrayList<Visitor>(visitors);
        for (int i = 0; i < inervalsInDay - 1; i++) {
            int listCount = visitorsTmp.size();
            int counter = 0;
            for (Visitor v: visitorsTmp) {v.setChecked(false);}
            for (int j = 0; j < listCount; j++) {
                if (currentBegin.before(visitorsTmp.get(j).dateIn) && currentEnd.after(visitorsTmp.get(j).dateOut)) {
                    counter++;
                    visitorsTmp.remove(visitorsTmp.get(j));
                    j--;
                    listCount--;
                } else if (currentEnd.after(visitorsTmp.get(j).dateIn)) {
                    counter++;
                }

            }
            countVisitors.put(currentBegin, counter);
            currentBegin = currentEnd;
            currentEnd = DateUtils.addMinutes(currentBegin, interval);
        }
        int maxVisitors = 0;
        Date maxLoad = null;

        for (
                Map.Entry<Date, Integer> entry : countVisitors.entrySet()) {
            if (maxVisitors < entry.getValue()) {
                maxLoad = entry.getKey();
                maxVisitors = entry.getValue();
            }

        }

        System.out.println("Maximum visitors in interval from " + parser.format(maxLoad) + " to " + parser.format(DateUtils.addMinutes(maxLoad, interval)) + " and there were visitors " + maxVisitors);


    }


    private void getNumbersFromFile() {
        //if file writed in OS Windows and started with BOM symbol
        Character bom = 65279;
        try {
            //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\IdeaProjects\\visitors\\src\\main\\resources\\visitors.txt")));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)));
            int count = 0;
            while (reader.ready()) {
                String[] str = reader.readLine().replace(bom, ' ').trim().split(" ");
                /*int hoursIn = Integer.parseInt(str[0].split(":")[0]);
                int minutesIn = Integer.parseInt(str[0].split(":")[1]);
                int secondsIn = Integer.parseInt(str[0].split(":")[2]);

                int hoursOut = Integer.parseInt(str[1].split(":")[0]);
                int minutesOut = Integer.parseInt(str[1].split(":")[1]);
                int secondsOut = Integer.parseInt(str[1].split(":")[2]);*/
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
                Date timeIn = null;
                Date timeOut = null;
                try {
                    timeIn = parser.parse(str[0]);
                    timeOut = parser.parse(str[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
/*
                LocalTime timeIn = LocalTime.of(hoursIn, minutesIn, secondsIn);
                LocalTime timeOut = LocalTime.of(hoursOut, minutesOut, secondsOut);
*/

                this.visitors.add(new Visitor(timeIn, timeOut));
            }
        } catch (IOException e) {
            System.out.println("Error when reading numbers from file. Maybe file have wrong format.");
            e.printStackTrace();
        }
    }

    private void getFileName() {
        System.out.println("Please enter destination of file with rectangle coordinats: ");
        File readed = null;
        int interval = 30;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String fileName = reader.readLine();
            readed = new File(fileName);
            if (!readed.exists() || readed.isDirectory())
                throw new IllegalArgumentException("File not exist or its directory.");
            System.out.println("Please inter interval (in minutes: ), or press Enter for interval in 30 minutes");
            String intervalTmp = reader.readLine();
            if (intervalTmp.length() > 0)
                interval = Integer.parseInt(intervalTmp);

        } catch (IOException e) {
            System.out.println("Error, when reading filename. Maybe you write something illegal in console.\n" + e.toString());
        }
        this.file = readed;
        this.interval = interval;
    }

}
