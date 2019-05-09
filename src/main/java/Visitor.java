import java.util.Date;

public class Visitor {
    Date dateIn;
    Date dateOut;
    boolean checked = false;

    public Visitor(Date dateIn, Date dateOut) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "dateIn=" + dateIn +
                ", dateOut=" + dateOut +
                '}';
    }
}
