import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Storage {
    private File file;
    private boolean fileExists;
    private TasksList list;

    public Storage(TasksList list) {
        this.list = list;
        Path path = Paths.get("data/duke.txt");
        this.fileExists = java.nio.file.Files.exists(path);
        this.file = path.toFile();
    }

    public void findData() {
        try {
            DukeExceptions.checkPastData(fileExists);
            //System.out.println("Successfully retrieved data");
        } catch (PastDataDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadData() {
        try {
            Scanner sc = new Scanner(this.file);
            while (sc.hasNextLine()) {
                String[] input = sc.nextLine().split(" \\| ");
                Tasks task;
                if (input[0].equals("T")) {
                    task = new Todo(input[2]);
                } else if (input[0].equals("D")) {
                    String datetime = input[3];
                    String newDateTime = dateTimeFormat(input[3]);
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    LocalDateTime formattedDeadline = LocalDateTime.parse(newDateTime, dateTimeFormatter);
                    task = new Deadline(input[2], formattedDeadline);
                } else {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                    String[] split = input[3].split(" - ");
                    String from = dateTimeFormat(split[0]);
                    String by = dateTimeFormat(split[1]);
                    LocalDateTime formattedstartTime = LocalDateTime.parse(from, dateTimeFormatter);
                    LocalDateTime formattedendTime= LocalDateTime.parse(by, dateTimeFormatter);
                    task = new Event(input[2], formattedstartTime, formattedendTime);
                }
                if (input[1].equals("1")) {
                    task.markAsDone();
                }
                list.addTask(task);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void saveData() {
        try {
            FileWriter writer = new FileWriter("data/duke.txt");
            for (int i = 0; i < list.getSize(); i++) {
                String line = list.getTask(i).log();
                writer.write(line);

            }
            writer.close();
            //System.out.println("Data successfully saved!");
        } catch (IOException e) {
            System.out.println("☹ OOPS!!! I cannot write to the file!");
        }
    }

    private String dateTimeFormat(String datetime) {
        String year = datetime.substring(0,4);
        String month = datetime.substring(5,7);
        String day = datetime.substring(8,10);
        String hour = datetime.substring(11,13);
        String min = datetime.substring(14,16);
        String newDateTime = day + "/" + month + "/" + year + " " + hour + min;
        return newDateTime;
    }
}
