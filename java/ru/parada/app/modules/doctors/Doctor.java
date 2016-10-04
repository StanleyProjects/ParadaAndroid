package ru.parada.app.modules.doctors;

import ru.parada.app.contracts.DoctorsContract;

public class Doctor
    implements DoctorsContract.ListItemModel
{
    private int id;
    private String last_name;
    private String first_name;
    private String middle_name;
    private String first_position;
    private String second_position;
    private String third_position;
    private String image_path;

    public Doctor(int i, String ln, String fn, String mn, String fp, String sp, String tp)
    {
        this.id = i;
        this.last_name = ln;
        this.first_name = fn;
        this.middle_name = mn;
        this.first_position = fp;
        this.second_position = sp;
        this.third_position = tp;
    }
    public Doctor(int i, String ln, String fn, String mn, String fp, String sp, String tp, String ip)
    {
        this(i, ln, fn, mn, fp, sp, tp);
        this.image_path = ip;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getPhotoPath()
    {
        return image_path;
    }

    @Override
    public String getLastName()
    {
        return last_name;
    }

    @Override
    public String getFirstName()
    {
        return first_name;
    }

    @Override
    public String getMiddleName()
    {
        return middle_name;
    }

    @Override
    public String getFirstPosition()
    {
        return first_position;
    }

    @Override
    public String getSecondPosition()
    {
        return second_position;
    }

    @Override
    public String getThirdPosition()
    {
        return third_position;
    }
}