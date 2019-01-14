package projekt.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjektDTO {

    private String name;
    private String client;
    private String branch;
    private Date date;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getDate() {
        return date;
    }

    // exception :-??
    public void setDate(String date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            this.date = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProjektDTO{" +
                "name='" + name + '\'' +
                ", client='" + client + '\'' +
                ", branch='" + branch + '\'' +
                ", date=" + date.toString() +
                ", description='" + description + '\'' +
                '}';
    }
}
